package com.revature.rms.auth.exceptions;

public class AuthenticationException extends AppUserException {

    private int status;

    public AuthenticationException() {
        super("Authentication failed!", 401);
    }
    public AuthenticationException(String message) {
        super(message, 401);
    }

}
