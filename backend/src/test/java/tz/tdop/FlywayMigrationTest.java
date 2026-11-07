package tz.tdop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tz.tdop.support.TestDatabase;

/**
 * Verifies the Phase 2 acceptance criteria that do not need a Spring context: migrations apply from an empty database,
 * re-applying them is a no-op, and a forward replay reproduces an identical schema.
 * <p>
 * See docs/DATA_MODEL.md section 6 for the rollback/forward policy these tests back up.
 */
class FlywayMigrationTest {

    private static final List<String> EXPECTED_SCRIPTS = List.of("V1__identity_categories_and_organizations.sql",
            "V2__opportunities.sql", "V3__applications_and_engagement.sql", "V4__governance.sql");

    /** Every application table the baseline must create, in sorted order. */
    private static final List<String> EXPECTED_TABLES = List.of("app_user", "application", "application_status_history",
            "audit_log", "category", "category_attribute_definition", "notification", "notification_preference",
            "opportunity", "opportunity_status_history", "organization", "organization_member",
            "organization_verification_request", "platform_setting", "report", "saved_opportunity", "seeker_education",
            "seeker_experience", "seeker_interest", "seeker_profile", "seeker_skill");

    private Flyway flyway;

    @BeforeEach
    void startFromAnEmptySchema() throws SQLException {
        flyway = newFlyway();
        TestDatabase.resetSchema();
    }

    @Test
    void migrationsApplyFromAnEmptyDatabase() throws Exception {
        flyway.migrate();

        assertEquals(EXPECTED_SCRIPTS, historyScripts(), "unexpected migration scripts or order");
        assertTrue(historyAllSucceeded(), "every applied migration must report success");
        assertEquals(EXPECTED_TABLES, applicationTables(), "the migrated schema must match the Phase 2 baseline");

        flyway.validate();
    }

    @Test
    void reapplyingMigrationsIsANoOp() throws Exception {
        flyway.migrate();
        int afterFirst = historyScripts().size();

        flyway.migrate();

        assertEquals(afterFirst, historyScripts().size(), "a second migrate must not re-apply anything");
        flyway.validate();
    }

    @Test
    void forwardReplayFromAnEmptySchemaProducesAnIdenticalSchema() throws Exception {
        flyway.migrate();
        List<String> first = schemaSnapshot();

        TestDatabase.resetSchema();
        flyway.migrate();
        List<String> second = schemaSnapshot();

        assertEquals(first, second, "replaying the migrations from an empty schema must reproduce it exactly");
    }

    private Flyway newFlyway() {
        return Flyway.configure().dataSource(TestDatabase.url(), TestDatabase.username(), TestDatabase.password())
                .locations("classpath:db/migration").load();
    }

    private List<String> historyScripts() throws Exception {
        return queryColumn("select script from flyway_schema_history order by installed_rank");
    }

    private boolean historyAllSucceeded() throws Exception {
        try (Connection connection = TestDatabase.open();
                Statement statement = connection.createStatement();
                ResultSet results = statement
                        .executeQuery("select count(*) from flyway_schema_history where success = false")) {
            return results.next() && results.getInt(1) == 0;
        }
    }

    private List<String> applicationTables() throws Exception {
        List<String> tables = queryColumn(
                "select table_name from information_schema.tables" + " where table_schema = 'public'"
                        + " and table_type = 'BASE TABLE'" + " and table_name <> 'flyway_schema_history'");
        tables.sort(String::compareTo);
        return tables;
    }

    /** Captures tables, columns, defaults, constraints and indexes as one comparable list. */
    private List<String> schemaSnapshot() throws Exception {
        String sql = "select 'TABLE ' || table_name from information_schema.tables"
                + " where table_schema = 'public' and table_type = 'BASE TABLE'" + " union all"
                + " select 'COLUMN ' || table_name || '.' || column_name || ' ' || data_type"
                + " || ' ' || coalesce(character_maximum_length::text, '')"
                + " || ' ' || is_nullable || ' ' || coalesce(column_default, '')"
                + " from information_schema.columns where table_schema = 'public'" + " union all"
                + " select 'CONSTRAINT ' || conrelid::regclass::text || ' ' || conname"
                + " || ' ' || pg_get_constraintdef(oid)"
                + " from pg_constraint where connamespace = 'public'::regnamespace" + " union all"
                + " select 'INDEX ' || indexname || ' ' || indexdef" + " from pg_indexes where schemaname = 'public'"
                + " order by 1";
        return queryColumn(sql);
    }

    private List<String> queryColumn(String sql) throws Exception {
        List<String> values = new ArrayList<>();
        try (Connection connection = TestDatabase.open();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(sql)) {
            while (results.next()) {
                values.add(results.getString(1));
            }
        }
        return values;
    }
}
