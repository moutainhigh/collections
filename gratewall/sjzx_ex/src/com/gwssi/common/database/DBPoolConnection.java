package com.gwssi.common.database;

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

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.constant.CollectConstants;

public class DBPoolConnection
{
	public Connection	conn	= null;

	private String		poolKey	= CollectConstants.DATASOURCE_DEFAULT;

	public DBPoolConnection(String poolKey)
	{
		try {
			conn = DbUtils.getConnection(poolKey);
			this.setPoolKey(poolKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection()
	{
		try {
			if (null == conn || conn.isClosed()) {
				conn = DbUtils.getConnection(this.getPoolKey());
			}
		} catch (DBException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 根据sql获取结果 selectBySql(这里用一句话描述这个方法的作用) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param sql
	 * @return List<Map>
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public List<Map> selectBySql(String sql)
	{   
		ResultSet rs = null;
		Statement stmt = null;
		List<Map> list = new ArrayList<Map>();
        conn=getConnection();
		try {
			if (conn != null) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				list = setToCollection(rs, true, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
					if (null != stmt) {
						stmt.close();
					}
					if (null != rs) {
						rs.close();
					}
					DbUtils.freeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 
	 * executeDDL(执行CREATE、ALTER、DROP语句)
	 * 
	 * @param sql
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String executeDDL(String sql)
	{
		conn=getConnection();
		Statement stmt = null;
		String msg="执行成功";
		try {
			if (conn != null) {
				stmt = conn.createStatement();
				stmt.execute(sql);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			msg="sql语句异常";
		} finally {
			try {
				if (null != conn) {
					if (null != stmt) {
						stmt.close();
					}
					DbUtils.freeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				msg="关闭数据库连接异常";
			} catch (DBException e) {
				e.printStackTrace();
				msg="关闭数据库连接异常";
			}
		}
		return msg;
	}

	/**
	 * 
	 * executeDML executeDDL(执行UPDATE,DELETE语句)
	 * 
	 * @param sql
	 * @return int
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public int executeDML(String sql)
	{
		conn=getConnection();
		int count = -1;
		Statement stmt = null;
		try {
			if (conn != null) {
				stmt = conn.createStatement();
				count = stmt.executeUpdate(sql);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
					if (null != stmt) {
						stmt.close();
					}
					DbUtils.freeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	private ArrayList setToCollection(ResultSet rs, boolean firstFlag,
			boolean inOrder) throws DBException
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
				if (firstFlag)
					;
			}
		} catch (SQLException ex) {
			throw DBException.newInstance(ex);
		}
		return alist;
	}

	public int executeBatch(String[] sqls)
	{
		int count = 0;
		conn=getConnection();
		Statement st = null;
		try {
			conn.setAutoCommit(false);
			st = conn.createStatement();
			for (int i = 0; i < sqls.length; i++) {
				st.addBatch(sqls[i]);
			}

			int[] countList = st.executeBatch();
			conn.commit();
			count = countList.length;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != st) {
					st.close();
				}
				if (null != conn) {
					conn.setAutoCommit(true);
				}
				DbUtils.freeConnection(conn);
			} catch (DBException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public String getPoolKey()
	{
		return poolKey;
	}

	public void setPoolKey(String poolKey)
	{
		this.poolKey = poolKey;
	}

	public static void main(String[] args)
	{
		// 例子
		DBPoolConnection conn = new DBPoolConnection("gwssi_gxk");
		List list = conn
				.selectBySql("select * from reg_bus_ent where rownum<=10");
	}

}
