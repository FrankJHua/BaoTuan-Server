package com.tuan.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.tuan.entity.Result;

public class MessageFactory {

	private static Gson gsonTool = new Gson();
	
	public static String createMessage(int statusCode, String message){
		
		Result result = new Result(statusCode,message);
		return gsonTool.toJson(result);
		
	}
	
	@SuppressWarnings("rawtypes")
	public static String createMessage(int statusCode, String message, Map data){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("status", statusCode);
		result.put("msg", message);
		result.put("data", data);
		return gsonTool.toJson(result);
	}
}
