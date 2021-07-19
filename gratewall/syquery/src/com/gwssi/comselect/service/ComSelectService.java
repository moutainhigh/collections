package com.gwssi.comselect.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gwssi.application.common.AppConstants;
import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.comselect.utils.ComSqlUtil;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.util.DictionaryManager;

@Service(value = "comSelectService")
public class ComSelectService extends BaseService{

	@Autowired
	DictionaryManager dictionaryManager;
	
	/**
	 * 全文检索详细 信息库
	 * @return
	 */
	private static String getDetail_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("regDetail.datasourcekey");


		return key;
	}
	/**
	 * 代码集库
	 */
	public static String getDc_code_KEY(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("dcCode.datasourcekey");

		return key;
	}
	
	/**
	 * excel导出查询
	 * @param bo
	 * @param request 
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryExcelEntQuery(EntSelectQueryBo bo, JSONObject res, String id,HttpServletRequest request) throws OptimusException{
		String querytype="excel";
		List<Map> list=(List<Map>)queryEnt(bo,querytype,res,id,request).get("data");
		return list;
	}
	
	
	/**
	 * 非分页查询
	 * @param bo
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryNoPageQuery(EntSelectQueryBo bo, HttpServletRequest request) throws OptimusException{
		String querytype="nopage";
		List<Map> list=(List<Map>)queryEnt(bo, querytype,null,null,request).get("data");
		return list;
	}
	
	/**
	 * 更具企业注册号查询
	 * @param regno
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryEntByRegNo(String regno) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from ENT_SELECT t  where 1=1 ");
		if(StringUtils.isNotEmpty(regno)){
			sql.append(" and t.regno=? ");
			params.add(regno);
		}
		return typechage(dao.queryForList(sql.toString(), params));
	}
	
	/**
	 * 分页查询
	 * @param bo
	 * @param httpServletRequest 
	 * @return
	 * @throws OptimusException
	 */
	public Map<String, Object> queryPageQuery(EntSelectQueryBo bo, HttpServletRequest httpServletRequest) throws OptimusException {
		String querytype="page";
		//String[] columns = {"industryphy","enttype"};
		//String[] dicts = {"C00014","C00021"};
		Map<String,Object> map = queryEnt(bo, querytype, null,null,httpServletRequest);
		return map;

		//return dictionaryManager.transferDict(list, columns, dicts);
	}
	
	

	public Map<String,Object> queryEnt(EntSelectQueryBo bo,String querytype, JSONObject res, String id,HttpServletRequest httpServletRequest) throws OptimusException {
	
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		if(querytype.equals("excel")){
			sql.append("select pripid");
			if(!"".equals(res.get("regno"))){
				sql.append(" ,regno");
			}if(!"".equals(res.get("reccap"))){
				sql.append(" ,reccap");
			}if(!"".equals(res.get("regcapcur"))){
				sql.append(" ,regcapcur_cn as regcapcur");
			}if(!"".equals(res.get("industryphy"))){
				sql.append(" ,industryphy_cn as industryphy");
			}if(!"".equals(res.get("entname"))){
				sql.append(" ,entname");
			}if(!"".equals(res.get("opscope"))){
				sql.append(" ,opscope");
			}if(!"".equals(res.get("dom"))){
				sql.append(" ,dom");
			}if(!"".equals(res.get("regorg"))){
				sql.append(" ,regorg_cn as regorg");
			}//if(!"".equals(res.get("adminbrancode"))){
				//sql.append(" adminbrancode");}
			if(!"".equals(res.get("enttype"))){
				sql.append(" ,enttype_cn as enttype");
			}if(!"".equals(res.get("regstate"))){
				sql.append(" ,regstate_cn as regstate");
			}if(!"".equals(res.get("industryco"))){
				sql.append(" ,industryco_cn as industryco");
			}if(!"".equals(res.get("estdate"))){
				sql.append(" ,estdate");
			}if(!"".equals(res.get("apprdate"))){
				sql.append(" ,apprdate");
			}if(!"".equals(res.get("lerep"))){
				sql.append(" ,lerep");
			}if(!"".equals(res.get("opfyears"))){
				sql.append(" ,opfyears");
			}
			if(bo.getEnttype() != null){
				sql.append(" from DC_RA_MER_BASE_QUERY t, com_enttype_new a where 1=1 ");
			}else{
				sql.append(" from DC_RA_MER_BASE_QUERY t where 1=1 " );
			}
			
		}else if(bo.getEnttype() != null){
			sql.append("select pripid,regno,entname,enttype,type,estdate,REGCAPCUR,RECCAP,REGSTATE,entstatus,opetype,estdate,apprdate,lerep,opfyears,entid from dc_ra_mer_base_query t, com_enttype_new a where 1=1  ");
		}else{
			sql.append("select pripid,regno,entname,enttype,type,estdate,REGCAPCUR,RECCAP,REGSTATE,entstatus,opetype,estdate,apprdate,lerep,opfyears,entid from dc_ra_mer_base_query t where 1=1  ");
		}
		if(StringUtils.isNotBlank(bo.getEstdate_start())){
			sql.append("  and t.estdate >= to_date(").append("?").append(",'YYYY-MM-DD')");
			params.add(bo.getEstdate_start());
		}
		
		if(StringUtils.isNotBlank(bo.getEstdate_end())){
			sql.append("  and t.estdate <= to_date(").append("?").append(",'YYYY-MM-DD')");
			params.add(bo.getEstdate_end());
		}
		
		if(StringUtils.isNotBlank(bo.getApprdate_start())){
			
			sql.append("  and t.apprdate >= to_date(").append("?").append(",'YYYY-MM-DD')");
			params.add(bo.getApprdate_start());
		}

		if(StringUtils.isNotBlank(bo.getApprdate_end())){
			
			sql.append("  and t.apprdate <= to_date(").append("?").append(",'YYYY-MM-DD')");
			params.add(bo.getApprdate_end());
		}		
		
		//reccap_start
		if(StringUtils.isNotBlank(bo.getReccap_start())){
			sql.append("  and t.reccap >= ").append("?").append("  ");
			params.add(bo.getReccap_start());
		}
		
		if(StringUtils.isNotBlank(bo.getReccap_end())){
			sql.append("  and t.reccap <= ").append("?").append("  ");
			params.add(bo.getReccap_end());
		}		
		
		
		if(StringUtils.isNotBlank(bo.getRegcapcur())){
			
			sql.append( " and t.regcapcur=" ).append("?").append("   ");
			params.add(bo.getRegcapcur());
		}
		
		if(StringUtils.isNotBlank(bo.getIndustryphy())){
			
			sql.append( " and (t.industryphy=? )");
			params.add(bo.getIndustryphy());
			//params.add(bo.getIndustryphy()+"#");
		}		
		
		Map map2= ComSqlUtil.getORSql(bo.getEntname_term(),bo.getEntname(),"t.entname");
		if(map2!=null){
			StringBuffer sql2=(StringBuffer) map2.get("sql");
			sql.append(sql2);
			params.addAll((List<String>)map2.get("parms"));
		}
		
		
		Map map1= ComSqlUtil.getORSql(bo.getOpscope_term(),bo.getOpscope(),"t.opscope");
		if(map1!=null){
			StringBuffer sql2=(StringBuffer) map1.get("sql");
			sql.append(sql2);
			params.addAll((List<String>)map1.get("parms"));
		}
		
		Map map3= ComSqlUtil.getORSql(bo.getDom_term(),bo.getDom(),"t.dom");
		if(map3!=null){
			StringBuffer sql2=(StringBuffer) map3.get("sql");
			sql.append(sql2);
			params.addAll((List<String>)map3.get("parms"));
		}		
		
		
		//四个并列条件  
		if(bo.getRegorg()!=null){
			Map map4= ComSqlUtil.getORS4ql(bo.getRegorg(),bo.getAdminbrancode(),bo.getGongzuowangge(),bo.getDanyuanwangge());
			if(map4!=null){
				StringBuffer sql2=(StringBuffer) map4.get("sql");
				sql.append(sql2);
				params.addAll((List<String>)map4.get("parms"));
			}		
		}
		
		
		//注册状态
		String[] regstate=	bo.getRegstate();
		if(regstate!=null && regstate.length>0){
			String sql1="and (1=2 ";
			List<String> str = new ArrayList<String>();
			for(int i=0;i<regstate.length;i++){
				if(StringUtils.isNotEmpty(regstate[i])){
					sql1= sql1+"   or t.regstate=?  ";
					str.add(regstate[i]);
				}
			}
			sql1=sql1+")";
			if(sql1.indexOf("or")>=0){
				sql.append(sql1);
				params.addAll(str);
			}
		}
		
		
		String enttype_radio =bo.getEnttype_radio();
		if(StringUtils.equals(enttype_radio, "00")){//大类
			if(bo.getEnttype()!=null&&bo.getEnttype().length>0){
				List<String> list=	queryEntTypeChild(bo.getEnttype());
					if(list!=null&&list.size()>0){
						sql.append(" and t.newenttype=a.code and  a.pid in (");
						sql.append(prepareSqlIn(list.size()));
						sql.append(")  ");
						params.addAll(list);
					}
				}
//			String[] enttypes = bo.getEnttype();
//			if(enttypes!=null&&enttypes.length>0){
//				sql.append(" and exists ( select n.co from  (select m.code as co from dc_code.com_enttype_new m "
//						+ "where m.is_stand = 'Y' start with m.code = ?");
//				params.add(enttypes[0]);
//				if(enttypes.length>1){
//					for(int i=1;i<enttypes.length;i++){
//						sql.append(" or m.code = ? ");
//						params.add(enttypes[i]);
//					}
//				}
//				sql.append(" connect by m.pid = prior m.code) n where t.newenttype = n.co) ");
//			}	
			//List<String> list=	queryEntTypeChild(bo.getEnttype());//entype A
			//select pripid,regno,entname,enttype,type,estdate,REGCAPCUR,RECCAP,REGSTATE,entstatus,opetype from V_ENT_SELECT t where 1=1
				
				/*if(list!=null&&list.size()>0){
					sql.append(" and t.newenttype in (");
					sql.append(prepareSqlIn(list.size()));
					sql.append(")  ");
					params.addAll(list);
				}*/
			
		}else{//小类
			String[] enttypes = bo.getEnttype();
//			if(enttypes!=null&&enttypes.length>0){
//				sql.append(" and exists ( select n.co from  (select m.code as co from dc_code.com_enttype_new m "
//						+ "where m.is_stand = 'Y' start with m.code = ?");
//				params.add(enttypes[0]);
//				if(enttypes.length>1){
//					for(int i=1;i<enttypes.length;i++){
//						sql.append(" or m.code = ? ");
//						params.add(enttypes[i]);
//					}
//				}
//				sql.append(" connect by m.pid = prior m.code) n where t.newenttype = n.co) ");
//			}
			if(bo.getEnttype()!=null&&bo.getEnttype().length>0){
				sql.append(" and t.newenttype in (");
				sql.append(prepareSqlIn(bo.getEnttype().length));
				sql.append(")  ");
				List<String> list = Arrays.asList(bo.getEnttype()); 
				params.addAll(list);
			}
		}
		
		//String querySql = "select count(1) from (  "+sql.toString()+"  )";
		HashMap<String, Object> hashMap  = new HashMap<String,Object>();
		if("page".equals(querytype) && httpServletRequest == null){
			String querySql = sql.toString().replace("pripid,regno,entname,enttype,type,estdate,REGCAPCUR,RECCAP,REGSTATE,entstatus,opetype,estdate,apprdate,lerep,opfyears,entid", " count(1) as cnt ");
			List<Map> countList = dao.queryForList(querySql, params);
			String count = countList.get(0).get("cnt").toString();
			hashMap.put("count", count);
			return hashMap;
	    }
		
		System.out.println("sql______________________" + sql);
		System.out.println("params____________________" + params + " ");
		if("page".equals(querytype)){
			if(params.size() == 0){
				sql.append(" and 1=2 ");
				hashMap.put("count", "0");
				hashMap.put("data", dao.queryForList(sql.toString(), params));
			}
			else{
				sql.append("  and rownum<=100 ");
				hashMap.put("data", dao.queryForList(sql.toString(), params));
				LogUtil.insertLog("商事主体查询", sql.toString(), params.toString(), httpServletRequest, dao);
			}
			return hashMap;
		}else if("excel".equals(querytype)){//这里需要修改  改为视图
			/*if("GONGYS@SZAIC".equals(id) || "LINQY1@SZAIC".equals(id) || "LIHAO@SZAIC".equals(id) || "CHENXH@SZAIC".equals(id) || "HUANGXD2@SZAIC".equals(id) || "CHAIML@SZAIC".equals(id) || "CHANGRUAN@SZAIC".equals(id)){
				//sql.append("and rownum<30001");
			}else{
				sql.append(" and rownum<5001");
			}*/
			
			HttpSession session = WebContext.getHttpSession();
			//User user = (User) session.getAttribute(OptimusAuthManager.USER);
		/*	if(user != null){
				String userId = user.getUserId();	
				List<Map> roleIdListByLoginName = new AuthService().getRoleIdByUsersRole(userId);*/
				
			List<String> roleCodes = (List<String>)session.getAttribute("roleIdList");
			if(roleCodes.contains("EXCELROLE")){
				//sql.append(" and rownum<200001");
				hashMap.put("data", typechage(dao.queryForList(sql.toString(), params)));
				LogUtil.insertLog("excel导出_商事主体", sql.toString(), params.toString(), httpServletRequest, dao);
				return hashMap;
			}
			
			sql.append(" and rownum<5001");
			//这处需要优化
			hashMap.put("data", typechage(dao.queryForList(sql.toString(), params)));
			LogUtil.insertLog("excel导出_商事主体", sql.toString(), params.toString(), httpServletRequest, dao);
			return hashMap;
		}else{
			String sql33="select * from (  select t_.*, rownum as rownum_ from (";
			sql33=sql33+sql.toString()+") t_ where rownum <= 100 ) where rownum_ > 0";
			//这处需要优化
			hashMap.put("data", typechage(dao.queryForList(sql33.toString(), params)));
			LogUtil.insertLog("商事主体查询", sql33.toString(), params.toString(), httpServletRequest, dao);
			return hashMap;
		}
		//return dao.queryForList(sql.toString(), params);
	}
	/**
	 * 
	 * 类型转换(把GregorianCalendar 转换为String)
	 * @return
	 */
	public static List<Map> typechage(List<Map> list){
		List<Map> changtype =new ArrayList<Map>();
		for(Map<String,Object> map1:list){
			
			Map<String,Object> newMap= new HashMap<String,Object>();
			for(String s :map1.keySet()){
				Object obj=map1.get(s);
				
				if (obj!=null&&(obj.getClass()==GregorianCalendar.class)){
					GregorianCalendar gcal =(GregorianCalendar)obj;
					//String format = "yyyy-MM-dd HH:mm:ss";
					String format = "yyyy-MM-dd";
					SimpleDateFormat formatter = new SimpleDateFormat(format);
					newMap.put(s,  formatter.format(gcal.getTime()).toString());
				}else{
					newMap.put(s, map1.get(s));
				}
			}
			changtype.add(newMap);
		}
		list=null;
		return  changtype;
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
	 * 获取代码 值
	 * @param string
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> queryCode_value(String string) throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		 List list=null;
		if("regcapcur".equals(string)){//币种
			 sql.append("select * from (select t.codeindex_code as value, t.codeindex_value as text from DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00013' order by t.illustrate) where rownum<=17 ");
			 list=dao.queryForList(sql.toString(),null);
		}else if("industryphy".equals(string)){ //行业
			 sql.append("select t.codeindex_code as value, t.codeindex_value as text from DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00014' ");
			 list=dao.queryForList(sql.toString(),null);

		}else if("regorg".equals(string)){//管辖区域
			 sql.append("select distinct fjdm as value,fjmc as text from v_jg_ent order by fjdm");
			 list=dao.queryForList(sql.toString(),null);	
		}
		else if("enttype".equals(string)){//企业类型
			 sql.append("select t.codeindex_code as value, t.codeindex_value as text from DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00021' ");
			 list=dao.queryForList(sql.toString(),null);	
		}
		
		if(list!=null){
	/*		 Map<String,String> map1 =new HashMap<String,String>(); 
			 map1.put("value", "");
			 map1.put("text", "全部");
			 list.add(map1);*/
		}
		
		
		
		return list;
		
		
	
	}
	/**
	 * 获取代码值
	 * @param type
	 * @param parm
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> queryCode_value(String type, String parm) throws OptimusException {
		if( parm == null){
			return null;
		}
		IPersistenceDAO dao=getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		 List list=null;
		if("regcapcur".equals(type)){//币种
			 sql.append("select t.codeindex_code as value, t.codeindex_value as text from DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00013' and t.codeindex_code = ? ");
			 str.add(parm);
			 list=dao.queryForList(sql.toString(),str);
		}else if("industryphy".equals(type)){ //行业
			 sql.append("select t.codeindex_code as value, t.codeindex_value as text from DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00014' and t.codeindex_code = ? ");
			 str.add(parm);
			 list=dao.queryForList(sql.toString(),str);

		}else if("regorg".equals(type)){//管辖区域
			 sql.append("select t.codeindex_code as value, t.codeindex_value as text from DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00017' and t.codeindex_code = ? ");
			 str.add(parm);
			 list=dao.queryForList(sql.toString(),str);	
		}
		else if("enttype".equals(type)){//企业类型
			 sql.append("select t.codeindex_code as value, t.codeindex_value as text from DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00021' and t.codeindex_code = ? ");
			 str.add(parm);
			 list=dao.queryForList(sql.toString(),str);	
		}
		else if("enttype_code".equals(type)){//企业类型转码
			 sql.append("select t.code as value, t.type_name as text from COM_ENTTYPE_NEW t where t.code = ? ");
			 str.add(parm);
			 list=dao.queryForList(sql.toString(),str);	
		}
		else if("regstate".equals(type)){//登记状态转码
			 sql.append("select t.codeindex_code as value, t.codeindex_value as text from DC_STANDARD_CODEDATA t where t.standard_codeindex = 'C00231' and t.codeindex_code = ? ");
			 str.add(parm);
			 list=dao.queryForList(sql.toString(),str);	
		}
		else if("adminbrancode".equals(type)){//所属监管所
			 sql.append("select distinct gssdm as value,gssmc as text from v_jg_ent where fjdm= ? order by gssdm ");
			 str.add(parm);
			 list=dao.queryForList(sql.toString(),str);	
		}
		else if("gongzuowangge".equals(type)){//工作网格
			 sql.append("select distinct gzwgdm as value,gzwgmc as text from v_jg_ent where gssdm= ? order by gzwgdm ");
			 str.add(parm);
			 list=dao.queryForList(sql.toString(),str);	
		}
		else if("danyuanwangge".equals(type)){//单元网格
			 sql.append("select distinct dywgdm as value,dywgmc as text from v_jg_ent where gzwgdm= ? order by dywgdm");
			 str.add(parm);
			 list=dao.queryForList(sql.toString(),str);	
		}
		
		
		
		return list;
	}
	
	/**
	 * 查询企业类型
	 * @param radiovalue 
	 * @return
	 * @throws OptimusException
	 */
	public List<Map<String, Object>> queryEntTypeTree(String radiovalue) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(this.getDc_code_KEY());
		StringBuffer sql = new StringBuffer();
		List<String> str = null; 
		sql.append(
				"select code as ID, type_name AS NAME , nvl(pid,'all') as pid, is_stand AS STANDE from com_enttype_new  ");
		if(StringUtils.equals(radiovalue, "01")){

		}else{
			str = new ArrayList<String>();
			sql.append("where is_stand =? ");
			str.add(AppConstants.EFFECTIVE_N);
		}
		sql.append(" order  by  id ");
	
		List list1 = dao.queryForList(sql.toString(), str);
		return list1;
	}
	
	/**
	 * 大类  查找一个节点的所有直属子节点（所有后代）。
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	private List<String> queryEntTypeChild(String str[]) throws OptimusException{
		if(str==null||str.length<1){
			return null;
		}
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		HashSet h=new HashSet();
		for (int i=0;i<str.length;i++){
			StringBuffer sql = new StringBuffer();
			List<String> str1 = new ArrayList<String>(); 
			sql.append(
					"select m.code from com_enttype_new m where m.is_stand ='X' start with m.code= ? connect by m.pid =prior m.code ");
	
			sql.append(" order  by  code ");
			
			str1.add(str[i]);
			List<Map> list1 = dao.queryForList(sql.toString(), str1);
			if(list1!=null && list1.size()>0){
				for(Map map1 :list1){
					h.add(map1.get("code"));
				}
			}
		}
		
		List<String> list2 = new ArrayList<String> ();  
		list2.addAll(h);  
		return list2;
	}
	public String queryCount() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select count(1) as cnt from V_ENT_SELECT t ";
		List<String> params = new ArrayList<String>();
		List<Map> countList = dao.queryForList(sql, params);
		String count = countList.get(0).get("cnt").toString();
		return count;
	}
}
