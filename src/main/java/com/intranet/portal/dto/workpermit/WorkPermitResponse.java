package com.intranet.portal.dto.workpermit;

import java.time.LocalDateTime;

public record WorkPermitResponse(
        Long id,
        String reason,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean isApproved,
        LocalDateTime approvedDate,
        String approvedBy,
        Boolean isDeleted,
        LocalDateTime permitDate,
        Integer permitDurationInMinutes,

        Long employeeId,
        String employeeFirstName,
        String employeeLastName

) {
}