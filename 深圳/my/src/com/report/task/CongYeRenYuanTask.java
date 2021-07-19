package com.report.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.report.bean.ShangShiZhuTiFileDown;
import com.report.dao.RedisDao;
import com.report.service.CongYeRenYuanService;
import com.report.util.UUIDUtils;

@Component
public class CongYeRenYuanTask {
	private int mytimeout = 10 * 60 * 60 * 24; // 90天过期
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private CongYeRenYuanService congYeRenYuanService ;
	
	//@Scheduled(cron="0 0 1 26 * ?")
	//@Scheduled(cron="*/5 * * * * ?")
	//@Scheduled(cron="*/50 * * * * ?")
	@Scheduled(cron="0 0 1 26 * ?")
	public void task(){
		//https://blog.csdn.net/yibanbairimeng/article/details/61416905
		//https://zhidao.baidu.com/question/387015103.html
		//ref  https://zhidao.baidu.com/question/443824693.html
		/*Calendar.DATE： 获取日期，包含年月日。
		Calendar.DAY_OF_MONTH ：获取月份中的某一天。*/
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		String time = sdf.format(date);
		List list = congYeRenYuanService.getList(time);
		
		//存入redis之中
		redisDao.add("cyry" + time, mytimeout, list);
		
		
		String fileTableUUID = UUIDUtils.getUUID32();
		ShangShiZhuTiFileDown fileDown = new ShangShiZhuTiFileDown();
		
		fileDown.setCreateTime(time);
		fileDown.setFileId(fileTableUUID);
		
		
		congYeRenYuanService.save(fileDown);
		
	}
	
	
	//@Scheduled(cron="0 0 3 26 * ?")
	@Scheduled(cron="0 0 3 26 * ?")
	public void taskDele() {
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.MONTH, -3);  //设置为前3月
		dBefore = calendar.getTime();   //得到前3月的时间
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
		String defaultEndDate = sdf.format(dNow); //格式化当前时间
		System.out.println("三个月之前时间======="+defaultStartDate);
		System.out.println("当前时间==========="+defaultEndDate);
		
		congYeRenYuanService.delete(defaultStartDate);
	}

}
