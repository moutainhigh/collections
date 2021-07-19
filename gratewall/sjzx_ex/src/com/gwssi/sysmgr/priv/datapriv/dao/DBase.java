/**
 * 
 */
package com.gwssi.sysmgr.priv.datapriv.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cn.gwssi.common.component.exception.TxnException;
/**
 * @author ����
 *
 */
public class DBase {
	private Connection conn;
	private static final Logger log = Logger.getLogger(DBase.class);
	private static DBase inst = new DBase(); 			// Ψһʵ��
	
	protected DBase(){
	}
	/**
	 * ��ȡ����
	 * @return �������Ӷ���
	 * @throws TxnException 
	 */
	public Connection getConn(){
		return conn;
	}
	
	static public DBase getInst(){
		return inst;
	}

	/**
	 * ��������
	 * @param conn ���Ӷ���
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * �´�һ������
	 * @throws ClassNotFoundException �Ҳ���������
	 * @throws SQLException SQL����
	 */
	public void open() throws ClassNotFoundException, SQLException{
		String url = "jdbc:db2://172.30.7.68:50000/cs_kf";
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("open : com.ibm.db2.jcc.DB2Driver������ : " 
					+ e.getMessage());
			throw e;
		}
		
		try {
			conn = DriverManager.getConnection(url, "db2admin", "qazwsx");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("CreateConn : ��������ʧ�� : " 
					+ e.getMessage());
			throw e;
		}
		log.debug("�Ѿ����ӵ� " + url);
	}
	
	/**
	 * �ر�����
	 * @throws SQLException SQL����
	 */
	public void close() throws SQLException{
		conn.close();
		log.debug("�ɹ��ر�����");
	}
	
	public int getCount(String sql) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		log.debug("��ʼ��ѯ,SQL='" + sql + "'");
		int nRet = -1;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) nRet = rs.getInt(1);
			rs.close();
			stmt.close();
			return nRet;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("ִ�в�ѯʧ�ܣ�SQL='" + sql + "'," + e.getMessage());
			throw e;
		}
	}
	
	public List getFieldList(String sql) throws SQLException{
		List result = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			log.debug("��ʼ��ѯ,SQL='" + sql + "'");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				result.add(rs.getObject(1));
			}
			log.debug(result);
			rs.close();
			stmt.close();
		} catch(SQLException e){
			e.printStackTrace();
			log.error("ִ�в�ѯʧ�ܣ�SQL='" + sql + "'," + e.getMessage());
			throw e;
		} finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
		return result;
	}
	
	public List query(String sql) throws SQLException{
		List result = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			log.debug("��ʼ��ѯ,SQL='" + sql + "'");
			System.out.println(conn);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			log.debug("<Table>");
			while(rs.next()){
				Map row = new HashMap();
				log.debug("  <row>");
				for(int i = 1; i <= rsmd.getColumnCount(); i++){
					log.debug("    <" + rsmd.getColumnName(i) + ">" + rs.getObject(i)
							+ "</" + rsmd.getColumnName(i) + "/>");
					row.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				log.debug("  </row>");
				result.add(row);
			}
			log.debug("</Table>");
			rs.close();
			stmt.close();
		} catch(SQLException e){
			e.printStackTrace();
			log.error("ִ�в�ѯʧ�ܣ�SQL='" + sql + "'," + e.getMessage());
			throw e;
		} catch(Exception e){
			e.printStackTrace();
		}
		finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		DBase dbase = new DBase();
		try {
			dbase.open();
			dbase.query("select * from funcinfo_new");
			dbase.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
