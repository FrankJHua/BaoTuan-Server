package com.tuan.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tuan.entity.Result;

public class MessageFactory {

	private static Gson gsonTool = new GsonBuilder()
			.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
			.setDateFormat("yyyy-MM-dd HH:mm")
			.create();
	
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
	
	public static String createMessage(int statusCode, String message, Object data){
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("status", statusCode);
		result.put("msg",message);
		result.put("data", data);
		return gsonTool.toJson(result);
	}
}
