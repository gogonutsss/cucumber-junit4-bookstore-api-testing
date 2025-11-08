package com.bookstore.cucumber.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.bookstore.cucumber.stepdefinitions"},
        plugin = {
                "pretty", "summary",
                "html:target/cucumber-reports/cucumber-report.html",
                "json:target/cucumber-reports/cucumber.json",
				"timeline:target/timeline"
        },
        tags = "@regression",
		monochrome = false,
		dryRun = false,
		stepNotifications = true
        )
public class TestRunner {
}