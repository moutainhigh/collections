package com.gwssi.ebaic.torch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.torch.config.TorchConstant;
import com.gwssi.torch.config_core.TorchConfig;
import com.gwssi.torch.core.CommonDefine;
import com.gwssi.torch.dao.IBaseCommonDao;
import com.gwssi.torch.dao.TorchDaoManager;
import com.gwssi.torch.db.jdbc.StoredProcParam;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.db.ResultColumns;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.fun.FunctionConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.util.ParameterUtils;
import com.gwssi.torch.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;

/**
 * 后台高级配置
 * 
 * @author liuxiangqian
 *
 */
public class HighLevelTorchConfig extends TorchConfig {
	
	protected static Set<String> widgetSet = new HashSet<String>(Arrays.asList("query","insert","update","delete"));
	
	private static final Logger logger = Logger.getLogger(TorchConfig.class);
	
	
	/**
	 * 插入SYS_FUNCTION_CONFIG
	 * 
	 * @param params
	 * @param result
	 */
	public void insertFunConfig(Map<String, String> params,Map<String, Object> result){
		String funSql = " insert into sys_function_config values (?,?,?, ?,?,?, ?,?,?) ";
		FunctionConfigBo funConfigBo = ParameterUtils.mapString2Bo(FunctionConfigBo.class, params);
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(UUIDUtil.getUUID());//FUNCTION_ID
		paramList.add(funConfigBo.getTreeId());//TREE_ID
		paramList.add(funConfigBo.getFunctionName());//FUNCTION_NAME
		paramList.add(funConfigBo.getFunctionDesc());//FUNCTION_DESC
		paramList.add(funConfigBo.getTreePath());//TREE_PATH
		paramList.add(funConfigBo.getMasterPath());//MASTER_PATH
		paramList.add(funConfigBo.getPagefilePath());//PAGEFILE_PATH
		paramList.add(funConfigBo.getPagefileName());//PAGEFILE_NAME
		paramList.add(funConfigBo.getFileSuffix());//FILE_SUFFIX
		
		//执行insert
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(funSql, paramList.toArray());
	}
	
	/**
	 * 更新SYS_FUNCTION_CONFIG
	 * 
	 * @param params
	 * @param result
	 */
	public void updateFunConfig(Map<String, String> params,Map<String, Object> result){
		String funSql = " update sys_function_config f set f.tree_id=?,f.function_name=?,f.function_desc=?,f.tree_path=?,f.master_path=?, f.pagefile_path=?,f.pagefile_name=?,f.file_suffix=? where f.function_id=? ";
		FunctionConfigBo funConfigBo = ParameterUtils.mapString2Bo(FunctionConfigBo.class, params);
		if(StringUtils.isBlank(funConfigBo.getFunctionId())){
			throw new RuntimeException(" function_id can not be null. ");
		}
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(funConfigBo.getTreeId());//TREE_ID
		paramList.add(funConfigBo.getFunctionName());//FUNCTION_NAME
		paramList.add(funConfigBo.getFunctionDesc());//FUNCTION_DESC
		paramList.add(funConfigBo.getTreePath());//TREE_PATH
		paramList.add(funConfigBo.getMasterPath());//MASTER_PATH
		paramList.add(funConfigBo.getPagefilePath());//PAGEFILE_PATH
		paramList.add(funConfigBo.getPagefileName());//PAGEFILE_NAME
		paramList.add(funConfigBo.getFileSuffix());//FILE_SUFFIX
		paramList.add(funConfigBo.getFunctionId());//FUNCTION_ID
		//执行update
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(funSql, paramList.toArray());
	}
	
	/**
	 * 获取sys_query_config/sys_edit_config表信息
	 * 
	 * @param params
	 * @param result
	 */
	public void queryConfig(Map<String, String> params,Map<String, Object> result){
		String widget = StringUtil.getMapStr(params, "editType");
		if(!widgetSet.contains(widget)){
			throw new RuntimeException("The value of parameter "+TorchConstant.WEB_CONFIG_WIDGET+" MUST be 'query'、'insert'、'update' or 'delete'.");
		}
		String configId = StringUtil.getMapStr(params, "configId");
		if(StringUtils.isBlank(configId)){
			throw new RuntimeException("configId can not null null.");
		}
		String querySql = "";
		PageBo retBo = null;
		IBaseCommonDao dao = TorchDaoManager.getDao();
		if("update".equals(widget)||"insert".equals(widget)
				||"delete".equals(widget)){
			querySql = " select * from sys_edit_config c where c.config_id=? ";
			retBo=(PageBo) dao.list(querySql, configId);
			result.put("configInfo", retBo);
		}
		if("query".equals(widget)){
			querySql = " select * from sys_query_config c where c.config_id=? ";
			retBo=(PageBo) dao.list(querySql, configId);
			result.put("configInfo", retBo);
		}
	}
	/**
	 * 插入一条新的配置信息
	 * 
	 * @param params
	 * @param result
	 */
	public void insertConfig(Map<String, String> params,Map<String, Object> result){
		String widget = StringUtil.getMapStr(params, "editType");
		if(!widgetSet.contains(widget)){
			throw new RuntimeException("The value of parameter "+TorchConstant.WEB_CONFIG_WIDGET+" MUST be 'query'、'insert'、'update' or 'delete'.");
		}
		String editSql = "insert into sys_edit_config values(?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?)";
		String querySql = " insert into sys_query_config values(?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?) ";
		if("update".equals(widget)||"insert".equals(widget)
				||"delete".equals(widget)){
			EditConfigBo editConfigBo = ParameterUtils.mapString2Bo(EditConfigBo.class, params);
			List<Object> paramList = new ArrayList<Object>();
			String configId = UUIDUtil.getUUID();
			paramList.add(configId);//CONFIG_ID
			paramList.add(editConfigBo.getFunctionId());//FUNCTION_ID
			paramList.add(editConfigBo.getConfigIndex());//CONFIG_INDEX
			paramList.add(editConfigBo.getConfigTitle());//CONFIG_TITLE
			paramList.add(editConfigBo.getConfigName());//CONFIG_NAME
			paramList.add(editConfigBo.getConfigDesc());//CONFIG_DESC
			paramList.add(editConfigBo.getConfigSql());//CONFIG_SQL
			paramList.add(editConfigBo.getTplPath());//TPL_PATH
			paramList.add(editConfigBo.getAdapterType());//ADAPTER_TYPE
			paramList.add(editConfigBo.getTableName());//TABLE_NAME
			paramList.add(editConfigBo.getPrimaryKey());//PRIMARY_KEY
			paramList.add(editConfigBo.getBeforeExecute());//BEFORE_EXECUTE
			paramList.add(editConfigBo.getAfterExecute());//AFTER_EXECUTE
			paramList.add(editConfigBo.getEditType());//EDIT_TYPE
			paramList.add(editConfigBo.getPrimaryKeyGenerateMode());//PRMARY_KEY_GENERATE_MODE
			paramList.add(editConfigBo.getDataSource());//DATA_SOURCE
			//执行insert
			IBaseCommonDao dao=TorchDaoManager.getDao();
			dao.execute(editSql, paramList.toArray());
			//构造编辑列信息
//			params.put("configId", configId);
//			getConfigInfoBySql(params,result);
		}
		if("query".equals(widget)){
			QueryConfigBo queryConfigBo = ParameterUtils.mapString2Bo(QueryConfigBo.class, params);
			List<Object> paramList = new ArrayList<Object>();
			String configId = UUIDUtil.getUUID();
			paramList.add(configId);//CONFIG_ID
			paramList.add(queryConfigBo.getFunctionId());//FUNCTION_ID
			paramList.add(queryConfigBo.getConfigIndex());//CONFIG_INDEX
			paramList.add(queryConfigBo.getConfigTitle());//CONFIG_TITLE
			paramList.add(queryConfigBo.getConfigName());//CONFIG_NAME
			paramList.add(queryConfigBo.getConfigDesc());//CONFIG_DESC
			paramList.add(queryConfigBo.getConfigSql());//CONFIG_SQL
			paramList.add(queryConfigBo.getTplPath());//TPL_PATH
			paramList.add(queryConfigBo.getAdapterType());//ADAPTER_TYPE
			paramList.add(queryConfigBo.isQueryForPage());//IS_PAGING
			paramList.add("1");//IS_QUERY_ON_LOADING
			paramList.add(queryConfigBo.getQueryType());//QUERY_TYPE
			paramList.add(queryConfigBo.getBeforeExecute());//BEFORE_EXECUTE
			paramList.add(queryConfigBo.getAfterExecute());//AFTER_EXECUTE
			paramList.add(queryConfigBo.getDataSource());//DATA_SOURCE
			//执行insert
			IBaseCommonDao dao=TorchDaoManager.getDao();
			dao.execute(querySql, paramList.toArray());
			//构造查询列信息
			params.put("configId", configId);
			getConfigInfoBySql(params,result);
		}
		
	}
	
	
	/**
	 * 根据sql生成配置信息
	 */
	public void getConfigInfoBySql(Map<String, String> params,Map<String, Object> result){
		String widget = params.get("editType");
		if(!widgetSet.contains(widget)){
			throw new RuntimeException("The value of parameter "+TorchConstant.WEB_CONFIG_WIDGET+" MUST be 'query'、'insert'、'update' or 'delete'.");
		}
		if("query".equals(widget)){
			getQueryConfigBySql(params,result);
		}
		if("update".equals(widget)){
			createEditColumnBySql(params,result);
		}
		if("insert".equals(widget)){
			createEditColumnBySql(params,result);
		}
		if("delete".equals(widget)){
			
		}
	}
	
	/**
	 * 根据插入的sql生成配置项
	 * 
	 * @param params
	 * @param result
	 */
	/*private void getInsertConfigBySql(Map<String, String> params,Map<String, Object>result){
		String sql = params.get("configSql");
		//表名称
		String tableName = params.get("tableName");
		String configId = params.get("configId"); 
		//插入列
		List<String> columnNameList = new ArrayList<String>();
		
		Matcher matcher = Pattern.compile("\\{.*?\\}",Pattern.DOTALL).matcher(sql);
		String columnName = "";
		
		while(matcher.find()){
			columnName = matcher.group();
			columnName =columnName.replaceAll("\\{|\\}|\\=|\\?", "").trim();
			columnNameList.add(columnName);
		}
		String columns = validateColumn(columnNameList,tableName);
		IBaseCommonDao dao = TorchDaoManager.getDao();
		//调用存储过程
		if(logger.isDebugEnabled()){
			logger.debug("调用存储过程{PROC_EDIT_COLUMN}，参数为：["+ tableName+","+configId+","+columns+"]");
		}
		List<StoredProcParam> proc_params = new ArrayList<StoredProcParam>();
		proc_params.add(new StoredProcParam(1, tableName, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(2, configId, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(3, columns, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		dao.callStoreProcess("{call PROC_EDIT_COLUMN(?,?,?)}", proc_params);
		getEditColumnInfo(params,result);
	}*/
	/**
	 * 查找传入的字符串中的SQL操作关键字，返回匹配到的关键字或符号
	 * @param expr
	 * @return
	 */
	private String getOpString(String expr){
		
		if(StringUtils.isNotBlank(expr)){
			expr=expr.toLowerCase();
			Matcher matcher =null;
			String tmp=null;
			if(expr.contains("in")){
				return "in";
			}
			if (expr.contains("like")){
				return "like";
			}
			matcher =CommonDefine.NOTMORE_PATTERN.matcher(expr);
			if(matcher.find()){
				tmp = matcher.group();
				return tmp;
			}
			matcher =CommonDefine.NOTLESS_PATTERN.matcher(expr);
			if(matcher.find()){
				tmp = matcher.group();
				return tmp;
			}
			matcher = CommonDefine.NOTEQUAL_PATTERN.matcher(expr);
			if(matcher.find()){
				tmp = matcher.group();
				return tmp;
			}
			if(expr.contains(">")){
				return ">";
			}
			if(expr.contains("<")){
				return "<";
			}
			if(expr.contains("=")){
				return "=";
			}
			return null;
		}
		return null;
		
	}
	/**
	 * 根据查询的sql生成配置项
	 * 
	 * @param params
	 * @param result
	 * @throws Exception 
	 */
	public void getQueryConfigBySql(Map<String, String> params,Map<String, Object>result) {
		String sql = params.get("configSql");
		
		String configId = params.get("configId"); 
		/**1 配置condition的参数**/
		Matcher matcher = Pattern.compile("\\{.*?\\}",Pattern.DOTALL).matcher(sql);
		String expr = "";
		StringBuffer ops=new StringBuffer();
		StringBuffer fields=new StringBuffer();
		StringBuffer names=new StringBuffer();
		while(matcher.find()){
			expr = matcher.group();
			String op = getOpString(expr);
			if(op==null){
				throw new RuntimeException("不支持的操作类型，请检查所配置的SQL正确性。 错误语句："+expr);
			}
			String field = expr.substring(1,expr.toLowerCase().indexOf(op)).trim();
			String name = StringUtil.underlineToCamel(field);//转成驼峰格式
			name=name.replaceAll("(?s)(.*?\\.)", "");//去掉表别名 t.字样
			ops.append(op).append("|");
			fields.append(field).append("|");
			names.append(name).append("|");
		}
		ops.setLength(ops.length()-1);
		fields.setLength(fields.length()-1);
		names.setLength(names.length()-1);
		
		/**2 配置column的参数**/
		IBaseCommonDao dao = TorchDaoManager.getDao();
		String sqltmp = sql.replaceAll("(?is)(\\{.*?\\})", "rownum=1");
		StringBuffer colNames = new StringBuffer();
		StringBuffer colFields = new StringBuffer();
		try {
			List<ResultColumns> columns = dao.getColumns(sqltmp);
			for (ResultColumns md : columns) {
				String colName = md.getColumnName();
				String labelName = md.getColumnLabel();
				labelName = StringUtil.underlineToCamel(labelName);
				labelName=labelName.replaceAll("(?s)(.*?\\.)", "");
				//indexs.append(i).append("|");
				colFields.append(colName).append("|");
				colNames.append(labelName).append("|");
			}
			//indexs.setLength(indexs.length()-1);
			colFields.setLength(colFields.length()-1);
			colNames.setLength(colNames.length()-1);
		} catch (Throwable e) {
			throw new RuntimeException("SQL配置有误，请检查SQL正确性："+e.getMessage());
		}
		
		//System.out.println("["+ configId+","+ops.toString()+","+fields.toString()+","+names.toString()+"]");
		
		//调用存储过程
		if(logger.isDebugEnabled()){
			logger.debug("调用存储过程{PROC_QUERY_COLUMN}，参数为：["+ configId+","+ops.toString()+","+fields.toString()+","+names.toString()+"]");
		}
		List<StoredProcParam> proc_params = new ArrayList<StoredProcParam>();
		proc_params.add(new StoredProcParam(1, configId, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(2, ops.toString(), StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(3, fields.toString(), StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(4, names.toString(), StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
	//	proc_params.add(new StoredProcParam(5, indexs.toString(), StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(5, colFields.toString(), StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(6, colNames.toString(), StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		dao.callStoreProcess("{call PROC_QUERY_CONFIG(?,?,?,?,?,?)}", proc_params);
		getQueryColumnInfo(params, result);
		getQueryConditionInfo(params, result);
	}
	/**
	 * 根据更新的sql生成配置项
	 * 
	 * @param Map<String,String>params（带插入传入值sql,configId等）
	 * 
	 * @param Map<String, Object>result
	 */
	private void createEditColumnBySql(Map<String, String> params,Map<String, Object>result){
		String sql = params.get("configSql");
		
		//更新表名称
		String tableName = params.get("tableName");
		String configId = params.get("configId"); 
		//更新列
		List<String> columnNameList = new ArrayList<String>();
		Matcher matcher = Pattern.compile("\\{.*?\\}",Pattern.DOTALL).matcher(sql);
		String columnName = "";
		StringBuffer camelName = new StringBuffer();
		String name = "";
		while(matcher.find()){
			columnName = matcher.group();
			columnName =columnName.replaceAll("\\{|\\}|\\=|\\?", "").trim();
			columnNameList.add(columnName);
			name = StringUtil.underlineToCamel(columnName);
			name = name.replaceAll("(?s)(.*?\\.)", "");//去掉表别名 t.字样
			camelName.append(name);
			camelName.append("|");
		}
		String camelNameStr = camelName.toString();
		if(StringUtils.isBlank(camelNameStr)){
			throw new RuntimeException("edit sql error：not found editColumn.");
		}
		camelNameStr = camelNameStr.toString().substring(0,camelNameStr.length()-1);
		
		String columns = validateColumn(columnNameList,tableName);
		IBaseCommonDao dao = TorchDaoManager.getDao();
		//调用存储过程
		if(logger.isDebugEnabled()){
			logger.debug("调用存储过程{PROC_EDIT_COLUMN}，参数为：["+ tableName+","+configId+","+columns+","+camelNameStr+"]");
		}
		List<StoredProcParam> proc_params = new ArrayList<StoredProcParam>();
		proc_params.add(new StoredProcParam(1, tableName, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(2, configId, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(3, columns, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		proc_params.add(new StoredProcParam(4, camelNameStr, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));

		dao.callStoreProcess("{call PROC_EDIT_COLUMN(?,?,?,?)}", proc_params);
		getEditColumnInfo(params,result);
	}
	
	/**
	 * 将insert、update转化为select惊醒校验
	 * 
	 * @param columnNameList
	 * @param tableName
	 * @return columns 如ENT_ID|ENT_NAME
	 */
	public static String validateColumn(List<String> columnNameList,String tableName){
		String columnName = "";
		String[]columnArray;
		StringBuffer columns = new StringBuffer();
		
		StringBuffer querySql = new StringBuffer(" select ");
		for(int i=0;i<columnNameList.size();i++){
			columnName = columnNameList.get(i);
			columnArray = columnName.split("\\.");
			//无别名更新列字符串如 ENT_NAME|ENT_ID
			if(i==0){
				if(columnArray.length==1){
					columns.append(columnName);
					querySql.append(columnArray[0]);
				}else if(columnArray.length==2){
					columns.append(columnName);
					querySql.append(columnArray[1]);
				}else{
					throw new RuntimeException("更新列别名有误，请检查配置。");
				}
			}else{
				if(columnArray.length==1){
					columns.append("|");
					columns.append(columnName);
					querySql.append(",");
					querySql.append(columnArray[0]);
				}else if(columnArray.length==2){
					columns.append("|");
					columns.append(columnName);
					querySql.append(",");
					querySql.append(columnArray[1]);
				}else{
					throw new RuntimeException("更新列别名有误，请检查配置。");
				}
			}
		}
		querySql.append(" from ");
		querySql.append(tableName);
		//校验更新字段名称是否有效
		IBaseCommonDao dao = TorchDaoManager.getDao();
		try{
			if(logger.isDebugEnabled()){
				logger.debug("更新转化的查询sql为："+querySql.toString());
			}
			dao.list(querySql.toString());
			return columns.toString();
		}catch(Throwable e){
			throw new RuntimeException("编辑列配置有误"+e.getMessage());
		}
	}
	
	/**
	 * 获取编辑操作的列信息
	 * 
	 * @param params
	 * @param result
	 */
	public void getEditColumnInfo(Map<String, String> params,Map<String, Object>result){
		IBaseCommonDao dao = TorchDaoManager.getDao();
		String sqlString = "select * from sys_edit_column_config c where c.config_id=?";
		int pageSize, pageNo;
		String configId = params.get("configId");
		pageSize = StringUtil.string2Int(params.get("pageSize"),
				10);
		pageNo = StringUtil.string2Int(params.get("pageNo"), 1);
		PageBo ret = null;
		ret = dao.listForPage(sqlString, pageNo, pageSize, configId);
		result.put("editColumn", ret);
	}
	
	/**
	 * 获取查询操作列的信息
	 * 
	 * @param params
	 * @param result
	 */
	public void getQueryColumnInfo(Map<String, String> params,Map<String, Object>result){
		IBaseCommonDao dao = TorchDaoManager.getDao();
		String sqlString = "select * from sys_query_column_config c where c.config_id=?";
		int pageSize, pageNo;
		String configId = params.get("configId");
		pageSize = StringUtil.string2Int(params.get("pageSize"),
				10);
		pageNo = StringUtil.string2Int(params.get("pageNo"), 1);
		PageBo ret = null;
		ret = dao.listForPage(sqlString, pageNo, pageSize, configId);
		result.put("queryColumn", ret);
	}
	
	/**
	 * 获取查询编辑列信息
	 * 
	 * @param params
	 * @param result
	 */
	public void getQueryConditionInfo(Map<String, String> params,Map<String, Object>result){
		IBaseCommonDao dao = TorchDaoManager.getDao();
		String sqlString = "select * from sys_query_condition_config c where c.config_id=?";
		int pageSize, pageNo;
		String configId = params.get("configId");
		pageSize = StringUtil.string2Int(params.get("pageSize"),
				10);
		pageNo = StringUtil.string2Int(params.get("pageNo"), 1);
		PageBo ret = null;
		ret = dao.listForPage(sqlString, pageNo, pageSize, configId);
		result.put("queryCondition", ret);
	}
}
