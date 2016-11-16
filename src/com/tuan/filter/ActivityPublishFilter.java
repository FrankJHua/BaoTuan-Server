package com.tuan.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuan.dao.CacheDao;
import com.tuan.entity.StatusCode;
import com.tuan.util.MessageFactory;
import com.tuan.util.StringUtil;

/**
 * 活动发布的过滤器
 * 1. 拦截非法请求参数
 * 2. 检查参数长度
 * 3. 检查参数是否符合数据转换规则
 * 4. 控制用户权限
 * 
 */
public class ActivityPublishFilter implements Filter {

	private int nameMaxLength = 0;
	private int positionMaxLength = 0;
	private int descriptionMaxLength = 0;
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest hRequest = (HttpServletRequest)request;
		HttpServletResponse hResponse = (HttpServletResponse)response;
		PrintWriter out = hResponse.getWriter();
		CacheDao cache = new CacheDao();
		
		//检查用户权限
		//获取客户端token
		String token = hRequest.getHeader("access-token");
		if(null!=token){
			Object userSession = cache.get(token);
			if(null==userSession){
				out.print(MessageFactory.createMessage(StatusCode.ERROR, "登陆信息已失效,请重新登陆"));
				out.close();
				return;
			}
		}else{
			out.print(MessageFactory.createMessage(StatusCode.ERROR, "请先登陆"));
			out.close();
			return;
		}
		
		//获取请求参数
		String name = request.getParameter("name");
		String position = request.getParameter("position");
		String description = request.getParameter("description");
		String time = request.getParameter("time");
		String fee = request.getParameter("fee");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String number = request.getParameter("number");
		String type = request.getParameter("type");
		
		//判空
		if(StringUtil.isEmpty(name,position,description,time,fee,province,city,district,number,type)){
			out.print(MessageFactory.createMessage(StatusCode.ERROR, "活动信息未填写完整"));
			out.close();
			return;
		}
		//检查字段长度
		if(name.length()>nameMaxLength){
			out.print(MessageFactory.createMessage(StatusCode.ERROR, "活动名过长"));
			out.close();
			return;
		}else if(position.length()>positionMaxLength){
			out.print(MessageFactory.createMessage(StatusCode.ERROR, "详细地址过长"));
			out.close();
			return;
		}else if(description.length()>descriptionMaxLength){
			out.print(MessageFactory.createMessage(StatusCode.ERROR, "活动描述信息过长"));
			out.close();
			return;
		}
		
		//检查数据类型
		try {
			Integer.parseInt(number);
			Float.parseFloat(fee);
			StringUtil.dateFormate(time);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			out.print(MessageFactory.createMessage(StatusCode.ERROR, "数字填写格式错误"));
			out.close();
			return;
		} catch (ParseException e){
			e.printStackTrace();
			out.print(MessageFactory.createMessage(StatusCode.ERROR, "日期填写格式错误"));
			out.close();
			return;
		}
		
		//检查通过，放行
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
		this.nameMaxLength = Integer.parseInt(fConfig.getInitParameter("nameMaxLength"));
		this.positionMaxLength = Integer.parseInt(fConfig.getInitParameter("positionMaxLength"));
		this.descriptionMaxLength = Integer.parseInt(fConfig.getInitParameter("descriptionMaxLength"));
	}
	
	public void destroy() {
		
	}
}
