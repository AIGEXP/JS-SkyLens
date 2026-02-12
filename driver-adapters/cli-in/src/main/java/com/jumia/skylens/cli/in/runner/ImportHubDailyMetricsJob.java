package com.jumia.skylens.cli.in.runner;

import com.jumia.skylens.domain.SaveHubDailyMetricUseCase;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Profile("job")
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"DM_EXIT"}, justification = "I prefer to suppress these FindBugs warnings")
public class ImportHubDailyMetricsJob implements ApplicationRunner {

    private static final String QUERY = """
            SELECT Hub_SID,
                   Service_Provider_SID,
                   Day,
                   Pre_paid,
                   Movement_Type,
                   Nr_packages_delivered,
                   Nr_packages_closed,
                   Nr_packages_received,
                   Nr_packages_lost_at_Hub,
                   Nr_packages_no_attempt_1D,
                   Nr_packages_no_attempt_2D,
                   Nr_packages_no_attempt_3D,
                   Nr_packages_no_attempt_4D,
                   "Nr_packages_no_attempt_+4D"
            FROM [SLRCH].[LPMT_HMT_HUB_PERFORMANCE_KPIS_DATA]
            WHERE service_provider_sid = 'e859c764-17d9-327e-a2db-ad4a6513726c' and day = 20251129
            ORDER BY [DAY]
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
            """;

    private final SaveHubDailyMetricUseCase saveHubDailyMetricUseCase;

    private final JobProperties jobProperties;

    private final ApplicationContext applicationContext;

    @Override
    public void run(final @NonNull ApplicationArguments args) {

        try (HikariDataSource dataSource = createDataSource()) {

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            runImport(jdbcTemplate);
        } catch (final Exception exception) {
            log.error("ImportHubDailyMetrics job failed", exception);
        }

        SpringApplication.exit(applicationContext, () -> 0);
        System.exit(0);
    }

    private void runImport(final JdbcTemplate jdbcTemplate) {

        log.info("Starting ImportHubDailyMetrics job with page size: {}", jobProperties.getPageSize());

        final HubDailyMetricRowMapper rowMapper = new HubDailyMetricRowMapper();

        int offset = 0;
        int totalProcessed = 0;

        while (true) {

            final List<HubDailyMetric> page = jdbcTemplate.query(QUERY, rowMapper, offset, jobProperties.getPageSize());

            if (page.isEmpty()) {
                break;
            }

            for (final HubDailyMetric metric : page) {
                saveHubDailyMetricUseCase.run(metric);
            }

            totalProcessed += page.size();
            log.info("Processed {} records (total: {})", page.size(), totalProcessed);

            if (page.size() < jobProperties.getPageSize()) {
                break;
            }

            offset += jobProperties.getPageSize();
        }

        log.info("ImportHubDailyMetrics job completed. Total records processed: {}", totalProcessed);
    }

    private HikariDataSource createDataSource() {

        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jobProperties.getSourceDb().getUrl());
        config.setUsername(jobProperties.getSourceDb().getUsername());
        config.setPassword(jobProperties.getSourceDb().getPassword());
        config.setReadOnly(true);
        config.setMaximumPoolSize(2);
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return new HikariDataSource(config);
    }
}
