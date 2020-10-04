package com.revature.rms.auth.util;

import com.revature.rms.auth.dtos.ErrorResponse;
import com.revature.rms.auth.exceptions.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Component
@RestControllerAdvice
public class ControllerAdvisor {

    /**
     * handleException method: Takes in an AppUserException, and a HttpServletResponse as input.
     * @param e ErrorResponse that provides status, message, and timestamp of the exception
     * @param resp HttpServletResponse that provides the response code
     * @return the error response
     */
    @ExceptionHandler
    public ErrorResponse handleException(AppUserException e, HttpServletResponse resp){
        ErrorResponse err = new ErrorResponse(e);

        if(e instanceof AuthenticationException){
            resp.setStatus(401);
        } else if (e instanceof AuthorizationException){
            resp.setStatus(403);
        } else if(e instanceof BadRequestException){
            resp.setStatus(400);
        }else if (e instanceof InternalServerException){
            resp.setStatus(500);
        }else if (e instanceof ResourceNotFoundException){
            resp.setStatus(404);
        }else if (e instanceof ResourcePersistenceException){
            resp.setStatus(409);
        }

        return err;
    }

}
