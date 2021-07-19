package com.yunpan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 
 * 
 * ConnectionUtil<BR>
 * 创建人:潭州学院-keke <BR>
 * 时间：2014年11月23日-上午12:59:01 <BR>
 * @version 1.0.0
 *
 */
public class ConnectionUtil {
	
	
	/**
	 * 数据库连接类
	 * 方法名：getConnection<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月23日-上午1:01:13 <BR>
	 * @return Connection<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static Connection getConnection(){
		Connection connection = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/yunpan", "root","xiaoer");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 关闭连接 方法名：closeConnection<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月9日-下午10:57:52 <BR>
	 * 
	 * @param connection
	 *            void<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接 方法名：closeConnection<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月9日-下午10:57:52 <BR>
	 * 
	 * @param connection
	 *            void<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static void closeResultset(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接 方法名：closeConnection<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月9日-下午10:57:52 <BR>
	 * 
	 * @param connection
	 *            void<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static void closeStatement(PreparedStatement pStatement) {
		try {
			pStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
