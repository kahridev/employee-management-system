package com.mustafakahriman.employeemanagementsystem.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Aspect
@Log4j2
@RequiredArgsConstructor
public class EmployeeAspect {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest httpServletRequest;

    @Pointcut("execution(* com.mustafakahriman.employeemanagementsystem.controller.*.*(..))")
    public void logController() {
    }

    @Around("logController()")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        String url = httpServletRequest.getRequestURL().toString();

        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().toString();
        Object[] args = proceedingJoinPoint.getArgs();

        stopWatch.start();
        Object proceed = proceedingJoinPoint.proceed();
        stopWatch.stop();

        String request = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(args);
        String response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(proceed);
        long executionTime = stopWatch.getTotalTimeMillis();

        log.info(String.format(
                "{ url: %s, className: %s, methodName: %s, executionTime: %s, requestArgs: %s, response %s }",
                url, className, methodName, executionTime, request, response));
        return proceed;
    }
}