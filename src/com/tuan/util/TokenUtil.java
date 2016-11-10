package com.tuan.util;

import java.util.Date;

public class TokenUtil {

	public static String createToken(String userId){
		String rawToken = new Date().toString() + userId;
		return MD5Util.MD5Encode(rawToken);
	}
}
