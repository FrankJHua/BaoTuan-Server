package com.tuan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuan.entity.StatusCode;
import com.tuan.service.user.UserService;
import com.tuan.util.MD5Util;
import com.tuan.util.MessageFactory;


public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String LOGIN_ACTION = "login";
	private static final String SIGNUP_ACTION = "signup";
	   
    public UserServlet() {
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
		
		UserService userService = new UserService();
		password = MD5Util.MD5Encode(password);
		return userService.loginCheckService(userId, password);
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
		
		UserService userService = new UserService();
		password = MD5Util.MD5Encode(password);
		return userService.UserRegistryService(mailbox, password);
		
	}
}
