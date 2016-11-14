package com.tuan.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.tuan.entity.User;
import com.tuan.util.DBUtil;

public class UserQueryDao {
	
	public final static String MAILBOX = "MAILBOX";
	public final static String PHONE  ="PHONE";
	
	//手机号码验证
	public static final Pattern PATTERN_MOBILE = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	//邮箱号码验证
	public static final Pattern PATTERN_EMAIL = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

	/**
	 * 通过用户的ID获取用户的全部个人信息
	 * @param Id
	 * @return 用户的个人信息
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public User queryUserById(long Id) throws ClassNotFoundException, SQLException{
		
		Connection conn = DBUtil.getConnection();
		String SQL  = "SELECT * FROM user WHERE ID = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, Id);
		ResultSet rs = stat.executeQuery();
		User user = null;
		if(rs.next()){
			user = new User();
			user.setID(Id);
			user.setUserName(rs.getString("USERNAME"));
			user.setPosition(rs.getString("POSITION"));
			user.setPhone(rs.getString("PHONE"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setMailbox(rs.getString("MAILBOX"));
			user.setGender(rs.getString("GENDER").charAt(0));
			user.setBirthday(rs.getDate("BIRTHDAY"));
			user.setAvatorURI(rs.getString("AVATOR"));
			user.setAge(rs.getInt("AGE"));
		}
		DBUtil.close(conn, stat, rs);
		return user;
		
	}
	
	/**
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
		long result = 0;
		if(rs.next()){
			result = rs.getLong(1);
		}else{
			result = -1;
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
		long result = 0;
		if(rs.next()){
			result = rs.getLong(1);
		}else{
			result = -1;
		}
		DBUtil.close(conn, stat, rs);
		return result;
	}
	
	/**
	 * 查询是否已经绑定手机号码
	 * @param ID
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isBindPhone( long ID ) throws ClassNotFoundException, SQLException{
		
		boolean flag = false;
		Connection conn = DBUtil.getConnection();
		String SQL = "SELECT PHONE FROM user WHERE ID = ?";
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setLong(1, ID);
		ResultSet rs = stat.executeQuery();
		if(rs.next()){
			flag = true;
		}
		DBUtil.close(conn, stat, rs);
		return flag;
	}
}
