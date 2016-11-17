package com.tuan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuan.service.activity.ActivitySearchService;

/**
 * 	
 * 搜索活动控制器
 *
 */
public class ActivitySearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String COMMEND_SEARCH_ACTION = "commend";
	private static final String KEYWORD_SEARCH_ACTION = "keyword";
	private static final String SPECIFIC_SEARCH_ACTION = "specific";
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		String action = requestURI.substring(requestURI.lastIndexOf("/")+1);
		String result = null;
		
		//根据兴趣标签推送活动
		if(COMMEND_SEARCH_ACTION.equalsIgnoreCase(action)){
			doCommendSearch(request, response);
		//根据关键字搜索活动
		}else if(KEYWORD_SEARCH_ACTION.equalsIgnoreCase(action)){
			result = doKeywordSearch(request, response);
			
		//查询某个指定的活动详细信息
		}else if(SPECIFIC_SEARCH_ACTION.equalsIgnoreCase(action)){
			doSpecificSearch(request, response);
		}
		
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	private String doCommendSearch(HttpServletRequest request, HttpServletResponse response){
		return null;
	}
	
	
	private String doKeywordSearch(HttpServletRequest request, HttpServletResponse response){
		
		String keyword = request.getParameter("keyword");
		int pageNumber = Integer.parseInt(request.getParameter("page"));
		ActivitySearchService activitySearchService = new ActivitySearchService();
		String result = activitySearchService.searchActivityByKeyword(keyword, pageNumber);
		return result;
	}
	
	private String doSpecificSearch(HttpServletRequest request, HttpServletResponse response){
		return null;
	}
}
