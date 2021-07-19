package com.gwssi.OAmove.wcmTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

/**
 * 查询出jc_role与wcmrole的差异数据
 * 调wcm接口入库 
 * @author yangzihao
 */
public class WcmSaveOrUpdateRole extends BaseStatefulScheduleWorker{

	private static Logger logger = Logger.getLogger(WcmSaveOrUpdateRole.class);
	private static	PropertiesUtil d1 = PropertiesUtil.getInstance("GwssiDataSource"); 

	
	@Override
	protected void execute() throws WCMException {

		String datasourceKey = AppConstants.DATASOURCE_KEY_WCM_SYNC;
		String yyjcname=d1.getProperty("db.yyjc.user.name");
		String dbwcm= d1.getProperty("db.wcm.user.name");
		
		//获取每日的实时时间
		String sql = "select realtime from "+dbwcm+".sm_wcmRealTime where id = '1'";
		List<Map<String, Object>> realtimeList = SpringJdbcUtil.query(datasourceKey,sql);
		String realTime = (String) realtimeList.get(0).get("REALTIME");
	
		//sql中直接取当前时间的前一天的实时时间后更新的数据
		StringBuilder sf = new StringBuilder()
		.append(" SELECT CASE WHEN T.ROLE_CODE NOT IN (SELECT ROLENAME FROM "+dbwcm+".WCMROLE) THEN 0")
		.append(" ELSE (SELECT B.ROLEID FROM "+dbwcm+".WCMROLE B WHERE B.ROLENAME = T.ROLE_CODE) END AS ROLEID,'0' AS ROLETYPE,")
		.append(" T.ROLE_CODE AS ROLENAME,T.ROLE_NAME AS ROLEDESC,'0' AS ROLERANGE FROM "+yyjcname+".JC_ROLE T ")
		.append(" WHERE T.FLAG = '1' AND APP_CODE = 'WCM' AND TO_CHAR(T.TIMESTAMP,'YYYY-MM-DD HH:MM')<CONCAT(TO_CHAR(SYSDATE-1,'YYYY-MM-DD'),")
		.append("'").append(realTime).append("')");

		List<Map<String, String>> list = SpringJdbcUtil.query2Wcm(datasourceKey, sf.toString());
		//调wcm接口方法
		String sServiceId = "wcm61_role", sMethodName="save";
		String RoleCode = null;
		try {
			if(list.size() != 0 && list != null){
				for(Map<String, String> role : list){
					RoleCode = role.get("ROLENAME");
					WCMServiceCaller.Call(sServiceId,sMethodName,role,true);
				}
			}else{
				System.out.println("没有差异数据");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage()+"保存角色信息出错：userName="+RoleCode);
			logger.info("保存角色信息出错：userName="+RoleCode);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws WCMException{
		WcmSaveOrUpdateRole wcmSaveOrUpdateRole = new WcmSaveOrUpdateRole();
		wcmSaveOrUpdateRole.execute();
	}
}
