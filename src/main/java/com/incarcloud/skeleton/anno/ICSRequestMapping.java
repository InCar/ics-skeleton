package com.incarcloud.skeleton.anno;




import com.incarcloud.skeleton.handler.dynamicrequest.define.ICSHttpRequestMethodEnum;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ICSRequestMapping {
    String value();
    ICSHttpRequestMethodEnum method() default ICSHttpRequestMethodEnum.GET;
}
