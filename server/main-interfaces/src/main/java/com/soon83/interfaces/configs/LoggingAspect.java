package com.soon83.interfaces.configs;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class LoggingAspect {
    @Around("bean(*Controller) || bean(*Facade) || bean(*Service) || bean(*Repository) || bean(*RepositoryJooq) || bean(*RepositoryJooqImpl)")
    public Object mdcLogging(ProceedingJoinPoint pjp) throws Throwable {
        boolean isNewTransaction = false;
        try {
            if (MDC.get("traceId") == null) {
                isNewTransaction = true;
                MDC.put("traceId", UUID.randomUUID().toString().replaceAll("-", ""));
            }

            MethodSignature signature = (MethodSignature) pjp.getSignature();
            String className = pjp.getTarget().getClass().getSimpleName();
            String methodName = signature.getMethod().getName();
            MDC.put("methodName", className + "." + methodName);

            return pjp.proceed();
        } finally {
            MDC.remove("methodName");
            if (isNewTransaction) {
                MDC.remove("traceId");
            }
        }
    }
}
