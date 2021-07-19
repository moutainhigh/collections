package com.gwssi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheUtile {
	
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
		if("3".equals(economicproperty)){//外资信息
			
			getWZNZBasic();
			getWaiZi();
			
		}else if("2".equals(economicproperty)){//内资
			getWZNZBasic();
			getNeiZi();
		}else if("1".equals(economicproperty)){//个体
			getGeTi();
			//getGeTiOper();
		}else if("4".equals(economicproperty)) {//集团
			getJITUAN();
		}else if("1001".equals(economicproperty)){//个体经营者
			getGeTiOper();
		}
		return sortMap;
	}
	

	/**
	 * 集团所对应的基本信息--GROUPENTINFO
	 */
	private static void getJITUAN() {
		sortMap.put("主体身份代码","pripid");
		sortMap.put("统一社会信用代码","uniscid");
		sortMap.put("集团名称","grpname");
		sortMap.put("集团简称","grpshform");
		sortMap.put("集团登记证号","grpregcno");
		sortMap.put("成立日期","estdate");
		sortMap.put("核准日期","appedate");
		sortMap.put("登记机关","regorg");
		sortMap.put("母公司主体身份代码","parcomid");
		sortMap.put("母公司名称","pcomname");
		sortMap.put("母公司注册号","pcomregno");
		sortMap.put("母公司和子公司注册资本总和","pcomandscomcapsum");
		sortMap.put("母公司住所","dom");
		sortMap.put("登记状态","opstate");
		sortMap.put("市场主体类型","enttype");
/*		sortMap.put("数据汇总单位","sExtFromnode");
		sortMap.put("数据汇总时间","sExtDatatime");*/
	}

	/**
	 * 对应表 E_BASEINFO 显示内容 外资和内资用到
	 */
	private static void getWZNZBasic(){
		sortMap.put("企业(机构)名称","entname");
		sortMap.put("主体身份代码","pripid");
		sortMap.put("统一社会信用代码","uniscid");	
		sortMap.put("注册号","regno");
		sortMap.put("市场主体类型","enttype");
		//sortMap.put("市场主体类型（中文名称）","enttypeCn");
		sortMap.put("行业门类","industryphy");
		sortMap.put("行业代码","industryco");
		sortMap.put("成立日期","estdate");
		sortMap.put("登记机关","regorg");
		//sortMap.put("登记机关（中文名称）","regorgCn");
		sortMap.put("业务范围类型","opscotype");
		//sortMap.put("业务范围类型（中文名称）","opscotypeCn");
		sortMap.put("经营范围","opscope");
		sortMap.put("经营(驻在)期限自","opfrom");
		sortMap.put("经营(驻在)期限至","opto");
		sortMap.put("登记状态","regstate");
		//sortMap.put("登记状态（中文名称）","regstateCn");
		sortMap.put("住所所在行政区划","domdistrict");
		sortMap.put("住所","dom");
		sortMap.put("注册资本(金)","regcap");
		sortMap.put("注册资本(金)币种","regcapcur");
	//	sortMap.put("注册资本(金)币种（中文名称）","regcapcurCn");
		sortMap.put("注册资本(金)折万美元","regcapusd");
		sortMap.put("实收资本","reccap");
		sortMap.put("实收资本折万美元","reccapusd");
		sortMap.put("国别(地区)","country");
		sortMap.put("从业人员/农专成员总数","empnum");
		sortMap.put("是否城镇","town");
		sortMap.put("法定代表人","name");
		sortMap.put("统计企业类型","reporttype");
		sortMap.put("核准日期","apprdate");

	
	}
		/**
		 *内资补充信息--E_DI_SUPL
		 */
	private static void getNeiZi() {
		//sortMap.put("主体身份代码(CA14)","pripid");
		sortMap.put("邮政编码","postalcode");
		sortMap.put("联系电话","tel");
		sortMap.put("电子邮箱","email");
		sortMap.put("属地监管工商所","localadm");
		sortMap.put("生产经营地所在行政区划","yiedistrict");
		sortMap.put("生产经营地","proloc");
		sortMap.put("核算方式","calculationmethod");
		sortMap.put("住所产权","domproright");
		sortMap.put("设立方式","insform");
		sortMap.put("主管部门","depincha");
		sortMap.put("隶属关系","hypotaxis");
		sortMap.put("经营方式","opform");
		sortMap.put("合伙人数","parnum");
		sortMap.put("有限合伙人数","limparnum");
		sortMap.put("合伙方式","parform");
		sortMap.put("执行人数","exenum");
	}
	/**
	 * 个体经营户 E_PB_BASEINFO
	 */
	private static void getGeTi() {
		sortMap.put("字号名称","traname");
		sortMap.put("注册号","regno");
		sortMap.put("主体身份代码","pripid");
		sortMap.put("统一社会信用代码","uniscid");
		sortMap.put("从业人数","empnum");
		sortMap.put("资金数额","fundam");
		sortMap.put("组成形式","compform");
		//sortMap.put("组成形式（中文名称）","compformCn");
		sortMap.put("行业门类","industryphy");
		sortMap.put("行业代码","industryco");
		sortMap.put("注册日期","estdate");
		sortMap.put("登记机关","regorg");
		//sortMap.put("登记机关（中文名称）","regorgCn");
		sortMap.put("经营范围","opscope");
		sortMap.put("经营场所所在行政区划","oplocdistrict");
		sortMap.put("经营场所","oploc");
		sortMap.put("经营(驻在)期限自","opfrom");
		sortMap.put("经营(驻在)期限至","opto");
		sortMap.put("属地监管工商所(CA12)","localadm");
		sortMap.put("登记状态","regstate");
		sortMap.put("登记状态（中文名称）","regstateCn");
		sortMap.put("是否城镇","town");
		sortMap.put("经营者","name");
		sortMap.put("核准日期","apprdate");
/*		sortMap.put("数据汇总单位","sExtFromnode");
		sortMap.put("数据汇总时间","sExtDatatime");*/
	}
	/**
	 * 个体经营者信息 对应表E_PB_OPERATOR
	 */
	private static void getGeTiOper(){
		sortMap.put("人员序号","personid");
		sortMap.put("主体身份代码","pripid");
		sortMap.put("姓名","name");
		sortMap.put("性别","sex");
		sortMap.put("出生日期","natdate");
		sortMap.put("文化程度","litedeg");
		sortMap.put("民族","nation");
		sortMap.put("住所","dom");
		sortMap.put("邮政编码","postalcode");
		sortMap.put("联系电话","tel");
		sortMap.put("政治面貌","polstand");
		sortMap.put("申请前职业状况","occstbeapp");
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","cerno");
		sortMap.put("证件签发日期","cerissdate");
		sortMap.put("经营者所在地区","operarea");
		sortMap.put("证件有效期","cervalper");
		sortMap.put("身份核证文件核(公)证机构(人)","notorg");
		sortMap.put("身份核证文件编号","notdocno");
/*		sortMap.put("数据汇总单位","sExtFromnode");
		sortMap.put("数据汇总时间","sExtDatatime");*/
	}
	

	/**
	 *  外资补充信息--E_FI_SUPL
	 */
	private static void getWaiZi() {
		//sortMap.put("主体身份代码(CA14)","pripid");
		sortMap.put("邮政编码","postalcode");
		sortMap.put("联系电话","tel");
		sortMap.put("电子邮箱","email");
		sortMap.put("属地监管工商所","localadm");
		sortMap.put("生产经营地所在行政区划","yiedistrict");
		sortMap.put("生产经营地","proloc");
		sortMap.put("核算方式","calculationmethod");
		sortMap.put("外资产业代码","forcapindcode");
		sortMap.put("中西部优势产业代码","midpreindcode");
		sortMap.put("实收资本折人民币","reccaprmb");
		sortMap.put("注册资本(金)折人民币","regcaprmb");
		sortMap.put("住所所在经济开发区","ecotecdevzone");
		sortMap.put("项目类型","protype");
		sortMap.put("投资总额","congro");
		sortMap.put("投资总额币种","congrocur");
		sortMap.put("投资总额折万美元","congrousd");
		sortMap.put("中方注册资本(金)","domeregcap");
		sortMap.put("中方注册资本(金)币种(CA04)","domeregcapcur");
		sortMap.put("中方注册资本(金)折万美元","domeregcapusd");
		sortMap.put("中方注册资本(金)出资比例","domeregcapinvprop");
		sortMap.put("中方实收资本","domereccap");
		sortMap.put("中方实收资本币种","domereccapcur");
		sortMap.put("中方实收资本折万美元","domereccapusd");
		sortMap.put("中方实收资本出资比例","domereccapconprop");
		sortMap.put("外方注册资本(金)","forregcap");
		sortMap.put("外方注册资本(金)币种","forregcapcur");
		sortMap.put("外方注册资本(金)折万美元","forregcapusd");
		sortMap.put("外方注册资本(金)出资比例","forregcapinvprop");
		sortMap.put("外方实收资本","forreccap");
		sortMap.put("外方实收资本币种","forreccapcur");
		sortMap.put("外方实收资本折万美元","forreccapusd");
		sortMap.put("外方实收资本出资比例","forreccapconprop");
		sortMap.put("转型日期","chamecdate");
		sortMap.put("设立方式","insform");
		sortMap.put("经营活动类型","opeacttype");
		sortMap.put("承包工程或经营管理项目","itemofoporcpro");
		sortMap.put("承包工程或经营管理内容","conofcontrpro");
		sortMap.put("主管部门","depincha");
		sortMap.put("审批机关","exaauth");
		sortMap.put("批准日期","sandate");
		sortMap.put("外国(地区)企业名称","forentname");
		sortMap.put("境外住所","fordom");
		sortMap.put("境外注册资本","forregecap");
		sortMap.put("境外经营范围","foropscope");
		sortMap.put("批准文号","sandocno");
		sortMap.put("外国(地区)企业外文名称","forentforname");
		sortMap.put("外国(地区)企业经营起始日期","forentopfrom");
		sortMap.put("外国(地区)企业经营截止日期","forentopto");
		sortMap.put("外国(地区)企业有权签字人","forentautsign");
		sortMap.put("外国(地区)企业责任形式","forenliafor");
		sortMap.put("外国(地区)企业资本(资产)币种","forentcapcur");
		sortMap.put("合伙人数","parnum");
		sortMap.put("有限合伙人数","limparnum");
		sortMap.put("合伙方式","parform");
		sortMap.put("执行人数","exenum");
/*		sortMap.put("数据汇总单位","sExtFromnode");
		sortMap.put("数据汇总时间","sExtDatatime");*/

	}
}
