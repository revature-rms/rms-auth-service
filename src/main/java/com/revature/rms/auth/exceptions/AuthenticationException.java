package com.revature.rms.auth.exceptions;

public class AuthenticationException extends RuntimeException {

    private int status;

    public AuthenticationException() {
        super("Authentication failed!");
        this.status = 401;
    }
    public AuthenticationException(String message) {
        super(message);
        this.status = 401;
    }

}
