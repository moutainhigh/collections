package com.gwssi.adtransfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Leezen
 * 
 *         整理新的组织机构的dn
 */
public class OuList {

	public List<TreeNode> queryOuList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<TreeNode> nodeLst = new ArrayList<TreeNode>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@10.3.70.134:1521:oragxk";
			conn = DriverManager.getConnection(url, "cjk", "cjk");
			stmt = conn.createStatement();

			String sql = "select * from AD_OU t ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				TreeNode treeNode = new TreeNode(rs.getString("organ_id"),
						rs.getString("organ_name"), rs.getString("parent_id"));
				nodeLst.add(treeNode);

			}
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
			return nodeLst;
		}
	}

	public static void main(String[] args) {
		OuList ouStr = new OuList();
		List<TreeNode> nodeList = ouStr.queryOuList();
		TreeIterator treeIterator = new TreeIterator();
		Map map = treeIterator.iteratorNode(nodeList);
		Iterator entries = map.entrySet().iterator();

		// StringBuffer strAll = new StringBuffer();
		List sqlList = new ArrayList();

		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			StringBuffer str = new StringBuffer();
			str.append(" UPDATE ad_ou t SET t.ou_str = ");
			str.append("'");
			str.append(value);
			str.append("'");
			str.append(" WHERE t.organ_id = ");
			str.append("'");
			str.append(key);
			str.append("'");
			// str.append(";");
			// str.append("\n");

			// strAll.append(str);
			sqlList.add(str);
		}

		conOracle(sqlList);

		// FileOperation f = new FileOperation();
		// f.contentToTxt("D://a.sql", strAll.toString());
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
