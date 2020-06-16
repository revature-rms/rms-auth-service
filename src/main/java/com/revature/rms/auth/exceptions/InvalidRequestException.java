package com.revature.rms.auth.exceptions;

public class InvalidRequestException extends RuntimeException {

    private int status;

    public InvalidRequestException() {
        super("Invalid request made!");
        this.status = 400;
    }

    public InvalidRequestException(String message) {
        super(message);
        this.status = 400;
    }

}
