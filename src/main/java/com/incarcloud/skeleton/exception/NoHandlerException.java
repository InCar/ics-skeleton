package com.incarcloud.skeleton.exception;

public class NoHandlerException extends Exception{
    private String uri;

    public String getUri() {
        return uri;
    }
    public NoHandlerException(String uri) {
        super("No Request["+uri +"] Handler");
        this.uri=uri;
    }
}
