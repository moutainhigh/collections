package cn.gwssi.datachange.datashare.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gwssi.optimus.core.persistence.datasource.DataSourceManager;

import cn.gwssi.broker.server.Cursor;
import cn.gwssi.broker.server.DefaultCursor;
import cn.gwssi.broker.server.ServiceProvider;
import cn.gwssi.common.exception.BrokerException;

public class NoticeServiceImpl extends ServiceProvider{

	@Override
	public Cursor execute(String params) throws BrokerException {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs= null;
		try {
			//通知数据中心该对象已经启动，并改变该对象的服务运行状态	
			/*StringBuffer sbf = new StringBuffer("update t_pt_fwdxjbxx set state='0' where serviceObjectName=?");
			con = DataSourceManager.getConnection("defaultDataSource");//同步获取参数，只要是获取的数据项和返回的结果项
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sbf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, params);
			pstmt.executeUpdate();*/
			StringBuffer sbf = new StringBuffer("update t_pt_fwjbxx set serviceRunState=? where serviceobjectid in (select fwdxjbid from t_pt_fwdxjbxx where serviceObjectName=?)");
			con = DataSourceManager.getConnection("defaultDataSource");//同步获取参数，只要是获取的数据项和返回的结果项
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sbf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println("======"+sbf.toString());
			System.out.println("======"+params);
			pstmt.setString(1, "0");
			pstmt.setString(2, params);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BrokerException(e.getMessage());
		}
		return new DefaultCursor(con,pstmt,rs);
	}

}
