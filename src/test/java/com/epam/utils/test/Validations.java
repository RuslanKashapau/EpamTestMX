package com.epam.utils.test;

import org.junit.Assert;

public class Validations {
	public static void validateEquals(String expected, String actual, String msg) {
		ReportUtils.attachText(msg+" Expected::"+expected+ " Actual::"+actual);
		validateEquals(expected,actual);
	}

	public static void validateEquals(String expected, String actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void validateContains(String expected, String actual, String msg) {
		ReportUtils.attachText(msg+" Expected::"+expected+ " Actual::"+actual);
		validateContains(expected,actual);
	}
	
	public static void validateContains(String expected, String actual) {
		Assert.assertTrue(actual.contains(expected)); 
	}

}
