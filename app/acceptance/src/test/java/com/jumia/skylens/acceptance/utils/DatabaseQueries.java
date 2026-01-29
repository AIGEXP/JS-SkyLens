package com.jumia.skylens.acceptance.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseQueries {

    private final JdbcTemplate jdbcTemplate;

    public Boolean packageExists(String trackingNumber) {

        return jdbcTemplate.queryForObject("""
                                                   SELECT EXISTS (
                                                       SELECT 1 FROM packages p
                                                       WHERE p.tracking_number = ?
                                                   );
                                                   """,
                                           Boolean.class,
                                           trackingNumber);
    }
}
