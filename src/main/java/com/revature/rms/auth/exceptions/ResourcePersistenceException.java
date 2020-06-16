package com.revature.rms.auth.exceptions;

public class ResourcePersistenceException extends RuntimeException {

    public ResourcePersistenceException() {
        super("Resource could not be persisted!");
    }
    public ResourcePersistenceException(String message) {
        super(message);
    }
    public ResourcePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
