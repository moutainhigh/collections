package com.gwssi.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * 解析SQL配置文件 内容包括TableName和Sql的解析
 * @author lifx
 * 
 */
public class SQLConfig{
	
	
	private static SQLConfig sqlConfig = null; 
	private Map mapSql = new HashMap();
	
	private final String SYSTEM = "system";
	private final String ID = "id";
	
	private SQLConfig() {
		try {
			//获取数据库类型  区分
			
			String dbType = java.util.ResourceBundle.getBundle("app").getString("dbType");
			
			SAXBuilder builder = new SAXBuilder();
			Document read_doc = builder.build(Constants.CONFIGPATH + Constants.SQL_CONFIG_FILE);
			
//			Document read_doc = builder.build("D:\\csdb\\src\\" + Constants.SQL_CONFIG_FILE);
			Element root = read_doc.getRootElement();
			Element system = (Element)root.getChildren(SYSTEM).get(0);
			List listModels = system.getChildren();
			for(int i = 0;i < listModels.size();i++){
				Element model = (Element)listModels.get(i);
//	          	String strModelName = model.getAttribute("name").getValue();
	          	
	          	List listFunction = model.getChildren();
	          	for(int j = 0;j < listFunction.size();j++){
	          		Element function = (Element)listFunction.get(j);
					//	          		String strFunName = function.getAttribute("name").getValue();
	          		String strFunId = function.getAttribute(ID).getValue();
	          		
	          		List listSql = function.getChildren(dbType);
		          	for(int x = 0;x < listSql.size();x++){
		          		Element sql = (Element)listSql.get(x);
		          		String strSqlId = sql.getAttribute(ID).getValue();
//		          		String strSqlName = sql.getAttribute("name").getValue();
		          		String strSql = sql.getText();
		          		mapSql.put(strFunId+"-"+strSqlId, strSql);
		          	}
	          	}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args){
		System.out.println(SQLConfig.get("10000-0001"));
	}
	public synchronized static SQLConfig getInstance(){
		if (sqlConfig == null){
			sqlConfig = new SQLConfig();
		}
		return sqlConfig;
	}
	
	public static synchronized void reload(){
		sqlConfig = new SQLConfig();
	}
	 
	public Map getSqlConfig(){
	    return mapSql;
	}
	
	/**
	 * 根据key得到资源文件信息内容
	 * @param key
	 * @return
	 */
	public static String get(String key){
		SQLConfig sqlConfig = getInstance();
		return (String)sqlConfig.getSqlConfig().get(key);
	}
	
	/**
	 * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
	 * @param key 为List 替换顺序从0开始
	 * @return
	 */
	public static String get(String key,List keys){
		SQLConfig sqlConfig = getInstance();
		String str = (String)sqlConfig.getSqlConfig().get(key);
		int i = 0 ; 
		for (Iterator iter = keys.iterator();iter.hasNext();i++){
			String s = (String)iter.next();
			str = StringUtil.replace(str, StringUtil.replace(Constants.sx,Constants.x,String.valueOf(i)), s);
		}
		return str;
	}
    
    /**
     * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
     * @param key : sql配置文件sql语句配置项的ID值
     * @param keys ：HashMap对象，id为参数位置，value为替换值
     * @return
     */
    public static String get(String key,HashMap keys){
        SQLConfig sqlConfig = getInstance();
        String str = (String)sqlConfig.getSqlConfig().get(key);
        
        Set set = keys.keySet();
        for(Iterator iter = set.iterator();iter.hasNext();){
            String s = (String)iter.next();
            str = StringUtil.replace(str, s,(String)keys.get(s));
        }
        return str;
    }
    
    	/**
	 * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1,String s2,String s3,String s4,String s5,String s6,String s7){
		SQLConfig sqlConfig = getInstance();
		String str = (String)sqlConfig.getSqlConfig().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		str = StringUtil.replace(str, Constants.s2, s2);
		str = StringUtil.replace(str, Constants.s3, s3);
		str = StringUtil.replace(str, Constants.s4, s4);
		str = StringUtil.replace(str, Constants.s5, s5);
		str = StringUtil.replace(str, Constants.s6, s6);
		str = StringUtil.replace(str, Constants.s7, s7);
		return str;
	}
	
	/**
	 * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1,String s2,String s3,String s4,String s5,String s6){
		SQLConfig sqlConfig = getInstance();
		String str = (String)sqlConfig.getSqlConfig().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		str = StringUtil.replace(str, Constants.s2, s2);
		str = StringUtil.replace(str, Constants.s3, s3);
		str = StringUtil.replace(str, Constants.s4, s4);
		str = StringUtil.replace(str, Constants.s5, s5);
		str = StringUtil.replace(str, Constants.s6, s6);
		return str;
	}
	
	/**
	 * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1,String s2,String s3,String s4,String s5){
		SQLConfig sqlConfig = getInstance();
		String str = (String)sqlConfig.getSqlConfig().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		str = StringUtil.replace(str, Constants.s2, s2);
		str = StringUtil.replace(str, Constants.s3, s3);
		str = StringUtil.replace(str, Constants.s4, s4);
		str = StringUtil.replace(str, Constants.s5, s5);
		return str;
	}
	/**
	 * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1,String s2,String s3,String s4){
		SQLConfig sqlConfig = getInstance();
		String str = (String)sqlConfig.getSqlConfig().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		str = StringUtil.replace(str, Constants.s2, s2);
		str = StringUtil.replace(str, Constants.s3, s3);
		str = StringUtil.replace(str, Constants.s4, s4);
		return str;
	}
	
	/**
	 * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1,String s2,String s3){
		SQLConfig sqlConfig = getInstance();
		String str = (String)sqlConfig.getSqlConfig().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		str = StringUtil.replace(str, Constants.s2, s2);
		str = StringUtil.replace(str, Constants.s3, s3);
		return str;
	}
	
	/**
	 * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1,String s2){
		SQLConfig sqlConfig = getInstance();
		String str = (String)sqlConfig.getSqlConfig().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		str = StringUtil.replace(str, Constants.s2, s2);
		return str;
	}
	
	/**
	 * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1){
		SQLConfig sqlConfig = getInstance();
		String str = (String)sqlConfig.getSqlConfig().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		return str;
	}
	
	/**
	 * 根据key得到资源文件信息内容，并通过替换将定义的Sql替换成数值。
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0){
		SQLConfig sqlConfig = getInstance();
		String str = (String)sqlConfig.getSqlConfig().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		return str;
	}
}
