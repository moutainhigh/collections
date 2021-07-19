package com.gwssi.otherselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.otherselect.controller.OtherSelectController;

@Service(value = "otherSelectService")
public class OtherSelectService extends BaseService{
	
	private static Logger logger = Logger.getLogger(OtherSelectService.class);	
	
	
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	//private static final String DATASOURS_DB_YYJC ="db_yyjc";
	Properties properties = ConfigManager.getProperties("UserRolesGet");
	String databasename = properties.getProperty("yyjc.database.username");
	String db_query = properties.getProperty("db_query.database.username");
	
    /**
     * 失信被执行人查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getSXList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select t.ID, t.INAME, t.SEX_NAME,t.AGE, t.CARDNUM, t.COURT_NAME, to_char(t.REG_DATE,'yyyy-MM-dd HH:mm:ss') as REG_DATE,t.S_EXT_SEQUENCE from DC_BL_LAOLAI t where  1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("iname");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and INAME = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("cardnum")!=null) {
				Object object2 = map.get("cardnum");
				if (object2.toString().trim().length()>0) {
					sql.append(" and CARDNUM = ?");
					list.add(object2.toString().trim());
				}
			}
		}
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		/*sql.append(" ORDER BY REG_DATE desc");*/
		List<Map> pageQueryForList = dao.pageQueryForList(testsql.toString(),list);
		LogUtil.insertLog("失信被执行人查询", testsql, list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}

	/**
	 * 根据sExtSequence获取失信被执行人的查询信息详情
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getSXQueryById(String sExtSequence) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql=" select t.INAME, t.CARDNUM, t.SEX_NAME, t.AGE, case t.PARTY_TYPE_NAME when '0' then '自然人' when '1' then '企业' else  '其他'end as partyTypeName, t.PERFORMANCE, t.DISREPUT_TYPE_NAME,t.CASE_CODE, t.COURT_NAME, t.GIST_CID, t.GIST_UNIT, t.BUESINESSENTITY, t.AREA_NAME,t.PERFORMED_PART, t.UNPERFORM_PART, to_char(t.PUBLISH_DATE, 'yyyy-MM-dd HH:mm:ss') as PUBLISH_DATE, to_char(t.REG_DATE, 'yyyy-MM-dd HH:mm:ss') as REG_DATE"
					+" from DC_BL_LAOLAI t where t.S_EXT_SEQUENCE = ?";
		List list=new ArrayList();
		list.add(sExtSequence);
		return dao.pageQueryForList(sql, list).get(0);
	}
	
	public List<Map> queryHPList(Map<String, String> str, HttpServletRequest httpServletRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from DC_BL_ENTER_BLACK t where S_EXT_VALIDFLAG = '1' ");
		if(!"".equals(str.get("entname"))){
			sql.append("and t.entname like ?");
			params.add("%" + str.get("entname") + "%");
		}
		if(!"".equals(str.get("regno"))){
			sql.append("and t.regno = ?");
			params.add(str.get("regno"));			
		}
	/*	if(!"".equals(str.get("enttype"))){
			sql.append("and t.enttype = ?");
			params.add(str.get("enttype"));			
		}*/
		if(!"".equals(str.get("name"))){
			sql.append("and t.name = ?");
			params.add(str.get("name"));			
		}
	/*	if(!"".equals(str.get("certype"))){
			sql.append("and t.certype = ?");
			params.add(str.get("certype"));			
		}*/
		if(!"".equals(str.get("cerno"))){
			sql.append("and t.cerno = ?");
			params.add(str.get("cerno"));			
		}
		if(!"".equals(str.get("revdate_start"))){
			sql.append("and t.revdate >= to_date(?,'yyyy-mm-dd')");
			params.add(str.get("revdate_start"));		
		}
		if(!"".equals(str.get("revdate_end"))){
			sql.append("and t.revdate <= to_date(?,'yyyy-mm-dd')");
			params.add(str.get("revdate_end"));			
		}
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(testsql.toString(), params);
		LogUtil.insertLog("黑牌企业查询", testsql, params.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	public String queryHPListCount(Map<String, String> str) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from DC_BL_ENTER_BLACK t where S_EXT_VALIDFLAG = '1' ");
		if(!"".equals(str.get("entname"))){
			sql.append("and t.entname like ?");
			params.add("%" + str.get("entname") + "%");
		}
		if(!"".equals(str.get("regno"))){
			sql.append("and t.regno = ?");
			params.add(str.get("regno"));			
		}
	/*	if(!"".equals(str.get("enttype"))){
			sql.append("and t.enttype = ?");
			params.add(str.get("enttype"));			
		}*/
		if(!"".equals(str.get("name"))){
			sql.append("and t.name = ?");
			params.add(str.get("name"));			
		}
	/*	if(!"".equals(str.get("certype"))){
			sql.append("and t.certype = ?");
			params.add(str.get("certype"));			
		}*/
		if(!"".equals(str.get("cerno"))){
			sql.append("and t.cerno = ?");
			params.add(str.get("cerno"));			
		}
		if(!"".equals(str.get("revdate_start"))){
			sql.append("and t.revdate >= to_date(?,'yyyy-mm-dd')");
			params.add(str.get("revdate_start"));		
		}
		if(!"".equals(str.get("revdate_end"))){
			sql.append("and t.revdate <= to_date(?,'yyyy-mm-dd')");
			params.add(str.get("revdate_end"));			
		}
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), params).get(0).get("count").toString();
	}
	
	/**
     * 一人责任有限公司查询 列表展示
     * @param map
	 * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getYRList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from DC_BL_ENTER_ONEPERSON t where t.s_ext_validflag = '1' ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("entname");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and ENTNAME = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("regno")!=null) {
				Object object2 = map.get("regno");
				if (object2.toString().trim().length()>0) {
					sql.append(" and REGNO = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("name")!=null) {
				Object object2 = map.get("name");
				if (object2.toString().trim().length()>0) {
					sql.append(" and NAME = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("certype")!=null) {
				Object object2 = map.get("certype");
				if (object2.toString().trim().length()>0) {
					sql.append(" and CERTYPE = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("regno")!=null) {
				Object object2 = map.get("regno");
				if (object2.toString().trim().length()>0) {
					sql.append(" and REGNO = ?");
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("inv")!=null) {
				Object object2 = map.get("inv");
				if (object2.toString().trim().length()>0) {
					sql.append(" and INV = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("certypeInv")!=null) {
				Object object2 = map.get("certypeInv");
				if (object2.toString().trim().length()>0) {
					sql.append(" and CERTYPE_INV = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("cernoInv")!=null) {
				Object object2 = map.get("cernoInv");
				if (object2.toString().trim().length()>0) {
					sql.append(" and CERNO_INV = ?");
					list.add(object2.toString().trim());
				}
			}
		}
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(testsql.toString(),list);
		LogUtil.insertLog("一人责任有限公司查询", testsql, list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getYRListCount(Map map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from DC_BL_ENTER_ONEPERSON t where t.s_ext_validflag = '1' ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("entname");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and ENTNAME = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("regno")!=null) {
				Object object2 = map.get("regno");
				if (object2.toString().trim().length()>0) {
					sql.append(" and REGNO = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("name")!=null) {
				Object object2 = map.get("name");
				if (object2.toString().trim().length()>0) {
					sql.append(" and NAME = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("certype")!=null) {
				Object object2 = map.get("certype");
				if (object2.toString().trim().length()>0) {
					sql.append(" and CERTYPE = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("regno")!=null) {
				Object object2 = map.get("regno");
				if (object2.toString().trim().length()>0) {
					sql.append(" and REGNO = ?");
					list.add(object2.toString().trim());
				}
			}
			
			if (map.get("inv")!=null) {
				Object object2 = map.get("inv");
				if (object2.toString().trim().length()>0) {
					sql.append(" and INV = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("certypeInv")!=null) {
				Object object2 = map.get("certypeInv");
				if (object2.toString().trim().length()>0) {
					sql.append(" and CERTYPE_INV = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("cernoInv")!=null) {
				Object object2 = map.get("cernoInv");
				if (object2.toString().trim().length()>0) {
					sql.append(" and CERNO_INV = ?");
					list.add(object2.toString().trim());
				}
			}
		}
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}

	public String getSXListCount(Map map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select t.ID, t.INAME, t.SEX_NAME,t.AGE, t.CARDNUM, t.COURT_NAME, to_char(t.REG_DATE,'yyyy-MM-dd HH:mm:ss') as REG_DATE,t.S_EXT_SEQUENCE from DC_BL_LAOLAI t where  1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("iname");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and INAME = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("cardnum")!=null) {
				Object object2 = map.get("cardnum");
				if (object2.toString().trim().length()>0) {
					sql.append(" and CARDNUM = ?");
					list.add(object2.toString().trim());
				}
			}
		}
	String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}

	
	 /**
     * 餐饮服务许可证信息查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getCYFWList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from ( select t.id,t.certificate_no,t.unit_name,t.legal_response from dc_f1_CYFW_ENT_BASIC_INFO t WHERE 1=1 "); //t.full_address,t.category,t.remark,substr(to_char(t.to_effect_limit,'yyyy-MM-dd'),0,10) as to_effect_limit
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("unitName");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.unit_name like ?");
					list.add("%"+object.toString().trim()+"%");
				}
			}
			if (map.get("certificateNo")!=null) {
				Object object2 = map.get("certificateNo");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.certificate_no = ?");
					list.add(object2.toString().trim());
				}
			}
		}
		sql.append(" ) where rownum<=10 ");
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("餐饮服务许可证信息查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	
	 /**
     * 食品流通许可证信息查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getSPFWList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from (select t.id,t.splt_license_no,t.company_name,t.responsible_person from dc_F1_SPLT_ENT_BASIC_INFO t  WHERE 1=1 ");//,t.business_place,a.operation_type,'自'||substr(to_char(t.applicant_date,'yyyy-MM-dd'),0,10)||'至'||substr(to_char(t.valid_until,'yyyy-MM-dd'),0,10)  as validdate   left join dc_F1_SPLT_OPERATION_TYPE a on t.id = a.splt_ent_basic_info
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("spltLicenseNo");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.splt_license_no = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("companyName")!=null) {
				Object object2 = map.get("companyName");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.company_name like ?");
					list.add("%"+object2.toString().trim()+"%");
				}
			}
		}
		sql.append(") where rownum<=10 ");
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("食品流通许可证信息查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	/**
	 * 餐饮许可证信息详情
	 * @param sExtSequence
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getCYQueryById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql=" select t.certificate_no,t.unit_name,t.legal_response,t.full_address,t.category,"
				+ "t.remark,substr(to_char(t.to_effect_limit,'yyyy-MM-dd'),0,10) as to_effect_limit "
				+ "from dc_f1_CYFW_ENT_BASIC_INFO t WHERE t.id = ? ";
		List list=new ArrayList();
		list.add(id);
		List<Map> pageQueryForList = dao.pageQueryForList(sql, list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList.get(0);
		}
		return null;
	}
	
	/**
	 * 食品流通许可证信息详情
	 * @param sExtSequence
	 * @return
	 * @throws OptimusException
	 */
	public Map getSPQueryById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql=" select t.splt_license_no,t.company_name,t.business_place,t.responsible_person,"
				+ "a.operation_type,'自'||substr(to_char(t.applicant_date,'yyyy-MM-dd'),0,10)||'至'||substr(to_char(t.valid_until,'yyyy-MM-dd'),0,10)  as validdate "
				+ "from dc_F1_SPLT_ENT_BASIC_INFO t left join dc_F1_SPLT_OPERATION_TYPE a on t.id = a.splt_ent_basic_info where t.id = ? ";
		List list=new ArrayList();
		list.add(id);
		List<Map> pageQueryForList = dao.pageQueryForList(sql, list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList.get(0);
		}
		return null;
	}

	public List<Map> getLogList(Map<String, String> map,
			HttpServletRequest httpRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from v_log_operation t  WHERE 1=1 ");//,t.business_place,a.operation_type,'自'||substr(to_char(t.applicant_date,'yyyy-MM-dd'),0,10)||'至'||substr(to_char(t.valid_until,'yyyy-MM-dd'),0,10)  as validdate   left join dc_F1_SPLT_OPERATION_TYPE a on t.id = a.splt_ent_basic_info
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("userId");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.user_id like ? ");
					list.add("%"+object.toString().trim().toUpperCase()+"%");
				}
			}
			if (map.get("conditionTtile")!=null) {
				Object object2 = map.get("conditionTtile");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.logtype = ? ");
					list.add(object2.toString().trim());
				}
			}
			
			String logStart = map.get("logStart");
			if(StringUtils.isNotEmpty(logStart)){
				sql.append(" and to_date(?,'yyyy-MM-dd') <= to_date(to_char(t.timestamp,'yyyy-MM-dd'),'yyyy-MM-dd') ");
				list.add(logStart.toString().trim());
			}
			
			String logEnd = map.get("logEnd");
			if(StringUtils.isNotEmpty(logEnd)){
				sql.append(" and to_date(?,'yyyy-MM-dd') >= to_date(to_char(t.timestamp,'yyyy-MM-dd'),'yyyy-MM-dd') ");
				list.add(logEnd.toString().trim());
			}
			
			String search = map.get("search");
			if(StringUtils.isNotEmpty(search)){
				sql.append(" and operation_sql_paratemer like ? ");
				list.add("%"+search.toString().trim()+"%");
			}
			
		}
		sql.append(" and t.user_id !=  'CHANGRUAN@SZAIC' order by t.timestamp desc");
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		return pageQueryForList;
	}

	/**
	 * 综合查询日志详细信息
	 * @param sExtSequence
	 * @return
	 * @throws OptimusExceptions
	 */
	public Map getLogQueryById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql="select t.operation_sql,t.operation_sql_paratemer from log_operation t where t.id = ? ";
		List list=new ArrayList();
		list.add(id);
		List<Map> pageQueryForList = dao.pageQueryForList(sql, list);
		
		/*if(pageQueryForList !=null && pageQueryForList.size()>0){
			Map map = pageQueryForList.get(0);
			String operationSql = (String)map.get("operationSql");
			StringBuffer newOperationSql = new StringBuffer();
			
			for(int i = 0;i<operationSql.length()/60;i++){
				newOperationSql.append(operationSql.substring(i*60, (i*60+60)));
				newOperationSql.append("<br>");
			}
			
			map.put("operationSql", newOperationSql.toString());
			
			return map;
		}*/
		
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList.get(0);
		}
		return null;
	}

	public List<Map> getRoleList(Map<String, String> map) throws OptimusException {
		//DATASOURS_DB_YYJCs
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select t.rolecode,t.rolename,t.roledesc from "+databasename+".ZHCX_ROLE t where t.flag = '1'  ");
		List list=new ArrayList();
		
		if (map!=null) {
			Object object = map.get("rolename");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.rolename like ? ");
					list.add("%"+object.toString().trim().toUpperCase()+"%");
				}
			}
			
			Object object2 = map.get("rolecode");
			if (object2!=null) {
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.rolecode like ? ");
					list.add("%"+object2.toString().trim().toUpperCase()+"%");
				}
			}
		}
		
		List<Map> pageQueryForList = dao.queryForList(sql.toString(), list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList;
		}
		return null;
	}

	public String deleteRolePerson(String userId,String roleCode) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		
		if("".equals(roleCode)){
			String sqlUp = "update "+databasename+".jc_public_people set flag = '0' where user_id = ? ";
			List list1=new ArrayList();
			list1.add(userId);
			
			String sqlDel="delete from "+databasename+".jc_user_role where user_id = ? and app_code = 'ZHCX' and role_code = ? ";
			
			List list2=new ArrayList();
			list2.add(userId);
			list2.add(roleCode);
			try {
				dao.execute(sqlUp, list1);
				dao.execute(sqlDel, list2);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return "1";
		}else{
			String sql="delete from "+databasename+".jc_user_role where user_id = ? and app_code = 'ZHCX' and role_code = ? ";
			List list=new ArrayList();
			list.add(userId);
			list.add(roleCode);
			try {
				dao.execute(sql, list);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return "1";
		}
	}

	public List<Map> RolePersonList(String roleCode,
			HttpServletRequest httpRequest,Map<String, String> map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		
		StringBuffer sql = new StringBuffer("select t.user_id,t.user_name,b.name,wm_concat(to_char(d.rolename)) as  role_names from "+databasename+".jc_public_people t left join "+databasename+".jc_user_role a on t.user_id = a.user_id "
				+ "left join "+databasename+".jc_public_department b on t.department_code = b.code left join "+databasename+".ZHCX_ROLE d on a.role_code = d.rolecode where  a.app_code = 'ZHCX' and t.flag = '1' and a.role_code is not null  "); 
		
		List list=new ArrayList();
		if(!"".equalsIgnoreCase(roleCode)){
			sql.append("  and a.role_code = ? ");
			list.add(roleCode);
		}
		
		
		if (map!=null) {
			Object object = map.get("userId");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.user_id like ? ");
					list.add("%"+object.toString().trim().toUpperCase()+"%");
				}
			}
			
			Object object2 = map.get("userName");
			if (object2!=null) {
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.user_name like ? ");
					list.add("%"+object2.toString().trim().toUpperCase()+"%");
				}
			}
			
			Object object3 = map.get("deptName");
			if (object3 != null) {
				if (object3.toString().trim().length() > 0) {
					sql.append(" and b.name like ? ");
					list.add("%"+object3.toString().trim()+"%");
				}
			}
		}
		
		sql.append("   group by t.user_id, t.user_name, b.name ");
		
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(), list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList;
		}
		return null;
	}

	/**
	 * 用户权限关系模糊匹配用品姓名
	 * @param userInfo
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryUserInfo(String userInfo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql = "select t.user_id from "+databasename+".jc_public_people t where t.user_id like ? or t.user_name like ?";
		List list=new ArrayList();
		list.add("%"+userInfo+"%");
		list.add("%"+userInfo+"%");
		List<Map> pageQueryForList = dao.pageQueryForList(sql, list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList;
		}
		return null;
	}

	/**
	 * 用户权限关系 保存人员角色关系
	 * @param userList
	 * @param roleCode
	 * @return
	 * @throws OptimusException 
	 */
	public void saveUserInfo(String userList, String roleCode) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		
		String[] split = userList.split(",");
		
		String userId = "";
		String sql = "insert into "+databasename+".jc_user_role t select sys_guid(),?,?,'ZHCX','4',sysdate from dual";
		
		for(int i=0;i<split.length;i++){
			userId = split[i];
			List list=new ArrayList();
			list.add(userId);
			list.add(roleCode);
			dao.execute(sql, list);
		}
	}

	/**
	 * 综合查询角色下功能查询列表
	 * @param roleCode
	 * @param httpRequest
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> RolePowerList(String roleCode,
			HttpServletRequest httpRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		String sql = "select a.function_code,b.function_name from "+db_query+".sm_role_func a left join "+db_query+".sm_function b on a.function_code = b.function_code where a.role_code = ?";
		
		List list=new ArrayList();
		list.add(roleCode);
		
		List<Map> pageQueryForList = dao.queryForList(sql, list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList;
		}
		return null;
	}

	public String deleteRolePower(String functionCode, String roleCode) {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql="delete from "+db_query+".sm_role_func where function_code = ? and role_code = ? ";
		List list=new ArrayList();
		list.add(functionCode);
		list.add(roleCode);
		try {
			int execute = dao.execute(sql, list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "1";
	}

	public List<Map> QueryFuncRole(String roleCode) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select functioncode,functionname from "+databasename+".ZHCX_FUNC_CODE where functionCode not in (select function_code from "+db_query+".sm_role_func where role_code = ?)";
		List list=new ArrayList();
		list.add(roleCode);
		List<Map> pageQueryForList = dao.queryForList(sql, list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList;
		}
		return null;
	}

	public String SaveFuncRole(String roleCode, String functionCode) {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "insert into "+db_query+".sm_role_func select ?,? from dual ";
		List list=new ArrayList();
		list.add(functionCode);
		list.add(roleCode);
		try {
			int execute = dao.execute(sql, list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "1";
	}

	public String AddRole(String roleCode, String roleName, String roleDesc) {
		IPersistenceDAO dao = getPersistenceDAO();
		//在zhcx_role表中添加新角色信息
		String sql = "insert into "+databasename+".zhcx_role select ?,?,?,sysdate,'1' from dual ";
		List list=new ArrayList();
		list.add(roleCode);
		list.add(roleName);
		list.add(roleDesc);
		
		//给新角色赋初始化权限
		String sql1 = "insert into "+db_query+".sm_role_func select 'ZHCX01',?  from dual";
		List list1=new ArrayList();
		list1.add(roleCode);
		
		try {
			dao.execute(sql, list);
			dao.execute(sql1, list1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "1";
	}
	
	
	/**
	 * 综合查询角色下部门查询列表
	 * @param roleCode
	 * @param httpRequest
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> QueryDep(String name) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		String sql = "select t.code,t.name from "+databasename+".jc_public_department t where t.name like ?";
		
		List list=new ArrayList();
		list.add("%"+name+"%");
		
		List<Map> pageQueryForList = dao.queryForList(sql, list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList;
		}
		return null;
	}
	
	/**
	 * 综合查询角色下部门查询列表
	 * @param roleCode
	 * @param httpRequest
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> QueryRole() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		String sql = "select t.roleCode,t.rolename from "+databasename+".zhcx_role t";
		
		List<Map> pageQueryForList = dao.queryForList(sql, null);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList;
		}
		return null;
	}

	public String SaveUser(String userList) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		String[] split = userList.split(",");
		
		String userId = split[0].toUpperCase();
		String userName = split[1];
		String departmentCode = split[2];
		String roleCode = split[3];
		
		String sqlquery = "select t.* from "+databasename+".jc_public_people t where t.user_id = ? and t.flag = '1' ";
		List listquery=new ArrayList();
		listquery.add(userId);
		List<Map> queryForList = dao.queryForList(sqlquery,listquery);
		if(queryForList.size()>0){
			return "0";
		}
		
		
		try {
			String sql = "insert into "+databasename+".jc_user_role t select sys_guid(),?,?,'ZHCX','4',sysdate from dual";
			
			List list=new ArrayList();
			list.add(userId);
			list.add(roleCode);
			dao.execute(sql, list);
			
			String sql1 = "insert into "+databasename+".jc_public_people t select ?,?,?,'1',sysdate from dual ";
			List list1=new ArrayList();
			list1.add(userId);
			list1.add(userName);
			list1.add(departmentCode);
			dao.execute(sql1, list1);
		} catch (Exception e) {
			e.printStackTrace();
			return "2";
			// TODO Auto-generated catch block
		}
		
		return "1";
		/*for(int i=0;i<split.length;i++){
			userId = split[i];
			List list=new ArrayList(); 
			list.add(userId);
			list.add(roleCode);
			dao.execute(sql, list);
		}*/
		
	}

	public String deleteRole(String roleCode) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		List list1=new ArrayList();
		list1.add(roleCode);
		
		try {
			String updateZHCXROLE = "update "+databasename+".ZHCX_ROLE set flag = '0' where rolecode = ?";
			dao.execute(updateZHCXROLE, list1);
			
			String deleteJcUserRole = "delete from "+databasename+".jc_user_role t where t.role_code = ? and app_code = 'ZHCX'";
			dao.execute(deleteJcUserRole, list1);
			
			String deleteSMfunction = ""
					+ " from "+db_query+".sm_role_func where role_code = ?";
			dao.execute(deleteJcUserRole, list1);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		return "1";
	}

	public List<Map> queryRoleInfo(String roleCode) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		String sql = "select rolename,rolecode,roledesc from "+databasename+".ZHCX_ROLE where rolecode = ? ";
		
		List list=new ArrayList();
		list.add(roleCode);
		
		List<Map> queryForList = dao.queryForList(sql, list);
		if(queryForList !=null && queryForList.size()>0){
			return queryForList;
		}
		return null;
	}


	public String EditRole(String oldRoleCode, String roleName,
			String roleCode, String roleDesc) {
		IPersistenceDAO dao = getPersistenceDAO();
		
		String updateRoleFunc = "update "+db_query+".sm_role_func set role_code = ? where role_code = ? ";
		String updateUserRole = "update "+databasename+".jc_user_role set role_code = ? where role_code = ? and app_code = 'ZHCX' ";
		List list=new ArrayList();
		list.add(roleCode);
		list.add(oldRoleCode);
		
		String updateRole = "update "+databasename+".ZHCX_ROLE set rolecode = ?,rolename = ?,roledesc = ?,timestamp = sysdate where rolecode = ? ";
		List list1=new ArrayList();
		list1.add(roleCode);
		list1.add(roleName);
		list1.add(roleDesc);
		list1.add(oldRoleCode);
		
		try {
		    dao.execute(updateRoleFunc, list);
		    dao.execute(updateUserRole, list);
		    dao.execute(updateRole, list1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "1";
	}

	public List<Map> PowerList(String functionCode, Map<String, String> map) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		StringBuffer sql = new StringBuffer("select t.* from "+db_query+".sm_function t where 1=1 ");
		
		List list=new ArrayList();
		
		if (map!=null) {
			Object object = map.get("functionCode");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.function_code like ? ");
					list.add("%"+object.toString().trim().toUpperCase()+"%");
				}
			}
			
			Object object2 = map.get("functionName");
			if (object2!=null) {
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.function_name like ? ");
					list.add("%"+object2.toString().trim().toUpperCase()+"%");
				}
			}
		}
		
		List<Map> queryForList = dao.queryForList(sql.toString(), list);
		if(queryForList !=null && queryForList.size()>0){
			return queryForList;
		}
		return null;
	}

	public String banPower(String functionCode, String flag) {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		StringBuffer sql = new StringBuffer("update "+db_query+".sm_function t set t.effective_marker = ? where function_code = ? ");
		
		List list=new ArrayList();
		list.add(flag);
		list.add(functionCode);
		
		try {
		    dao.execute(sql.toString(), list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "1";
	}

	public List<Map> getSingleEntInfo(String sql, String name,
			HttpServletRequest request) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		
		List list=new ArrayList();
		list.add(name);
		list.add(name);
		list.add(name);
		
		LogUtil.insertLog("银行接口_单条查询", sql, list.toString(), request, dao);
		
		return dao.queryForList(sql, list);
	}

	public List<Map> getListEntInfo(String sql, HttpServletRequest request) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		
		LogUtil.insertLog("银行接口_多条查询", sql, null, request, dao);
		
		return dao.queryForList(sql, null);
	}

	public List<Map> getNbStatisticsInfo(String code,String time) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		
		StringBuffer stringBuffer = new StringBuffer();
		
		if(code == null&&isEmpty(time)){
			stringBuffer.append("select * from DC_NB_TJ_VIEW t where t.TYPE='5' and  t.TJ_DATE = TO_CHAR(sysdate,'yyyymmdd')");
			return dao.queryForList(stringBuffer.toString(), null);
			
		}else if(code == null&&!isEmpty(time)){
			System.out.println(" === > " +time);
			//根据时间节点进行主页查询
			List listparam = new ArrayList();
			listparam.add(time);
			logger.info("查询的时间参数是： " +  listparam);
			stringBuffer.append("select * from DC_NB_TJ_VIEW t where t.TYPE='5' and  t.TJ_DATE = ?");
			System.out.println(stringBuffer.toString());
			return dao.queryForList(stringBuffer.toString(), listparam);
		}else{
			ArrayList arrayList = new ArrayList();
			arrayList.add(time);
			arrayList.add(code);
			System.out.println("===============入选的时间" + time );
			stringBuffer.append("select * from DC_NB_TJ_VIEW t where t.TYPE='6' and  t.TJ_DATE =? and t.pcode =?");
			return dao.queryForList(stringBuffer.toString(), arrayList);
		}
	}
	
	
	
	
	
	
	

	public List<Map> getZhfxInfo(String code) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		
		StringBuffer stringBuffer = new StringBuffer();
		
		if(code == null){
			stringBuffer.append("SELECT A.CODE,A.CODENAME, A.SORT,A.TYPE, A.PARENTCODE,A.COM_CNT, A.EXISTENCE_CNT,A.LOGOFF_CNT,A.REVOKE_CNT, A.REPORT_CNT,A.NO_REPORT_CNT,A.IN_CASE_CNT, A.IN_PROTECT_CNT, A.FOOD_ENTERPRISE_CNT, A.CARD_FOOD_ENTERPRISE_CNT,B.CODENAME AS PARENTCODENAME ")
					.append(" FROM (SELECT A.CODE, A.CODENAME,A.SORT,A.TYPE,A.PARENTCODE,SUM(B.COM_CNT) AS COM_CNT,SUM(B.EXISTENCE_CNT) AS EXISTENCE_CNT,SUM(B.LOGOFF_CNT) AS LOGOFF_CNT,SUM(B.REVOKE_CNT) AS REVOKE_CNT,SUM(B.REPORT_CNT) AS REPORT_CNT,SUM(B.NO_REPORT_CNT) AS NO_REPORT_CNT, SUM(B.IN_CASE_CNT) AS IN_CASE_CNT,SUM(B.IN_PROTECT_CNT) AS IN_PROTECT_CNT,SUM(B.FOOD_ENTERPRISE_CNT) AS FOOD_ENTERPRISE_CNT, SUM(B.CARD_FOOD_ENTERPRISE_CNT) AS CARD_FOOD_ENTERPRISE_CNT ")
					.append(" FROM DC_BM_GXQY A left join DC_COM_SYN_ANALYSIS B on A.CODE = (case when B.REGORG is null then '9999' else B.REGORG end)   WHERE A.TYPE = '5' AND B.OPTDATE = (SELECT MAX(OPTDATE) FROM DC_COM_SYN_ANALYSIS) GROUP BY A.CODE, A.CODENAME, A.SORT, A.TYPE,A.PARENTCODE UNION ALL SELECT A.CODE,A.CODENAME,A.SORT,A.TYPE, A.PARENTCODE,SUM(B.COM_CNT) AS COM_CNT, SUM(B.EXISTENCE_CNT) AS EXISTENCE_CNT, ")
					.append(" SUM(B.LOGOFF_CNT) AS LOGOFF_CNT,SUM(B.REVOKE_CNT) AS REVOKE_CNT,SUM(B.REPORT_CNT) AS REPORT_CNT,SUM(B.NO_REPORT_CNT) AS NO_REPORT_CNT,SUM(B.IN_CASE_CNT) AS IN_CASE_CNT,SUM(B.IN_PROTECT_CNT) AS IN_PROTECT_CNT,SUM(B.FOOD_ENTERPRISE_CNT) AS FOOD_ENTERPRISE_CNT,SUM(B.CARD_FOOD_ENTERPRISE_CNT) AS CARD_FOOD_ENTERPRISE_CNT FROM DC_BM_GXQY A, DC_COM_SYN_ANALYSIS B WHERE A.CODE = B.ADMINBRANCODE AND A.TYPE = '6'AND B.OPTDATE = (SELECT MAX(OPTDATE) FROM DC_COM_SYN_ANALYSIS) ")
					.append(" GROUP BY A.CODE, A.CODENAME, A.SORT, A.TYPE,A.PARENTCODE) A left join DC_BM_GXQY B on A.PARENTCODE = B.CODE where A.type ='5' ORDER BY SORT ");
			
			return dao.queryForList(stringBuffer.toString(), null);
			
		}else{
			
			ArrayList arrayList = new ArrayList();
			arrayList.add(code);
			arrayList.add(code);
			
			stringBuffer.append("SELECT A.CODE,A.CODENAME, A.SORT,A.TYPE, A.PARENTCODE, SUM(B.COM_CNT) AS COM_CNT,SUM(B.EXISTENCE_CNT) AS EXISTENCE_CNT, SUM(B.LOGOFF_CNT) AS LOGOFF_CNT,")
			.append(" SUM(B.REVOKE_CNT) AS REVOKE_CNT,SUM(B.REPORT_CNT) AS REPORT_CNT,SUM(B.NO_REPORT_CNT) AS NO_REPORT_CNT, SUM(B.IN_CASE_CNT) AS IN_CASE_CNT,SUM(B.IN_PROTECT_CNT) AS IN_PROTECT_CNT, ")
			.append(" SUM(B.FOOD_ENTERPRISE_CNT) AS FOOD_ENTERPRISE_CNT,SUM(B.CARD_FOOD_ENTERPRISE_CNT) AS CARD_FOOD_ENTERPRISE_CNT FROM DC_BM_GXQY A, DC_COM_SYN_ANALYSIS B ")
			.append(" WHERE A.TYPE = '6'AND A.CODE = B.ADMINBRANCODE AND B.OPTDATE = (SELECT MAX(OPTDATE) FROM DC_COM_SYN_ANALYSIS) AND A.PARENTCODE = ? ")
			.append(" AND B.REGORG = ? GROUP BY A.CODE, A.CODENAME, A.SORT, A.TYPE, A.PARENTCODE ");

			return dao.queryForList(stringBuffer.toString(), arrayList);
		}
	}
	
	
	
	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
}
