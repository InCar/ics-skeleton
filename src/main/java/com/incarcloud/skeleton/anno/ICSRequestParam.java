package com.incarcloud.skeleton.anno;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ICSRequestParam {
    String value();
    boolean required() default true;
    String defaultValue() default "";
}
