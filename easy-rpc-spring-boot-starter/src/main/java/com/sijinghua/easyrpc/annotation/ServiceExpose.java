package com.sijinghua.easyrpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Definite a annotation:
 * 1. Mark the service provider
 * 2. Expose the interface of service
 */
@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceExpose {
    String value() default "";
}
