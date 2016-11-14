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
}
