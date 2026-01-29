package com.jumia.skylens.acceptance.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;

    public void truncateAllTables() {

        // Temporarily disable FK constraints
        jdbcTemplate.execute("SET session_replication_role = replica");

        // Get all table names in the public schema
        final List<String> tables = jdbcTemplate.queryForList(
                "SELECT tablename FROM pg_tables WHERE schemaname='public' AND tablename <> 'flyway_schema_history'",
                String.class
        );

        if (!tables.isEmpty()) {
            final String truncateSql = "TRUNCATE TABLE " + String.join(", ", tables) + " RESTART IDENTITY CASCADE";
            jdbcTemplate.execute(truncateSql);
        }

        // Restore FK constraints
        jdbcTemplate.execute("SET session_replication_role = origin");
    }
}
