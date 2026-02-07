package ru.yp.sprint7pw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {
    // Обработка 404
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<Rendering> handleNoSuchElementException(NoSuchElementException e, Model model) {
        return Mono.just(
                Rendering.view("error")
                        .modelAttribute("errReason", e.getMessage())
                        .build()
        );
    }

    // Обработка 400
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Rendering> handleIllegalArgumentException(RuntimeException e, Model model) {
        return Mono.just(
                Rendering.view("error")
                        .modelAttribute("errReason", e.getMessage())
                        .build()
        );
    }
}