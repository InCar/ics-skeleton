package com.incarcloud.skeleton.config;


import com.incarcloud.skeleton.log.LoggerFactory;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Dispatcher配置类
 */
public class Config {
    public final static String DEFAULT_REQUEST_MAPPING_PRE ="/ics";
    public final static String DEFAULT_REQUEST_STATIC_MAPPING_PRE="/static/";
    public final static String DEFAULT_FILE_STATIC_MAPPING_PRE ="/ics/static/";
    public final static String DEFAULT_ENCODING="UTF-8";
    public final static String[] DEFAULT_SCAN_PACKAGES=new String[]{"com.incarcloud"};
    public static Logger GLOBAL_LOGGER;

    //匹配request路径前缀
    private String requestMappingPre;

    //静态资源请求匹配前缀
    private String requestStaticMappingPre;
    //静态资源类路径文件路径匹配前缀
    private String fileStaticMappingPre;
    //额外静态资源类路径文件路径匹配前缀(优先级为 extFileStaticMappingPres>fileStaticMappingPre)
    private String[] extFileStaticMappingPres;

    //response编码
    private String encoding;

    //logger
    private LogConfig logConfig;
    private Logger logger;

    //mysql连接配置
    private DataSource dataSource;
    private JdbcConfig jdbcConfig;

    //扫面ics组件的包路径
    private String[] scanPackages;


    public Config() {
        this.requestMappingPre = DEFAULT_REQUEST_MAPPING_PRE;
        this.encoding = DEFAULT_ENCODING;
        this.requestStaticMappingPre = DEFAULT_REQUEST_STATIC_MAPPING_PRE;
        this.fileStaticMappingPre=DEFAULT_FILE_STATIC_MAPPING_PRE;
        this.logConfig=new LogConfig();
        this.logger= LoggerFactory.getLogger(logConfig);
        GLOBAL_LOGGER =this.logger;
        this.dataSource=DataSource.Other;
        this.scanPackages=DEFAULT_SCAN_PACKAGES;
    }

    public String getRequestMappingPre() {
        return requestMappingPre;
    }

    public Config withRequestMappingPre(String requestMappingPre) {
        this.requestMappingPre = requestMappingPre;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public Config withEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public Logger getLogger() {
        return logger;
    }

    public Config withLogger(Logger logger) {
        this.logger = logger;
        GLOBAL_LOGGER =logger;
        return this;
    }

    public LogConfig getLogConfig() {
        return logConfig;
    }

    public Config withLogConfig(LogConfig logConfig) {
        this.logConfig = logConfig;
        this.logger=LoggerFactory.getLogger(logConfig);
        return this;
    }

    public String getRequestStaticMappingPre() {
        return requestStaticMappingPre;
    }

    public Config withRequestStaticMappingPre(String requestStaticMappingPre) {
        this.requestStaticMappingPre = requestStaticMappingPre;
        return this;
    }

    public String getFileStaticMappingPre() {
        return fileStaticMappingPre;
    }

    public Config withFileStaticMappingPre(String fileStaticMappingPre) {
        this.fileStaticMappingPre = fileStaticMappingPre;
        return this;
    }

    public JdbcConfig getJdbcConfig() {
        return jdbcConfig;
    }

    public Config withJdbcConfig(JdbcConfig jdbcConfig) {
        this.dataSource=DataSource.JDBC;
        this.jdbcConfig = jdbcConfig;
        return this;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Config withDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public String[] getScanPackages() {
        return scanPackages;
    }

    public Config addScanPackages(String... scanPackages) {
        if(scanPackages!=null&&scanPackages.length>0){
            List<String> scanPackageList = Arrays.stream(scanPackages).filter(scanPackage -> scanPackage != null && !"".equals(scanPackage.trim())).distinct().collect(Collectors.toList());
            if (!scanPackageList.isEmpty()) {
                scanPackageList.addAll(0,Arrays.asList(this.scanPackages));
                this.scanPackages=scanPackageList.toArray(new String[scanPackageList.size()]);
            }
        }
        return this;
    }

    public String[] getExtFileStaticMappingPres() {
        return extFileStaticMappingPres;
    }

    public Config withExtFileStaticMappingPres(String... extFileStaticMappingPres) {
        this.extFileStaticMappingPres = extFileStaticMappingPres;
        return this;
    }
}
