package com.intranet.portal.dto.attendance;

import jakarta.validation.constraints.NotBlank;

public record AttendanceCheckInRequest(
        @NotBlank String cardCode
) {
}