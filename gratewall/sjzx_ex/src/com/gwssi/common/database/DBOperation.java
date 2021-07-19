package com.gwssi.common.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.result.Page;


public abstract class DBOperation {
	public static final String DB_CONFIG = "app";
	private static final String DB_CONNECT_TYPE = "dbConnectionType";
	protected static final String MAX_QUERY_TIMEOUT = "maxTime";
	
	protected abstract String getPaginationSQL(String sql,int from,int to);
	
	private int maxSeconds = -1;
	
	private String getConnectType(){
		return java.util.ResourceBundle.getBundle(DB_CONFIG).getString(DB_CONNECT_TYPE);
	}
	
	protected String getMaxWaiteTime(){
		return java.util.ResourceBundle.getBundle(DB_CONFIG).getString(MAX_QUERY_TIMEOUT);
	}
	
	public List select(String sql,Page page) throws DBException{
		int currentPage = page.getCurrentPage();
		int everyPage = page.getEveryPage();
		int to = currentPage*everyPage;
		int from = to-everyPage+1;
		return select(getPaginationSQL(sql,from,to));
	}
	
	public List selectInOrder(String sql,Page page) throws DBException{
		int currentPage = page.getCurrentPage();
		int everyPage = page.getEveryPage();
		int to = currentPage*everyPage;
		int from = to-everyPage+1;
		return selectInOrder(getPaginationSQL(sql,from,to));
	}
	
	public Map selectOne(String sql) throws DBException{
		return DbUtils.selectOne(sql,getConnectType());
	}	
	
	public Map selectOne(String sql,String connType) throws DBException{
		return DbUtils.selectOne(sql,connType);
	}	
	
	public List select(String sql) throws DBException{
		List list =null;
		try{
		    list = DbUtils.select(sql,getConnectType());
		}catch(DBException e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}	
	
	public List selectInOrder(String sql) throws DBException{
		List list =null;
		try{
		    list = DbUtils.selectInOrder(sql,getConnectType());
		}catch(DBException e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	/**
	 * 指定查询时间
	 * @param sql
	 * @return
	 * @throws DBException
     * @author wyx
	 */
	public List selectWithTimeOut(String sql, int maxSeconds) throws DBException{
		List list =null;
		try{
		    list = DbUtils.selectWithTimeOut(sql,getConnectType(), maxSeconds);
		}catch(DBException e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public int[] execute(List sqls,boolean flag,String type) throws DBException{
		if (flag) {
			int transId = -999999;
			try {
				transId = DbUtils.transactionBegin(type);
				int[] returnValue = DbUtils.execute(sqls, transId);
				DbUtils.transactionCommit(transId);
				return returnValue;
			} catch (DBException e) {
				e.printStackTrace();
				if (transId != -999999) {
					DbUtils.transactionRollback(transId);
				}
				throw e;
			}
		} else {
			return DbUtils.execute(sqls, type);
		}
	}

	public void executeCall(List callSql, boolean flag, String type) throws DBException
	{
		if(callSql!=null&&callSql.size()>0){
	        Connection conn = null; 
	        CallableStatement proc = null;
			if (flag) {
				int transId = -999999;
				try {
					transId = DbUtils.transactionBegin(type);
					conn = DbUtils.getConnection(transId);
					
					for(int i=0;i<callSql.size();i++){
						String s = (String)callSql.get(i);
						String[] str = s.split("&");
						for(int j=0;j<str.length;j++){
							if(j==0)
							    proc = conn.prepareCall(str[j]);
							else
								proc.setString(j, str[j]);
						}
						proc.execute();
					}
					DbUtils.transactionCommit(transId);
				} catch (SQLException e) {
					e.printStackTrace();
					if (transId != -999999) {
						DbUtils.transactionRollback(transId);
					}
					throw DBException.newInstance(e);
				} finally {
					try {
						if (proc != null) {
							proc.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ex1) {
						throw DBException.newInstance(ex1);
					}
				}

			} else {
				try {
					conn = DbUtils.getConnection(type);
					
					for(int i=0;i<callSql.size();i++){
						String s = (String)callSql.get(i);
						String[] str = s.split("&");
						for(int j=0;j<str.length;j++){
							if(j==0)
							    proc = conn.prepareCall(str[j]);
							else
								proc.setString(j, str[j]);
						}
						proc.execute();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw DBException.newInstance(e);
				} finally {
					try {
						if (proc != null) {
							proc.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ex1) {
						throw DBException.newInstance(ex1);
					}
				}		
			}				
		}
		
	}
	
	public void executeCall(String sql,String[] params,boolean flag,String type) throws DBException{
        Connection conn = null; 
        CallableStatement proc = null;
		if (flag) {
			int transId = -999999;
			try {
				transId = DbUtils.transactionBegin(type);
				conn = DbUtils.getConnection(transId);
				proc = conn.prepareCall(sql);
				for(int i=0;i<params.length;i++){
					proc.setString(i+1, params[i]);
				}
				proc.execute();
				DbUtils.transactionCommit(transId);
			} catch (SQLException e) {
				e.printStackTrace();
				if (transId != -999999) {
					DbUtils.transactionRollback(transId);
				}
				throw DBException.newInstance(e);
			} finally {
				try {
					if (proc != null) {
						proc.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException ex1) {
					throw DBException.newInstance(ex1);
				}
			}

		} else {
			try {
				conn = DbUtils.getConnection(type);
				proc = conn.prepareCall(sql);
				for(int i=0;i<params.length;i++){
					proc.setString(i+1, params[i]);
				}
				proc.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				throw DBException.newInstance(e);
			} finally {
				try {
					if (proc != null) {
						proc.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException ex1) {
					throw DBException.newInstance(ex1);
				}
			}		
		}		
	}
	
	public int[] execute(List sqls,boolean flag) throws DBException{
		if (flag) {
			int transId = -999999;
			try {
				transId = DbUtils.transactionBegin(getConnectType());
				int[] returnValue = DbUtils.execute(sqls, transId);
				DbUtils.transactionCommit(transId);
				return returnValue;
			} catch (DBException e) {
				if (transId != -999999) {
					DbUtils.transactionRollback(transId);
				}
				throw e;
			}
		} else {
			return DbUtils.execute(sqls, getConnectType());
		}
	}
	
	public int execute(String sql,boolean flag) throws DBException{
		if (flag) {
			int transId = -999999;
			try {
				transId = DbUtils.transactionBegin(getConnectType());
				int returnValue = DbUtils.execute(sql, transId);
				DbUtils.transactionCommit(transId);
				return returnValue;
			} catch (DBException e) {
				if (transId != -999999) {
					DbUtils.transactionRollback(transId);
				}
				throw e;
			}
		} else {
			return DbUtils.execute(sql, getConnectType());
		}
	}
	
	public long count(String sql) throws DBException{
		if(maxSeconds > 0){
			return countWithTimeOut(sql);
		}
		String countSql = "select count(1) as totals from ("+sql+") t";
		List list = select(countSql);
		if (list != null && list.size() > 0) {
			Map map = (Map) list.get(0);
			Object[] o = map.values().toArray();
			if (o != null && o.length > 0) {
				return Long.parseLong(o[0].toString());
			} else
				return 0;
		} else
			return 0;
	}
	
	public long countWithTimeOut(String sql) throws DBException{
		String countSql = "select count(1) as totals from ("+sql+") t";
		List list = selectWithTimeOut(countSql, maxSeconds);
		if (list != null && list.size() > 0) {
			Map map = (Map) list.get(0);
			Object[] o = map.values().toArray();
			if (o != null && o.length > 0) {
				return Long.parseLong(o[0].toString());
			} else
				return 0;
		} else
			return 0;
	}
	
	public int executeJDBCUpdate(String updaSQL, String sqlValue, String id) throws Exception{
		Connection conn = null;
		try {
			conn = DbUtils.getConnection(getConnectType());
			PreparedStatement stmt = conn.prepareStatement(updaSQL);
			conn.setAutoCommit(false);
			
			stmt.setString(1, sqlValue);
			stmt.setString(2, id);
			int i = stmt.executeUpdate();
			
			conn.commit();
			
			return i;
		} catch (Exception e) {
			if(conn != null){
				conn.rollback();
			}
			return 0;
		}
	}

	public int getMaxSeconds()
	{
		return maxSeconds;
	}

	public void setMaxSeconds(int maxSeconds)
	{
		this.maxSeconds = maxSeconds;
	}

}
