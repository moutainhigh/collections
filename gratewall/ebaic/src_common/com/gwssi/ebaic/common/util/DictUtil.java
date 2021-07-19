package com.gwssi.ebaic.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.gwssi.optimus.core.cache.dictionary.DicData;
import com.gwssi.optimus.core.cache.dictionary.DictionaryManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * @author liuhailong
 */
public class DictUtil {
	
	/**
	 * 得到页面上可用的下拉框候选项。
	 * 适用于jazz组件。
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getOptionsForJazz(String key , Object ... params){
		String sql = ConfigUtil.get("dictOptions."+key);
		if(StringUtil.isBlank(sql)){
			return "{ data: [ { data:[{text:'',value:''},{text:'',value:''}] } ]}";
		}else{
			List<Map<String,Object>> list = DaoUtil.getInstance().queryForList(sql, params);
			Map<String,Object> retMap = createDicData(list);
			String ret = JSON.toJSONString(retMap);
			return ret;
		}
	}
	
	/**
	 * @param dataList
	 * @return
	 */
	private static Map<String,Object> createDicData(List<Map<String,Object>> dataList) {
		// { data:[{text:'',value:''},{text:'',value:''}] }
		Map<String,Object> dataWraper = new HashMap<String,Object>();
		dataWraper.put("data", dataList);
		
		// [ { data:[{text:'',value:''},{text:'',value:''}] } ]
		List<Object> dataListWraper = new ArrayList<Object>();
		dataListWraper.add(dataWraper);
		
		// { data: 
		//	[ { data:[{text:'',value:''},{text:'',value:''}] } ] 
		// }
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("data", dataListWraper);
		
		return ret;
	}
	
	/**
	 * 
	 * @param dataList
	 * @return
	 */
	public String createDicData(String dmbId, @SuppressWarnings("rawtypes") List<Map> dataList) {
		DicData dicData = new DicData();
		dicData.setDmbId(dmbId);
		dicData.setDataList(dataList);
		String ret = dicData.getJSONData();
		return ret;
//		// { data:[{text:'',value:''},{text:'',value:''}] }
//		Map<String,Object> dataWraper = new HashMap<String,Object>();
//		dataWraper.put("data", dataList);
//		
//		// [ { data:[{text:'',value:''},{text:'',value:''}] } ]
//		List<Object> dataListWraper = new ArrayList<Object>();
//		dataListWraper.add(dataWraper);
//		
//		// { data: 
//		//	[ { data:[{text:'',value:''},{text:'',value:''}] } ] 
//		// }
//		Map<String,Object> ret = new HashMap<String,Object>();
//		ret.put("data", dataListWraper);
//		
//		return ret;
	}
	
	/**
	 * 
	 * @param dmbId
	 * @param value
	 * @return
	 */
	public static String getText(String dmbId,String value){
		DicData dicData = null;
		try {
			dicData = DictionaryManager.getData(dmbId);
		} catch (OptimusException e) {
			throw new RodimusException(e.getMessage(),e);
		}
		if(dicData==null){
			return StringUtil.EMPTY_STRING;
		}
		String ret = dicData.getText(value);
		if(StringUtil.isBlank(ret)){
			return StringUtil.EMPTY_STRING;
		}
		return ret;
	}
}
