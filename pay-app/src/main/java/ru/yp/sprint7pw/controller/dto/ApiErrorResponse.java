package ru.yp.sprint7pw.controller.dto;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public record ApiErrorResponse(HttpStatus status, String message, LocalDateTime timestamp) {
}