package com.gwssi.adtransfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDao {

	public List conOracle() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@10.3.70.134:1521:oragxk";
			conn = DriverManager.getConnection(url, "cjk", "cjk");
			stmt = conn.createStatement();

			String sql = "select * from AD_USER_OLD_TO_NEW_1 t where t.user_old_str is not null and t.user_new_str is not null and t.xh >= 150 and t.xh < 200 order by t.xh";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map userMap = new HashMap();
				userMap.put("user_name", rs.getString("user_name"));
				userMap.put("user_id", rs.getString("user_id"));
				userMap.put("user_old_str", rs.getString("user_old_str"));
				userMap.put("user_new_str", rs.getString("user_new_str"));
				list.add(userMap);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭数据库，结束进程
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}

	}

	public static void main(String[] args) {
		BaseDao c = new BaseDao();
		List list = c.conOracle();
		System.out.println(list);
	}

}
