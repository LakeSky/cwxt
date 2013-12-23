package com.kzh.generate.auto;

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
public @interface QField {
    //中文名
    String name() default "";

    //类型（text,textarea,dict,date,time）
    String type() default "text";

    //能力(query,show,edit)多个用逗号隔开
    String actions() default "";

    boolean nullable() default true;

    //------------------------------------------
    //日期类型
    String dateFormat() default "yyyy-MM-dd";

    //时间类型
    String timeFormat() default "yyyy-MM-dd HH:mm:ss";

    //类型可以为static,dynamic如果是dynamic请指定相应取值的sql
    String dictType() default "static";

    String[] dictValues() default {};

    String dictSql() default "";

    //为textarea类型的时候使用的属性
    int textareaLength() default 500;
}
