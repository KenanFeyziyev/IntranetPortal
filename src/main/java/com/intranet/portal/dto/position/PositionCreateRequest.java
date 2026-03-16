package com.intranet.portal.dto.position;

import jakarta.validation.constraints.NotBlank;

public record PositionCreateRequest(
        @NotBlank String name
) {
}
