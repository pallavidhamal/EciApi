package com.eci.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class GetDBConnection {

 	private static Connection  conn= null;
 	public static Properties prop = new Properties();
 	public static InputStream inputStream = GetDBConnection.class.getClassLoader().getResourceAsStream("database.properties");

 	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException{
	  
	  prop.load(inputStream);
	  
   	  String driver = prop.getProperty("driver").trim();  
   	  String url = prop.getProperty("url").trim();  
   	  
   	  String user = prop.getProperty("user").trim();  
   	  String password = prop.getProperty("password").trim();
   	  
	  
   	  Class.forName(driver);
   	  conn = DriverManager.getConnection(url,user,password);
    return conn;
 	}
 	
 	
 	public static void closeConn(Connection conn){
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception ex) {
		}
	}
	
	public static void closeCstmt(CallableStatement cstmt){
		try {
			if (cstmt != null) {
				cstmt.close();
				cstmt = null;
			}
		} catch (Exception ex) {
		}
	}
	
	public static void closeStmt(Statement stmt){
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (Exception ex) {
		}
	}
	
	public static void closeRS(ResultSet rs){
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (Exception ex) {
		}
	}
			
	public static void closePstmt(PreparedStatement ps){
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (Exception ex) {
		}
	}
}
