package com.tuan.service.user;

import java.sql.SQLException;

import com.tuan.dao.user.UserQueryDao;
import com.tuan.dao.user.UserUpdateDao;
import com.tuan.entity.StatusCode;
import com.tuan.util.MessageFactory;

/**
 * 
 * 用户兴趣标签业务类
 *
 */
public class UserInterestService {

	/**
	 * 设置用户的兴趣标签
	 * @param ID
	 * @param interest
	 * @return
	 */
	public String setUserInterest(long ID, String interest){
		
		UserUpdateDao userUpdateDao = new UserUpdateDao();
		String result = null;
		try {
			userUpdateDao.updateUserInterest(ID, interest);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.ERROR, "设置失败");
		}
		result = MessageFactory.createMessage(StatusCode.SUCCESS, "设置成功");
		return result;
	}
	
	public String getUserInterest(long ID){
		
		UserQueryDao userQueryDao = new UserQueryDao();
		String result = null;
		try {
			result = userQueryDao.queryUserInterest(ID);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.ERROR, "获取失败");
		}
		if(null!=result){
			String[] interestItems = result.split("-");
			result = MessageFactory.createMessage(StatusCode.SUCCESS, "获取成功", interestItems);
		}else{
			result = MessageFactory.createMessage(StatusCode.ERROR, "您还未设置任何兴趣标签");
		}
		return result;
	}
}
