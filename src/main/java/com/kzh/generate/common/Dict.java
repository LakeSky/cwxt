package com.kzh.generate.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: kzh
 * Date: 13-10-17
 * Time: 上午11:20
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Dict {
    //类型可以为static,dynamic如果是dynamic请指定相应取值的sql
    String type() default "static";

    String[] values() default {};

    String sql() default "";
}
