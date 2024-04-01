package com.oozeander.courses_manager_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParticipantDto(
        @Min(value = 1, message = "{participants.number.minimum}")
        @NotNull(message = "{participants.number.notnull}")
        Integer number,
        @NotBlank(message = "{participants.name.notblank}")
        String name
) {
}
