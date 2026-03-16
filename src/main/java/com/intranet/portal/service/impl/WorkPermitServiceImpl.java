package com.intranet.portal.service.impl;

import com.intranet.portal.dto.workpermit.WorkPermitApproveRequest;
import com.intranet.portal.dto.workpermit.WorkPermitCreateRequest;
import com.intranet.portal.dto.workpermit.WorkPermitResponse;
import com.intranet.portal.dto.workpermit.WorkPermitUpdateRequest;
import com.intranet.portal.entity.Employee;
import com.intranet.portal.entity.WorkPermit;
import com.intranet.portal.exception.BadRequestException;
import com.intranet.portal.exception.NotFoundException;
import com.intranet.portal.mapper.WorkPermitMapper;
import com.intranet.portal.repository.EmployeeRepository;
import com.intranet.portal.repository.WorkPermitRepository;
import com.intranet.portal.service.WorkPermitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkPermitServiceImpl implements WorkPermitService {

    private final WorkPermitRepository workPermitRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkPermitMapper workPermitMapper;

    @Override
    @Transactional(readOnly = true)
    public List<WorkPermitResponse> getAll() {

        log.info("Fetching all work permits");

        return workPermitRepository.findAllWithEmployee()
                .stream()
                .map(workPermitMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WorkPermitResponse getById(Long id) {

        log.info("Fetching work permit by id: {}", id);

        WorkPermit workPermit = workPermitRepository.findByIdWithEmployee(id)
                .orElseThrow(() -> new NotFoundException("WorkPermit not found: " + id));

        return workPermitMapper.toResponse(workPermit);
    }

    @Override
    public Long create(WorkPermitCreateRequest request) {

        log.info("Creating work permit for employee id: {}", request.employeeId());

        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new NotFoundException("Employee not found: " + request.employeeId()));

        validateDates(request.startDate(), request.endDate());

        WorkPermit workPermit = workPermitMapper.toEntity(request);
        workPermit.setEmployee(employee);
        workPermit.setIsApproved(false);
        workPermit.setApprovedDate(null);
        workPermit.setApprovedBy(null);
        workPermit.setIsDeleted(false);
        workPermit.setPermitDate(LocalDateTime.now());
        workPermit.setPermitDurationInMinutes(
                (int) Duration.between(request.startDate(), request.endDate()).toMinutes()
        );

        WorkPermit saved = workPermitRepository.save(workPermit);

        log.info("Work permit created successfully with id: {}", saved.getId());

        return saved.getId();
    }

    @Override
    public WorkPermitResponse update(Long id, WorkPermitUpdateRequest request) {

        log.info("Updating work permit with id: {}", id);

        WorkPermit existing = workPermitRepository.findByIdWithEmployee(id)
                .orElseThrow(() -> new NotFoundException("WorkPermit not found: " + id));

        LocalDateTime newStartDate = request.startDate() != null ? request.startDate() : existing.getStartDate();
        LocalDateTime newEndDate = request.endDate() != null ? request.endDate() : existing.getEndDate();

        validateDates(newStartDate, newEndDate);

        workPermitMapper.updateEntityFromDto(request, existing);

        if (request.employeeId() != null) {

            log.info("Updating employee for work permit id: {} to employee id: {}", id, request.employeeId());

            Employee employee = employeeRepository.findById(request.employeeId())
                    .orElseThrow(() -> new NotFoundException("Employee not found: " + request.employeeId()));

            existing.setEmployee(employee);
        }

        existing.setPermitDurationInMinutes(
                (int) Duration.between(existing.getStartDate(), existing.getEndDate()).toMinutes()
        );

        WorkPermit saved = workPermitRepository.save(existing);

        log.info("Work permit updated successfully with id: {}", saved.getId());

        return workPermitMapper.toResponse(saved);
    }

    @Override
    public WorkPermitResponse approve(Long id, WorkPermitApproveRequest request) {

        log.info("Approving work permit with id: {}", id);

        WorkPermit workPermit = workPermitRepository.findByIdWithEmployee(id)
                .orElseThrow(() -> new NotFoundException("WorkPermit not found: " + id));

        if (Boolean.TRUE.equals(workPermit.getIsDeleted())) {
            log.warn("Approval failed. Work permit is deleted: {}", id);
            throw new BadRequestException("Deleted work permit cannot be approved: " + id);
        }

        if (Boolean.TRUE.equals(workPermit.getIsApproved())) {
            log.warn("Approval failed. Work permit already approved: {}", id);
            throw new BadRequestException("WorkPermit is already approved: " + id);
        }

        workPermit.setIsApproved(true);
        workPermit.setApprovedDate(LocalDateTime.now());
        workPermit.setApprovedBy(request.approvedBy());

        WorkPermit saved = workPermitRepository.save(workPermit);

        log.info("Work permit approved successfully with id: {}", saved.getId());

        return workPermitMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting work permit with id: {}", id);

        WorkPermit existing = workPermitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("WorkPermit not found: " + id));

        existing.setIsDeleted(true);

        workPermitRepository.save(existing);

        log.info("Work permit marked as deleted with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getTotalPermitDuration() {

        log.info("Calculating total work permit duration for current month");

        LocalDate today = LocalDate.now();

        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = today.withDayOfMonth(today.lengthOfMonth()).atTime(LocalTime.MAX);

        List<WorkPermit> workPermits = workPermitRepository.findMonthlyActivePermits(startOfMonth, endOfMonth);

        Map<String, Long> totalMinutesByEmployee = new LinkedHashMap<>();

        for (WorkPermit workPermit : workPermits) {

            if (workPermit.getStartDate() == null || workPermit.getEndDate() == null) {
                continue;
            }

            long totalMinutes = Duration.between(workPermit.getStartDate(), workPermit.getEndDate()).toMinutes();

            Employee employee = workPermit.getEmployee();
            String employeeKey = employee.getId() + " - " + employee.getFirstName() + " " + employee.getLastName();

            totalMinutesByEmployee.merge(employeeKey, totalMinutes, Long::sum);
        }

        Map<String, String> result = new LinkedHashMap<>();

        for (Map.Entry<String, Long> entry : totalMinutesByEmployee.entrySet()) {

            long totalMinutes = entry.getValue();
            long hours = totalMinutes / 60;
            long remainingMinutes = totalMinutes % 60;

            String duration = hours > 0
                    ? hours + " saat " + remainingMinutes + " dəqiqə"
                    : totalMinutes + " dəqiqə";

            result.put(entry.getKey(), duration);
        }

        return result;
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {

        if (startDate == null || endDate == null) {
            log.warn("Validation failed. Start date or end date is null");
            throw new BadRequestException("Start date and end date must not be null");
        }

        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            log.warn("Validation failed. End date must be after start date");
            throw new BadRequestException("End date must be after start date");
        }
    }
}