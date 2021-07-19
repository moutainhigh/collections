package com.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class DoQueryService {

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
	
	
	
	@Autowired
	private TaiWaiGeTiService taiWaiGeTiService;
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List  task01JinNian(String endTime) {
		List list = data01Service.jinNian(endTime);
		return list;
		
	}
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task01QuNian(String begTime) {
		List list =data01Service.quNianTongQi(begTime);
		return list;
	}
	
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02NeiZiYouShiYiLai(String begTime,String endTime) {
		List list =data02Service.getNeiZiQiYe(begTime, endTime, false);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02NeiZiBenQi(String begTime,String endTime) {
		List list =	data02Service.getNeiZiQiYe(begTime, endTime, true);
		return list;
	}
	
	
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02SiYingYouShiYiLai(String begTime,String endTime) {
		List list =data02Service.getSiYingQiYe(begTime, endTime, false);
		return list;
	}
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02SiYingBenQi(String begTime,String endTime) {
		List list =data02Service.getSiYingQiYe(begTime, endTime, true);
		return list;
	}
	
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02WaiZiYouShiYiLai(String begTime,String endTime) {
		List list =data02Service.getWaiZiQiYe(begTime, endTime, false);
		return list;
	}
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task02WaiZiBenQi(String begTime,String endTime) {
		List list =data02Service.getWaiZiQiYe(begTime, endTime, true);
		return list;
	}
	
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task03NianChuDaoBenYue(String begTime,String endTime) {
		List list =data03Service.getListByNianChuDaoBenYue(begTime,endTime);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task03DangQi(String begTime,String endTime) {
		List list =data03Service.getListByDangQi(begTime,endTime);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task04NianChuDaoBenYue(String begTime,String endTime) {
		List list =data04Service.getListByNianChuDaoBenYue(begTime, endTime);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task04DangQi(String begTime,String endTime) {
		List list =data04Service.getListByDangQi(begTime,endTime);
		return list;
	}
	

	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task05YouShiYiLai(String begTime,String endTime) {
		List list =data05Service.getList(begTime,endTime,false);
		return list;
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List task05BenQi(String begTime,String endTime) {
		List list =	data05Service.getList(begTime,endTime,true);
		return list;
	}
	
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public List taskForTaiWan(String begTime,String endTime) {
		List list =	taiWaiGeTiService.getList(begTime,endTime);
		return list;
	}
}



