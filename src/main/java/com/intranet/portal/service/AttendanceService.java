package com.intranet.portal.service;

import com.intranet.portal.dto.attendance.AttendanceCheckInRequest;
import com.intranet.portal.dto.attendance.AttendanceCreateRequest;
import com.intranet.portal.dto.attendance.AttendanceResponse;
import com.intranet.portal.dto.attendance.AttendanceUpdateRequest;
import com.intranet.portal.dto.employee.EmployeeResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    List<AttendanceResponse> getAll();

    AttendanceResponse getById(Long id);

    Long create(AttendanceCreateRequest request);

    AttendanceResponse update(Long id, AttendanceUpdateRequest request);

    AttendanceResponse checkIn(AttendanceCheckInRequest request);

    List<EmployeeResponse> getLateEmployees(LocalDate date);

    void delete(Long id);

    AttendanceResponse checkInForCurrentUser(String email);
}