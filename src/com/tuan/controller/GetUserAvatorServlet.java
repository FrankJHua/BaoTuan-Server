package com.tuan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetUserAvatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 在请求的url中包含文件名
		String requestURI = request.getRequestURI();
		String fileName = requestURI.substring(requestURI.lastIndexOf("/") + 1);

		// 文件名不为空
		if (!"".equals(fileName) && null != fileName) {
			String basePath = getServletConfig().getInitParameter("basePath");
			File file = new File(basePath, fileName);
			// 本地文件中存在这个图片
			synchronized (this) {
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					OutputStream out = response.getOutputStream();

					byte[] buffer = new byte[1024];
					int offset = 0;
					while ((offset = fis.read(buffer)) != -1) {
						out.write(buffer, 0, offset);
					}
					out.flush();
					out.close();
					fis.close();
				}
			}
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
