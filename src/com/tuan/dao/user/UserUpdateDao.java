package com.tuan.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tuan.entity.User;
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
	public void insertUser( String mailbox, String password, String userName ) throws ClassNotFoundException{
		
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String SQL = "INSERT INTO user(MAILBOX, PASSWORD, USERNAME) VALUES(?,?,?)";
			stat = conn.prepareStatement(SQL);
			stat.setString(1, mailbox);
			stat.setString(2, password);
			stat.setString(3, userName);
			stat.executeUpdate();
			conn.commit();
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
	
	
//<<------------------------------------------------------------------------------------------------------------------------------------------------->>//
//  用户个人信息更新方法
	
	
	/**
	 * 更新用户个人信息
	 * @param user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateUserInfo(long ID, User user) throws ClassNotFoundException{
		
		Connection conn = null;
		PreparedStatement stat = null;
		try{
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String SQL = "UPDATE user SET USERNAME=?,GENDER=?,MAILBOX=?,DESCRIPTION=?,PROVINCE=?,CITY=?,DISTRICT=?,AGE=? WHERE ID=?";
			stat = conn.prepareStatement(SQL);
			stat.setString(1, user.getUserName());
			stat.setString(2, String.valueOf(user.getGender()));
			stat.setString(3, user.getMailbox());
			stat.setString(4, user.getDescription());
			stat.setString(5, user.getProvince());
			stat.setString(6, user.getCity());
			stat.setString(7, user.getDistrict());
			stat.setInt(8, user.getAge());
			stat.setLong(9, ID);
			stat.executeUpdate();
			conn.commit();
		}catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		DBUtil.close(conn, stat);
	}
	
	
	/**
	 * 更新用户的头像
	 * @param ID
	 * @param avatorURI
	 * @throws ClassNotFoundException
	 */
	public void updateUserAvatorURI(long ID, String avatorURI) throws ClassNotFoundException{
		
		Connection conn = null;
		PreparedStatement stat = null;
		
		try{
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String SQL = "UPDATE user SET AVATORURI=? WHERE ID=?";
			stat = conn.prepareStatement(SQL);
			stat.setString(1, avatorURI);
			stat.setLong(2, ID);
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
	
	/**
	 * 更新用户的兴趣标签
	 * @param ID
	 * @param interest
	 * @throws ClassNotFoundException
	 */
	public void updateUserInterest(long ID, String interest) throws ClassNotFoundException{
		
		Connection conn = null;
		PreparedStatement stat = null;
		try{
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String SQL = "UPDATE user SET INTEREST = ? WHERE ID = ?";
			stat = conn.prepareStatement(SQL);
			stat.setString(1, interest);
			stat.setLong(2, ID);
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
}
