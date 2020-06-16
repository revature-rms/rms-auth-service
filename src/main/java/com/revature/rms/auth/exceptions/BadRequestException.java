package com.revature.rms.auth.exceptions;

public class BadRequestException extends AppUserException {

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
