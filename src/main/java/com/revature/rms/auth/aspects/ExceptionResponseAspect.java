package com.revature.rms.auth.aspects;

import com.revature.rms.core.aspects.CoreExceptionResponseAspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * class extends from CoreExceptionResponseAspect in rms-core dependency, all custom exceptions are handled in the core aspect class
 * this class allows spring to initialize it when it boots up
 */
@Component
@RestControllerAdvice
public class ExceptionResponseAspect extends CoreExceptionResponseAspect {


}
