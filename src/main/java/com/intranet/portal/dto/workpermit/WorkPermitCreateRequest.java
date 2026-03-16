package com.intranet.portal.dto.workpermit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record WorkPermitCreateRequest(
        @NotBlank String reason,
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        @NotNull Long employeeId
) {
}