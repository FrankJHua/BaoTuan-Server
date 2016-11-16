package com.tuan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtil {

	/**
     * @param str 需要判空的字符串组合
     * @return  一旦其中一个为空，返回true，否则返回false
     */
    public static boolean isEmpty(String...str){
        for (String s : str){
            if(s == null || s.trim().isEmpty()){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 日期格式化
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date dateFormate(String date) throws ParseException{
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	Date time = sdf.parse(date);
    	System.out.println("util---"+time.toString());
		return time;
    }
    
    public static String matcher(String value){
    	
    	final Pattern PATTERN_MOBILE = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    	final Pattern PATTERN_EMAIL = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    	
    	if(PATTERN_MOBILE.matcher(value).matches()){
    		return "PHONE";
    	}else if(PATTERN_EMAIL.matcher(value).matches()){
    		return "MAILBOX";
    	}
    	return "NONE";
    }
}
