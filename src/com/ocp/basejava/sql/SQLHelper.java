package com.ocp.basejava.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {

    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void doConnection(String sql) {
        doConnection(sql, PreparedStatement::execute);
    }

    public <T> T doConnection(String sql, SQLRunner<T> sqlRunner) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return sqlRunner.execute(ps);
        } catch (SQLException e) {
            throw  ExceptionUtil.convertException(e);
        }
    }
}
