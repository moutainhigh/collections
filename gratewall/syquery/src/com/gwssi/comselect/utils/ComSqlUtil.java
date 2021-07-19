package com.gwssi.comselect.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public  class ComSqlUtil {
	
	/**
	 * 同一个字段 and 拼接
	 * @param term 条件 include 包含 
	 * @param value 具体值
	 * @param filename 字段名字
	 * @return
	 */
	public static Map<String,Object> getORSql(String[] term,String[] value,String filename){
		if(term!=null&&term.length>0){
				List<String> list2 = new ArrayList<String>();
				StringBuffer sql2=new StringBuffer();
				String[] entname_term=term;
				String[] entname =value;
				int len1=entname.length;
				int len2=entname_term.length;
				int length=0;
				if(len1>=len2){
					length=len2;
				}
				if(length>0){
					//sql2.append(" and (1=2");
					sql2.append(" and (1=1");
				}
				
				for(int i=0; i<length;i++){
					if(StringUtils.isNotBlank(entname_term[i])&&StringUtils.isNotBlank(entname[i])){
						if("include".equals(entname_term[i])){
							sql2.append(" and  ").append(filename).append(" like ?");
							//list2.add(entname[i]+"%");
							list2.add("%"+entname[i]+"%");
						}else if("notinclude".equals(entname_term[i])){
							sql2.append(" and  ").append(filename).append(" not like ?");
							//list2.add(entname[i]+"%");
							list2.add("%"+entname[i]+"%");
						}else{
							sql2.append(" and  ").append(filename).append(" = ?");
							list2.add(entname[i]);
						}
					}
				}
				
				if(length>0){
					sql2.append(")   ");
				}
				
				Map<String,Object> map1 =null;
				
				
				if(sql2.indexOf("like")>=0){
					map1 =new HashMap<String,Object>();
					map1.put("sql", sql2);
					map1.put("parms", list2);
				}else if(sql2.indexOf("=")>=0){
					map1 =new HashMap<String,Object>();
					map1.put("sql", sql2);
					map1.put("parms", list2);
				}
				
				return map1;
		}else{
			return null;
		}
	}

	
	
	/**
	 * 4个条件
	 * @param regorg 管辖区域
	 * @param adminbrancode 建管所
	 * @param gongzuowangge 工作网格
	 * @param danyuanwangge 单元网格
	 * @return
	 */
	public static Map getORS4ql(String[] regorg, String[] adminbrancode,
			String[] gongzuowangge, String[] danyuanwangge) {
		List<String> parms = new ArrayList<String>();
		StringBuffer sql=new StringBuffer();
		sql.append(" and (1=2 ");
		for(int i=0;i<regorg.length;i++){
			//sql.append("  and ((1=1");
			if(StringUtils.isNotEmpty(regorg[i])){
				if("9999".equals(regorg[i])){
					sql.append(" or (  regorg is null ");
				}
				else{
					sql.append("  or (  regorg = ?");
					parms.add(regorg[i]);
				}
				if(adminbrancode!=null && adminbrancode.length>i &&StringUtils.isNotEmpty(adminbrancode[i])){
					sql.append(" and adminbrancode= ? ");
					parms.add(adminbrancode[i]);
				}
				if(gongzuowangge!=null && gongzuowangge.length>i &&StringUtils.isNotEmpty(gongzuowangge[i])){
					sql.append(" and gongzuowangge= ? ");
					parms.add(gongzuowangge[i]);
				}			
				if(danyuanwangge!=null && danyuanwangge.length>i &&StringUtils.isNotEmpty(danyuanwangge[i])){
					sql.append(" and danyuanwangge= ? ");
					parms.add(danyuanwangge[i]);
				}	
				sql.append( ")");
			}
		}
		
		sql.append(")");
		
		Map map1=null;
		if(sql.indexOf("or")>=0){
			map1 =new HashMap<String,Object>();
			map1.put("sql", sql);
			map1.put("parms", parms);
		}


		
		return map1;
	}

}
