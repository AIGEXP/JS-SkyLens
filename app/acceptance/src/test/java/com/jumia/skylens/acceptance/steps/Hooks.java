package com.jumia.skylens.acceptance.steps;

import com.jumia.skylens.acceptance.utils.DatabaseCleaner;
import io.cucumber.java.Before;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Hooks {

    private final DatabaseCleaner databaseCleaner;

    @Before
    public void resetDatabase() {

        databaseCleaner.truncateAllTables();
    }
}
