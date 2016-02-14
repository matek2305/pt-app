package io.github.matek2305.pt.exception;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
