package com.gwssi.dw.aic.bj;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

public class EmptyLogonSessionServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public static final String DB_CONFIG = "app";
	private static final String DB_CONNECT_TYPE = "dbConnectionType";
	
	private static String getConnectType(){
		return java.util.ResourceBundle.getBundle(DB_CONFIG).getString(DB_CONNECT_TYPE);
	}
	
	public static String dbType = getConnectType();
	
	/**
	 * 删除LOGON_SESSION表的数据库记录
	 */
	public void init(ServletConfig config) {
//       try {
//			DbUtils.execute("delete from login_session", dbType);
//		} catch (DBException e) {
//			e.printStackTrace();
//		}
	}
}
