package com.oozeander.courses_manager_api.controller;

import com.oozeander.courses_manager_api.dto.ErrorResponse;
import com.oozeander.courses_manager_api.dto.RaceDto;
import com.oozeander.courses_manager_api.service.RaceManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("race-manager")
@RequiredArgsConstructor
@Tag(name = "PMU Technical Test", description = "Race management API")
public class RaceManagerController {

    private final RaceManagerService raceManagerService;

    @Operation(summary = "Save and publish a race")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saved and published race",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RaceDto.class))}),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Void> addRace(
            @RequestBody @Valid RaceDto raceDto
    ) {
        raceManagerService.saveAndPublishRace(raceDto);
        return ResponseEntity.ok().build();
    }
}
