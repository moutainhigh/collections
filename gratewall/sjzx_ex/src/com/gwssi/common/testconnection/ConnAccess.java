package com.gwssi.common.testconnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.gwssi.common.context.DataBus;

public class ConnAccess
{
	/**
	 * 
	 * getColumnName(ȡ����ֶ���Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param ip
	 * @param datasourceName
	 * @param user
	 * @param password
	 * @param tableName        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void getColumnName(String ip,String datasourceName,String user,String password,String tableName){
		Connection con = getConnection(ip,datasourceName,user,password);
		
		ResultSet rs=null;
		
		try {
			DatabaseMetaData dbmd = con.getMetaData();
			rs = dbmd.getColumns(null, null, tableName, "%");//access
			while(rs.next()){
				
				
				DataBus db_tmp = new DataBus();
				String columnName = rs.getString("COLUMN_NAME");
				
				db_tmp.setValue("codename", columnName);
				db_tmp.setValue("codevalue", columnName);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)
					rs.close();
				
				if(con!=null)
					con.close();
			} catch (SQLException e) {
				System.out.println("�ر������쳣");
				e.printStackTrace();
			}
			
		}
		
		
	}
	/**
	 * 
	 * getTableName(��ȡ���б�����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param datasourcename
	 * @param user
	 * @param password        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void getTableName(String ip,String datasourcename,String user,String password){
		Connection con = getConnection(ip,datasourcename,user,password);
		Statement sql;
		ResultSet rs;
		
		try {
			DatabaseMetaData dbmd = con.getMetaData();
			rs = dbmd.getTables(null, null, "%", new String[]{"TABLE"});
			while(rs.next()){
				DataBus db_tmp = new DataBus();
				String tableName = rs.getString(3);
				String tableId = tableName;
				//String tableId = rs.getString(3);
				System.out.println("tableName="+tableName+"  tableId="+tableId);
				db_tmp.setValue("codename", tableName);
				//db_tmp.setValue("codevalue", tableId);
				System.out.println("access--db_tmp="+db_tmp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 
	 * testConnection(����access�������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param datasourcename
	 * @param user
	 * @param password
	 * @return  true:��ͨ;false:��ͨ
	 * boolean       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public  boolean testConnection(String ip,String datasourcename,String user,String password){
		Connection conn = null;
	
		try {
			Class.forName("org.objectweb.rmijdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("��ʼ��ʵ���쳣");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("�Ƿ������쳣");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("δ�ҵ����쳣");
			e.printStackTrace();
		}
		
		String url = "jdbc:rmi://"+ip+"/jdbc:odbc:"+datasourcename;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * getConnection(����access���ݿ�����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param datasourcename
	 * @param user
	 * @param password
	 * @return        
	 * Connection       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public Connection getConnection(String ip,String datasourcename,String user,String password){
		/*Connection conn;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("�����Ž����쳣");
			e.printStackTrace();
		}
		
		String url = "jdbc:odbc:"+datasourcename;
		try {
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;*/
		
		Connection conn;
		
			try {
				Class.forName("org.objectweb.rmijdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				System.out.println("��ʼ��ʵ���쳣");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("�Ƿ������쳣");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("δ�ҵ����쳣");
			e.printStackTrace();
		}
		
		String url = "jdbc:rmi://"+ip+"/jdbc:odbc:"+datasourcename;
		System.out.println("url=="+url);
		try {
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * getConnection(����access���ݿ�����)    
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
	public Connection getConnection(String url,String user,String password){
		Connection conn;
		
		try {
			Class.forName("org.objectweb.rmijdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("��ʼ��ʵ���쳣");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("�Ƿ������쳣");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("δ�ҵ����쳣");
		e.printStackTrace();
	}
	

	try {
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
		
	}
	
	/**
	 * 
	 * closeConn(�ر�access���ݿ�����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param conn        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void closeConn(Connection conn){
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("�ر�access�����쳣");
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args)
	{
		ConnAccess ca = new ConnAccess();
		//System.out.println(ca.testConnection("172.30.18.61","access","dwn","dwn"));
		ca.getColumnName("172.30.18.60","access","dwn","dwn","student");
		//ca.getTableName("172.30.18.60","access","dwn","dwn");
		/*try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("�����Ž����쳣");
			e.printStackTrace();
		}
		
		Connection con;
		Statement sql;
		ResultSet rs;
		try {
			con=DriverManager.getConnection("jdbc:odbc:access","dwn","dwn");
			DatabaseMetaData dbmd = con.getMetaData();
			rs = dbmd.getTables(null, null, "%", new String[]{"TABLE"});
			while(rs.next()){
				System.out.println("table-name:"+rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*try{
			con=DriverManager.getConnection("jdbc:odbc:access","dwn","dwn");
			sql=con.createStatement();
			rs=sql.executeQuery("Select * FROM student");
			while(rs.next()){
			 String id=rs.getString(1); 
			 String name=rs.getString(2); //������ݿ��һ��
			 String sex=rs.getString(3); 
			 System.out.println("id:"+id); //�����Ϣ
			System.out.println("����:"+name); //�����Ϣ
			System.out.println("�Ա�:"+sex);
			   }
			rs.close();
			sql.close();
			con.close();
			}catch(SQLException e){
				
//				}
*/		
	}
}
