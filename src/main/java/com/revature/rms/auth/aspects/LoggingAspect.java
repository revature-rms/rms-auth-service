package com.revature.rms.auth.aspects;

import com.revature.rms.core.aspects.CoreLoggingAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * this class extends the rms-core CoreLoggingAspect so spring can initialize it
 * this class logs activity to the server
 */
@Aspect
@Component
public class LoggingAspect extends CoreLoggingAspect {

    @Override
    @Pointcut("within(com.revature..*)")
    public void logAll() {}
}
