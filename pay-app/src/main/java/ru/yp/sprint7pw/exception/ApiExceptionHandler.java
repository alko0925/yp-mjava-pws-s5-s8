package ru.yp.sprint7pw.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.controller.dto.ApiErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleApiException(ApiException e) {
        return Mono.just(new ResponseEntity<>(new ApiErrorResponse(e.getStatus(), e.getMessage(), LocalDateTime.now()), e.getStatus())
        );
    }
}
