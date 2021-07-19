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
 * 更新角色和人的关系
 * @author chaihw
 *
 */
public class SaveOrUpdateUserRole extends BaseStatefulScheduleWorker {

	private static Logger logger = Logger.getLogger(SaveOrUpdateUserRole.class);
	private static PropertiesUtil d1 = PropertiesUtil.getInstance("GwssiDataSource");

	@Override
	protected void execute() throws WCMException {

		String datasourceKey = AppConstants.DATASOURCE_KEY_WCM_SYNC;
		String yyjcname = d1.getProperty("db.yyjc.user.name");
		String dbwcm = d1.getProperty("db.wcm.user.name");
		String sServiceId = "wcm61_role";
		String sMethodName ="saveUsersOfSpecialRole";

		//需要删除的 暂时先不做 保留changruan 林部 黄贤度 等
/*		select to_number(wu.roleid) as RoleId ,wu.userid as UserIds  from wcmroleuser wu
		minus (select  to_number(t.role_code)  as RoleId ,  u.userid as UserIds
				  from DB_YYJc.jc_user_role t, szw_wcm.wcmuser u
				 where u.username = lower(replace(t.user_id, '@', '_'))
				   and t.app_code = 'WCM')*/
		
		
		
		//增量增加的 需要增加的 即jc_user_role存在而wcmuser可能存在但是不一致
		StringBuffer addsql = new StringBuffer();
		
		addsql.append("select  to_number(t.role_code)  as RoleId ,  to_char( wm_concat(u.userid) ) as UserIds from ");
		addsql.append(yyjcname).append(".").append("jc_user_role t, ");
		addsql.append(dbwcm).append(".").append("wcmuser u");
		addsql.append("  where u.username = lower(replace(t.user_id, '@', '_'))");
		addsql.append("   and t.app_code = 'WCM'  group by role_code");
		
/*		addsql.append("and t.role_code in ( select wcmadd.roleid  from (" );
		
		addsql.append("select  to_number(t.role_code)  as RoleId ,  u.userid as UserIds from ");
		addsql.append(yyjcname).append(".").append("jc_user_role t, ");
		addsql.append(dbwcm).append(".").append("wcmuser u");
		addsql.append("  where u.username = lower(replace(t.user_id, '@', '_'))");
		addsql.append("   and t.app_code = 'WCM'  minus (select to_number(wu.roleid) as RoleId ,wu.userid as UserIds  from wcmroleuser wu)");
		
		addsql.append("  ) wcmadd  )  group by role_code");*/
		List<Map<String, Object>> list = SpringJdbcUtil.query(datasourceKey, addsql.toString());
		
		
		
		
		
		
	if (list.size() != 0 && list != null) {
		for (Map<String, Object> userRole : list) {
			try {
				WCMServiceCaller.Call(sServiceId, sMethodName, userRole, true);
				logger.info("调用该接口失败>>:"+userRole);
			} catch (Exception e) {
				logger.info("调用该接口失败>>:"+userRole);
				e.printStackTrace();
			}
		}
	}else{
		
		logger.info("没有需要增加或者修改的");
	}
		
		
		
	}

	public static void main(String[] args) throws WCMException {
		SaveOrUpdateUserRole WcmSaveOrUpdateUserRole = new SaveOrUpdateUserRole();
		WcmSaveOrUpdateUserRole.execute();
	}
}
