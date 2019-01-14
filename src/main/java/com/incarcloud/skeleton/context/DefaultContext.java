package com.incarcloud.skeleton.context;

import com.incarcloud.skeleton.config.Config;
import com.incarcloud.skeleton.exception.BaseRuntimeException;
import com.incarcloud.skeleton.exception.NoHandlerException;
import com.incarcloud.skeleton.request.RequestData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@SuppressWarnings("unchecked")
public class DefaultContext implements Context,AutoScanner{
    private Config config;
    private RequestHandler requestHandler;
    private ResourceHandler resourceHandler;
    private Dispatcher dispatcher;
    private AutoScanner autoScanner;
    private boolean isInit=false;




    public DefaultContext(Config config) {
        this.config = config;
        this.autoScanner=new DefaultAutoScanner();
    }

    public void init() {
        if(isInit){
            throw BaseRuntimeException.getException("Context["+this.getClass()+"] Already Init");
        }
        isInit=true;
        if(config==null){
            throw BaseRuntimeException.getException("Param[config] Must Not Be Null");
        }
        //1、根据配置扫描所有组件
        scanComponents(config);
        //2、获取请求分发器
        dispatcher=getBeanByName("dispatcher");
        //3、获取静态请求处理器
        resourceHandler=getBeanByName("resourceHandler");
        //4、获取动态请求处理器
        requestHandler=getBeanByName("requestHandler");
        //5、初始化所有组件
        initComponents();
    }


    /**
     * 遍历所有bean,如果实现了Initializable接口,则进行初始化
     */
    protected void initComponents(){
        //1、如果实现了Initializable接口,则调用初始化方法
        for (Object e : getBeanMap().values()) {
            if(e instanceof Initializable){
                ((Initializable)e).init(this);
            }
        }
    }

    @Override
    public <T> T getBeanByType(Class clazz) {
        return autoScanner.getBeanByType(clazz);
    }

    @Override
    public <T> T getBeanByName(String name) {
        return autoScanner.getBeanByName(name);
    }

    @Override
    public Map<String, Object> getBeanMap() {
        return autoScanner.getBeanMap();
    }

    @Override
    public void scanComponents(Config config) {
        autoScanner.scanComponents(config);
    }

    public DefaultContext withConfig(Config config) {
        if(isInit){
           throw BaseRuntimeException.getException("Context Has Init,Can't Modify Config");
        }
        this.config=config;
        return this;
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public void handleRequest(RequestData requestData) {
        requestHandler.handleRequest(requestData);
    }

    @Override
    public void handleResource(RequestData requestData) {
        resourceHandler.handleResource(requestData);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws NoHandlerException{
        RequestData requestData=new RequestData(request,response,this);
        if(requestData.getSubPath()==null){
            throw new NoHandlerException(request.getRequestURI());
        }
        dispatch(requestData);
    }

    @Override
    public void dispatch(RequestData requestData) {
        dispatcher.dispatch(requestData);
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public DefaultContext withRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        if(requestHandler instanceof Initializable){
            ((Initializable) requestHandler).init(this);
        }
        return this;
    }

    public ResourceHandler getResourceHandler() {
        return resourceHandler;
    }

    public DefaultContext withResourceHandler(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
        if(resourceHandler instanceof Initializable){
            ((Initializable) resourceHandler).init(this);
        }
        return this;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public DefaultContext withDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        if(dispatcher instanceof Initializable){
            ((Initializable) dispatcher).init(this);
        }
        return this;
    }

    public AutoScanner getAutoScanner() {
        return autoScanner;
    }

    public DefaultContext withAutoScanner(AutoScanner autoScanner) {
        this.autoScanner = autoScanner;
        return this;
    }
}

