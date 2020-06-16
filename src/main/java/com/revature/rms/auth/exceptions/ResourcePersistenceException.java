package com.revature.rms.auth.exceptions;

public class ResourcePersistenceException extends AppUserException {

    private int status;

    public ResourcePersistenceException() {
        super("Resource could not be persisted!");
        this.status = 409;
    }

    public ResourcePersistenceException(String message) {
        super(message);
        this.status = 409;
    }

    public ResourcePersistenceException(String message, Throwable cause) {
        super(message, cause);
        this.status = 409;
    }

}
