package com.gwssi.common.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.genersoft.frame.base.database.DBException;

public class DBConnectUtil
{

	public DataSource	ds	= null;

	private static Context getInitialContext() throws Exception
	{
		/**本机**/
		String str1 = "";
		String str2 = "";
		String str3 = "";
		
		Properties prop = new Properties();
		InputStream in = DBConnectUtil.class.getClassLoader().getResourceAsStream("/weblogic-config.properties");
		try {   
            prop.load(in);     
        } catch (IOException e) {
            e.printStackTrace();   
        } finally{
        	in.close();
        }
        
        str1 = prop.getProperty("weblogicAddress").trim();
        str2 = prop.getProperty("weblogicUsername").trim();   
        str3 = prop.getProperty("weblogicPassword").trim();

         
//		String str1 = "t3://127.0.0.1:7010";
//		String str2 = "weblogic";
//		String str3 = "weblogic123";
		
		/**正式环境**/
/*		String str1 = "t3://160.99.1.4:7002";
		String str2 = "weblogic";
		String str3 = "bjgs_gwssi_jhpt_weblogic";*/
		
		/**测试环境**/
//		String str1 = "t3://160.99.2.2:7002";
//		String str2 = "weblogic";
//		String str3 = "weblogic123";
		
		Properties localProperties = null;
		try {
			localProperties = new Properties();
			localProperties.put("java.naming.factory.initial",
					"weblogic.jndi.WLInitialContextFactory");
			
			localProperties.put("java.naming.provider.url", str1);
			if (str2 != null) {
				localProperties.put("java.naming.security.principal", str2);
				localProperties.put("java.naming.security.credentials",
						(str3 == null) ? "" : str3);
			}

			return new InitialContext(localProperties);
		} catch (Exception localException) {
			throw localException;
		}
	}

	private DataSource getDataSourceByJndi(String paramString)
	{
		DataSource localDataSource = null;
		Context localContext = null;
		try {
			
			localContext = getInitialContext();
			
			localDataSource = (DataSource) localContext.lookup(paramString);
			System.out.println("----获取数据源成功---->" + localDataSource);
		} catch (Exception localException) {
			localException.printStackTrace();
			System.out.println("------获取数据源出现异常-------");
		}
		return localDataSource;
	}

	public DBConnectUtil(String paramString)
	{
		this.ds = getDataSourceByJndi(paramString);
	}

	public Connection getConnection()
	{
		try {
			if (null != this.ds) {
				return this.ds.getConnection();
			}
			return null;
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList setToCollection(ResultSet rs, boolean inOrder)
			throws DBException
	{
		ArrayList alist = new ArrayList();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String[] columns = new String[columnCount];
			for (int i = 0; i < columnCount; ++i) {
				columns[i] = rsmd.getColumnName(i + 1);
			}
			while (rs.next()) {
				HashMap ht = null;
				if (inOrder)
					ht = new LinkedHashMap();
				else {
					ht = new HashMap();
				}
				for (int j = 0; j < columns.length; ++j) {
					Object value = rs.getObject(j + 1);
					ht.put(columns[j], value);
				}
				alist.add(ht);
			}
		} catch (SQLException ex) {
			throw DBException.newInstance(ex);
		}
		return alist;
	}

	/**
	 * 根据sql查询并返回单条数据，默认有排序
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map selectOne(String sql) throws DBException
	{
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn=null;
		try {
			if (null == conn || conn.isClosed()) {
				conn = this.getConnection();
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ArrayList r = this.setToCollection(rs, true);
			if ((r != null) && (r.size() > 0)) {
				return ((Map) r.get(0));
			}
			return new HashMap();
		} catch (SQLException ex) {
			throw DBException.newInstance(ex);
		} finally {
			this.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * 根据sql查询并返回list，默认有排序
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectInOrder(String sql)
	{
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn=null;
		List list = new ArrayList();
		try {
			if (null == conn || conn.isClosed()) {
				conn = this.getConnection();
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = this.setToCollection(rs, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(conn, stmt, rs);
		}
		return list;
	}

	/**
	 * 执行插入、更新、删除语句
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int execute(String sql)
	{
		Statement stmt = null;
		int count = 0;
		Connection conn=null;
		try {
			if (null == conn || conn.isClosed()) {
				conn = this.getConnection();
			}
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(conn, stmt, null);
		}
		return count;
	}

	/**
	 * 关闭连接的方法
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void closeConnection(Connection conn, Statement stmt, ResultSet rs)
	{
		try {
			if (null != conn) {
				if (!conn.isClosed()) {
					conn.close();
				}
			}
			if (null != stmt) {
				stmt.close();
			}
			if (null != rs) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
