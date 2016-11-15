package com.tuan.service.activity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.tuan.dao.activity.ActivityQueryDao;
import com.tuan.dao.activity.ActivityUpdateDao;
import com.tuan.dao.user.UserQueryDao;
import com.tuan.entity.Activity;
import com.tuan.entity.StatusCode;
import com.tuan.util.MessageFactory;

public class ActivityService {

	//手机号码验证
	public static final Pattern PATTERN_MOBILE = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	//邮箱号码验证
	public static final Pattern PATTERN_EMAIL = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	
	/**
	 * 发布活动业务
	 * @param userId
	 * @param activity
	 * @return
	 */
	public String activityPublish(String userId, Activity activity){
		
		UserQueryDao userQueryDao = new UserQueryDao();
		long ID = -1;
		long activityId = -1;
		try {
			if (PATTERN_EMAIL.matcher(userId).matches()) {
				ID = userQueryDao.queryUserIdByMailbox(userId);
			} else if (PATTERN_MOBILE.matcher(userId).matches()) {
				ID = userQueryDao.queryUserIdByPhone(userId);
			}
		
			//这里以后会检查用户是否已经绑定了手机号码(绑定之后才可以发布活动)
			//
			//
			
			if(ID==-1){
				return MessageFactory.createMessage(StatusCode.ERROR, "用户不存在");
			}else{
				activity.setPublisher(ID);
				ActivityUpdateDao activityUpdateDao = new ActivityUpdateDao();
				activityId = activityUpdateDao.insertActivity(activity);
			}
		} catch (Exception e) {
			return MessageFactory.createMessage(StatusCode.SERVER_ERROR, "服务端发生错误");
		}
		Map<String, Long> data = new HashMap<String, Long>();
		data.put("act_id", activityId);
		return MessageFactory.createMessage(StatusCode.SUCCESS, "活动发布成功",data);
	}
	
	/**
	 * 发布活动封面业务
	 * @param activityId
	 * @param fileName
	 * @return
	 */
	public String setActivityCover(long activityId, String fileName){
		
		ActivityQueryDao activityQueryDao = new ActivityQueryDao();
		boolean isContain = false;
		String result = null;
		
		try {
			isContain = activityQueryDao.isContains(activityId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.SERVER_ERROR, "服务端发生错误");
			return result;
		}
		
		if (!isContain) {
			result = MessageFactory.createMessage(StatusCode.ERROR, "该活动ID不存在");
			return result;
		}
		
		ActivityUpdateDao activityUpdateDao = new ActivityUpdateDao();
		try {
			activityUpdateDao.updateActivityCover(activityId, fileName);
			result = MessageFactory.createMessage(StatusCode.SUCCESS, "成功上传活动封面");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.SERVER_ERROR, "服务端发生错误");
		}
		return result;
	}
}
