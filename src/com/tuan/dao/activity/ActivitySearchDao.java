package com.tuan.dao.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tuan.entity.Activity;
import com.tuan.util.DBUtil;

/**
 * 
 * 搜索活动的DAO
 */
public class ActivitySearchDao {

	public List<Activity> searchActivityByKeyword(String keyword, int searchOffset) 
			throws ClassNotFoundException,SQLException{
		
		String key = "%"+keyword+"%";
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT * FROM activity WHERE ACT_NAME LIKE ? OR ACT_TYPE LIKE ? OR DESCRIPTION LIKE ? LIMIT ?,5";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, key);
		stat.setString(2, key);
		stat.setString(3, key);
		stat.setInt(4, searchOffset);
		ResultSet rs = stat.executeQuery();
		List<Activity> searchResults = new ArrayList<Activity>();
		while(rs.next()){
			Activity activity = new Activity();
			activity.setID(rs.getLong("ID"));
			activity.setPublisher(rs.getLong("CREATOR"));
			activity.setName(rs.getString("ACT_NAME"));
			activity.setType(rs.getString("ACT_TYPE"));
			activity.setTime(rs.getDate("ACT_TIME"));
			activity.setProvince(rs.getString("ACT_PROVINCE"));
			activity.setCity(rs.getString("ACT_CITY"));
			activity.setDistrict(rs.getString("ACT_DISTRICT"));
			activity.setPosition(rs.getString("POSITION"));
			activity.setNumber(rs.getInt("PEOPLE_NUMBER"));
			activity.setFee(rs.getFloat("ACT_FEE"));
			activity.setDescription(rs.getString("DESCRIPTION"));
			activity.setActivity_cover_url(rs.getString("ACT_PIC"));
			searchResults.add(activity);
		}
		DBUtil.close(conn, stat, rs);
		return searchResults;
	}
	
	
}
