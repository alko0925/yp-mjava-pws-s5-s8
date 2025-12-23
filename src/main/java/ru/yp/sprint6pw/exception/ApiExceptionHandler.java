package ru.yp.sprint6pw.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {
    // Обработка 404
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
        model.addAttribute("errReason", e.getMessage());
        return "error";
    }

    // Обработка 400
    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(RuntimeException e, Model model) {
        model.addAttribute("errReason", e.getMessage());
        return "error";
    }
}
