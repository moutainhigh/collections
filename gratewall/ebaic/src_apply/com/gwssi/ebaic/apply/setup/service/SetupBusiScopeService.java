package com.gwssi.ebaic.apply.setup.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("setupBusiScopeService")
public class SetupBusiScopeService {

	/**
	 * @param gid
	 * @return
	 */
	public Map<String, Object> getTags(String gid) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		// all groups  查询所有经营项目大类
		String sql = "select * from (select t.*,rownum as sn from (select d.dm as code, d.wb as name,d.bz as expr,xh from t_pt_dmsjb d where d.dmb_id = 'BE_SCOPE' and d.qy_bj='1' and d.fdm is null order by d.xh asc) t)t where sn < 7";
		List<Map<String,Object>> groups = DaoUtil.getInstance().queryForList(sql);
		ret.put("groups", groups);
		
		// tags
		sql = "select d.fdm as group_code, d.dm as code, d.wb as name,d.xh as sn from t_pt_dmsjb d where d.dmb_id = 'BE_SCOPE' and d.qy_bj='1' and d.fdm is not null order by d.fdm asc, d.xh asc";
		List<Map<String,Object>> allTags = DaoUtil.getInstance().queryForList(sql);
		
		Map<String,List<Map<String,Object>>> tags = new HashMap<String,List<Map<String,Object>>>();
		String code = null;
		for(Map<String,Object> tag : allTags){
			String groupCode = StringUtil.safe2String(tag.get("groupCode"));
			
			List<Map<String,Object>> groupTags = tags.get(groupCode);
			if(groupTags==null){
				groupTags = new ArrayList<Map<String,Object>>();
				tags.put(groupCode, groupTags);
			}
			if(groupTags.size()>=15){
				continue;
			}
			if("2".equals(groupCode)){
				code = (String) tag.get("code");
				if("02006".equals(code) || "02007".equals(code) || "02008".equals(code)){
					continue;
				}
			}
			groupTags.add(tag);
		}
		
		ret.put("tags", tags);
		
		return ret;
	}

	/**
	 * 加载经营范围。
	 * 
	 * @param gid
	 * @return
	 */
	public Map<String, Object> load(String gid) {
		String sql = "select nvl(t.scope_json,'{}') as scope_json,t.business_scope,t.op_scope,t.pt_bus_scope,t.op_suffix,t.op_custom_scope,nvl(t.scope_sign,'0') as  scope_sign,nvl(t.op_scope_type,'6') as op_scope_type, t.main_scope,is_main_scope_standard from be_wk_ent t where t.gid = ?";
		Map<String, Object> ret = DaoUtil.getInstance().queryForRow(sql, gid);
		if(ret==null){
			ret = new HashMap<String, Object>();
		}
		return ret;
	}
	/**
	 * 保存经营范围。 
	 * @param gid
	 * @param scopeJson
	 * @param businessScope
	 */
	public void save(String gid, String scopeJson, String businessScope) {
		// 0. 参数校验
		if(StringUtil.isBlank(gid)){
			throw new EBaicException("批次号不能为空。");
		}
		if(StringUtil.isBlank(scopeJson)){
			throw new EBaicException("经营范围不能为空。");
		}
		if(StringUtil.isBlank(businessScope)){
			throw new EBaicException("经营范围不能为空。");
		}
		JSONObject jo = null;
		try{
			jo = JSONObject.parseObject(scopeJson);
		}catch(Throwable e){
			throw new EBaicException("解析JSON出错："+e.getMessage());
		}
		if(jo==null){
			throw new EBaicException("解析JSON出错。");
		}
		
		
		String type = jo.getString("type");
		if(StringUtil.isBlank(type)){
			type = "2"; // 默认按2解析。
		}
		if("6".equals(type)){ 
			Calendar now = DateUtil.getCurrentTime();
			String mainScope = "";
			String isMainScopeStandard = "" ;
			StringBuffer opScope = new StringBuffer();
			StringBuffer ptScope = new StringBuffer();
			String customScope = "";
			String suffix = "";
			
			// 获得 op_suffix，项下标注
			suffix = jo.getString("suffix");
			if(StringUtil.isBlank(suffix)){
				suffix = "";
			}
			
			JSONObject joMainScope = jo.getJSONObject("mainScope");
			if(joMainScope==null){
				throw new EBaicException("请选择主要经营范围。");
			}
			boolean isStandard = joMainScope.getBooleanValue("isStandard");
			mainScope = joMainScope.getString("text");
			if(StringUtil.isBlank(mainScope)){
				throw new EBaicException("请选择主要经营范围。");
			}
			mainScope = mainScope.trim();
			mainScope += "。";
			if(isStandard){
				isMainScopeStandard = "1";
			}else{
				isMainScopeStandard = "0";
			}
			
			// 获得 op_scope ， 许可经营范围
			opScope = getOpScope(jo,opScope);
			
			// 获得 pt_bus_scope，普通经营范围
			ptScope =  getPtScope(jo,ptScope);
			
			// 获得 customScope
			customScope = jo.getString("customScope");
			if(StringUtil.isBlank(customScope)){
				customScope = "";
			}
			

			String sql = "update be_wk_ent t set t.scope_json = ? , t.business_scope = ?, t.op_scope = ?,pt_bus_scope=?,t.op_suffix=?,t.OP_CUSTOM_SCOPE=?,t.timestamp =?,t.op_scope_type=?,main_scope=?,is_main_scope_standard=? where t.gid = ?";
			DaoUtil.getInstance().execute(sql, scopeJson,businessScope ,opScope.toString(),ptScope.toString(),suffix,customScope,now,type,mainScope,isMainScopeStandard,gid);
			
			return ;
			
		}
		// 主营 + 项下标注 + 许可
		if("1".equals(type)){ 
			// {"type":"1","suffix":"（企业依法","scope":[],"customScope":"","mainScope":{text:"科技信息咨询服务 ",isStandard:true}}
			
			Calendar now = DateUtil.getCurrentTime();
			StringBuffer opScope = new StringBuffer();//许可经营范围
			String suffix = "";
			String ptScope = "";
			String customScope = "";
			
			// 获得 op_suffix，项下标注
			suffix = jo.getString("suffix");
			if(StringUtil.isBlank(suffix)){
				suffix = "";
			}
			
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
			}else{
				//保存在用户自己输入里
				customScope = text;
			}
			
			// 获得 op_scope ， 许可经营范围
			opScope = getOpScope(jo,opScope);
			
			String sql = "update be_wk_ent t set t.scope_json = ? , t.business_scope = ?, t.op_scope = ?,pt_bus_scope=?,t.op_suffix=?,t.OP_CUSTOM_SCOPE=?,t.timestamp =?,t.op_scope_type=? where t.gid = ?";
			DaoUtil.getInstance().execute(sql, scopeJson,businessScope ,opScope.toString(),ptScope,suffix,customScope,now,type,gid);
			return ;
		}
		
		if("2".equals(type)){
			
			Calendar now = DateUtil.getCurrentTime();
			StringBuffer opScope = new StringBuffer();
			StringBuffer ptScope = new StringBuffer();
			String customScope = "";
			String suffix = "";
			
			// 获得 op_suffix，项下标注
			suffix = jo.getString("suffix");
			if(StringUtil.isBlank(suffix)){
				suffix = "";
			}
			
			// 获得 op_scope ， 许可经营范围
			opScope = getOpScope(jo,opScope);
			
			// 获得 pt_bus_scope，普通经营范围
			ptScope =  getPtScope(jo,ptScope);
			
			// 获得 customScope
			customScope = jo.getString("customScope");
			if(StringUtil.isBlank(customScope)){
				customScope = "";
			}
			
			String sql = "update be_wk_ent t set t.scope_json = ? , t.business_scope = ?, t.op_scope = ?,pt_bus_scope=?,t.op_suffix=?,t.OP_CUSTOM_SCOPE=?,t.timestamp =?,t.op_scope_type=? where t.gid = ?";
			DaoUtil.getInstance().execute(sql, scopeJson,businessScope ,opScope.toString(),ptScope.toString(),suffix,customScope,now,type,gid);
			
			return ;
		}
		
		if("3".equals(type)){
			Calendar now = DateUtil.getCurrentTime();
			String customScope = "";
			
			// 获得 customScope
			customScope = jo.getString("customScope");
			if(StringUtil.isBlank(customScope)){
				customScope = "";
			}
			
			String sql = "update be_wk_ent t set t.scope_json = ? , t.business_scope = ?,t.OP_CUSTOM_SCOPE=?,t.timestamp =?,t.op_scope_type=? where t.gid = ?";
			DaoUtil.getInstance().execute(sql, scopeJson, businessScope, customScope, now,type, gid);
			
			return ;
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
		// {
		//"scope":[[{"name":"技术咨询  ","code":"02001","group":"2"},{"name":"技术服务  ","code":"02002","group":"2"},{"name":"合同能源管理","code":"02006","group":"2"}],
		//	[{"name":"销售卫生用品","code":"01020","group":"1"},{"name":"销售纺织品、针织品","code":"01016","group":"1"},{"name":"销售未经加工的干果、坚果","code":"01014","group":"1"}],
		//	[{"name":"企业管理咨询","code":"3002","group":"3"},{"name":"发布广告","code":"3008","group":"3"},{"name":"税务服务","code":"3014","group":"3"},
		//	{"name":"翻译服务","code":"3019","group":"3"},{"name":"商标代理服务","code":"3015","group":"3"}],
		//	null,[{"name":"文艺创作","code":"11002","group":"11"}]],
		// "customScope":"","mainScope":"","license":[{"name":"废弃电器电子产品处理","code":"2"},{"name":"国际船舶运输","code":"3"}]}
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
	/**
	 * @param 
	 * @return
	 */
	public List<Map<String,Object>> compareScope(String customScopeVal){
		List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
		String sql = "select license.lic_metter_memo as name,license.lic_config_id as code,license.lic_anth as anth from sys_license_config license";
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql);
		if(list.isEmpty()&&list.size()<=0){
			throw new EBaicException("查询后置许可经营范围失败。");
		}
		Iterator<Map<String, Object>> iterator = list.iterator();
		while(iterator.hasNext()){
			Map<String, Object> map = iterator.next();
			String name = (String)map.get("name");
			if(customScopeVal.contains(name)){
				ret.add(map);
			}
		}
		return ret;
	}
	
	/**
	 * @return 主营范围候选。
	 */
	public List<Map<String,Object>> getMainScope(){
		String sql = "select d.dm as value , d.wb as text from t_pt_dmsjb d where d.dmb_id = 'BE_SCOPE' and d.dm in ('02005','02004','02003','02002','02001') order by d.xh asc";
		List<Map<String,Object>> ret = DaoUtil.getInstance().pageQueryForList(sql);
		return ret;
	}
}
