package com.gwssi.ebaic.torch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.torch.dao.IBaseCommonDao;
import com.gwssi.torch.dao.TorchDaoManager;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.edit.EditColumnConfig;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.fun.TreeConfigBo;
import com.gwssi.torch.domain.query.QueryColumnConfigBo;
import com.gwssi.torch.domain.query.QueryConditionConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.util.ParameterUtils;
import com.gwssi.torch.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

@SuppressWarnings("unused")
@Service
public class JazzTorchConfigService {
	
	/**
	 * 获取torch配置树
	 * 
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List queryFuncTree() throws OptimusException{
        IBaseCommonDao dao = TorchDaoManager.getDao();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("");
        sqlBuffer.append(" select c.tree_id as id, c.tree_pid as pid, c.node_name as name, 'tree' as type ");
        sqlBuffer.append(" from sys_menutree_config c ");
        sqlBuffer.append(" union all ");
        sqlBuffer.append(" select f.function_id as id, f.tree_id as pid, f.pagefile_name as name, 'func' as type ");
        sqlBuffer.append(" from sys_function_config f ");
//        sqlBuffer.append(" union all ");
//        sqlBuffer.append(" select q.config_id as id, q.function_id as pid, q.config_name as name, 'query' as type ");
//        sqlBuffer.append(" from sys_query_config q ");
//        sqlBuffer.append(" union all ");
//        sqlBuffer.append(" select e.config_id as id, e.function_id as pid, e.config_name as name, 'edit' as type ");
//        sqlBuffer.append(" from sys_edit_config e ");
        List funcList = dao.list(sqlBuffer.toString());
        return funcList;
    }
	/**
	 * 获取menutree的详细信息
	 * 
	 * @param treeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map queryTreeDetail(String treeId){
		if(StringUtils.isBlank(treeId)){
			throw new RuntimeException("treeId can not be null.");
		}
		IBaseCommonDao dao = TorchDaoManager.getDao();
		StringBuffer sql = new StringBuffer(" select c.*,nvl(d.node_name,'Torch') as pname ");
		sql.append(" from sys_menutree_config c ");
		sql.append(" left join sys_menutree_config d ");
		sql.append(" on c.tree_pid = d.tree_id ");
		sql.append(" where c.tree_id = ? ");
		List list = dao.list(sql.toString(), treeId);
		Map ret = new HashMap();
		if(list!=null){
			ret = (Map)list.get(0);
		}
		return ret;
	}
	
	/**
	 * 保存树节点配置
	 * 
	 * @param treeBo
	 */
	public void saveTree(TreeConfigBo treeBo){
		IBaseCommonDao dao = TorchDaoManager.getDao();
		if(StringUtils.isBlank(treeBo.getTreeId())){
			String treeId = UUIDUtil.getUUID();
			String sql = " insert into sys_menutree_config c values(?,?,?,?,?) ";
			dao.execute(sql, treeId, treeBo.getTreePid(),treeBo.getNodeName(),treeBo.getLevelPath(),treeBo.getTreeIndex());
		}else{
			String sql = " update sys_menutree_config c set c.tree_pid=?,c.node_name=?,c.level_path=?,c.tree_index=? where c.tree_id=? ";
			dao.execute(sql, treeBo.getTreePid(),treeBo.getNodeName(),treeBo.getLevelPath(),treeBo.getTreeIndex(),treeBo.getTreeId());
		}
	}
	
	
	/**
	 * 获取function的配置信息
	 * 
	 * @param funcId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map queryFuncDetail(String funcId){
		if(StringUtils.isBlank(funcId)){
			throw new RuntimeException("funcId can not be null.");
		}
		IBaseCommonDao dao = TorchDaoManager.getDao();
		String sql = "select * from sys_function_config c where c.function_id=?";
		List list = dao.list(sql, funcId);
		Map ret = new HashMap();
		if(list!=null){
			ret = (Map)list.get(0);
		}
		return ret;
	}
	
	/**
	 * 查询sys_function_config信息
	 * @param functionId
	 * @param result
	 */
	public void queryFuncConfig(String functionId,Map<String, Object> result){
		if(StringUtils.isBlank(functionId)){
			throw new RuntimeException("functionId can not null null.");
		}
		String querySql = "";
		IBaseCommonDao dao = TorchDaoManager.getDao();
		
		querySql = " select * from sys_function_config c where c.function_id=? ";
		List<Map<String, Object>> list = dao.list(querySql, functionId);
		result.put("functionInfo", list);
	}
	
	/**
	 * 查询sys_edit_config信息
	 * @param configId
	 * @param result
	 */
	public void queryEditConfig(String configId,Map<String, Object> result){
		if(StringUtils.isBlank(configId)){
			throw new RuntimeException("configId can not null null.");
		}
		String querySql = "";
		IBaseCommonDao dao = TorchDaoManager.getDao();
		querySql = " select * from sys_edit_config c where c.config_id=? ";
		List<Map<String, Object>> list = dao.list(querySql, configId);
		result.put("editConfigInfo", list);
	}
	
	/**
	 * 查询sys_query_config信息
	 * @param configId
	 * @param result
	 */
	public void queryQueryConfig(String configId,Map<String, Object> result){
		if(StringUtils.isBlank(configId)){
			throw new RuntimeException("configId can not null null.");
		}
		String querySql = "";
		IBaseCommonDao dao = TorchDaoManager.getDao();
		querySql = " select * from sys_query_config c where c.config_id=? ";
		List<Map<String, Object>> list = dao.list(querySql, configId);
		result.put("queryConfigInfo", list);
	}
	/**
	 * 查询sys_edit_column_config信息
	 * @param configId
	 * @param result
	 */
	public void queryEditColumnDetail(String columnId,Map<String, Object> result){
		if(StringUtils.isBlank(columnId)){
			throw new RuntimeException("columnId can not null null.");
		}
		String querySql = "";
		IBaseCommonDao dao = TorchDaoManager.getDao();
		querySql = " select * from sys_edit_column_config c where c.column_id=? ";
		List<Map<String, Object>> list = dao.list(querySql, columnId);
		result.put("editColumn", list);
	}
	/**
	 * 修改sys_edit_column_config信息
	 * @param configId
	 * @param result
	 */
	public void saveEditColumn(Map<String,String> map){
		
		String updateSql = " update sys_edit_column_config s set s.column_index=?,s.column_title=?,s.column_name=?,s.column_field=?,"
						 + " s.column_field_type=?,s.column_dict=?,s.column_format=?,s.column_width=?,s.column_generate=?,"
						 + "s.column_editable=?,s.column_tips=?,s.column_rule=?,s.column_value_from=? where s.column_id=? ";
				
		EditColumnConfig editColConfigBo = ParameterUtils.mapString2Bo(EditColumnConfig.class, map);
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(editColConfigBo.getColumnIndex());
		paramList.add(editColConfigBo.getColumnTitle());
		paramList.add(editColConfigBo.getColumnName());
		paramList.add(editColConfigBo.getColumnField());
		paramList.add(editColConfigBo.getColumnFieldType());
		paramList.add(editColConfigBo.getColumnDict());
		paramList.add(editColConfigBo.getColumnFormat());
		paramList.add(editColConfigBo.getColumnWidth());
		paramList.add(editColConfigBo.getColumnGenerate());
		paramList.add(editColConfigBo.getColumnEditable());
		paramList.add(editColConfigBo.getColumnTips());
		paramList.add(editColConfigBo.getColumnRule());
		paramList.add(editColConfigBo.getColumnValueFrom());
		paramList.add(editColConfigBo.getColumnId());
		
		//执行insert
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	
	/**
	 * 添加sys_edit_column_config信息
	 * @param configId
	 * @param result
	 */
	public void insertEditColumn(Map<String,String> map, Map<String, Object> result){
		String insertSql = " insert into sys_edit_column_config values (?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?) ";
		/*String updateSql = " update sys_edit_column_config s set s.column_index=?,s.column_title=?,s.column_name=?,s.column_field=?,"
						 + " s.column_field_type=?,s.column_dict=?,s.column_format=?,s.column_width=?,s.column_generate=?,"
						 + "s.column_editable=?,s.column_tips=?,s.column_rule=?,s.column_value_from=? where s.column_id=? ";
		 */				
		EditColumnConfig editColConfigBo = ParameterUtils.mapString2Bo(EditColumnConfig.class, map);
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(UUIDUtil.getUUID());//column_id
		paramList.add(editColConfigBo.getConfigId());
		paramList.add(editColConfigBo.getColumnIndex());
		paramList.add(editColConfigBo.getColumnTitle());
		paramList.add(editColConfigBo.getColumnName());
		paramList.add(editColConfigBo.getColumnField());
		paramList.add(editColConfigBo.getColumnFieldType());
		paramList.add(editColConfigBo.getColumnDict());
		paramList.add(editColConfigBo.getColumnFormat());
		paramList.add(editColConfigBo.getColumnWidth());
		paramList.add(editColConfigBo.getColumnGenerate());
		paramList.add(editColConfigBo.getColumnEditable());
		paramList.add(editColConfigBo.getColumnTips());
		paramList.add(editColConfigBo.getColumnRule());
		paramList.add(editColConfigBo.getColumnValueFrom());
		
		//执行insert
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(insertSql, paramList.toArray());
	}
	
	/**
	 * 查询sys_query_column_config信息
	 * @param configId
	 * @param result
	 */
	public void queryQueryColumnDetail(String columnId,Map<String, Object> result){
		if(StringUtils.isBlank(columnId)){
			throw new RuntimeException("columnId can not null null.");
		}
		String querySql = "";
		IBaseCommonDao dao = TorchDaoManager.getDao();
		querySql = " select * from sys_query_column_config c where c.column_id=? ";
		List<Map<String, Object>> list = dao.list(querySql, columnId);
		result.put("queryColumn", list);
	}
	/**
	 * 修改sys_query_column_config信息
	 * @param configId
	 * @param result
	 */
	public void insertQueryColumn(Map<String,String> map, Map<String, Object> result){
		String insertSql = " insert into sys_query_column_config values (?,?,?, ?,?,?, ?,?,?,?) ";		

		/*String updateSql = " insert into sys_query_column_config s set s.column_index=?,s.column_title=?,s.column_name=?,s.column_field=?,"
						 + " s.column_field_type=?,s.column_dict=?,s.column_format=?,s.column_width=? where s.column_id=? ";*/
		QueryColumnConfigBo queryColConfigBo = ParameterUtils.mapString2Bo(QueryColumnConfigBo.class, map);
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(UUIDUtil.getUUID());//conlumn_id
		paramList.add(queryColConfigBo.getConfigId());
		paramList.add(queryColConfigBo.getColumnIndex());
		paramList.add(queryColConfigBo.getColumnTitle());
		paramList.add(queryColConfigBo.getColumnName());
		paramList.add(queryColConfigBo.getColumnField());
		paramList.add(queryColConfigBo.getColumnFieldType());
		paramList.add(queryColConfigBo.getColumnDict());
		paramList.add(queryColConfigBo.getColumnFormat());
		paramList.add(queryColConfigBo.getColumnWidth());
		
		//执行insert
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(insertSql, paramList.toArray());
	}
	
	/**
	 * 修改sys_query_column_config信息
	 * @param configId
	 * @param result
	 */
	public void saveQueryColumn(Map<String,String> map){
		
		String updateSql = " update sys_query_column_config s set s.column_index=?,s.column_title=?,s.column_name=?,s.column_field=?,"
						 + " s.column_field_type=?,s.column_dict=?,s.column_format=?,s.column_width=? where s.column_id=? ";
				
		QueryColumnConfigBo queryColConfigBo = ParameterUtils.mapString2Bo(QueryColumnConfigBo.class, map);
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(queryColConfigBo.getColumnIndex());
		paramList.add(queryColConfigBo.getColumnTitle());
		paramList.add(queryColConfigBo.getColumnName());
		paramList.add(queryColConfigBo.getColumnField());
		paramList.add(queryColConfigBo.getColumnFieldType());
		paramList.add(queryColConfigBo.getColumnDict());
		paramList.add(queryColConfigBo.getColumnFormat());
		paramList.add(queryColConfigBo.getColumnWidth());
		paramList.add(queryColConfigBo.getColumnId());
		
		//执行insert
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	
	/**
	 * 查询sys_query_condition_config信息
	 * @param configId
	 * @param result
	 */
	public void queryQueryConditionDetail(String conditionId,Map<String, Object> result){
		if(StringUtils.isBlank(conditionId)){
			throw new RuntimeException("conditionId can not null null.");
		}
		String querySql = "";
		IBaseCommonDao dao = TorchDaoManager.getDao();
		querySql = " select * from sys_query_condition_config c where c.condition_id=? ";
		List<Map<String, Object>> list = dao.list(querySql, conditionId);
		result.put("queryCondition", list);
	}
	/**
	 * 修改sys_query_condition_config信息
	 * @param configId
	 * @param result
	 */
	public void insertQueryCondition(Map<String,String> map, Map<String, Object> result){
		String insertSql = " insert into sys_query_condition_config values (?,?,?, ?,?,?, ?,?,?, ?,?,?,?) ";	
		/*String updateSql = " update sys_query_condition_config s set s.condition_index= ?,s.condition_title= ?,s.condition_name= ?,s.condition_field= ?,"
						 + " s.condition_field_type = ?,s.condition_dict= ?,s.condition_width= ?,s.condition_op= ?,s.condition_tips= ?, "
						 + " s.condition_rule= ?,s.condition_value_from= ? where s.condition_id = ? ";*/
				
		QueryConditionConfigBo queryConditionConfigBo = ParameterUtils.mapString2Bo(QueryConditionConfigBo.class, map);
		List<Object> paramList = new ArrayList<Object>();
		
		paramList.add(UUIDUtil.getUUID());
		paramList.add(queryConditionConfigBo.getConfigId());
		paramList.add(queryConditionConfigBo.getConditionIndex());
		paramList.add(queryConditionConfigBo.getConditionTitle());
		paramList.add(queryConditionConfigBo.getConditionName());
		paramList.add(queryConditionConfigBo.getConditionDict());
		paramList.add(queryConditionConfigBo.getConditionFieldType());
		paramList.add(queryConditionConfigBo.getConditionWidth());
		paramList.add(queryConditionConfigBo.getConditionField());
		paramList.add(queryConditionConfigBo.getConditionOp());
		paramList.add(queryConditionConfigBo.getConditionTips());
		paramList.add(queryConditionConfigBo.getConditionRule());
		paramList.add(queryConditionConfigBo.getConditionValueFrom());
		
	
		
		//执行insert
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(insertSql, paramList.toArray());
	}
	
	/**
	 * 插入sys_query_condition_config信息
	 * @param configId
	 * @param result
	 */
	public void saveQueryCondition(Map<String,String> map){
		
		String updateSql = " update sys_query_condition_config s set s.condition_index= ?,s.condition_title= ?,s.condition_name= ?,s.condition_field= ?,"
						 + " s.condition_field_type = ?,s.condition_dict= ?,s.condition_width= ?,s.condition_op= ?,s.condition_tips= ?, "
						 + " s.condition_rule= ?,s.condition_value_from= ? where s.condition_id = ? ";
				
		QueryConditionConfigBo queryConditionConfigBo = ParameterUtils.mapString2Bo(QueryConditionConfigBo.class, map);
		List<Object> paramList = new ArrayList<Object>();
		
		paramList.add(queryConditionConfigBo.getConditionIndex());
		paramList.add(queryConditionConfigBo.getConditionTitle());
		paramList.add(queryConditionConfigBo.getConditionName());
		paramList.add(queryConditionConfigBo.getConditionField());
		paramList.add(queryConditionConfigBo.getConditionFieldType());
		paramList.add(queryConditionConfigBo.getConditionDict());
		paramList.add(queryConditionConfigBo.getConditionWidth());
		paramList.add(queryConditionConfigBo.getConditionOp());
		paramList.add(queryConditionConfigBo.getConditionTips());
		paramList.add(queryConditionConfigBo.getConditionRule());
		paramList.add(queryConditionConfigBo.getConditionValueFrom());
		
		paramList.add(queryConditionConfigBo.getConditionId());
		
		//执行insert
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	
	/**
	 * 保存sys_edit_config信息
	 * @param configId
	 * @param result
	 */
	public void saveEditConfig(Map<String,String> map){
		
		String updateSql = " UPDATE SYS_EDIT_CONFIG S set S.CONFIG_INDEX = ?, S.CONFIG_TITLE = ?, S.CONFIG_NAME = ?, S.CONFIG_DESC = ?, S.CONFIG_SQL = ?," +
				"S.TPL_PATH = ?, S.ADAPTER_TYPE = ? ,S.TABLE_NAME = ?, S.PRIMARY_KEY = ? ,S.BEFORE_EXECUTE = ?, S.AFTER_EXECUTE = ? ,S.EDIT_TYPE = ? where S.CONFIG_ID = ?";
				
		EditConfigBo editConfigBo = ParameterUtils.mapString2Bo(EditConfigBo.class, map);
		List<Object> paramList = new ArrayList<Object>();
		
		paramList.add(editConfigBo.getConfigIndex());
		paramList.add(editConfigBo.getConfigTitle());
		paramList.add(editConfigBo.getConfigName());
		paramList.add(editConfigBo.getConfigDesc());
		paramList.add(editConfigBo.getConfigSql());
		paramList.add(editConfigBo.getTplPath());
		paramList.add(editConfigBo.getAdapterType());
		paramList.add(editConfigBo.getTableName());
		paramList.add(editConfigBo.getPrimaryKey());
		paramList.add(editConfigBo.getBeforeExecute());
		paramList.add(editConfigBo.getAfterExecute());
		paramList.add(editConfigBo.getEditType());
		
		paramList.add(editConfigBo.getConfigId());
		
		//执行insert
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	
	/**
	 * 保存sys_query_config信息
	 * @param configId
	 * @param result
	 */
	public void savequeryConfig(Map<String,String> map){
		
		String updateSql = " UPDATE sys_query_config S set S.CONFIG_INDEX = ?,S.CONFIG_TITLE = ?,S.CONFIG_NAME= ?,S.CONFIG_DESC = ?,S.CONFIG_SQL = ?," +
				"S.TPL_PATH  = ?,S.ADAPTER_TYPE  = ?,S.QUERY_FOR_PAGE =  ?,S.QUERY_ON_LOADING = ?,S.QUERY_TYPE = ?  where S.CONFIG_ID = ?";
				
		QueryConfigBo queryConfigBo = ParameterUtils.mapString2Bo(QueryConfigBo.class, map);
		List<Object> paramList = new ArrayList<Object>();
		
		paramList.add(queryConfigBo.getConfigIndex());
		paramList.add(queryConfigBo.getConfigTitle());
		paramList.add(queryConfigBo.getConfigName());
		paramList.add(queryConfigBo.getConfigDesc());
		paramList.add(queryConfigBo.getConfigSql());
		paramList.add(queryConfigBo.getTplPath());
		paramList.add(queryConfigBo.getAdapterType());
		paramList.add(queryConfigBo.isQueryForPage());
		paramList.add(queryConfigBo.isQueryOnLoading());
		paramList.add(queryConfigBo.getQueryType());
		
		paramList.add(queryConfigBo.getConfigId());
		
		//执行insert
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	/**
	 * 删除 sys_query_config信息
	 * @param configId
	 * @param result
	 */
	public void deleteQueryConfig(String configId) {
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(configId);
		String updateSql = " delete from  sys_query_config s  where s.config_id=? ";
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	
	/**
	 * 删除  sys_edit_config信息
	 * @param configId
	 * @param result
	 */
	public void deleteEditConfig(String configId) {
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(configId);
		String updateSql = " delete from sys_edit_config s  where s.config_id=? ";
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	
	/**
	 * 删除sys_edit_column_config信息
	 * @param configId
	 * @param result
	 */
	public void deleteEditColumn(String columnId){
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(columnId);
		String updateSql = " delete from sys_edit_column_config s  where s.column_id=? ";
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	
	
	/**
	 * 删除sys_query_column_config信息
	 * @param configId
	 * @param result
	 */
	public void deleteQueryColumn(String columnId){
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(columnId);
		String updateSql = " delete from sys_query_column_config s  where s.column_id=? ";
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	
	/**
	 * 删除sys_query_condition_config信息
	 * @param configId
	 * @param result
	 */
	public void deleteQueryConditionColumn(String conditionId){
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(conditionId);
		String updateSql = " delete from sys_query_condition_config s  where s.condition_id=? ";
		IBaseCommonDao dao=TorchDaoManager.getDao();
		dao.execute(updateSql, paramList.toArray());
	}
	
	/**
	 * 查询sys_query_config,sys_edit_config信息
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	public void editOrQueryGrid(Map<String, String> params,Map<String, Object>result){
		IBaseCommonDao dao = TorchDaoManager.getDao();
		StringBuffer sql = new StringBuffer();
		sql.append(" select f.config_id,f.function_id,f.config_index,f.config_title,f.config_name,f.config_desc,f.tpl_path,f.config_sql from ");
		sql.append(" (select c.config_id,c.function_id,c.config_index,c.config_title,c.config_name,c.config_desc,c.tpl_path,c.config_sql from sys_edit_config c ");
		sql.append(" union all select s.config_id,s.function_id,s.config_index,s.config_title,s.config_name,s.config_desc,s.tpl_path,s.config_sql from sys_query_config s) f ");
		sql.append("where f.function_id = ? order by f.config_index ");
		int pageSize, pageNo;
		String functionId = params.get("functionId");
		pageSize = StringUtil.string2Int(params.get("pageSize"),
				10);
		pageNo = StringUtil.string2Int(params.get("pageNo"), 1);
		PageBo ret = null;
		ret = dao.listForPage(sql.toString(), pageNo, pageSize, functionId);
		result.put("editOrQueryGrid", ret);
	}
	
}
