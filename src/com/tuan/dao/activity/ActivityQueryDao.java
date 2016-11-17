package com.tuan.dao.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tuan.util.DBUtil;

public class ActivityQueryDao {

	/**
	 * 根据活动id查询活动是否存在
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isContains(long id) throws ClassNotFoundException,SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT ID FROM activity WHERE ID = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, id);
		ResultSet rs = stat.executeQuery();
		boolean result = false;
		if(rs.next()){
			result = true;
		}
		DBUtil.close(conn, stat, rs);
		return result;
	}
	
	/**
	 * 根据活动ID查询活动的发布者ID
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public long queryPublisherId(long id) throws ClassNotFoundException,SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT CREATOR FROM activity WHERE ID=?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, id);
		ResultSet rs = stat.executeQuery();
		long result = -1;
		if(rs.next()){
			result = rs.getLong(1);
		}
		DBUtil.close(conn, stat, rs);
		return result;
	}
	
	
	/**
	 * 查看用户是否已经参加活动
	 * @param userId
	 * @param actId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isJoinActivity(long userId, long actId) throws ClassNotFoundException,SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT ID FROM user_activity WHERE USER_ID=? AND ACT_ID=?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, userId);
		stat.setLong(2, actId);
		ResultSet rs = stat.executeQuery();
		boolean result = false;
		if(rs.next()){
			result = true;
		}
		DBUtil.close(conn, stat, rs);
		return result;
	}
}
