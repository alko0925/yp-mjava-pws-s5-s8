package ru.yp.sprint7pw.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.controller.dto.ErrorResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleApiException(ApiException e) {
        return Mono.just(new ResponseEntity<>(
                new ErrorResponse().status(e.getStatus().toString())
                        .message(e.getMessage())
                        .timestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC)), e.getStatus()));
    }
}
