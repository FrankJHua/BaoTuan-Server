package com.tuan.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class EncodingFilter implements Filter {

	private static String charset = null;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(charset);
		chain.doFilter(request, response);
		response.setCharacterEncoding(charset);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		charset = fConfig.getInitParameter("Charset");
	}
	public EncodingFilter() {
	}	
	public void destroy() {
	}
}
