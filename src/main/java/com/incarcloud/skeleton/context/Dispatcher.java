package com.incarcloud.skeleton.context;

import com.incarcloud.skeleton.request.RequestData;

public interface Dispatcher {
    void dispatch(RequestData requestData);
}
