package com.tuan.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.tuan.entity.StatusCode;
import com.tuan.service.activity.ActivityService;
import com.tuan.util.FileUtil;
import com.tuan.util.MessageFactory;

/**
 * 
 * 上传活动封面图片
 */
public class ActivityCoverServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private String basePath = null;   

    public ActivityCoverServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.basePath = getServletConfig().getInitParameter("basePath");
		
		DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload sfu=new ServletFileUpload(factory);
        sfu.setHeaderEncoding("UTF-8");  	//处理中文问题
        sfu.setFileSizeMax(3*1024*1024);   	//限制文件大小
  
        PrintWriter out = response.getWriter();
        
        String activityId = null;
        String newFileName = null;
        String result = null;
        
        try {
        	
			List<FileItem> fileItems= sfu.parseRequest(request);
			
			for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField() && fileItem.getFieldName().equals("act-id")) {
                	activityId = fileItem.getString(); 
                }else{
                    String fileName = fileItem.getName();
                    if(FileUtil.checkImageFileName(fileName)){
                    	//同步代码块，防止出现重复文件名
                    	synchronized (this) {
                    		String filePortfix = FileUtil.getFilePortfix(fileName);
                    		newFileName = System.currentTimeMillis() + "." + filePortfix;
                    		File file = new File(basePath,newFileName);
                        	fileItem.write(file);
						}
                    }else{
                    	//图片格式不正确，直接返回错误
                    	result = MessageFactory.createMessage(StatusCode.ERROR, "图片格式不正确");
                    	out.write(result);
                    	out.close();
                    	return;
                    }
                }                
            } 
		} catch (FileUploadException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.ERROR, "文件大小超过限制");
			out.write(result);
			out.close();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
        
        if(activityId!=null && newFileName!=null){
        	Long ID = Long.parseLong(activityId);
        	ActivityService service = new ActivityService();
        	result = service.setActivityCover(ID, newFileName);
        	out.write(result);
            out.close();
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
