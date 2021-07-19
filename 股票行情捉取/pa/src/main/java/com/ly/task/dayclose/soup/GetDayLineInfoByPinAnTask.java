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
import com.ly.stocktrade.getdayline.PingAnWithouAuthInfo;
import com.ly.task.dayclose.GetDayUpService;

import redis.RedisDao;


//https://blog.csdn.net/wolfies/article/details/79441564
@Component
public class GetDayLineInfoByPinAnTask {
	private static Logger dayline = Logger.getLogger("dayline");

	@Autowired
	private StockDao stockDao;

	@Autowired
	private RedisDao redisDao;

	private static int recordeTimeOut = 60 * 60 * 24 * 60;// 10秒  10,现在保存60天

	@Autowired
	private GetDayUpService getDayUpService;

	
	
	
	@Autowired
	private PingAnWithouAuthInfo pingAnWithouAuthInfo;

	 @SuppressWarnings({ "rawtypes", "unchecked" })
	// @Scheduled(cron="00 30 16 ? * MON-FRI")
	//@Scheduled(cron = "*/20 * * * * ?")
	 //@Scheduled(cron="00 22 20 ? * MON-FRI")
	@Scheduled(cron="40 10 17 ? * MON,WED,FRI")
	public void task01() throws InterruptedException  {
		long startTime = System.currentTimeMillis();    //获取开始时间
		int total = stockDao.getTotalsForDay();
		Map pageMap = new HashMap();
		List<Map<String, String>> temps = null;
		Map<String, String> codeMap = null;
		Map returnMap = null;
		Map changeMap = null;
		int rows = 150;
		int step = total / rows + 1;
		// int step = (int) Math.ceil(total / (double) rows);
		for (int i = 1; i <= step; i++) {
			temps = new ArrayList<Map<String,String>>();
			pageMap.put("pageIndex", (i - 1) * rows);
			pageMap.put("pageSize", rows);
			dayline.info("当前迭代次数==========>  " +  i +" ,还需要"+((step-i)+1)+" 次迭代爬虫");
			if(i>1){
				Thread.sleep(1000*40);
			}
			//temps.clear();
			List<Stocks> stocks = stockDao.getListForDay(pageMap);
			if (stocks != null && stocks.size() > 0) {
				for (int j = 0; j < stocks.size(); j++) {
					
					codeMap = new HashMap<String, String>();
					codeMap.put("code", stocks.get(j).getCode());
					codeMap.put("marketType", stocks.get(j).getMarketType());
					temps.add(codeMap);
				}
				
			//	System.out.println(temps);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
				Date date = new Date();
				dayline.info("当前时间是：" + dateFormat.format(date));
				List list = pingAnWithouAuthInfo.getStockInfoByBatch(temps);
				for (int k = 0; k < list.size(); k++) {
					returnMap = (Map) list.get(k);
					String code = (String) returnMap.get("code");
					String keys = code + "_" + dateFormat.format(date);
					String open = (String) returnMap.get("open");
					String maxPrice = (String) returnMap.get("maxPrice");
					String minPrice = (String) returnMap.get("minPrice");
					String prevClose = (String) returnMap.get("prevClose");
					String newPrice = (String) returnMap.get("newPrice");
					String name = (String) returnMap.get("stockName");
					//以昨日收盘价乘以1.1就是今天的涨停价
					//以昨日收盘价乘以0.9就是今天的跌停价
					//昨日收盘价＋（昨日收盘价×10％）＝今日涨停价。小数采用四舍五入
					//前一个交易日收盘价 X1.1倍 涨停 X0.9倍 跌停 分位要四舍五入的
					

					changeMap = new HashMap();
					changeMap.put("open", open);
					changeMap.put("code", code);
					changeMap.put("name", name);
					changeMap.put("maxPrice", maxPrice);
					changeMap.put("minPrice", minPrice);
					changeMap.put("prevClose", prevClose);
					changeMap.put("closePrice", newPrice);
					changeMap.put("date", dateFormat.format(date));
					dayline.info("生成的key " + keys + "===>  存的内容为  ：   " + changeMap);
					//redisDao.add(keys, recordeTimeOut, list.get(k));
					redisDao.add(keys, recordeTimeOut, changeMap);
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
	 
	 
	 
	 
	 
	 
	 
	 
	
	 /*
		public static void main(String[] args) {
			int size = 150;
			int rowNum = 30;
			int step = size / rowNum + 1;
			System.out.println(step);
			for (int i = 1; i < step; i++) {
				System.out.println("第 " + i + "行");

				int cBeg = (i - 1) * rowNum + 1;
				int cEnd = i * rowNum;
				
				for(int k=cBeg;k<=cEnd;k++) {
					System.out.println(k);
				}
			}
		}*/
	/* 
	 calcs(3,stocks);
	 public static <T> void  calcs(int commitCountEveryTime,List<T> list) {
		 	List retList = new ArrayList();
			int commitCount = (int) Math.ceil(list.size() / (double) commitCountEveryTime);
			System.out.println("共 " +commitCount+"提交");
			List<T> tempList = new ArrayList<T>(commitCountEveryTime);
			int start, stop;
			for (int i = 0; i < commitCount; i++) {
				tempList.clear();
				start = i * commitCountEveryTime;
				stop = Math.min(i * commitCountEveryTime + commitCountEveryTime - 1, list.size() - 1);
				//System.out.println(start + "  " + stop);
				for (int j = start; j <= stop; j++) {
					Stocks sts = (Stocks) list.get(j);
					tempList.add((T) sts);
				}
				
				System.out.println(tempList + " i = " + i);
			}
		
		}
	 
	 public static void main(String[] args) {
		 List list = new ArrayList();
			for (int i = 0; i < 100; i++) {
				list.add(""+i);
			}
			 calcs(30,list);
	}*/
	 
	 
	 
	/* int size = 3583;
		int commitCountEveryTime = 50;
		int commitCount = (int) Math.ceil(size / (double) commitCountEveryTime);
		//System.out.println(commitCount);
		int start, stop;
		for (int i = 0; i < commitCount; i++) {
			start = i * commitCountEveryTime;
			stop = Math.min(i * commitCountEveryTime + commitCountEveryTime - 1, size - 1);
			System.out.println(start + "  "  + stop);
		}*/
}



