package com.incarcloud.skeleton.dao.hbase;

import com.incarcloud.skeleton.anno.ICSComponent;
import com.incarcloud.skeleton.anno.ICSConditionalOnMissingBean;
import com.incarcloud.skeleton.anno.ICSDataSource;
import com.incarcloud.skeleton.config.DataSource;
import com.incarcloud.skeleton.config.HBaseConfig;
import com.incarcloud.skeleton.context.Context;
import com.incarcloud.skeleton.context.Initializable;
import com.incarcloud.skeleton.dao.DataAccess;

import java.util.function.Function;

@ICSComponent
@ICSDataSource(DataSource.HBase)
@ICSConditionalOnMissingBean(DataAccess.class)
public class HBaseDataAccess implements DataAccess<Object>,Initializable{
    @Override
    public void init(Context context) {
        HBaseConfig hBaseConfig= context.getConfig().getHBaseConfig();
    }

    @Override
    public <R> R doInConnection(Function<Object, R> function) {
        return null;
    }
}
