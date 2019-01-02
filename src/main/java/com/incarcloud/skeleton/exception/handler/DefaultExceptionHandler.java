package com.incarcloud.skeleton.exception.handler;

import com.incarcloud.skeleton.anno.ICSAutowire;
import com.incarcloud.skeleton.anno.ICSComponent;
import com.incarcloud.skeleton.anno.ICSConditionalOnMissingBean;
import com.incarcloud.skeleton.config.Config;
import com.incarcloud.skeleton.json.JsonReader;
import com.incarcloud.skeleton.message.JsonMessage;
import com.incarcloud.skeleton.request.RequestData;
import com.incarcloud.skeleton.util.ExceptionUtil;

import java.io.IOException;

@ICSComponent
@ICSConditionalOnMissingBean(ExceptionHandler.class)
public class DefaultExceptionHandler implements ExceptionHandler{
    @ICSAutowire
    JsonReader jsonReader;

    @Override
    public void resolveException(RequestData requestData, Throwable throwable) {
        Config.GLOBAL_LOGGER.severe(ExceptionUtil.getStackTraceMessage(throwable));
        requestData.getResponse().setCharacterEncoding(requestData.getContext().getConfig().getEncoding());
        try {
            if(!requestData.getResponse().isCommitted()){
                JsonMessage result= ExceptionUtil.toJsonMessage(throwable);
                String msg=jsonReader.toJson(result);
                requestData.getResponse().setContentType("application/json");
                requestData.getResponse().getWriter().write(msg);
            }
        } catch (IOException e) {
            requestData.getResponse().setStatus(500);
            Config.GLOBAL_LOGGER.severe(ExceptionUtil.getStackTraceMessage(e));
        }
    }
}
