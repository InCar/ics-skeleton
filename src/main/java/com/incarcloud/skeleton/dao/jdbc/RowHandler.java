package com.incarcloud.skeleton.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowHandler<R> {
    R apply(ResultSet rs) throws SQLException;
}
