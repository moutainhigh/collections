package com.gwssi.optimus.plugin.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmGrantAuthBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmServicesBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Service
public class ServiceService extends BaseService{
	/**
	 * 权限管理 --服务管理 （获取所有的服务）
	 * @param sms （为null 表示超级管理员 获取全部菜单）
	 * @return
	 * @throws OptimusException
	 */
	public List findMenu(SmServicesBO sms) throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		List<String> str = new ArrayList<String>();	//？传值
		 StringBuffer sql = new StringBuffer();
		 if(sms==null||sms.equals("")||sms.equals(null)){
			 sql.append("select * from SM_SERVICES where EFFECTIVE_MARKER = ? ")
			// .append(AppConstants.EFFECTIVE_Y)
			 .append(" order by SERVICE_NO");
			 str.add(AppConstants.EFFECTIVE_Y);
		 }else{
				sql.append(" select * from  SM_SERVICES where 1=1 and EFFECTIVE_MARKER = ?  ");
				str.add(AppConstants.EFFECTIVE_Y);
				if(sms != null){
					String systemName = sms.getServiceName();
					if(!"".equals(systemName)){
						sql.append(" and  SERVICE_NAME like ? ");
						str.add("%"+systemName+"%");
					}
					String pk = sms.getPkSysIntegration();
					if(!"".equals(pk)){
						sql.append(" and  PK_SYS_INTEGRATION = ? ");
						str.add(pk);
					}
				}
				sql.append(" order by MODIFIER_TIME DESC"); 
		 }
		List<Map> list1 =dao.pageQueryForList(sql.toString(), str);
		
		
		//转换serviceNOtoString
		List list2 =ServiceNotoString(list1);
		return list2;
	}
	/**
	 * 把serviceNo转换为String 并把结果返回
	 * @param list
	 * @return
	 */
	public List ServiceNotoString(List<Map> list){
		for(Map map1:list){
		String serviceNo=	 map1.get("serviceNo").toString();
			map1.remove("serviceNo");
			map1.put("serviceNo", serviceNo);
		}
		return list;
	}
	public List findMenu(List<String> pksys, SmServicesBO sms) throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		List<String> str = new ArrayList<String>();	//？传值
		 StringBuffer sql = new StringBuffer();
		 if(sms==null||sms.equals("")||sms.equals(null)){
			 sql.append("select * from SM_SERVICES where EFFECTIVE_MARKER = ? ");
			sql.append("  and PK_SYS_INTEGRATION in (");
			sql.append(prepareSqlIn(pksys.size()));
			sql.append(")")
			 .append(" order by SERVICE_NO");
			 str.add(AppConstants.EFFECTIVE_Y);
				for(String s:pksys){
					str.add(s);
				}
		 }else{
				sql.append(" select * from  SM_SERVICES where 1=1 and EFFECTIVE_MARKER = ?  ");
				sql.append("  and PK_SYS_INTEGRATION in (");
				sql.append(prepareSqlIn(pksys.size()));
				sql.append(")");
				 str.add(AppConstants.EFFECTIVE_Y);
					for(String s:pksys){
						str.add(s);
					}				
				if(sms != null){
					String systemName = sms.getServiceName();
					if(!"".equals(systemName)){
						sql.append(" and  SERVICE_NAME like ? ");
						str.add("%"+systemName+"%");
					}
					String pk = sms.getPkSysIntegration();
					if(!"".equals(pk)){
						sql.append(" and  PK_SYS_INTEGRATION = ? ");
						str.add(pk);
					}
				}
				sql.append(" order by MODIFIER_TIME DESC"); 
		 }
		List<Map> list1 =dao.pageQueryForList(sql.toString(), str);
		List list2 =ServiceNotoString(list1);
		return list2;
	}		

	/**
	 * 获取当前前用户的所有角色
	 * @param roleList
	 * @return
	 * @throws OptimusException 
	 */
	public List<SmRoleBO> findRolebyPKList(List roleList) throws OptimusException {
        if(roleList==null)
            return null;
        List <Map> rolel=roleList;
        List<String> role =new ArrayList();
        for(Map r1:rolel){
        	role.add((String)r1.get("roleCode"));
        }
        
		StringBuffer sql = new StringBuffer();

		 sql.append("select * from SM_ROLE   ");
			sql.append("where role_code in (");
			sql.append(prepareSqlIn(roleList.size()));
			sql.append(")");
			sql.append(" and  EFFECTIVE_MARKER = ?");
			role.add(AppConstants.EFFECTIVE_Y);
			IPersistenceDAO dao = getPersistenceDAO();
			List <SmRoleBO> smf =dao.queryForList(SmRoleBO.class, sql.toString(),role);
   
       return smf;
	}
	/**
	 * 封装sql in 查询条件
	 * 
	 * @param length
	 * @return (?,?)
	 */	
	private StringBuffer prepareSqlIn(int length) {
		StringBuffer builder = new StringBuffer();
		for (int i = 0; i < length;) {
			builder.append("?");
			if (++i < length) {
				builder.append(",");
			}
		}
		return builder;
	}
	/**
	 * 获取admin所拥有的tree
	 * @param pksys
	 * @return
	 * @throws OptimusException
	 */
	public List<SmSysIntegrationBO> findAdminSmSysInte(List<String> pksys) throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();	
		 sql.append("select * from SM_SYS_INTEGRATION ");
		 sql.append(" where PK_SYS_INTEGRATION in (");
		
		sql.append(prepareSqlIn(pksys.size()));
		sql.append(")");
		sql.append(" and  EFFECTIVE_MARKER = ?");
		pksys.add(AppConstants.EFFECTIVE_Y);
		 sql.append(" order by ORDER_NO");	

		List<SmSysIntegrationBO> l1= dao.queryForList(SmSysIntegrationBO.class, sql.toString(),pksys);
		 return l1;
	}
	/**
	 * 把SmSysIntegrationBO转换为TreeList
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> changSmSysIntegrationBOToTreeList(
			List<SmSysIntegrationBO> list) {
		List system = new ArrayList();
		Map systemN=null;
		for(SmSysIntegrationBO sm:list){	
			systemN=new HashMap();
			systemN.put("text", sm.getSystemName());
			systemN.put("value", sm.getPkSysIntegration());
			system.add(systemN);
		}
		
		return system;
	}
	public List<SmSysIntegrationBO> findSuperAdminSmSysInte() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();	
		List<String> str = new ArrayList<String>();	//？传值
		
		sql.append("select * from SM_SYS_INTEGRATION ");
		sql.append(" where  EFFECTIVE_MARKER = ?");
		str.add(AppConstants.EFFECTIVE_Y);
		sql.append(" order by ORDER_NO");	

		List<SmSysIntegrationBO> l1= dao.queryForList(SmSysIntegrationBO.class, sql.toString(),str);
		 return l1;
	}	
	/**
	 * 协议类型
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> findAgreementTypeTree() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		  List <String> str= new ArrayList<String>();

			 sql.append(" select  code as value, name  as text  from sm_dm_service ")
			 .append(" order by code");

		
		List list1 =dao.queryForList(sql.toString(),null);
		return list1;
	}
	/**
	 * 获取厂商信息
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> findServicePkSmFirme() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		  List <String> str= new ArrayList<String>();

			 sql.append(" select  PK_SM_FIRM as value, FIRM_NAME  as text  from SM_FIRM ");
			 sql.append(" where  EFFECTIVE_MARKER = ?");
			str.add(AppConstants.EFFECTIVE_Y);
			 sql.append(" order by PK_SM_FIRM");

		
		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}
	/**
	 * 获取联系人主键
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> findServicePkSmLikeman() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		  List <String> str= new ArrayList<String>();

			 sql.append(" select  PK_SM_LIKEMAN as value, SM_LIKEMAN  as text  from SM_LIKEMAN ");
			sql.append(" where  EFFECTIVE_MARKER = ?");
			str.add(AppConstants.EFFECTIVE_Y);
			 sql.append(" order by PK_SM_LIKEMAN");

		
		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}
	public List<Map<String, Object>> findServicePkSmLikeman(String pkSmFirm) throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		  List <String> str= new ArrayList<String>();

		sql.append(" select  PK_SM_LIKEMAN as value, SM_LIKEMAN  as text  from SM_LIKEMAN ");
		sql.append(" where  EFFECTIVE_MARKER = ?");
		str.add(AppConstants.EFFECTIVE_Y);
		sql.append("   and  PK_SM_FIRM = ?");
		str.add(pkSmFirm);
		sql.append(" order by PK_SM_LIKEMAN");

		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}	
	/**
	 * 权限管理--服务管理 （新增服务）
	 * @param smfunction
	 * @throws OptimusException
	 */
	public void insertSmServicesBO(SmServicesBO smfunction) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(smfunction);
	}
	/**
	 * 通过主键获取服务表
	 * @param pkSmServices
	 * @return
	 * @throws OptimusException
	 */
	public SmServicesBO findSmServicesByPK(String pkSmServices) throws OptimusException {
        IPersistenceDAO dao = getPersistenceDAO();
        SmServicesBO sms = dao.queryByKey(SmServicesBO.class, pkSmServices);
        return sms;
	}
	/**
	 * 修改smservices
	 * @param smfunction
	 * @throws OptimusException
	 */
	public void updateSmServices(SmServicesBO smfunction) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(smfunction);
	}
	/**
	 * 获取已经分配的SERVICES
	 * @param pkRole
	 * @return
	 * @throws OptimusException 
	 */
	public List findAuthService(String pkRole) throws OptimusException {
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select PK_SYS_INTEGRATION as id from SM_GRANT_AUTH where PK_SM_SERVICES= ? ";
		List<String> str = new ArrayList<String>();	//？传值
		str.add(pkRole);
        List authFuncList = dao.queryForList(sql, str);
        return authFuncList;
	}
	public List<Map> findAllSystem() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		  List <String> str= new ArrayList<String>();
		sql.append(" select SYSTEM_NAME as NAME,PK_SYS_INTEGRATION as ID ,0 as PID  from SM_SYS_INTEGRATION ");
		sql.append(" where  EFFECTIVE_MARKER = ?");
		str.add(AppConstants.EFFECTIVE_Y);
		List<Map> RoleList = dao.queryForList(sql.toString(), str);	
		return RoleList;
	}
	
	/**
	 * 获取某个服务的授权信息
	 * @param pkSmServices
	 * @return
	 * @throws OptimusException 
	 */
	public List<SmGrantAuthBO> findSmgrantAuthByPkSmSer(String pkSmServices) throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List <String> str= new ArrayList<String>();
		if(StringUtils.isEmpty(pkSmServices))
		
			sql.append("select * from SM_GRANT_AUTH  where PK_SM_SERVICES= ?");
			str.add(pkSmServices);
				
		List <SmGrantAuthBO> list1 =dao.queryForList(SmGrantAuthBO.class, sql.toString(),str);
		return list1;	
	}

	/**
	 *删除某个系统的授予信息
	 * @param pkSmServices
	 * @throws OptimusException
	 */
	public void deleteSmGrantAuthBO(String pkSmServices) throws OptimusException {
		List<String> str = new ArrayList<String>();	//？传值
	    IPersistenceDAO dao = getPersistenceDAO();
	    StringBuffer sql = new StringBuffer();
	    sql.append("delete from SM_GRANT_AUTH  where PK_SM_SERVICES= ?");
	    str.add(pkSmServices);
	    dao.execute(sql.toString(), str);
	}	
	
	/**
	 * 新增某个系统的授权信息
	 * @param smfunction
	 * @throws OptimusException
	 */
	public void insertSmGrantAuthBO(SmGrantAuthBO sm) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(sm);
	}	
}
