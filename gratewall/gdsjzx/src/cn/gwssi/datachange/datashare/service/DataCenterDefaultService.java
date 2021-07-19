package cn.gwssi.datachange.datashare.service;

import java.sql.*;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.broker.server.Cursor;
import cn.gwssi.broker.server.DefaultCursor;
import cn.gwssi.broker.server.ServiceProvider;
import cn.gwssi.broker.server.hander.SRContent;
import cn.gwssi.common.exception.BrokerException;

import com.gwssi.optimus.core.persistence.datasource.DataSourceManager;

public class DataCenterDefaultService extends ServiceProvider{

	@Override
	public Cursor execute(String params) throws BrokerException {
		//return super.execate(params);
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs= null;
		try {
			StringBuffer sbf = new StringBuffer("select ");
			if(StringUtils.isNotBlank(params)){
				SRContent s = new SRContent(params);
				sbf.append("");
			}else{
				
			}
			con = DataSourceManager.getConnection("defaultDataSource");//同步获取参数，只要是获取的数据项和返回的结果项
			pstmt = con.prepareStatement("select * from t_pt_dmsjb",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BrokerException(e.getMessage());
		}
	////sdqlsdsd=resultset
		return new DefaultCursor(con,pstmt,rs);
	}

}
