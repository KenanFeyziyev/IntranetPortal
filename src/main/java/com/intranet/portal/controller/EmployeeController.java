package com.intranet.portal.controller;

import com.intranet.portal.dto.birthday.BirthdayCelebrantResponse;
import com.intranet.portal.dto.employee.EmployeeCreateRequest;
import com.intranet.portal.dto.employee.EmployeeResponse;
import com.intranet.portal.dto.employee.EmployeeUpdateRequest;
import com.intranet.portal.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponse> getAll() {
        log.info("HTTP GET /api/employees called");
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable Long id) {
        log.info("HTTP GET /api/employees/{} called", id);
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Long create(@RequestBody @Valid EmployeeCreateRequest request) {
        log.info("HTTP POST /api/employees called");
        return employeeService.addEmployee(request);
    }

    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id, @RequestBody @Valid EmployeeUpdateRequest request) {
        log.info("HTTP PUT /api/employees/{} called", id);
        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("HTTP DELETE /api/employees/{} called", id);
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/net-salary")
    public BigDecimal netSalary(@RequestParam("grossSalary") BigDecimal grossSalary) {
        log.info("HTTP GET /api/employees/net-salary called with grossSalary: {}", grossSalary);
        return employeeService.calculateNetSalary(grossSalary);
    }

    @GetMapping("/birthdays/this-month")
    public List<BirthdayCelebrantResponse> birthdaysThisMonth() {
        log.info("HTTP GET /api/employees/birthdays/this-month called");
        return employeeService.getBirthdayCelebrantsThisMonth();
    }

    @GetMapping("/me")
    public EmployeeResponse getCurrentUser(Authentication authentication) {
        return employeeService.getCurrentUser(authentication.getName());
    }
}