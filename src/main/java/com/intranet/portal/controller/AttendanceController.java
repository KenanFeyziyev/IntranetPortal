package com.intranet.portal.controller;

import com.intranet.portal.dto.attendance.AttendanceCheckInRequest;
import com.intranet.portal.dto.attendance.AttendanceCreateRequest;
import com.intranet.portal.dto.attendance.AttendanceResponse;
import com.intranet.portal.dto.attendance.AttendanceUpdateRequest;
import com.intranet.portal.dto.employee.EmployeeResponse;
import com.intranet.portal.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public List<AttendanceResponse> getAll() {
        log.info("HTTP GET /api/attendances called");
        return attendanceService.getAll();
    }

    @GetMapping("/{id}")
    public AttendanceResponse getById(@PathVariable Long id) {
        log.info("HTTP GET /api/attendances/{} called", id);
        return attendanceService.getById(id);
    }

    @PostMapping
    public Long create(@RequestBody @Valid AttendanceCreateRequest request) {
        log.info("HTTP POST /api/attendances called");
        return attendanceService.create(request);
    }

    @PostMapping("/check-in")
    public AttendanceResponse checkIn(@RequestBody @Valid AttendanceCheckInRequest request) {
        log.info("HTTP POST /api/attendances/check-in called");
        return attendanceService.checkIn(request);
    }

    @PutMapping("/{id}")
    public AttendanceResponse update(@PathVariable Long id,
                                     @RequestBody @Valid AttendanceUpdateRequest request) {
        log.info("HTTP PUT /api/attendances/{} called", id);
        return attendanceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("HTTP DELETE /api/attendances/{} called", id);
        attendanceService.delete(id);
    }

    @GetMapping("/late")
    public List<EmployeeResponse> getLateEmployees(@RequestParam LocalDate date) {
        log.info("HTTP GET /api/attendances/late called with date: {}", date);
        return attendanceService.getLateEmployees(date);
    }
}