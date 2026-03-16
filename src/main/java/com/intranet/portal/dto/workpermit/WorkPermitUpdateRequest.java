package com.intranet.portal.dto.workpermit;

import java.time.LocalDateTime;

public record WorkPermitUpdateRequest(
        String reason,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long employeeId
) {
}