package com.jumia.skylens.acceptance;

import com.jumia.skylens.acceptance.configuration.AcceptanceTestConfiguration;
import io.cucumber.core.options.Constants;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.ContextConfiguration;

@Suite
@CucumberContextConfiguration
@ContextConfiguration(classes = {AcceptanceTestConfiguration.class})
@IncludeEngines("cucumber")
@SelectPackages("com.jumia.skylens.acceptance")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = Acceptance.CUCUMBER_PLUGINS)
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.jumia.skylens.acceptance")
public class Acceptance {

    public static final String CUCUMBER_PLUGINS = """
            pretty,
            summary,
            junit:build/reports/acceptanceTest/junit.xml,
            html:build/reports/acceptanceTest/html,
            json:build/reports/acceptanceTest/cucumber.json
            """;
}
