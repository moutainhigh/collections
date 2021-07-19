package com.gwssi.comselect.service;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;
import com.gwssi.trs.controller.CaseController;
import com.gwssi.util.QueryCodeSql;

@Service(value = "nbSelectService")
public class NBSelectService extends BaseService{
	private static  Logger log=Logger.getLogger(NBSelectService.class);
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
		}
		List list = dao_dc.queryForList(sql.toString(),null);
		return list;
	}
	/**
	 * 查询年报前100条信息 
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getNBList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(testsql.toString(),list);
		LogUtil.insertLog("年报查询", testsql, list.toString(), httpServletRequest, dao);
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
			Object objectNbmark = map.get("nbmark");//年报标识，1是已年报，0是未年报
			Object objectEntmark = map.get("entmark");//商事主体标识,1是企业，0是个体
			Object objectYeWork = map.get("yeWork");//异常名录标识,1是，0否
			String  sObjectNbmark = (String) objectNbmark;
			String  sObjectEntmark = (String) objectEntmark;
			String  sobjectYeWork = (String) objectYeWork;
			//下面是分四中情况进行判断查询：1.企业已年报 ； 2.企业未年报 ；  3.个体已年报 ； 4.个体未年报
			if ("1".equals(sObjectNbmark)&&"1".equals(sObjectEntmark)) {//企业已年报
				sql.append("select p.ancheyear,p.anchedate,p.ancheid, p.pripid,t.regno,");
				sql.append("t.pripid as id, t.entname, t.enttype as enttype,t.estdate,t.reccap,t.regorg,t.adminbrancode,t.dom,t.enttype_cn,t.gslly_mobtel,gslly_name ");
				sql.append("from dc_ra_mer_base_query t , dc_NB_QY_JBXX p where t.entid  = p.pripid and p.ifpub >1 and 1=1 ");
				if ("1".equals(sobjectYeWork)) {//在异常名录表里面
					sql.append(" and t.entid  in(select entid from dc_ms_abnormal_diretory where type='2') ");//在
				}else{
					sql.append(" and t.entid  not in(select entid from dc_ms_abnormal_diretory where type='2') ");//不在
				}
				if (map.get("entname")!=null) {
					Object object2 = map.get("entname");
					if (object2.toString().trim().length()>0) {
						if (isNumeric(object2.toString().trim()) || isTest(object2.toString().trim()) ) {
							sql.append(" and (t.regno = ?)");
							list.add(object2.toString().trim());
							//list.add(object2.toString().trim());
						}else{
							sql.append(" and t.entname = ?");
							list.add(object2.toString().trim());
						}	
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
				if (map.get("ancheyear")!=null) {
					Object object2 = map.get("ancheyear");
					if (object2.toString().trim().length()>0) {
						sql.append(" and p.ancheyear = ?");
						list.add(object2.toString().trim());
					}
				}
			/*	if (map.get("anchedate_begin")!=null) {
					Object object2 = map.get("anchedate_begin");
					if (object2.toString().trim().length()>0) {
						sql.append(" and p.anchedate >= to_date(?,'yyyy-mm-dd')");
						list.add(object2.toString().trim());
					}
				}
				
				if (map.get("anchedate_end")!=null) {
					Object object2 = map.get("anchedate_end");
					if (object2.toString().trim().length()>0) {
						sql.append(" and p.anchedate <= to_date(?,'yyyy-mm-dd')");
						list.add(object2.toString().trim());
					}
				}*/
			}
			
			if ("0".equals(sObjectNbmark)&&"1".equals(sObjectEntmark)) {//企业未年报
				sql.append("select b.pripid,a.regno,");
				sql.append("a.pripid as id, a.entname, a.enttype as enttype,a.estdate,a.reccap,a.regorg,a.adminbrancode,a.dom,a.enttype_cn,a.gslly_mobtel,gslly_name ");
				sql.append("from dc_ra_mer_base_query a,(select pripid as pripid,ancheid from dc_NB_QY_JBXX where ifpub >1 and 1=1 ");
				
				if (map.get("ancheyear")!=null) {
					Object object2 = map.get("ancheyear");
					if (object2.toString().trim().length()>0) {
						sql.append(" and ancheyear = ? ");
						list.add(object2.toString().trim());
					}
				}
			/*	if (map.get("anchedate_begin")!=null) {
					Object object2 = map.get("anchedate_begin");
					if (object2.toString().trim().length()>0) {
						sql.append(" and anchedate >= to_date(?,'yyyy-mm-dd')");
						list.add(object2.toString().trim());
					}
				}
				
				if (map.get("anchedate_end")!=null) {
					Object object2 = map.get("anchedate_end");
					if (object2.toString().trim().length()>0) {
						sql.append(" and anchedate <= to_date(?,'yyyy-mm-dd')");
						list.add(object2.toString().trim());
					}
				}*/
				sql.append(" ) b where a.entid=b.pripid(+) and b.pripid is null  ");
				
				if ("1".equals(sobjectYeWork)) {//在异常名录表里面
					sql.append(" and a.entid  in(select entid from dc_ms_abnormal_diretory where type='2') ");//在
				}else{
					sql.append(" and a.entid  not in(select entid from dc_ms_abnormal_diretory where type='2') ");//不在
				}
				if (map.get("ancheyear")!=null) {
					Object object2 = map.get("ancheyear");
					if (object2.toString().trim().length()>0) {
						sql.append(" and a.estdate <= to_date('"+object2.toString().trim()+"-12-31','yyyy-mm-dd') ");
					//	list.add(object2.toString().trim());
					}
				}
				
				if (map.get("entname")!=null) {
					Object object2 = map.get("entname");
					if (object2.toString().trim().length()>0) {
						if (isNumeric(object2.toString().trim()) || isTest(object2.toString().trim()) ) {
							sql.append(" and (a.regno = ?)");
						//	list.add(object2.toString().trim());
							list.add(object2.toString().trim());
						}else{
							sql.append(" and a.entname = ?");
							list.add(object2.toString().trim());
						}	
					}	
				}
				sql.append(" and entstatus in ('1','7','9','11') ");
				if (map.get("regorg")!=null) {
					Object object2 = map.get("regorg");
					if (object2.toString().trim().length()>0) {
						sql.append(" and a.regorg = ? ");
						list.add(object2.toString().trim());
					}
				}
				if (map.get("adminbrancode")!=null) {
					Object object2 = map.get("adminbrancode");
					if (object2.toString().trim().length()>0) {
						sql.append(" and a.adminbrancode = ?");
						list.add(object2.toString().trim());
					}
				}
				//sql.append("  and enttype <>'9500' and enttype <>'9510' and enttype <>'9520' and enttype <>'9530' and enttype <>'9540' and enttype <>'9550' ");
				sql.append("  and enttype in(select code from dc_bm_nbtj where flag='1' or flag='2') ");
			}
			
			if ("1".equals(sObjectNbmark) && "0".equals(sObjectEntmark)) {//个体已年报
				sql.append("select p.ancheyear,p.anchedate,p.ancheid, p.pripid,t.regno, ");
				sql.append("t.pripid as id, t.entname, t.enttype as enttype,t.estdate,t.reccap,t.regorg,t.adminbrancode,t.dom,t.enttype_cn,t.gslly_mobtel,gslly_name ");
				sql.append("from dc_ra_mer_base_query t , dc_NB_GT_JBXX p where t.entid  = p.pripid and p.ifpub >1 and 1=1 ");
				
				if ("1".equals(sobjectYeWork)) {//在异常名录表里面
					sql.append(" and t.entid  in(select entid from dc_ms_abnormal_diretory where type='2') ");//在    type:状态（1拟载入，2载入）
				}else{
					sql.append(" and t.entid  not in(select entid from dc_ms_abnormal_diretory where type='2') ");//不在  type:状态（1拟载入，2载入）
				}
				if (map.get("entname")!=null) {
					Object object2 = map.get("entname");
					if (object2.toString().trim().length()>0) {
						if (isNumeric(object2.toString().trim()) || isTest(object2.toString().trim()) ) {
							sql.append(" and (t.regno = ?)");
							list.add(object2.toString().trim());
							//list.add(object2.toString().trim());
						}else{
							sql.append(" and t.entname = ?");
							list.add(object2.toString().trim());
						}	
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
				if (map.get("ancheyear")!=null) {
					Object object2 = map.get("ancheyear");
					if (object2.toString().trim().length()>0) {
						sql.append(" and p.ancheyear = ?");
						list.add(object2.toString().trim());
					}
				}
				/*if (map.get("anchedate_begin")!=null) {
					Object object2 = map.get("anchedate_begin");
					if (object2.toString().trim().length()>0) {
						sql.append(" and p.anchedate >= to_date(?,'yyyy-mm-dd')");
						list.add(object2.toString().trim());
					}
				}
				
				if (map.get("anchedate_end")!=null) {
					Object object2 = map.get("anchedate_end");
					if (object2.toString().trim().length()>0) {
						sql.append(" and p.anchedate <= to_date(?,'yyyy-mm-dd')");
						list.add(object2.toString().trim());
					}
				}*/
			}
			
			if ("0".equals(sObjectNbmark) && "0".equals(sObjectEntmark)) {//个体未年报
				sql.append("select b.pripid,a.regno,");
				sql.append("a.pripid as id, a.entname, a.enttype as enttype,a.estdate,a.reccap,a.regorg,a.adminbrancode,a.dom,a.enttype_cn,a.gslly_mobtel,gslly_name ");
				sql.append("from dc_ra_mer_base_query a,(select pripid as pripid,ancheid from dc_NB_GT_JBXX where ifpub >1 and 1=1 ");
				
				if (map.get("ancheyear")!=null) {
					Object object2 = map.get("ancheyear");
					if (object2.toString().trim().length()>0) {
						sql.append(" and ancheyear = ? ");
						list.add(object2.toString().trim());
					}
				}
				/*if (map.get("anchedate_begin")!=null) {
					Object object2 = map.get("anchedate_begin");
					if (object2.toString().trim().length()>0) {
						sql.append(" and anchedate >= to_date(?,'yyyy-mm-dd')");
						list.add(object2.toString().trim());
					}
				}
				
				if (map.get("anchedate_end")!=null) {
					Object object2 = map.get("anchedate_end");
					if (object2.toString().trim().length()>0) {
						sql.append(" and anchedate <= to_date(?,'yyyy-mm-dd')");
						list.add(object2.toString().trim());
					}
				}*/
				sql.append(" ) b where a.entid=b.pripid(+) and b.pripid is null  ");
				
				
				if ("1".equals(sobjectYeWork)) {//在异常名录表里面
					sql.append(" and a.entid  in(select entid from dc_ms_abnormal_diretory where type='2') ");//在
				}else{
					sql.append(" and a.entid  not in(select entid from dc_ms_abnormal_diretory where type='2') ");//不在
				}
				if (map.get("ancheyear")!=null) {
					Object object2 = map.get("ancheyear");
					if (object2.toString().trim().length()>0) {
						sql.append("  and a.estdate <= to_date('"+object2.toString().trim()+"-12-31','yyyy-mm-dd') ");
					//	list.add(object2.toString().trim());
					}
				}
				if (map.get("entname")!=null) {
					Object object2 = map.get("entname");
					if (object2.toString().trim().length()>0) {
						if (isNumeric(object2.toString().trim()) || isTest(object2.toString().trim()) ) {
							sql.append(" and (a.regno = ?)");
							list.add(object2.toString().trim());
						//	list.add(object2.toString().trim());
						}else{
							sql.append(" and a.entname = ?");
							list.add(object2.toString().trim());
						}	
					}	
				}
				if (map.get("regorg")!=null) {
					Object object2 = map.get("regorg");
					if (object2.toString().trim().length()>0) {
						sql.append(" and a.regorg = ? ");
						list.add(object2.toString().trim());
					}
				}
				if (map.get("adminbrancode")!=null) {
					Object object2 = map.get("adminbrancode");
					if (object2.toString().trim().length()>0) {
						sql.append(" and a.adminbrancode = ?");
						list.add(object2.toString().trim());
					}
				}
				sql.append(" and entstatus in ('1','7','9','11') and enttype in('9500','9510','9520','9530','9540','9550')");
			}
			String area = (String)map.get("area");
			if (area!=null) {
				if ("-1".equals(area)) { //全部
					sql.append(" and t.pripid in (select b.id from dc_ns_enterprise_list b)");
				}else if ("1".equals(area)) { //前海
					sql.append(" and t.pripid in (select b.id from dc_ns_enterprise_list b where b.addr_flag = ?)");
					list.add(area);
				}else if ("0".equals(area)) { //蛇口
					sql.append(" and  t.pripid in (select b.id from dc_ns_enterprise_list b where b.addr_flag = ?)");
					list.add(area);
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
	public String getNBListCount(Map<String, String> map) throws OptimusException{
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
		sql.append("select ancheyear, to_char(nvl(firstpubtime,updatetime), 'yyyy/MM/dd') as anchedate,pripid,ancheid from DC_NB_QY_JBXX t where t.pripid = ? and ifpub >1 order by ancheyear desc ");
		list.add(pripid);
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
 		LogUtil.insertLog("企业年报查询", sql.toString(), list.toString(), httpServletRequest, dao);
 		if (pageQueryForList !=null && pageQueryForList.size()<=0 ) {
			String  sql1 = "select ancheyear,  to_char(nvl(firstpubtime,updatetime), 'yyyy/MM/dd') as anchedate,pripid,ancheid from DC_NB_QY_JBXX t where trim(t.pripid) = ? and ifpub >1 order by ancheyear desc";
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
		sql.append("select ancheyear,  to_char(nvl(firstpubtime,updatetime), 'yyyy/MM/dd') as anchedate,pripid,ancheid from DC_NB_GT_JBXX t where t.pripid = ? and ifpub >1 order by ancheyear desc ");
		list.add(pripid);
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("个体年报查询", sql.toString(), list.toString(), httpRequest, dao);
		if (pageQueryForList ==null && pageQueryForList.size()<=0 ) {
			String  sql1 = "select ancheyear,  to_char(nvl(firstpubtime,updatetime), 'yyyy/MM/dd') as anchedate,pripid,ancheid from DC_NB_GT_JBXX t where trim(t.pripid) = ? and ifpub >1 order by ancheyear desc";
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
		 
		/*String sqlRolQuerySql = "select count(1) as count from db_yyjc.jc_user_role  t where t.role_code='ZHCX_NB_IFPUB' and  t.user_id = ? ";
		List<String> sqlRoleParams = new ArrayList<String>();
		
		
		HttpSession session = WebContext.getHttpSession();
	    User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String userid  = "";
	    if(user!=null) {
	    	userid  = user.getUserId();
	    	
	    }
	    sqlRoleParams.add(userid);
		 
		//String testsql =  "select count(1) count from ("+sql+")";
		//return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
		
		Map permmitMap = dao.queryForList(sqlRolQuerySql, sqlRoleParams).get(0);
		
		System.out.println(permmitMap);
		String permitFlagStr = permmitMap.get("count").toString() ;
		
		Integer permitFlag = Integer.valueOf(permitFlagStr);*/
		 
	 	HttpSession session = WebContext.getHttpSession();
	    User user = (User) session.getAttribute(OptimusAuthManager.USER);
	    String userid  = "";
	    if(user!=null) {
	    	userid  = user.getUserId();
	    	
	    }
	 	List<Map> roleIdListByLoginName = new AuthService().getRoleIdByUsersRole(userid);
		int permitFlag = 0;
		for(Map roleMap : roleIdListByLoginName){
			String role_code = (String)roleMap.get("roleCode");
			if(role_code.equals("ZHCX_NB_IFPUB")) {
				permitFlag = 1;
			}
		}
		 
		if(permitFlag>0) {
			if ("1".equals(entityNo)) {// 基本信息
				log.info("企业年报【基本信息查询】查询的年报序号ancheid:"+(String)map.get("id"));
				sql.append("select * from v_dc_nb_qy_jbxx_JS t where t.ancheid = ? ");
				params.add(map.get("id").toString());
			}else if ("2".equals(entityNo)) {// 资产状况信息
				log.info("企业年报【资产状况信息】查询的年报序号ancheid:"+(String)map.get("id"));
				sql.append("select * from v_dc_nb_qy_zczk_JS t where t.ancheid = ? ");
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
			} else if ("10".equals(entityNo)) {// 社会保险信息
				log.info("企业年报【社会保险信息】查询的年报序号ancheid:"+(String)map.get("id"));
				sql.append("select * from V_DC_NB_QY_SBXX t where t.ancheid = ? ");
				params.add(map.get("id").toString());
			}
		}else {
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
			} else if ("10".equals(entityNo)) {// 社会保险信息
				log.info("企业年报【社会保险信息】查询的年报序号ancheid:"+(String)map.get("id"));
				sql.append("select * from V_DC_NB_QY_SBXX t where t.ancheid = ? ");
				params.add(map.get("id").toString());
			}
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
	 * queryListUserRolesByUserId
	 * @param type
	 * @param parm
	 * @return
	 * @throws OptimusException 
	 *//*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> queryUserRolesWNB() throws OptimusException{
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
		
	}*/
	
	/**
	 * 获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  是否公示？未年报导出juese
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
	
	
	//华宇公司
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map queryListUserRolesByUserId(String name) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
			String userId = name;	
			String sql  = "select * from getHuaYuLTD";
			List list=new ArrayList();
			list.add(name);
			List<Map> lists = dao.queryForList(sql,null);
			if(lists!=null&&list.size()>0){
				return lists.get(0);
			}else{
				return null;
			}
	}

	
	
	

	public List<Map> exportExcel(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		//String testsql =  "select * from ("+sql+")  e where rownum <= 30000";
		String testsql =  "select * from ("+sql+")  e where rownum <= 80000"; //修改为8万条数据
		List<Map> pageQueryForList = dao.queryForList(testsql.toString(),list);
		LogUtil.insertLog("未年报导出", testsql.toString(), list.toString(), httpServletRequest, dao);
		return typechage(pageQueryForList);
			
	}
	
	
	
	public String exportExcelCount(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		commonMethod(map, sql, list);
		//String testsql =  "select * from ("+sql+")  e where rownum <= 30000";
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
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
