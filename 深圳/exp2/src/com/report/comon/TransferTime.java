package com.report.comon;

import java.util.HashMap;
import java.util.Map;

public class TransferTime {

	
	public static Map getStartTimeAndEndTimeByStr(String endTimeString) {
		Map map = new HashMap();
		
		/*String dateAddShiFenMiao = endTimeString + " 23:59:59";
		String lastYear = TimeUtil.getLastYearByTimeString(dateAddShiFenMiao,"yyyy-MM-dd HH:mm:ss");
		String threeMonthAgo = TimeUtil.getLastThreeMonth(dateAddShiFenMiao,"yyyy-MM-dd HH:mm:ss");*/
		
		
		
		String dateAddShiFenMiao = endTimeString;
		String lastYear = TimeUtil.getLastYearByTimeString(dateAddShiFenMiao,"yyyy-MM-dd");
		
		//String str = TimeUtil.getDateBefore("2019-06-25",91, "yyyy-MM-dd");
		String str = TimeUtil.getDateBefore(endTimeString,91, "yyyy-MM-dd");
		map.put("选择查询截止时间", "'" +endTimeString+"'");
		map.put("选择查询截止时间的去年同期时间", "'"+ lastYear+"'");
		//map.put("查询截止时间三个月前的开始时间","'" +  str+" 00:00:00'");
		map.put("查询截止时间三个月前的开始时间","'" +  str+"'");
				
				
		//String nianChuDaoBenYueTime = lastYear.split("-")[0]+"-12-26 00:00:00";
		String nianChuDaoBenYueTime = lastYear.split("-")[0]+"-12-26";
		
		map.put("今年年初时间","'"+nianChuDaoBenYueTime+"'");
		
		
		
		
		int lastNianChu  = Integer.valueOf(nianChuDaoBenYueTime.split("-")[0]) - 1 ;
		String lastYearNianChuTime  = lastNianChu +"-12-26";
		
		
		map.put("去年年初开始时间","'"+lastYearNianChuTime+"'");
		
		
		String lastYearThreeMonthAgoTongQi = lastYear;
		
		String str2 = TimeUtil.getDateBefore(lastYearThreeMonthAgoTongQi,91, "yyyy-MM-dd");
		map.put("查询去年与当前月份一致的三个月前时间","'" +  str2+"'");
		
		return map;
	}
	
	
	public static void main(String[] args) {
		String timeStr = "2019-09-25";
		Map map = TransferTime.getStartTimeAndEndTimeByStr(timeStr);
		System.out.println(map);
	}
	
	
}
