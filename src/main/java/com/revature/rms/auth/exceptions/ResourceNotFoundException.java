package com.revature.rms.auth.exceptions;

public class ResourceNotFoundException extends AppUserException {

    private int status;

    public ResourceNotFoundException() {
        super("No resource found with provided search criteria!", 404);
    }

    public ResourceNotFoundException(String message) {
        super(message, 404);
    }

}
