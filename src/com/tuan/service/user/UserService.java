package com.tuan.service.user;

import java.sql.SQLException;
import java.util.regex.Pattern;

import com.tuan.dao.user.UserQueryDao;
import com.tuan.dao.user.UserUpdateDao;
import com.tuan.entity.StatusCode;
import com.tuan.util.MessageFactory;

public class UserService {
	
	//手机号码验证
	public static final Pattern PATTERN_MOBILE = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	//邮箱号码验证
	public static final Pattern PATTERN_EMAIL = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	
	
	/**
	 * 用户的登陆验证业务
	 * 支持邮箱登陆，手机号码登陆，用户ID登陆
	 * @param key
	 * @param password
	 * @return
	 */
	public String loginCheckService(String key, String password){
		
		UserQueryDao userQueryDao = new UserQueryDao();
		String psd = "";
		String result = "";
		try{
			//以邮箱登陆
			if(PATTERN_EMAIL.matcher(key).matches()){
				psd = userQueryDao.queryPasswordBySpecific(UserQueryDao.MAILBOX, key);
			//以电话登陆
			}else if(PATTERN_MOBILE.matcher(key).matches()){
				psd = userQueryDao.queryPasswordBySpecific(UserQueryDao.PHONE, key);
				//ID号码同时也匹配了电话号码
				if(null==psd)
					psd = userQueryDao.queryPasswordById(Long.parseLong(key));
			}else{
				psd = userQueryDao.queryPasswordById(Long.parseLong(key));
			}
			
			//查询不到用户的密码，用户不存在
			if(null==psd){
				result = MessageFactory.createMessage(StatusCode.ERROR, "用户不存在");	
			}else{
				if(psd.equals(password)){
					result = MessageFactory.createMessage(StatusCode.SUCCESS, "登陆成功");
				}else{
					result = MessageFactory.createMessage(StatusCode.ERROR, "密码错误");
				}
			}
		}catch( ClassNotFoundException | SQLException e ){
			result = MessageFactory.createMessage(StatusCode.SERVER_ERROR, "未知错误");
		}catch( NumberFormatException e ){
			result = MessageFactory.createMessage(StatusCode.ERROR, "用户不存在");
		}
		return result;
	}
	
	/**
	 * 完成用户的注册业务
	 * @param mailbox
	 * @param userName
	 * @param password
	 * @return 处理后的json字符串
	 */
	public String UserRegistryService(String mailbox, String password){
		
		//邮箱格式不正确
		if(!PATTERN_EMAIL.matcher(mailbox).matches()){
			return MessageFactory.createMessage(StatusCode.ERROR, "邮箱格式错误");
		}
		
		//验证邮箱是否已经被注册
		UserQueryDao userQueryDao = new UserQueryDao();
		boolean flag = true;
		try {
			flag = userQueryDao.isContainsUser(mailbox);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return MessageFactory.createMessage(StatusCode.SERVER_ERROR, "未知错误");	
		}
		
		//邮箱已注册
		if(flag){
			return MessageFactory.createMessage(StatusCode.ERROR, "该邮箱已被其他用户注册");
		}
		
		//邮箱可用
		UserUpdateDao userUpdateDao = new UserUpdateDao();
		try {
			userUpdateDao.insertUser(mailbox, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return MessageFactory.createMessage(StatusCode.SERVER_ERROR, "未知错误");	
		}
		return MessageFactory.createMessage(StatusCode.SUCCESS, "注册成功");	
	}
}
