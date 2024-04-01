package com.oozeander.courses_manager_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

public record RaceDto(
        @NotNull(message = "{race.date.notnull}")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @Min(value = 1, message = "{race.number.minimum}")
        @NotNull(message = "{race.number.notnull}")
        Integer number,
        @NotBlank(message = "{race.name.notblank}")
        String name,
        @Valid
        @Size(min = 3, message = "{race.participants.size}")
        Set<ParticipantDto> participants
) {
}
