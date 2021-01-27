package org.cgi.assesment.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ExceptionHelper {

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
