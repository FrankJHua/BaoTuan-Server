package com.tuan.util;

public class FileUtil {

	
	/**
	 * 通过检查文件名后缀来判断是否为图片文件
	 * @param fileName
	 * @return
	 */
	public static boolean checkImageFileName(String fileName){
		
		String postfix = getFilePortfix(fileName);
		if(postfix.equalsIgnoreCase("jpg")  || postfix.equalsIgnoreCase("jpeg") ||
		   postfix.equalsIgnoreCase("png") || postfix.equalsIgnoreCase("bmp")){
			return true;
		}
		return false;
	}
	
	public static String getFilePortfix(String fileName){
		
		int postfixStart = fileName.lastIndexOf(".")+1;
		String postfix = fileName.substring(postfixStart);
		return postfix;
	}
	
	synchronized public static String createFileNameBySystemTime(String filePortfix){
		
		return System.currentTimeMillis() + "." +filePortfix;
		
	}
}
