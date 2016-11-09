package com.tuan.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *  拦截所有请求，并设置编码字符集
 */
public class EncodingFilter implements Filter {

	private static String charset = null;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		//获取配置文件中设置的编码字符集
		charset = fConfig.getInitParameter("Charset");
	}
	public EncodingFilter() {
	}	
	public void destroy() {
	}
}
