package com.revature.rms.auth.exceptions;

public class AppUserException extends RuntimeException{

    private int status;

    public AppUserException(int status) {
        super("Authentication failed!");
        this.status = status;
    }
    public AppUserException(String message, int status) {
        super(message);
        this.status = status;
    }

    public AppUserException(String message, Throwable cause, int status){

        super(message, cause);
        this.status = status;

    }

    public int getStatus() {
        return status;
    }
}
