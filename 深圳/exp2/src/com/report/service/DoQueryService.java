package com.report.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.report.dao.RedisDao;

@Component
@EnableAsync
public class DoQueryService {

	private int mytimeout = 10 * 60 * 60 * 24; // 90天过期
	
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private Data01Service data01Service;
	
	
	
	@Autowired
	private Data02Service data02Service;
	
	
	@Autowired
	private Data03Service data03Service;
	
	
	@Autowired
	private Data04Service data04Service;
	
	
	@Autowired
	private Data05Service data05Service;
	
	
	public String getRedisTimeKeys() {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		String time = sdf.format(date);
		
		
		return time;
	}
	
	
	/*@Autowired
	private TaiWaiGeTiService taiWaiGeTiService;*/
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List  task01JinNian(String endTime) {
		List list = data01Service.jinNian(endTime);
		
		
		String keys01 =  getRedisTimeKeys();
		redisDao.add("tas01JinNian" + keys01, mytimeout, list);
		return list;
		
	}
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task01QuNian(String endTime) {
		List list =data01Service.quNianTongQi(endTime);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task01QuNian" + keys01, mytimeout, list);
		return list;
	}
	
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02NeiZiYouShiYiLai(String begTime,String endTime) {
		List list  = data02Service.getNeiZiQiYe(begTime, endTime, false);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task02NeiZiYouShiYiLai" + keys01, mytimeout, list);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02NeiZiBenQi(String begTime,String endTime) {
		List list =	data02Service.getNeiZiQiYe(begTime, endTime, true);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task02NeiZiBenQi" + keys01, mytimeout, list);
		return list;
	}
	
	
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02SiYingYouShiYiLai(String begTime,String endTime) {
		List list =data02Service.getSiYingQiYe(begTime, endTime, false);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task02SiYingYouShiYiLai" + keys01, mytimeout, list);
		return list;
	}
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02SiYingBenQi(String begTime,String endTime) {
		List list =data02Service.getSiYingQiYe(begTime, endTime, true);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task02SiYingBenQi" + keys01, mytimeout, list);
		return list;
	}
	
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02WaiZiYouShiYiLai(String begTime,String endTime) {
		List list =data02Service.getWaiZiQiYe(begTime, endTime, false);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task02WaiZiYouShiYiLai" + keys01, mytimeout, list);
		return list;
	}
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02WaiZiBenQi(String begTime,String endTime) {
		List list =data02Service.getWaiZiQiYe(begTime, endTime, true);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task02WaiZiBenQi" + keys01, mytimeout, list);
		return list;
	}
	
	//03今年的
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task03NianChuDaoBenYue(String begTime,String endTime) {
		List list =data03Service.getListByNianChuDaoBenYue(begTime,endTime);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task03NianChuDaoBenYue" + keys01, mytimeout, list);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task03DangQi(String begTime,String endTime) {
		List list =data03Service.getListByDangQi(begTime,endTime);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task03DangQi" + keys01, mytimeout, list);
		return list;
	}
	
	
	
	
	
	//去年03的
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task03QuNianDaoBenYue(String begTime,String endTime) {
		List list =data03Service.getListByNianChuDaoBenYue(begTime,endTime);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task03QuNianDaoBenYue" + keys01, mytimeout, list);
		return list;
	}
	//去年03的
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task03QuNianDangQi(String begTime,String endTime) {
		List list =data03Service.getListByDangQi(begTime,endTime);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task03QuNianDangQi" + keys01, mytimeout, list);
		return list;
	}
	
	
	
	
	
	
	
	//04今年部分
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task04NianChuDaoBenYue(String begTime,String endTime) {
		List list =data04Service.getListByNianChuDaoBenYue(begTime, endTime);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task04NianChuDaoBenYue" + keys01, mytimeout, list);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task04DangQi(String begTime,String endTime) {
		List list =data04Service.getListByDangQi(begTime,endTime);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task04DangQi" + keys01, mytimeout, list);
		return list;
	}
	
	
	///04 去年的部分
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task04QuNianNianChuDaoBenYue(String begTime,String endTime) {
		List list =data04Service.getListByNianChuDaoBenYue(begTime, endTime);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task04QuNianNianChuDaoBenYue" + keys01, mytimeout, list);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task04QuNianDangQi(String begTime,String endTime) {
		List list =data04Service.getListByDangQi(begTime,endTime);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task04QuNianDangQi" + keys01, mytimeout, list);
		return list;
	}
	

	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task05YouShiYiLai(String begTime,String endTime) {
		List list =data05Service.getList(begTime,endTime,false);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task05YouShiYiLai" + keys01, mytimeout, list);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task05BenQi(String begTime,String endTime) {
		List list =	data05Service.getList(begTime,endTime,true);
		String keys01 =  getRedisTimeKeys();
		redisDao.add("task05BenQi" + keys01, mytimeout, list);
		return list;
	}
	
	
	/*@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List taskForTaiWan(String begTime,String endTime) {
		List list =	taiWaiGeTiService.getList(begTime,endTime);
		return list;
	}*/
}



