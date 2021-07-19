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
 * ��ѯ��jc_user_role��wcmuserrole�Ĳ�������  Ȼ���wcm�ӿڶ�wcmuserrole����в���/����
 * ��ѯ��jc_user_role_delete2wcm������� �ñ���jc_user_role����app_codeΪWCM�����ݱ�ɾ����ᱸ�ݵ�jc_user_role_delete2wcm��
 * 		Ȼ���wcm�ӿڶ�wcmuserrole����� ɾ��
 * 
 * ע��roleId ��wcmrole������� �������ǵ�role_code ��Ҫ������ѯ
 *    userIds ��wcmuser������� Ҳ�������ǵ�user_id ���ҿ����Ƕ��userId�ĺϼ�  ��','���� �� '2,3' 
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
		
		//��ȡÿ�յ�ʵʱʱ���Լ�վ����
		String sql = "select realtime,siteId from "+dbwcm+".sm_wcmRealTime where id = '1'";
		List<Map<String, Object>> realtimeList = SpringJdbcUtil.query(datasourceKey,sql);
		String realTime = (String) realtimeList.get(0).get("REALTIME");
		
		//��ȡվ����
		Integer SiteId = Integer.valueOf((String) realtimeList.get(0).get("SITEID"));
		
		//���û��󶨽�ɫ 
		StringBuilder sf = new StringBuilder()
		.append(" SELECT (SELECT ROLEID FROM WCMROLE WHERE ROLENAME = LOWER(T.ROLE_CODE)) AS ROLEID,")
		.append(" (SELECT USERID FROM WCMUSER WHERE USERNAME = LOWER(REPLACE(T.USER_ID, '@', '_'))) AS USERIDS,'")
		.append(SiteId)
		.append("' as SiteId FROM "+yyjcname+".JC_USER_ROLE T")
		.append(" WHERE APP_CODE = 'WCM' AND TO_CHAR(T.TIMESTAMP,'YYYY-MM-DD HH:MM')>CONCAT(TO_CHAR(SYSDATE-1,'YYYY-MM-DD'),")
		.append("'").append(realTime).append("')");
		
		List<Map<String, String>> list = SpringJdbcUtil.query2Wcm(datasourceKey, sf.toString());
		//��wcm�ӿڷ���
		String sServiceId = "wcm61_role", sMethodName="saveRoleUsers";
		Map<String, String> errorData = null;
		try {
			if(list.size() != 0 && list != null){
				for(Map<String, String> userRole : list){
					errorData = userRole;
					if(userRole.containsValue(null)){
						logger.info("�����û���ɫ���ݳ��� ��ɫ�����п�ֵ��� ���������");
						throw new Exception("�����û���ɫ���ݳ��� ��ɫ�����п�ֵ��� ���������");
					}
					WCMServiceCaller.Call(sServiceId,sMethodName,userRole,true);
				}
			}else{
				System.out.println("û�в�������");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage()+"���û���ɫ���ݳ���-------"+errorData.toString());
			logger.info("���û���ɫ���ݳ���-------"+errorData.toString());
			e.printStackTrace();
		}
		
		//���û�����ɫ
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
						logger.info("�����û���ɫ���ݳ��� ��ɫ�����п�ֵ��� ���������");
						throw new Exception("�����û���ɫ���ݳ��� ��ɫ�����п�ֵ��� ���������");
					}
					WCMServiceCaller.Call(sServiceId,sMethodName,userRoleDel,true);
				}
			}else{
				System.out.println("û�в�������");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage()+"����û���ɫ���ݳ���-------"+errorData);
			logger.info("����û���ɫ���ݳ���-------"+errorData.toString());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws WCMException{
		WcmSaveOrUpdateUserRole WcmSaveOrUpdateUserRole = new WcmSaveOrUpdateUserRole();
		WcmSaveOrUpdateUserRole.execute();
	}
}
