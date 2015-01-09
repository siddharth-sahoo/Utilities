package com.awesome.pro.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Utility for reading from a property file.
 * @author siddharth.s
 */
public final class PropertyFileUtility {

	/**
	 * Root logger instance.
	 */
	private final static Logger LOGGER = Logger.getLogger(
			PropertyFileUtility.class);

	/**
	 * Configuration object populated from the specified properties file.
	 */
	private Properties config;

	/**
	 * Property file to be accessed.
	 * Used for logging purposes.
	 */
	private String fileName;

	/**
	 * Property file utility constructor.
	 * @param fileName Property file to be read.
	 * @throws IOException When the file is not found or there is an error
	 * in opening or reading the file.
	 */
	public PropertyFileUtility(String fileName) throws IOException {
		this.fileName = fileName;
		this.config = new Properties();
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(this.fileName);
		if (is == null) {
			FileReader reader = new FileReader(this.fileName);
			config.load(reader);
		}
		else {
			this.config.load(is);
			is.close();
		}
	}

	/**
	 * @return Map of all the properties.
	 */
	public Map<String, String> getAllProperties() {
		Iterator<Entry<Object, Object>> iter = this.config.entrySet().iterator();
		Map<String, String> allProperties = new HashMap<>();
		while(iter.hasNext()) {
			Entry<Object, Object> entry = iter.next();
			allProperties.put(entry.getKey().toString(), entry.getValue().toString());
		}
		return allProperties;
	}

	/**
	 * @param key Property to be returned.
	 * @return If found, string value of the specified property is returned.
	 * Else, null is returned.
	 */
	public String getStringValue(String key) {
		if(this.config.containsKey(key))
			return this.config.getProperty(key);
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return null;
	}

	/**
	 * @param key Property to be returned.
	 * @param defaultValue Default value of the property.
	 * @return If found, string value of the property is returned.
	 * Else, the default value is returned.
	 */
	public String getStringValue(String key, String defaultValue) {
		if(this.config.containsKey(key))
			return this.config.getProperty(key);
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return defaultValue;
	}

	/**
	 * @param key Property to be returned.
	 * @return If found, boolean value of the specified property is returned.
	 * Else, false is returned.
	 * False is also returned, in case the string does not match true.
	 */
	public boolean getBooleanValue(String key) {
		if(this.config.containsKey(key)) {
			String val = this.config.getProperty(key).trim().toLowerCase();
			if(val.equals("true"))
				return true;
			return false;
		}
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return false;
	}

	/**
	 * @param key Property to be returned.
	 * @param defaultValue Default value of the property.
	 * @return If found, boolean value of the property is returned.
	 * Else, the default value is returned.
	 * The default value is also returned in case the string value
	 * does not match true.
	 */
	public boolean getBooleanValue(String key, boolean defaultValue) {
		if(this.config.containsKey(key)) {
			String val = this.config.getProperty(key).trim().toLowerCase();
			if(val.equals("true"))
				return true;
			return false;
		}
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return defaultValue;
	}

	/**
	 * @param key Property to be returned.
	 * @return If found, integer value of the specified property is returned.
	 * Else, -1 is returned.
	 * -1 is also returned, in case of parse exception.
	 */
	public int getIntegerValue(String key) {
		if(this.config.containsKey(key)) {
			String val = this.config.getProperty(key).trim().toLowerCase();
			try {
				return Integer.parseInt(val);
			} catch (NumberFormatException e) {
				LOGGER.error("Error parsing as integer value: " + val 
						+ "\nIn " + this.fileName + " for key: " + key, e);
				return -1;
			}
		}
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return -1;
	}

	/**
	 * @param key Property to be returned.
	 * @param defaultValue Default value of the property.
	 * @return If found, integer value of the property is returned.
	 * Else, the default value is returned.
	 * The default value is also returned in case of a parse exception.
	 */
	public int getIntegerValue(String key, int defaultValue) {
		if(this.config.containsKey(key)) {
			String val = this.config.getProperty(key).trim().toLowerCase();
			try {
				return Integer.parseInt(val);
			} catch (NumberFormatException e) {
				LOGGER.error("Error parsing as integer value: " + val 
						+ "\nIn " + this.fileName + " for key: " + key, e);
				return defaultValue;
			}
		}
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return defaultValue;
	}

	/**
	 * @param key Property to be returned.
	 * @return If found, integer value of the specified property is returned.
	 * Else, -1 is returned.
	 * -1 is also returned, in case of parse exception.
	 */
	public double getDoubleValue(String key) {
		if(this.config.containsKey(key)) {
			String val = this.config.getProperty(key).trim().toLowerCase();
			try {
				return Double.parseDouble(val);
			} catch (NumberFormatException e) {
				LOGGER.error("Error parsing as double value: " + val 
						+ "\nIn " + this.fileName + " for key: " + key, e);
				return -1.0;
			}
		}
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return -1.0;
	}

	/**
	 * @param key Property to be returned.
	 * @param defaultValue Default value of the property.
	 * @return If found, double value of the property is returned.
	 * Else, the default value is returned.
	 * The default value is also returned in case of a parse exception.
	 */
	public double getDoubleValue(String key, double defaultValue) {
		if(this.config.containsKey(key)) {
			String val = this.config.getProperty(key).trim().toLowerCase();
			try {
				return Double.parseDouble(val);
			} catch (NumberFormatException e) {
				LOGGER.error("Error parsing as double value: " + val 
						+ "\nIn " + this.fileName + " for key: " + key, e);
				return defaultValue;
			}
		}
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return defaultValue;
	}

	/**
	 * @param key Property to be returned.
	 * @return If found, integer value of the specified property is returned.
	 * Else, -1 is returned.
	 * -1 is also returned, in case of parse exception.
	 */
	public long getLongValue(String key) {
		if(this.config.containsKey(key)) {
			String val = this.config.getProperty(key).trim().toLowerCase();
			try {
				return Long.parseLong(val);
			} catch (NumberFormatException e) {
				LOGGER.error("Error parsing as double value: " + val 
						+ "\nIn " + this.fileName + " for key: " + key, e);
				return -1;
			}
		}
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return -1;
	}

	/**
	 * @param key Property to be returned.
	 * @param defaultValue Default value of the property.
	 * @return If found, double value of the property is returned.
	 * Else, the default value is returned.
	 * The default value is also returned in case of a parse exception.
	 */
	public long getLongValue(String key, long defaultValue) {
		if(this.config.containsKey(key)) {
			String val = this.config.getProperty(key).trim().toLowerCase();
			try {
				return Long.parseLong(val);
			} catch (NumberFormatException e) {
				LOGGER.error("Error parsing as double value: " + val 
						+ "\nIn " + this.fileName + " for key: " + key, e);
				return defaultValue;
			}
		}
		LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
		return defaultValue;
	}

}
