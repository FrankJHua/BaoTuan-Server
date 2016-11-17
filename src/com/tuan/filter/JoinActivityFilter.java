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
 * 参加活动拦截器
 * 检查参数的合法性
 *
 */
public class JoinActivityFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		PrintWriter out = response.getWriter();
		String act_idStr = request.getParameter("act_id");
		String result = null;
		try{
			if(null==act_idStr){
				throw new NumberFormatException();
			}
			Long.parseLong(act_idStr);
		}catch(NullPointerException | NumberFormatException e){
			result = MessageFactory.createMessage(StatusCode.ERROR, "无法请求该活动");
			out.write(result);
			out.close();
			return;
		}
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}
	public void destroy() {
	}

}
