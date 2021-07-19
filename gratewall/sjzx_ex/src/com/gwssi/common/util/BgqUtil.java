package com.gwssi.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 报告期辅助工具类
 * 进行报告期代码与报告期显示的转换
 * @author zhyi
 * @version v1.0.0
 */
/**
 * @author zhyi
 *
 */
public class BgqUtil
{
	public static List bgqListFactory(String bgqType,String begYear,String endYear){
		List bgqList = new ArrayList();
		int begin;
		int end;
		String year;
		begin = new Integer(begYear).intValue();
		end =   new Integer(endYear).intValue();
		if(begin>end){
			begin = new Integer(endYear).intValue();
			end =   new Integer(begYear).intValue();
		}
		
		for(int i=begin;i<=end;i++){
			year = new Integer(i).toString();
			if(bgqType.equals("1")){//年报
				bgqList.addAll(BgqUtil.makeYearBgqList(year));
			}else if(bgqType.equals("2")){//半年报
				bgqList.addAll(BgqUtil.makeHalfYearBgqList(year));
			}else if(bgqType.equals("3")){//季报
				bgqList.addAll(BgqUtil.makeQuarterBgqList(year));
			}else if(bgqType.equals("4")){//月报
				bgqList.addAll(BgqUtil.makeMonthBgqList(year));
			}else if(bgqType.equals("5")){//半月报
				bgqList.addAll(BgqUtil.makeHalfMonthBgqList(year));
			}else if(bgqType.equals("6")){//十年报,可以定义策略
				bgqList.addAll(BgqUtil.makeYearBgqList(year));
			}else if(bgqType.equals("7")){//两年报
				bgqList.addAll(BgqUtil.makeYearBgqList(year));
			}else if(bgqType.equals("8")){//五年报
				bgqList.addAll(BgqUtil.makeYearBgqList(year));
			}
			year = null;
		}
		
		return bgqList;
	}
	
	public static HashMap bgqMapFactory(String bgqType,String begYear,String endYear){
		HashMap bgqHashMap = new HashMap();
		int begin;
		int end;
		String year;
		begin = new Integer(begYear).intValue();
		end =   new Integer(endYear).intValue();
		if(begin>end){
			begin = new Integer(endYear).intValue();
			end =   new Integer(begYear).intValue();
		}
		
		for(int i=begin;i<=end;i++){
			year = new Integer(i).toString();
			if(bgqType.equals("1")){//年报
				bgqHashMap.putAll(BgqUtil.makeYearBgq(year));
			}else if(bgqType.equals("2")){//半年报
				bgqHashMap.putAll(BgqUtil.makeHalfYearBgq(year));
			}else if(bgqType.equals("3")){//季报
				bgqHashMap.putAll(BgqUtil.makeQuarterBgq(year));
			}else if(bgqType.equals("4")){//月报
				bgqHashMap.putAll(BgqUtil.makeMonthBgq(year));
			}else if(bgqType.equals("5")){//半月报
				bgqHashMap.putAll(BgqUtil.makeHalfMonthBgq(year));
			}else if(bgqType.equals("6")){//十年报,可以定义策略
				bgqHashMap.putAll(BgqUtil.makeYearBgq(year));
			}else if(bgqType.equals("7")){//两年报
				bgqHashMap.putAll(BgqUtil.makeYearBgq(year));
			}else if(bgqType.equals("8")){//五年报
				bgqHashMap.putAll(BgqUtil.makeYearBgq(year));
			}
			year = null;
		}
		
		return bgqHashMap;
	}
	
	private static HashMap getBgqByYear(String year){
		HashMap bgqMaps = new HashMap();
		//生成年报告期
		bgqMaps.putAll(makeYearBgq(year));
		//生成月报告期
		bgqMaps.putAll(makeMonthBgq(year));
		//生成季报告期
		bgqMaps.putAll(makeQuarterBgq(year));
		return bgqMaps;
	}
	
	private static HashMap makeYearBgq(String year){
		HashMap bgqMaps = new HashMap();
		String bgqDm;
		String bgqMc;
		//生成年报告期
		bgqDm = year+"0000";
		bgqMc = year+"年";
		bgqMaps.put(bgqDm, bgqMc);
		bgqDm = null;
		bgqMc = null;
		return bgqMaps;
	}
	//生成月报、半月报、旬报的报告期对
	private static HashMap makeMonthBgq(String year){
		HashMap bgqMaps = new HashMap();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqMaps.put(year+"0"+month+"0", year+"年"+i+"月");
		}
		return bgqMaps;
	}
	
//	生成半月报报告期对
	private static HashMap makeHalfMonthBgq(String year){
		HashMap bgqMaps = new HashMap();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqMaps.put(year+"0"+month+"4", year+"年"+i+"月上半月");
			bgqMaps.put(year+"0"+month+"5", year+"年"+i+"月下半月");
		}
		return bgqMaps;
	}
	
//	生成月报、半月报、旬报的报告期对
	private static HashMap makeXunBgq(String year){
		HashMap bgqMaps = new HashMap();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqMaps.put(year+"0"+month+"1", year+"年"+i+"月上旬");
			bgqMaps.put(year+"0"+month+"2", year+"年"+i+"月中旬");
			bgqMaps.put(year+"0"+month+"3", year+"年"+i+"月下旬");
		}
		return bgqMaps;
	}
	
	private static HashMap makeQuarterBgq(String year){
		HashMap bgqMaps = new HashMap();
		String bgqDm;
		String bgqMc;
		String quarter;
		for(int i=1;i<5;i++){
			quarter = new Integer(i).toString();
			bgqDm = year+quarter+"000";
			bgqMc = year+"年"+i+"季度";
			bgqMaps.put(bgqDm, bgqMc);
		}
		return bgqMaps;
	}
	
	private static HashMap makeHalfYearBgq(String year){
		HashMap bgqMaps = new HashMap();
		bgqMaps.put(year+"5000", year+"年上半年");
		bgqMaps.put(year+"6000", year+"年下半年");
		return bgqMaps;
	}
	
	
	/**
	 * 生成年报告期代码集
	 * @param year
	 * @return
	 */
	private static List makeYearBgqList(String year){
		List bgqList = new ArrayList();
		String bgqDm;
		//生成年报告期
		bgqDm = year+"0000";
		bgqList.add(bgqDm);
		bgqDm = null;
		return bgqList;
	}
	/**
	 * 生成月报告期代码集
	 * @param year
	 * @return
	 */
	private static List makeMonthBgqList(String year){
		List bgqList = new ArrayList();
		String bgqDm;
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqList.add(year+"0"+month+"0");
		}
		return bgqList;
	}
	
//	生成半月报报告期对
	private static List makeHalfMonthBgqList(String year){
		List bgqList = new ArrayList();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqList.add(year+"0"+month+"4");
			bgqList.add(year+"0"+month+"5");
		}
		return bgqList;
	}
	
//	生成月报、半月报、旬报的报告期对
	private static List makeXunBgqList(String year){
		List bgqList = new ArrayList();
		String month;
		for(int i=1;i<13;i++){
			month = new Integer(i).toString();
			if(i<10)month = "0"+month;
			bgqList.add(year+"0"+month+"1");
			bgqList.add(year+"0"+month+"2");
			bgqList.add(year+"0"+month+"3");
		}
		return bgqList;
	}
	
	private static List makeQuarterBgqList(String year){
		List bgqList = new ArrayList();
		String bgqDm;
		String quarter;
		for(int i=1;i<5;i++){
			quarter = new Integer(i).toString();
			bgqDm = year+quarter+"000";
			bgqList.add(bgqDm);
		}
		return bgqList;
	}
	
	private static List makeHalfYearBgqList(String year){
		List bgqList = new ArrayList();
		bgqList.add(year+"5000");
		bgqList.add(year+"6000");
		return bgqList;
	}
	
	public static String dmToMc(String bgqDm){
		String bagMc = null;
		String year,quarter,month,xun;
		
		if(bgqDm.length()==8){
			year = bgqDm.substring(0,4);
			quarter = bgqDm.substring(4,5);
			month = bgqDm.substring(5,7);
			xun = bgqDm.substring(7,8);
			
			if(quarter.equals("0")&&month.equals("00")&&xun.equals("0")){
				bagMc = year+"年";
			}else if(!quarter.equals("0")){
				bagMc = year+"年"+quarter+"季度";
			}else if(!month.equals("0")){
				if(xun.equals("0")){
					bagMc = year+"年"+month+"月";
				}else if(xun.equals("1")){
					bagMc = year+"年"+month+"月上旬";
				}else if(xun.equals("2")){
					bagMc = year+"年"+month+"月中旬";
				}else if(xun.equals("3")){
					bagMc = year+"年"+month+"月下旬";
				}
			}
		}
		
		return bagMc;
	}
	
	public static String mcToDm(String bgqMc){
		String bagDm = null;
		
		return bagDm;
	}
	
	public static void main(String[] args){
		System.out.println(BgqUtil.dmToMc("20060010"));
	}
}
