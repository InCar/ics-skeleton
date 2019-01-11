package com.incarcloud.skeleton;

import com.incarcloud.skeleton.config.Config;
import com.incarcloud.skeleton.config.JdbcConfig;
import com.incarcloud.skeleton.context.Context;
import com.incarcloud.skeleton.context.DefaultContext;
import com.incarcloud.skeleton.dao.jdbc.JdbcDataAccess;

public class Starter {
    public static Context getContext(){
        JdbcConfig mysqlConfig=new JdbcConfig(
                JdbcDataAccess.MYSQL_DRIVER_CLASS_NAME_8,
                "jdbc:mysql://47.98.211.203:3306/test?characterEncoding=utf8&useSSL=false&rewriteBatchedStatements=true&serverTimezone=CTT&autoReconnect=true",
                "root",
                "maptracking");
        Config config=new Config().withJdbcConfig(mysqlConfig);
        DefaultContext context=new DefaultContext(config);
        return context;
    }

    public static void main(String [] args){
        Context context=getContext();
        context.getConfig().getLogger().severe("test");
    }
}
