package com.intranet.portal.dto.attendance;

import java.time.LocalDateTime;

public record AttendanceResponse(
        Long id,
        LocalDateTime actionDate,

        Long employeeId,
        String employeeFirstName,
        String employeeLastName

) {
}