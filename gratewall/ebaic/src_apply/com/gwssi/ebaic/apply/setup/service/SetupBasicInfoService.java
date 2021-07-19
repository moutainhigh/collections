package com.gwssi.ebaic.apply.setup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;

/**
 * 
 * @author qiaozhaoyang
 */
@Service("setupBasicInfoService")
public class SetupBasicInfoService {
	
	
	/**
	 * 保存企业基本信息修改的内容
	 * @param params
	 */
	public void saveChanged(Map<String, String> params){
		String gid = params.get("gid");
		String sql = "select e.ENT_NAME,e.DOM_DISTRICT,e.DOM_OWNER,REG_CAP,e.TRADE_TERM,e.PRO_LOC_CITY,e.DOM_ACREAGE," +
				"e.DOM_DETAIL,e.PRO_LOC_OTHER,e.copy_no,e.dom_own_type,e.DOM_USAGE_TYPE,e.op_scope,e.pt_bus_scope," +
				"e.scope_json,e.business_scope,e.op_suffix,e.OP_CUSTOM_SCOPE,e.op_scope_type,e.main_scope " +
				"from be_wk_ent e where e.gid=?";
		/*String sql = "select e.* from be_wk_ent e where e.gid=?";*/
		List<String> param = new ArrayList<String>();
		param.add(gid);
//		List<BeWkEntBO> list = DaoUtil.getInstance().queryForListBo(sql, BeWkEntBO.class, param);
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql, param);
		if(list.isEmpty()){
			throw new EBaicException("保存企业基本信息修改的内容错误。");
		}
		Map<String, Object> oldParam = list.get(0);
		
		String scopeJson = ParamUtil.get("scopeJson",false);
		String businessScope = ParamUtil.get("businessScope",false);
		params = this.setParamValue(params, scopeJson, businessScope);
		
		/*params.put("opScope", params.get("approve-license"));
		params.put("ptBusScope", params.get("approveScope"));
		params.put("opSuffix", params.get("approveSuffix"));
		params.put("opCustomScope", params.get("approveCustom"));
		params.put("mainScope", params.get("approve-main"));*/
		
		for (String newKey : params.keySet()) {
			for(String oldKey : oldParam.keySet()){
				if(newKey.equals(oldKey)){
					String oldValue = "";
					if(oldParam.get(oldKey)!=null){
						oldValue = oldParam.get(oldKey).toString();
					}
					if(params.get(newKey)!=null&&(!(params.get(newKey).toString()).equals(oldValue))){
						this.saveModify(gid, newKey, oldValue, params.get(newKey).toString());
					}
				}
			}
		}
		
	}
	/**
	 * 保存企业基本信息修改的内容到数据库
	 * @param gid
	 * @param tableField
	 * @param beforeValue
	 * @param afterValue
	 */
	private void saveModify(String gid,String tableField,String beforeValue,String afterValue){
		//查询版本
		String sql = "select r.version from be_wk_requisition r where r.gid=?";
		List<String> param = new ArrayList<String>();
		param.add(gid);
		BigDecimal version = (BigDecimal) DaoUtil.getInstance().queryForOne(sql, param);
		if(version==null){
			version = new BigDecimal(1);
		}
		String selectSql = "select MODIFY_ID from BE_WK_MODIFY where TABLE_FIELD=?  and gid=?";
		List<String> selectParam = new ArrayList<String>();
		selectParam.add(tableField);
		selectParam.add(gid);
		String mid = (String) DaoUtil.getInstance().queryForOne(selectSql, selectParam);
		
		if(mid==null){
			//添加基本信息修改记录
			String insertSql = "insert into BE_WK_MODIFY(APP_USER_ID,MODIFY_ID,VERSION,TABLE_NAME,TABLE_FIELD,BEFORE,AFTER,GID,TIMESTAMP) " +
					"values(?,?,?,?,?,?,?,?,sysdate)";
			List<String> insertParam = new ArrayList<String>();
			insertParam.add(HttpSessionUtil.getCurrentUser().getUserId());
			insertParam.add(UUIDUtil.getUUID());
//			String newVersion = version.add(new BigDecimal(1)).toString();
			insertParam.add(version.toString());
			insertParam.add("BE_WK_ENT");
			insertParam.add(tableField.toUpperCase());
			insertParam.add(beforeValue);
			insertParam.add(afterValue);
			insertParam.add(gid);
			DaoUtil.getInstance().execute(insertSql, insertParam);
		}else{
			//更新基本信息修改记录
			String updateSql = "update BE_WK_MODIFY set APP_USER_ID=?,TABLE_NAME=?,TABLE_FIELD=?,AFTER=?,TIMESTAMP=sysdate  " +
					"where gid=?";
			List<String> updateParam = new ArrayList<String>();
			updateParam.add(HttpSessionUtil.getCurrentUser().getUserId());
			updateParam.add("BE_WK_ENT");
			updateParam.add(tableField.toUpperCase());
			updateParam.add(afterValue);
			updateParam.add(gid);
			DaoUtil.getInstance().execute(updateSql, updateParam);
		}
		
	}
	/**
	 * 解析scopeJson/businessScope 获取数据库be_wk_ent对应字段值,并添加到params里
	 * @param params
	 * @param scopeJson
	 * @param businessScope
	 */
	
	private Map<String, String> setParamValue(Map<String, String> params, String scopeJson, String businessScope) {
		// 0. 参数校验
		if(StringUtil.isBlank(scopeJson)){
			throw new EBaicException("经营范围不能为空。");
		}
		if(StringUtil.isBlank(businessScope)){
			throw new EBaicException("经营范围不能为空。");
		}
		
		//scope_json
//		params.put("scopeJson", (scopeJson==null)?"":scopeJson);
		//business_scope
//		params.put("businessScope", (businessScope==null)?"":businessScope);
		
		JSONObject jo = null;
		try{
			jo = JSONObject.parseObject(scopeJson);
		}catch(Throwable e){
			throw new EBaicException("解析JSON出错："+e.getMessage());
		}
		if(jo==null){
			throw new EBaicException("解析JSON出错。");
		}
		
		//op_scope_type
		String type = jo.getString("type");
		params.put("opScopeType", (type==null)?"2":type);
		if("6".equals(type)){ 
			String mainScope = "";
//			String isMainScopeStandard = "" ;
			StringBuffer opScope = new StringBuffer();
			StringBuffer ptScope = new StringBuffer();
			String customScope = "";
			String suffix = "";
			
			// 获得 op_suffix，项下标注
			suffix = jo.getString("suffix");
			params.put("opSuffix", (suffix==null)?"":suffix);
			
			JSONObject joMainScope = jo.getJSONObject("mainScope");
			if(joMainScope==null){
				throw new EBaicException("请选择主要经营范围。");
			}
//			boolean isStandard = joMainScope.getBooleanValue("isStandard");
			mainScope = joMainScope.getString("text");
			if(StringUtil.isBlank(mainScope)){
				throw new EBaicException("请选择主要经营范围。");
			}
			mainScope = mainScope.trim();
			mainScope += "。";
			//MAIN_SCOPE
			params.put("mainScope", mainScope);
			
			/*if(isStandard){
				isMainScopeStandard = "1";
			}else{
				isMainScopeStandard = "0";
			}
			//IS_MAIN_SCOPE_STANDARD
			params.put("isMainScopeStandard", isMainScopeStandard);*/
			// 获得 op_scope ， 许可经营范围
			opScope = getOpScope(jo,opScope);
			params.put("opScope", (opScope==null)?"":opScope.toString());
			
			// 获得 pt_bus_scope，普通经营范围
			ptScope =  getPtScope(jo,ptScope);
			params.put("ptBusScope", (ptScope==null)?"":ptScope.toString());
			
			// 获得 OP_CUSTOM_SCOPE
			customScope = jo.getString("customScope");
			params.put("opCustomScope", (customScope==null)?"":customScope.toString());
			
			return params;
			
		}
		// 主营 + 项下标注 + 许可
		if("1".equals(type)){ 
			// {"type":"1","suffix":"（企业依法","scope":[],"customScope":"","mainScope":{text:"科技信息咨询服务 ",isStandard:true}}
			
			StringBuffer opScope = new StringBuffer();//许可经营范围
			String suffix = "";
			String ptScope = "";
			String customScope = "";
			
			// 获得 op_suffix，项下标注
			suffix = jo.getString("suffix");
			params.put("opSuffix", (suffix==null)?"":suffix);
			
			JSONObject joMainScope = jo.getJSONObject("mainScope");
			if(joMainScope==null){
				throw new EBaicException("请选择主要经营范围。");
			}
			boolean isStandard = joMainScope.getBooleanValue("isStandard");
			String text = joMainScope.getString("text");
			if(StringUtil.isBlank(text)){
				throw new EBaicException("请选择主要经营范围。");
			}
			text = text.trim();
			text += "。";
			if(isStandard){
				//保存在pt_but_Scope里
				ptScope = text;
				params.put("ptBusScope",ptScope);
			}else{
				//保存在用户自己输入里	// 获得 OP_CUSTOM_SCOPE
				customScope = text;
				params.put("opCustomScope",customScope);
			}
			
			// 获得 op_scope ， 许可经营范围
			opScope = getOpScope(jo,opScope);
			params.put("opScope", (opScope==null)?"":opScope.toString());
			
			return params;
		}
		
		if("2".equals(type)){
			
			StringBuffer opScope = new StringBuffer();
			StringBuffer ptScope = new StringBuffer();
			String customScope = "";
			String suffix = "";
			
			// 获得 op_suffix，项下标注
			suffix = jo.getString("suffix");
			params.put("opSuffix", (suffix==null)?"":suffix);
			
			// 获得 op_scope ， 许可经营范围
			opScope = getOpScope(jo,opScope);
			params.put("opScope", (opScope==null)?"":opScope.toString());
			
			// 获得 pt_bus_scope，普通经营范围
			ptScope =  getPtScope(jo,ptScope);
			params.put("ptBusScope", (ptScope==null)?"":ptScope.toString());
			
			// 获得 customScope
			customScope = jo.getString("customScope");
			params.put("opCustomScope", (customScope==null)?"":customScope);
			
			return params;
		}
		
		if("3".equals(type)){
			String customScope = "";
			
			// 获得 customScope
			customScope = jo.getString("customScope");
			params.put("opCustomScope", (customScope==null)?"":customScope);
			
			return params;
		}
		
		throw new EBaicException("type不正确，请刷新后重试。");
	}
	/**
	 * 获取普通经营范围。
	 * 
	 * @param jo
	 * @param ptScope
	 * @return
	 */
	private StringBuffer getPtScope(JSONObject jo, StringBuffer sb) {
		if(sb==null){
			sb = new StringBuffer();
		}
		if(jo==null){
			return sb;
		}
		JSONArray jaPtScope = jo.getJSONArray("scope");
		if(jaPtScope==null || jaPtScope.isEmpty()){
			return sb;
		}
		
		//StringBuffer group = new StringBuffer();
		int groupSize = jaPtScope.size();
		for(int groupIdx = 0;	groupIdx< groupSize;	++groupIdx){
			// 每个元素是一个group，依然是个数组。
			JSONArray jaGroup = jaPtScope.getJSONArray(groupIdx);
			
			boolean first = true;
			if(jaGroup==null || jaGroup.isEmpty()){
				continue;
			}
			StringBuffer group = new StringBuffer();
			for(int i=0,len = jaGroup.size();i<len;++i){
				JSONObject joTag = jaGroup.getJSONObject(i);
				String tag = joTag.getString("name");
				if(StringUtil.isNotBlank(tag)){
					if(StringUtil.isBlank(tag)){
						continue;
					}
					if(first){
						first = false;
					}else{
						group.append("、");
					}
					if(group.indexOf("销售")>-1 && tag.indexOf("销售")>-1){
						tag = tag.substring(2);
					}
					tag = tag.trim();
					group.append(tag);
					
				}
			}// end of one loop
			sb.append(group.toString());
			
			
			// 如果是最后一条，拼句号，否则是分号
			sb.append("；");
			
		}// end of groups loop
		if(StringUtil.isNotBlank(sb.toString())){
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append("。");
		
		return sb;
	}

	/**
	 * 获得许可经营范围。
	 * 
	 * @param jo
	 * @param sb
	 * @return
	 */
	private StringBuffer getOpScope(JSONObject jo , StringBuffer sb){
		if(sb==null){
			sb = new StringBuffer();
		}
		if(jo==null){
			return sb;
		}
		
		JSONArray jaOpScope = jo.getJSONArray("license");
		if(jaOpScope==null || jaOpScope.isEmpty()){
			return sb;
		}
		
		// [{"name":"废弃电器电子产品处理","code":"2"},{"name":"国际船舶运输","code":"3"}]
		boolean first = true;
		for(int i=0,len = jaOpScope.size();i<len;++i){
			JSONObject joLicense = jaOpScope.getJSONObject(i);
			String license = joLicense.getString("name");
			//String code = joLicense.getString("code");
			if(StringUtil.isNotBlank(license)){
				if(first){
					first = false;
				}else{
					sb.append("；");
				}
				sb.append(license);
				//FIXME 增加许可经营范围 ，向be_wk_license插入记录
			}
		}
		sb.append("。");
		
		return sb;
	}
	
	//判断当前状态是否是退回修改
	public boolean hasStateOfBacked(String gid){
		String sql = " select count(1) from be_wk_reqprocess r where r.gid = ? ";
		List<String> params = new ArrayList<String>();
		params.add(gid);
		Long cnt = DaoUtil.getInstance().queryForOneLong(sql, params);
		boolean flag = false;
		if(cnt > 0){
			flag = true;
		}
		return flag;
	}
}
