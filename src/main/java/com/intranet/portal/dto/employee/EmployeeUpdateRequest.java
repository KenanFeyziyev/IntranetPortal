package com.intranet.portal.dto.employee;

import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record EmployeeUpdateRequest(
        String firstName,
        String lastName,
        String patronymic,
        LocalDate birthDate,

        @Email String email,
        String phoneNumber,
        String address,

        String password,

        String cardCode,

        Long departmentId,
        Long positionId,

        Boolean isActive
) {
}