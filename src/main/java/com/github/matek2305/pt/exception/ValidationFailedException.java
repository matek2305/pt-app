package com.github.matek2305.pt.exception;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class ValidationFailedException extends RuntimeException {

    public ValidationFailedException(String message) {
        super(message);
    }
}
