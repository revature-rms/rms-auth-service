package com.revature.rms.auth.exceptions;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("Requester lacks the proper authorities to perform that action!x");
    }
    public AuthorizationException(String message) {
        super(message);
    }

}
