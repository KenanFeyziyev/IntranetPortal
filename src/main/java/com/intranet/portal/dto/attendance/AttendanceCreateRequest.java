package com.intranet.portal.dto.attendance;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AttendanceCreateRequest(
        @NotNull LocalDateTime actionDate,
        @NotNull Long employeeId
) {
}