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

/**
 * 
 * 检查登陆参数是否存在并且是否符合定义长度
 */

public class LoginFilter implements Filter {
	
	private static int userIdLength;
	private static int passwordLength;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		//获取登陆的请求参数
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		PrintWriter out = response.getWriter();
		if(null!=userId && null!=password){
			
			boolean userIdFlag = (userId.length()>0 && userId.length()<=userIdLength);
			boolean passwordFlag = (password.length()>0 && password.length()<=passwordLength);
			
			if(userIdFlag && passwordFlag)
				chain.doFilter(request, response);
			else{
				out.write(MessageFactory.createMessage(StatusCode.ERROR, "用户名或密码长度有误"));
				out.close();
				return;
			}
		}else{
			out.write(MessageFactory.createMessage(StatusCode.ERROR, "用户名或密码不能为空"));
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
		userIdLength = Integer.parseInt(fConfig.getInitParameter("userIdMaxLength"));
		passwordLength = Integer.parseInt(fConfig.getInitParameter("passwordLength"));
	}

	public void destroy() {
		
	}
}
