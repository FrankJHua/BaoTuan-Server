package com.tuan.service.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tuan.dao.activity.ActivityQueryDao;
import com.tuan.dao.user.UserQueryDao;
import com.tuan.dao.user.UserUpdateDao;
import com.tuan.entity.Activity;
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
				user.setAvatorURL("http://119.29.235.81:8080/tuan/user/get-avator/"+user.getAvatorURL());
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
	public String setUserInfo(long ID,String paramName, String paramValue){
		
		UserUpdateDao userUpdateDao = new UserUpdateDao();
		String result = null;
		if(null==paramValue){  paramValue = ""; }
		try{
			if(paramName.equals("age")){
				int age = Integer.parseInt(paramValue);
				userUpdateDao.updateUserInfo(ID, paramName, age);
			}else{
				userUpdateDao.updateUserInfo(ID, paramName, paramValue);
			}
			result = MessageFactory.createMessage(StatusCode.SUCCESS, "保存成功");
		}catch(NumberFormatException e){
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.ERROR, "年龄应为自然数");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.ERROR, "保存失败");
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
	
	/**
	 * 获取用户参与的活动
	 * @param userId
	 * @return
	 */
	public String getUserJoinActivities(long userId){
		
		ActivityQueryDao activityQueryDao = new ActivityQueryDao();
		String result = null;
		try {
			List<Long> actIds = activityQueryDao.queryUserJoinActivityID(userId);
			if(null!=actIds && !actIds.isEmpty()){
				List<Activity> activities = new ArrayList<Activity>();
				UserQueryDao userQueryDao = new UserQueryDao();
				for(Long actId : actIds){
					Activity activity = activityQueryDao.queryActivityByActID(actId);
					String userAvator = userQueryDao.queryUserAvator(activity.getPublisher());
					String userAvatorUrl = "http://119.29.235.81:8080/tuan/avator/thumb_"+userAvator;
					activity.setPublisher_avator_url(userAvatorUrl);
					String publisherName = userQueryDao.queryUserName(activity.getPublisher());
					activity.setPublisherName(publisherName);
					int joinCount = activityQueryDao.queryJoinCount(activity.getID());
					activity.setJoinCount(joinCount);
					String activityCover = activity.getActivity_cover_url();
					String activityCoverUrl = "http://119.29.235.81:8080/tuan/activity/get-cover/"+activityCover;
					activity.setActivity_cover_url(activityCoverUrl);
					activities.add(activity);
				}
				result = MessageFactory.createMessage(StatusCode.SUCCESS, "获取成功",activities);
			}else{
				result = MessageFactory.createMessage(StatusCode.ERROR, "您还未参与任何活动");
			}
		} catch (ClassNotFoundException |SQLException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.ERROR, "获取失败");
		} 
		return result;
	}
}
