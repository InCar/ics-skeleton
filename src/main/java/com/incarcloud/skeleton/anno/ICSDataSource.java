package com.incarcloud.skeleton.anno;

import com.incarcloud.skeleton.config.DataSource;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ICSDataSource {
    DataSource value();
}
