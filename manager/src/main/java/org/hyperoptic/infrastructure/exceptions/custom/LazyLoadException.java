package org.hyperoptic.infrastructure.exceptions.custom;

public class LazyLoadException extends RuntimeException {
    public LazyLoadException() {
    }

    public LazyLoadException(String message) {
        super(message);
    }
}
