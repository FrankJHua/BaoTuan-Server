package com.tuan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuan.dao.CacheDao;
import com.tuan.entity.User;
import com.tuan.service.user.UserInfoService;

/**
 *  用户个人信息设置接口
 *  
 */
public class UserInfoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String QUERY_ACTION = "query";
	private static final String UPDATE_ACTION = "update";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		String action = requestURI.substring(requestURI.lastIndexOf("/")+1);
		String result = null;
		
		//查询个人信息请求
		if(QUERY_ACTION.equalsIgnoreCase(action)){
			result = doQuery(request, response);
		}else if(UPDATE_ACTION.equalsIgnoreCase(action)){
			result = doUpdate(request,response);
		}
		
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

	
	private String doQuery(HttpServletRequest request, HttpServletResponse response){
		
		//获取请求头携带的token
		String token = request.getHeader("access-token");
		
		//根据token从缓存中获取用户userId
		CacheDao cache = new CacheDao();
		long userId = Long.parseLong(cache.get(token));
		//根据userId获取用户个人信息
		UserInfoService userInfoService = new UserInfoService();
		String result = userInfoService.getUserInfo(userId);
		return result;
	}
	
	private String doUpdate(HttpServletRequest request, HttpServletResponse response){
		
		////获取请求头携带的token
		String token = request.getHeader("access-token");
		
		//获取请求参数
		String userName = request.getParameter("userName");
		String mailbox = request.getParameter("mailbox");
		String description = request.getParameter("description");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		char gender = request.getParameter("gender").charAt(0);
		String ageStr = request.getParameter("age");
		int age = -1;
		if(null!=ageStr){
			age = Integer.parseInt(ageStr);
		}
		
		User user = new User();
		user.setUserName(userName);
		user.setMailbox(mailbox);
		user.setDescription(description);
		user.setProvince(province);
		user.setCity(city);
		user.setDistrict(district);
		user.setGender(gender);
		user.setAge(age);
		
		//根据token从缓存中获取用户userId
		CacheDao cache = new CacheDao();
		long userId = Long.parseLong(cache.get(token));
		UserInfoService userInfoService = new UserInfoService();
		return userInfoService.setUserInfo(userId, user);
	}
}
