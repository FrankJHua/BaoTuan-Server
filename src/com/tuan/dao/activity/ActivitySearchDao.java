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

	/**
	 * 关键字搜索活动 
	 * @param keyword
	 * @param searchOffset
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	
	/**
	 * 活动类型搜索活动
	 * @param actType
	 * @param feeLower
	 * @param feeUpper
	 * @param peopleLower
	 * @param peopleUpper
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Activity> searchActivityByType(String actType, float feeLower, float feeUpper, 
			int peopleLower, int peopleUpper, int offset) throws ClassNotFoundException, SQLException{
		
		String SQL = "SELECT * FROM activity WHERE (ACT_TYPE LIKE ?) ";
		if(feeUpper==-1){
			feeUpper = Integer.MAX_VALUE;
		}
		if(peopleUpper==-1){
			peopleUpper = Integer.MAX_VALUE;
		}
		SQL = SQL + " AND (ACT_FEE BETWEEN ? AND ?) AND (PEOPLE_NUMBER BETWEEN ? AND ?) LIMIT ?,5";
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, "%"+actType+"%");
		stat.setFloat(2, feeLower);
		stat.setFloat(3, feeUpper);
		stat.setInt(4, peopleLower);
		stat.setInt(5, peopleUpper);
		stat.setInt(6, offset);
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
	
//<<------------------------------------------------------------------------------------------------------------------------------------------------------>>//
//
//      暂定代码： 活动广场数据获取
//
	
	
}
