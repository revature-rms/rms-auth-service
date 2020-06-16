package com.revature.rms.auth.exceptions;

public class BadRequestException extends RuntimeException {

    private int status;

    public BadRequestException() {
        super("An invalid request was made!");
        this.status = 400;
    }

    public BadRequestException(String message) {
        super(message);
        this.status = 400;
    }

}
