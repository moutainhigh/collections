package com.gwssi.common.rodimus.report.core;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.common.rodimus.report.expr.ExprUtil;
import com.gwssi.common.rodimus.report.util.CalendarUtil;
import com.gwssi.common.rodimus.report.util.DaoUtil;
import com.gwssi.common.rodimus.report.util.DateUtil;
import com.gwssi.common.rodimus.report.util.DictUtil;
import com.gwssi.common.rodimus.report.util.NumberUtil;
import com.gwssi.common.rodimus.report.util.StringUtil;

/**
 * 报表查询工具类。
 * 
 * @author liuhailong
 */
public class ReportQueryUtil {
	
	
	/**
	 * 根据配置，从request中获取查询参数。
	 * 
	 * @param config
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParams(JSONObject config,HttpServletRequest request){
		
		Map<String, Object> params=new HashMap<String, Object>();
		
		// 查询参数
		JSONArray jaParamsConfig = config.getJSONArray("params");
		if(jaParamsConfig!=null && jaParamsConfig.size()>0){
			JSONObject joParamConfig ;
			String paramName , paramValue;
			for(int i=0;i<jaParamsConfig.size();++i){
				joParamConfig = jaParamsConfig.getJSONObject(i);
				paramName = joParamConfig.getString("name");
				paramValue = request.getParameter(paramName);
				params.put(paramName, paramValue);
			}
		}
		// 分页参数
		String pageSize = request.getParameter("pageSize");
		params.put("pageSize", pageSize);
		String pageNo = request.getParameter("pageNo");
		params.put("pageNo", pageNo);
		// 排序参数
		String orderBy = request.getParameter("orderBy");
		params.put("orderBy", orderBy);
		
		return params;
	}
	
	
	public static void main(String[] args){
		
		String  str = "StrJoin(\"<a target='_blank' href='../detail/detail.html?code=DC_SX_INFO&id=\",  id ,\"'>\",iname,\"</a>\")";
		 String result = "";
		int lastFirst = str.lastIndexOf(',');
		String str2 = str.substring(0,lastFirst);
		result = str2.substring(str2.lastIndexOf(",")+1,str2.length()).trim();
		System.out.println(lastFirst+"result====="+result);
		
	}
	

	/**
	 * 参数配置模式。
	 */
	protected static final Pattern PARMS_PATTERN = Pattern.compile("(\\{params.*?\\})");
	/**
	 * 获得SQL语句。
	 * 
	 * @param config
	 * @param params
	 * @param sqlParams
	 * @return
	 */
	public static String getSql(JSONObject config,Map<String, Object> params,List<Object> sqlParams){
		
		
		// 0、从配置中获得SQL
		String sql = config.getString("sql");//其中包含 {params}、{params[x,x]}、{params^[x,x]}、{dateParams}、{orderBy}等标记
		JSONArray jaParamsConfig = config.getJSONArray("params");//参数配置
		if(sqlParams==null){
			sqlParams = new ArrayList<Object>();
		}else{
			sqlParams.clear();
		}
		
		// 1、根据当前登录用户所在管辖机关过滤
		String regOrgFilter = getRegOrgFilter(config);
		if(!StringUtils.isEmpty(regOrgFilter)){
			sql = sql.replaceAll("\\{params", regOrgFilter + " \\{params");
		}
				
		// 2、 处理{dateParams}
		String dateParamsModel = "\\{dateParams\\}";
		if(jaParamsConfig==null || jaParamsConfig.isEmpty()){
			sql = sql.replaceAll(dateParamsModel, " ") ;// 全部替换为空格
		}else{
			if(sql.indexOf("{dateParams}")!=-1){
				String dateParamsString = buildDateParams(jaParamsConfig,params);
				sql = sql.replaceAll(dateParamsModel, dateParamsString) ;
			}
		}
		
		// 3、处理{params}、{params[x,x]}、{params^[x,x]}
		if(jaParamsConfig==null || jaParamsConfig.isEmpty()){
			// 全部替换为空格
			Matcher matcher = PARMS_PATTERN.matcher(sql);
			while(matcher.find()){
				sql = matcher.replaceAll(" ");
			}
		}else{
			Matcher matcher = PARMS_PATTERN.matcher(sql);
			String paramPlaceHolder = "",expr="";
			String paramBuildModel = "";//参数构建模式
			String paramsSqlClause = "";
			
			while(matcher.find()){
				paramPlaceHolder = matcher.group(); // 如 ： {paramsblabla}
				if(paramPlaceHolder==null || paramPlaceHolder.length()<8){//至少包含{params}
					continue ;
				}
				expr = paramPlaceHolder.substring(7, paramPlaceHolder.length()-1);
				
				if(StringUtils.isEmpty(expr)){
					// 表达式为空，包含所有参数
					paramBuildModel = "all";
					expr = "";
				}else{
					expr = expr.trim();
					
					if(expr.startsWith("^")){
						// 排除模式
						paramBuildModel = "exclude";
						expr = expr.substring(2,expr.length()-1);
					}else{
						// 包含模式
						paramBuildModel = "include";
						expr = expr.substring(1,expr.length()-1);
					}
					expr.split(",");
				}
				Set<String> paramNameSet = getParamNameSet(jaParamsConfig,paramBuildModel,expr);
				paramsSqlClause = buildParamsSqlClause(jaParamsConfig,paramNameSet,params,sqlParams);
				sql = sql.replace(paramPlaceHolder, paramsSqlClause);
				
			}//end of while(matcher.find())
			
		}// end of if-else
		
		// 4、处理{orderBy}
		String orderBy =  StringUtil.safe2String(params.get("orderBy"));
		if(StringUtils.isEmpty(orderBy)){
			orderBy = config.getString("defaultOrderBy");
		}
		sql = sql.replaceAll("\\{orderBy\\}", orderBy) ;
				
		// 5、返回SQL
		return sql;
	}
	

	/**
	 * 
	 * 
	 * @param jaParamsConfig 	参数配置
	 * @param params 			参数值Map
	 * @param paramBuildModel 	all-所有参数，include-包含模式，exclude-排除模式
	 * @param expr 				逗号分隔的字符串
	 * @param sqlParams			SQL参数列表，用作返回值
	 * @return 
	 */
	private static String buildParamsSqlClause(JSONArray jaParamsConfig,Set<String> paramNameSet ,
			Map<String, Object> params, List<Object> sqlParams) {
		
		if(paramNameSet==null||paramNameSet.isEmpty()){
			return "";
		}
		
		// 查询参数
		StringBuffer sbSqlClause = new StringBuffer();
		sbSqlClause.append(" ");
		
		JSONObject joParamConfig ;
		String fieldName,paramName , op  , fieldType , paramValueString;
		
		Object paramValue = null;
		Date paramValueDateObj = null;
		for(int i=0;i<jaParamsConfig.size();++i){
			joParamConfig = jaParamsConfig.getJSONObject(i);
			
			fieldName = joParamConfig.getString("field");//数据库字段名
			paramName = joParamConfig.getString("name");//数据库字段名，如果fieldName为空，则采用name
			fieldType = joParamConfig.getString("type");//数据类型
			
			if(!paramNameSet.contains(paramName)){
				continue ;
			}

			if(StringUtils.isEmpty(fieldName)){
				fieldName = paramName ;
			}
			
			op = joParamConfig.getString("op");//操作符，如 =、>=、like、in
			if(StringUtils.isEmpty(op)){
				throw new RuntimeException(paramName+"未设置op属性。");
			}
			op = op.trim();
			
			paramValue = params.get(paramName);//需通过name获得值
			
			// 如果参数值不为空，考虑作为查询条件
			if(paramValue==null){
				continue;
			}
			paramValueString = StringUtil.safe2String(paramValue); //处理为字符串
			if(StringUtils.isEmpty(paramValueString)){//不为空字符串，作为查询条件
				continue;
			}
			
			// 如果是日期类型，特殊处理
			if("date".equals(fieldType)){ 
				// field 不包含 to_char ，按日期处理
				if(fieldName.indexOf("to_char")==-1){
					paramValueDateObj = CalendarUtil.parseSqlDate(paramValueString);
					if(paramValueDateObj!=null){
						
						if("<".equals(op) || "<=".equals(op)){
							paramValueDateObj = setDateTo235959(paramValueDateObj);
							op = "<";
						}
						sbSqlClause.append(" and ").append(fieldName).append(" ").append(op).append(" ? ");
						sqlParams.add(paramValueDateObj);
						
					}
					
					continue;
				}
				
			}// end of date
				
			// in 操作，特殊处理
			// FIXME 未考虑逗号分隔的多个值中有空值的情况
			if("in".equals(op)){
				String[] arrParamValues = paramValueString.split(",");
				if(arrParamValues==null || arrParamValues.length==0){
					continue;
				}
				if(arrParamValues.length<=900){
					sbSqlClause.append(" and ").append(fieldName).append(" ").append(op).append(" (");
					for (int j = 0; j < arrParamValues.length; j++) {
						if(j>0){
							sbSqlClause.append(",?");
						}else{
							sbSqlClause.append("?");
						}
						sqlParams.add(arrParamValues[j]);
					}
					sbSqlClause.append(") ");
					continue;
				}else {
					// in 中list超过1000
					sbSqlClause.append(" and (");
					
					// (x in (x,x,xx,x,x,x))  or (x in (x))
					StringBuffer sbSubInOr = new StringBuffer();
					for (int j = 0; j < arrParamValues.length; ) {
						sbSubInOr.append(" ( ").append(fieldName).append(" in ( ");
						for (int j2 = 0; j2 < 900; j2++) {
							if(j2>0){
								sbSubInOr.append(",?");
							}else{
								sbSubInOr.append("?");
							}
							sqlParams.add(arrParamValues[j]);
							j++;
							if(j>=arrParamValues.length){
								break;
							}
						}
						sbSubInOr.append(" ) ) ");
						if(j!=0 && j<arrParamValues.length-1){
							sbSubInOr.append(" or ");
						}
					}
					sbSqlClause.append(sbSubInOr);
					sbSqlClause.append(")");
					continue;
				}
			}//end of in
			
			if("literal".equals(op)){
				sbSqlClause.append(" and ").append(paramValueString).append(" ");
				continue ; 
			}
			
			// 默认处理
			sbSqlClause.append(" and ").append(fieldName).append(" ").append(op).append(" ? ");
			sqlParams.add(paramValue);
			
			
		}// end of for
		
		return sbSqlClause.toString();
	}
	
	

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	

	
	/**
	 * 得到参数名。
	 * 
	 * @param jaParamsConfig
	 * @param paramBuildModel
	 * @param expr
	 * @return
	 */
	private static Set<String> getParamNameSet(JSONArray jaParamsConfig,String paramBuildModel, String expr){
		// 参数名
		Set<String> paramNameSet = new HashSet<String>();
		
		// 定义临时变量
		JSONObject joParamConfig ;
		String paramName ;
		boolean isSkipInSql = true;//是否参数数据查询过滤
		
		for(int i=0;i<jaParamsConfig.size();++i){
			joParamConfig = jaParamsConfig.getJSONObject(i);
			isSkipInSql = joParamConfig.getBooleanValue("isSkipInSql");
			if (isSkipInSql) {
				continue ;
			}
			paramName = joParamConfig.getString("name");//数据库字段名，如果fieldName为空，则采用name
			if("all".equals(paramBuildModel)){
				paramNameSet.add(paramName);
			}
			if("include".equals(paramBuildModel)){
				if(expr.indexOf(paramName)!=-1){
					paramNameSet.add(paramName);
				}
			}
			if("exclude".equals(paramBuildModel)){
				if(expr.indexOf(paramName)==-1){
					paramNameSet.add(paramName);
				}
			}
		}// end of for
		return paramNameSet;
	}
	/**
	 * 得到日期参数。
	 * 
	 * @param sql
	 * @param config
	 * @param params
	 * @param sqlParams
	 * @return sql
	 */
	private static String buildDateParams(JSONArray jaParamsConfig,Map<String, Object> params){
		
		StringBuffer sbDateParamString = new StringBuffer(" ");
		
		JSONObject joParamConfig ;
		String fieldName,paramName , op  , fieldType , paramValueString;
		boolean isSkipInSql = true;//是否参数数据查询过滤
		Object paramValue = null;
		Date paramValueDateObj = null;
		for(int i=0;i<jaParamsConfig.size();++i){
			joParamConfig = jaParamsConfig.getJSONObject(i);
			isSkipInSql = joParamConfig.getBooleanValue("isSkipInSql");
			if (isSkipInSql) {
				continue ;
			}
			
			fieldName = joParamConfig.getString("field");//数据库字段名
			paramName = joParamConfig.getString("name");//数据库字段名，如果fieldName为空，则采用name
			fieldType = joParamConfig.getString("type");//数据类型
			
			if(StringUtils.isEmpty(fieldName)){
				fieldName = paramName ;
			}
			
			op = joParamConfig.getString("op");//操作符，如 =、>=、like、in
			if(StringUtils.isEmpty(op)){
				throw new RuntimeException(paramName+"未设置op属性。");
			}
			op = op.trim();
			
			paramValue = params.get(paramName);//需通过name获得值
			
			// 如果参数值不为空，考虑作为查询条件
			if(paramValue==null){
				continue;
			}
			paramValueString = StringUtil.safe2String(paramValue); //处理为字符串
			if(StringUtils.isEmpty(paramValueString)){//不为空字符串，作为查询条件
				continue;
			}
			
			// 如果是日期类型，特殊处理
			if("date".equals(fieldType)){ 
				// field 不包含 to_char ，按日期处理
				if(fieldName.indexOf("to_char")==-1){
					paramValueDateObj = CalendarUtil.parseSqlDate(paramValueString);
					if(paramValueDateObj!=null){
						String dateStr = DateUtil.format(paramValueDateObj);
						/// sbDateParamString
						if(">".equals(op) || ">=".equals(op)){
							sbDateParamString.append(" and ").append(fieldName).append(" ").append(op).append(" to_date('"+dateStr+"','yyyy-MM-dd') ");
						}
						if("<".equals(op) || "<=".equals(op)){
							dateStr = sdf.format(paramValueDateObj);// dateStr + " 23:59:59";
							sbDateParamString.append(" and ").append(fieldName).append(" < to_date('"+dateStr+"','yyyy-MM-dd hh24:mi:ss') ");
						}
					}
					continue;
				}
			}// end of date
			
		}// end of for
		
		return sbDateParamString.toString();
	}
	
	
	
	private static Date setDateTo235959(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
//		calendar.set(Calendar.HOUR_OF_DAY, 23);
//		calendar.set(Calendar.MINUTE, 59);
//		calendar.set(Calendar.SECOND, 59);
//		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date ret = new Date(calendar.getTime().getTime());
		return ret;
	}


	/**
	 * <h3>根据登录用户所属机关不同，过滤数据</h3>
	 * <p>
	 * 如果是市局用户，可以查询市局和分局的数据，不能查询工商所的数据；
	 * 如果是分局用户，只能查询本分局及下属工商所的数据；
	 * 如果是工商所用户，只能查询本所的数据。
	 * </p>
	 * @param config 报表配置，从报表配置中以regOrgField为key，取得 SQL中使用的 审核机关 字段名。
	 * 			如果报表配置中未配置regOrgField，则返回空字符串，表示不进行过滤。
	 * @return SQL查询语句片段，和其他查询参数一起，过滤查询数据，形如：“ and substr(reg_org,0,6) = '110108'”
	 */
	private static String getRegOrgFilter(JSONObject config){
		// 0. 参数校验
		// 得到数据库字段
		/*String field = config.getString("regOrgField");
		if(StringUtils.isEmpty(field)){
			// 报表配置中未配置 regOrgField，则不执行过滤
			return "";
		}
		// 返回的SQL片段
		StringBuffer sb = new StringBuffer();
		// 当前登录用户所在工商机关编号，形如：形如110000000、110108000、110108012、110108。
		String currentUserOrgCode = CurrentUserUtil.getOrgCode();
		if(StringUtils.isBlank(currentUserOrgCode)){
			throw new RuntimeException("当前登录用户不是工商人员，没有权限执行当前操作。");
		}
		// 判断regOrg是否合法。
		if(currentUserOrgCode.length()<6){
			throw new RuntimeException("当前登录用户不是工商人员，没有权限执行当前操作。");
		}
		
		// 1. 根据所在机关不同情况做不同处理
		
		// 1.1. 市局用户：regOrg为 110000000（或110000）
		String subRegOrg = currentUserOrgCode.substring(0, 6);//取前6位
		if("110000".equals(subRegOrg)){
			//如果是市局的,则可以查询市局及分局的数据，相应数据中regOrg的值应该为6位，或后三位为000			
			//sb.append(" and ( substr(").append(field).append(",7,9)").append("='000' or length(").append(field).append(")=6 ) ");
			//return sb.toString();
			return "";//市局统计所有
		}

		// 1.2. 分局用户：regOrg只有6位 或 后3位为0
		subRegOrg = currentUserOrgCode.substring(6, 9);//取后3位
		if(currentUserOrgCode.length()==6 || "000".equals(subRegOrg)){
			subRegOrg = currentUserOrgCode.substring(0, 6);//取前6位
			//如果是分局的，则可以查询当前分局及其工商所的，判断方法，相应数据中regOrg前六位与当前登录用户组织机构代码的前六位相同
			sb.append(" and substr(").append(field).append(",0,6) = '").append(subRegOrg).append("' ");
			return sb.toString();
		}
		
		// 1.2. 工商所用户：regOrg为9位，且后三位不为000
		subRegOrg = currentUserOrgCode.substring(6, 9);//取后3位
		if(currentUserOrgCode.length()==9 && !"000".equals(subRegOrg)){
			//如果是工商所的，则只可以查询当前工商所的
			sb.append(" and ").append(field).append(" = '").append(currentUserOrgCode).append("' ");
			return sb.toString();
		}
		
		// 其他情况，regOrg无法归类，说明不是工商机关用户
		throw new RuntimeException("当前登录用户不是工商人员，没有权限执行当前操作。");*/
		return "";
	}

	
	
	
	
	/**
	 * 对查询结果进行处理。
	 * 
	 * @param config
	 * @param rows
	 * @return
	 */
	public static List<Map<String, Object>> onQueryEnd(JSONObject config,List<Map<String, Object>> rows) {
		// 数据钻取
		rows = onQueryEnd_dig(config,rows);
		// 转换码表
		onQueryEnd_dict(config,rows);
		// 格式化
		onQueryEnd_expr(config,rows);
		// 格式化
		onQueryEnd_format(config,rows);
		
		return rows;
	}
	
	


	
	
	/**
	 * 计算表达式。
	 * @param config
	 * @param rows
	 */
	private static void onQueryEnd_expr(JSONObject config,
			List<Map<String, Object>> rows) {
		if(rows==null || rows.size()<1){
			return ;
		}
		// 找到需要格式化的
		Map<String, String> columnMap = getExprColumnList(config);
		if(columnMap==null || columnMap.isEmpty()){
			return ;
		}
		
		// 逐行处理
		String fieldName ,expr;
		Object exprResult = null;
		for (int i = 0; i < rows.size(); i++) {
			Map<String, Object> rowMap = rows.get(i);
			
			for(Map.Entry<String, String> entry : columnMap.entrySet()){
				fieldName = entry.getKey();
				expr = entry.getValue();
				exprResult = "";
				if(!StringUtils.isEmpty(expr)){
					expr = getSubStr(expr);
					try{
						exprResult = ExprUtil.process(expr, rowMap);
					}catch(Throwable e){
						exprResult = "[expr err:"+e.getMessage()+"]";
					}
					
					if(exprResult==null || "NaN".equals(exprResult)){
						exprResult = "";
					}
					rowMap.put(fieldName, exprResult);
				}
			}
		}
	}

	
	//当有超链接时，截取字符串   如：StrJoin(\"<a target='_blank' href='../detail/detail.html?code=DC_SX_INFO&id=\",id,\"'>\",iname,\"</a>\")
	private static String getSubStr(String expr) {
		String result = "";
		int lastFirst = expr.lastIndexOf(',');
		String str2 = expr.substring(0,lastFirst);
		result = str2.substring(str2.lastIndexOf(",")+1,str2.length()).trim();
		return result;
	}

	/**
	 * 码表转换。
	 * 
	 * @param config
	 * @param rows
	 */
	private static void onQueryEnd_dict(JSONObject config,List<Map<String, Object>> rows){
		// 码表转换
		JSONArray jaDict = config.getJSONArray("dict");
		JSONObject dictItem ;
		String fieldName , typeId ,fieldValue,dictedValue; 
		if(jaDict!=null && jaDict.size()>0 && rows!=null && rows.size()>0){
			for (int i = 0; i < rows.size(); i++) {
				Map<String, Object> rowMap = rows.get(i);
				for (int j = 0; j < jaDict.size(); j++) {
					dictItem = jaDict.getJSONObject(j);
					fieldName = dictItem.getString("name");
					typeId = dictItem.getString("dict");
					if(!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(typeId)){
						fieldValue = StringUtil.safe2String(rowMap.get(fieldName));
						if(!StringUtils.isEmpty(fieldValue)){
							dictedValue = DictUtil.trans(typeId, fieldValue);
							if(!StringUtils.isEmpty(dictedValue)){
								rowMap.put(fieldName, dictedValue);
							}
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 格式化。
	 * 
	 * @param config
	 * @param rows
	 */
	private static void onQueryEnd_format(JSONObject config,List<Map<String, Object>> rows){
		if(rows==null || rows.size()<1){
			return ;
		}
		// 找到需要格式化的
		Map<String, String> formatColumnMap = getFormatColumnList(config);
		if(formatColumnMap==null || formatColumnMap.isEmpty()){
			return ;
		}
		
		// 逐行处理
		Object object;
		String fieldName ,formatString, formatedValueString;
		for (int i = 0; i < rows.size(); i++) {// 循环处理每行
			Map<String, Object> rowMap = rows.get(i);

			for(Map.Entry<String, String> entry : formatColumnMap.entrySet()){ //循环处理每个字段
				fieldName = entry.getKey();// 字段名
				formatString = entry.getValue();//格式化定义
				object = rowMap.get(fieldName);//值
				if(object==null){
					rowMap.put(fieldName, "　");
					continue;
				}
				if(formatString.indexOf("%")>-1){//按数字进行格式化
					formatedValueString = "";
					try{
						formatedValueString = String.format(formatString, object);
						rowMap.put(fieldName, formatedValueString);
					}catch(Throwable e){
						formatedValueString = "";
						//e.printStackTrace();
					}
				}
			}

		}
		
	}
	
	/**
	 * 格式化：得到需要格式化的列名。
	 * 
	 * @param config
	 * @return
	 */
	private static Map<String, String> getExprColumnList(JSONObject config){
		Map<String, String> columnMap = new HashMap<String, String>();
		if(config==null){
			return columnMap ;
		}
		JSONArray columnConfigs = config.getJSONArray("headerDataConfig");
		if(columnConfigs==null){
			return columnMap;
		}
		
		JSONObject columnConfig = null;
		String name = null;
		String expr = null;
		
		
		for (int i = 0; i < columnConfigs.size(); i++) {
			columnConfig = columnConfigs.getJSONObject(i);
			name = columnConfig.getString("name");
			expr = columnConfig.getString("expr");
			if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(expr)){
				columnMap.put(name, expr);
			}
		}
		
		return columnMap;
	}
	/**
	 * 格式化：得到需要格式化的列名。
	 * 
	 * @param config
	 * @return
	 */
	private static Map<String, String> getFormatColumnList(JSONObject config){
		Map<String, String> columnMap = new HashMap<String, String>();
		if(config==null){
			return columnMap ;
		}
		JSONArray columnConfigs = config.getJSONArray("headerDataConfig");
		if(columnConfigs==null){
			return columnMap;
		}
		
		JSONObject columnConfig = null;
		String name = null;
		String format = null;
		
		
		for (int i = 0; i < columnConfigs.size(); i++) {
			columnConfig = columnConfigs.getJSONObject(i);
			name = columnConfig.getString("name");
			format = columnConfig.getString("format");
			if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(format)){
				columnMap.put(name, format);
			}
		}
		
		return columnMap;
	}
	
	
	
	
	
	
	
	/**
	 * 数据钻取 入口。
	 * 
	 * @param config
	 * @param rows
	 */
	private static List<Map<String, Object>> onQueryEnd_dig(JSONObject config,List<Map<String, Object>> rows){
		// 0. 参数校验
		if(rows==null || rows.isEmpty()){
			return rows;
		}
		JSONObject joDig = config.getJSONObject("dig");
		if(joDig==null){
			return rows;
		}
		// 0.1. 得到进行挖掘的字段名 和 码表key
		String name = joDig.getString("name");//进行挖掘的字段名
		String var  = joDig.getString("var");//进行挖掘的码表key
		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(var)){
			return rows;
		}
		// 0.2. 得到需要汇总的列
		List<Map<String, String>> statColumnList = getStatColumnList(config);
		if(statColumnList==null || statColumnList.isEmpty()){
			return rows;
		}
		// 1. 查询码表
		StringBuffer sb = new StringBuffer();
//		sb.append("select d.*,level from (");
//		sb.append("select v.code_value as v,nvl(p.code_value,'root') as p,v.code_name as t from sysmgr_cvalue v ");
//		sb.append("left join sysmgr_cvalue p on v.parent_id=p.code_id ");
//		sb.append("where v.type_id_fk =? ");
//		sb.append(" ) d start with d.p='root' connect by prior d.v=d.p order by d.v ");
//		
		sb.append("select d.*, level ");
		sb.append("  from (select v.code_value as v, nvl(p.code_value, 'root') as p,v.code_name as t, ");
		sb.append("               v.code_id,nvl(v.parent_id,'root') as parent_id ");
		sb.append("          from sysmgr_cvalue v ");
		sb.append("          left join sysmgr_cvalue p on v.parent_id = p.code_id ");
		sb.append("         where v.type_id_fk = ? ");
		sb.append("  ) d ");
		sb.append(" start with d.p = 'root'  ");
		sb.append("connect by prior d.code_id = d.parent_id  ");
		sb.append(" order by d.code_id  ");
		String dataSource = config.getString("dataSource");
		List<Map<String,Object>> varList = DaoUtil.getInstance(dataSource).queryForList(sb.toString(),var);
		if(varList==null || varList.isEmpty()){
			return rows;
		}
		
		// 2. 将数据处理为Map
		Map<String, Map<String,Object>> oriRowsMap = new HashMap<String, Map<String,Object>>();
		Map<String,Object> row = null;
		String key = null;
		for (int i = 0; i < rows.size(); i++) {
			row = rows.get(i);
			key = StringUtil.safe2String(row.get(name));
			key = key.trim();
			row.put(name, key);
			oriRowsMap.put(key, row);
		}
		// 3. 依照 varList 的顺序和字典项，合并 rows
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String digValue = null;
		String digParent = null;
		String digText = null;
		String digLevel = null;
		Map<String,Object> varRow = null;
		Map<String,Object> dataRow = null;
		Map<String, Map<String,Object>> rowsMap = new HashMap<String, Map<String,Object>>();
		
		for (int i = 0; i < varList.size(); i++) {
			// 获取挖掘变量
			varRow = varList.get(i);
			digValue = StringUtil.safe2String(varRow.get("v"));
			digParent = StringUtil.safe2String(varRow.get("p"));
			digText = StringUtil.safe2String(varRow.get("t"));
			digLevel = StringUtil.safe2String(varRow.get("level"));
			// 找到对应数据
			dataRow = oriRowsMap.get(digValue);
			oriRowsMap.remove(digValue);//读取后,从map中去掉
			if(dataRow==null){
				dataRow = new HashMap<String, Object>();
			}
			// 构造新的数据行
			dataRow.put("digValue", digValue);
			dataRow.put("digParent", digParent);
			dataRow.put("digText", digText);
			dataRow.put("digLevel", digLevel);
			dataRow.put(name, digText);
			// 加到返回List中
			list.add(dataRow);
			rowsMap.put(digValue, dataRow);
		}
		
		if(!oriRowsMap.isEmpty()){
			Map<String,Object> otherMap = new HashMap<String, Object>();
			otherMap.put("v", "other");
			otherMap.put("p", "root");
			otherMap.put("t", "其他");
			varList.add(otherMap);
			dataRow = new HashMap<String, Object>();
			dataRow.put("digValue", "other");
			dataRow.put("digParent", "root");
			dataRow.put("digText", "其他");
			dataRow.put("digLevel", "1");
			dataRow.put(name, "其他");
			list.add(dataRow);
			rowsMap.put("other", dataRow);
			
			String otherDictKey,otherDictText ;
			Map<String, Object> otherRowData ;
			
			for (Entry<String, Map<String, Object>> entry : oriRowsMap.entrySet()) {
				otherDictKey = entry.getKey();
				otherRowData = entry.getValue();
				
				if(otherRowData==null){
					otherRowData = new HashMap<String, Object>();
				}
				// 构造新的数据行
				if(StringUtils.isEmpty(otherDictKey)){
					otherDictKey = "nil";
					otherDictText = "<空>";
				}else{
					otherDictText = otherDictKey;
				}
				otherRowData.put("digValue", otherDictKey);
				otherRowData.put("digParent", "other");
				otherRowData.put("digText", otherDictText);
				otherRowData.put("digLevel", "2");
				otherRowData.put(name, otherDictKey);
				list.add(otherRowData);
				rowsMap.put(otherDictKey, otherRowData);
			}
		}

		
		Map<String,Object> totalMap = new HashMap<String, Object>();
		totalMap.put("v", "root");
		totalMap.put("p", "");
		totalMap.put("t", "总计");
		varList.add(totalMap);
		dataRow = new HashMap<String, Object>();
		dataRow.put("digValue", "root");
		dataRow.put("digParent", "");
		dataRow.put("digText", "总计");
		dataRow.put("digLevel", "1");
		dataRow.put(name, "总计");
		list.add(dataRow);
		rowsMap.put("root", dataRow);
		
		
		// 4. 自下向上汇总
		Map<String,Object> currentRow = null;
		Map<String,Object> parentRow = null;
		for (int i = list.size()-1; i>=0 ; --i) {
			// 每处理一行，将当前行的统计数值加到父亲节点
			currentRow = list.get(i); // current row
			digValue = StringUtil.safe2String(currentRow.get("digValue"));
			digParent = StringUtil.safe2String(currentRow.get("digParent"));
			parentRow = rowsMap.get(digParent); // parent row
			if(parentRow==null){
				parentRow = rowsMap.get("root");
			}
			addCurrentToParent(currentRow,parentRow,statColumnList);
		}
		// 5. 清除空行
		String fieldName = null;
		BigDecimal fieldValue = null;
		BigDecimal rowSum = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		int rowIdx = 1;
		for (int i = 0; i < list.size(); i++) {
			dataRow = list.get(i);
			rowSum = BigDecimal.ZERO;
			Iterator<Map<String, String>> it = statColumnList.iterator();
			while (it.hasNext()) {
				Map<String, String> itemMap = it.next();
				fieldName = itemMap.get("name");
				fieldValue = NumberUtil.parseBigDecimal(dataRow.get(fieldName));
				rowSum = rowSum.add(fieldValue);
			}
			if(BigDecimal.ZERO.compareTo(rowSum)<0){
				dataRow.put("rowIdx", rowIdx);
				++rowIdx;
				ret.add(dataRow);
			}
		}
		return ret;
	}
	
	/**
	 * 数据钻取：得到需要汇总的列名。
	 * 
	 * @param config
	 * @return
	 */
	private static List<Map<String, String>> getStatColumnList(JSONObject config){
		List<Map<String, String>> statColumnList = new ArrayList<Map<String, String>>();
		if(config==null){
			return statColumnList ;
		}
		JSONArray columnConfigs = config.getJSONArray("headerDataConfig");
		if(columnConfigs==null){
			return statColumnList;
		}
		
		JSONObject columnConfig = null;
		String stat = null;
		String name = null;
		
		for (int i = 0; i < columnConfigs.size(); i++) {
			columnConfig = columnConfigs.getJSONObject(i);
			name = columnConfig.getString("name");
			stat = columnConfig.getString("stat");
			if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(stat)){
				Map<String, String> itemMap = new HashMap<String, String>();
				itemMap.put("name", name);
				itemMap.put("stat", stat);
				statColumnList.add(itemMap);
			}
		}
		
		return statColumnList;
	}
	
	/**
	 * 数据钻取 ： 数据汇总。
	 * 
	 * @param currentRow
	 * @param parentRow
	 * @param statColumnList
	 */
	private static void addCurrentToParent(Map<String, Object> currentRow,
			Map<String, Object> parentRow, List<Map<String, String>> statColumnList) {
		if(currentRow==null || currentRow.isEmpty()){
			return ;
		}
		if(parentRow==null){
			return ;
		}
		if(statColumnList==null||statColumnList.isEmpty()){
			return ;
		}
		
		// 逐个字段处理
		Iterator<Map<String, String>> it = statColumnList.iterator();
		String fieldName = null;
		String stat = null;
		BigDecimal current = BigDecimal.ZERO;
		BigDecimal parent = BigDecimal.ZERO;
		String digText = "";
		while (it.hasNext()) {
			Map<String, String> itemMap = it.next();
			fieldName = itemMap.get("name");
			stat = itemMap.get("stat");
			current = NumberUtil.parseBigDecimal(currentRow.get(fieldName));
			parent = NumberUtil.parseBigDecimal(parentRow.get(fieldName));
			if(current!=null ){
				if(stat.contains("rate") && parentRow!=null){
					digText = (String)parentRow.get("digText");
					if("总计".equals(digText)){
						parentRow.put(fieldName, new BigDecimal(100));
						continue;
					}
				}
				if(!BigDecimal.ZERO.equals(current)){
					parent = current.add(parent);
					parentRow.put(fieldName, parent);
				}
			}
		}
	}
}
