package com.jumia.skylens.app;

import com.jumia.skylens.cli.in.runner.HubDailyMetricRowMapper;
import com.jumia.skylens.domain.SaveHubDailyMetricUseCase;
import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/debug")
public class MssqlDebugController {

    @Value("${app.job.source-db.url}")
    private String url;

    @Value("${app.job.source-db.username}")
    private String username;

    @Value("${app.job.source-db.password}")
    private String password;

    private HikariDataSource dataSource;

    @PostConstruct
    void init() {

        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setReadOnly(true);
        config.setMaximumPoolSize(2);
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource = new HikariDataSource(config);
        log.info("MSSQL debug datasource initialized for URL: {}", url);
    }

    @PreDestroy
    void destroy() {

        if (dataSource != null) {
            dataSource.close();
        }
    }

    @PostMapping("/mssql/query")
    public List<Map<String, Object>> executeQuery(@RequestBody Map<String, String> request) {

        final String query = request.get("query");

        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("'query' field is required");
        }

        log.info("Executing MSSQL debug query: {}", query);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForList(query);
    }

    @PostMapping("/mssql/query/save")
    public void executeQueryAndSaveToDb(@RequestBody Map<String, String> request) {

        final String query = request.get("query");

        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("'query' field is required");
        }

        log.info("Executing MSSQL debug query: {}", query);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        runImportFromController(jdbcTemplate, query);
    }

    private final SaveHubDailyMetricUseCase saveHubDailyMetricUseCase;

    public void runImportFromController(final JdbcTemplate jdbcTemplate, final String query) {

        log.info("Calling BI database with page size: {}", 50);

        final HubDailyMetricRowMapper rowMapper = new HubDailyMetricRowMapper();

        int offset = 0;
        int totalProcessed = 0;

        while (true) {

            final List<HubDailyMetric> page = jdbcTemplate.query(query, rowMapper, offset, 50);

            if (page.isEmpty()) {
                break;
            }

            for (final HubDailyMetric metric : page) {
                saveHubDailyMetricUseCase.run(metric);
            }

            totalProcessed += page.size();
            log.info("Processed {} records (total: {})", page.size(), totalProcessed);

            if (page.size() < 50) {
                break;
            }

            offset += 50;
        }

        log.info("ImportHubDailyMetrics job completed. Total records processed: {}", totalProcessed);
    }
}
