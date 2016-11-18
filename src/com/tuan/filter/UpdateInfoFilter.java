package com.tuan.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.tuan.entity.StatusCode;
import com.tuan.util.MessageFactory;

public class UpdateInfoFilter implements Filter {

	private Set<String> paramSet = new HashSet<String>();
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Enumeration<String>paramNames = request.getParameterNames();
		String result = null;
		if(paramNames.hasMoreElements()){
			String paramName = paramNames.nextElement();
			if(paramSet.contains(paramName)){
				chain.doFilter(request, response);
			}else{
				result = MessageFactory.createMessage(StatusCode.ERROR, "包含不合法参数");
			}
		}else{
			result = MessageFactory.createMessage(StatusCode.ERROR, "请求缺少参数");
		}
		if(null!=result){
			PrintWriter out = response.getWriter();
			out.write(result);
			out.close();
			return;
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		paramSet.add("userName");
		paramSet.add("description");
		paramSet.add("province");
		paramSet.add("city");
		paramSet.add("district");
		paramSet.add("age");
		paramSet.add("gender");
	}
	public void destroy() {
	}
}
