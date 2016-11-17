package com.tuan.service.activity;

import java.sql.SQLException;
import java.util.List;

import com.tuan.dao.activity.ActivitySearchDao;
import com.tuan.dao.user.UserQueryDao;
import com.tuan.entity.Activity;
import com.tuan.entity.StatusCode;
import com.tuan.util.MessageFactory;

public class ActivitySearchService {

	public String searchActivityByKeyword(String keyword, int pageNumber){
		
		int searchOffset = (pageNumber-1)*5;
		ActivitySearchDao activitySearchDao = new ActivitySearchDao();
		String result = null;
		List<Activity> data = null;;
		try {
			data = activitySearchDao.searchActivityByKeyword(keyword,searchOffset);
			UserQueryDao userQueryDao = new UserQueryDao();
			for(Activity activity : data){
				String userAvator = userQueryDao.queryUserAvator(activity.getPublisher());
				String userAvatorUrl = "http://119.29.235.81:8080/tuan/avator/thumb_"+userAvator;
				activity.setPublisher_avator_url(userAvatorUrl);
				String activityCover = activity.getActivity_cover_url();
				String activityCoverUrl = "http://119.29.235.81:8080/tuan/activity/get-cover/"+activityCover;
				activity.setActivity_cover_url(activityCoverUrl);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			result = MessageFactory.createMessage(StatusCode.ERROR, "搜索失败");
			return result;
		}
		
		if(null!=data && !data.isEmpty()){
			result = MessageFactory.createMessage(StatusCode.SUCCESS, "搜索成功",data);
		}else{
			result = MessageFactory.createMessage(StatusCode.ERROR, "找不到更多活动了"); 
		}
		return result;
	}
}
