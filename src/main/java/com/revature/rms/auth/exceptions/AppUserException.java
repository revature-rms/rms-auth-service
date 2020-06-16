package com.revature.rms.auth.exceptions;

public class AppUserException extends RuntimeException{

    private int status;

    public AppUserException() {
        super("Authentication failed!");
        this.status = 520;
    }
    public AppUserException(String message) {
        super(message);
        this.status = 520;
    }

    public AppUserException(String message, Throwable cause){

        super(message, cause);
        this.status = 520;

    }

    public int getStatus() {
        return status;
    }
}
