package com.revature.rms.auth.aspects;

import com.revature.rms.core.aspects.CoreExceptionResponseAspect;
import com.revature.rms.core.exceptions.AuthenticationException;
import dto.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class ExceptionResponseAspect extends CoreExceptionResponseAspect {

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse handleConstraintViolationException(ConstraintViolationException cve){
//        return new ErrorResponse(409, cve.getMessage());
//    }

}
