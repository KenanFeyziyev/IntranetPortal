package com.intranet.portal.dto.department;

import jakarta.validation.constraints.NotBlank;

public record DepartmentCreateRequest(
        @NotBlank String departmentName
) {
}
