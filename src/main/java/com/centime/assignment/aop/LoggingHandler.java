package com.centime.assignment.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingHandler {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Before("execution(* com.centime.assignment..*(..)) && @annotation(com.centime.assignment.aop.LogMethodParams)")
    public void logMethodParams(JoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Object[] args = point.getArgs();
        LOG.info("ClassName:{}, MethodName: {}, Method Parameters: {}",
                className, methodName, args);
    }
}
