package com.intranet.portal.dto.attendance;

import java.time.LocalDateTime;

public record AttendanceUpdateRequest(
        LocalDateTime actionDate,
        Long employeeId
) {}