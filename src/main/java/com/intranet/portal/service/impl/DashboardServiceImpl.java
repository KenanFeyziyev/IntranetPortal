package com.intranet.portal.service.impl;

import com.intranet.portal.dto.DashboardSummaryResponse;
import com.intranet.portal.repository.AttendanceRepository;
import com.intranet.portal.repository.DepartmentRepository;
import com.intranet.portal.repository.EmployeeRepository;
import com.intranet.portal.repository.WorkPermitRepository;
import com.intranet.portal.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final WorkPermitRepository workPermitRepository;

    @Override
    public DashboardSummaryResponse getSummary() {
        long employeeCount = employeeRepository.count();
        long departmentCount = departmentRepository.count();

        LocalTime lateTime = LocalTime.of(9, 5);
        long todayLateCount = attendanceRepository.findByActionDate(LocalDate.now())
                .stream()
                .filter(attendance -> attendance.getActionDate().toLocalTime().isAfter(lateTime))
                .count();

        long pendingWorkPermitCount = workPermitRepository.findAll()
                .stream()
                .filter(workPermit -> !Boolean.TRUE.equals(workPermit.getIsApproved()) && !Boolean.TRUE.equals(workPermit.getIsDeleted()))
                .count();

        return new DashboardSummaryResponse(
                employeeCount,
                departmentCount,
                todayLateCount,
                pendingWorkPermitCount
        );
    }
}