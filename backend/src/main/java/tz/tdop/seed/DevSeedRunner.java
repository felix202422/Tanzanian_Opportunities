package tz.tdop.seed;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Applies development seed data when the {@code seed} Spring profile is active.
 * <p>
 * The seed is deliberately <em>not</em> a Flyway migration. Flyway history would then differ between a seeded and an
 * unseeded database, and opening an unseeded one with default locations fails validation ("applied migration not
 * resolved locally"). Running the seed outside Flyway keeps the schema history byte-identical in every environment
 * while still keeping development data out of production, which is what D-014 requires.
 * <p>
 * {@link Flyway#migrate()} is invoked first so ordering relative to Spring Boot's own Flyway initialization cannot
 * matter: the call is a no-op when the schema is already current.
 */
@Component
@Profile("seed")
public class DevSeedRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DevSeedRunner.class);

    private static final Resource SEED_SCRIPT = new ClassPathResource("db/seed/dev_seed.sql");

    private final Flyway flyway;
    private final DataSource dataSource;

    @Autowired
    public DevSeedRunner(Flyway flyway, DataSource dataSource) {
        this.flyway = flyway;
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        flyway.migrate();

        Connection connection = DataSourceUtils.getConnection(dataSource);
        boolean originalAutoCommit = true;
        try {
            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            ScriptUtils.executeSqlScript(connection, SEED_SCRIPT);
            connection.commit();
            log.info("Development seed data applied from {}", SEED_SCRIPT);
        } catch (SQLException | RuntimeException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(originalAutoCommit);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }
}
