package com.kzh.generate.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: kzh
 * Date: 13-10-11
 * Time: 下午3:17
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Name {
    String value() default "";
}
