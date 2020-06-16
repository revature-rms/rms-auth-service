package com.revature.rms.auth.exceptions;

public class InvalidRequestException extends AppUserException {

    private int status;

    public InvalidRequestException() {
        super("Invalid request made!", 400);
    }

    public InvalidRequestException(String message) {
        super(message, 400);
    }

}
