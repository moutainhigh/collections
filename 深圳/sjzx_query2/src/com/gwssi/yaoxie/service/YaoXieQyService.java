package com.gwssi.yaoxie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;

import org.apache.log4j.Logger;

@Service
public class YaoXieQyService extends BaseService{
	private static  Logger log=Logger.getLogger(YaoXieQyService.class);
	
	
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
	public List<Map<String, Object>> queryCode_value(String...params) throws OptimusException {
		IPersistenceDAO dao_dc=getPersistenceDAO("dc_dc");
		IPersistenceDAO dao_code=getPersistenceDAO("dc_code");
		String type = "";
		String param = "";
		StringBuffer sql = new StringBuffer();
		List list = null;
		List<String> str = new ArrayList<String>();//参数准备
		if(params.length == 1){
			type = params[0];
			if("ancheyear".equals(type)){//年报年度
				 sql.append("select distinct ancheyear as value,ancheyear as text from dc_nb_gt_jbxx order by ancheyear desc ");
			}else if("regorg".equals(type)){ //管辖区域
				 sql.append("select distinct fjdm  as value, fjmc as text  from v_jg_ent ");
			}else if("enttype".equals(type)){ //商事主体类型
				 sql.append("select t.codeindex_code as value, t.codeindex_value as text from dc_code.DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00021' ");
			}
			 list = dao_dc.queryForList(sql.toString(),null);
		}
		 
		return list;
	}
	
	

	
	public String getYaoXieListCount(Map<String, String> map) throws OptimusException{
		
		
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}
	
	

	public List<Map> getYaoXieList(Map<String, String> map, HttpServletRequest httpRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql = new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(testsql.toString(),list);
		LogUtil.insertLog("药械企业查询", testsql, list.toString(), httpRequest, dao);
		return pageQueryForList;
	}

	
	
	
	
	public List<Map>  getSpQyDetail(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		sql.append("select * from V_DC_YAOXIE_QY_yz where entid=? ");
		list.add(pripid);
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		return pageQueryForList;
	}

	
	private void commonMethod(Map map, StringBuffer sql, List list) {
		if (map!=null) {
			String  SpQymark = (String) map.get("SpQymark");//食品企业许可证标识，0是全部，1是有证,2是无证
			//下面是分三种情况进行判断查询：0是全部，1是有证,2是无证
			if ("0".equals(SpQymark)) {//全部
				sql.append("select * from V_DC_YAOXIE_QY t where 1=1");
			}else if("1".equals(SpQymark)){//1是有证
				sql.append("select * from V_DC_YAOXIE_QY t where t.entid is not null ");
			}else if("2".equals(SpQymark)){//2是无证
				sql.append("select * from V_DC_YAOXIE_QY t where t.entid is  null ");
			}
			if (map.get("entname")!=null) {
				Object object2 = map.get("entname");
				if (object2.toString().trim().length()>0) {
						//sql.append(" and t.entname = ?");
						sql.append(" and instr(t.entname,?,1,1)<>0 ");
						
						list.add(object2.toString().trim());
				}	
			}
			if (map.get("regno")!=null) {
				Object object2 = map.get("regno");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.regno = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("regorg")!=null) {
				Object object2 = map.get("regorg");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.regorg = ?");
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("estdate_begin")!=null) {
				Object object2 = map.get("estdate_begin");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.estdate >= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("estdate_end")!=null) {
				Object object2 = map.get("estdate_end");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.estdate <= to_date(?,'yyyy-mm-dd')");
					list.add(object2.toString().trim());
				}
			}
			
		}
	}




	public List<String> queryListUserRolesByUserId() throws OptimusException{
		//获取当前登陆人dejuese
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user != null){
			String userId = user.getUserId();	
			List<Map> roleIdListByLoginName = new AuthService().getRoleIdByUsersRole(userId);
			
			ArrayList<String> roleCodes = new ArrayList<String>();
			for(Map roleMap : roleIdListByLoginName){
				String role_code = (String)roleMap.get("roleCode");
				roleCodes.add(role_code);
			}
			session.setAttribute("roleIdList", roleCodes);//将该用户角色List放入session
		
		}

		List<String> roleCodes = (List<String>)session.getAttribute("roleIdList");
		return roleCodes;
		
	}
	
	
}
