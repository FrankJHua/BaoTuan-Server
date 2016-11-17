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

import com.tuan.dao.CacheDao;
import com.tuan.entity.StatusCode;
import com.tuan.service.user.UserInfoService;
import com.tuan.util.FileUtil;
import com.tuan.util.MessageFactory;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 
 * 用户头像上传
 *
 */
public class UserAvatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserAvatorServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取用户ID
		String token = request.getHeader("access-token");
		CacheDao cache = new CacheDao();
		long ID = Long.parseLong(cache.get(token));

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		fileUpload.setFileSizeMax(3*1024*1024);
		
		String result = "";
		
		//开始接受头像文件
		try {
			
			List<FileItem> fileItems = fileUpload.parseRequest(request);
			
			for(FileItem fileItem : fileItems){
				//判断是否是文件域
				if(!fileItem.isFormField()){
					//获取文件名
					String fileName = fileItem.getName();
					System.out.println(fileName);
					//检查是否是支持的图片文件格式
					boolean isImage = FileUtil.checkImageFileName(fileName);
					if(isImage){
						//生成新的文件名，防止不同用的文件名冲突
						String filePortfix = FileUtil.getFilePortfix(fileName);
						String newFileName = FileUtil.createFileNameBySystemTime(filePortfix);
						
						//在数据库中更新用户图片存储路径
						UserInfoService service = new UserInfoService();
						result = service.setUserAvator(ID, newFileName);
						//保存图片
						File avator = createImage(newFileName);
						fileItem.write(avator);
						//生成缩略图
						File thumbAvator = createThumbImage("thumb_" + newFileName);
						Thumbnails.of(avator).size(50, 50).toFile(thumbAvator);
					}else{
						result = MessageFactory.createMessage(StatusCode.ERROR, "不支持该图片的格式");
					}
					
				}
			}
		}catch (FileSizeLimitExceededException e) {
			result = MessageFactory.createMessage(StatusCode.ERROR, "图片大小超过3M");
		}catch (Exception e){
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
		File avatorFile = new File(basePath, fileName);
		return avatorFile;
	}
	
	private File createThumbImage(String fileName){
		String thumbBasePath = getServletConfig().getInitParameter("thumbBasePath");
		File thumbAvatorFile = new File(thumbBasePath,fileName);
		return thumbAvatorFile;
	}
}
