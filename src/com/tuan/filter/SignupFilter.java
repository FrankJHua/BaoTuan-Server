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

public class SignupFilter implements Filter {
	
	private static int maxilboxMaxLength;
	private static int passwordLength;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		//获取注册的请求参数
		String mailbox = request.getParameter("mailbox");
		String password = request.getParameter("password");
		
		PrintWriter out = response.getWriter();
		//判空
		if(null!=mailbox && null!=password){
			//判定参数长度
			boolean userIdFlag = (mailbox.length()>0 && mailbox.length()<=maxilboxMaxLength);
			boolean passwordFlag = (password.length()>0 && password.length()<=passwordLength);
			
			if(userIdFlag && passwordFlag){
				chain.doFilter(request, response);
			}else{
				out.write(MessageFactory.createMessage(StatusCode.ERROR, "邮箱或密码长度有误"));
				out.close();
				return;
			}
		}else{
			out.write(MessageFactory.createMessage(StatusCode.ERROR, "用户名或密码不能为空"));
			out.close();
			return;
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
		maxilboxMaxLength = Integer.parseInt(fConfig.getInitParameter("mailboxMaxLength"));
		passwordLength = Integer.parseInt(fConfig.getInitParameter("passwordLength"));
	}

	public void destroy() {
		
	}
}
