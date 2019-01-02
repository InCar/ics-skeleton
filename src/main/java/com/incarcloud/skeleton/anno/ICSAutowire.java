package com.incarcloud.skeleton.anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ICSAutowire {
    String value() default "";
}
