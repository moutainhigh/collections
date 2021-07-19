package cn.gwssi.plugin.runtimemonitor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMonitor {
		
	
	
	Connection getConn() throws ClassNotFoundException, SQLException{
		Class.forName("com.sybase.jdbc3.jdbc.SybDriver");//这是连接mysql数据库的驱动
		String url="jdbc:sybase:Tds:10.1.2.110:7220/datacenter?charset=cp936";
		Connection conn = DriverManager.getConnection(url,"sa","123456");
		return conn;
	}
	
	
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		DatabaseMonitor dm=new DatabaseMonitor();
		System.out.println(dm.getConn().getMetaData());
	}
	
	
	
	
	
	
	
	
}
