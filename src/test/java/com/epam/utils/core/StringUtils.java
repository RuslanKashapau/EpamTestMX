package com.epam.utils.core;

public class StringUtils {
	public static boolean isNullEmpty(String s) {
		if (s == null || s.isEmpty())
			return true;
		return false;
	}
	
	public static String fixcucumberNextLine(String s) {
		return s.replaceAll("\\\\n", "\n");
	}
}
