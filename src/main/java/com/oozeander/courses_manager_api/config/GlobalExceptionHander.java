package com.oozeander.courses_manager_api.config;

import com.oozeander.courses_manager_api.dto.ErrorResponse;
import com.oozeander.courses_manager_api.exception.ParticipantsNumberNotLinearException;
import com.oozeander.courses_manager_api.exception.RaceNameNotUniqueException;
import com.oozeander.courses_manager_api.exception.RaceNumberNotUniqueException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHander extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getBindingResult().getFieldErrors().stream().map(
                                FieldError::getDefaultMessage
                        ).toList(),
                        request.getDescription(false)
                )
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        List.of("Invalid date format provided"),
                        request.getDescription(false)
                )
        );
    }

    @ExceptionHandler(RaceNameNotUniqueException.class)
    public ResponseEntity<ErrorResponse> handleCourseNameNotUniqueException(
            RaceNameNotUniqueException ex, WebRequest request
    ) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        List.of(ex.getLocalizedMessage()),
                        request.getDescription(false)
                )
        );
    }

    @ExceptionHandler(RaceNumberNotUniqueException.class)
    public ResponseEntity<ErrorResponse> handleCourseNumberNotUniqueException(
            RaceNumberNotUniqueException ex, WebRequest request
    ) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        List.of(ex.getLocalizedMessage()),
                        request.getDescription(false)
                )
        );
    }

    @ExceptionHandler(ParticipantsNumberNotLinearException.class)
    public ResponseEntity<ErrorResponse> handlePartantsNumberNotLinearException(
            ParticipantsNumberNotLinearException ex, WebRequest request
    ) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        List.of(ex.getLocalizedMessage()),
                        request.getDescription(false)
                )
        );
    }
}
