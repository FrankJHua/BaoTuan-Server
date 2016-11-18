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
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
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

    public ActivityCoverServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload sfu=new ServletFileUpload(factory);
        sfu.setHeaderEncoding("UTF-8");  	//处理中文问题
        sfu.setFileSizeMax(3*1024*1024);   	//限制文件大小
      
        String result = null;
        
        try {
        	long act_id = Long.parseLong(request.getParameter("act-id"));
			List<FileItem> fileItems= sfu.parseRequest(request);
			
			
			for(FileItem fileItem : fileItems){
				//判断是否是文件域
				if(!fileItem.isFormField()){
					//获取文件名
					String fileName = fileItem.getName();
					//检查是否是支持的图片文件格式
					boolean isImage = FileUtil.checkImageFileName(fileName);
					if(isImage){
						//生成新的文件名，防止不同用的文件名冲突
						String filePortfix = FileUtil.getFilePortfix(fileName);
						String newFileName = FileUtil.createFileNameBySystemTime(filePortfix);
						//保存图片
						File cover = createImage(newFileName);
						fileItem.write(cover);
						//在数据库中更新活动封面图片存储路径
						ActivityService activityService = new ActivityService();
						result = activityService.setActivityCover(act_id, newFileName);
					}else{
						result = MessageFactory.createMessage(StatusCode.ERROR, "不支持该图片的格式");
					}
					
				}
			}
		}catch (FileSizeLimitExceededException e) {
			result = MessageFactory.createMessage(StatusCode.ERROR, "图片大小超过3M");
		}catch (Exception e){
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.ERROR, "上传失败");
		}
		
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

	private File createImage(String fileName){
		String basePath = getServletConfig().getInitParameter("basePath");
		File file = new File(basePath,fileName);
		return file;
		
	}
}
