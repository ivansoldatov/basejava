package com.ocp.basejava.sql;

import com.ocp.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {

    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T doConnection(String sql, SQLScript<T> sqlScript) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return sqlScript.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
