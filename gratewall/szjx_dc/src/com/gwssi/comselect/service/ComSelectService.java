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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.comselect.utils.ComSqlUtil;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "comSelectService")
public class ComSelectService extends BaseService{

	
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
	private static String getDc_code_KEY(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("dcCode.datasourcekey");

		return key;
	}
	
	/**
	 * excel导出查询
	 * @param bo
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryExcelEntQuery(EntSelectQueryBo bo) throws OptimusException{
		String querytype="excel";
		List<Map> list=queryEnt(bo, querytype);
		return list;
	}
	
	
	/**
	 * 非分页查询
	 * @param bo
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryNoPageQuery(EntSelectQueryBo bo) throws OptimusException{
		String querytype="nopage";
		List<Map> list=queryEnt(bo, querytype);
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
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryPageQuery(EntSelectQueryBo bo) throws OptimusException {
		String querytype="page";
		List<Map> list=queryEnt(bo, querytype);
		return list;
	}
	public List<Map> queryEnt(EntSelectQueryBo bo,String querytype) throws OptimusException {
	
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		List<String> params = new ArrayList<String>();
		StringBuffer sql= new StringBuffer();
		sql.append("select * from ENT_SELECT t where 1=1 ");
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
			
			sql.append( " and t.industryphy=" ).append("?").append("   ");
			params.add(bo.getIndustryphy());
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
		String enttype_radio =bo.getEnttype_radio();
		if(StringUtils.equals(enttype_radio, "00")){//大类
			if(bo.getEnttype()!=null&&bo.getEnttype().length>0){
			List<String> list=	queryEntTypeChild(bo.getEnttype());
				if(list!=null&&list.size()>0){
					sql.append(" and t.enttype in (");
					sql.append(prepareSqlIn(list.size()));
					sql.append(")  ");
					params.addAll(list);
				}
			}
		}else{//小类
			if(bo.getEnttype()!=null&&bo.getEnttype().length>0){
				sql.append(" and t.enttype in (");
				sql.append(prepareSqlIn(bo.getEnttype().length));
				sql.append(")  ");
				List<String> list = Arrays.asList(bo.getEnttype()); 
				params.addAll(list);
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
		System.out.println(sql);
		System.out.println(params);
		if("page".equals(querytype)){
			return dao.pageQueryForList(sql.toString(), params);
		}else if("excel".equals(querytype)){//这里需要修改  改为视图
			String sql33="select * from (  select t_.*, rownum as rownum_ from (";
			sql33=sql33+sql.toString()+") t_ where rownum <= 5000 ) where rownum_ > 0";
			//这处需要优化
			return typechage(dao.queryForList(sql33.toString(), params));
		}else{
			String sql33="select * from (  select t_.*, rownum as rownum_ from (";
			sql33=sql33+sql.toString()+") t_ where rownum <= 100 ) where rownum_ > 0";
			//这处需要优化
			return typechage(dao.queryForList(sql33.toString(), params));
		}
		//return dao.queryForList(sql.toString(), params);
	}
	/**
	 * 
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
	private Map<String,Object> getGuanxiaquyu(){
		
		return null;
	}
	
	/**
	 * 获取代码 值
	 * @param string
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> queryCode_value(String string) throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO(this.getDc_code_KEY());
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		 List list=null;
		if("regcapcur".equals(string)){//币种
			 sql.append("select code as value, currency  as text from tcCurrency  ");
			 list=dao.queryForList(sql.toString(),null);
		}else if("industryphy".equals(string)){ //行业
			 sql.append("select code as value, code_desc  as text from C00014  ");
			 list=dao.queryForList(sql.toString(),null);

		}else if("regorg".equals(string)){//管辖区域
			 sql.append("select code as value, deptname  as text from tcDept  where depttype= '4'  ");
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
		IPersistenceDAO dao=getPersistenceDAO(this.getDc_code_KEY());
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		 List list=null;
		if("adminbrancode".equals(type)){//所属建管所
			 sql.append("select code as value, deptname  as text from tcDept  where depttype= '6'  and upperdept=? ");
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
				"select code as ID, type_name AS NAME , nvl(pid,'all') as pid, is_stand AS STANDE from com_enttype  ");
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
		IPersistenceDAO dao = getPersistenceDAO(this.getDc_code_KEY());
		HashSet h=new HashSet();
		for (int i=0;i<str.length;i++){
			StringBuffer sql = new StringBuffer();
			List<String> str1 = new ArrayList<String>(); 
			sql.append(
					"select m.code from com_enttype m where m.is_stand ='Y' start with m.code= ? connect by m.pid =prior m.code ");
	
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
	
}
