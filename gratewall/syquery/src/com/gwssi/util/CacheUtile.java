package com.gwssi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheUtile {
	
	private static Map<String,Object> sortMap = new LinkedHashMap<String,Object>();
	
	static{
		sortMap = new LinkedHashMap<String,Object>();
	}
	public static String transcode(String[] str){
		String res = null;
		if(str != null && str.length>0){
			if("0110".equals(str[0])){
				res = "种植养殖类";
			}else if("0120".equals(str[0])){
				res = "食品生产类";
			}else if("0130".equals(str[0])){
				res = "食品经营类";
			}else if("0140".equals(str[0])){
				res = "餐饮类";
			}else if("0190".equals(str[0])){
				res = "其它食品经营类主体";
			}else if("9900".equals(str[0])){
				res = "其它类";
			}
			for(int i=1;i<str.length;i++){
				if("0110".equals(str[0])){
					res = res + "," + "种植养殖类";
				}else if("0120".equals(str[0])){
					res = res + "," + "食品生产类";
				}else if("0130".equals(str[0])){
					res = res + "," + "食品经营类";
				}else if("0140".equals(str[0])){
					res = res + "," + "餐饮类";
				}else if("0190".equals(str[0])){
					res = res + "," + "其它食品经营类主体";
				}else if("9900".equals(str[0])){
					res = res + "," + "其它类";
				}
			}
		}
		
		return res;
	}
	public static String encrypt(String str){
		String result = str;
	/*	if(str != null && str.length()> 3){
			String strsub1 = str.substring(str.length()- 4,str.length());
			result = str.replace(strsub1, "****");
		}*/
		return result;
	}
	/**
	 * 获取基本信息显示（以后要给成从缓存中读取，而内容需要放在文件的方便管理）
	 * @param flag
	 * @param type
	 * @return
	 */
	public static Map<String,Object> getLinkedHashMap(String flag, String opetype, String entstatus) {
		String economicproperty= flag;
		if("3".equals(economicproperty)){//外资信息
			getWaiZi(opetype,entstatus);			
		}else if("2".equals(economicproperty)){//内资
			getNeiZi(opetype,entstatus);
		}else if("1".equals(economicproperty)){//个体
			getGeTi(opetype,entstatus);
		}else if("4".equals(economicproperty)) {//集团
			getJITUAN(opetype,entstatus);
		}else if("1001".equals(economicproperty)){//个体经营者
			getGeTiOper();
		}else if("02".equals(economicproperty)){ //隶属
			getLiShu();
		}else if("03".equals(economicproperty)){ //注销
			getZhuxiao();
		}else if("04".equals(economicproperty)){ //清算
			getQingsuan();
		}else if("05".equals(economicproperty)){ //财务负责人
			getCaiwufuze();
		}else if("06".equals(economicproperty)){ //迁入
			getQianru();
		}else if("07".equals(economicproperty)){ //迁出
			getQianchu();
		}else if("08".equals(economicproperty)){ //办税人员
			getBanshui();
		}else if("09".equals(economicproperty)){ //税务代理人
			getShuiwudaili();
		}else if("10".equals(economicproperty)){ //多证合一
			getDuozheng();
		}else if("11".equals(economicproperty)){ //集团母公司信息
			getMugongsi();
		}else if("12".equals(economicproperty)){ //法定代表人信息
			getDaibiaoren();
		}else if("13".equals(economicproperty)){ //工商联络员信息
			getLianluoyuan();
		}
		return sortMap;
	}
	
	private static void getLianluoyuan() {
		sortMap.put("姓名","name");
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","cerno");
		sortMap.put("固定电话","tel");
		sortMap.put("移动电话","mobel");
		sortMap.put("电子邮箱","email");
		
	}

	private static void getDaibiaoren() {
		sortMap.put("姓名","name");
		sortMap.put("性别","sex");
		sortMap.put("证照类型","certype");
		sortMap.put("证照号码","cerno");
		sortMap.put("英文名","forname");
		sortMap.put("移动电话","mobel");
		sortMap.put("电话","tel");
		sortMap.put("邮政编码","postalcode");
		sortMap.put("电子邮件","email");
		//sortMap.put("出生日期","natdate");
		sortMap.put("文化程度","litedeg");
		sortMap.put("国别（地区）","countrycode");
		sortMap.put("地址","addr");
		sortMap.put("证件有效期","cervalper");
		
	}

	private static void getMugongsi() {
		sortMap.put("母公司名称","entname");
		sortMap.put("统一社会信用代码","unifsocicrediden");
		sortMap.put("注册号","regno");
		sortMap.put("法定代表人","lerep");
		sortMap.put("成立日期","estdate");
		sortMap.put("企业(机构)类型","enttype");
		sortMap.put("认缴注册资本","regcap");
		sortMap.put("住所所在行政区划","domdistrict");
		sortMap.put("经营(驻在)期限自","opfrom");
		sortMap.put("经营(驻在)期限至","opto");
		sortMap.put("住所","dom");
		sortMap.put("币种","currency");
		sortMap.put("国别","countrycode");
		sortMap.put("经营(业务)范围","opscope");
		sortMap.put("联系人","linkman");
		sortMap.put("联系人电话","tel");
		sortMap.put("电子邮件","email");
		
	}

	private static void getGTjingying() {
		sortMap.put("适用会计制度","accosystcode");
		sortMap.put("附属行业明细1","affiindudetaone");
		sortMap.put("附属行业明细3","affiindudetathre");
		
	}

	private static void getDuozheng() {
		/*sortMap.put("适用会计制度","accosystcode");
		sortMap.put("附属行业明细1","affiindudetaone");
		sortMap.put("附属行业明细3","affiindudetathre");
		sortMap.put("附属行业明细2","affiindudetatwo");
		sortMap.put("申请组织机构代码证副本数","agencodecertcopynumb");
		sortMap.put("申请代码证正本数量","agencodecertnumb");
		sortMap.put("申请组织机构数字证书","applorgadigicert");
		sortMap.put("办证点编号","bzdbh");
		sortMap.put("核算方式","calcmethcode");
		sortMap.put("从业人数","empnum");
		sortMap.put("网站地址","entewebs");
		sortMap.put("网站名称","entewebsname");
		sortMap.put("总分机构标志","entisuborgamark");
		sortMap.put("其中外籍人数","forempnum");
		sortMap.put("隶属关系","hypotaxis");
		sortMap.put("小微企业声明","issmalente");
		sortMap.put("法定代表人（负责人）固定电话","landtel");
		sortMap.put("所在市场","marketcode");
		sortMap.put("纳税人名称","nsrmc");
		sortMap.put("生产经营期限止","opertermenddate");
		sortMap.put("生产经营期限起","opertermstardate");
		sortMap.put("总机构企业所得税所属机关","orgabusiincotaxbeloagen");
		sortMap.put("总机构工商注册号码","orgainducommreginumb");
		sortMap.put("生产经营地址","prodbusiaddr");
		sortMap.put("生产经营地联系电话","prodbusiaddrconttele");
		sortMap.put("生产经营地邮政编码","prodbusiaddrpostcode");
		sortMap.put("注册地联系电话","regicontphonnumb");
		sortMap.put("注册地邮政编码","regipostcode");
		sortMap.put("刻章申请","sealtype");
		sortMap.put("是否申请电子印章","sfsqdzyz");
		sortMap.put("个体摊位号","shopbootnumb");
		sortMap.put("其中固定人数","shopownefixenumb");
		sortMap.put("社保联系人联系电话","socisecucontphonnumb");
		sortMap.put("社保联系人","sociseculink");
		sortMap.put("街道乡镇","stretowncode");
		sortMap.put("纳税人名称（英文）","taxpayer");
		sortMap.put("税务登记证副本申请本数","taxregicertcopynumb");
		sortMap.put("使用网站类型","usewebtype");
		sortMap.put("网站负责人邮箱","webpmail");
		sortMap.put("网站负责人名称","webpname");
		sortMap.put("网站负责人手机","webpphone");
		sortMap.put("网站类型","webtype");
		sortMap.put("网站网址","wzwz");*/
		sortMap.put("隶属关系","hypotaxis");
		sortMap.put("适用会计制度","accosystcode");
		sortMap.put("总机构企业所得税所属机关","orgabusiincotaxbeloagen");
		sortMap.put("注册地邮政编码","regipostcode");
		sortMap.put("注册地联系电话","regicontphonnumb");
		sortMap.put("生产经营地","prodbusiaddr");
		sortMap.put("核算方式","calcmethcode");
		sortMap.put("网站网址","wzwz");
		sortMap.put("从业人数","empnum");
		sortMap.put("外籍人数","forempnum");
		sortMap.put("刻章申请","sealtype");
		sortMap.put("是否申请电子刻章","sfsqdzyz");
		sortMap.put("社会联保人","sociseculink");
		sortMap.put("社会联保人电话","socisecucontphonnumb");
		sortMap.put("本企业是否属小型企业或微型企业","issmalente");
		sortMap.put("网站地址","entewebs");
		sortMap.put("网站名称","entewebsname");
		sortMap.put("网站类型","webtype");
		sortMap.put("网站负责人名称","webpname");
		sortMap.put("网站负责人手机","webpphone");
		sortMap.put("网站负责人邮箱","webpmail");
	}

	private static void getShuiwudaili() {
		sortMap.put("税务代理人名称","taxagen");
		sortMap.put("税务代理人联系电话","taxagenconttele");
		sortMap.put("税务代理人电子邮箱","taxagenemai");
		sortMap.put("税务代理人纳税人识别号","taxagenfinachieidencode");
		sortMap.put("分店电话","fddhhm");
		
	}

	private static void getBanshui() {
		sortMap.put("姓名","taxmanname");
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","taxmanidennumb");
		sortMap.put("固定电话","taxmanlandtele");
		sortMap.put("移动电话","taxmanmobitele");
		sortMap.put("电子邮箱","taxmanemail");
		
	}

	private static void getQianchu() {
		sortMap.put("迁出类型","iflocal");
		sortMap.put("迁出日期","moutddate");
		sortMap.put("迁出函号","moutletnum");
		sortMap.put("迁出原因","moutrea");
		sortMap.put("迁入地区","minarea");
		sortMap.put("迁入地省级编号","minareaprovince");
		sortMap.put("迁入地市登记机关","minareregorg");
		sortMap.put("迁出办理人","acpper");
		sortMap.put("迁出办理日期","acptime");
		
	}

	private static void getQianru() {
		sortMap.put("迁入日期","minddate");
		sortMap.put("迁入函号","minletnum");
		sortMap.put("迁入原因","minrea");
		sortMap.put("迁出地区","moutarea");
		sortMap.put("迁出地省级编号","moutareaprovince");
		sortMap.put("迁出地市登记机关","moutareregorg");
		sortMap.put("迁出办理人","acpper");
		sortMap.put("迁出办理日期","acptime");
		
	}

	private static void getCaiwufuze() {
		sortMap.put("财务负责人","finachie");
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","finachieindenumb");
		sortMap.put("固定电话","finachielandtele");
		sortMap.put("移动电话","finachiemobitele");
		sortMap.put("电子邮箱","finachieemai");		
	}

	/**
	 * 清算基本信息--GROUPENTINFO
	 */
	private static void getQingsuan() {
		sortMap.put("债权承接人","claimtranee");
		sortMap.put("债务承接人","debttranee");
		sortMap.put("清算完结日期","ligenddate");
		sortMap.put("清算完结情况","ligst");
		sortMap.put("地址","addr");
		sortMap.put("备注","remark");
		
	}

	/**
	 * 注销基本信息--GROUPENTINFO
	 */
	private static void getZhuxiao() {
		sortMap.put("注销日期","candate");
		sortMap.put("注销原因","canrea");
		sortMap.put("注销文号","candocno");
		sortMap.put("公告日期","pubdate");
		sortMap.put("公告报纸名称","pubnewsname");
		sortMap.put("批准日期","sandate");
		sortMap.put("批准文号","sandocno");
		sortMap.put("批准机关","sanauth");
		sortMap.put("批准证书缴销情况","blicrevcert");
		sortMap.put("公章缴销枚数","carevnum");
		sortMap.put("公章缴销情况","carevst");
		sortMap.put("营业执照缴销副本编号","blicrevdupcono");
		sortMap.put("营业执照缴销副本数","blicrevdupconum");
		sortMap.put("营业执照缴销正本编号","blicrevorino");
		sortMap.put("营业执照缴销正本数","blicrevorinum");
		sortMap.put("分支机构（分公司）注销登记情况","branchcancon");
		sortMap.put("非法人分支注销完毕标志","cancelbrsign");
		sortMap.put("海关税务清理情况","ciqdecle");
		sortMap.put("清税情况","cleantax");
		sortMap.put("海关手续清缴情况","customsettlement");
		sortMap.put("清理债权债务单位","declecomp");
		sortMap.put("债权债务清理情况","declecon");
		sortMap.put("债权债务清理完结标志","declesign");
		sortMap.put("对外投资清理完毕标志","extclebrsign");
		sortMap.put("组织机构代码","orgcode");
		sortMap.put("社保登记证号","siregno");
		sortMap.put("纳税人识别号","taxpayid");
		sortMap.put("备注","remark");
			
	}
	
	/**
	 * 隶属基本信息--GROUPENTINFO
	 */
	private static void getLiShu() {
		sortMap.put("隶属企业名称","entname");
		sortMap.put("统一社会信用代码","unifsocicrediden");
		sortMap.put("注册号","regno");
		sortMap.put("法定代表人","lerep");
		sortMap.put("成立日期","estdate");
		sortMap.put("企业(机构)类型","enttype");
		sortMap.put("认缴注册资本","regcap");
		sortMap.put("住所所在行政区划","domdistrict");
		sortMap.put("经营(驻在)期限自","opfrom");
		sortMap.put("经营(驻在)期限至","opto");
		sortMap.put("住所","dom");
		sortMap.put("币种","currency");
		sortMap.put("国别","countrycode");
		sortMap.put("经营(业务)范围","opscope");
		sortMap.put("联系人","linkman");
		sortMap.put("联系人电话","tel");
		sortMap.put("电子邮件","email");
			
	}


	/**
	 * 集团所对应的基本信息--GROUPENTINFO
	 */
	private static void getJITUAN(String opetype,String entstatus) {
		sortMap.put("集团名称","entname");
		sortMap.put("统一社会信用代码","unifsocicrediden");
		sortMap.put("集团登记证号","regno");
		sortMap.put("母公司和子公司注册资本总和","regcap");
		sortMap.put("成立日期","estdate");
		sortMap.put("核准日期","apprdate");
		sortMap.put("登记机关","regorg");
		sortMap.put("企业状态","entstatus");
		sortMap.put("字号","enttra");
		sortMap.put("字号拼音","tradpiny");
		if("4".equals(entstatus)){ //注销
			sortMap.put("注销日期","cancdate");
			sortMap.put("吊销日期","revodate");
		}else if("2".equals(entstatus)){ //吊销
			sortMap.put("吊销日期","revodate");
		}
		//sortMap.put("属地监管工商所","localadm");
		sortMap.put("指定联系人","linkmancode");

	}

	/**
	 * 对应表 E_BASEINFO 显示内容 外资和内资用到
	 */
	private static void getWZNZBasic(){
		sortMap.put("企业(机构)名称","entname");
		sortMap.put("统一社会信用代码","unifsocicrediden");	
		sortMap.put("注册号","regno");
		sortMap.put("企业(机构)类型","enttype");
		sortMap.put("认缴注册资本(金)","regcap");
		sortMap.put("法定代表人","lerep");
		sortMap.put("成立日期","estdate");
		sortMap.put("核准日期","apprdate");
		sortMap.put("住所","dom");
		sortMap.put("住所所在行政区划","domdistrict");
		sortMap.put("行业门类","industryphy");
		sortMap.put("行业细类","industryco");
		sortMap.put("经营(业务)范围","opscope");
		sortMap.put("经营(驻在)期限自","opfrom");
		sortMap.put("经营(驻在)期限至","opto");
		sortMap.put("营业期限(年限)","opfyears");
		sortMap.put("登记机关","regorg");
		sortMap.put("企业状态","entstatus");
		sortMap.put("副本数","entstatus");

	
	}
		/**
		 *内资补充信息--E_DI_SUPL
		 */
	private static void getNeiZi(String opetype ,String entstatus) {
		sortMap.put("企业(机构)名称","entname");
		sortMap.put("统一社会信用代码","unifsocicrediden");	
		sortMap.put("注册号","regno");
		sortMap.put("企业(机构)类型","enttype");
		if("HZS".equals(opetype)){
			sortMap.put("成员出资总额","regcap");
		}else if("GS".equals(opetype) || "NZFR".equals(opetype)|| "HHQY".equals(opetype)|| "GRDZ".equals(opetype)|| "WZGS".equals(opetype)|| "WZHH".equals(opetype)|| "HZS".equals(opetype)|| "SLYB".equals(opetype)){
			sortMap.put("认缴注册资本","regcap");
			sortMap.put("实缴注册资本","reccap");
		}
		sortMap.put("企业状态","entstatus");
		if("FGS".equals(opetype) || "HHFZ".equals(opetype) || "GRDZFZ".equals(opetype)){
			sortMap.put("负责人","lerep");
		}else if("HHQY".equals(opetype) || "WZHH".equals(opetype)){
			sortMap.put("执行事务合伙人","lerep");
		}else if("GRDZ".equals(opetype)){
			sortMap.put("投资人","lerep");
		}else if("WGDB".equals(opetype)){
			sortMap.put("首席代表","lerep");
		}else{
			sortMap.put("法定代表人","lerep");
		}
		//sortMap.put("住所所在行政区划","domdistrict");
		sortMap.put("登记机关","regorg");
		sortMap.put("成立日期","estdate");
		sortMap.put("核准日期","apprdate");
		sortMap.put("行业门类","industryphy");
		sortMap.put("行业细类","industryco");
		if("HHQY".equals(opetype) || "WZHH".equals(opetype)){
			sortMap.put("主要经营场所","dom");
		}else if("HZSFZ".equals(opetype)|| "GT".equals(opetype)){
			sortMap.put("经营场所","dom");
		}else if("FGS".equals(opetype)|| "NZYY".equals(opetype)|| "HHFZ".equals(opetype)|| "GRDZFZ".equals(opetype)|| "WZFZ".equals(opetype)|| "WZHHFZ".equals(opetype)){
			sortMap.put("营业场所","dom");
		}else{
			sortMap.put("住所","dom");
		}
		sortMap.put("经营(业务)范围","opscope");
		if("HHQY".equals(opetype) || "WZHH".equals(opetype)){
			sortMap.put("合伙期限自","opfrom");
			sortMap.put("合伙期限至","opto");
		}else if("NZFR".equals(opetype)){
			sortMap.put("经营期限自","opfrom");
			/*sortMap.put("经营期限至","opto");*/
		}else{
			sortMap.put("营业期限自","opfrom");
			/*sortMap.put("营业期限至","opto");*/
		}
		/*sortMap.put("营业期限(年限)","opfyears");*/
		sortMap.put("副本数","bliccopynum");
		sortMap.put("一般经营项目","cbuitem");
		sortMap.put("许可经营项目","pabuitem");
//		sortMap.put("组织机构代码","orgcode");
//		sortMap.put("经营场所","oploc");
//		sortMap.put("设立类型","launchtype");
//		sortMap.put("执照有效期限","licexpdate");
//		sortMap.put("企业(机构)字号","enttra");
//		sortMap.put("字号拼音","tradpiny");
		if("4".equals(entstatus)){ //注销
			sortMap.put("注销日期","cancdate");
			sortMap.put("吊销日期","revodate");
		}else if("2".equals(entstatus)){ //吊销
			sortMap.put("吊销日期","revodate");
		}else if("6".equals(entstatus)){
			sortMap.put("迁出日期","moutddte"); //迁出
		}
	/*	sortMap.put("迁入日期","minddte");*/
		/*sortMap.put("迁入或迁出行政区划","inoroutdistcode");*/
//		sortMap.put("区县","distcoun");
//		sortMap.put("乡（镇街道）","streetown");
//		sortMap.put("路（村、社区）","road");
//		sortMap.put("门牌号","doorplate");
//		sortMap.put("属地监管工商所","localadm");
//		sortMap.put("从业人数","empnum");
//		sortMap.put("执照字","licword");
//		sortMap.put("执照号","licnum");
		sortMap.put("经营类别","optype");
		sortMap.put("指定联系人","linkmancode");
		/*sortMap.put("经营方式","busform");*/
		if("GS".equals(opetype) || "NZFR".equals(opetype)|| "HHQY".equals(opetype)|| "GRDZ".equals(opetype)|| "WZGS".equals(opetype)|| "WZHH".equals(opetype)|| "HZS".equals(opetype)|| "SLYB".equals(opetype)){
			sortMap.put("分支机构","branch");
		}
		


	}
	/**
	 * 个体经营户 E_PB_BASEINFO
	 */
	private static void getGeTi(String opetype,String entstatus) {
		sortMap.put("字号名称","entname");
		sortMap.put("统一社会信用代码","unifsocicrediden");
		sortMap.put("注册号","regno");
		sortMap.put("资金数额","regcap");
		sortMap.put("经营者","lerep");
		sortMap.put("企业状态","entstatus");
		sortMap.put("成立日期","estdate");
		sortMap.put("核准日期","apprdate");
		sortMap.put("经营场所","dom");
		sortMap.put("行业门类","industryphy");
		sortMap.put("行业细类","industryco");
		sortMap.put("经营(业务)范围","opscope");
		sortMap.put("经营期限自","opfrom");
		/*sortMap.put("经营期限至","opto");*/
		//sortMap.put("住所所在行政区划","domdistrict");
		/*sortMap.put("营业期限（年限）","opfyears");*/
		sortMap.put("登记机关","regorg");
		sortMap.put("副本数","bliccopynum");
		sortMap.put("组成形式","compform");
		sortMap.put("一般经营项目","cbuitem");
		sortMap.put("许可经营项目","pabuitem");
//		sortMap.put("组织机构代码","orgcode");
//		sortMap.put("执照有效期限","licexpdate");
//		sortMap.put("字号","enttra");
//		sortMap.put("字号拼音","tradpiny");
		if("4".equals(entstatus)){ //注销
			sortMap.put("注销日期","cancdate");
			sortMap.put("吊销日期","revodate");
		}else if("7".equals(entstatus)){ //吊销
			sortMap.put("吊销日期","revodate");
		}else if("6".equals(entstatus)){
			sortMap.put("迁出日期","moutddte"); //迁出
		}
	/*	sortMap.put("迁入日期","minddte");*/
		/*sortMap.put("迁入或迁出的行政区划","inoroutdistcode");*/
//		sortMap.put("区县","distcoun");
//		sortMap.put("乡（镇街道）","streetown");
//		sortMap.put("路（村、社区）","road");
//		sortMap.put("门牌号","doorplate");
//		sortMap.put("属地监管工商所","localadm");
//		sortMap.put("从业人数","empnum");
//		sortMap.put("执照字","licword");
//		sortMap.put("执照号","licnum");
		sortMap.put("经营类别","optype");
		sortMap.put("指定联系人","linkmancode");
		/*sortMap.put("经营方式","busform");*/
		sortMap.put("家庭成员","familymembers");

	}
	/**
	 * 个体经营者信息 对应表E_PB_OPERATOR
	 */
	private static void getGeTiOper(){
		//sortMap.put("人员序号","personid");
		//sortMap.put("主体身份代码","pripid");
		sortMap.put("姓名","persname");
		sortMap.put("性别","sex");
		sortMap.put("证件类型","certype");
		sortMap.put("证件号码","cerno");
		//sortMap.put("出生日期","natdate");
		sortMap.put("文化程度","litedeg");
		sortMap.put("民族","nation");
		sortMap.put("邮政编码","postalcode");
		sortMap.put("住所","dom");
		sortMap.put("联系电话","tel");
		sortMap.put("政治面貌","polstand");
		sortMap.put("申请前职业状况","occstbeapp");
		sortMap.put("证件签发日期","cerissdate");
		sortMap.put("经营者所在地区","operarea");
		sortMap.put("证件有效期","cervalper");
		//sortMap.put("身份核证文件核(公)证机构(人)","notorg");
		//sortMap.put("身份核证文件编号","notdocno");
/*		sortMap.put("数据汇总单位","sExtFromnode");
		sortMap.put("数据汇总时间","sExtDatatime");*/
	}
	

	/**
	 *  外资补充信息--E_FI_SUPL
	 */
	private static void getWaiZi(String opetype, String entstatus) {
		sortMap.put("企业(机构)名称","entname");
		sortMap.put("统一社会信用代码","unifsocicrediden");	
		sortMap.put("注册号","regno");
		sortMap.put("企业(机构)类型","enttype");
		if("HZS".equals(opetype)){
			sortMap.put("成员出资总额","regcap");
		}else if("GS".equals(opetype) || "NZFR".equals(opetype)|| "HHQY".equals(opetype)|| "GRDZ".equals(opetype)|| "WZGS".equals(opetype)|| "WZHH".equals(opetype)|| "HZS".equals(opetype)|| "SLYB".equals(opetype)){
			sortMap.put("认缴注册资本","regcap");
			sortMap.put("实缴注册资本","reccap");
		}
		if("FGS".equals(opetype) || "HHFZ".equals(opetype) || "GRDZFZ".equals(opetype)){
			sortMap.put("负责人","lerep");
		}else if("HHQY".equals(opetype) || "WZHH".equals(opetype)){
			sortMap.put("执行事务合伙人","lerep");
		}else if("GRDZ".equals(opetype)){
			sortMap.put("投资人","lerep");
		}else if("WGDB".equals(opetype)){
			sortMap.put("首席代表","lerep");
		}else{
			sortMap.put("法定代表人","lerep");
		}
		sortMap.put("登记机关","regorg");		
		sortMap.put("企业状态","entstatus");		
		sortMap.put("成立日期","estdate");
		sortMap.put("核准日期","apprdate");
		sortMap.put("行业门类","industryphy");
		sortMap.put("行业细类","industryco");
		sortMap.put("住所","dom");
		sortMap.put("经营(业务)范围","opscope");
		sortMap.put("经营期限自","opfrom");
		/*sortMap.put("经营期限至","opto");*/
		/*sortMap.put("营业期限(年限)","opfyears");*/
		//sortMap.put("住所所在行政区划","domdistrict");
		sortMap.put("币种","currency");
		sortMap.put("一般经营项目","cbuitem");
		sortMap.put("许可经营项目","pabuitem");
		sortMap.put("实缴注册资本","reccap");
		sortMap.put("副本数","bliccopynum");
//		sortMap.put("组织机构代码","orgcode");
//		sortMap.put("经营场所","oploc");
//		sortMap.put("设立类型","launchtype");
//		sortMap.put("执照有效期限","licexpdate");
//		sortMap.put("企业(机构)字号","enttra");
//		sortMap.put("字号拼音","tradpiny");
		if("4".equals(entstatus)){ //注销
			sortMap.put("注销日期","cancdate");
			sortMap.put("吊销日期","revodate");
		}else if("2".equals(entstatus)){ //吊销
			sortMap.put("吊销日期","revodate");
		}else if("6".equals(entstatus)){
			sortMap.put("迁出日期","moutddte"); //迁出
		}
		/*sortMap.put("迁入日期","minddte");*/
		/*sortMap.put("迁入或迁出行政区划","inoroutdistcode");*/
//		sortMap.put("区县","distcoun");
//		sortMap.put("乡（镇街道）","streetown");
//		sortMap.put("路（村、社区）","road");
//		sortMap.put("门牌号","doorplate");
		if("WZGS".equals(opetype) || "WZHH".equals(opetype)){
			sortMap.put("投资总额","congro");
			sortMap.put("注册资本折万美元","regcapusd");
			sortMap.put("注册资本折万人民币","regcaprmb");
			sortMap.put("投资总额折万美元","congrousd");
			sortMap.put("投资总额折万人民币","congrormb");
		}
//		sortMap.put("属地监管工商所","localadm");
//		sortMap.put("从业人数","empnum");
		sortMap.put("外国(地区)企业名称","forentname");
		sortMap.put("境外住所","fordom");
		sortMap.put("国别地区","countrycode");
//		sortMap.put("出资日期","conddte");
//		sortMap.put("注册字","wzregword");
//		sortMap.put("执照字","licword");
//		sortMap.put("执照号","licnum");
		//sortMap.put("经营方式代码","optype");
		sortMap.put("指定联系人","linkmancode");
		sortMap.put("法定送达联系人","lawmancode");
		/*sortMap.put("经营方式","busform");*/
		sortMap.put("分支机构","wzbranch");
//		sortMap.put("分支机构","branch");
	}
}
