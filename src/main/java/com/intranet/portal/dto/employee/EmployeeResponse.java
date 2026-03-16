package com.intranet.portal.dto.employee;

import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String patronymic,
        LocalDate birthDate,
        String email,
        String phoneNumber,
        String address,
        Boolean isActive,

        String cardCode,

        Long departmentId,
        String departmentName,

        Long positionId,
        String positionName
) {
}