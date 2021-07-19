package com.gwssi.OAmove.wcmTest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.gwssi.AppConstants;
import com.gwssi.util.PropertiesUtil;
import com.gwssi.util.SpringJdbcUtil;
import com.trs.components.common.job.BaseStatefulScheduleWorker;
import com.trs.infra.common.WCMException;
import com.trs.web2frame.WCMServiceCaller;
import com.trs.web2frame.dispatch.Dispatch;

/**
 * 从jc_Public_people表查询出差异数据
 * 调wcm接口入库 
 * @author yangzihao
 */
public class WcmSaveOrUpdateUser extends BaseStatefulScheduleWorker{
	
	private static Logger logger = Logger.getLogger(WcmSaveOrUpdateUser.class);
	private static	PropertiesUtil d1 = PropertiesUtil.getInstance("GwssiDataSource"); 
	

	//查询差异数据 如果已经在目标表存在 则更新 否则新增 并且最后调用wcm接口方法 保存数据
	@Override
	protected void execute() throws WCMException {

		String datasourceKey = AppConstants.DATASOURCE_KEY_WCM_SYNC;
		String yyjcname=d1.getProperty("db.yyjc.user.name");
		String dbwcm= d1.getProperty("db.wcm.user.name");
		
		//获取每日的实时时间
		//String sql = "SELECT REALTIME FROM "+dbwcm+".SM_WCMREALTIME WHERE ID = '1'";
		//List<Map<String, Object>> realtimeList = SpringJdbcUtil.query(datasourceKey,sql);
		//String realTime = (String) realtimeList.get(0).get("REALTIME");
		
		//直接通过sql判断出新增还是更新 删除不考虑 因为单点登录都进不来 
		//根据时间戳判断差异数据
		StringBuilder sf = new StringBuilder()
				 .append(" SELECT * FROM (")
				.append("SELECT CASE WHEN LOWER(REPLACE(T.USER_ID, '@', '_')) NOT IN (SELECT USERNAME FROM "+dbwcm+".WCMUSER) THEN 0")
				.append(" ELSE (SELECT B.USERID FROM "+dbwcm+".WCMUSER B WHERE B.USERNAME = LOWER(REPLACE(T.USER_ID, '@', '_'))) END AS USERID, '30' AS STATUS,")
				.append("(SELECT GROUPID FROM "+dbwcm+".WCMGROUP WHERE ORGAN_CODE = T.DEPARTMENT_CODE) AS GROUPID,")
				.append(" LOWER(REPLACE(T.USER_ID, '@', '_')) AS USERNAME,T.USER_NAME AS NICKNAME,'TEMP@TRS.COM.CN' AS EMAIL,'trsadmin' AS PASSWORD")
				.append(" FROM "+yyjcname+".JC_PUBLIC_PEOPLE T")
				.append(" WHERE T.FLAG = '1'  and T.USER_ID <>T.department_code ")
				//.append("'").append(realTime).append("')   ")
				.append(" )  t WHERE 1=1 and upper(t.username) like '%_%'");
		
		System.out.println(sf);
		List<Map<String, String>> list = SpringJdbcUtil.query2Wcm(datasourceKey, sf.toString());
		System.out.println(list.size());
		//调wcm接口方法
	String sServiceId = "wcm61_user", sMethodName="save";
		String UserName = null;
		try {
			if(list.size() != 0 && list != null){
				for(Map<String, String> user : list){
					UserName = user.get("USERNAME");
					WCMServiceCaller.Call(sServiceId,sMethodName,user,true);
				}
			}else{
				logger.info("保存用户信息结果：没有差异数据");
				System.out.println("没有差异数据");
			}
		} catch (Exception e) {
			//System.err.println(e.getMessage()+"-------userName="+UserName);
			logger.info("保存用户信息出错  userName="+UserName);
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws WCMException{
		WcmSaveOrUpdateUser WcmSaveOrUpdateUser = new WcmSaveOrUpdateUser();
		WcmSaveOrUpdateUser.execute();
	}
}
