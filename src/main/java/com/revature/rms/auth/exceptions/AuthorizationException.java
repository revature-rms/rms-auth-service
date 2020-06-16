package com.revature.rms.auth.exceptions;

public class AuthorizationException extends AppUserException {

    private int status;

    public AuthorizationException() {
        super("Requester lacks the proper authorities to perform that action!", 403);
    }

    public AuthorizationException(String message) {
        super(message, 403);
    }
}
