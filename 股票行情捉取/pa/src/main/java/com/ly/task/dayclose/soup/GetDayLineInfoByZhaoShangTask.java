package com.ly.task.dayclose.soup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.DayLineUpByAutoDao;
import com.ly.dao.StockDao;
import com.ly.pojo.StockDayLineUpByAuto;
import com.ly.pojo.Stocks;
import com.ly.stocktrade.getdayline.GetDayLineByZhaoShangInfo;
import com.ly.task.dayclose.GetDayUpService;

import redis.RedisDao;

//https://blog.csdn.net/wolfies/article/details/79441564
@Component
public class GetDayLineInfoByZhaoShangTask {
	private static Logger dayline = Logger.getLogger("dayline");
	
	@Autowired
	private StockDao stockDao;

	@Autowired
	private RedisDao redisDao;

	private static int recordeTimeOut = 60 * 60 * 24 * 60;// 10秒 10,现在保存60天

	@Autowired
	private GetDayLineByZhaoShangInfo zhaoShang;
	
	@Autowired
	private GetDayUpService getDayUpService;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//@Scheduled(cron = "*/10 * * * * ?")
	 @Scheduled(cron="20 10 17 ? * TUE,THU")
	public void task01() throws Exception {
		long startTime = System.currentTimeMillis();    //获取开始时间
		int total = stockDao.getTotalsForDay();
		Map pageMap = new HashMap();
		Map changeMap = null;
		int rows = 150;
		int step = total / rows + 1;
		for (int i = 1; i <= step; i++) {
			pageMap.put("pageIndex", (i - 1) * rows);
			pageMap.put("pageSize", rows);
			dayline.info("当前迭代次数==========>  " +  i +" ,还需要"+((step-i)+1)+" 次迭代爬虫");
			if(i>1){
				Thread.sleep(1000*40);
			}
			List<Stocks> stocks = stockDao.getListForDay(pageMap);
			if (stocks != null && stocks.size() > 0) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
				Date date = new Date();
				dayline.info("当前时间是：" + dateFormat.format(date));
				List list = zhaoShang.getBatchList(stocks);
				if(list!=null&&list.size()>0){
					for (int j = 0; j < list.size(); j++) {
						ArrayList temp = (ArrayList) list.get(j);
						Double currentPrice = (Double) temp.get(1);
						String name = (String) temp.get(4);
						String code = (String) temp.get(6);
						Double open = (Double) temp.get(12);
						Double prevClose = (Double) temp.get(13);
						Double maxPrice = (Double) temp.get(14);
						Double minPrice = (Double) temp.get(15);
						
						
						changeMap = new HashMap();
						changeMap.put("name", name);
						changeMap.put("code", code);
						changeMap.put("open", open);
						changeMap.put("maxPrice", maxPrice);
						changeMap.put("minPrice", minPrice);
						changeMap.put("prevClose", prevClose);
						changeMap.put("closePrice", currentPrice);
						changeMap.put("date", dateFormat.format(date));
						
						String keys = code + "_" + dateFormat.format(date);
						dayline.info("生成的key " + keys + "===>  存的内容为  ：   " + changeMap);
						redisDao.add(keys, recordeTimeOut, changeMap);
					}
				}
			}
		}
		long endTime = System.currentTimeMillis();    //获取结束时间
		long time = endTime - startTime;
		double hour = time/1000/60.0/60.0;
		dayline.debug("程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间
		dayline.info("系统运行完成");
		
		getDayUpService.taskGetDayUpStock();
				
	}
	
}

