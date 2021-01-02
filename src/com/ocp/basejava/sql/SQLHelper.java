package com.ocp.basejava.sql;

import com.ocp.basejava.exception.ExistStorageException;
import com.ocp.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {

    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T doConnection(String sql, SQLRunner<T> sqlRunner) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return sqlRunner.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505"))
                throw new ExistStorageException(null);
            else
                throw new StorageException(e);
        }
    }
}
