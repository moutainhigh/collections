package com.gwssi.adtransfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Leezen 将新的用户的dn整理出来
 */
public class UserList {

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		// StringBuffer strall = new StringBuffer();

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@10.3.70.134:1521:oragxk";
			conn = DriverManager.getConnection(url, "cjk", "cjk");
			stmt = conn.createStatement();

			List list = new ArrayList();

			String sql = "select u.user_name, u.user_id, o.ou_str from AD_USER u, AD_OU o where u.organ_id = o.organ_id and user_id is not null";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				StringBuffer str = new StringBuffer();
				str.append("update AD_USER set USER_STR = ");
				str.append("'");
				str.append("CN=");
				str.append(rs.getString("user_name"));
				str.append(",");
				str.append(rs.getString("ou_str"));
				str.append("'");
				str.append(" WHERE user_id = ");
				str.append("'");
				str.append(rs.getString("user_id"));
				str.append("'");
				// str.append(";");
				// strall.append(str);
				// strall.append("\n");
				list.add(str);
			}

			conOracle(list);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void conOracle(List insertList) {
		Connection conn = null;
		Statement stmt = null;
		// ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@10.3.70.134:1521:oragxk";
			conn = DriverManager.getConnection(url, "cjk", "cjk");
			stmt = conn.createStatement();

			for (int i = 0; i < insertList.size(); i++) {
				stmt.executeUpdate(insertList.get(i).toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭数据库，结束进程
				// rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
