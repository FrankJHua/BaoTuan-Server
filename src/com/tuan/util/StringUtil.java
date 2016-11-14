package com.tuan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    
    public static Date dateFormate(String date) throws ParseException{
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date time = sdf.parse(date);
		return time;
    }
}
