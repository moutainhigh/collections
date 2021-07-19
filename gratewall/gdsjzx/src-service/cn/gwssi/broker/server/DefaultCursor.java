package cn.gwssi.broker.server;

import java.sql.*;

import cn.gwssi.common.exception.BrokerException;

public class DefaultCursor implements Cursor{
	private static ResultSet rs = null;
	private static Connection con=null;
	private static PreparedStatement pstmt = null;
	
	public DefaultCursor(Connection con,PreparedStatement pstmt,ResultSet res){
		this.con = con;
		this.pstmt = pstmt;
		this.rs = res;
	}
	
	@Override
	public String next() throws BrokerException {
		StringBuffer sbf = new StringBuffer();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			sbf.append("<col>");
			if(rs.next()){//组装
				for (int col = 0; col < colCount; col++) {
					String colname = rsmd.getColumnName(col+1);
					sbf.append("<").append(colname).append(">");
					sbf.append(rs.getString(colname));
					sbf.append("</").append(colname).append(">");
					//sbf.append("\r\n");
				}
			}
			sbf.append("</col>");
			System.out.println("sbf.toString():===>"+sbf);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BrokerException("SQLException:"+e.getMessage());
		}
		return sbf.toString();
	}

	@Override
	public void colse() throws BrokerException {
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int size() throws BrokerException {
		int countSum = 0;
		try {
			if(rs!=null){
				rs.last();
				countSum = rs.getRow();
				rs.beforeFirst();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BrokerException("SQLException:"+e.getMessage());
		}
		return countSum;
	}
}
