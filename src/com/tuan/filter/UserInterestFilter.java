package com.tuan.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.tuan.entity.StatusCode;
import com.tuan.util.MessageFactory;
import com.tuan.util.StringUtil;

public class UserInterestFilter implements Filter {

	private int interestMaxLength = 0;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String interest = request.getParameter("interest");
		PrintWriter out = response.getWriter();
		if(StringUtil.isEmpty(interest)){
			String result = MessageFactory.createMessage(StatusCode.ERROR, "兴趣标签不能为空");
			out.write(result);
			out.close();
			return;
		}
		if(interest.length()>interestMaxLength){
			String result = MessageFactory.createMessage(StatusCode.ERROR, "兴趣标签超过上限");
			out.write(result);
			out.close();
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		interestMaxLength = Integer.parseInt(fConfig.getInitParameter("interestMaxLength"));
	}
	public void destroy() {
		
	}
}
