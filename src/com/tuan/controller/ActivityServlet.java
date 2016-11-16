package com.tuan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuan.dao.CacheDao;
import com.tuan.entity.Activity;
import com.tuan.entity.StatusCode;
import com.tuan.service.activity.ActivityService;
import com.tuan.util.MessageFactory;
import com.tuan.util.StringUtil;


public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String PUBLISH_ACTION = "publish";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		String action = requestURI.substring(requestURI.lastIndexOf("/")+1);
		String result = null;
		
		if(action.equalsIgnoreCase(PUBLISH_ACTION)){
			result = doPublish(request,response);
		}
		
		PrintWriter out = response.getWriter();
		out.print(result);
		out.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private String doPublish(HttpServletRequest request, HttpServletResponse response){
		
		String result = null;
		//获取请求参数
		String name = request.getParameter("name");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String position = request.getParameter("position");
		String description = request.getParameter("description");
		String type = request.getParameter("type");
		
		System.out.println(request.getParameter("time"));
		
		Date time = null;
		try {
			time = StringUtil.dateFormate(request.getParameter("time"));
		} catch (ParseException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.SERVER_ERROR, "日期转化异常");
			return result;
		}
		int number = Integer.parseInt(request.getParameter("number"));
		float fee = Float.parseFloat(request.getParameter("fee"));
		fee = Math.round(fee*100)*0.01f;
		
		Activity activity = new Activity();
		activity.setName(name);
		activity.setProvince(province);
		activity.setCity(city);
		activity.setDistrict(district);
		activity.setPosition(position);
		activity.setDescription(description);
		activity.setType(type);
		activity.setTime(time);
		activity.setNumber(number);
		activity.setFee(fee);
		
		String token = request.getHeader("access-token");
		ActivityService activityService = new ActivityService();
		CacheDao cache = new CacheDao();
		long publisher = Long.parseLong(cache.get(token));
		result = activityService.activityPublish(publisher, activity);
		return result;
	}
}
