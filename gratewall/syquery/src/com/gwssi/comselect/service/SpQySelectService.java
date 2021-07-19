package com.gwssi.comselect.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;
import com.gwssi.trs.controller.CaseController;
import com.gwssi.util.QueryCodeSql;

@Service(value = "spQySelectService")
public class SpQySelectService extends BaseService{
	private static  Logger log=Logger.getLogger(SpQySelectService.class);
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
	/**
	 * 获取代码值
	 * @param type
	 * @param parm
	 * @return
	 * @throws OptimusException 
	 */
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
		}else{
			type = params[0];
			param = params[1];
			if("adminbrancode".equals(type)){ //所属工商所
				sql.append("select distinct gssdm as value,gssmc as text from v_jg_ent where fjdm= ? order by gssdm ");
			}
			str.add(param);
			 list = dao_dc.queryForList(sql.toString(),str);
		}
		 
		return list;
	}
	/**
	 * 查询前100条信息 
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getSpQyList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(testsql.toString(),list);
		LogUtil.insertLog("食品企业查询", testsql, list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	// 判断以数字开头的字符串的正则表达式:"[0-9]*"
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str.charAt(0)+"");
		if (!isNum.matches()) {
			return false;
		}
			return true;
	}
	
	// 判断以数字开头的字符串的正则表达式:"[a-zA-Z]*"
	public static boolean isTest(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher isTest = pattern.matcher(str.charAt(0)+"");
		if (!isTest.matches()) {
			return false;
		}
			return true;
	}
		
	private void commonMethod(Map map, StringBuffer sql, List list) {
		if (map!=null) {
			String  SpQymark = (String) map.get("SpQymark");//食品企业许可证标识，0是全部，1是有证,2是无证
			//下面是分三种情况进行判断查询：0是全部，1是有证,2是无证
			if ("0".equals(SpQymark)) {//全部
				sql.append("select * from v_spqy_query t where 1=1");
			}else if("1".equals(SpQymark)){//1是有证
				sql.append("select * from v_spqy_query t where t.entname is not null ");
			}else if("2".equals(SpQymark)){//2是无证
				sql.append("select * from v_spqy_query t where t.entname is null ");
			}
			if (map.get("entname")!=null) {
				Object object2 = map.get("entname");
				if (object2.toString().trim().length()>0) {
						sql.append(" and t.entname = ?");
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
			if (map.get("adminbrancode")!=null) {
				Object object2 = map.get("adminbrancode");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.adminbrancode = ?");
					list.add(object2.toString().trim());
				}
			}
			
		}
	}
	/**
	 * 年报总条数
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getSpQyListCount(Map<String, String> map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}
	/**
	 * 企业年报查询
	 * @param pripid
	 * @param httpRequest
	 * @throws OptimusException 
	 */
	public List<Map>  getQYList(String pripid, HttpServletRequest httpServletRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		sql.append("select ancheyear,to_char(anchedate,'yyyy/MM/dd') as anchedate,pripid,ancheid from DC_NB_QY_JBXX t where t.pripid = ? and ifpub >1 order by ancheyear desc ");
		list.add(pripid);
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
 		LogUtil.insertLog("企业年报查询", sql.toString(), list.toString(), httpServletRequest, dao);
 		if (pageQueryForList !=null && pageQueryForList.size()<=0 ) {
			String  sql1 = "select ancheyear, to_char(anchedate,'yyyy/MM/dd') as anchedate,pripid,ancheid from DC_NB_QY_JBXX t where trim(t.pripid) = ? and ifpub >1 order by ancheyear desc";
		    pageQueryForList = dao.pageQueryForList(sql1.toString(),list);
		}
		return pageQueryForList;
	}
	
	/**
	 * 个体年报查询
	 * @param pripid
	 * @param httpRequest
	 * @throws OptimusException 
	 */

	public List<Map>  getGTList(String pripid, HttpServletRequest httpRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		sql.append("select ancheyear, to_char(anchedate,'yyyy/MM/dd') as anchedate,pripid,ancheid from DC_NB_GT_JBXX t where t.pripid = ? and ifpub >1 order by ancheyear desc ");
		list.add(pripid);
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("个体年报查询", sql.toString(), list.toString(), httpRequest, dao);
		if (pageQueryForList ==null && pageQueryForList.size()<=0 ) {
			String  sql1 = "select ancheyear, to_char(anchedate,'yyyy/MM/dd') as anchedate,pripid,ancheid from DC_NB_GT_JBXX t where trim(t.pripid) = ? and ifpub >1 order by ancheyear desc";
		    pageQueryForList = dao.pageQueryForList(sql1.toString(),list);
		}
		return pageQueryForList;
	}
	
	/**
	 * 企业年报信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryNBQYInfo(Map map,int entityNoInt) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		log.info("regDetail_datasourcekey:"+DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List<String> params = new ArrayList<String>();
		 String entityNo =String.valueOf(entityNoInt);
		if ("1".equals(entityNo)) {// 基本信息
			log.info("企业年报【基本信息查询】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_QY_JBXX t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("2".equals(entityNo)) {// 资产状况信息
			log.info("企业年报【资产状况信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_QY_ZCZK t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("3".equals(entityNo)) {// 股权变更信息
			log.info("企业年报【股东及出资信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_QY_CZXX t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("4".equals(entityNo)) {// 股权变更信息
			log.info("企业年报【股权变更信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_QY_GQBG t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("5".equals(entityNo)) {// 对外投资信息
			log.info("企业年报【对外投资信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_QY_DWTZ t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("6".equals(entityNo)) {// 股权变更信息
			log.info("企业年报【对外提供保证担保】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_QY_DWDB t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("7".equals(entityNo)) {//网站或网店
			log.info("企业年报【网站或网店】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_QY_WDXX t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("8".equals(entityNo)) {// 党建信息
			log.info("企业年报【党建信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_QY_DJXX t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("9".equals(entityNo)) {// 修改信息
			log.info("企业年报【修改信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_ALTER_HIS t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}
		return dao.pageQueryForList(sql.toString(), params);
	}
	
	/**
	 * 个体年报信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryNBGTInfo(Map map,int entityNoInt) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		log.info("regDetail_datasourcekey:"+DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List<String> params = new ArrayList<String>();
		 String entityNo =String.valueOf(entityNoInt);
		if ("1".equals(entityNo)) {// 基本信息
			log.info("个体年报【基本信息查询】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_GT_JBXX t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("2".equals(entityNo)) {// 生产经营情况信息
			log.info("个体年报【生产经营情况信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_GT_ZCZK t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("3".equals(entityNo)) {// 行政许可信息
			log.info("个体年报【行政许可信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_GT_XZXK t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("4".equals(entityNo)) {// 网站或网店信息
			log.info("个体年报【网站或网店信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_GT_WDXX t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("5".equals(entityNo)) {// 党建信息
			log.info("个体年报【党建信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_GT_DJXX t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}else if ("6".equals(entityNo)) {// 修改信息
			log.info("个体年报【修改信息】查询的年报序号ancheid:"+(String)map.get("id"));
			sql.append("select * from V_DC_NB_ALTER_HIS t where t.ancheid = ? ");
			params.add(map.get("id").toString());
		}
		return dao.pageQueryForList(sql.toString(), params);
	}
	
	/**
	 * 对外提供保证担保详情信息
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getDWDBQueryById(String ancheid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql=" select * from V_DC_NB_QY_DWDB t where t.ancheid = ? ";
		List list=new ArrayList();
		list.add(ancheid);
		return dao.pageQueryForList(sql, list).get(0);
	}
	/**
	 * 股东及出资详情信息
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getCZXXQueryById(String ancheid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql=" select * from V_DC_NB_QY_CZXX t where t.ancheid = ? ";
		List list=new ArrayList();
		list.add(ancheid);
		Map map = dao.pageQueryForList(sql, list).get(0);
		BigDecimal lisubconam =  (BigDecimal) map.get("lisubconam");
		map.put("lisubconam", lisubconam.toString()+"万元");
		BigDecimal liacconam =  (BigDecimal) map.get("liacconam");
		map.put("liacconam", liacconam.toString()+"万元");
		return map;
	}
	
	/**
	 * 工商联络员信息
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getContactQueryById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql=" select t.id,t.main_tb_id,t.persname,func_getcode('C00001',t.certype) as certype,t.cerno,t.mobel,t.tel,t.email from dc_ra_mer_persons t where t.main_tb_id = ? ";
		List list=new ArrayList();
		list.add(id);
		/*Map map = dao.pageQueryForList(sql, list).get(0);
		BigDecimal lisubconam =  (BigDecimal) map.get("lisubconam");
		map.put("lisubconam", lisubconam.toString()+"万元");
		BigDecimal liacconam =  (BigDecimal) map.get("liacconam");
		map.put("liacconam", liacconam.toString()+"万元");*/
		List<Map> pageQueryForList = dao.pageQueryForList(sql, list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList.get(0);
		}
		return null;
	}
	/**
	 * 获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  是否公示？
	 * @param type
	 * @param parm
	 * @return
	 * @throws OptimusException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	/**
	 * 类型转换(把GregorianCalendar 转换为String)
	 * @return
	 */
	public List<Map> typechage(List<Map> list){
		List<Map> changtype =new ArrayList<Map>();
		for(Map<String,Object> map1:list){
			
			Map<String,Object> newMap= new HashMap<String,Object>();
			for(String s :map1.keySet()){
				Object obj=map1.get(s);
				
				if (obj!=null&&(obj.getClass()==GregorianCalendar.class)){
					GregorianCalendar gcal =(GregorianCalendar)obj;
					String format = "yyyy-MM-dd";
					SimpleDateFormat formatter = new SimpleDateFormat(format);
					newMap.put(s,  formatter.format(gcal.getTime()).toString());
				}else{
					newMap.put(s, map1.get(s));
				}
			}
			changtype.add(newMap);
		}
		
		return  changtype;
	}


}
