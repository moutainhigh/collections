package com.gwssi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CPRSelectCacheUtile {
	
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
		if("CPR".equals(economicproperty)){//信息件
			getCPRInfoWare();			
		}else if("involvedMain".equals(economicproperty)){//涉及主体
			getCPRInvolvedMain();			
		}else if("involvedObject".equals(economicproperty)){//涉及客体
			getCPRInvolvedObject();			
		}else if("infoProvider".equals(economicproperty)){//信息提供方信息
			getInfoProvider();			
		}else if("dispatch".equals(economicproperty)){//分流信息
			getCPRDispatch();			
		}else if("investigation".equals(economicproperty)){//调查信息
			getCPRInvestigation();			
		}else if("mediation".equals(economicproperty)){//调解信息
			getCPRMediation();
		}else if("feedback".equals(economicproperty)){//反馈信息
			getCPRFeedback();
		}
		return sortMap;
	}

	private static void getCPRInfoWare() {
		sortMap.put("登记编号","regino");
		sortMap.put("信息件类型","inftype");
		sortMap.put("信息来源","infoori");
		sortMap.put("接收方式","incform");
		sortMap.put("登记部门","regdepname");
		sortMap.put("登记部门上级名称","regpardepname");
		sortMap.put("登记人","regperson");
		sortMap.put("登记时间","regtime");
		
		sortMap.put("处理部门","handepname");
		sortMap.put("处理部门上级名称","hanpardepname");
		sortMap.put("处理人名称","handle");
		sortMap.put("来电号码/来访人数","inccall");
		sortMap.put("被叫号码","calnum");
		sortMap.put("事件级别","evegrade");
		sortMap.put("简要内容","content");
		sortMap.put("更多内容","contentmore");
		
		sortMap.put("要求回复","reqreply");
		sortMap.put("要求回复时间","reqreplydate");
		sortMap.put("难点问题标志","difficult");
		sortMap.put("是否重复信息","duplicate");
		sortMap.put("是否要求保密","secrequired");
		sortMap.put("基本问题","applbasque");
		sortMap.put("关键字","keyword");
		sortMap.put("批准延时天数","delay");
		sortMap.put("受理时间","accepttime");
		sortMap.put("归档时间","archievetime");
		sortMap.put("办结时间","finishtime");
		sortMap.put("第一次反馈时间","firinvtime");
		
		sortMap.put("转移区域","transferdivision");
		
		sortMap.put("转移目标","transfertarget");
		sortMap.put("未回复的督办数","norepsupervision");
		sortMap.put("最后流程步骤","findistype");
		sortMap.put("办结审批人ID","cloappuserid");
		sortMap.put("办结审批人姓名","cloappusername");
		sortMap.put("转发服务站状态","transferstate");
		sortMap.put("消委会数据同步状态","xftstatus");
		
		sortMap.put("是否公开","hanadvopen");
		sortMap.put("是否关闭公开","hanadvoutopen");
		sortMap.put("主题","subject");
		
		sortMap.put("消委会登记单号","xftregisterno");
		sortMap.put("分单号","disno");
		/*sortMap.put("版本","version");*/
		sortMap.put("是否热点举报","ishotinform");
		sortMap.put("是否有分单","isdis");
		sortMap.put("互联网原文","contents");
	}

	private static void getCPRInvolvedMain() {
		sortMap.put("涉及主体名称","invname");
		sortMap.put("企业类型","enttype");
		sortMap.put("主体类别","pttype");
		sortMap.put("行业类型","tradetype");
		sortMap.put("工商注册登记号","compregno");
		sortMap.put("辖区","division");
		sortMap.put("街道","stretowncode");
		sortMap.put("地址","addr");
		sortMap.put("是否涉及电子商务/是否非现场购物","ebusiness");
		sortMap.put("网站类型/购物类型","websitetype");
		sortMap.put("网址","website");
		sortMap.put("联系人","contact");
		sortMap.put("联系电话","tel");
		
		sortMap.put("基本信息","generalinfo");
		sortMap.put("监管所编号","supdepcode");
		sortMap.put("监管所","supdepname");
		sortMap.put("远程购物类型","remshotype");
	}

	private static void getCPRInvolvedObject() {
		sortMap.put("涉及客体类型","invobjtype");
		sortMap.put("商品进出口标志","exporttype");
		sortMap.put("涉及金额/消费金额","invoam");
		sortMap.put("商品名称","mdsename");
		sortMap.put("品牌/商标","brandname");
		sortMap.put("规格型号","standard");
		sortMap.put("数量","quantity");
		sortMap.put("计量单位","measureunit");
	}
	
	private static void getInfoProvider() {
		sortMap.put("信息提供方类型","revetype");
		sortMap.put("姓名","persname");
		sortMap.put("性别","sex");
		sortMap.put("年龄","age");
		
		sortMap.put("人员身份","peride");
		sortMap.put("人员类别","pertype");
		sortMap.put("残疾人标志","handisign");
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","cerno");
		
		
		sortMap.put("工作单位","workunit");
		sortMap.put("辖区","division");
		sortMap.put("地址","addr");
		sortMap.put("固定电话","landtel");
		sortMap.put("移动电话","mobile");
		sortMap.put("传真","fax");
		sortMap.put("电子邮箱","email");
		sortMap.put("企业类型","enttype");
	}
	
	private static void getCPRDispatch() {
		sortMap.put("分派时间","dispatchtime");
		sortMap.put("分流去向","dispatchtype");
		
//		sortMap.put("分派人","dispatchuserid");
		sortMap.put("分派人名称","dispatchusername");
		sortMap.put("事件级别","evegrade");
	}
	
	private static void getCPRInvestigation() {
		sortMap.put("调查信息ID","investigationid");
		sortMap.put("调查时间","investigatetime");
		sortMap.put("调查地点","invplace");
		
		sortMap.put("被调查人性别","invedsex");
		sortMap.put("被调查人年龄","invedage");
		sortMap.put("单位或地址","invedaddress");
		sortMap.put("调查内容","content");
		
		sortMap.put("调查人编号","invuseid");
		sortMap.put("调查人姓名","invusename");
		sortMap.put("调查部门编号","invdepcode");
		sortMap.put("调查部门简称","invdepname");
		sortMap.put("最后修改人","modifiedby");
		sortMap.put("最后修改时间","lasmodtime");
		
		sortMap.put("被调查人","surman");
	}

	
	private static void getCPRMediation() {
		sortMap.put("调解结束时间","intenddate");
		sortMap.put("调解结果","mediate_result");
		sortMap.put("侵权类型","tortype");
		sortMap.put("未履行义务","defobl");
		sortMap.put("是否欺诈","cheatsign");
		sortMap.put("争议金额","disam");
		sortMap.put("挽回经济损失","redecolos");
		sortMap.put("精神赔偿金额","spiameam");
		sortMap.put("加倍赔偿金额","douameam");
		sortMap.put("政府罚没金额","govconfiscation");
		sortMap.put("行政调解文号","intno");
		sortMap.put("难点问题标志","difficult");
		
		sortMap.put("是否有主题经营资格","mainbodyqualify");
		sortMap.put("是否先行和解","conciliationfirst");
		sortMap.put("是否支持消费者起诉","supportlawsuit");
		sortMap.put("是否产生公函","officialletter");
		
		sortMap.put("消费者是否满意","satisfied");
		sortMap.put("是否锦旗表扬","pennant");
		sortMap.put("是否收到感谢信","thankletter");
		sortMap.put("是否媒体曝光","mediareport");
		
		
	}

	
	private static void getCPRFeedback() {
		sortMap.put("反馈登记人名称","feeregper");
		
		sortMap.put("反馈部门","feedepname");
		sortMap.put("反馈时间","feedbacktime");
		sortMap.put("受理类型","acctype");
		sortMap.put("应急处理进展状态","emeprogress");
		
		sortMap.put("处理方式","handletype");
		sortMap.put("回复方式","replytype");
		sortMap.put("处理意见","handleadvice");
		sortMap.put("转移单位名称","transferorgan");
		sortMap.put("协助单位类型","aidunittype");
		sortMap.put("经办人","tran");
		sortMap.put("办理部门","handdep");
		sortMap.put("是否立案","putoncase");
		sortMap.put("是否回复","replied");
		sortMap.put("信息件类型","infowaretype");
		
		sortMap.put("执行车辆","executecar");
		sortMap.put("执行人数","executeperson");
		
	}



}
