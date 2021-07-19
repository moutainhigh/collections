package com.gwssi.comselect.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * 法定代表人实现层，批量查询功能的实现逻辑
 * 
 * @author ye
 * 
 */

@Service
public class BatchFDDBRService extends BaseService {

	
	@SuppressWarnings("rawtypes")
	public List getFDDBRList(Map queryParam)	throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List params = (List) queryParam.get("list");// 得到要操作的数据类型
		String flag = (String) queryParam.get("flag");
		Map maps = null;
		String sql = null;
		List<String> queryParamsCondition = null;
	
		List resultList = null; //
		if (flag.equals("1")){
			StringBuffer bf = new StringBuffer();
			for (int i = 0; i < params.size(); i++) {
				Map map = (Map) params.get(i);
				String name = (String) map.get("name");
				if(i==params.size()-1){
					bf.append("'" + name+"'");
				}else{
					bf.append("'"+name+"',");
				}
			}
			sql = "select * from v_legalpersion_base_batch where 1=1 and lerep in("+bf.toString()+") order by lerep"; // 1649
			resultList  = dao.pageQueryForList(sql,null);
		} else if (flag.equals("2")) {
			StringBuffer bf = new StringBuffer();
			for (int i = 0; i < params.size(); i++) {
				Map map = (Map) params.get(i);
				String name = (String) map.get("cerno");
				if(i==params.size()-1){
					bf.append("'" + name+"'");
				}else{
					bf.append("'"+name+"',");
				}
			}
			sql = "select * from v_legalpersion_base_batch where 1=1 and  cerNo in("+bf.toString()+") order by cerNo"; // 1724
			resultList  = dao.pageQueryForList(sql,null);
		} else {
			for (int i = 0; i < params.size(); i++) {
				queryParamsCondition = new ArrayList<String>();
				maps = (Map) params.get(i);
				String name = (String) maps.get("name");
				String cerNo = (String) maps.get("cerNo");
				sql = "select * from v_legalpersion_base_batch where lerep =? and cerNo=? order by lerep"; // 1724
				queryParamsCondition.add(name.trim());
				queryParamsCondition.add(cerNo.trim());
			}
			resultList  = dao.pageQueryForList(sql, queryParamsCondition);
		}
		System.out.println("  ====================   ");
		System.out.println(resultList);
		
		if(resultList!=null&&resultList.size()>0){
			return resultList;
		}else{
			return null;
		}
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getFDDBRDownList(Map queryParam) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List params = (List) queryParam.get("list");// 得到要操作的数据类型
		String flag = (String) queryParam.get("flag");
		Map maps = null;
		String sql = null;
		List<String> queryParamsCondition = null;
	
		List resultList = null; //
		if (flag.equals("1")){
			StringBuffer bf = new StringBuffer();
			for (int i = 0; i < params.size(); i++) {
				Map map = (Map) params.get(i);
				String name = (String) map.get("name");
				if(i==params.size()-1){
					bf.append("'" + name+"'");
				}else{
					bf.append("'"+name+"',");
				}
			}
			//sql = "select * from v_legalpersion_base_batch where 1=1 and lerep in("+bf.toString()+")"; // 1649
			sql = "select * from (  select t_.*, rownum as rownum_ from ("; // 1649
			sql+= "select * from v_legalpersion_base_batch where 1=1 and  lerep in("+bf.toString()+") order by lerep" + ") t_ where rownum <= 5000 ) where rownum_ > 0";
			System.out.println(sql);
			
			resultList  = dao.queryForList(sql,null);
			
		} else if (flag.equals("2")) {
			StringBuffer bf = new StringBuffer();
			for (int i = 0; i < params.size(); i++) {
				Map map = (Map) params.get(i);
				String name = (String) map.get("cerno");
				if(i==params.size()-1){
					bf.append("'" + name+"'");
				}else{
					bf.append("'"+name+"',");
				}
			}
			//sql = "select * from v_legalpersion_base_batch where 1=1 and  cerNo in("+bf.toString()+")"; // 1724
			sql = "select * from (  select t_.*, rownum as rownum_ from ("; // 1649
			sql+= "select * from v_legalpersion_base_batch where 1=1 and  cerNo in("+bf.toString()+") order by cerNo" + ") t_ where rownum <= 5000 ) where rownum_ > 0";
			System.out.println(sql);
			
			resultList  = dao.queryForList(sql,null);
		} else {
			for (int i = 0; i < params.size(); i++) {
				queryParamsCondition = new ArrayList<String>();
				maps = (Map) params.get(i);
				String name = (String) maps.get("name");
				String cerNo = (String) maps.get("cerNo");
				sql = "select * from (  select t_.*, rownum as rownum_ from ("; // 1649
				sql+= "select * from v_legalpersion_base_batch where 1=1 and  lerep =("+name+") and cerNo =("+cerNo+") order by lerep" + ") t_ where rownum <= 5000 ) where rownum_ > 0";
				
				//sql = "select * from v_legalpersion_base_batch where lerep =? and cerNo=?"; // 1724
				//queryParamsCondition.add(name.trim());
				//queryParamsCondition.add(cerNo.trim());
			}
			System.out.println(sql);
			resultList  = dao.queryForList(sql, queryParamsCondition);
		}
		if(resultList!=null&&resultList.size()>0){
			
			
			System.out.println("MMMMMMMM  " + resultList);
			return resultList;
		}else{
			return null;
		}
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
	
	
	
	public boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}

	
	// 判断以数字开头的字符串的正则表达式:"[a-zA-Z]*"
	// 判断一个字符串是否含有数字
	public static boolean hasDigit(String content) {
	    boolean flag = false;
	    Pattern p = Pattern.compile(".*\\d+.*");
	    Matcher m = p.matcher(content);
	    if (m.matches()) {
	        flag = true;
	    }
	    return flag;
	}







	
	/*
	private void commonMethod(Map map, StringBuffer sql, List list) {
		if (map!=null) {
			Object objectNbmark = map.get("nbmark");//年报标识，1是已年报，0是未年报
			Object objectEntmark = map.get("entmark");//商事主体标识,1是企业，0是个体
			String  sObjectNbmark = (String) objectNbmark;
			String  sObjectEntmark = (String) objectEntmark;
			//下面是分四中情况进行判断查询：1.企业已年报 ； 2.企业未年报 ；  3.个体已年报 ； 4.个体未年报
			if ("1".equals(sObjectNbmark)&&"1".equals(sObjectEntmark)) {//企业已年报
				sql.append("select p.ancheyear,p.anchedate,p.ancheid, p.pripid,t.regno,");
				sql.append("t.pripid as id, t.entname, t.enttype as enttype,t.estdate,t.reccap,t.regorg ");
				sql.append("from dc_ra_mer_base_query t , dc_NB_QY_JBXX p where t.entid  = p.pripid and p.ifpub >1 and 1=1 ");
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
				
		*/	
}
