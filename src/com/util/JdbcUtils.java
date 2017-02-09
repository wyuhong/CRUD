package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专门用于jdbc的增删改查操作工具类<br>
 * 开发思路：<br>
 * 1、List list = JdbcUtils.query("select * from fruits");<br>
 * 2、List list = JdbcUtils.query("select * from fruits where f_name=?",new Object[]{"苹果"});<br>
 * @author dhc
 * @version V1.0
 */
public class JdbcUtils {
	
	private static Connection conn = null;
	private static PreparedStatement pst = null;
	private static ResultSet rs = null;
	
	/**
	 * 专门执行不带?占位符的普通SQL语句
	 * @param sql 普通的SQL语句
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List query(String sql){
		try {
			//通过该方法获取预编译语句对象
			getPreparedStatement(sql);
			//通过预编译语句对象的查询方法得到结果集
			rs = pst.executeQuery();
			//希望这个方法具有如下功能：rs-->List
			return ResultSetToListMap(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			ConnectionManager.getInstance().free(rs, pst, conn);
		}
	}
	/**
	 * 专门执行带?占位符的SQL语句
	 * @param sql 带占位符的SQL语句 
	 * @param params 专门给占位符提供内容的数组
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List query(String sql,Object... params){
		try {
			//通过该方法获取预编译语句对象
			getPreparedStatement(sql);
			//select * from frutis where f_name=? and f_price=? and xxxx=?
			//['a','b','c']
			for(int i=0;i<params.length;i++){
				pst.setObject(i+1, params[i]);
			}
			//通过预编译语句对象的查询方法得到结果集
			rs = pst.executeQuery();
			//希望这个方法具有如下功能：rs-->List
			return ResultSetToListMap(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			ConnectionManager.getInstance().free(rs, pst, conn);
		}
	}
	/**
	 * 专门用来结果为一行一列的查询
	 * @param sql select count(*) from fruits;
	 * @return int
	 */
	public static int querySingle(String sql){
		int i = 0;
		String str = "";
		try {
			//获取预编译语句对象
			getPreparedStatement(sql);
			//发送SQL语句查询出结果集
			rs = pst.executeQuery();
			while(rs.next()){
				str = rs.getString(1);
			}
			i = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			ConnectionManager.getInstance().free(rs, pst, conn);
		}
		return i;
	}
	
	/**
	 * 专门用来结果为一行一列的查询,携带占位符
	 * @param sql select count(*) from fruits;
	 * @return int
	 */
	public static int querySingle(String sql,Object... params){
		int i = 0;
		String str = "";
		try {
			//获取预编译语句对象
			getPreparedStatement(sql);
			for(int j=0;j<params.length;j++){
				pst.setObject(j+1, params[j]);
			}
			//发送SQL语句查询出结果集
			rs = pst.executeQuery();
			while(rs.next()){
				str = rs.getString(1);
			}
			i = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			ConnectionManager.getInstance().free(rs, pst, conn);
		}
		return i;
	}
	
	/**
	 * 专门用于执行不带占位符的普通增删改语句
	 * @param sql 增删改
	 * @return 受到影响的行数
	 */
	public static int update(String sql){
		try {
			getPreparedStatement(sql);
			return pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			ConnectionManager.getInstance().free(rs, pst, conn);
		}
	}
	
	public static int update(String sql,Object...params){
		try {
			getPreparedStatement(sql);
			for(int i=0;i<params.length;i++){
				pst.setObject(i+1, params[i]);
			}
			return pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			ConnectionManager.getInstance().free(rs, pst, conn);
		}
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List ResultSetToListMap(ResultSet rs2) {
		List list = new ArrayList();
		try {
			while(rs2.next()){
				//获取元数据（就是结果集中列字段的集合）
				//打个比方：水果表的所有列名的集合：f_id,s_id,f_name,f_price
				//md.getColumnCount(),水果表中获取到的就是4，表中有多少个列，这里就是几！
				//md.getColumnLabel(i),就是对应的列的名称
				ResultSetMetaData md = rs2.getMetaData();
				//新建一个空的map
				Map map = new HashMap();
				//为了遍历表中的某行的所有列 key=表中字段名；value=某行中某列的值
				for(int i=1;i<=md.getColumnCount();i++){
					map.put(md.getColumnLabel(i), rs2.getObject(i));
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return list;
	}

	/**
	 * 私有的静态方法获取预编译语句对象
	 * @param sql
	 */
	private static PreparedStatement getPreparedStatement(String sql) {
		try {
			//使用自己写的工具类获取数据库连接
			conn = ConnectionManager.getInstance().getConnection();
			//通过数据库连接对象获取预编译语句对象
			pst = conn.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return pst;
	}
}
