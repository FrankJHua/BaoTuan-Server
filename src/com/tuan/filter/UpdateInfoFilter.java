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

public class UpdateInfoFilter implements Filter {

	private int usernameMaxLength = 0;
	private int descriptionMaxLength = 0;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		String userName = request.getParameter("userName");
		String mailbox = request.getParameter("mail");
		String description = request.getParameter("description");
		String gender = request.getParameter("gender");
		
		
		boolean hasNull = StringUtil.isEmpty(userName,mailbox,description,gender);
		boolean isError = false;
		String result = null;
		if(hasNull){
			result = MessageFactory.createMessage(StatusCode.ERROR, "个人信息未填写完整");
			isError = true;
			
		}else if(userName.length()>usernameMaxLength){
			result = MessageFactory.createMessage(StatusCode.ERROR, "用户名过长");
			isError = true;
			
		}else if(description.length()>descriptionMaxLength){
			result = MessageFactory.createMessage(StatusCode.ERROR, "个性签名过长");
			isError = true;
			
		}else if(!"MAILBOX".equals(StringUtil.matcher(mailbox))){
			result = MessageFactory.createMessage(StatusCode.ERROR, "邮箱格式错误");
			isError = true;
		}
		if(isError){
			PrintWriter out = response.getWriter();
			out.write(result);
			out.close();
			return;
		}
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		usernameMaxLength = Integer.parseInt(fConfig.getInitParameter("usernameMaxLength"));
		descriptionMaxLength = Integer.parseInt(fConfig.getInitParameter("descriptionMaxLength"));
	}
	public void destroy() {
	}
}
