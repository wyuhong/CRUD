package com.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * 管理数据连接的工具类<br>
 * 开发思路：<br>
 * 1、想这么使用：ConnectionManager.getInstance().getConnection();<br>
 * 2、想释放相关资源：ConnectionManager.getInstance().free(rs,sta,conn);<br>
 * @author dhc
 * @version V1.0
 */
public class ConnectionManager {

	private static ConnectionManager cm = null;
	private static Properties pro = null;
	
	/**
	 * 私有的构造器
	 */
	private ConnectionManager() {
		try {
			//去工程的类路径下找到db.properties文件，并将该文件添加到输入流中
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("db.properties");
			//新建配置文件对象实例
			pro = new Properties();
			//通过pro加载输入流
			pro.load(is);
			//注册驱动
			Class.forName(pro.getProperty("driver"));
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * 获取当前类的实例
	 * @return
	 */
	public static ConnectionManager getInstance(){
		if(cm == null){
			synchronized (ConnectionManager.class) {
				if(cm == null){
					cm = new ConnectionManager();
				}
			}
		}
		return cm;
	}
	/**
	 * 获取数据库连接对象
	 * @return connection
	 */
	public Connection getConnection(){
		Connection conn = null;
		try {
			if(conn == null){
				conn = DriverManager.getConnection(pro.getProperty("url"), pro.getProperty("username"), pro.getProperty("pwd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return conn;
	}
	/**
	 * 释放关闭资源
	 * @param rs
	 * @param sta
	 * @param conn
	 */
	public void free(ResultSet rs , Statement sta , Connection conn){
		try {
			if(rs != null) rs.close();
			if(sta != null) sta.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
