package com.tuan.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tuan.util.DBUtil;

public class UserUpdateDao {

	/**
	 * 插入一个新用户
	 * @param mailbox
	 * @param userName
	 * @param password
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void insertUser( String mailbox, String password ) throws ClassNotFoundException{
		
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String SQL = "INSERT INTO user(MAILBOX, PASSWORD, GENDER) VALUES(?,?,?)";
			stat = conn.prepareStatement(SQL);
			stat.setString(1, mailbox);
			stat.setString(2, password);
			stat.setString(3, "男");
			stat.executeUpdate();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("tracisition rollback");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("rollback error");
			}
		}
		DBUtil.close(conn, stat);
	}
	
	
	
}
