package com.tuan.service.user;

import java.sql.SQLException;

import com.tuan.dao.user.UserQueryDao;
import com.tuan.dao.user.UserUpdateDao;
import com.tuan.entity.StatusCode;
import com.tuan.entity.User;
import com.tuan.util.MessageFactory;
import com.tuan.util.StringUtil;

/**
 * 用户个人信息业务类
 * 个人信息的增删改查
 */
public class UserInfoService {

	/**
	 * 获取用户个人信息
	 * @param userId (手机号码或者邮箱号码)
	 * @return
	 */
	public String getUserInfo(long userId){
		
		//调用dao层接口
		UserQueryDao userQueryDao = new UserQueryDao();
		
		//开始查询
		String result = null;
		try {
			User user = userQueryDao.queryUser(userId);
			if(null!=user){
				result = MessageFactory.createMessage(StatusCode.SUCCESS, "查询个人信息成功", user);
				
			}else{
				result = MessageFactory.createMessage(StatusCode.ERROR, "获取个人信息失败");
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			result = MessageFactory.createMessage(StatusCode.ERROR, "获取个人信息失败");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 修改用户个人信息
	 * @param user
	 * @return
	 */
	public String setUserInfo(long ID,User user){
		
		UserQueryDao userQueryDao = new UserQueryDao();
		String result = null;
		
		try {
			long userId = userQueryDao.queryUserIdByMailbox(user.getMailbox());
			if(userId!=-1 && userId!=ID){
				result = MessageFactory.createMessage(StatusCode.ERROR, "该邮箱已被注册");
			}else{
				UserUpdateDao userUpdateDao = new UserUpdateDao();
				userUpdateDao.updateUserInfo(ID,user);
				result = MessageFactory.createMessage(StatusCode.SUCCESS, "修改成功");
			}
		} catch (ClassNotFoundException | SQLException e) {
			result = MessageFactory.createMessage(StatusCode.ERROR, "修改个人信息失败");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 修改用户头像
	 * @param ID
	 * @param fileName
	 * @return
	 */
	public String setUserAvator(long ID, String fileName){
		
		UserUpdateDao userUpdateDao = new UserUpdateDao();
		try {
			userUpdateDao.updateUserAvatorURI(ID, fileName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return MessageFactory.createMessage(StatusCode.ERROR, "上传失败");
		}
		return MessageFactory.createMessage(StatusCode.SUCCESS, "上传成功");
	}
	
	/**
	 * 获取用户的ID号
	 * @param userId
	 * @return
	 */
	public long getID(String userId){
		
		String matchType = StringUtil.matcher(userId);
		long ID = -1;
		UserQueryDao userQueryDao = new UserQueryDao();
		
		try {
			if(matchType.equals("PHONE")){
				ID = userQueryDao.queryUserIdByPhone(userId);
			}else{
				ID = userQueryDao.queryUserIdByMailbox(userId);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return ID;
	}
}
