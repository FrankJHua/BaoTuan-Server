package com.tuan.dao.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tuan.entity.Activity;
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
	
	/**
	 * 查询活动的参加人数
	 * @param activityId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int queryJoinCount(long activityId) throws ClassNotFoundException, SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT COUNT(*) FROM user_activity WHERE ACT_ID = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, activityId);
		ResultSet rs = stat.executeQuery();
		int count = 0;
		if(rs.next()){
			count = rs.getInt(1);
		}
		DBUtil.close(conn, stat, rs);
		return count;
	}
	
	/**
	 * 查询用户参与的活动号
	 * @param userId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Long> queryUserJoinActivityID(long userId) throws ClassNotFoundException, SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT ACT_ID FROM user_activity WHERE USER_ID = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, userId);
		ResultSet rs = stat.executeQuery();
		List<Long> result = new ArrayList<Long>();
		while(rs.next()){
			result.add(rs.getLong("ACT_ID"));
		}
		DBUtil.close(conn, stat, rs);
		return result;
	}
	
	/**
	 * 根据活动ID获得活动信息
	 * @param actId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Activity queryActivityByActID(long actId) throws ClassNotFoundException, SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT * FROM activity WHERE ID = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, actId);
		ResultSet rs = stat.executeQuery();
		Activity act = null;
		while(rs.next()){
			act = new Activity();
			act.setID(rs.getLong("ID"));
			act.setTime(rs.getDate("ACT_TIME"));
			act.setType(rs.getString("ACT_TYPE"));
			act.setPublisher(rs.getLong("CREATOR"));
			act.setProvince(rs.getString("ACT_PROVINCE"));
			act.setPosition(rs.getString("POSITION"));
			act.setNumber(rs.getInt("PEOPLE_NUMBER"));
			act.setName(rs.getString("ACT_NAME"));
			act.setFee(rs.getFloat("ACT_FEE"));
			act.setDistrict(rs.getString("ACT_DISTRICT"));
			act.setDescription(rs.getString("DESCRIPTION"));
			act.setCity(rs.getString("ACT_CITY"));
			act.setActivity_cover_url(rs.getString("ACT_PIC"));
		}
		DBUtil.close(conn, stat, rs);
		return act;
	}
}
