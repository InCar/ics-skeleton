package com.incarcloud.skeleton.handler.dynamicrequest.convert.impl;

import com.incarcloud.skeleton.exception.BaseRuntimeException;
import com.incarcloud.skeleton.handler.dynamicrequest.convert.ICSHttpParamConverter;

public class NumberParamConverter implements ICSHttpParamConverter<Number>{
    public final static NumberParamConverter INSTANCE=new NumberParamConverter();
    @Override
    public Number convert(String[] source, Class targetType) {
        if(source==null||source.length==0){
            return null;
        }else{
            try {
                if (Long.class.isAssignableFrom(targetType)) {
                    return Long.parseLong(source[0]);
                } else if (Integer.class.isAssignableFrom(targetType)) {
                    return Integer.parseInt(source[0]);
                } else if (Short.class.isAssignableFrom(targetType)) {
                    return Short.parseShort(source[0]);
                } else if (Byte.class.isAssignableFrom(targetType)) {
                    return Byte.parseByte(source[0]);
                } else {
                    throw BaseRuntimeException.getException("NumberParamConverter Type[" + targetType.getName() + "] Value[" + source[0] + "] Not Support");
                }
            }catch (NumberFormatException e){
                throw BaseRuntimeException.getException(e);
            }
        }
    }
}
