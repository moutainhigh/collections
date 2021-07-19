package com.ly.task.ext;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.HolidayDao;
import com.ly.pojo.Holiday;

@Component
public class HolidayTask {
	private static Logger logger = Logger.getLogger(HolidayTask.class);

	@Autowired
	private HolidayDao holidayDao;
	
	

	// 计算两个日期相差天数
	// https://www.cnblogs.com/mingforyou/p/3545174.html
	public static final int daysBetween(Date early, Date late) {

		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}

	
	
	//根据当前系统时间，每月15号的10点15分更新一次假日表的节日信息
	//@Scheduled(cron="0/10 * *  * * ? ")
	//https://blog.csdn.net/qq_33556185/article/details/51852537
	//@Scheduled(cron= "0 0/1 * * * ?  ")
	//@Scheduled(cron= "0 15 10 10,28 * ?")
	@Scheduled(cron= "0 15 10 10,28 * ?")
	public void updateHolidayByCurrentSystem() throws Exception{
		Calendar rightNow = Calendar.getInstance(); 
		logger.debug("=======假日表的节日信息 ");
		rightNow.setTime(new Date());  
		int year  = rightNow.get(Calendar.YEAR);
		DateFormat df = DateFormat.getDateInstance();  
		Holiday holiday = new Holiday();
		holidayDao.delete();
		//http://www.sse.com.cn/disclosure/dealinstruc/closed/
		/*String str = httpClientService.doGet("http://www.sse.com.cn/disclosure/dealinstruc/closed/");
		System.out.println(str);*/
		Document doc = Jsoup.connect("http://www.sse.com.cn/disclosure/dealinstruc/closed/").get();
		/*doc = Jsoup.connect("http://blog.csdn.net/roy_70")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                .get();*/
		
	/*	Document doc = Jsoup.connect("http://blog.csdn.net/roy_70")
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post();*/
		
		Elements trs = doc.getElementsByClass("table").select("tr");  ;
		 for (int i = 0; i < trs.size(); ++i) {  
		        // 获取一个tr  
		        Element tr = trs.get(i);  
		        // 获取该行的所有td节点  
		        Elements tds = tr.select("td");  
		        // 选择某一个td节点  
		        for (int j = 0; j < tds.size(); ++j) {  
		            Element td = tds.get(j);  
		           if(td.text().contains("节")||td.text().contains("旦")){
		        	// System.out.println(td.text().substring(0, td.text().length()-1));
		        	   String holidayName = td.text().substring(0, td.text().length()-1);
		        	   holiday.setHolidayName(holidayName);
		        	  // System.out.println(holidayName);
		           }else{
		        	   Date date3 = null;
		        	   Date earlydate = null;
		        	   int days = 0;
		        	   Date  latedate = null;
		        	   if(td.text().contains("至")){
		        		  String from =  td.text().split("至")[0].substring(0,td.text().split("至")[0].indexOf("日")+1);
		        		  String end =  td.text().split("至")[1].substring(0, td.text().split("至")[1].indexOf("休市"));
		        		  
		        		  String begin_month = from.substring(0,from.indexOf("月"));
		        		  String begin_day = from.substring(from.indexOf("月")+1,from.indexOf("日"));
		        		  
		        		  String end_month = end.substring(0,end.indexOf("月"));
		        		  String end_day = end.substring(end.indexOf("月")+1,end.indexOf("日"));
		        		  
		        		  if(begin_month.length()<2){
		        			  begin_month ="0"+begin_month;
		        		  }
		        		  if(begin_day.length()<2){
		        			  begin_day ="0"+begin_day;
		        		  }
		        		  
		        		  if(end_month.length()<2){
		        			  end_month ="0"+end_month;
		        		  }
		        		  if(end_day.length()<2){
		        			  end_day ="0"+end_day;
		        		  }
		        		  
		        		  if(!begin_month.contains("年")){
		        			  String earyDateStr = year+"-"+begin_month+"-"+begin_day;
			        		  String lateDateStr  = year+"-"+end_month+"-"+end_day;
			        		 
			        		  earlydate = df.parse(earyDateStr);   
			        		  latedate = df.parse(lateDateStr);   
			        		  days = daysBetween(earlydate,latedate)+1;
			        		  //System.out.println(getDays + "       " + earlydate +"  "+ latedate);
		        		  }else{
		        			  String begTimeStr = from;
		        			  String endTimeStr = end.substring(0,end.indexOf("（"));
		        			
		        			  begTimeStr = begTimeStr.replace("年", "-").replace("月", "-").replace("日", "");
		        			  endTimeStr=endTimeStr.replace("年", "-").replace("月", "-").replace("日", "");
		        			  earlydate = df.parse(begTimeStr);   
		        	          latedate = df.parse(endTimeStr);
		        			  days =  daysBetween(earlydate, latedate)+1;
		        		  }
		        		  for (int k = 0; k < days; k++) {
		        			  //将开始日期加上日期相差之后的时间,用于获取相差日期天数之后的日期
		        			   date3 = new Date(earlydate.getTime() + k * 24 * 60 * 60 * 1000);
		        			   holiday.setHoliday(date3);
		        			   holidayDao.saveHoliday(holiday);
						  }
		        	   }else{
		        		   //供给五一节日的或者是元旦的，比如18年的。
		        		  // System.out.println(td.text().substring(0,4));
		        		   String dataStr = td.text().substring(0,4);
		        		   String begin_month = dataStr.substring(0,dataStr.indexOf("月"));
			        	   String begin_day = dataStr.substring(dataStr.indexOf("月")+1,dataStr.indexOf("日"));
			        		  
			        		  if(begin_month.length()<2){
			        			  begin_month ="0"+begin_month;
			        		  }
			        		  if(begin_day.length()<2){
			        			  begin_day ="0"+begin_day;
			        		  }
			        		  String dateStr = year+"-"+begin_month+"-"+begin_day;
			        		  //System.out.println(dateStr);
			        		  Date  date = df.parse(dateStr);  
			        		  holiday.setHoliday(date);
			        		  holidayDao.saveHoliday(holiday);
		        	   }
		           }
		       }  
		 }
	}
}
