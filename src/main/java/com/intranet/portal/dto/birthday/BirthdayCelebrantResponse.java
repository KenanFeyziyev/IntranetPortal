package com.intranet.portal.dto.birthday;

import java.time.LocalDate;

public record BirthdayCelebrantResponse(
        Long employeeId,
        String fullName,
        LocalDate birthDate,
        int age,
        String ageCategory,
        String message
) {
}