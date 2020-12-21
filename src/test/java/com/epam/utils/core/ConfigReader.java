package com.epam.utils.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	public static String readPropByEnvironment(String prop) {
		String env = getDefaultEnv();
		return readProp(env + "." + prop);
	}

	public static String getDefaultEnv() {
		String env = System.getProperty("defaultEnv");
		return StringUtils.isNullEmpty(env) ? readProp("defaultEnv") : env;
	}

	public static String readProp(String prop) {
		try (InputStream input = new FileInputStream(FileUtils.getProjectPath() + "/config.properties")) {

			Properties props = new Properties();
			props.load(input);
			return props.getProperty(prop);

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
