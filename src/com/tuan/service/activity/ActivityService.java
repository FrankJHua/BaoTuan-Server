package com.tuan.service.activity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.tuan.dao.activity.ActivityQueryDao;
import com.tuan.dao.activity.ActivityUpdateDao;
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
	public String activityPublish(long userId, Activity activity){
		
		long activityId = -1;
		try {
			//这里以后会检查用户是否已经绑定了手机号码(绑定之后才可以发布活动)
			//
			//
			
			if(userId==-1){
				return MessageFactory.createMessage(StatusCode.ERROR, "用户不存在");
			}else{
				activity.setPublisher(userId);
				ActivityUpdateDao activityUpdateDao = new ActivityUpdateDao();
				activityId = activityUpdateDao.insertActivity(activity);
				activityUpdateDao.joinActivity(userId, activityId);
			}
		} catch (Exception e) {
			return MessageFactory.createMessage(StatusCode.ERROR, "活动发布失败");
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
	
	/**
	 * 参加活动业务
	 * @param userId
	 * @param actId
	 * @return
	 */
	public String joinAvtivity(long userId, long actId){
		
		ActivityQueryDao activityQueryDao = new ActivityQueryDao();
		String result = null;
		try {
			boolean activityExist = activityQueryDao.isContains(actId);
			if(activityExist){
				boolean isJoin = activityQueryDao.isJoinActivity(userId, actId);
				if(isJoin){
					result = MessageFactory.createMessage(StatusCode.ERROR, "您已参加该活动");
				}else{
					ActivityUpdateDao activityUpdateDao = new ActivityUpdateDao();
					activityUpdateDao.joinActivity(userId, actId);	
					result = MessageFactory.createMessage(StatusCode.SUCCESS, "成功参加活动");
				}
			}else{
				result = MessageFactory.createMessage(StatusCode.ERROR, "该活动不存在");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.ERROR, "参加活动失败");
		}
		return result;
	}
}
