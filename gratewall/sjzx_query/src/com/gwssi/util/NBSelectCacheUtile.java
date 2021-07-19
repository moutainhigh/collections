package com.gwssi.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class NBSelectCacheUtile {
	
	private static Map<String,Object> sortMap = new LinkedHashMap<String,Object>();
	
	static{
		sortMap = new LinkedHashMap<String,Object>();
	}
	
	/**
	 * 获取基本信息显示（以后要给成从缓存中读取，而内容需要放在文件的方便管理）
	 * @param flag
	 * @param entityNo 
	 * @param type
	 * @return
	 */
	public static Map<String,Object> getLinkedHashMap(String type, int entityNo) {
		if("qy".equals(type)){  //企业年报
			switch (entityNo) {
				case 1: // 基本信息
					getNBQYJBXX();
					break;
				case 2: // 资产状况信息
					getNBQYZCZK();
					break;
				case 7: // 网站或网店
					getNBQYWDXX();
					break;
				case 8: // 党建信息
					getNBQYDJXX();
					break;
				default:
					break;
			}
		}else if("gt".equals(type)){ //个体年报
				switch (entityNo) {
					case 1: // 基本信息
						getNBGTJBXX();
						break;
					case 2: // 生产经营情况
						getNBGTZCZK();
						break;
					case 4: // 网站或网店
						getNBGTWDXX();
						break;
					case 5: // 党建信息
						getNBGTDJXX();
						break;
					default:
						break;
					}
			}
		
		return sortMap;
	}


	private static void getNBQYJBXX() {
		sortMap.put("企业名称","entname");
		sortMap.put("统一社会信用代码","uniscid");
		sortMap.put("注册号","regno");
		sortMap.put("年报时间","anchedate");
		sortMap.put("年报年度","ancheyear");
		sortMap.put("从业人数","empnum");
		
		sortMap.put("联系电话","tel");
		sortMap.put("联系地址","addr");
		sortMap.put("邮政编码","postalcode");
		sortMap.put("邮箱","email");
		sortMap.put("经营状态 ","busst");
		sortMap.put("首次公示时间","firstpubtime");
		sortMap.put("高校毕业生人数(经营者)","colgranum");
		
		sortMap.put("高校毕业生人数(雇工)","colemplnum");
		sortMap.put("退役士兵人数(经营者)","retsolnum");
		sortMap.put("退役士兵人数(雇工)","retemplnum");
		sortMap.put("残疾人人数(经营者)","dispernum");
		sortMap.put("残疾人人数(雇工)","disemplnum");
		sortMap.put("失业人员再就业人数(经营者)","unenum");
		sortMap.put("失业人员再就业人数(雇工)","uneemplnum");
		
		/*sortMap.put("是否拥有网站","ifhasweb");
		sortMap.put("是否有对外投资","ifinvother");
		sortMap.put("是否有股权转让","ifhasgqzr");
		sortMap.put("是否公示从业人数","ifpubempnum");
		sortMap.put("是否公示","anchetype");*/
		/*sortMap.put("年报类别","executeperson");*/
		
		
		
	}


	private static void getNBQYZCZK() {
		sortMap.put("资产总额","assgro");
		sortMap.put("负债总额  ","liagro");
		sortMap.put("销售(营业)收入","vendinc");
		
		sortMap.put("其中主营业务收入","maibusinc");
		sortMap.put("利润总额","progro");
		sortMap.put("净利润","netinc");
		sortMap.put("纳税总额","ratgro");
		sortMap.put("所有者权益合计","totequ");
		/*sortMap.put("公示资产总额","ifassgro");
		sortMap.put("公示负债总额","ifliagro");
		
		sortMap.put("公示销售(营业)收入","ifvendinc");
		sortMap.put("公示其中主营业务收入","ifmaibusinc");
		sortMap.put("公示利润总额","ifprogro");
		sortMap.put("公示净利润","ifnetinc");
		sortMap.put("公示纳税总额","ifratgro");
		sortMap.put("公示所有者权益合计","iftotequ");
		*/
	}




	private static void getNBQYWDXX() {
		
		sortMap.put("网站名称","websitname");
		sortMap.put("域名地址","domain");
		sortMap.put("网站类型","webtype");
	}


	private static void getNBQYDJXX() {
		sortMap.put("党员（预备党员）人数","numparm");
		sortMap.put("党组织建制","parins");
		/*sortMap.put("法定代表人是否党员","resparmsign");
		sortMap.put("法定代表人是否党组织书记","resparsecsign");*/
		
	}


	private static void getNBGTJBXX() {
		sortMap.put("个体工商户名称","traname");
		sortMap.put("注册号","regno");
		sortMap.put("年报年度","ancheyear");
		sortMap.put("年报时间","anchedate");
		sortMap.put("经营者姓名","name");
		sortMap.put("从业人数","empnum");
		sortMap.put("联系电话","tel");
		sortMap.put("高校毕业生人数(经营者)","colgranum");
		
		sortMap.put("高校毕业生人数(雇工)","colemplnum");
		sortMap.put("退役士兵人数(经营者)","retsolnum");
		sortMap.put("退役士兵人数(雇工)","retemplnum");
		sortMap.put("残疾人人数(经营者)","dispernum");
		
		sortMap.put("残疾人人数(雇工)","unenum");
		sortMap.put("失业人员再就业人数(经营者)","parins");
		sortMap.put("失业人员再就业人数(雇工)","uneemplnum");
	/*	sortMap.put("是否拥有网站","ifhasweb");*/
		
	}


	private static void getNBGTZCZK() {
		sortMap.put("销售(营业)收入","vendinc");
		sortMap.put("纳税总额","ratgro");
	/*	sortMap.put("公示销售(营业)收入","ifvendinc");
		sortMap.put("公示纳税总额","ifratgro");*/
		
	}

	private static void getNBGTWDXX() {
		
		sortMap.put("网站名称","websitname");
		sortMap.put("域名地址","domain");
		sortMap.put("网站类型","webtype");
	}


	private static void getNBGTDJXX() {
		sortMap.put("党员（预备党员）人数","numparm");
		sortMap.put("党组织建制","parins");
	/*	sortMap.put("法定代表人是否党员","resparmsign");
		sortMap.put("法定代表人是否党组织书记","resparsecsign");*/
		
	}



}
