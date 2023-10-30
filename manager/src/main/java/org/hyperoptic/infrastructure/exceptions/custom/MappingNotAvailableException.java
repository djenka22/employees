package org.hyperoptic.infrastructure.exceptions.custom;

public class MappingNotAvailableException extends RuntimeException {
    public MappingNotAvailableException() {
    }

    public MappingNotAvailableException(String message) {
        super(message);
    }
}
