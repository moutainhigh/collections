package cn.gwssi.datachange.datashare.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.gwssi.optimus.core.persistence.datasource.DataSourceManager;

import cn.gwssi.broker.server.Cursor;
import cn.gwssi.broker.server.DefaultCursor;
import cn.gwssi.broker.server.ServiceProvider;
import cn.gwssi.common.exception.BrokerException;

public class AuthCacheClientImpl extends ServiceProvider{
	private static Logger log = Logger.getLogger(AuthCacheClientImpl.class);
	@Override
	public Cursor execute(String params) throws BrokerException {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs= null;
		try {
			//查出服务对象、服务、主题
			//StringBuffer sbf = new StringBuffer("select a.serviceobjectname,b.servicename,b.serviceurl,b.invokeclass,d.themename from T_PT_FWDXJBXX a,T_PT_FWJBXX b,T_PT_FWDXQXGLB c,T_PT_THEMEXX d where a.fwdxjbId=c.serviceObjectId and b.serviceId=c.serviceID and d.fwdxjbId=a.fwdxjbid and a.serviceObjectName=?");
			/*StringBuffer sbf = new StringBuffer("select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceObjectIP serviceobjectip,a.serviceObjectPort serviceobjectport,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,'' themename from T_PT_FWDXJBXX a,T_PT_FWJBXX b,T_PT_FWDXQXGLB c where a.fwdxjbId=c.serviceObjectId and b.serviceId=c.serviceID and a.serviceObjectName=?");
			sbf.append(" union all ");
			sbf.append("select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceObjectIP serviceobjectip,a.serviceObjectPort serviceobjectport,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,d.themename themename from T_PT_FWDXJBXX a,T_PT_THEMEXX d where d.fwdxjbId=a.fwdxjbid and a.serviceObjectName=?");*/
			/*StringBuffer sbf = new StringBuffer("select d.controlobjectstate controlobjectstate,d.serviceobjectname serviceobjectname,d.serviceobjectip serviceobjectip,d.serviceobjectport serviceobjectport,d.servicename servicename,d.serviceurl serviceurl,d.invokeclass invokeclass,d.executetype executetype,d.defaulttime defaulttime,d.themename themename,e.tablecode tablecode,e.columncode columncode,e.servicecontentcondition servicecontentcondition,e.servicecontent servicecontent,e.servicecontentname servicecontentname from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceObjectIP serviceobjectip,a.serviceObjectPort serviceobjectport,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,'' themename,b.serviceconentid from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where a.serviceObjectName=? and a.state='0' and b.servicestate='0' and a.controlobjectstate='0') d left join T_PT_FWNRXX e on d.serviceconentId=e.fwnrId and e.isenabled='0'");
			sbf.append(" union all ");
			sbf.append("select controlobjectstate,serviceobjectname,serviceobjectip,serviceobjectport,servicename,serviceurl,invokeclass,executetype,defaulttime,themename,tablecode,columncode,servicecontentcondition,servicecontent,servicecontentname from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceObjectIP serviceobjectip,a.serviceObjectPort serviceobjectport,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,d.themename themename,'' tablecode,'' columncode,'' servicecontentcondition,'' servicecontent,'' servicecontentname,a.state from T_PT_FWDXJBXX a left join T_PT_THEMEXX d on d.fwdxjbId=a.fwdxjbid and d.isstart='0') s where s.serviceObjectName=? and s.state='0' and s.controlobjectstate='0'");*/
			/*StringBuffer sbf = new StringBuffer("select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,'' themename from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where a.state='0' and b.servicestate='0' and a.controlobjectstate='0' and a.serviceObjectName=?"); 
			sbf.append(" union all ");
			sbf.append("select e.controlobjectstate,e.serviceobjectname,e.serviceobjectip,e.serviceobjectport,e.servicename,e.serviceurl,e.invokeclass,e.executetype,e.defaulttime,e.themename from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,d.themename themename from T_PT_FWDXJBXX a left join T_PT_THEMEXX d on d.fwdxjbId=a.fwdxjbid and d.isstart='0' and a.state='0' and a.controlobjectstate='0' and a.serviceObjectName=?) e where e.themename is not null");*/
			StringBuffer sbf = new StringBuffer("select '0' type,a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,(select serviceobjectname from T_PT_FWDXJBXX where fwdxjbid in(select serviceobjectid from T_PT_FWJBXX where serviceid=b.serviceid)) serviceobjectnamef,b.servicename servicename,b.serviceurl serviceurl,b.invokeclass invokeclass,b.executetype executetype,b.defaulttime defaulttime,b.alias,'' themename from T_PT_FWDXJBXX a left join T_PT_FWDXQXGLB c on a.fwdxjbId=c.serviceObjectId left join T_PT_FWJBXX b on b.serviceId=c.serviceID where a.state='0' and b.servicestate='0' and a.serviceObjectName=?"); 
			sbf.append(" union all ");
			sbf.append("select '0' type,e.controlobjectstate,e.serviceobjectname,e.serviceobjectip,e.serviceobjectport,''  serviceobjectnamef,e.servicename,e.serviceurl,e.invokeclass,e.executetype,e.defaulttime,alias,e.themename from (select a.controlobjectstate controlobjectstate,a.serviceobjectname serviceobjectname,a.serviceobjectip serviceobjectip,a.serviceobjectport serviceobjectport,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,'' alias,d.themename themename from T_PT_FWDXJBXX a left join T_PT_THEMEXX d on d.fwdxjbId=a.fwdxjbid and d.isstart='0' and a.state='0' and a.serviceObjectName=?) e where e.themename is not null");
			sbf.append(" union all ");
			sbf.append("select '1' type,d.controlobjectstate controlobjectstate,d.serviceobjectname serviceobjectname,d.serviceobjectip serviceobjectip,d.serviceobjectport serviceobjectport,'' serviceobjectnamef,'' servicename,'' serviceurl,'' invokeclass,'' executetype,'' defaulttime,'' alias,'' themename from T_PT_FWDXJBXX d where d.state='0' and d.serviceObjectName=?");
			log.info("客户端权限语句："+sbf);
			con = DataSourceManager.getConnection("defaultDataSource");//同步获取参数，只要是获取的数据项和返回的结果项
			pstmt = con.prepareStatement(sbf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, params);
			pstmt.setString(2, params);
			pstmt.setString(3, params);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BrokerException(e.getMessage());
		}
		return new DefaultCursor(con,pstmt,rs);
	}
}
