package com.gwssi.common.rodimus.detail.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.common.rodimus.detail.config.DetailConfigManager;
import com.gwssi.common.rodimus.detail.util.RequestUtil;
import com.gwssi.common.rodimus.report.dao.BaseDao;
import com.gwssi.common.rodimus.report.util.DaoUtil;


public class DetailDataManager {
	
	public static JSONObject getData(HttpServletRequest request){
		String configCode = request.getParameter("code");
		Map<String, Object> content = RequestUtil.getRequestParamsAsMap(request);
		JSONObject ret = getData(configCode,content);
		return ret;
	}
	
	/**
	 * 查询数据。
	 * 
	 * @param configCode
	 * @param content
	 * @return
	 */
	public static JSONObject getData(String configCode, Map<String,Object> content){
		// 0. 参数检查
		if(StringUtils.isBlank(configCode)){
			throw new RuntimeException("configCode 不能为空。");
		}
		if(content==null || content.isEmpty()){
			throw new RuntimeException("参数不能为空。");
		}
		
		JSONObject ret = new JSONObject();
		ret.put("code", configCode);
		// 1. 加载配置
		JSONObject config = DetailConfigManager.getConfig(configCode);
		if(config==null){
			throw new RuntimeException("未找到 code 为 " + configCode + " 的配置。");
		}
		ret.put("label", config.getString("title"));
		ret.put("nav", config.getJSONObject("nav"));
		
		// 2. 逐个处理 subform
		JSONArray retSubform = new JSONArray();
		ret.put("subform", retSubform);
		
		JSONArray jaSubforms = config.getJSONArray("subform");
		if(jaSubforms==null || jaSubforms.isEmpty()){
			return ret;
		}
		for(int i = 0 ; i< jaSubforms.size() ; ++ i){
			JSONObject subformConfig  = jaSubforms.getJSONObject(i);
			JSONObject subformData = getSubformData(subformConfig,content);
			retSubform.add(subformData);
		}
		
		return ret;
	}
	/**
	 * 获得subform数据。
	 *    
	 * @param subformConfig
	 * @param content
	 * @return
	 */
	private static JSONObject getSubformData(JSONObject config, Map<String, Object> content) {
		// 0. 参数检查
		if(config==null || config.isEmpty()){
			throw new RuntimeException("Subform config 不能为空。");
		}
		if(content==null || content.isEmpty()){
			throw new RuntimeException("参数不能为空。");
		}
		String type = config.getString("type");
		if(StringUtils.isBlank(type)){
			throw new RuntimeException("subform必须有type属性。");
		}
		if(!"form".equalsIgnoreCase(type) && !"list".equalsIgnoreCase(type) ){
			throw new RuntimeException("subform的type属性取值必须是 form 或list。");
		}
		JSONObject dbConfig = config.getJSONObject("db");
		if(dbConfig==null){
			throw new RuntimeException("subform必须配置db属性。");
		}
		String sql = dbConfig.getString("sql");
		if(StringUtils.isBlank(sql)){
			throw new RuntimeException("subform.db必须配置sql属性。");
		}
		JSONArray jaColumns = config.getJSONArray("column");
		if(jaColumns==null || jaColumns.isEmpty()){
			throw new RuntimeException("subform必须配置column属性。");
		}
		// 1、获取数据源
		String datasource = dbConfig.getString("datasource");
		BaseDao dao = null;
		if(StringUtils.isBlank(datasource)){
			dao = DaoUtil.getDefaultInstance();
		}else{
			dao = DaoUtil.getInstance(datasource);
		}
		if(dao==null){
			throw new RuntimeException("未获取数据库连接。");
		}
		// 2、 获取查询参数
		List<Object> sqlParams = getSqlParams(dbConfig,content);
		
		// 3、 执行查询并返回结果。
		JSONObject ret = new JSONObject();
		ret.put("type", type);
		ret.put("label", config.getString("title"));
		ret.put("code", config.getString("code"));
		
		if("form".equals(type)){
			Map<String,Object> data = dao.queryForRow(sql, sqlParams);
			List<Map<String,Object>> formData = translate2FormData(data,jaColumns);
			ret.put("data", formData);
			return ret;
		}
		
		if("list".equals(type)){
			// 表头
			JSONArray jaHeader = new JSONArray();
			for(int i=0;i<jaColumns.size();++i){
				JSONObject joColumnConfig = jaColumns.getJSONObject(i);
				JSONObject columnHeader = new JSONObject();
				columnHeader.put("code", joColumnConfig.getString("code"));
				columnHeader.put("label", joColumnConfig.getString("label"));
				columnHeader.put("width", joColumnConfig.getString("width"));
				jaHeader.add(columnHeader);
			}
			ret.put("header", jaHeader);
			// 数据
			List<Map<String,Object>> data = dao.queryForList(sql, sqlParams);
			List<List<Map<String,Object>>> listData = new ArrayList<List<Map<String,Object>>>();
			for(Map<String,Object> row : data){
				List<Map<String,Object>> formData = translate2FormData(row,jaColumns);
				listData.add(formData);
			}
			ret.put("data", listData);
			return ret;
		}
		throw new RuntimeException("不支持的 type 类型：" + type);
	}
	/**
	 * 将每个属性转成一条数据。
	 * @param data
	 * @param jaColumns 
	 * @return
	 */
	private static List<Map<String, Object>> translate2FormData(Map<String, Object> data, JSONArray jaColumns) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		if(data==null || data.isEmpty()){
			return ret;
		}
		
		for(int i=0;i<jaColumns.size();++i){
			JSONObject joColumnConfig = jaColumns.getJSONObject(i);
			String code = joColumnConfig.getString("code");
			if(StringUtils.isBlank(code)){
				throw new RuntimeException("column必须配置code属性。");
			}
			if(code.indexOf('_')>-1){
				code = underlineToCamel(code);
			}
			Object value = data.get(code);
			
			Map<String, Object> row = new LinkedHashMap<String,Object>();
			row.put("idx", i+1);
			row.put("code", code);
			row.put("label", joColumnConfig.getString("label"));
			row.put("text", joColumnConfig.getString("text"));
			row.put("href", joColumnConfig.getString("href"));
			row.put("align", joColumnConfig.getString("align"));//仅用于list
			//特殊条件,格式为条件=参数,如pid=id,自带分隔符,如:?pid,&pid
			String more =joColumnConfig.getString("more");
			if(StringUtils.isNotBlank(more)){
				String regex = "(.*?)=(.*?);";
				Pattern pattern =Pattern.compile(regex);
				Matcher matcher =pattern.matcher(more);
				StringBuilder sb = new StringBuilder();
				while(matcher.find()){
					String condition  =matcher.group(1);
					String param =matcher.group(2);
					sb.append(condition).append("=").append(data.get(param));
					
				}
				row.put("more", sb.toString());//仅用于list
			}
			row.put("lengthSize", joColumnConfig.getString("lengthSize"));//仅用于form
			String dateFormat = joColumnConfig.getString("dateFormat");
			if(value !=null && StringUtils.isNotBlank(dateFormat)){
				DateFormat df = new SimpleDateFormat(dateFormat);
				Calendar cal = (Calendar)value;
				value = df.format(cal.getTime());
			}
			if(value==null){
				value = "";
			}
			row.put("value", value);
			ret.add(row);
		}
		
		return ret;
	}
	/**
     * 下划线式字符串转为驼峰式
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c=='_'){
               if (++i<len){
                   sb.append(Character.toUpperCase(param.charAt(i)));
               }
            }else{
                sb.append(Character.toLowerCase(c));   
            }
        }
        return sb.toString();
    }
	/**
	 * 获取SQL查询参数。
	 * 
	 * @param dbConfig
	 * @param content
	 * @return
	 */
	private static List<Object> getSqlParams(JSONObject config, Map<String, Object> content) {
		// 0、 参数检查
		if(config==null || config.isEmpty()){
			throw new RuntimeException("db config 不能为空。");
		}
		if(content==null || content.isEmpty()){
			throw new RuntimeException("参数不能为空。");
		}
		
		JSONArray jaParamsConfig = config.getJSONArray("params");
		if(jaParamsConfig==null || jaParamsConfig.isEmpty()){
			throw new RuntimeException("subform.db的params属性不能为空。");
		}
		// 2、 根据参数名到上下文中取值
		List<Object> ret = new ArrayList<Object>();
		for(int i=0; i<jaParamsConfig.size();++i){
			String paramName = jaParamsConfig.getString(i);
			Object paramValue = content.get(paramName);
			ret.add(paramValue);
		}
		// 3、 返回
		return ret;
	}
}
