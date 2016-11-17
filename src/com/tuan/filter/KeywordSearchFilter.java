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

/**
 * 
 * 关键字搜索活动请求拦截器
 *
 */
public class KeywordSearchFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String keyword = request.getParameter("keyword");
		String pageNumber = request.getParameter("page");
		String result = null;
		try{
			if(StringUtil.isEmpty(keyword)){
				throw new NullPointerException();
			}
			Integer.parseInt(pageNumber);
		}catch(NullPointerException e){
			result = MessageFactory.createMessage(StatusCode.ERROR, "关键字不能为空");
		}catch(NumberFormatException e){
			result = MessageFactory.createMessage(StatusCode.ERROR, "页号只能为整数");
		}
		if(null!=result){
			PrintWriter out = response.getWriter();
			out.write(result);
			out.close();
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
