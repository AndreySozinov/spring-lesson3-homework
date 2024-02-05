package ru.gb.springdemo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimerAspect {

    @Pointcut("within(ru.gb.springdemo.*)")
    public void classAnnotatedWith() {

    }

    @Pointcut("@annotation(ru.gb.springdemo.aop.Timer)")
    public void methodsAnnotatedWith() {

    }

    @Around("classAnnotatedWith() || methodsAnnotatedWith()")
    public Object timerAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;

            log.info("Class: " + joinPoint.getTarget().getClass().getName() +
                    " Method: " + joinPoint.getSignature().getName() +
                    " Elapsed Time: " + elapsedTime + " ms.");

            return result;
        } catch (Throwable ex) {
            log.error("exception = [{}, {}]", ex.getClass(), ex.getMessage());
            throw ex;
        }
    }
}
