package com.ly.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//https://blog.csdn.net/qq_35723367/article/details/78280522
//https://blog.csdn.net/expect521/article/details/81843176
//https://www.cnblogs.com/0201zcr/p/5000977.html
//https://www.cnblogs.com/mingforyou/p/3545174.html
public class Test04 {
/*	public static void main(String[] args) throws ParseException {
		 String time = "2019年1月1日";
		 SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日");//yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
		 Date utilDate = sdf.parse(time);
		 System.out.println(utilDate);
		 System.out.println(sdf.format(utilDate));
		 
		time =  time.replace("年", "-").replace("月", "-").replace("日", "");
		 System.out.println(time);
	}*/
	
	public static final int daysBetween(Date early, Date late) { 
	     
        java.util.Calendar calst = java.util.Calendar.getInstance();   
        java.util.Calendar caled = java.util.Calendar.getInstance();   
        calst.setTime(early);   
         caled.setTime(late);   
         //设置时间为0时   
         calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         calst.set(java.util.Calendar.MINUTE, 0);   
         calst.set(java.util.Calendar.SECOND, 0);   
         caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         caled.set(java.util.Calendar.MINUTE, 0);   
         caled.set(java.util.Calendar.SECOND, 0);   
        //得到两个日期相差的天数   
         int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst   
                .getTime().getTime() / 1000)) / 3600 / 24;   
         
        return days;   
   }   
  
   
	 public static void main(String[] args)
   {
        /* Date earlydate = new Date();   
        Date latedate = new Date();   */
        DateFormat df = DateFormat.getDateInstance();   
        try {   
        	 String latedateStr = "2019年1月1日";
        	 latedateStr =  latedateStr.replace("年", "-").replace("月", "-").replace("日", "");
        	 String earlydateStr = "2018年12月30日";
        	 earlydateStr =  earlydateStr.replace("年", "-").replace("月", "-").replace("日", "");
        	 
        	 
        	/*Date earlydate = df.parse("2018-12-30");   
        	Date latedate = df.parse("2019-1-1"); */
        	 
        	 Date earlydate = df.parse(earlydateStr);   
         	Date latedate = df.parse(latedateStr);
         	
         	
        	 int days = daysBetween(earlydate,latedate)+1;   
             System.out.println(days);  
        } catch (ParseException e) {   
              e.printStackTrace();   
          }   
        
   }
}
