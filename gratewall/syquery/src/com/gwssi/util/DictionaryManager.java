package com.gwssi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;


@Service("dictionaryManager")
public class DictionaryManager extends BaseService{

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List transferDict(List<Map> listData,String[] columns,String[] dicts ) throws OptimusException{
	
		Map<String, Map<String, String>> codeMap=new HashMap<String, Map<String,String>>();
		
		IPersistenceDAO dao = getPersistenceDAO("db_dc");
		StringBuffer sql = new StringBuffer("select standard_codeindex as cind,codeindex_code as dcode, codeindex_value as dvalue from dc_standard_codedata ");
		sql.append("where standard_codeindex in(");
		List<String> param=new ArrayList<String>();
		for (int i = 0; i < dicts.length; i++) {
			sql.append("?,");
			param.add(dicts[i]);
		}
		String sqls=sql.substring(0, sql.length()-1);
		//System.out.println("------->"+sqls+")");
		List<Map> listCode=dao.queryForList(sqls+")", param);
		//System.out.println("----listCode--->"+listCode.size());
		Map<String, String> dictMap=null;
		for (int i = 0; i < listCode.size(); i++) {
			Map<String, String> code=listCode.get(i);
			String codeIndex=code.get("cind");
			if (!codeMap.containsKey(codeIndex)){
				if (null==dictMap) {
					dictMap =new HashMap<String, String>();
				}else {
					codeMap.put(codeIndex, dictMap);
					dictMap.clear();
				}
				dictMap.put(code.get("dcode"), code.get("dvalue"));
			}else{
				dictMap.put(code.get("dcode"), code.get("dvalue"));
			}
		}
		for (int i = 0; i < listData.size(); i++) {
			Map<String, Object> data=listData.get(i);
			String type = (String)listData.get(i).get("enttype");
			for (int j = 0; j < columns.length; j++) {
				String trsd=transData(codeMap,dicts[j],data.get(columns[j]));
				data.put(columns[j], trsd);
				data.put("type", type.trim());
			}
		}
		return listData;
	}
	
	private String transData(Map<String, Map<String, String>> codeMap,String index,Object key){
		if (key==null) {
			return "";
		}else {
			if (null == codeMap.get(index)) {
				return "";
			}else {
				Map<String, String> valueMap= codeMap.get(index);
				String k=key.toString().trim();
				return valueMap.get(k)==null ? k : valueMap.get(k).toString();
			}
		}
	}
	
	
}
