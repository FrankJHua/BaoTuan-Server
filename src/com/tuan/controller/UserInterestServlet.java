package com.tuan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuan.dao.CacheDao;
import com.tuan.service.user.UserInterestService;

/**
 * 
 * 用户设置兴趣爱好的servlet
 */
public class UserInterestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String QUERY_ACTION = "query";
	private static final String UPDATE_ACTION = "update";
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		String action = requestURI.substring(requestURI.lastIndexOf("/")+1);
		String result = null;
		
		//设置兴趣标签请求
		if(UPDATE_ACTION.equalsIgnoreCase(action)){
			result = doSetInterest(request,response);
		//查询兴趣标签请求
		}else if(QUERY_ACTION.equalsIgnoreCase(action)){
			result = doGetInterest(request,response);
		}
		
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

	
	private String doSetInterest(HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getHeader("access-token");
		CacheDao cache = new CacheDao();
		long ID = Long.parseLong(cache.get(token));
		String interests = request.getParameter("interest");
		//调用service接口
		UserInterestService interestService = new UserInterestService();
		String result = interestService.setUserInterest(ID, interests);
		return result;
	}
	
	private String doGetInterest(HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getHeader("access-token");
		CacheDao cache = new CacheDao();
		long ID = Long.parseLong(cache.get(token));
		//调用service接口
		UserInterestService interestService = new UserInterestService();
		String result = interestService.getUserInterest(ID);
		return result;
	}
}
