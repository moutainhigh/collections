package com.gwssi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CaseCacheUtile {
	
	private static Map<String,Object> sortMap = new LinkedHashMap<String,Object>();
	
	static{
		sortMap = new LinkedHashMap<String,Object>();
	}
	
	/**
	 * 获取基本信息显示（以后要给成从缓存中读取，而内容需要放在文件的方便管理）
	 * @param flag
	 * @param type
	 * @return
	 */
	public static Map<String,Object> getLinkedHashMap(String flag) {
		String economicproperty = flag;
		if("0".equals(economicproperty)){//案件信息
			getCaseBasic();
			
		}else if("1".equals(economicproperty)){//简易案件信息
			getSimpleCaseBasic();
		}
		return sortMap;
	}
	

	/**
	 * 案件所对应的基本信息
	 */
	private static void getCaseBasic() {
		sortMap.put("案件记录ID","pripid");
		sortMap.put("案件编号","caseno");
		sortMap.put("案件名称","casename");
		sortMap.put("案发地所在行政区划","casescedistrict");
		sortMap.put("案发地","casespot");
		sortMap.put("案发时间","casetime");
		sortMap.put("案由","casereason");
		sortMap.put("案值","caseval");
		sortMap.put("案发地","casespot");
		sortMap.put("适用程序","appprocedure");
		sortMap.put("是否利用网络","caseinternetsign");
		sortMap.put("是否涉外案件","caseforsign");
		sortMap.put("案件状态","casestate");
		sortMap.put("立案机关","casefiauth");
		sortMap.put("立案日期","casefidate");
		sortMap.put("执行日期","exedate");
		sortMap.put("执行类别","exesort");
		sortMap.put("未执行原因类别","unexereasort");
		sortMap.put("案件结果","caseresult");
		sortMap.put("办案机构","casedep");
		sortMap.put("销案理由","clocaserea");
		sortMap.put("销案日期","clocasedate");
		sortMap.put("数据汇总单位","s_ext_fromnode");		
	}

	/**
	 * 简易案件所对应的基本信息
	 */
	private static void getSimpleCaseBasic(){
		sortMap.put("案件记录ID","pripid");
		sortMap.put("案件编号","caseno");
		sortMap.put("案件名称","casename");
		sortMap.put("案发地所在行政区划","casescedistrict");
		sortMap.put("案发地","casespot");
		sortMap.put("案发时间","casetime");
		sortMap.put("案由","casereason");
		sortMap.put("案值","caseval");
		sortMap.put("适用程序","appprocedure");
		sortMap.put("办案机关","casefiauth");
		sortMap.put("办案日期","casefidate");
		sortMap.put("办案机构","casedep");
		//sortMap.put("数据汇总单位","s_ext_fromnode");	
	}
	
	
}
