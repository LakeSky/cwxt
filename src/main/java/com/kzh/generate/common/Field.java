package com.kzh.generate.common;

import javax.persistence.Column;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: kzh
 * Date: 13-10-17
 * Time: 上午9:31
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Field{
    String name() default "";
    String type() default "";
    String action() default "";
}
