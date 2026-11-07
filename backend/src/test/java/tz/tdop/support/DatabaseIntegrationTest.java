package tz.tdop.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.Locale;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Base class for tests that write to the test database.
 * <p>
 * Tables are emptied before every test, so each test owns its rows, and again after the class finishes, so no test
 * class can leave data behind for a later class to trip over.
 */
@SpringBootTest
public abstract class DatabaseIntegrationTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void emptyApplicationTables() throws SQLException {
        TestDatabase.truncateApplicationTables();
    }

    @AfterAll
    static void leaveTheDatabaseEmpty() throws SQLException {
        TestDatabase.truncateApplicationTables();
    }

    /** Asserts that the database rejected the operation with the given SQLSTATE. */
    protected void assertRejectedWith(String expectedSqlState, String description, Executable operation) {
        Throwable thrown = assertThrows(Throwable.class, operation, () -> description + " should be rejected");
        Throwable cause = thrown;
        while (cause != null && !(cause instanceof SQLException)) {
            cause = cause.getCause();
        }
        assertNotNull(cause, () -> "No SQLException in the cause chain of " + thrown);
        assertEquals(expectedSqlState, ((SQLException) cause).getSQLState(), description);
    }

    protected String uniqueSuffix() {
        return Long.toHexString(System.nanoTime());
    }

    protected UUID newUser(String email) {
        return jdbcTemplate.queryForObject(
                "insert into app_user (email, password_hash, display_name, system_role, status)"
                        + " values (?, 'not-a-real-hash', 'Test User', 'SEEKER', 'ACTIVE') returning id",
                UUID.class, email);
    }

    protected UUID newCategory(String code) {
        return jdbcTemplate.queryForObject(
                "insert into category (code, slug, name_en, name_sw)"
                        + " values (?, ?, 'Test category', 'Kundi la majaribio') returning id",
                UUID.class, code, code.toLowerCase(Locale.ROOT));
    }

    protected UUID newOrganization(UUID ownerUserId, String slug) {
        return jdbcTemplate.queryForObject(
                "insert into organization (owner_user_id, name, slug) values (?, ?, ?) returning id", UUID.class,
                ownerUserId, "Organisation " + slug, slug);
    }

    protected UUID newOpportunity(UUID organizationId, UUID categoryId, UUID createdByUserId, String applicationMode,
            String externalUrl) {
        return jdbcTemplate.queryForObject("insert into opportunity (organization_id, category_id, title, description,"
                + " application_mode, external_url, created_by_user_id)"
                + " values (?, ?, 'Test opportunity', 'Description for integrity testing.'," + " ?, ?, ?) returning id",
                UUID.class, organizationId, categoryId, applicationMode, externalUrl, createdByUserId);
    }

    protected int count(String sql, Object... arguments) {
        Integer value = jdbcTemplate.queryForObject(sql, Integer.class, arguments);
        return value == null ? 0 : value;
    }
}
