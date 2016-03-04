package io.github.matek2305.pt.api;

import io.github.matek2305.pt.api.response.ValidationFailedResponse;
import io.github.matek2305.pt.exception.ForbiddenResourceException;
import io.github.matek2305.pt.exception.ResourceNotFoundException;
import io.github.matek2305.pt.exception.ValidationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public abstract class BaseExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationFailedException.class)
    public ValidationFailedResponse validationFailedExceptionHandler(ValidationFailedException ex) {
        return new ValidationFailedResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationFailedResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
        List<String> errorMessageList = fieldErrorList.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ValidationFailedResponse(errorMessageList);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenResourceException.class)
    public String forbiddenResourceExceptionHandler(ForbiddenResourceException ex) {
        return ex.getMessage();
    }
}
