package com.incarcloud.skeleton.anno;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ICSComponent
public @interface ICSController {
    String value() default "";
}
