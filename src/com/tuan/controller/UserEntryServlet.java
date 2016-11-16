package com.tuan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuan.dao.CacheDao;
import com.tuan.entity.StatusCode;
import com.tuan.service.user.UserEntryService;
import com.tuan.service.user.UserInfoService;
import com.tuan.util.MD5Util;
import com.tuan.util.MessageFactory;
import com.tuan.util.TokenUtil;


public class UserEntryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String LOGIN_ACTION = "login";
	private static final String SIGNUP_ACTION = "signup";
	private static final String LOGOUT_ACTION = "logout";
	private static final String CHECK_TOKEN_ACTION = "check-token";
	   
    public UserEntryServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		String action = requestURI.substring(requestURI.lastIndexOf("/")+1);
		String result = null;
		
		//登陆请求
		if(LOGIN_ACTION.equalsIgnoreCase(action)){
			result = checkLogin(request, response);
			
		//注册请求	
		}else if(SIGNUP_ACTION.equalsIgnoreCase(action)){
			result = signup(request, response);
		
		//登出请求
		}else if(LOGOUT_ACTION.equalsIgnoreCase(action)){
			result = logout(request, response);
		
		//检查token信息有效性请求
		}else if(CHECK_TOKEN_ACTION.equalsIgnoreCase(action)){
			result = checkToken(request, response);
			
		//请求的url未定义
		}else{
			result = MessageFactory.createMessage(StatusCode.UNDEFINED_URL, "无法请求该资源");
		}
		
		//响应
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 登陆
	 * @param request
	 * @param response
	 * @return
	 */
	private String checkLogin(HttpServletRequest request, HttpServletResponse response){
			
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");	
		
		UserEntryService userService = new UserEntryService();
		password = MD5Util.MD5Encode(password);
		String result = userService.loginCheckService(userId, password);
		if(result.contains("\"status\":200")){
			String token = TokenUtil.createToken(userId);
			response.setHeader("access-token", token);
			UserInfoService userInfoService = new UserInfoService();
			long ID = userInfoService.getID(userId);
			CacheDao cache = new CacheDao();
			cache.set(token, CacheDao.EXP_WEEK, ID);
		}
		return result;
	}

	/**
	 * 注册
	 * @param request
	 * @param response
	 * @return
	 */
	private String signup(HttpServletRequest request, HttpServletResponse response){
		
		String mailbox = request.getParameter("mailbox");
		String password = request.getParameter("password");
		
		UserEntryService userService = new UserEntryService();
		password = MD5Util.MD5Encode(password);
		String result = userService.UserRegistryService(mailbox, password);
		if(result.contains("\"status\":200")){
			String token = TokenUtil.createToken(mailbox);
			response.setHeader("access-token", token);
			UserInfoService userInfoService = new UserInfoService();
			long ID = userInfoService.getID(mailbox);
			CacheDao cache = new CacheDao();
			cache.set(token, CacheDao.EXP_WEEK, ID);
		}
		return result;
	}
	
	/**
	 * 登出
	 * @param request
	 * @param response
	 * @return
	 */
	private String logout(HttpServletRequest request, HttpServletResponse response){
		
		String token = request.getHeader("access-token");
		String result = "";
		if(null!=token){
			CacheDao cache = new CacheDao();
			if(null!=cache.get(token))
				cache.delete(token);
			result = MessageFactory.createMessage(StatusCode.SUCCESS, "登出成功");
		}else{
			result =  MessageFactory.createMessage(StatusCode.HEADER_NOT_FOUND, "请求头缺少token");
		}
		return result;
	}
	
	/**
	 * 检查token的有效性
	 * @param request
	 * @param response
	 * @return
	 */
	private String checkToken(HttpServletRequest request, HttpServletResponse response){
		
		String token = request.getHeader("access-token");
		String result="";
		if(null==token){
			result = MessageFactory.createMessage(StatusCode.HEADER_NOT_FOUND, "请求头缺少token");
		}else{
			CacheDao cache = new CacheDao();
			if(null==cache.get(token)){
				result = MessageFactory.createMessage(StatusCode.ERROR, "token已失效");
			}else{
				result = MessageFactory.createMessage(StatusCode.SUCCESS, "token有效");
			}
		}
		
		return result;
	}
}
