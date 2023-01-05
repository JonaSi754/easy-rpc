package com.sijinghua.easyrpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Definite a annotation:
 * Inject remote service
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceReference {
}
