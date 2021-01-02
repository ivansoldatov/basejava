package com.ocp.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SQLRunner<T> {
    T execute(PreparedStatement ps) throws SQLException;
}
