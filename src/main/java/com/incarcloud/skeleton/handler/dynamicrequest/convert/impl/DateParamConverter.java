package com.incarcloud.skeleton.handler.dynamicrequest.convert.impl;

import com.incarcloud.skeleton.exception.BaseRuntimeException;
import com.incarcloud.skeleton.handler.dynamicrequest.convert.ICSHttpParamConverter;

import java.util.Date;

public class DateParamConverter implements ICSHttpParamConverter<Date>{
    public final static DateParamConverter INSTANCE=new DateParamConverter();
    @Override
    public Date convert(String[] source, Class targetType) {
        if(source==null||source.length==0){
            return null;
        }else{
            try {
                long mill = Long.parseLong(source[0]);
                return new Date(mill);
            }catch (NumberFormatException e){
                throw BaseRuntimeException.getException(e);
            }
        }
    }
}
