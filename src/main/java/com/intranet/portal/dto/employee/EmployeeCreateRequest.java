package com.intranet.portal.dto.employee;

import com.intranet.portal.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmployeeCreateRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        String patronymic,
        LocalDate birthDate,

        @Email String email,
        String phoneNumber,
        String address,

        @NotBlank String password,

        @NotBlank String cardCode,

        @NotNull Long departmentId,
        @NotNull Long positionId,

        @NotNull Role role,

        Boolean isActive
) {
}