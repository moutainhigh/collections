package com.gwssi.ebaic.torch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.config_core.HighLevelConfig;
import com.gwssi.torch.core.TemplateSupport;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.fun.TreeConfigBo;

/**
 * Torch组件后台配置入口
 * 
 * @author liuxiangqian
 *
 */
@Controller
@RequestMapping(value="/torch/config")
public class JazzTorchConfigController {
	
	@Autowired
	JazzTorchConfigService jazzConfigService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/queryFuncTree")
    public void queryFuncTree(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        List funcList = jazzConfigService.queryFuncTree();
        Map<String,String> rootNode = new HashMap<String,String>();
        String rootName = "Torch"+"("+TemplateSupport.PAGE_RELATIVE_PATH+")";
        rootNode.put("name", rootName);
        rootNode.put("id", "0");
        rootNode.put("type", "root");
        funcList.add(rootNode);
        res.addTree("funcTree", funcList);
    }
	
	@RequestMapping("/saveTree")
	public void saveTree(OptimusRequest req,OptimusResponse res)
			throws OptimusException{
		TreeConfigBo treeBo = req.getForm("treeformpanel",TreeConfigBo.class);
		if(treeBo==null){
			throw new OptimusException("未找到树节点配置信息");
		}
		jazzConfigService.saveTree(treeBo);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/treeDetail")
	public void treeDetail(OptimusRequest req,OptimusResponse res)
			throws OptimusException{
		String treeId = ParamUtil.get(req, "treeId", true);
		Map detail = new HashMap();
		detail = jazzConfigService.queryTreeDetail(treeId);
		res.addForm("formpanel",detail);
	}
	/**
	 * 火炬框架后台更新和插入sys_function_config
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/insertFunConfig")
	public void insertFunConfig(OptimusRequest request, OptimusResponse response)throws OptimusException {
		Map<String, String> map = request.getForm("functionConfigPanel");
		Map<String,Object> result = new HashMap<String,Object>();
		HighLevelConfig hc = new HighLevelConfig();
		String functionId = map.get("functionId");
		if(StringUtil.isBlank(functionId)){
			hc.insertFunConfig(map, result);
		}else{
			hc.updateFunConfig(map, result);
		}
		response.addAttr("save","success");
	}
	
	/**
	 * 查询sys_function_config信息
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryFuncConfig")
	public void queryFuncConfig(OptimusRequest request, OptimusResponse response) throws OptimusException{
		String functionId = (String)request.getAttr("functionId");
		Map<String,Object> result = new HashMap<String,Object>();
		jazzConfigService.queryFuncConfig(functionId, result);
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("functionInfo");
		Map<String, Object> map = null;
		if(!list.isEmpty()){
			map =list.get(0);
		}else{
			throw new OptimusException("该function不存在");
		}
		response.addForm("functionConfigPanel", map);
	}
	/**
	 * 火炬框架后台配置保存sys_edit_config、sys_query_config表，生成对应的column和condition入口
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/torchConfig")
	public void torchConfig(OptimusRequest request, OptimusResponse response) throws OptimusException {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String, String> map = null;
		map =request.getForm("sysEditConfigPanel");
		if(map==null){
			map = request.getForm("sysQueryConfigPanel");
		}
		
		if(map==null){
			throw new OptimusException("请注意所传formpanel的正确性。");
		}
		
		String model = (String)request.getAttr("model");
		String editType = map.get("editType");
		if(StringUtil.isBlank(model)){
			throw new OptimusException("请选择配置模式");
		}
		if(StringUtil.isBlank(editType)){
			throw new OptimusException("请选择编辑类型");
		}
		map.put("model", model);
		PageBo ret = null;
		PageBo retCondition = null;
		if("high".equals(model)){//高级模式
			HighLevelTorchConfig hc = new HighLevelTorchConfig();
			String configId = map.get("configId");
			if(StringUtil.isBlank(configId)){//插入并且，初始化列信息
				
				hc.insertConfig(map, result);
				
				if("query".equals(editType)){
					ret = (PageBo) result.get("queryColumn");
					retCondition = (PageBo) result.get("queryCondition");
					if(ret==null){
						throw new OptimusException("未查到列信息。");
					}
					/*response.addGrid("queryColumnGrid", ret.getResult());
					response.addGrid("queryConditionGrid", retCondition.getResult());*/
					response.addAttr("save","success");
				}
				
				if("insert".equals(editType)||"update".equals(editType)){
					ret = (PageBo) result.get("editColumn");
					if(ret==null){
						response.addAttr("save","success");
//						throw new OptimusException("未查到列信息。");
					}
					/*response.addGrid("editColumnGrid", ret.getResult());*/
					response.addAttr("save","success");
				}
				
				if("delete".equals(editType)){
					ret = (PageBo) result.get("editColumn");
					if(ret==null){
						response.addAttr("save","success");
					}
					/*response.addGrid("editColumnGrid", ret.getResult());*/
				}
				
			}else{
				//更新；
				if("query".equals(editType)){
					jazzConfigService.savequeryConfig(map);
					response.addAttr("save","success");
				}
				if("insert".equals(editType)||"update".equals(editType)||"delete".equals(editType)){
					jazzConfigService.saveEditConfig(map);
					response.addAttr("save","success");
				}
			}
		}
		
		if("low".equals(model)){//低级模式
			
		}
	}
	
	/**
	 * 查询sys_query_config,sys_edit_config信息
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/editOrQueryGrid")
	public void editOrQueryGrid(OptimusRequest request, OptimusResponse response) throws OptimusException{
		String functionId = (String)request.getAttr("functionId");
		
//		String functionId =request.getParameter("functionId");
//		String functionId = "query_ent_validate";
		Map<String,String> pageMap = new HashMap<String, String>();
		Map<String,Object> result = new HashMap<String,Object>();
		pageMap.put("functionId",functionId);
		//解析jazz  gridPanel  参数
		String postData = request.getParameter("postData");
		Map map = JSON.parseObject(postData);
		List list1 = (List)map.get("data");
		Map map0 = (Map)list1.get(0);
		Map map1 = (Map)list1.get(2);
		String pageSize = String.valueOf((Integer)map0.get("data"));
		String pageNo = String.valueOf((Integer)map1.get("data"));
		pageMap.put("pageSize",pageSize);
		pageMap.put("pageNo",pageNo);
		//查询列表的值
		jazzConfigService.editOrQueryGrid(pageMap,result);
		PageBo ret = (PageBo) result.get("editOrQueryGrid");
		response.setPaginationParams(generatePaginationParams(ret));
		if(ret!=null){
			response.addGrid("editOrQueryGrid", ret.getResult());
		}else{
			throw new OptimusException("没有配置查询列信息");
		}
	}
	
	/**
	 * 查询sys_query_config信息
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryQueryConfig")
	public void queryQueryConfig(OptimusRequest request, OptimusResponse response) throws OptimusException{
		String configId = (String)request.getAttr("configId");
		Map<String,Object> result = new HashMap<String,Object>();
		jazzConfigService.queryQueryConfig(configId, result);
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("queryConfigInfo");
		Map<String, Object> map = null;
		if(!list.isEmpty()){
			map =list.get(0);
		}else{
			throw new OptimusException("该查询配置不存在");
		}
		response.addForm("sysQueryConfigPanel", map);
	}
	
	@RequestMapping("/queryConfig")
	public void queryConfig(OptimusRequest request, OptimusResponse response)throws OptimusException{
		String configId = request.getParameter("configId");
		String sql = "select q.* from sys_query_config q where config_id=?";
		@SuppressWarnings("rawtypes")
		Map map = DaoUtil.getInstance().queryForRow(sql, configId);
		response.addForm("sysQueryConfigPanel", map);
	}
	
	/**
	 * 查询sys_edit_config信息
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryEditConfig")
	public void queryEditConfig(OptimusRequest request, OptimusResponse response) throws OptimusException{
		String configId = (String)request.getAttr("configId");
		Map<String,Object> result = new HashMap<String,Object>();
		jazzConfigService.queryEditConfig(configId, result);
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("editConfigInfo");
		Map<String, Object> map = null;
		if(!list.isEmpty()){
			map =list.get(0);
		}
		response.addForm("editColumnPanel", map);
	}
	
	
	/**
	 * 查询sys_edit_column_config的列表
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/editColumnList")
	public void editColumnList(OptimusRequest request, OptimusResponse response)throws OptimusException {
		Map<String,String> pageMap = new HashMap<String, String>();
		//准备参数configId
		Map<String,Object> result = new HashMap<String,Object>();
		String configId = (String)request.getAttr("configId");
		pageMap.put("configId",configId);
		//解析jazz  gridPanel  参数
		String postData = request.getParameter("postData");
		Map map = JSON.parseObject(postData);
		List list1 = (List)map.get("data");
		Map map0 = (Map)list1.get(0);
		Map map1 = (Map)list1.get(2);
		String pageSize = String.valueOf((Integer)map0.get("data"));
		String pageNo = String.valueOf((Integer)map1.get("data"));
		pageMap.put("pageSize",pageSize);
		pageMap.put("pageNo",pageNo);
		//查询列表的值
		HighLevelConfig hc = new HighLevelConfig();
		hc.getEditColumnInfo(pageMap, result);
		PageBo ret = (PageBo) result.get("editColumn");
		response.setPaginationParams(generatePaginationParams(ret));
		if(ret!=null){
			response.addGrid("editColumnGrid", ret.getResult());
		}else{
			throw new OptimusException("没有配置查询列信息");
		}
	}
	
	/**
	 * 查询sys_query_column_config的列表
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryColumnList")
	public void queryColumnList(OptimusRequest request, OptimusResponse response)throws OptimusException {
		Map<String,String> pageMap = new HashMap<String, String>();
		//准备参数configId
		Map<String,Object> result = new HashMap<String,Object>();
		String configId = (String)request.getAttr("configId");
		pageMap.put("configId",configId);
		//解析jazz  gridPanel  参数
		String postData = request.getParameter("postData");
		Map map = JSON.parseObject(postData);
		List list1 = (List)map.get("data");
		Map map0 = (Map)list1.get(0);
		Map map1 = (Map)list1.get(2);
		String pageSize = String.valueOf((Integer)map0.get("data"));
		String pageNo = String.valueOf((Integer)map1.get("data"));
		pageMap.put("pageSize",pageSize);
		pageMap.put("pageNo",pageNo);
		//查询列表的值
		HighLevelConfig hc = new HighLevelConfig();
		hc.getQueryColumnInfo(pageMap, result);
		PageBo ret = (PageBo) result.get("queryColumn");
		response.setPaginationParams(generatePaginationParams(ret));
		if(ret!=null){
			response.addGrid("queryColumnGrid", ret.getResult());
		}else{
			throw new OptimusException("没有配置查询列信息");
		}
	}
	
	/**
	 * 查询sys_query_condition_config的列表
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryConditionColumnList")
	public void queryConditionColumnList(OptimusRequest request, OptimusResponse response)throws OptimusException {
		Map<String,String> pageMap = new HashMap<String, String>();
		//准备参数conditionId
		Map<String,Object> result = new HashMap<String,Object>();
		String configId = (String)request.getAttr("configId");
		pageMap.put("configId",configId);
		//解析jazz  gridPanel  参数
		String postData = request.getParameter("postData");
		Map map = JSON.parseObject(postData);
		List list1 = (List)map.get("data");
		Map map0 = (Map)list1.get(0);
		Map map1 = (Map)list1.get(2);
		String pageSize = String.valueOf((Integer)map0.get("data"));
		String pageNo = String.valueOf((Integer)map1.get("data"));
		pageMap.put("pageSize",pageSize);
		pageMap.put("pageNo",pageNo);
		//查询列表的值
		HighLevelConfig hc = new HighLevelConfig();
		hc.getQueryConditionInfo(pageMap, result);
		PageBo ret = (PageBo) result.get("queryCondition");
		response.setPaginationParams(generatePaginationParams(ret));
		if(ret!=null){
			response.addGrid("queryConditionGrid", ret.getResult());
		}else{
			throw new OptimusException("没有配置查询列信息");
		}
	}
	/**
	 * 火炬框架后台查询sys_edit_column_config
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/queryEditColumnDetail")
	public void queryEditColumnDetail(OptimusRequest request, OptimusResponse response)throws OptimusException {
		String columnId = (String)request.getAttr("columnId");
		Map<String,Object> result = new HashMap<String,Object>();
		jazzConfigService.queryEditColumnDetail(columnId, result);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("editColumn");
		Map<String, Object> map = null;
		if(!list.isEmpty()){
			map =list.get(0);
		}else{
			throw new OptimusException("该列数据不存在");
		}
		response.addForm("editColumnPanel", map);
	}
	
	/**
	 * 火炬框架后台保存sys_edit_column_config
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/saveEditColumn")
	public void saveEditColumn(OptimusRequest request, OptimusResponse response)throws OptimusException {
		Map<String, String> map = request.getForm("editColumnPanel");
		Map<String,Object> result = new HashMap<String,Object>();
		String columnId = map.get("columnId");
		if(StringUtil.isBlank(columnId)){
			jazzConfigService.insertEditColumn(map, result);
		}else{
			jazzConfigService.saveEditColumn(map);
		}
		response.addAttr("save","success");
	}
	
	/**
	 * 火炬框架后台删除sys_query_config列入口
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteQueryConfig")
	public void deleteQueryConfig(OptimusRequest request, OptimusResponse response)throws OptimusException {
		String configId = (String)request.getAttr("configId");
		jazzConfigService.deleteQueryConfig(configId);
		response.addAttr("del","success");
	}
	/**
	 * 火炬框架后台删除sys_edit_config;列入口
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteEditConfig")
	public void deleteEditConfig(OptimusRequest request, OptimusResponse response)throws OptimusException {
		String configId = (String)request.getAttr("configId");
		jazzConfigService.deleteEditConfig(configId);
		response.addAttr("del","success");
	}
	/**
	 * 火炬框架后台删除sys_query_condition_config列入口
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteQueryConditionColumn")
	public void deleteQueryConditionColumn(OptimusRequest request, OptimusResponse response)throws OptimusException {
		String conditionId = (String)request.getAttr("conditionId");
		jazzConfigService.deleteQueryConditionColumn(conditionId);
		response.addAttr("del","success");
	}
	
	/**
	 * 火炬框架后台删除sys_query_column_config列入口
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteQueryColumn")
	public void deleteQueryColumn(OptimusRequest request, OptimusResponse response)throws OptimusException {
		String columnId = (String)request.getAttr("columnId");
		jazzConfigService.deleteQueryColumn(columnId);
		response.addAttr("del","success");
	}
	
	/**
	 * 火炬框架后台删除sys_edit_column_config列入口
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteEditColumn")
	public void deleteEditColumn(OptimusRequest request, OptimusResponse response)throws OptimusException {
		String columnId = (String)request.getAttr("columnId");
		jazzConfigService.deleteEditColumn(columnId);
		response.addAttr("del","success");
	}
	
	/**
	 * 火炬框架后台查询sys_edit_column_config
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/queryQueryColumnDetail")
	public void queryQueryColumnDetail(OptimusRequest request, OptimusResponse response)throws OptimusException {
		String columnId = (String)request.getAttr("columnId");
		Map<String,Object> result = new HashMap<String,Object>();
		jazzConfigService.queryQueryColumnDetail(columnId, result);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("queryColumn");
		Map<String, Object> map = null;
		if(!list.isEmpty()){
			map =list.get(0);
		}else{
			throw new OptimusException("该列数据不存在");
		}
		response.addForm("queryColumnPanel", map);
	}
	
	/**
	 * 火炬框架后台保存sys_edit_column_config
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/saveQueryColumn")
	public void saveQueryColumn(OptimusRequest request, OptimusResponse response)throws OptimusException {
		Map<String, String> map = request.getForm("queryColumnPanel");
		Map<String,Object> result = new HashMap<String,Object>();
		String columnId = map.get("columnId");
		if(StringUtil.isBlank(columnId)){
			jazzConfigService.insertQueryColumn(map, result);
		}else{
			jazzConfigService.saveQueryColumn(map);
		}
		response.addAttr("save","success");
	}
	/**
	 * 火炬框架后台查询sys_query_condition_config
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/queryQueryConditionDetail")
	public void queryQueryConditionDetail(OptimusRequest request, OptimusResponse response)throws OptimusException {
		String conditionId = (String)request.getAttr("conditionId");
		Map<String,Object> result = new HashMap<String,Object>();
		jazzConfigService.queryQueryConditionDetail(conditionId, result);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("queryCondition");
		Map<String, Object> map = null;
		if(!list.isEmpty()){
			map =list.get(0);
		}else{
			throw new OptimusException("该列数据不存在");
		}
		response.addForm("queryConditionPanel", map);
	}
	
	/**
	 * 火炬框架后台保存sys_query_condition_config
	 * @param optimusRequest
	 * @param optimusResponse
	 * @throws OptimusException
	 */
	@RequestMapping("/saveQueryCondition")
	public void saveQueryCondition(OptimusRequest request, OptimusResponse response)throws OptimusException {
		Map<String, String> map = request.getForm("queryConditionPanel");
		Map<String,Object> result = new HashMap<String,Object>();
		String conditionId = map.get("conditionId");
		if(StringUtil.isBlank(conditionId)){
			jazzConfigService.insertQueryCondition(map, result);
		}else{
			jazzConfigService.saveQueryCondition(map);
		}
		response.addAttr("save","success");
	}
	
	
	
	
	/**
	 * 构造分页参数
	 * @param bo
	 * @return
	 */
	public Map<String, Object>generatePaginationParams(Object bo){
		if(bo == null){
			return null;
		}
		Map<String,Object> paginationParamsMap = new HashMap<String,Object>();
		if(bo instanceof PageBo){
			PageBo pageBo = (PageBo)bo;
			paginationParamsMap.put("page", pageBo.getPageIndex());
			paginationParamsMap.put("pagerows", pageBo.getPageRows());
			paginationParamsMap.put("totalrows", pageBo.getTotal());
		}
		return paginationParamsMap;
	}
}
