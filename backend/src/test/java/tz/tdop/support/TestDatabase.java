package tz.tdop.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Connection details and lifecycle helpers for the PostgreSQL test database.
 * <p>
 * Values come from the environment only (D-017): {@code TDOP_TEST_DB_URL}, {@code TDOP_TEST_DB_USERNAME} and
 * {@code TDOP_TEST_DB_PASSWORD}. Where those are absent the password falls back to {@code TDOP_DB_PASSWORD}, which is
 * how the same role is configured for local development. No credential is ever written into this repository.
 */
public final class TestDatabase {

    private static final String DEFAULT_URL = "jdbc:postgresql://127.0.0.1:5432/tdop_test";
    private static final String DEFAULT_USERNAME = "tdop_app";

    private TestDatabase() {
    }

    public static String url() {
        return value("TDOP_TEST_DB_URL", DEFAULT_URL);
    }

    public static String username() {
        return value("TDOP_TEST_DB_USERNAME", DEFAULT_USERNAME);
    }

    public static String password() {
        String password = System.getenv("TDOP_TEST_DB_PASSWORD");
        if (password == null || password.isEmpty()) {
            password = System.getenv("TDOP_DB_PASSWORD");
        }
        return password == null ? "" : password;
    }

    /** Drops the public schema so migrations can be applied to a genuinely empty database. */
    public static void resetSchema() throws SQLException {
        try (Connection connection = open();
                Statement statement = connection.createStatement()) {
            statement.execute("drop schema if exists public cascade");
            statement.execute("create schema public");
        }
    }

    /**
     * Empties every application table. {@code flyway_schema_history} is left alone: clearing it would desynchronise
     * Flyway from a schema that still exists.
     * <p>
     * Called before <em>and</em> after tests that write, so no test class can leave rows behind for a later class to
     * trip over.
     */
    public static void truncateApplicationTables() throws SQLException {
        List<String> tables = applicationTables();
        if (tables.isEmpty()) {
            return;
        }
        String statement = "truncate table " + String.join(", ", tables) + " restart identity cascade";
        try (Connection connection = open();
                Statement truncate = connection.createStatement()) {
            truncate.execute(statement);
        }
    }

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(url(), username(), password());
    }

    private static List<String> applicationTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        String query = "select table_name from information_schema.tables" + " where table_schema = 'public'"
                + " and table_type = 'BASE TABLE'" + " and table_name <> 'flyway_schema_history'";
        try (Connection connection = open();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(query)) {
            while (results.next()) {
                tables.add(results.getString(1));
            }
        }
        return tables;
    }

    private static String value(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isEmpty() ? fallback : value;
    }
}
