package com.tuan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuan.dao.CacheDao;
import com.tuan.service.user.UserInfoService;

public class UserActivityServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String GET_MY_JOIN_ACTIVITY_ACTION = "my-join";
	private static final String GET_MY_PUBLISH_ACTIVITY_ACTION = "my-publish";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		String action = requestURI.substring(requestURI.lastIndexOf("/")+1);
		String result = null;
		
		if(action.equalsIgnoreCase(GET_MY_JOIN_ACTIVITY_ACTION)){
			
			result = doGetMyJoinActivity(request,response);
		}
		
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
			
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

	
	private String doGetMyJoinActivity(HttpServletRequest request, HttpServletResponse response){
		
		String token = request.getHeader("access-token");
		CacheDao cache = new CacheDao();
		long userId = Long.parseLong(cache.get(token));
		UserInfoService infoService = new UserInfoService();
		String result = infoService.getUserJoinActivities(userId);
		return result;
	}
}
