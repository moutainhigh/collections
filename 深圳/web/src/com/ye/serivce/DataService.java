package com.ye.serivce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.ye.excel.Data01ExcelUtil;
import com.ye.redis.dao.RedisDao;
import com.ye.repo.Data01Repo;
import com.ye.repo.Data02Repo;
import com.ye.repo.Data03Repo;
import com.ye.repo.Data04Repo;
import com.ye.repo.Data05Repo;
import com.ye.repo.TaiWanGeTiRepo;

@EnableAsync
@Component
public class DataService {

	
	@Autowired
	private RedisDao redisDao;
	
	private int mytimeout = 3 * 60 * 60 * 24; // 3天过期
	
	@Autowired
	private Data01Repo data01;
	@Autowired
	private Data02Repo data02;
	@Autowired
	private Data03Repo data03;
	@Autowired
	private Data04Repo data04;
	@Autowired
	private Data05Repo data05;
	@Autowired
	private TaiWanGeTiRepo taiWan;
	
	
	@Async //声明是一个异步方法  
	public void getData01(String month,String fileNamePath) {
		
		String key1 =  "data01_getThisYear" + month;
		List getThisYear = redisDao.get(key1, List.class);
		if(getThisYear==null) {
			getThisYear = data01.getThisYear(month);
			redisDao.add(key1, mytimeout, getThisYear);
		}
		 
		
		String key2 = "data01_lastYear" + month;
		List lastYear = redisDao.get(key2, List.class);
		if(lastYear==null) {
			lastYear = data01.getLastYear(month);
			redisDao.add(key2, mytimeout, lastYear);
		}
				
		
		try {
			if(getThisYear!=null&&lastYear!=null) {
				Data01ExcelUtil.createExcell(getThisYear, lastYear, month, fileNamePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Async //声明是一个异步方法  
	public void getData02(String month) {
		
		String key1 = "data02_orderList" + month;
		List orderList = redisDao.get(key1, List.class);
		if(orderList==null) {
			orderList = data02.getJianGuanSuoPaiXuByZongShu(month,true);
			redisDao.add(key1, mytimeout, orderList);
		}
		
		
		String key2= "data02_orderListYouShiYiLai" + month;
		List orderListYouShiYiLai = redisDao.get(key2, List.class);
		if(orderListYouShiYiLai==null) {
			orderListYouShiYiLai = 	data02.getJianGuanSuoPaiXuByZongShu(month,false);
			redisDao.add(key2, mytimeout, orderListYouShiYiLai);
		}
		
		
		
		String key3= "data02_getNeiZiFromJanuary" + month;
		List getNeiZiFromJanuary =  redisDao.get(key3, List.class);
		if(getNeiZiFromJanuary==null) {
			getNeiZiFromJanuary = data02.getNeiZiFromJanuary(month, orderList);
			redisDao.add(key3, mytimeout, getNeiZiFromJanuary);
		}
		
		
		String key4= "data02_getSiYingFromJanuary" + month;
		List getSiYingFromJanuary =  redisDao.get(key4, List.class);
		if(getSiYingFromJanuary==null) {
			getSiYingFromJanuary =  data02.getSiYingFromJanuary(month, orderList);
			redisDao.add(key4, mytimeout, getSiYingFromJanuary);
		}
		
		String key5= "data02_getWaiZiFromJanuary" + month;
		List getWaiZiFromJanuary =  redisDao.get(key5, List.class);
		if(getWaiZiFromJanuary==null) {
			getWaiZiFromJanuary =  data02.getWaiZiFromJanuary(month, orderList);
			redisDao.add(key5, mytimeout, getWaiZiFromJanuary);
		}
		
		
		
		String key6= "data02_getNeiZiYouShiYiLai" + month;
		List getNeiZiYouShiYiLai = redisDao.get(key6, List.class);
		if(getNeiZiYouShiYiLai==null) {
			getNeiZiYouShiYiLai = data02.getNeiZiYouShiYiLai(month, orderListYouShiYiLai);
			redisDao.add(key6, mytimeout, getNeiZiYouShiYiLai);
		}
		
		String key7= "data02_getSiYingYouShiYiLai" + month;
		List getSiYingYouShiYiLai = redisDao.get(key7, List.class);
		if(getSiYingYouShiYiLai==null) {
			getSiYingYouShiYiLai = data02.getSiYingYouShiYiLai(month, orderListYouShiYiLai);
			redisDao.add(key7, mytimeout, getSiYingYouShiYiLai);
		}
		
		String key8= "data02_getWaiZiYouShiYiLai" + month;
		List getWaiZiYouShiYiLai = redisDao.get(key8, List.class);
		if(getWaiZiYouShiYiLai==null) {
			getWaiZiYouShiYiLai = 	data02.getWaiZiYouShiYiLai(month, orderListYouShiYiLai);
			redisDao.add(key8, mytimeout, getWaiZiYouShiYiLai);
		}
		
		
		
		
	}
	
	@Async //声明是一个异步方法  
	public void getData03(String month) {
		String key1= "data03_getBenQi" + month;
		List getBenQi = redisDao.get(key1, List.class);
		if(getBenQi==null) {
			getBenQi = 	 data03.getBenQi(month);
			redisDao.add(key1, mytimeout, getBenQi);
		}
		
		String key2= "data03_getLastYearTongQi" + month;
		List getLastYearTongQi = redisDao.get(key2, List.class);
		if(getLastYearTongQi==null) {
			getLastYearTongQi=  data03.getLastYearTongQi(month);
			redisDao.add(key2, mytimeout, getLastYearTongQi);
		}
		
		String key3= "data03_getThisYearFromJanuary" + month;
		List getThisYearFromJanuary = redisDao.get(key3, List.class);
		if(getThisYearFromJanuary==null) {
			getThisYearFromJanuary= data03.getThisYearFromJanuary(month);
			redisDao.add(key3, mytimeout, getThisYearFromJanuary);
		}
		
		
		
		String key4= "data03_getLastYearFromJanuary" + month;
		List getLastYearFromJanuary =redisDao.get(key4, List.class);
		if(getLastYearFromJanuary==null) {
			getLastYearFromJanuary = data03.getLastYearFromJanuary(month);
			redisDao.add(key4, mytimeout, getLastYearFromJanuary);
		}
		
		
		
		
		
	}
	@Async //声明是一个异步方法  
	public void getData04(String month) {
		
		String key1 = "data04_getBenQi" + month;
		List getBenQi =redisDao.get(key1, List.class);
		if(getBenQi==null) {
			getBenQi =data04.getBenQi(month);
			redisDao.add(key1, mytimeout, getBenQi);
		}
		
		
		String key2 = "data04_getLastYearTongQi" + month;
		List getLastYearTongQi =redisDao.get(key2, List.class);
		if(getLastYearTongQi==null) {
			getLastYearTongQi =data04.getLastYearTongQi(month);
			redisDao.add(key2, mytimeout, getLastYearTongQi);
		}

		String key3 = "data04_getThisYearFromJanuary" + month;
		List getThisYearFromJanuary = redisDao.get(key3, List.class);
		if(getThisYearFromJanuary==null) {
			getThisYearFromJanuary = data04.getThisYearFromJanuary(month);
			redisDao.add(key3, mytimeout, getThisYearFromJanuary);
			
		}
		
		String key4  = "data04_getLastYearFromJanuary" + month;
		List getLastYearFromJanuary = redisDao.get(key4, List.class);
		if(getLastYearFromJanuary==null) {
			getLastYearFromJanuary = data04.getLastYearFromJanuary(month);
			redisDao.add(key4, mytimeout, getLastYearFromJanuary);
		}
	}
	
	
	@Async //声明是一个异步方法  
	public void getData05(String month) {
		String key1  = "data05_getThisYearFromJanuary" + month;
		List getThisYearFromJanuary =redisDao.get(key1, List.class);
		if(getThisYearFromJanuary==null) {
			getThisYearFromJanuary = data05.getThisYearFromJanuary(month);
			redisDao.add(key1, mytimeout, getThisYearFromJanuary);
		}
		
		String key2  = "data05_getYouShiYiLai" + month;
		List getYouShiYiLai =redisDao.get(key2, List.class);
		if(getYouShiYiLai==null) {
			getYouShiYiLai = data05.getYouShiYiLai(month);
			redisDao.add(key2, mytimeout, getYouShiYiLai);
		}
		
	}
	
	@Async //声明是一个异步方法  
	public void getData06Taiwan(String month) {
		String key1  = "data06_taiwan" + month;
		List getList =redisDao.get(key1, List.class);
		if(getList==null) {
			getList = taiWan.getList(month);
			redisDao.add(key1, mytimeout, getList);
		}
	
	}
	
}
