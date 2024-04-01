package com.oozeander.courses_manager_api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        List<String> errors,
        String path
) {
}
