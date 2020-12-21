package com.epam.test.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features", glue = "com.epam.test.steps", monochrome = true, tags = { "@Test" }, plugin = {
		"json:target/Reports/JSON/results.json" })
public class TestRunner {

}
