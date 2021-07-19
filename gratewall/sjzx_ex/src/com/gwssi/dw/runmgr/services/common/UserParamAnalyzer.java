package com.gwssi.dw.runmgr.services.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserParamAnalyzer
{
	/** key为字段名，value为用户提供的值 */
	private Map paramMap = null;
	private String sql = null;
	private static final String USER_PARAM_SIGN = "（参数值）";

	public UserParamAnalyzer(Map params)
	{
		paramMap = params;
	}
	
	public String createSQL(){
		String originSQL = ""+paramMap.get("QUERY_SQL");
		String sql = analyseUserParams(originSQL);
		
		return sql.toString();
	}
	
	private String analyseUserParams(String sql){
		Pattern p = Pattern.compile("\\w+\\.\\w+\\s(([=><])||(<>)|(>=)||(<=)||(like)||(LIKE)||(in)||(IN))\\s+((\\('"+USER_PARAM_SIGN+"'\\))|('"+USER_PARAM_SIGN+"')|('%"+USER_PARAM_SIGN+"%')|("+USER_PARAM_SIGN+"))");
		Matcher m = p.matcher(sql);
		while (m.find()){
			String one = m.group();
			//System.out.println(one);
			Pattern p2 = Pattern.compile("\\.\\w+");
			Matcher m2 = p2.matcher(one);
			if(m2.find()){
				String key = m2.group().substring(1);
				String v = paramMap.get(key)==null?"":(String)paramMap.get(key);
				if(isINPattern(one)){
					//System.out.println("is in pattern......");
					v = v.replaceAll(Constants.MULTI_COLUMN_IN_SEPARATOR, "','");
					sql = sql.replaceFirst(USER_PARAM_SIGN, v);
					System.out.println("sql:"+sql);
				}else if(v.indexOf(Constants.MULTI_COLUMN_CONDITION_SEPARATOR) != -1){
					String first = v.substring(0, v.indexOf(Constants.MULTI_COLUMN_CONDITION_SEPARATOR));
					//System.out.println("first--->"+first);
					sql = sql.replaceFirst(USER_PARAM_SIGN, first);
					paramMap.put(key, v.substring(v.indexOf(Constants.MULTI_COLUMN_CONDITION_SEPARATOR) + 1));
				}else{
					sql = sql.replaceFirst(USER_PARAM_SIGN, v);
				}
			}
		}
		return sql;
	}
	
	public static boolean isINPattern(String s){
		Pattern p2 = Pattern.compile("\\w+\\.\\w+\\s+((IN)||(in))\\s+(\\('"+USER_PARAM_SIGN+"'\\))");
		Matcher m2 = p2.matcher(s);
		
		return m2.find();
	}
	
	public static void main(String[] args)
	{
		Map map = new HashMap();
		map.put("REG_BUS_ENT_ID", "A3000000000000100000000028000249|A3000000000000100000000028000250");
		
		String sql = new UserParamAnalyzer(map).analyseUserParams("SELECT REG_BUS_ENT.REG_BUS_ENT_ID, REG_BUS_ENT.ORGAN_CODE, REG_BUS_ENT.PRI_P_ID, REG_BUS_ENT.REG_NO, REG_BUS_ENT.OLD_REG_NO, REG_BUS_ENT.LIC_REG_NO FROM REG_BUS_ENT  WHERE ( REG_BUS_ENT.REG_BUS_ENT_ID IN '（参数值）'  )");
		System.out.println(sql);
//		UserParamAnalyzer.isINPattern("aaa.aa in ('11','22')");
		
	}
	
	/**
	 * 解析SQL语句，查找用户参数部分
	 * @param sql
	 */
	protected void setUserParams(String sql){
		String conditionStr = sql.substring(sql.indexOf("WHERE") + 5);
		System.out.println(conditionStr);
		
//		Pattern p = Pattern.compile("\\w+\\.\\w+(([=><])||(<>)||(\\s+BETWEEN\\s+\\?\\s+AND\\s+))\\?");
//		Matcher m = p.matcher(sql);
//		if(m.find(0)){
//			paramMap = new HashMap();
//			int start = 0;
//			Pattern p2 = Pattern.compile("\\w+\\.\\w+");
//			while (start < sql.length() && m.find(start)){
//				String con = m.group();
//				start = m.end() + 1;
//				Matcher m2 = p2.matcher(con);
//				m2.find(0);
//				String v = m2.group();
//				paramMap.put(con, v.substring(v.indexOf(".")+1, v.length()).toUpperCase());
//			}
//		}
	}
	
	/**
	 * 获得所有参数字段
	 * @return Collection
	 */
	public Collection getAllParam(){
		if(paramMap == null){
			return null;
		}
		
		return paramMap.values();
	}
	
	/**
	 * 获得目标字段的多个参数
	 * @param col
	 * @return List
	 */
	public List getMultiCols(String col){
		Set set = paramMap.keySet();
		Iterator it = set.iterator();
		List l = new ArrayList();
		while(it.hasNext()){
			String key = (String)it.next();
			String c = (String)paramMap.get(key);
			if(c.trim().equalsIgnoreCase(col))
				l.add(key);
		}
		
		return l;
	}
	
	public Map getParamMap()
	{
		return paramMap;
	}
	
	public void setParamMap(Map paramMap)
	{
		this.paramMap = paramMap;
	}

	public String getSql()
	{
		return sql;
	}
	
	public String getNewSql()
	{
		if(paramMap != null){
			Set set = paramMap.keySet();
			Iterator it = set.iterator();
			while(it.hasNext()){
				String str = (String)it.next();
				String newValue = (String)paramMap.get(str);
				String toReplace = ParamUtil.replaceAllRegexKeySymbol(str, '?');
				System.out.println("replace from ===> "+toReplace);
				System.out.println("replace to ===> "+newValue);
				sql = sql.replaceAll(toReplace, newValue);
			}
		}
		return sql;
	}
	
	public static void main1(String[] args)
	{
//		UserParamAnalyzer up = new UserParamAnalyzer("");
//		String sql = "SELECT * FROM REG_BUS_ENT, REG_BUS_ENT_MEM, REG_BUS_ENT_JOB  WHERE (   REG_BUS_ENT.REG_BUS_ENT_ID = REG_BUS_ENT_MEM.REG_BUS_ENT_ID  AND REG_BUS_ENT_JOB.REG_BUS_ENT_MEM_ID = REG_BUS_ENT_MEM.REG_BUS_ENT_MEM_ID  )  AND ( REG_BUS_ENT.ENT_NAME like '%用友%'  ) AND ( REG_BUS_ENT.ENT_NAME = '（参数值）' AND REG_BUS_ENT.ENT_SORT = '（参数值）'  )";
//		String conditionStr = sql.substring(sql.indexOf("WHERE") + 5);
//		System.out.println(conditionStr);
//		Pattern p = Pattern.compile("[AND||and||OR||or]\\s?\\w?\\.\\w?\\s?"+UserParamAnalyzer.USER_PARAM_SIGN);
//		Matcher m = p.matcher(sql);
//		m.find();
//		System.out.println(m.group());
//		System.out.println(m.end());
//		System.out.println(sql.indexOf("）"));
//		System.out.println(sql.substring(sql.indexOf("）")));
//		if(m.find()){
//			int start = 0;
//			Pattern p2 = Pattern.compile("\\w+\\.\\w+");
//			while (start < sql.length() && m.find(start)){
//				String con = m.group();
//				start = m.end() + 1;
//				Matcher m2 = p2.matcher(con);
//				m2.find(0);
//				String v = m2.group();
//			}
//		}
//		String ss = "aa=? or bb=? and (cc=? and dd=?) or aa=?";
//		//"aa != null || bb!=null && (cc != null && dd!=null) || aa!=null"
//		Map map = new HashMap();
//		map.put("aa", "aa");
////		if(map.get("aa") != null || map.get("bb") != null){
////			System.out.println("----");
////		}
//		bsh.Interpreter interpreter= new bsh.Interpreter();
//		try {
//			Object aa = map.get("aa");
//			Object bb = map.get("bb");
//			System.out.println((aa != null || bb != null));
//			interpreter.set("aa", aa);
//			interpreter.set("bb", bb);
//			interpreter.set("boolean", interpreter.eval("aa != null || bb != null"));
//			System.out.println(interpreter.get("boolean"));
//		} catch (EvalError e) {
//			e.printStackTrace();
//		}
		
		
	}
}
