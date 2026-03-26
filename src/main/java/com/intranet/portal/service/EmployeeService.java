package com.intranet.portal.service;

import com.intranet.portal.dto.birthday.BirthdayCelebrantResponse;
import com.intranet.portal.dto.employee.EmployeeCreateRequest;
import com.intranet.portal.dto.employee.EmployeeResponse;
import com.intranet.portal.dto.employee.EmployeeUpdateRequest;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {

    List<EmployeeResponse> getEmployees();

    EmployeeResponse getEmployeeById(Long id);

    Long addEmployee(EmployeeCreateRequest request);

    EmployeeResponse updateEmployee(Long id, EmployeeUpdateRequest request);

    void deleteEmployee(Long id);

    BigDecimal calculateNetSalary(BigDecimal grossSalary);

    //List<String> getBirthdayCelebrantsThisMonth();

    List<BirthdayCelebrantResponse> getBirthdayCelebrantsThisMonth();

    EmployeeResponse getCurrentUser(String email);
}