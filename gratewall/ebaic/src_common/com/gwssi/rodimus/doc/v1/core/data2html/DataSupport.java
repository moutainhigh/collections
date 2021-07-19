package com.gwssi.rodimus.doc.v1.core.data2html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v1.config.SysDocDataForDocChapManager;
import com.gwssi.rodimus.doc.v1.config.SysDocDataForListManager;
import com.gwssi.rodimus.doc.v1.domain.SysDocDataConfig;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
/**
 * <h2>依据 sys_doc_data配置获取数据。</h2>
 * <pre>
 *  如果sys_doc_data记录中list_id不为空，则数据作用域为list。 
 *  如果sys_doc_data记录中list_id不为空，则数据作用域为doc。
 * </pre> 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class DataSupport {

	/**
	 * 依据 sys_doc_data配置获取数据。
	 * 
	 *  如果sys_doc_data记录中list_id不为空，则数据作用域为list。 
	 *  如果sys_doc_data记录中list_id不为空，则数据作用域为doc。
	 * @param listId
	 * @param gid
	 * @return
	 */
	public static Map<String, Object> buildListContext(String docCode, Map<String, Object> paramsMap) {
		if(StringUtil.isBlank(docCode)){
			throw new RodimusException("docCode不能为空。");
		}
		String sql = "select c.* from sys_doc_config c where c.doc_code = ?";
		Map<String,Object> listConfig = DaoUtil.getInstance().queryForRow(sql, docCode);
		String docConfigId = StringUtil.safe2String(listConfig.get("docConfigId"));
		// 定义返回值
		Map<String, Object> ret = new HashMap<String,Object>();
		// 读入配置信息
		List<SysDocDataConfig> configs = SysDocDataForListManager.instance.getConfig(docConfigId);
		String var , paramNames , varType ;
		for(SysDocDataConfig config : configs){
			sql = config.getSql();
			if(StringUtil.isBlank(sql)){
				throw new RodimusException("sql不能为空。");
			}
			var = config.getVar();
			if(StringUtil.isBlank(var)){
				throw new RodimusException("var不能为空。");
			}
			varType = config.getVarType();
			if(StringUtil.isBlank(varType)){
				varType = "list";
			}
			paramNames = config.getParams();
			if(StringUtil.isBlank(paramNames)){
				throw new RodimusException("params不能为空。");
			}
			List<Object> sqlParams = prepareSqlParams(paramNames,paramsMap);
			List<Map<String,Object>> list = DaoUtil.getInstance().queryForList(sql, sqlParams);
			if(list==null || list.isEmpty()){
				continue ;
			}
			if("list".equals(varType)){
				ret.put(var, list);
				continue ;
			}
			if("map".equals(varType)){
				Map<String,Object> row = list.get(0);
				ret.put(var, row);
				continue ;
			}
		}
		paramsMap.put("docTitle",listConfig.get("title"));
		return ret;
	}

	/**
	 * @param listId
	 * @param gid
	 * @return
	 */
	public Map<String, Object> buildDocContext(String chapConfigId, String gid) {
		if(StringUtil.isBlank(chapConfigId)){
			throw new RodimusException("chapConfigId不能为空。");
		}
		if(StringUtil.isBlank(gid)){
			throw new RodimusException("gid不能为空。");
		}
		// 定义返回值
		Map<String, Object> ret = new HashMap<String,Object>();
		// 读入配置信息
		Map<String,Object> paramsMap = ParamUtil.prepareParams(gid);
		List<SysDocDataConfig> configs = SysDocDataForDocChapManager.instance.getConfig(chapConfigId);
		String sql ,var , params , varType ;
		for(SysDocDataConfig config : configs){
			sql = config.getSql();
			if(StringUtil.isBlank(sql)){
				throw new RodimusException("sql不能为空。");
			}
			var = config.getVar();
			if(StringUtil.isBlank(sql)){
				throw new RodimusException("var不能为空。");
			}
			varType = config.getVarType();
			if(StringUtil.isBlank(varType)){
				varType = "list";
			}
			params = config.getParams();
			if(StringUtil.isBlank(params)){
				throw new RodimusException("params不能为空。");
			}
			List<Object> sqlParams = prepareSqlParams(params,paramsMap);
			List<Map<String,Object>> list = DaoUtil.getInstance().queryForList(sql, sqlParams);
			if(list==null || list.isEmpty()){
				continue ;
			}
			if("list".equals(varType)){
				ret.put(var, list);
				continue ;
			}
			if("map".equals(varType)){
				Map<String,Object> row = list.get(0);
				ret.put(var, row);
				continue ;
			}
		}
		return ret;
	}
	
	/**
	 * @param params
	 * @param paramsMap
	 * @return
	 */
	private static List<Object> prepareSqlParams(String params, Map<String, Object> paramsMap) {
		List<Object> ret = new ArrayList<Object>();
		if(StringUtil.isBlank(params) || paramsMap==null){
			return ret ; 
		}
		params = params.replaceAll(" ", "");
		String[] arrParamNames = params.split(",");
		if(arrParamNames.length<1){
			return ret ;
		}
		for(String paramName : arrParamNames){
			Object o = paramsMap.get(paramName);
			ret.add(o);
		}
		return ret;
	}
}
