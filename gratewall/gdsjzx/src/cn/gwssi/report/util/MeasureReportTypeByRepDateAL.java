//package cn.gwssi.report.util;
//
///* 
//*  根据报告日期判定报告频率 
//*/  
//import java.util.Calendar;  
//import java.util.Date;  
//import java.util.GregorianCalendar;  
///** 
//* 工作日，周末、月末、季末、半年末，年末的判断 
//*  
//*  
//*/  
//public class MeasureReportTypeByRepDateAL {  
//private ITjFactDealday tjFactDealday;  
///** 
//  *  
//  * @return 
//  */  
//public ITjFactDealday getTjFactDealday() {  
//  return tjFactDealday;  
//}  
///** 
//  *  
//  * @param tjFactDealday 
//  */  
//public void setTjFactDealday(ITjFactDealday tjFactDealday) {  
//  this.tjFactDealday = tjFactDealday;  
//}  
///** 
//  * 判断某日期是否为其所在周、月、季、半年、年的最后一个工作日，即周末，月末，季末，半年末，年末 
//  *  
//  * @param reportDate 
//  *            报告日期 
//  * @param freq 
//  *            频率：2：周，3：月，4：季，5：半年，6：年 
//  * @return 
//  */  
//public boolean isEndDayByFreq(Date reportDate, int freq) {  
//  boolean isEndDay = false;  
//  Calendar reportDay = dateToCalendar(reportDate);  
//  Calendar nextDay_1 = new GregorianCalendar(); // nextDay_1：reportDay的下一天  
//  nextDay_1.set(Calendar.YEAR, reportDay.get(Calendar.YEAR));//下一天 所处的年份  
//  nextDay_1.set(Calendar.DAY_OF_YEAR,reportDay.get(Calendar.DAY_OF_YEAR) + 1);  
//  boolean nextDayIsWorkDay = isWorkDay(nextDay_1.getTime());//下一天是否是工作日  
//  Date nextDay_2 = null; // nextDay_2：reportDay的下一个工作日  
//  if (!nextDayIsWorkDay) {//如果下一天非工作日  
//   nextDay_2 = getAfterWorkDay(nextDay_1.getTime());//获取工作日 日期  
//  } else {//下一天为工作日  
//   nextDay_2 = nextDay_1.getTime();//获取工作日 日期  
//  }  
//  Calendar nextWorkDay = dateToCalendar(nextDay_2);//转换为Calendar类型  
//  switch (freq) {  
//  // 判断当前日期是否为该日期所在自然周的最后一个工作日  
//  case ParameterUtil.WEEK:// 周的常量值：2  
//   //如果报告日期所属当年的周与下一个工作日所属当年的周不相等  
//   if (reportDay.get(Calendar.WEEK_OF_YEAR) != nextWorkDay.get(Calendar.WEEK_OF_YEAR)) {  
//    isEndDay = true;//则报告日期为本周最后一个工作日  
//   }  
//   ;  
//   break;  
//  // 判断当前日期是否为该日期所在月的最后一个工作日  
//  case ParameterUtil.MONTH://月的常量值：3  
//   if (reportDay.get(Calendar.MONTH) != nextWorkDay.get(Calendar.MONTH)) {  
//    isEndDay = true;  
//   }  
//   ;  
//   break;  
//  // 判断当前日期是否为该日期所在季度的最后一个工作日  
//  case ParameterUtil.SEASON://季度的常量值：4  
//   if ((reportDay.get(Calendar.MONTH) == Calendar.MARCH)  
//     && (nextWorkDay.get(Calendar.MONTH) != Calendar.MARCH)) {  
//    isEndDay = true;  
//   } else if ((reportDay.get(Calendar.MONTH) == Calendar.JUNE)  
//     && (nextWorkDay.get(Calendar.MONTH) != Calendar.JUNE)) {  
//    isEndDay = true;  
//   } else if ((reportDay.get(Calendar.MONTH) == Calendar.SEPTEMBER)  
//     && (nextWorkDay.get(Calendar.MONTH) != Calendar.SEPTEMBER)) {  
//    isEndDay = true;  
//   } else if ((reportDay.get(Calendar.MONTH) == Calendar.DECEMBER)  
//     && (nextWorkDay.get(Calendar.MONTH) != Calendar.DECEMBER)) {  
//    isEndDay = true;  
//   }  
//   ;  
//   break;  
//  // 判断当前日期是否为该日期所在半年的最后一个工作日  
//  case ParameterUtil.HALF_YEAR://半年的常量值：5  
//   if ((reportDay.get(Calendar.MONTH) == Calendar.JUNE)  
//     && (nextWorkDay.get(Calendar.MONTH) != Calendar.JUNE)) {  
//    isEndDay = true;  
//   } else if ((reportDay.get(Calendar.MONTH) == Calendar.DECEMBER)  
//     && (nextWorkDay.get(Calendar.MONTH) != Calendar.DECEMBER)) {  
//    isEndDay = true;  
//   }  
//   ;  
//   break;  
//  // 判断当前日期是否为该日期所在年份的最后一个工作日  
//  case ParameterUtil.YEAR://年的常量值：6  
//   if (reportDay.get(Calendar.YEAR) != nextWorkDay.get(Calendar.YEAR)) {  
//    isEndDay = true;  
//   }  
//   ;  
//   break;  
//  default:  
//  }  
//  return isEndDay;  
//}  
///** 
//  * 获取reportDate的下一个工作日，注：reportDate必须为非工作日 
//  * 此方法根据自己的需求获取工作日 
//  * @param reportDate 
//  * @return 
//  */  
//public Date getAfterWorkDay(Date reportDate) {  
//  return tjFactDealday.afterDay(reportDate);  
//}  
///** 
//  * Date类型数据转换为Calendar类型数据 
//  *  
//  * @param date 
//  * @return 
//  */  
//private Calendar dateToCalendar(Date date) {  
//  int year = DamsDateUtils.getYear(date);  
//  int month = DamsDateUtils.getMonth(date);  
//  int day = DamsDateUtils.getDay(date);  
//  Calendar calendar = new GregorianCalendar();  
//  calendar.set(Calendar.YEAR, year);  
//  calendar.set(Calendar.MONTH, month); // Calendar的月份0-11代表1月到12月  
//  calendar.set(Calendar.DAY_OF_MONTH, day);  
//  return calendar;  
//}  
//}  