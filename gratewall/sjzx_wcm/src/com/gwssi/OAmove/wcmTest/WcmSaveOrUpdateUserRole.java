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

/**
 * 查询出jc_user_role与wcmuserrole的差异数据  然后调wcm接口对wcmuserrole表进行插入/更新
 * 查询出jc_user_role_delete2wcm表的数据 该表是jc_user_role表中app_code为WCM的数据被删除后会备份到jc_user_role_delete2wcm表
 * 		然后调wcm接口对wcmuserrole表进行 删除
 * 
 * 注意roleId 是wcmrole表的主键 不是我们的role_code 需要关联查询
 *    userIds 是wcmuser表的主键 也不是我们的user_id 并且可以是多个userId的合集  以','隔开 如 '2,3' 
 * @author yangzihao
 */
public class WcmSaveOrUpdateUserRole extends BaseStatefulScheduleWorker{

	private static Logger logger = Logger.getLogger(WcmSaveOrUpdateUserRole.class);
	private static	PropertiesUtil d1 = PropertiesUtil.getInstance("GwssiDataSource"); 
	
	@Override
	protected void execute() throws WCMException {
		
		String datasourceKey = AppConstants.DATASOURCE_KEY_WCM_SYNC;
		String yyjcname=d1.getProperty("db.yyjc.user.name");
		String dbwcm= d1.getProperty("db.wcm.user.name");
		
		//获取每日的实时时间以及站点编号
		String sql = "select realtime,siteId from "+dbwcm+".sm_wcmRealTime where id = '1'";
		List<Map<String, Object>> realtimeList = SpringJdbcUtil.query(datasourceKey,sql);
		String realTime = (String) realtimeList.get(0).get("REALTIME");
		
		//获取站点编号
		Integer SiteId = Integer.valueOf((String) realtimeList.get(0).get("SITEID"));
		
		//给用户绑定角色 
		StringBuilder sf = new StringBuilder()
		.append(" SELECT (SELECT ROLEID FROM WCMROLE WHERE ROLENAME = LOWER(T.ROLE_CODE)) AS ROLEID,")
		.append(" (SELECT USERID FROM WCMUSER WHERE USERNAME = LOWER(REPLACE(T.USER_ID, '@', '_'))) AS USERIDS,'")
		.append(SiteId)
		.append("' as SiteId FROM "+yyjcname+".JC_USER_ROLE T")
		.append(" WHERE APP_CODE = 'WCM' AND TO_CHAR(T.TIMESTAMP,'YYYY-MM-DD HH:MM')>CONCAT(TO_CHAR(SYSDATE-1,'YYYY-MM-DD'),")
		.append("'").append(realTime).append("')");
		
		List<Map<String, String>> list = SpringJdbcUtil.query2Wcm(datasourceKey, sf.toString());
		//调wcm接口方法
		String sServiceId = "wcm61_role", sMethodName="saveRoleUsers";
		Map<String, String> errorData = null;
		try {
			if(list.size() != 0 && list != null){
				for(Map<String, String> userRole : list){
					errorData = userRole;
					if(userRole.containsValue(null)){
						logger.info("保存用户角色数据出错 角色代码有空值情况 请检查后再试");
						throw new Exception("保存用户角色数据出错 角色代码有空值情况 请检查后再试");
					}
					WCMServiceCaller.Call(sServiceId,sMethodName,userRole,true);
				}
			}else{
				System.out.println("没有差异数据");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage()+"绑定用户角色数据出错-------"+errorData.toString());
			logger.info("绑定用户角色数据出错-------"+errorData.toString());
			e.printStackTrace();
		}
		
		//给用户解绑角色
		StringBuilder sfDel = new StringBuilder()
		.append(" SELECT (SELECT ROLEID FROM WCMROLE WHERE ROLENAME = LOWER(T.ROLE_CODE)) AS ROLEID , ")
		.append(" (SELECT USERID FROM WCMUSER WHERE USERNAME = LOWER(REPLACE(T.USER_ID, '@', '_'))) AS USERIDS,'")
		.append("-1")
		.append("' as SiteId FROM "+yyjcname+".JC_USER_ROLE_DELETE2WCM T")
		.append(" WHERE TO_CHAR(T.TIMESTAMP,'YYYY-MM-DD HH:MM')>CONCAT(TO_CHAR(SYSDATE-1,'YYYY-MM-DD'),")
		.append("'").append(realTime).append("')");
		
		List<Map<String, String>> listDel = SpringJdbcUtil.query2Wcm(datasourceKey, sfDel.toString());
		sMethodName = "removeUsersFromCurrRole";
		try {
			if(listDel.size() != 0 && listDel != null){
				for(Map<String, String> userRoleDel : listDel){
					errorData = userRoleDel;
					if(userRoleDel.containsValue(null)){
						logger.info("保存用户角色数据出错 角色代码有空值情况 请检查后再试");
						throw new Exception("保存用户角色数据出错 角色代码有空值情况 请检查后再试");
					}
					WCMServiceCaller.Call(sServiceId,sMethodName,userRoleDel,true);
				}
			}else{
				System.out.println("没有差异数据");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage()+"解绑用户角色数据出错-------"+errorData);
			logger.info("解绑用户角色数据出错-------"+errorData.toString());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws WCMException{
		WcmSaveOrUpdateUserRole WcmSaveOrUpdateUserRole = new WcmSaveOrUpdateUserRole();
		WcmSaveOrUpdateUserRole.execute();
	}
}
