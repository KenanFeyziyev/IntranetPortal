package com.intranet.portal.dto;

public record DashboardSummaryResponse(
        long employeeCount,
        long departmentCount,
        long todayLateCount,
        long pendingWorkPermitCount
) {
}
