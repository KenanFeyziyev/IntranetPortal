package com.intranet.portal.service.impl;

import com.intranet.portal.dto.attendance.AttendanceCheckInRequest;
import com.intranet.portal.dto.attendance.AttendanceCreateRequest;
import com.intranet.portal.dto.attendance.AttendanceResponse;
import com.intranet.portal.dto.attendance.AttendanceUpdateRequest;
import com.intranet.portal.dto.employee.EmployeeResponse;
import com.intranet.portal.entity.Attendance;
import com.intranet.portal.entity.Employee;
import com.intranet.portal.exception.BadRequestException;
import com.intranet.portal.exception.NotFoundException;
import com.intranet.portal.mapper.AttendanceMapper;
import com.intranet.portal.mapper.EmployeeMapper;
import com.intranet.portal.repository.AttendanceRepository;
import com.intranet.portal.repository.EmployeeRepository;
import com.intranet.portal.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceMapper attendanceMapper;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponse> getAll() {
        log.info("Fetching all attendances");

        return attendanceRepository.findAllWithEmployee()
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceResponse getById(Long id) {
        log.info("Fetching attendance by id: {}", id);

        Attendance attendance = attendanceRepository.findByIdWithEmployee(id)
                .orElseThrow(() -> new NotFoundException("Attendance not found: " + id));

        return attendanceMapper.toResponse(attendance);
    }

    @Override
    public Long create(AttendanceCreateRequest request) {
        log.info("Creating attendance for employee id: {}", request.employeeId());

        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new NotFoundException("Employee not found: " + request.employeeId()));

        Attendance attendance = attendanceMapper.toEntity(request);
        attendance.setEmployee(employee);

        Attendance saved = attendanceRepository.save(attendance);

        log.info("Attendance created successfully with id: {}", saved.getId());
        return saved.getId();
    }

    @Override
    public AttendanceResponse update(Long id, AttendanceUpdateRequest request) {
        log.info("Updating attendance with id: {}", id);

        Attendance existing = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendance not found: " + id));

        attendanceMapper.updateEntityFromDto(request, existing);

        if (request.employeeId() != null) {
            log.info("Updating employee for attendance id: {} to employee id: {}", id, request.employeeId());

            Employee employee = employeeRepository.findById(request.employeeId())
                    .orElseThrow(() -> new NotFoundException("Employee not found: " + request.employeeId()));
            existing.setEmployee(employee);
        }

        Attendance saved = attendanceRepository.save(existing);

        log.info("Attendance updated successfully with id: {}", saved.getId());
        return attendanceMapper.toResponse(saved);
    }

    @Override
    public AttendanceResponse checkIn(AttendanceCheckInRequest request) {
        log.info("Check-in attempt for card code: {}", request.cardCode());

        Employee employee = employeeRepository.findByCardCode(request.cardCode())
                .orElseThrow(() -> new NotFoundException("Employee not found for card code: " + request.cardCode()));

        LocalDate today = LocalDate.now();

        attendanceRepository.findTodayAttendance(employee.getId(), today)
                .ifPresent(attendance -> {
                    log.warn("Check-in rejected. Attendance already exists for employee id: {} on date: {}", employee.getId(), today);
                    throw new BadRequestException("Attendance already exists for today");
                });

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .actionDate(LocalDateTime.now())
                .build();

        Attendance saved = attendanceRepository.save(attendance);

        log.info("Check-in completed successfully for employee id: {}, attendance id: {}", employee.getId(), saved.getId());
        return attendanceMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting attendance with id: {}", id);

        Attendance existing = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendance not found: " + id));

        attendanceRepository.delete(existing);

        log.info("Attendance deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getLateEmployees(LocalDate date) {
        log.info("Fetching late employees for date: {}", date);

        LocalTime lateTime = LocalTime.of(9, 5);

        return attendanceRepository.findByActionDate(date)
                .stream()
                .filter(attendance -> attendance.getActionDate().toLocalTime().isAfter(lateTime))
                .map(attendance -> employeeMapper.toResponse(attendance.getEmployee()))
                .toList();
    }
}