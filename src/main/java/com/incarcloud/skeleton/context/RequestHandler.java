package com.incarcloud.skeleton.context;

import com.incarcloud.skeleton.request.RequestData;

public interface RequestHandler {
    void handleRequest(RequestData requestData);
}
