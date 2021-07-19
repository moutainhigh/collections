package com.gwssi.dw.dq.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

public class JsonDao
{
	private Connection conn;
	
	public JsonDao()
	{
		super();
		conn = null;
	}

	public JsonDao(Connection conn)
	{
		super();
		this.conn = conn;
	}

	/**
	 * 查找分组中的预定义条件
	 * @param sql
	 * @param connType
	 * @return
	 */
	public String queryPrevCondition(String sql, String connType) {
		String value  = "";
		try {
			checkConnection(connType);
			Map valueMap = DbUtils.selectOne(sql, conn);
			value = (String)valueMap.get("PREV_CONDITION");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 查找分组中的自定义条件
	 * @param sql
	 * @param connType
	 * @return
	 */
	public String queryCustomCondition(String sql, String connType) {
		String value  = "";
		try {
			checkConnection(connType);
			Map valueMap = DbUtils.selectOne(sql, conn);
			value = (String)valueMap.get("CUSTOM_ITEM_COND");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public Map queryOne(String sql, String connType) {
		try {
			checkConnection(connType);
			Map valueMap = DbUtils.selectOne(sql, conn);
			return valueMap;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List queryAll(String sql, String connType) {
		try {
			checkConnection(connType);
			List valueList = DbUtils.select(sql, conn);
			return valueList;
		} catch (DBException e) {
			System.out.println("JsonDao.queryAll:"+sql+e.getMessage());
		} catch (SQLException e) {
			System.out.println("JsonDao.queryAll:"+sql+e.getMessage());
		}
		return null;
	}
	
	public boolean freeConnection() {
		try {
			if (this.conn != null || !this.conn.isClosed()) {
				DbUtils.freeConnection(conn);
			}
			conn = null;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void checkConnection(String connType) throws DBException, SQLException {
		if (this.conn == null || this.conn.isClosed())
			conn = DbUtils.getConnection(connType);
	}

}
