package com.gwssi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CaseSelectCacheUtile {
	
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
		String economicproperty= flag;
		if("case".equals(economicproperty)){//案件基本信息
			getCaseBaseInfo();			
		}else if("2".equals(economicproperty)){//自然人当事人信息
			getNaturalInfo();			
		}else if("1".equals(economicproperty)){//企业当事人信息
			getEnterprisesInfo();			
		}else if("penalize".equals(economicproperty)){//违法行为及处罚信息
			getPenalizeInfo();			
		}
		else if("source".equals(economicproperty)){//案件源信息
			getSourceInfo();			
		}
		else if("move".equals(economicproperty)){//移送信息
			getMoveInfo();			
		}else {
			getNaturalInfo();	
		}
		return sortMap;
	}
	
	private static void getCaseBaseInfo() {
		sortMap.put("案件编号","caseno");
		sortMap.put("案件来源","casesource");
		sortMap.put("当事人名称","litigantname");
	//	sortMap.put("案件名称","casename");
		
		sortMap.put("文书和流程选择","casemodel");
		sortMap.put("案件类型","caseType");
		sortMap.put("案发地","casespot");
		sortMap.put("案情简要","casesummary");
		sortMap.put("案由","casereason");
		sortMap.put("案值","caseval");
		sortMap.put("适用程序","appprocedure");
		sortMap.put("案件状态","casestate");
		sortMap.put("是否利用网络","caseinternetsign");
		sortMap.put("是否涉外案件","caseforsign");
		sortMap.put("立案机关","filedepartment");
		sortMap.put("行政区划","division");
		sortMap.put("承办人","undertaker");
		sortMap.put("办案机构","casedep");
		sortMap.put("案发时间","casetime");
		sortMap.put("立案日期","caseregistertime");
		sortMap.put("执行日期","exedate");
		sortMap.put("结案日期","caseendtime");
	}

	private static void getPenalizeInfo() {
		sortMap.put("当事人名称","litigantname");
		sortMap.put("案件名称","casename");
		sortMap.put("涉嫌违法事实","illegfact");
		sortMap.put("违反法律","violation");
		sortMap.put("违法行为种类","illegacttype");
		sortMap.put("违法程度","sExtNodenum");	
		
		sortMap.put("处罚依据","penbasis");
		sortMap.put("处罚理由","punisreason");
		sortMap.put("处罚种类内容","pentypecontext");
		sortMap.put("处罚决定内容概述","punishcontent");
		sortMap.put("案值","caseval");
		sortMap.put("罚款金额","penam");	
		
		sortMap.put("没收金额","forfam");
		sortMap.put("变价金额","apprcuram");
		sortMap.put("处罚决定书签发日期","pendecissdate");
		sortMap.put("处罚决定书文号","docno");
	}

	private static void getNaturalInfo() {
		sortMap.put("案件名称","casename");
		sortMap.put("姓名","name");
		sortMap.put("性别","sex");
		sortMap.put("年龄","age");
		sortMap.put("职业","occupation");
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","cerno");
		sortMap.put("住所","dom");
		sortMap.put("联系电话","tel");
		sortMap.put("邮政编码","postalcode");
		
	
	}
	
	private static void getEnterprisesInfo() {
		sortMap.put("单位名称","unitname");
		sortMap.put("注册号","regno");
		sortMap.put("法定代表人","lerep");
		
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","cerno");
		sortMap.put("住所","dom");
		sortMap.put("市场主体类型","enttype");
		/*sortMap.put("联系电话","tel");*/
	}
	
	private static void getSourceInfo() {
		sortMap.put("案源来源","casekind");
		sortMap.put("当事人类型","litiganttype");
		sortMap.put("当事人名称","litigantname");
		sortMap.put("当事人证件号","litigantcerno");
		sortMap.put("企业类型","enttype");
		
		
		sortMap.put("案情简要","caseinfo");
		sortMap.put("登记时间","regtime");	
		sortMap.put("承办人","undertaker");
		sortMap.put("部门","department");
		sortMap.put("案源状态","casesourcestate");
		sortMap.put("案源终止时间","casedeadline_end");
		sortMap.put("案源登记内容","regcontent");	
		
		sortMap.put("转办案源","turned");
		sortMap.put("转办线索","turnedclue");
		sortMap.put("案源违法类型","sourcetype");
		sortMap.put("登记人","registerman");
		
		sortMap.put("审批人名称","approvalname");
		sortMap.put("文书和流程选择","casemodel");
		sortMap.put("产品类别","productkind");
		sortMap.put("流通环节","circulation");
		sortMap.put("行政区划","division");
	}
	
	private static void getMoveInfo() {
		sortMap.put("移送接收机关","receiveunit");
		sortMap.put("移送接收人","receiver");
		sortMap.put("移送理由","remarks");
		sortMap.put("移送名义","movename");
		sortMap.put("移送日期","movetime");
		sortMap.put("移送人员","poster");	
		
		sortMap.put("是否有效","abolish");
	}


}
