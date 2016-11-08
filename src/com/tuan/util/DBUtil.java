package com.tuan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DBUtil {

	private static String DBdirver;
	private static String DBurl;
	private static String DBuser;
	private static String DBpassword;
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
			
		Connection conn = null;
		
		Map<String,String> configs = DBConfig.getDatabaseConfig();
		
		DBdirver = configs.get("driver");
		DBurl = configs.get("url");
		DBuser = configs.get("user");
		DBpassword = configs.get("password");
		
		Class.forName(DBdirver);
		
		conn = DriverManager.getConnection(DBurl, DBuser, DBpassword);
		
		return conn;
	}
	
	public static void close( Connection conn, PreparedStatement stat ){
		
		try {
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("状态集关闭错误");
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库连接关闭错误");
		}
		
	}
	
	public static void close( Connection conn, PreparedStatement stat, ResultSet rs ){
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("资源集关闭错误");
		}
		
		try {
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("状态集关闭错误");
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库连接关闭错误");
		}
	}
	
}
