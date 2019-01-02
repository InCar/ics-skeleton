package com.incarcloud.skeleton.exception.handler;

import com.incarcloud.skeleton.request.RequestData;

public interface ExceptionHandler {
    void resolveException(RequestData requestData,Throwable throwable);
}
