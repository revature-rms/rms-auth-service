package com.revature.rms.auth.exceptions;

public class InternalServerException extends RuntimeException{

    private int status;

    public InternalServerException() {
        super("An invalid request was made!");
        this.status = 500;
    }

    public InternalServerException(String message) {
        super(message);
        this.status = 500;
    }

}
