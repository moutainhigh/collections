package com.gwssi.common.rodimus.report.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class DictUtil {
	
	protected static Map<String, Map<String, String>> dictMaps = new HashMap<String, Map<String,String>>();
	
	public static String trans(String typeId,String codeValue){
		if(StringUtils.isEmpty(typeId) || StringUtils.isEmpty(codeValue)){
			return "";
		}
		typeId = typeId.trim();
		codeValue = codeValue.trim();
		
		String codeName = "";
		Map<String, String> dictMap = dictMaps.get(typeId);
		if(dictMap==null){
			String sql = "select v.code_value as value,v.code_name as text from sysmgr_cvalue v where v.type_id_fk = ?";
			List<Map<String, Object>> list = DaoUtil.getDictInstance().queryForList(sql,typeId);
			dictMap = new HashMap<String, String>();
			if(list!=null && !list.isEmpty()){
				String text,value;
				for(Map<String, Object> row:list){
					text = (String)row.get("text");
					value = (String)row.get("value");
					dictMap.put(value, text);
				}
				if(dictMap!=null && !dictMap.isEmpty()){
					dictMaps.put(typeId, dictMap);
				}
			}
		}
		codeName = dictMap.get(codeValue);
		if(StringUtils.isEmpty(codeName)){
			codeName = "";
		}
		return codeName;
	}
}
