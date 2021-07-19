package com.gwssi.report.util;

import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.gwssi.report.model.TCognosReportBO;

public class Report2Stream {
	// jdbc:oracle:thin:@10.3.70.134:1521:oragxk
	private static String url = "jdbc:oracle:thin:@10.0.3.153:1521/racdb5";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
	private static String user = "db_csdb";// 用户名,系统默认的账户名
	private static String password = "db_csdb";// 你安装时选设置的密码
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}// 获取连接
	}

	// 讲报表库数据存到业务库
	public static void printOracle(TCognosReportBO bo, String result1) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {
			System.out.println("连接成功！");
			String uuid = UUID.randomUUID().toString().replace("-", "");

			Writer outStream = null;
			String sql = " INSERT INTO DB_CSDB.T_COGNOS_Report(id, regcode, reportContext, reportType, reportTime, reportParamters, ReportName, mouth, year)  "
					+ "values ('"
					+ uuid
					+ "', '"
					+ bo.getRegcode()
					+ "', empty_clob(), '"
					+ bo.getReporttype()
					+ "', '"
					+ bo.getReporttime()
					+ "', '"
					+ bo.getReportparamters()
					+ "', '"
					+ bo.getReportname()
					+ "', '"
					+ bo.getMouth()
					+ "', '" + bo.getYear() + "')";
			pre = con.prepareStatement(sql);
			pre.execute(sql);
			sql = "select reportContext from DB_CSDB.T_COGNOS_Report where id = ?  for update";
			pre = con.prepareStatement(sql);// 实例化预编译语句r
			pre.setString(1, uuid);

			ResultSet rs = pre.executeQuery();
			oracle.sql.CLOB clob = null;
			if (rs.next()) {
				// 得到java.sql.Clob对象后强制转换为oracle.sql.CLOB
				clob = (oracle.sql.CLOB) rs.getClob("reportContext");
				outStream = clob.getCharacterOutputStream();
				// data是传入的字符串，定义：String data
				char[] c = result1.toCharArray();
				outStream.write(c, 0, c.length);
			}
			outStream.flush();
			outStream.close();

			con.commit();
			rs.close();
			pre.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 讲报表库数据展示到页面
	public String fromOraclePrint(String cognosId) throws Exception {

		String data = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		con.setAutoCommit(false);
		Statement st = con.createStatement();

		Reader inStream = null;
		ResultSet rs = st.executeQuery("select reportContext from DB_CSDB.T_COGNOS_Report where ID='" + cognosId + "'");
		if (rs.next()) {
			java.sql.Clob clob = rs.getClob("reportContext");
			inStream = clob.getCharacterStream();
			char[] c = new char[(int) clob.length()];
			inStream.read(c);
			// data是读出并需要返回的数据，类型是String
			data = new String(c);
			inStream.close();
		}
		inStream.close();
		con.commit();
		con.close();
		return data;
	}

}
