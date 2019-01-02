package com.incarcloud.skeleton.handler.dynamicrequest.request;

import com.incarcloud.skeleton.request.RequestData;

public interface DynamicRequestHandler {
    Object handle(RequestData requestData) throws Throwable;
}
