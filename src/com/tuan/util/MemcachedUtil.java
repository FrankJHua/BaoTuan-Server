package com.tuan.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class MemcachedUtil {

	private static InputStream in = DBConfig.class.getClassLoader().getResourceAsStream("memcached.properties");
	private static Properties prop = new Properties();
	
	public static Map<String,String> getMemcachedConfig(){
		
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final String HOST = prop.getProperty("HOST");
		final String PORT = prop.getProperty("PORT");
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("HOST", HOST);
		params.put("PORT", PORT);
		return params;
	}
}
