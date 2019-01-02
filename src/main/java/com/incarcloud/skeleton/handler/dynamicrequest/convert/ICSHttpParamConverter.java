package com.incarcloud.skeleton.handler.dynamicrequest.convert;

public interface ICSHttpParamConverter<T> {
    T convert(String[] source, Class targetType);
}
