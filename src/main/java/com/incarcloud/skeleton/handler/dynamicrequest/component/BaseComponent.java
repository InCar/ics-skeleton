package com.incarcloud.skeleton.handler.dynamicrequest.component;

import com.incarcloud.skeleton.config.Config;
import com.incarcloud.skeleton.context.Context;
import com.incarcloud.skeleton.context.Initializable;

public class BaseComponent implements Initializable {
    protected Context context;
    protected Config config;

    @Override
    public void init(Context context) {
        this.context=context;
        this.config=context.getConfig();
    }

    public Context getContext() {
        return context;
    }

    public Config getConfig() {
        return config;
    }
}
