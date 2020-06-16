package com.revature.rms.auth.exceptions;

public class AuthorizationException extends RuntimeException {

    private int status;

    public AuthorizationException() {
        super("Requester lacks the proper authorities to perform that action!x");
        this.status = 403;
    }

    public AuthorizationException(String message) {
        super(message);
        this.status = 403;
    }
}
