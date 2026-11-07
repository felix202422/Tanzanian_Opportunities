package tz.tdop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * Verifies the seed profile: it loads the reference catalogue and clearly-marked development records, and it never
 * writes anything that could act as a credential.
 * <p>
 * This class runs with {@code @ActiveProfiles("seed")}, so it gets its own application context and its own Flyway run.
 * The seed is executed outside Flyway (tz.tdop.seed.DevSeedRunner), so the schema history stays identical whether or
 * not the profile is active.
 * <p>
 * Every assertion is scoped to the seed's fixed UUID prefix rather than to the whole table, so the result does not
 * depend on which test class ran first.
 */
@SpringBootTest
@ActiveProfiles("seed")
class SeedProfileTest {

    /** All seed rows use this UUID prefix, see db/seed/dev_seed.sql. */
    private static final String SEED_ROWS = "00000000-0000-4000-8000-%";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void seedsTheTwelveReferenceCategories() {
        assertEquals(12, count("select count(*) from category where id::text like ?", SEED_ROWS),
                "expected the 12 catalogue entries");
        assertEquals(12, count("select count(distinct lower(code)) from category where id::text like ?", SEED_ROWS),
                "category codes must be unique ignoring case");
        assertEquals(12,
                count("select count(*) from category where id::text like ?"
                        + " and name_en is not null and name_sw is not null", SEED_ROWS),
                "every seeded category must be bilingual");
    }

    @Test
    void seedsVersionedAttributeDefinitionsForConcreteCategories() {
        assertTrue(
                count("select count(*) from category_attribute_definition" + " where id::text like ?", SEED_ROWS) > 0,
                "seed must demonstrate the versioned category attribute schema");
        assertEquals(0,
                count("select count(*) from category_attribute_definition" + " where id::text like ?"
                        + " and (schema_version < 1 or attribute_key is null)", SEED_ROWS),
                "definition rows must carry a version and a key");
        assertEquals(0,
                count("select count(*) from category_attribute_definition"
                        + " where id::text like ? and data_type = 'ENUM'" + " and allowed_values is null", SEED_ROWS),
                "ENUM attributes must declare their allowed values");
    }

    @Test
    void everySeededAttributeHasADefinitionAtItsPinnedVersion() {
        String sql = "select count(*) from opportunity o," + " jsonb_object_keys(o.attributes) as attribute_key_value"
                + " where o.id::text like ? and not exists (" + "   select 1 from category_attribute_definition d"
                + "   where d.category_id = o.category_id" + "     and d.schema_version = o.attributes_schema_version"
                + "     and d.attribute_key = attribute_key_value)";
        assertEquals(0, count(sql, SEED_ROWS),
                "seed opportunity attributes must be covered by their category's schema");
    }

    @Test
    void seedsDevelopmentRecordsThatCannotAuthenticate() {
        assertEquals(3, count("select count(*) from app_user where id::text like ?", SEED_ROWS),
                "expected the development actors");
        assertEquals(3,
                count("select count(*) from app_user where id::text like ?" + " and password_hash like 'PLACEHOLDER%'",
                        SEED_ROWS),
                "seeded password hashes must all be placeholders");
        assertEquals(1, count("select count(*) from organization where id::text like ?", SEED_ROWS),
                "expected one seed organisation");
        assertEquals(3, count("select count(*) from opportunity where id::text like ?", SEED_ROWS),
                "expected three seed opportunities");
        assertEquals(1, count("select count(*) from application where id::text like ?", SEED_ROWS),
                "expected one seed application");
        assertEquals(1, count("select count(*) from platform_setting where setting_key = 'seed_data_marker'"),
                "seeded databases must be identifiable by the seed marker");
    }

    @Test
    void seedDataKeepsTheDocumentedPublicationRules() {
        assertEquals(0,
                count("select count(*) from opportunity where id::text like ?" + " and publication_status = 'PUBLISHED'"
                        + " and moderation_status <> 'APPROVED'", SEED_ROWS),
                "no seed row may be published without approval");
        assertEquals(0,
                count("select count(*) from opportunity where id::text like ?"
                        + " and application_mode = 'EXTERNAL' and external_url is null", SEED_ROWS),
                "external seed opportunities must carry an official destination");
    }

    private int count(String sql, Object... arguments) {
        Integer value = jdbcTemplate.queryForObject(sql, Integer.class, arguments);
        return value == null ? 0 : value;
    }
}
