package tz.tdop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import tz.tdop.support.DatabaseIntegrationTest;

/**
 * Proves that the constraints in the Phase 2 schema are actually enforced by PostgreSQL, and that a normal
 * write/read/update round trip survives.
 * <p>
 * Business rules that need cross-row or service knowledge are listed in docs/DATA_MODEL.md section 7 and are
 * intentionally absent here.
 */
class SchemaIntegrityTest extends DatabaseIntegrationTest {

    @Test
    void rejectsOpportunityPointingAtAnUnknownOrganisation() {
        String suffix = uniqueSuffix();
        UUID category = newCategory("FKCAT" + suffix);
        UUID user = newUser("fk." + suffix + "@example.org");
        UUID unknownOrganisation = UUID.randomUUID();

        assertRejectedWith("23503", "opportunity with an unknown organisation",
                () -> newOpportunity(unknownOrganisation, category, user, "INTERNAL", null));
    }

    @Test
    void rejectsDuplicateEmailIgnoringCase() {
        String suffix = uniqueSuffix();
        newUser("Case.Ignore." + suffix + "@example.org");

        assertRejectedWith("23505", "same email written in a different case",
                () -> newUser("case.ignore." + suffix + "@example.org"));
    }

    @Test
    void rejectsDuplicateCategoryCode() {
        String suffix = uniqueSuffix();
        newCategory("DUPCODE" + suffix);

        assertRejectedWith("23505", "duplicate category code", () -> newCategory("DUPCODE" + suffix));
    }

    @Test
    void rejectsSecondApplicationFromTheSameSeeker() {
        String suffix = uniqueSuffix();
        UUID user = newUser("applicant." + suffix + "@example.org");
        UUID category = newCategory("APPCAT" + suffix);
        UUID organisation = newOrganization(user, "app-org-" + suffix);
        UUID opportunity = newOpportunity(organisation, category, user, "INTERNAL", null);
        String insert = "insert into application (opportunity_id, seeker_user_id, status)"
                + " values (?, ?, 'SUBMITTED')";
        jdbcTemplate.update(insert, opportunity, user);

        assertRejectedWith("23505", "second application for the same opportunity",
                () -> jdbcTemplate.update(insert, opportunity, user));
    }

    @Test
    void rejectsDuplicateSavedOpportunity() {
        String suffix = uniqueSuffix();
        UUID user = newUser("saver." + suffix + "@example.org");
        UUID category = newCategory("SAVCAT" + suffix);
        UUID organisation = newOrganization(user, "save-org-" + suffix);
        UUID opportunity = newOpportunity(organisation, category, user, "INTERNAL", null);
        String insert = "insert into saved_opportunity (user_id, opportunity_id) values (?, ?)";
        jdbcTemplate.update(insert, user, opportunity);

        assertRejectedWith("23505", "saving the same opportunity twice",
                () -> jdbcTemplate.update(insert, user, opportunity));
    }

    @Test
    void rejectsClosingDateBeforeOpeningDate() {
        String suffix = uniqueSuffix();
        UUID user = newUser("dates." + suffix + "@example.org");
        UUID category = newCategory("DATECAT" + suffix);
        UUID organisation = newOrganization(user, "date-org-" + suffix);

        assertRejectedWith("23514", "closing date earlier than the opening date",
                () -> jdbcTemplate.update("insert into opportunity (organization_id, category_id, title,"
                        + " description, application_mode, opens_at, closes_at,"
                        + " created_by_user_id) values (?, ?, 'Bad dates', 'x',"
                        + " 'INTERNAL', now(), now() - interval '1 day', ?)", organisation, category, user));
    }

    @Test
    void rejectsExternalOpportunityWithoutAnOfficialDestination() {
        String suffix = uniqueSuffix();
        UUID user = newUser("external." + suffix + "@example.org");
        UUID category = newCategory("EXTCAT" + suffix);
        UUID organisation = newOrganization(user, "ext-org-" + suffix);

        assertRejectedWith("23514", "external application mode without external_url",
                () -> newOpportunity(organisation, category, user, "EXTERNAL", null));
    }

    @Test
    void rejectsPublicationWithoutModerationApproval() {
        String suffix = uniqueSuffix();
        UUID user = newUser("publish." + suffix + "@example.org");
        UUID category = newCategory("PUBCAT" + suffix);
        UUID organisation = newOrganization(user, "pub-org-" + suffix);

        assertRejectedWith("23514", "published while still in DRAFT moderation state",
                () -> jdbcTemplate.update("insert into opportunity (organization_id, category_id, title,"
                        + " description, application_mode, publication_status,"
                        + " created_by_user_id) values (?, ?, 'Unapproved', 'x'," + " 'INTERNAL', 'PUBLISHED', ?)",
                        organisation, category, user));
    }

    @Test
    void rejectsMissingRequiredColumn() {
        String suffix = uniqueSuffix();

        assertRejectedWith("23502", "category without a code",
                () -> jdbcTemplate.update("insert into category (slug, name_en, name_sw)" + " values ('nocode-" + suffix
                        + "', 'No code'," + " 'Hakuna msimbo')"));
    }

    @Test
    void rejectsValueOutsideTheBoundedVocabulary() {
        String suffix = uniqueSuffix();
        UUID user = newUser("vocab." + suffix + "@example.org");
        UUID category = newCategory("VOCAT" + suffix);
        UUID organisation = newOrganization(user, "vo-org-" + suffix);
        UUID opportunity = newOpportunity(organisation, category, user, "INTERNAL", null);

        assertRejectedWith("23514", "application status outside the allowed vocabulary", () -> jdbcTemplate.update(
                "insert into application (opportunity_id, seeker_user_id, status)" + " values (?, ?, 'TELEPHONED')",
                opportunity, user));
    }

    @Test
    void rejectsJsonbAttributesThatAreNotAnObject() {
        String suffix = uniqueSuffix();
        UUID user = newUser("jsonb." + suffix + "@example.org");
        UUID category = newCategory("JSONCAT" + suffix);
        UUID organisation = newOrganization(user, "json-org-" + suffix);

        assertRejectedWith("23514", "array written into the attributes column",
                () -> jdbcTemplate.update(
                        "insert into opportunity (organization_id, category_id, title,"
                                + " description, application_mode, attributes,"
                                + " created_by_user_id) values (?, ?, 'Bad json', 'x'," + " 'INTERNAL', ?::jsonb, ?)",
                        organisation, category, "[1, 2]", user));
    }

    @Test
    void rejectsDeletingACategoryThatIsStillInUse() {
        String suffix = uniqueSuffix();
        UUID user = newUser("restrict." + suffix + "@example.org");
        UUID category = newCategory("RSTCAT" + suffix);
        UUID organisation = newOrganization(user, "rst-org-" + suffix);
        newOpportunity(organisation, category, user, "INTERNAL", null);

        // ON DELETE RESTRICT raises 23001 (restrict_violation), not 23503 (foreign_key_violation).
        assertRejectedWith("23001", "deleting a category that still has opportunities",
                () -> jdbcTemplate.update("delete from category where id = ?", category));
    }

    @Test
    void deletesProfileRowsWithTheirOwner() {
        String suffix = uniqueSuffix();
        UUID user = newUser("cascade." + suffix + "@example.org");
        UUID profile = jdbcTemplate.queryForObject("insert into seeker_profile (user_id, first_name, last_name)"
                + " values (?, 'Asha', 'Juma') returning id", UUID.class, user);
        jdbcTemplate.update("insert into seeker_education (profile_id, institution, qualification)"
                + " values (?, 'University of Dar es Salaam', 'Degree')", profile);

        jdbcTemplate.update("delete from app_user where id = ?", user);

        assertEquals(0, count("select count(*) from seeker_education"),
                "education rows must be removed with their profile owner");
        assertEquals(0, count("select count(*) from seeker_profile"), "profile must be removed too");
    }

    @Test
    void persistsRoundTripIncludingJsonbAttributes() {
        String suffix = uniqueSuffix();
        UUID user = newUser("roundtrip." + suffix + "@example.org");
        UUID category = newCategory("RTCATH" + suffix);
        UUID organisation = newOrganization(user, "rt-org-" + suffix);
        UUID opportunity = newOpportunity(organisation, category, user, "INTERNAL", null);

        jdbcTemplate.update("update opportunity set title = ?, attributes = ?::jsonb where id = ?",
                "Updated opportunity", "{\"duration_weeks\": 12, \"stipend_amount\": 250000}", opportunity);

        Map<String, Object> row = jdbcTemplate.queryForMap("select title from opportunity where id = ?", opportunity);
        assertEquals("Updated opportunity", row.get("title"));

        assertEquals(1, count("select count(*) from opportunity where id = ?" + " and attributes @> ?::jsonb",
                opportunity, "{\"duration_weeks\": 12}"), "jsonb values must round-trip exactly");
        assertEquals(0,
                count("select count(*) from opportunity where id = ?"
                        + " and jsonb_exists(attributes, 'team_size_max')", opportunity),
                "attributes must not silently gain keys they were never written with");
    }
}
