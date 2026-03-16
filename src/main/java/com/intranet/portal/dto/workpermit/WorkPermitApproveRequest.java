package com.intranet.portal.dto.workpermit;

import jakarta.validation.constraints.NotBlank;

public record WorkPermitApproveRequest(
        @NotBlank String approvedBy
) {
}