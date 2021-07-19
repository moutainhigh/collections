package com.gwssi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class OtherSelectCacheUtile {
	
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
		if("blackent".equals(economicproperty)){//黑牌企业基本信息
			getBlackEnt();			
		}else if("diaoxiao".equals(economicproperty)){//黑牌企业吊销信息
			getBlackEntDiaoxiao();			
		}else if("YR".equals(economicproperty)){//一人企业基本信息
			getYREnt();			
		}
		return sortMap;
	}
	
	private static void getYREnt() {
		sortMap.put("企业机构名称","entname");
		sortMap.put("注册号","regno");
		sortMap.put("法定代表人名称","name");
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","cerno");
		sortMap.put("投资人姓名","inv");
		sortMap.put("投资人证件类型","certypeInv");
		sortMap.put("投资人证件号码","cernoInv");
		sortMap.put("企业状态","entstatus");
		sortMap.put("币种","regcapcur");
		sortMap.put("住所","dom");
		sortMap.put("住所所在行政区划","domdistrict");
		sortMap.put("行业代码","industryco");
		sortMap.put("注册资金","regcap");
		sortMap.put("实收资本","reccap");
		sortMap.put("经营范围","opscope");
		sortMap.put("经营范围及方式","opscoandform");
		sortMap.put("许可经营项目","abuitem");
		sortMap.put("一般经营项目","cbuitem");
		sortMap.put("成立日期","esdate");
		sortMap.put("核准日期","apprdate");	
		sortMap.put("经营(驻在)期限自","opfrom");	
		sortMap.put("经营(驻在)期限至","opto");	
		
		sortMap.put("企业类型","enttype");
		sortMap.put("企业分类","entcat");
		sortMap.put("信用等级","credlevel");
		sortMap.put("登记机关","regorg");
		sortMap.put("联系电话","tel");
		sortMap.put("备注","remark");
		sortMap.put("投资人备注","remarkInv");
		//sortMap.put("标识位","sExtValidflag");
		sortMap.put("数据来源","sExtNodenum");
	}

	private static void getBlackEntDiaoxiao() {
		sortMap.put("吊销日期","revdate");
		sortMap.put("吊销依据","revbasis");
		sortMap.put("是否注销","iscan");
		sortMap.put("吊销原因","illegact");
		/*sortMap.put("标识位","sExtValidflag");*/
		sortMap.put("数据来源","sExtNodenum");		
	}

	private static void getBlackEnt() {
		sortMap.put("企业（机构）名称","entname");
		sortMap.put("注册号","regno");
		sortMap.put("法定代表人名称","name");
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","cerno");
		sortMap.put("企业状态","entstatus");
		sortMap.put("住所","dom");
		sortMap.put("住所所在行政区划","domdistrict");
		sortMap.put("注册资金.","regcap");
		sortMap.put("实收资本","reccap");
		sortMap.put("注册资本币种","regcapcur");
		sortMap.put("经营范围","opscope");
		sortMap.put("经营范围及方式","opscoandform");
		sortMap.put("许可经营项目","abuitem");
		sortMap.put("一般经营项目","cbuitem");
		sortMap.put("登记机关","regorg");
		sortMap.put("成立日期","esdate");
		sortMap.put("核准日期","apprdate");
		sortMap.put("行业代码","industryco");
		sortMap.put("经营(驻在)期限自","opfrom");
		sortMap.put("经营(驻在)期限至","opto");
		
		sortMap.put("企业机构类型","enttype");
		sortMap.put("企业分类","entcat");
		sortMap.put("联系电话","tel");
		sortMap.put("信用等级","credlevel");
		sortMap.put("吊销日期","revdate");
		sortMap.put("吊销原因","illegact");
		sortMap.put("备注字段","remark");
	//	sortMap.put("标识位","sExtValidflag");
		sortMap.put("数据来源","sExtNodenum");
		
		
	}


}
