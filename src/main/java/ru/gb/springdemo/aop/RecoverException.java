package ru.gb.springdemo.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecoverException {

    Class<? extends RuntimeException>[] noRecoverFor() default {};

}
//@RecoverException(noRecoverFor = {IllegalArgumentException.class, NoSuchElementException.class})
