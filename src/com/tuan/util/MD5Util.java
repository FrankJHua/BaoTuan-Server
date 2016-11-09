package com.tuan.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * MD5 信息摘要
 *
 */
public class MD5Util {

	public static String MD5Encode(String encodeStr) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.reset();
		md.update(encodeStr.getBytes());

		// 经过信息摘要运算得到二进制数组
		byte[] bArray = md.digest();

		int i;
		StringBuffer buf = new StringBuffer("");
		// 将二进制数组转换为十六进制字符串
		for (int offset = 0; offset < bArray.length; offset++) {
			i = bArray[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append('0');
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}
}
