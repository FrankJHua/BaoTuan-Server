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

public class SpecificSearchFilter implements Filter {


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String actType = request.getParameter("actType");
		String costLower = request.getParameter("costLower");
		String costUpper = request.getParameter("costUpper");
		String peopleLower = request.getParameter("peopleLower");
		String peopleUpper = request.getParameter("peopleUpper");
		
		String result = null;
		try{
			if(StringUtil.isEmpty(actType,costLower,costUpper,peopleLower,peopleUpper)){
				throw new NullPointerException();
			}
			Float.parseFloat(costLower);
			Float.parseFloat(costUpper);
			Integer.parseInt(peopleLower);
			Integer.parseInt(peopleUpper);
		}catch(NullPointerException e){
			result = MessageFactory.createMessage(StatusCode.ERROR, "请选择填写并选择搜索条件");
		} catch(NumberFormatException e){
			result = MessageFactory.createMessage(StatusCode.ERROR, "数值类型错误");
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
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
