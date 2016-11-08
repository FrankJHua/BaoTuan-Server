package com.tuan.util;

import com.google.gson.Gson;
import com.tuan.entity.Result;

public class MessageFactory {

	private static Gson gsonTool = new Gson();
	
	public static String createMessage(int statusCode, String message){
		
		Result result = new Result(statusCode,message);
		return gsonTool.toJson(result);
		
	}
}
