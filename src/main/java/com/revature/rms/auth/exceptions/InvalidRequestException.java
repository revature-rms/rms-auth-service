package com.revature.rms.auth.exceptions;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
        super("Invalid request made!");
    }
    public InvalidRequestException(String message) {
        super(message);
    }

}
