package cn.gwssi.mian;




import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;


/**
 * cn.gwssi.mian
 * ConnectManager.java
 * 上午10:55:59
 * @author wuminghua
 */
public class ConnectManager {

	private static DataSource dataSource;

	/**
	 * 下列常量信息需要考虑，是否存入配置文件，由配置文件读取
	 */
	
	//日志服务器数据库地址
	private static String dbURL = "jdbc:oracle:thin:@172.30.7.129:1521:oragxk";   
	//用户名
	private static String dbUser = "db_yyjc";   
	//密码
	private static String dbPwd = "db_yyjc";  

	private static String driverClass = "oracle.jdbc.OracleDriver"; 

	static {
		Properties dbProperties = new Properties();
		try {
			//	   dbProperties.load(ConnectManager.class.getClassLoader()
			dbProperties.put("password", dbPwd);
			dbProperties.put("username", dbUser);
			dbProperties.put("url", dbURL);
			dbProperties.put("driverClassName", driverClass);
			dbProperties.put("maxActive", "11");
			dbProperties.put("maxWait", 1000);
			dataSource = BasicDataSourceFactory.createDataSource(dbProperties);
		} catch (Exception e) {
		}
	}
	private ConnectManager() {
	}
	/**
	 * 
	 * @see {@link ConnectManager#closeConn(Connection)}
	 * @return
	 */
	public static final Connection getConn() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
		}
		return conn;
	}
	/**
	 * 关闭连接
	 * 
	 * @param conn
	 *            */
	public static void closeConn(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
		}
	}
}