package com.incarcloud.skeleton.json;

import com.incarcloud.skeleton.anno.ICSComponent;
import com.incarcloud.skeleton.anno.ICSConditionalOnMissingBean;

@ICSComponent
@ICSConditionalOnMissingBean(JsonReader.class)
public class DefaultJsonReader implements JsonReader{
    @Override
    public String toJson(Object obj) {
        if(obj==null){
            return "";
        }
        return obj.toString();
    }

}
