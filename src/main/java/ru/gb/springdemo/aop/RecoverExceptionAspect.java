package ru.gb.springdemo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Slf4j
//@Aspect
//@Component
//public class RecoverExceptionAspect {
////    @Pointcut("within(ru.gb.springdemo.*)")
////    public void classAnnotatedWith() {
////
////    }
//
//    @Pointcut("@annotation(ru.gb.springdemo.aop.RecoverException)")
//    public void methodsAnnotatedWith() {
//
//    }
//
//    @AfterThrowing(value = "methodsAnnotatedWith()", throwing = "exception")
//    public Object recover(Exception exception) throws Throwable {
//        if (exception instanceof (RecoverException.class.getField)) {
//            throw exception;
//        } else {
//            return null;
//        }
//
//    }
//}
// RuntimeException.class.isAssignableFrom(IllegalArgumentException.class)