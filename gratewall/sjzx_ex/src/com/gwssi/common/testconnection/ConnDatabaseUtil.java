package com.gwssi.common.testconnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


import utils.system;


public class ConnDatabaseUtil
{

	/**
	 * 
	 * testOracleConn(测试oracle数据库的连接) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程
	 * – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:连接成功;false:连接失败 boolean
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public boolean testOracleConn(String url, String user, String password)
	{
       long begin=System.currentTimeMillis();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		} catch (InstantiationException e) {

			System.out.println("实例化oracle驱动异常");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("非法访问oracle驱动异常");
			return false;
		} catch (ClassNotFoundException e) {

			System.out.println("没有找到oracle驱动类异常");
			return false;
		}
        Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			System.out.println("获得连接异常 ");
			return false;
		}finally{
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 
	 * testDB2Conn(测试db2数据库的连接) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:连接成功;false:连接失败 boolean
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public boolean testDB2Conn(String url, String user, String password)
	{

		try {
			// Class.forName("COM.ibm.db2.jdbc.net.DB2Driver").newInstance();
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (InstantiationException e) {

			System.out.println("实例化oracle驱动异常");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("非法访问oracle驱动异常");
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("没有找到db2驱动类异常");
			return false;
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("连接");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("获得连接异常 ");
			return false;
		}finally{
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * getDB2Conn(获取db2连接)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param url
	 * @param user
	 * @param password
	 * @return        
	 * Connection       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public Connection getDB2Conn(String url,String user,String password){
		try {
			// Class.forName("COM.ibm.db2.jdbc.net.DB2Driver").newInstance();
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (InstantiationException e) {

			System.out.println("实例化oracle驱动异常");
			
		} catch (IllegalAccessException e) {

			System.out.println("非法访问oracle驱动异常");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("没有找到db2驱动类异常");
			
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("db2ttrue");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("获得连接异常 ");
			
		}
		
		return conn;
		
	}

	/**
	 * 
	 * testSqlServer2000Conn(测试SqlServer2005数据库的连接状态) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:连接成功;false:连接失败 boolean
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public boolean testSqlServer2000Conn(String url, String user,
			String password)
	{
		/*
		 * sql server 2000中加载驱动和URL路径的语句 String driverName =
		 * "com.microsoft.jdbc.sqlserver.SQLServerDriver"; String dbURL =
		 * "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sample"; sql
		 * server 2005中加载驱动和URL的语句为： String driverName =
		 * "com.microsoft.sqlserver.jdbc.SQLServerDriver"; String dbURL =
		 * "jdbc:sqlserver://localost:1433;DatabaseName=sample";
		 */

		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver")
					.newInstance();// sql server 2000
			// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();//sql
			// server 2005
			System.out.println("SqlServer驱动加载成功");
		} catch (InstantiationException e) {

			System.out.println("实例化SqlServer驱动异常");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("非法访问SqlServer驱动异常");
			return false;
		} catch (ClassNotFoundException e) {

			System.out.println("没有找到SqlServer驱动类异常");
			return false;
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			System.out.println("获得连接异常 ");
			return false;
		}finally{
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * testSqlServer2005Conn(测试SqlServer2005数据库的连接状态) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:连接成功;false:连接失败 boolean
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public boolean testSqlServer2005Conn(String url, String user,
			String password)
	{
		/*
		 * sql server 2000中加载驱动和URL路径的语句 String driverName =
		 * "com.microsoft.jdbc.sqlserver.SQLServerDriver"; String dbURL =
		 * "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sample"; sql
		 * server 2005中加载驱动和URL的语句为： String driverName =
		 * "com.microsoft.sqlserver.jdbc.SQLServerDriver"; String dbURL =
		 * "jdbc:sqlserver://localost:1433;DatabaseName=sample";
		 */

		try {
			// Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();//sql
			// server 2000
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
					.newInstance();// sql server 2005
		} catch (InstantiationException e) {

			System.out.println("实例化SqlServer驱动异常");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("非法访问SqlServer驱动异常");
			return false;
		} catch (ClassNotFoundException e) {

			System.out.println("没有找到SqlServer驱动类异常");
			return false;
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			System.out.println("获得连接异常 ");
			return false;
		}finally{
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Connection getSqlServer2005Conn(String url, String user,String password)
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
					.newInstance();// sql server 2005
		} catch (InstantiationException e) {
			System.out.println("实例化SqlServer驱动异常");
		} catch (IllegalAccessException e) {
			System.out.println("非法访问SqlServer驱动异常");
		} catch (ClassNotFoundException e) {
			System.out.println("没有找到SqlServer驱动类异常");
			
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("获得连接异常 ");
		}
		return conn;
	}
	/**
	 * 
	 * testMySqlConn(测试MySql数据库的连接状态) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程
	 * – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:连接成功;false:连接失败 boolean
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public boolean testMySqlConn(String url, String user, String password)
	{

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("MySql驱动加载成功");
		} catch (InstantiationException e) {

			System.out.println("实例化MySql驱动异常");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("非法访问MySql驱动异常");
			return false;
		} catch (ClassNotFoundException e) {

			System.out.println("没有找到MySql驱动类异常");
			return false;
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			System.out.println("获得连接异常 ");
			return false;
		}finally{
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * getMySqlConn(获取MySql连接)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param url
	 * @param user
	 * @param password
	 * @return        
	 * boolean       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public Connection getMySqlConn(String url, String user, String password)
	{

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("MySql驱动加载成功");
		} catch (InstantiationException e) {

			System.out.println("实例化MySql驱动异常");
			
		} catch (IllegalAccessException e) {

			System.out.println("非法访问MySql驱动异常");
			
		} catch (ClassNotFoundException e) {

			System.out.println("没有找到MySql驱动类异常");
			
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("获得连接异常 ");
		}
		return conn;
	}
	
	/**
	 * 
	 * getOracleConnection(获取oracle连接)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException        
	 * Connection       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public Connection getOracleConnection(String url, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Connection conn = null;
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取连接失败");
		}
		return conn;
		
	}
	
	public void closeConnection(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("关闭连接失败!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * getOracleData(oracle中获取用户下的所有表信息)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param url
	 * @param user
	 * @param password        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void getOracleUserTableDatas(String url, String user, String password){
		
		try {
			Connection conn = getOracleConnection(url,user,password);//数据库连接
			Statement stmt = null;
			ResultSet rs = null;
			
			
			try {
				conn = DriverManager.getConnection(url, user, password);
				DatabaseMetaData dbmd = conn.getMetaData();
				String schema = dbmd.getUserName();
				rs = dbmd.getTables(null, null,"%",new String[]{"TABLE"});
				
				while(rs.next()){
				String tableName = rs.getString("TABLE_NAME");
				String tableType = rs.getString("TABLE_TYPE");
				System.out.println(tableName+"--------"+tableType);
			}
//				stmt = conn.createStatement();
//				String sql = "select table_name from all_tab_comments where owner = '"+user.toUpperCase()+"' and table_type = 'TABLE'";
//				rs = stmt.executeQuery(sql);
//				while(rs.next()){
//					String tableName = rs.getString(1);
//					//System.out.println("11tableName=="+tableName);
//				}
//				
				rs.close();
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("建立连接错误!");
				e.printStackTrace();
			}
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return null;
	}
	
	/**
	 * 
	 * getOracleUserTableDatas(获取表的字段信息)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param url
	 * @param user
	 * @param password
	 * @param tablename        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public String getOracleUserTableColumns(String url, String user, String password,String tablename){
		
		StringBuffer columns = new StringBuffer("");
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			Connection conn = getOracleConnection(url,user,password);//数据库连接
			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				conn = DriverManager.getConnection(url, user, password);
				stmt = conn.createStatement();
				//String sql = "select table_name from all_tab_comments where owner = '"+user.toUpperCase()+"' and table_type = 'TABLE'";
				String sql = "select column_id, column_name, data_type, data_length, data_precision, data_scale,nullable,data_default from user_tab_columns where table_name = '"+tablename.toUpperCase()+"' order by column_id";
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					String column_name = rs.getString(2);
					//System.out.println("11column_name=="+column_name);
					if ( !columns.toString().equals("")){
						columns.append(","+column_name);
					}else{
						columns.append(column_name);
					}
				}
				
				
				
				rs.close();
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("建立连接错误!");
				e.printStackTrace();
			}
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("字段名称="+columns.toString());
		return columns.toString();
	}
	
	/**
	 * 
	 * getConn(获取连接)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param db_type：数据库类型
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @return        
	 * Connection       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public Connection getConn(String db_type,String url,String db_username,String db_password){
		Connection conn = null;
		if(db_type!=null&&!db_type.equals("")){
			if("00".equals(db_type))//oracle
			{
				try {
					conn = getOracleConnection(url,db_username,db_password);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if("01".equals(db_type))//db2
			{
				conn = getDB2Conn(url,db_username,db_password);
				
			}else if("02".equals(db_type))//sql server
			{
				conn = getSqlServer2005Conn(url,db_username,db_password);
			}else if("03".equals(db_type))//mysql
			{
				conn = getMySqlConn(url,db_username,db_password);
				
			}else if("04".equals(db_type)){
				
				ConnAccess ca = new ConnAccess();
				conn = ca.getConnection(url, db_username, db_password);
				
			}else{
				
			}
		}
		return conn;
	}
	
	/**
	 * 
	 * getTableName(以HashMap形式返回表名称)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param db_type
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @return        
	 * HashMap<Integer,String>       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public HashMap<Integer, String>  getTableName(String db_type,String url,String db_username,String db_password){
		HashMap<Integer, String> tableName = new HashMap<Integer, String>();
		Connection conn = getConn(db_type,url,db_username,db_password);
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(url,db_username,db_password);
			DatabaseMetaData dbmd = conn.getMetaData();
			//String schema = dbmd.getUserName();
			if("04".equals(db_type)||"01".equals(db_type)){
				rs = dbmd.getTables(null, null, "%", new String[]{"TABLE"});
			/*}else if ("01".equals(db_type)){
				rs = dbmd.getTables(null, null,"%",new String[]{"TABLE"});*/
			}else{
				rs = dbmd.getTables(null, db_username.toUpperCase(),"%",new String[]{"TABLE"});
				
			}
			
			int num = 0;
			while(rs.next()){
				String name = rs.getString("TABLE_NAME");
				System.out.println("name="+name);
				tableName.put(num, name);
				num++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null)
					rs.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
		
		return tableName;
	}
	
	/**
	 * 
	 * getTableColumnName(以HashMap形式返回表的字段信息名称)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param db_type
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @param tablename
	 * @return        
	 * HashMap<Integer,String>       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public HashMap<Integer, String>  getTableColumnName(String db_type,String url,String db_username,String db_password,String tablename){
		HashMap<Integer, String> columnName = new HashMap<Integer, String>();
		Connection conn = getConn(db_type,url,db_username,db_password);
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(url,db_username,db_password);
			DatabaseMetaData dbmd = conn.getMetaData();
			
			if(db_type.equals("04")||"01".equals(db_type)){
				rs = dbmd.getColumns(null, null, tablename.toUpperCase(), "%");
			}else{
				rs = dbmd.getColumns(null, db_username.toUpperCase(), tablename.toUpperCase(), "%");
			}
			
			int num = 0;
			while(rs.next()){
				String name = rs.getString("COLUMN_NAME");
				columnName.put(num, name);
				num++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
				try {
					if(rs!=null)
						rs.close();
					if(conn!=null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		
		return columnName;
	}
	
	/**
	 * 
	 * getColumnNameAndType(获取表的字段名称和类型)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param db_type
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @param tablename
	 * @return        
	 * HashMap<String,String>       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public HashMap<String, String>  getColumnNameAndType(String db_type,String url,String db_username,String db_password,String tablename){
		HashMap<String, String> columnName = new HashMap<String, String>();
		Connection conn = getConn(db_type,url,db_username,db_password);
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(url,db_username,db_password);
			DatabaseMetaData dbmd = conn.getMetaData();
			
			if(db_type.equals("04")||db_type.equals("01")){
				rs = dbmd.getColumns(null, null, tablename.toUpperCase(), "%");
			}else{
				rs = dbmd.getColumns(null, db_username.toUpperCase(), tablename.toUpperCase(), "%");
			}
			//rs = dbmd.getColumns(null, db_username.toUpperCase(), tablename.toUpperCase(), "%");
			
			while(rs.next()){
				String name = rs.getString("COLUMN_NAME");
				String type = rs.getString("TYPE_NAME");
				columnName.put(name, type);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null)
					rs.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
		
		return columnName;
	}
	
	
	public void  getDB2(String db_type,String url,String db_username,String db_password,String tablename){
		HashMap<Integer, String> columnName = new HashMap<Integer, String>();
		Connection conn = getConn(db_type,url,db_username,db_password);
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		
		
		if("01".equals(db_type)){
			sql = "select * from SKS.youtable";
		}else{
			//sql = "select "+colSql+" from "+souTable;
		}
		//sql = "";
		System.out.println("sql="+sql);
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				
				System.out.println("rs.getString(1)=="+rs.getString(1));
				System.out.println("rs.getString(2)=="+rs.getString(2));
				System.out.println("rs.getString(3)=="+rs.getString(3));
					
					/*if((desColumns.containsValue(tmpKey))&&(bigNumType.indexOf(souColumnsType.get(tmpKey).toUpperCase())<0)){
						if (insertCol == null
								|| insertCol.toString().equals("")) {
							insertCol.append(tmpKey);
							
							if(tmpValue!=null){
								if("null".equals(tmpValue))
									tmpValue="";
							}else{
								tmpValue = "";
							}
							
							insertVal.append("'" + tmpValue+ "'");
						} else {
							insertCol.append("," + tmpKey);
							System.out.println("1111tmpValue="+tmpValue);
							if(tmpValue!=null){
								if("null".equals(tmpValue))
									tmpValue="";
							}else{
								tmpValue = "";
							}
							System.out.println("2222tmpValue="+tmpValue);
							insertVal.append(",'" + tmpValue+ "'");
						}*/
				//	}
					
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * main(这里用一句话描述这个方法的作用) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param args
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		ConnDatabaseUtil cd = new ConnDatabaseUtil();
		/*System.out.println("oracle连接测试结果："
				+ cd.testOracleConn("jdbc:oracle:thin:@172.30.7.244:1522:orcl",
						"zxk", "zxk"));*/
		//cd.getOracleUserTableDatas("jdbc:oracle:thin:@172.30.7.244:1521:orcl","zxk", "zxk");
		//db_type=01 url=jdbc:db2://172.30.18.60:50000/sampledb_username=db2admin db_password=db2admin
		cd.getDB2("01", "jdbc:db2://172.30.18.60:50000/sample","db2admin", "db2admin","YOUTABLE");
		//cd.getTableName("01", "jdbc:db2://172.30.18.60:50000/sample","db2admin", "db2admin");
		//cd.getColumnNameAndType("01", "jdbc:db2://172.30.18.60:50000/sample","db2admin", "db2admin","youtable");
		//cd.getTableColumnName("00", "jdbc:oracle:thin:@172.30.7.244:1521:orcl","zxk", "zxk","sys_rd_table");
		//getColumnNameAndType
		//ca.getColumnName("172.30.18.60","access","dwn","dwn","student");jdbc:rmi://172.30.18.60/jdbc:odbc:access
		//cd.getColumnNameAndType("00", "jdbc:oracle:thin:@172.30.7.244:1521:orcl","zxk", "zxk","sys_rd_table");
		//cd.getColumnNameAndType("04", "jdbc:rmi://172.30.18.60/jdbc:odbc:access","zxk", "zxk","student");
		//cd.getOracleUserTableColumns("jdbc:oracle:thin:@172.30.7.244:1521:orcl","zxk", "zxk","sys_rd_table");
		
		// System.out.println("db2连接测试结果1："+cd.testDB2Conn("jdbc:db2://172.30.7.189:50000/newxcdb","db2inst2","1.password.1"));//newxcdb为数据库名

		/*
		 * sql server 2000中加载驱动和URL路径的语句 String driverName =
		 * "com.microsoft.jdbc.sqlserver.SQLServerDriver"; String dbURL =
		 * "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sample"; sql
		 * server 2005中加载驱动和URL的语句为： String driverName =
		 * "com.microsoft.sqlserver.jdbc.SQLServerDriver"; String dbURL =
		 * "jdbc:sqlserver://localost:1433;DatabaseName=sample";
		 */
		// sqlserver2005
		// System.out.println("sqlServer连接测试结果："+cd.testSqlServer2005Conn("jdbc:sqlserver://172.30.7.18:1433;DatabaseName=mydb","zxk","zxk"));//mydb为数据库名
		// sqlserver2005
		// System.out.println("sqlServer连接测试结果："+cd.testSqlServer2000Conn("jdbc:microsoft:sqlserver://172.30.7.18:1433;DatabaseName=mydb","zxk","zxk"));//mydb为数据库名
		// System.out.println("MySql连接测试结果："+cd.testMySqlConn("jdbc:mysql://172.30.18.29:3306/wjdc","root","root"));//mydb为数据库名

	}
}
