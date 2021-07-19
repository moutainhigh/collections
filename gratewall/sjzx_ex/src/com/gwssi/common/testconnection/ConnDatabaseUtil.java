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
	 * testOracleConn(����oracle���ݿ������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������
	 * �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:���ӳɹ�;false:����ʧ�� boolean
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public boolean testOracleConn(String url, String user, String password)
	{
       long begin=System.currentTimeMillis();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		} catch (InstantiationException e) {

			System.out.println("ʵ����oracle�����쳣");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("�Ƿ�����oracle�����쳣");
			return false;
		} catch (ClassNotFoundException e) {

			System.out.println("û���ҵ�oracle�������쳣");
			return false;
		}
        Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			System.out.println("��������쳣 ");
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
	 * testDB2Conn(����db2���ݿ������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:���ӳɹ�;false:����ʧ�� boolean
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public boolean testDB2Conn(String url, String user, String password)
	{

		try {
			// Class.forName("COM.ibm.db2.jdbc.net.DB2Driver").newInstance();
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (InstantiationException e) {

			System.out.println("ʵ����oracle�����쳣");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("�Ƿ�����oracle�����쳣");
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("û���ҵ�db2�������쳣");
			return false;
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("����");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("��������쳣 ");
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
	 * getDB2Conn(��ȡdb2����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param url
	 * @param user
	 * @param password
	 * @return        
	 * Connection       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public Connection getDB2Conn(String url,String user,String password){
		try {
			// Class.forName("COM.ibm.db2.jdbc.net.DB2Driver").newInstance();
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (InstantiationException e) {

			System.out.println("ʵ����oracle�����쳣");
			
		} catch (IllegalAccessException e) {

			System.out.println("�Ƿ�����oracle�����쳣");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("û���ҵ�db2�������쳣");
			
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("db2ttrue");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("��������쳣 ");
			
		}
		
		return conn;
		
	}

	/**
	 * 
	 * testSqlServer2000Conn(����SqlServer2005���ݿ������״̬) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:���ӳɹ�;false:����ʧ�� boolean
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public boolean testSqlServer2000Conn(String url, String user,
			String password)
	{
		/*
		 * sql server 2000�м���������URL·������� String driverName =
		 * "com.microsoft.jdbc.sqlserver.SQLServerDriver"; String dbURL =
		 * "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sample"; sql
		 * server 2005�м���������URL�����Ϊ�� String driverName =
		 * "com.microsoft.sqlserver.jdbc.SQLServerDriver"; String dbURL =
		 * "jdbc:sqlserver://localost:1433;DatabaseName=sample";
		 */

		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver")
					.newInstance();// sql server 2000
			// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();//sql
			// server 2005
			System.out.println("SqlServer�������سɹ�");
		} catch (InstantiationException e) {

			System.out.println("ʵ����SqlServer�����쳣");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("�Ƿ�����SqlServer�����쳣");
			return false;
		} catch (ClassNotFoundException e) {

			System.out.println("û���ҵ�SqlServer�������쳣");
			return false;
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			System.out.println("��������쳣 ");
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
	 * testSqlServer2005Conn(����SqlServer2005���ݿ������״̬) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:���ӳɹ�;false:����ʧ�� boolean
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public boolean testSqlServer2005Conn(String url, String user,
			String password)
	{
		/*
		 * sql server 2000�м���������URL·������� String driverName =
		 * "com.microsoft.jdbc.sqlserver.SQLServerDriver"; String dbURL =
		 * "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sample"; sql
		 * server 2005�м���������URL�����Ϊ�� String driverName =
		 * "com.microsoft.sqlserver.jdbc.SQLServerDriver"; String dbURL =
		 * "jdbc:sqlserver://localost:1433;DatabaseName=sample";
		 */

		try {
			// Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();//sql
			// server 2000
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
					.newInstance();// sql server 2005
		} catch (InstantiationException e) {

			System.out.println("ʵ����SqlServer�����쳣");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("�Ƿ�����SqlServer�����쳣");
			return false;
		} catch (ClassNotFoundException e) {

			System.out.println("û���ҵ�SqlServer�������쳣");
			return false;
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			System.out.println("��������쳣 ");
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
			System.out.println("ʵ����SqlServer�����쳣");
		} catch (IllegalAccessException e) {
			System.out.println("�Ƿ�����SqlServer�����쳣");
		} catch (ClassNotFoundException e) {
			System.out.println("û���ҵ�SqlServer�������쳣");
			
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("��������쳣 ");
		}
		return conn;
	}
	/**
	 * 
	 * testMySqlConn(����MySql���ݿ������״̬) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������
	 * �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return true:���ӳɹ�;false:����ʧ�� boolean
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public boolean testMySqlConn(String url, String user, String password)
	{

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("MySql�������سɹ�");
		} catch (InstantiationException e) {

			System.out.println("ʵ����MySql�����쳣");
			return false;
		} catch (IllegalAccessException e) {

			System.out.println("�Ƿ�����MySql�����쳣");
			return false;
		} catch (ClassNotFoundException e) {

			System.out.println("û���ҵ�MySql�������쳣");
			return false;
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			System.out.println("��������쳣 ");
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
	 * getMySqlConn(��ȡMySql����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param url
	 * @param user
	 * @param password
	 * @return        
	 * boolean       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public Connection getMySqlConn(String url, String user, String password)
	{

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("MySql�������سɹ�");
		} catch (InstantiationException e) {

			System.out.println("ʵ����MySql�����쳣");
			
		} catch (IllegalAccessException e) {

			System.out.println("�Ƿ�����MySql�����쳣");
			
		} catch (ClassNotFoundException e) {

			System.out.println("û���ҵ�MySql�������쳣");
			
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("��������쳣 ");
		}
		return conn;
	}
	
	/**
	 * 
	 * getOracleConnection(��ȡoracle����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException        
	 * Connection       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public Connection getOracleConnection(String url, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Connection conn = null;
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ȡ����ʧ��");
		}
		return conn;
		
	}
	
	public void closeConnection(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("�ر�����ʧ��!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * getOracleData(oracle�л�ȡ�û��µ����б���Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param url
	 * @param user
	 * @param password        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void getOracleUserTableDatas(String url, String user, String password){
		
		try {
			Connection conn = getOracleConnection(url,user,password);//���ݿ�����
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
				System.out.println("�������Ӵ���!");
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
	 * getOracleUserTableDatas(��ȡ����ֶ���Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param url
	 * @param user
	 * @param password
	 * @param tablename        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public String getOracleUserTableColumns(String url, String user, String password,String tablename){
		
		StringBuffer columns = new StringBuffer("");
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			Connection conn = getOracleConnection(url,user,password);//���ݿ�����
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
				System.out.println("�������Ӵ���!");
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
		System.out.println("�ֶ�����="+columns.toString());
		return columns.toString();
	}
	
	/**
	 * 
	 * getConn(��ȡ����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param db_type�����ݿ�����
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @return        
	 * Connection       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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
	 * getTableName(��HashMap��ʽ���ر�����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param db_type
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @return        
	 * HashMap<Integer,String>       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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
	 * getTableColumnName(��HashMap��ʽ���ر���ֶ���Ϣ����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param db_type
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @param tablename
	 * @return        
	 * HashMap<Integer,String>       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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
	 * getColumnNameAndType(��ȡ����ֶ����ƺ�����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param db_type
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @param tablename
	 * @return        
	 * HashMap<String,String>       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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
	 * main(������һ�仰�����������������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param args
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		ConnDatabaseUtil cd = new ConnDatabaseUtil();
		/*System.out.println("oracle���Ӳ��Խ����"
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
		
		// System.out.println("db2���Ӳ��Խ��1��"+cd.testDB2Conn("jdbc:db2://172.30.7.189:50000/newxcdb","db2inst2","1.password.1"));//newxcdbΪ���ݿ���

		/*
		 * sql server 2000�м���������URL·������� String driverName =
		 * "com.microsoft.jdbc.sqlserver.SQLServerDriver"; String dbURL =
		 * "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sample"; sql
		 * server 2005�м���������URL�����Ϊ�� String driverName =
		 * "com.microsoft.sqlserver.jdbc.SQLServerDriver"; String dbURL =
		 * "jdbc:sqlserver://localost:1433;DatabaseName=sample";
		 */
		// sqlserver2005
		// System.out.println("sqlServer���Ӳ��Խ����"+cd.testSqlServer2005Conn("jdbc:sqlserver://172.30.7.18:1433;DatabaseName=mydb","zxk","zxk"));//mydbΪ���ݿ���
		// sqlserver2005
		// System.out.println("sqlServer���Ӳ��Խ����"+cd.testSqlServer2000Conn("jdbc:microsoft:sqlserver://172.30.7.18:1433;DatabaseName=mydb","zxk","zxk"));//mydbΪ���ݿ���
		// System.out.println("MySql���Ӳ��Խ����"+cd.testMySqlConn("jdbc:mysql://172.30.18.29:3306/wjdc","root","root"));//mydbΪ���ݿ���

	}
}
