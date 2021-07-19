package com.ye.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ye.bus.ReportCacheToRedisBusAync;

@Component
public class JobTask {
	private static Logger logger = Logger.getLogger(JobTask.class); // 获取logger实例
	
	@Autowired
	private ReportCacheToRedisBusAync reportCacheToRedisBus;


	public String getTime() {
		Date dNow = new Date();   //当前时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); //设置时间格式
		String month = sdf.format(dNow); //格式化当前时间
		return month;
	}
	
	//https://zhidao.baidu.com/question/1994074218098434827.html
	
	//@Scheduled(cron="*/5 * * * * ?")
	//@Scheduled(cron="0 */5 * * * ?")
	@Scheduled(cron="0 */30 * * * ?")
	public void task () throws Exception{
		String month = getTime();
		logger.debug("开始采集===> 采集时间===>" + month);
		reportCacheToRedisBus.queryFromDbToRedis(month); 
		System.out.println("采集完成");
	}
	
}
