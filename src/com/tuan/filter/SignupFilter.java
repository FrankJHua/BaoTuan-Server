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

public class SignupFilter implements Filter {
	
	private static int maxilboxMaxLength;
	private static int passwordLength;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		//获取注册的请求参数
		String mailbox = request.getParameter("mail");
		String password = request.getParameter("password");
		String userName = request.getParameter("userName");
		
		PrintWriter out = response.getWriter();
		String result = "";
		
		//判空
		if(StringUtil.isEmpty(mailbox,password,userName)){
			result = MessageFactory.createMessage(StatusCode.ERROR, "注册信息未填写完整");
			out.write(result);
			out.close();
			return;
		}
		//判断长度
		boolean isError = false;
		do{
			if(mailbox.length() > maxilboxMaxLength){
				result = MessageFactory.createMessage(StatusCode.ERROR, "邮箱长度过长");
				isError = true;
				break;
			}
			if(password.length() > passwordLength){
				result = MessageFactory.createMessage(StatusCode.ERROR, "密码长度过长");
				isError = true;
				break;
			}
			if(userName.length() > 32){
				result = MessageFactory.createMessage(StatusCode.ERROR, "用户名长度过长");
				isError = true;
				break;
			}
		}while(false);
		
		if(isError){
			out.write(result);
			out.close();
			return;
		}
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
		maxilboxMaxLength = Integer.parseInt(fConfig.getInitParameter("mailboxMaxLength"));
		passwordLength = Integer.parseInt(fConfig.getInitParameter("passwordLength"));
	}

	public void destroy() {
		
	}
}
