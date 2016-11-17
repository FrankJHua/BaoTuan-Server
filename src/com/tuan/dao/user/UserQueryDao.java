package com.tuan.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tuan.entity.User;
import com.tuan.util.DBUtil;

public class UserQueryDao {
	
	public final static String MAILBOX = "MAILBOX";
	public final static String PHONE  ="PHONE";

	
	
	/**
	 * 通过ID号登陆时
	 * 通过用户ID查询用户的密码
	 * @param Id
	 * @return 用户的密码
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String queryPasswordById(long Id) throws ClassNotFoundException, SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT PASSWORD FROM user WHERE ID = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, Id);
		ResultSet rs = stat.executeQuery();
		String queryResult = null;
		if(rs.next()){
			queryResult = rs.getString(1);
		}
		DBUtil.close(conn, stat, rs);
		return queryResult;
	}
	
	/**
	 * 通过邮箱或手机号码登陆时
	 * 根据用户的邮箱或电话号码查询密码
	 * @param specificDomain
	 * @return 用户的密码
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String queryPasswordBySpecific(String specificDomain, String value) throws ClassNotFoundException, SQLException{
		
		if(null==specificDomain || null==value) return null;
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT PASSWORD FROM user WHERE "+specificDomain+" = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, value);
		ResultSet rs = stat.executeQuery();
		String queryResult = null;
		if(rs.next()){
			queryResult = rs.getString(1);
		}
		DBUtil.close(conn, stat, rs);
		return queryResult;
	}
	
	/**
	 * 用户注册时验证邮箱的唯一性
	 * 查询注册邮箱为mailbox的用户是否已经存在
	 * @param mailbox
	 * @return	返回真假值
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isContainsUser(String mailbox) throws ClassNotFoundException, SQLException{
		
		if(null==mailbox) return true;
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT ID FROM user WHERE MAILBOX = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, mailbox);
		ResultSet rs = stat.executeQuery();
		boolean flag = false;
		if(rs.next())
			flag = true;
		DBUtil.close(conn, stat, rs);
		return flag;
	}
	
	/**
	 * 根据邮箱查询用户ID
	 * @param mailbox
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public long queryUserIdByMailbox( String mailbox ) throws ClassNotFoundException, SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT ID FROM user WHERE MAILBOX = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, mailbox);
		ResultSet rs = stat.executeQuery();
		long result = -1;
		if(rs.next()){
			result = rs.getLong(1);
		}
		DBUtil.close(conn, stat, rs);
		return result;
	}
	
	/**
	 * 根据电话号码查询用户ID
	 * @param mailbox
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public long queryUserIdByPhone( String phone ) throws ClassNotFoundException, SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT ID FROM user WHERE PHONE = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, phone);
		ResultSet rs = stat.executeQuery();
		long result = -1;
		if(rs.next()){
			result = rs.getLong(1);
		}
		DBUtil.close(conn, stat, rs);
		return result;
	}
	
	
//<<----------------------------------------------------------------------------------------------------------------------------------------------------->>//
	/**
	 **  用户个人信息 DAO
	**/
	
	/**
	 * 根据用户ID查询用户个人信息
	 * @param ID
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public User queryUser( long ID ) throws ClassNotFoundException, SQLException{
		
		User user = null;
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT * FROM user WHERE ID = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, ID);
		ResultSet rs = stat.executeQuery();
		if(rs.next()){
			user = new User();
			user.setUserName(rs.getString("USERNAME"));
			user.setMailbox(rs.getString("MAILBOX"));
			user.setPhone(rs.getString("PHONE"));
			user.setProvince(rs.getString("PROVINCE"));
			user.setCity(rs.getString("CITY"));
			user.setDistrict(rs.getString("DISTRICT"));
			user.setGender(rs.getString("GENDER").charAt(0));
			user.setAge(rs.getInt("AGE"));
			user.setDescription(rs.getString("DESCRIPTION"));
		}
		DBUtil.close(conn, stat, rs);
		return user;
	}
	
//<<----------------------------------------------------------------------------------------------------------------------------------------------------->>//
	/**
	 * 用户兴趣爱好DAO
	 */
	public String queryUserInterest(long ID)throws ClassNotFoundException,SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT INTEREST FROM user WHERE ID=?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, ID);
		ResultSet rs = stat.executeQuery();
		String result = null;
		if(rs.next()){
			result = rs.getString(1);
		}
		DBUtil.close(conn, stat, rs);
		return result;
	}
}
