package com.epam.test.steps;

import com.epam.utils.test.ReportUtils;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class Hooks {
	@Before()
	public void before(Scenario scenario) {
		ReportUtils.setScenario(scenario);
		System.out.println("SCENARIO STARTS :: " + scenario.getName());
	}

	@After()
	public void tearDown(Scenario scenario) {	
		System.out.println("SCENARIO ENDS :: " + scenario.getName());		
	}
}
