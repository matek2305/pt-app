package io.github.matek2305.pt.api;

import io.github.matek2305.pt.exception.ResourceNotFoundException;
import io.github.matek2305.pt.exception.ValidationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public abstract class BaseExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationFailedException.class)
    public String validationFailedExceptionHandler(ValidationFailedException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        return ex.getMessage();
    }
}
