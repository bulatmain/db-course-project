package com.bulatmain.conference.infastructure.repository.db;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.ConnectionFactoryCustomizer;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Profile({ "dev", "prod" })
public class CustomJdbcTemplate {
    @FunctionalInterface
    public interface SQLFunction<T, R> {
        R apply(T object) throws SQLException;
    }

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T object) throws SQLException;
    }

    private final DataSource connectionPool;

    private Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection(connectionPool);
    }

    private void releaseConnection(Connection conn) {
        DataSourceUtils.releaseConnection(conn, connectionPool);
    }

    public void connection(SQLConsumer<? super Connection> consumer) throws SQLException {
        Objects.requireNonNull(consumer);
        var conn = getConnection();
        try {
            consumer.accept(conn);
        } catch (SQLException e) {
            // Release Connection early, to avoid potential connection pool deadlock
            // in the case when the exception translator hasn't been initialized yet.
            releaseConnection(conn);
            conn = null;
            throw e;
        } finally {
            releaseConnection(conn);
        }
    }

    public <R> R connection(SQLFunction<? super Connection, ? extends R> function) throws SQLException {
        Objects.requireNonNull(function);
        var conn = getConnection();
        try {
            return function.apply(conn);
        } catch (SQLException e) {
            // Release Connection early, to avoid potential connection pool deadlock
            // in the case when the exception translator hasn't been initialized yet.
            releaseConnection(conn);
            conn = null;
            throw e;
        } finally {
            releaseConnection(conn);
        }
    }

    public <R> R statement(SQLFunction<? super Statement, ? extends R> function) throws SQLException {
        Objects.requireNonNull(function);
        return connection(conn -> {
            try (Statement stmt = conn.createStatement()) {
                return function.apply(stmt);
            }
        });
    }

    public void statement(SQLConsumer<? super Statement> consumer) throws SQLException {
        Objects.requireNonNull(consumer);
        connection(conn -> {
            try (Statement stmt = conn.createStatement()) {
                consumer.accept(stmt);
            }
        });
    }

    public <R> R preparedStatement(String sql, SQLFunction<? super PreparedStatement, ? extends R> function) throws SQLException {
        Objects.requireNonNull(function);
        return connection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                return function.apply(stmt);
            }
        });
    }

    public void preparedStatement(String sql, SQLConsumer<? super PreparedStatement> consumer) throws SQLException {
        Objects.requireNonNull(consumer);
        connection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                consumer.accept(stmt);
            }
        });
    }
}