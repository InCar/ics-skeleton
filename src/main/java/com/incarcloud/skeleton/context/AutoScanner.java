package com.incarcloud.skeleton.context;

import com.incarcloud.skeleton.config.Config;

import java.util.Map;

public interface AutoScanner {
     <T>T getBeanByType(Class clazz);
     <T>T getBeanByName(String name);
     Map<String,Object> getBeanMap();
     void scanComponents(Config config);
}
