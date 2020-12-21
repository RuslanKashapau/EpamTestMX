package com.epam.utils.test;

import cucumber.api.Scenario;
import lombok.Getter;
import lombok.Setter;

public class ReportUtils {

	@Getter @Setter
	public static Scenario scenario;	
	
	public static void attachText(String text) {
		scenario.write(text);		
	}

}
