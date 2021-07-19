package com.gwssi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class FoodCacheUtile {
	
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
		if("1".equals(flag) || "3".equals(flag)){
			getFoodEntBasic();			
		}else if("11".equals(flag) || "31".equals(flag)){
			getFoodLicBasic(flag);
		}else if("4".equals(flag)){
			getFoodOldEntBasic();
		}else if("5".equals(flag)){
			getFoodLTEntBasic();
		}else if("51".equals(flag)){
			getFoodLTLicBasic();
		}else if("6".equals(flag)){
			getFoodCYFWBasic();
		}
		return sortMap;
	}
	

	/**
	 * 食品经营许可证的企业基本信息
	 */
	private static void getFoodEntBasic() {
		sortMap.put("企业（机构）名称","entName");
		sortMap.put("注册号","entRegNo");
		sortMap.put("统一社会信用代码","entScCode");
		sortMap.put("经济性质","financialNature");
		sortMap.put("法定代表人","legalPresent");
		sortMap.put("成立日期","applyDate");
		sortMap.put("住所","address");
		sortMap.put("住所省名称","addressProvinceName");
		sortMap.put("住所市名称","addressCityName");
		sortMap.put("住所县名称","addressCountyName");
		sortMap.put("住所街道名称","addressStreetName");
		sortMap.put("住所详细地址","addressDetailName");
		sortMap.put("住所房屋编码","addressHouseCode");
		sortMap.put("企业状态","companyState");		
	}

	/**
	 * 食品经营许可证基本信息
	 */
	private static void getFoodLicBasic(String flag){
		sortMap.put("单位名称","entName");
		sortMap.put("许可证编号","licenseNo");
		sortMap.put("法定代表人","legalPresentName");
		sortMap.put("许可证状态","licenseState");
		sortMap.put("发证日期","certificationDate");
		sortMap.put("发证机关","certificationOrganName");
		sortMap.put("有效期限自","validityFrom");
		sortMap.put("有效期限至","validityTo");
		sortMap.put("签发人名称","signerName");
		sortMap.put("经营项目名称","engageProject");
		if("11".equals(flag)){
			sortMap.put("经营场所","engagePlace");
			sortMap.put("经营场所省名称","engagePlaceProvinceName");		
			sortMap.put("经营场所市名称","engagePlaceCityName");
			sortMap.put("经营场所县名称","engagePlaceCountyName");
			sortMap.put("经营场所街道名称","engagePlaceStreetName");
			sortMap.put("经营场所详细地址","engagePlaceDetailName");
		}else if("31".equals(flag)){
			sortMap.put("生产场所","engagePlace");
			sortMap.put("生产场所省名称","engagePlaceProvinceName");		
			sortMap.put("生产场所市名称","engagePlaceCityName");
			sortMap.put("生产场所县名称","engagePlaceCountyName");
			sortMap.put("生产场所街道名称","engagePlaceStreetName");
			sortMap.put("生产场所详细地址","engagePlaceDetailName");
		}
		sortMap.put("住所房屋编码","addressHouseCode");
		sortMap.put("日常监督管理机构名称","inspectOrganName");
		sortMap.put("日常监督管理人员名称","inspectUserName");
		sortMap.put("投诉举报电话","complaintPhone");
		sortMap.put("申请日期","newtaskDate");
		sortMap.put("决定日期","judgeDate");
		if("11".equals(flag)){
			sortMap.put("主体业态","mainFormatJy");
		}else if("31".equals(flag)){
			sortMap.put("食品、食品添加剂","mainFormatSc");
		}
		sortMap.put("主体业态分类名称或主导类别名称","mainFormatName");		
		sortMap.put("是否申请网络经营","applyWebEngage");
		sortMap.put("是否申请建立中央厨房","applyCenterKitchen");
		sortMap.put("是否申请集体用餐配送","applyGroupDelivery");
		sortMap.put("副本数","duplicateNum");
		sortMap.put("职工人数","staffNum");
		sortMap.put("应体检人数","checkPersonNum");
		sortMap.put("就餐座位数","repastNum");
		sortMap.put("邮政编码","zipCode");
		sortMap.put("电子邮件","email");
		sortMap.put("利用自动售货设备从事食品销售","foodsellMachineSelf");
		sortMap.put("最大就餐人数","maxDinningPerson");
		sortMap.put("外设仓库地址","outsideWarehouseAddress");		
		sortMap.put("是否连锁企业","chainEnterprise");
		sortMap.put("是否直通车企业","trainEnterprise");
		sortMap.put("本市门店数","storeCount");
		sortMap.put("年营业额(单位：万元)","annualTurnover");
	}
	
	/**
	 * 食品生产许可证（旧）企业基本信息
	 */
	private static void getFoodOldEntBasic() {
		sortMap.put("单位名称","applicantName");
		sortMap.put("许可证号","spscLicenseNo");
		sortMap.put("生产场所","productAddr");
		sortMap.put("企业住所","entLocateAddr");
		sortMap.put("法定代表人或负责人","legalPerson");		
		sortMap.put("发证日期","issueDate");
		sortMap.put("有效期","validDate");
		sortMap.put("申请申证单元","categoryUnit");
		sortMap.put("核准申证单元","hzhszdy");
		sortMap.put("联系人","contactPerson");
		sortMap.put("联系电话","contactTel");
		sortMap.put("手机","contactMobile");
		sortMap.put("接收人员","inputPerson");
		sortMap.put("接收日期","inputDate");		
		sortMap.put("资料审查人员","zlscPerson");
		sortMap.put("资料审查日期","zlscDate");
		sortMap.put("资料审查意见","zlscOpinion");
		sortMap.put("受理人员","acceptPerson");
		sortMap.put("受理日期","acceptDate");
		sortMap.put("受理意见","acceptOpinion");
		sortMap.put("现场核查开始时间","xchcPlanDate");
		sortMap.put("现场核查结束时间","xchc_endDate");
		sortMap.put("现场核查结论","investigateConclusion");		
		sortMap.put("报批人","jbrPerson");
		sortMap.put("报批日期","jbrDate");
		sortMap.put("报批意见","jbrOpinion");
		sortMap.put("经办人","chushenPerson");
		sortMap.put("经办日期","chushenDate");
		sortMap.put("经办意见","chushenOpinion");
		sortMap.put("经办退回","htyj");
		sortMap.put("初审人员","csPerson");
		sortMap.put("初审日期","csDate");		
		sortMap.put("初审意见","csOpinion");
		sortMap.put("审核人员","auditPerson");
		sortMap.put("审核日期","auditDate");
		sortMap.put("审核意见","auditOpinion");
		sortMap.put("审批人员","checkPerson");
		sortMap.put("审批日期","checkDate");
		sortMap.put("审批意见","checkOpinion");
	}
	
	/**
	 * 食品流通许可证的企业基本信息
	 */
	private static void getFoodLTEntBasic() {
		sortMap.put("单位名称","companyName");
		sortMap.put("许可证号","spltLicenseNo");
		sortMap.put("经营场所","businessPlace");
		sortMap.put("经营方式","operateMode");
		sortMap.put("法定代表人（负责人）","responsiblePerson");
		sortMap.put("许可范围","operationType");
		sortMap.put("发证日期","issueDate");
		sortMap.put("有效期限","applicantDate");
		sortMap.put("主体类型","bodyType");
		sortMap.put("注册号","nameRegistNo");
		sortMap.put("正本数","originalAmount");
		sortMap.put("副本数","copiesAmount");
		sortMap.put("申请人名称","applicantName");
		sortMap.put("手机","mobile");			
		sortMap.put("联系电话","contactTel");
		sortMap.put("行政区划","areaDivision");
		sortMap.put("所属管辖区域","underJurisdiction");
		sortMap.put("建筑面积","buildArea");
		sortMap.put("使用方式","useWay");
		sortMap.put("备注","remark");
	}
	/**
	 * 食品流通许可证信息
	 */
	private static void getFoodLTLicBasic() {
		sortMap.put("单位名称","companyName");
		sortMap.put("许可证号","spltLicenseNo");
		sortMap.put("正本序号","zbNo");
		sortMap.put("副本序号","fbNo");
	}
	
	/**
	 * 餐饮服务企业基本信息
	 */
	private static void getFoodCYFWBasic() {
		sortMap.put("单位名称","unitName");
		sortMap.put("许可证号","certificateNo");
		sortMap.put("单位地址","fullAddress");
		sortMap.put("法定代表人","legalResponse");
		sortMap.put("法定代表人手机号码","legalMobile");
		sortMap.put("联系人","contactMan");
		sortMap.put("联系人手机号码","contactMobile");
		sortMap.put("类别","category");
		sortMap.put("发证日期","issueDate");
		sortMap.put("有效期限至","toEffectLimit");
		sortMap.put("经济性质","bodyType");
		sortMap.put("经营场所面积（m2）","buildArea");
		sortMap.put("单位地址所在街道","fullAddressStret");
		sortMap.put("单位地址所在区","fullAddressDistrict");
		sortMap.put("备注","remark");	
	}
}
