package com.rvsappiumautomation.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.rvsappiumautomation.utils.AutomationConstants;

public class PropertiesDataHandler {
	/**
	 * Function to get the details from property file
	 * 
	 * @author rahul.raman
	 * @param fileName
	 * @param key
	 * @return
	 * @throws IOException
	 */

	public static String getProperty(String fileName, String key) throws IOException {
		String propertyValue = null;
		try {
		Properties props = new Properties();
		ClassLoader classLoader = PropertiesDataHandler.class.getClassLoader();
		InputStream input = classLoader.getResourceAsStream("propertyFiles/" + fileName + ".properties");
		props.load(input);
		propertyValue = props.getProperty(key);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return propertyValue;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String wait = getProperty(AutomationConstants.CONFIG_FILE, "timeout");
		System.out.println(wait);
	}

}
