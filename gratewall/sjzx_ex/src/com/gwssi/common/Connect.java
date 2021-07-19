package com.gwssi.common;

import java.sql.*;
import java.util.*;
import javax.naming.*;

public class Connect
{
	public Connection getConnect()
	{
		Context ctx = null;
		Hashtable ht = new Hashtable();
		ht.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		ht.put(Context.PROVIDER_URL, "t3://160.99.2.100:7002");
		Connection conn = null;
		try {
			ctx = new InitialContext(ht);
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx
					.lookup("gwssi_gxk");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public String selectRs()
	{
		String count = null;
		Connect conn = new Connect();
		Connection connection = conn.getConnect();
		try {
			Statement stmt = connection.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT MON_YC_STA.UPDATE_DATE ,MON_YC_STA.REG_BUS_ENT_ID ,MON_YC_STA.INSPE_REGID_ALL ,MON_YC_STA.INSPE_REAS ,MON_YC_STA.INSPE_YEAR  FROM MON_YC_STA WHERE  (   MON_YC_STA.INSPE_YEAR  = '2012'  )  AND (   MON_YC_STA.UPDATE_DATE  >= '2000-09-27'  AND  MON_YC_STA.UPDATE_DATE  <= '2013-09-28'  )");
			stmt.execute(sql.toString());
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				count = rs.getString(1);
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
