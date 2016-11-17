package com.tuan.dao.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tuan.entity.Activity;
import com.tuan.util.DBUtil;

public class ActivityUpdateDao {

	/**
	 * 添加新活动
	 * @param activity
	 * @return
	 * @throws ClassNotFoundException
	 */
	public long insertActivity( Activity activity ) throws ClassNotFoundException{
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		long activityId = -1;
		try {
			conn = DBUtil.getConnection();
			String SQL = "INSERT INTO activity"
					+ "(ACT_NAME,ACT_PROVINCE,ACT_CITY,ACT_DISTRICT,POSITION,ACT_TIME,PEOPLE_NUMBER,ACT_FEE,CREATOR,ACT_TYPE,DESCRIPTION)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			stat = conn.prepareStatement(SQL);
			stat.setString(1, activity.getName());
			stat.setString(2, activity.getProvince());
			stat.setString(3, activity.getCity());
			stat.setString(4, activity.getDistrict());
			stat.setString(5, activity.getPosition());
			stat.setTimestamp(6, new java.sql.Timestamp(activity.getTime().getTime()));
			stat.setInt(7, activity.getNumber());
			stat.setFloat(8, activity.getFee());
			stat.setLong(9, activity.getPublisher());
			stat.setString(10, activity.getType());
			stat.setString(11, activity.getDescription());
			conn.setAutoCommit(false);
			stat.executeUpdate();
			conn.commit();
			stat = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			rs = stat.executeQuery();
			if(rs.next()){
				activityId = rs.getLong(1);
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtil.close(conn, stat, rs);
		}
		return activityId;
	}
	
	/**
	 * 更新活动封面
	 * @param ID
	 * @param fileName
	 * @throws ClassNotFoundException
	 */
	public void updateActivityCover(long ID, String fileName) throws ClassNotFoundException{
		
		Connection conn = null;
		PreparedStatement stat = null;
		try{
			conn = DBUtil.getConnection();
			String SQL = "UPDATE activity SET ACT_PIC = ? WHERE ID = ?";
			stat = conn.prepareStatement(SQL);
			stat.setString(1, fileName);
			stat.setLong(2, ID);
			conn.setAutoCommit(false);
			stat.executeUpdate();
			conn.commit();
		}catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtil.close(conn, stat);
		}
	}
	
	
	/**
	 * 插入用户参加活动记录
	 * @param userId
	 * @param actId
	 * @throws ClassNotFoundException
	 */
	public void joinActivity(long userId, long actId) throws ClassNotFoundException{
		
		Connection conn = null;
		PreparedStatement stat = null;
		try{
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String SQL = "INSERT INTO user_activity(USER_ID,ACT_ID) VALUES(?,?)";
			stat = conn.prepareStatement(SQL);
			stat.setLong(1, userId);
			stat.setLong(2, actId);
			stat.executeUpdate();
			conn.commit();
		}catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
