package com.gwssi.common.rodimus.report.expr.func;

import java.util.HashMap;
import java.util.Map;

import com.gwssi.common.rodimus.report.util.StringUtil;

/**
 * <h2>获得统计变量信息</h2>
 * 
 * <p>获得统计变量信息，包括：数据库字段名(field)、码表(dict)、页面显示文字(label)</p>
 * <p>要求用到的表中在整个系统中有唯一的后缀。</p>
 * 
 * @author liuhailong
 */
public class GetStatType extends BaseFunction {

	public static Map<String, Object> fieldsMap = new HashMap<String, Object>();
	
	static {
		init();
	}
	public static void init(){
		if(fieldsMap==null){
			fieldsMap = new HashMap<String, Object>();
		}
		fieldsMap.clear();
		Map<String, String> authMap = new HashMap<String, String>(); 
		authMap.put("field", "reg_org");
		authMap.put("dict", "CA11");
		authMap.put("label", "审核机关");
		fieldsMap.put("stat_type_regOrg", authMap);
		
		Map<String, String> busiMap = new HashMap<String, String>(); 
		busiMap.put("field", "operation_type");
		busiMap.put("dict", "CD01");
		busiMap.put("label", "业务类型");
		fieldsMap.put("stat_type_busiType", busiMap);
		
		Map<String, String> entTypeMap = new HashMap<String, String>(); 
		entTypeMap.put("field", "ent_type");
		entTypeMap.put("dict", "CA16");
		entTypeMap.put("label", "企业类型");
		fieldsMap.put("stat_type_entType", entTypeMap);
	}
	
	@Override
	public Object run(Object... args) {
		if(args==null || args.length<1){
			return new HashMap<String, String>();
		}
		String key = StringUtil.safe2String(args[0]);
		Object ret = fieldsMap.get(key);
		return ret;
	}

}
