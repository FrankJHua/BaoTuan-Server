package com.tuan.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBConfig {

	private static InputStream in = DBConfig.class.getClassLoader().getResourceAsStream("config.properties");
	private static Properties prop = new Properties();
	
	public static Map<String,String> getDatabaseConfig(){
		
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final String DRIVER = prop.getProperty("DRIVER");
		final String CONNECT_WAY = prop.getProperty("CONNECT_WAY");
		final String DB_HOST = prop.getProperty("DB_HOST");
		final String DB_PORT = prop.getProperty("DB_PORT");
		final String DB_NAME = prop.getProperty("DB_NAME");
		final String DB_USER = prop.getProperty("DB_USER");
		final String DB_PASSWORD = prop.getProperty("DB_PASSWORD");
		
		final String URL = CONNECT_WAY + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
		Map<String,String> configs = new HashMap<String,String>();
		configs.put("driver", DRIVER);
		configs.put("url", URL);
		configs.put("user", DB_USER);
		configs.put("password", DB_PASSWORD);
		
		return configs;
	}
}
