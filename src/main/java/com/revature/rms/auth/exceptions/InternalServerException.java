package com.revature.rms.auth.exceptions;

public class InternalServerException extends AppUserException{

    private int status;

    public InternalServerException() {
        super("An invalid request was made!", 500);
    }

    public InternalServerException(String message) {
        super(message, 500);
    }

}
