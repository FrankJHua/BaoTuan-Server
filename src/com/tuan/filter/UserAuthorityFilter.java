package com.tuan.filter;

import java.io.IOException;
import java.io.PrintWriter;

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

/**
 * 
 * 控制用户的权限，若用户未登陆，则拦截请求
 *
 */
public class UserAuthorityFilter implements Filter {


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest hRequest = (HttpServletRequest)request;
		HttpServletResponse hResponse = (HttpServletResponse)response;
		PrintWriter out = hResponse.getWriter();
		
		String token = hRequest.getHeader("access-token");
		CacheDao cacheDao = new CacheDao();
		
		//用户有token并且token有效才放行
		if(null!=token && null!=cacheDao.get(token)){
			chain.doFilter(request, response);
		}else{
			String responseResult = MessageFactory.createMessage(StatusCode.ERROR, "请先登陆");
			out.write(responseResult);
			out.close();
			return;
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}
	public void destroy() {
		
	}
}
