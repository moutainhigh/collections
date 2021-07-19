package com.gwssi.common.rodimus.report.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.common.rodimus.report.entity.ReportConfig;
import com.gwssi.common.rodimus.report.util.DaoUtil;
import com.gwssi.common.rodimus.report.util.FileUtil;


public class ReportConfigManager {
	

	protected final static Logger logger = Logger.getLogger(ReportConfigManager.class);
	
	private static Map<String, Object> LOCAL_CACHE = new HashMap<String, Object>();
	
	static{
		//init();
	}
	
	/**
	 * 根据globalId获取配置信息。
	 * 
	 * 从本地缓存取得。
	 * 
	 * @param globalId
	 * @return
	 */
	public static JSONObject getConfigWithCache(String globalId){
		JSONObject ret = (JSONObject)LOCAL_CACHE.get(globalId);
		if(ret==null){
			refresh(globalId);
			ret = (JSONObject)LOCAL_CACHE.get(globalId);
		}
		return ret ;
	}
	
	public static JSONObject getConfig(String globalId){
		ReportConfig reportConfig = getConfigFromDatabase(globalId);
		if(reportConfig==null){
			throw new RuntimeException("【报表配置错误】从数据库获得【"+globalId+"】的配置信息失败。");
		}else{
			String config=reportConfig.getConfig();
			
			// update local cache 
			JSONObject joConfig = parse(config);
			
			joConfig.put("title", reportConfig.getName());
			joConfig.put("type", reportConfig.getType());
			joConfig.put("name", reportConfig.getCode());
			joConfig.put("remark", reportConfig.getRemark());
			
			return joConfig;
		}
	}
	
	/**
	 * 更新本地缓存。
	 * 
	 * @param globalId
	 */
	public static void refresh(String globalId){
		// get lastest config value 
		ReportConfig reportConfig = getConfigFromDatabase(globalId);
		if(reportConfig==null){
			throw new RuntimeException("未找到指定代码的报表配置。");
		}
		String config=reportConfig.getConfig();
		// update local cache 
		JSONObject joConfig = parse(config);
		if(joConfig==null){
			return ;
		}else{
			joConfig.put("title", reportConfig.getName());
			joConfig.put("type", reportConfig.getType());
			joConfig.put("name", reportConfig.getCode());
		}
		LOCAL_CACHE.put(globalId, joConfig);
	}
	
	/**
	 * 根据globalId获取配置信息。
	 * 
	 * 从本地缓存取得。
	 * 
	 * @param globalId
	 * @return
	 */
	public static JSONObject getDummyConfig(String globalId){
		String config = getConfigFromLocalFile(globalId);
		if(StringUtils.isEmpty(config)){
			return null;
		}
		// update local cache 
		JSONObject joConfig = parse(config);
		return joConfig ;
	}
	
	public static void init(){
		try{
			LOCAL_CACHE.clear();
			List<Object> params = new ArrayList<Object>();
			params.add("1");
			String sql = "select * from ReportConfig c where c.flag = ? ";
			List<Map<String, Object>> list = DaoUtil.getDefaultInstance().queryForList(sql, params); 
			if(list!=null && !list.isEmpty()){
				JSONObject joConfig;
				for(int i = 0;i<list.size();++i){
					String code = i+"",name="";
					try{
						ReportConfig reportConfig = (ReportConfig) list.get(i);
						name = reportConfig.getName();
						code = reportConfig.getCode();
						joConfig = parse(reportConfig.getConfig());
						joConfig.put("title", name);
						joConfig.put("type", reportConfig.getType());
						joConfig.put("name", code);
						LOCAL_CACHE.put(reportConfig.getCode(), joConfig);
						
						logger.info(String.format("报表[%s][%s]加载成功。",code,name));
					}catch(Throwable e){
						e.printStackTrace();
						logger.info(String.format("报表[%s][%s]加载失败。",code,name));
					}
				}
			}
		}catch(Throwable e){
			e.printStackTrace();
		}
		logger.info(String.format("报表加载完成。"));
	}
	
	
	/**
	 * 这是个极其耗时的操作。
	 * 
	 * @param jsonString
	 * @return
	 */
	public static JSONObject parse(String jsonString) {
		// 1、 准备参数
		JSONObject ret = null;
		long start = System.currentTimeMillis();
		// 2、解析为JSON对象
		ret = JSON.parseObject(jsonString);
		if(ret==null){
			logger.error("parse config file error : " + jsonString);
			throw new RuntimeException("配置中存在错误，请联系管理员。");
		}else{
			logger.debug("config file object : \r\n" + jsonString);
		}
		
		// 3、对Header配置信息进行重构，便于使用
		JSONArray jaHeader = ret.getJSONArray("header");

		if(jaHeader==null || jaHeader.size()==0){
			throw new RuntimeException("未配置报表表头信息。");
		}
		
		int headerRowCnt = 0;
		List<JSONObject> headerDataConfig = new ArrayList<JSONObject>();
		// 设置colspan和level
		headerRowCnt = prepareHeaderTree(jaHeader,headerDataConfig,1);
		
		// 设置rowspan
		JSONObject joColumn;int level,span;
		List<JSONObject> jaRetHeaderDataConfig = new ArrayList<JSONObject>();
		List<JSONObject> jaRetDict = new ArrayList<JSONObject>();
		JSONObject joRetColumn;
		for(int i=0;i<headerDataConfig.size();++i){
			joColumn = headerDataConfig.get(i);
			level = joColumn.getInteger("level");
			span = headerRowCnt - level;
			if(span>0){
				joColumn.put("rowspan", span + 1);
			}
			joRetColumn = new JSONObject();
			joRetColumn.put("label", joColumn.getString("label"));
			joRetColumn.put("name", joColumn.getString("name"));
			joRetColumn.put("align", joColumn.getString("align"));
			joRetColumn.put("stat", joColumn.getString("stat"));
			joRetColumn.put("expr", joColumn.getString("expr"));
			joRetColumn.put("format", joColumn.getString("format"));
			
			String dictTypeId = joColumn.getString("dict");
			if(!StringUtils.isEmpty(dictTypeId)){
				JSONObject dict = new JSONObject();
				dict.put("name", joColumn.getString("name"));
				dict.put("dict", joColumn.getString("dict"));
				jaRetDict.add(dict);
			}
			
			jaRetHeaderDataConfig.add(joRetColumn);
		}
		
		// 将Header整理为需要的形式
		JSONArray jaRetHeader = new JSONArray();
		JSONArray jaRetRow ; 
		for(int i=0;i<headerRowCnt;++i){
			jaRetRow = new JSONArray();
			
			List<JSONObject> joRow =  new ArrayList<JSONObject>();
			joRow = getColumnsAtLevel(joRow,jaHeader,i+1);
			for(int c=0;c<joRow.size();++c){
				JSONObject col = joRow.get(c);
				joRetColumn = new JSONObject();
				joRetColumn.put("label", col.getString("label"));
				joRetColumn.put("colspan", col.getString("colspan"));
				joRetColumn.put("rowspan", col.getString("rowspan"));
				jaRetRow.add(joRetColumn);
			}
			jaRetHeader.add(jaRetRow);
		}
		ret.remove("header");
		ret.put("head", jaRetHeader);
		ret.put("headerRowCnt", headerRowCnt);
		ret.put("headerDataConfig", jaRetHeaderDataConfig);
		ret.put("dict", jaRetDict);
		
		long end = System.currentTimeMillis();
		logger.debug("获取配置信息 耗时："+(end-start));
	
		return ret;
	}
	
	private static List<JSONObject> getColumnsAtLevel(List<JSONObject> ret,JSONArray jaHeader, int level) {
		if(ret==null){
			ret = new ArrayList<JSONObject>();
		}
		if(jaHeader==null || jaHeader.size()==0){
			return ret;
		}
		
		JSONObject joColumn;JSONArray jaChildren ;int columnLevel;
		for(int i = 0;i<jaHeader.size();++i){
			joColumn = jaHeader.getJSONObject(i);
			columnLevel = joColumn.getIntValue("level");
			if(columnLevel==level){
				ret.add(joColumn);
			}
			jaChildren = joColumn.getJSONArray("columns");
			if(jaChildren!=null && jaChildren.size()>0){ // 没有子节点了
				getColumnsAtLevel(ret,jaChildren,level);
			}
		}
		return ret;
	}

	public static int prepareHeaderTree(JSONArray tree,List<JSONObject> treeDataConfig,int level){
		if(tree==null || tree.size()==0){
			return 0;
		}
		int childRowCnt = 0 , maxChildRowCnt = 0;
		JSONObject joColumn;
		JSONArray jaChildren ;
		for(int i = 0;i<tree.size();++i){
			joColumn = tree.getJSONObject(i);
			joColumn.put("level", level);
			jaChildren = joColumn.getJSONArray("columns");
			if(jaChildren==null || jaChildren.size()==0){ // 没有子节点了
				treeDataConfig.add(joColumn);
				childRowCnt = 0;
			}else{
				joColumn.put("colspan", jaChildren.size());
				childRowCnt = prepareHeaderTree(jaChildren,treeDataConfig,level+1);
			}
			if(childRowCnt>maxChildRowCnt){
				maxChildRowCnt = childRowCnt;
			}
		}
		int rowCnt = 1 + maxChildRowCnt;
		
		return rowCnt;
	}
	protected static ReportConfig getConfigFromDatabase(String globalId) {
		String sql = "select * from rpt_config c where c.code = ? ";
		List<ReportConfig> listBo = DaoUtil.getDefaultInstance().queryForListBo(sql, ReportConfig.class, globalId);
		if(listBo!=null && !listBo.isEmpty()){
			ReportConfig reportConfig = (ReportConfig) listBo.get(0);
			return reportConfig ;
		}else{
			return null;
		}
	}
	
	protected static String getConfigFromLocalFile(String globalId){
		String CONFIG_ROOT_PATH = ReportConfigManager.class.getClassLoader().getResource("").getPath()
				+ "rodimus_report_config"+File.separator +"%s.json" ;
		String configFilePath = String.format(CONFIG_ROOT_PATH, globalId);
		String ret = FileUtil.getFileText(new File(configFilePath));
		if(StringUtils.isEmpty(ret)){
			throw new RuntimeException(String.format("没有找到配置信息(%s)，请联系管理员。", globalId));
		}
		return ret;
	}
	

}
