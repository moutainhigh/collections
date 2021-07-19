package com.ly.task.dayclose;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.dao.DayLineUpByAutoDao;
import com.ly.dao.StockDao;
import com.ly.pojo.StockDayLineUpByAuto;
import com.ly.pojo.Stocks;

import redis.RedisDao;

//https://blog.csdn.net/liuwei0376/article/details/13620879
@Component
public class GetDayUpService {
	private static Logger calcdayline = Logger.getLogger("dayline");
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private StockDao stockDao;
	@Autowired
	private DayLineUpByAutoDao dayLineDao;
	
	public void taskGetDayUpStock(){
		try {
			dayLineDao.truncateTable();
			Stocks sts = null;
			/* Calendar calendar1 = Calendar.getInstance();
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
			// SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			 calendar1.add(Calendar.DATE, -3);
			 String three_days_ago = dateFormat.format(calendar1.getTime());
			 
			 System.out.println(three_days_ago);*/
			List<StockDayLineUpByAuto> infoParamList = null;
			StockDayLineUpByAuto dayInfo = null;
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
			String currentTime = dateFormat.format(date);
			String threeDaysAgo  =getWorkDay(date,3);
			long startTime = System.currentTimeMillis();    //获取开始时间
			int total = stockDao.getTotalsForDay();
			Map pageMap = new HashMap();
			int rows = 150;
			int step = total / rows + 1;
			for (int i = 1; i <= step; i++) {
				
				infoParamList = new ArrayList<StockDayLineUpByAuto>();
				
				pageMap.put("pageIndex", (i - 1) * rows);
				pageMap.put("pageSize", rows);
				calcdayline.info("当前迭代次数==========>  " +  i +" ,还需要"+(step-i)+" 次迭代计算");
				List<Stocks> stocks = stockDao.getListForDay(pageMap);
				if (stocks != null && stocks.size() > 0) {
					for (int j = 0; j < stocks.size(); j++) {
						sts = stocks.get(j);
						String code = sts.getCode();
						String currentDayKey = code +"_"+ currentTime;
						String threeDayKey = code  +"_" + threeDaysAgo;
						Map<String,String> currentDayKeyMap = redisDao.get(currentDayKey,Map.class);
						Map<String,String> threeDayKeyMap = redisDao.get(threeDayKey,Map.class);
						String threeDayKclosePrice ="0.0";
						if(threeDayKeyMap!=null){
							threeDayKclosePrice=  threeDayKeyMap.get("closePrice");
							
						}
						if(currentDayKeyMap!=null){
							String close = currentDayKeyMap.get("closePrice");
							String name = currentDayKeyMap.get("name");
							String prevClose = currentDayKeyMap.get("prevClose");
							
							String maxPrice =  currentDayKeyMap.get("maxPrice");
							String minPrice =  currentDayKeyMap.get("minPrice");
							String openPrice =  currentDayKeyMap.get("open");
							
							Double  d1 = 0.0;
							Double d2 = 0.0;
							Double d3 = 0.0;
							if(!close.contains("-")){
								d1 = Double.valueOf(close);
							}
							if(!prevClose.contains("-")){
								d2  = Double.valueOf(prevClose);
							}
							if(!prevClose.contains("-")){
								 d3 = Double.valueOf(threeDayKclosePrice);
							}
							
							Double ret1 = d1-d2;
							Double ret2 = d2-d3;
							
							if(ret1>0&&ret2>0){
								dayInfo = new StockDayLineUpByAuto();
								
								dayInfo.setCode(code);
								dayInfo.setName(name);
								dayInfo.setClosePrice(close);
								dayInfo.setPrevClose(prevClose);
								dayInfo.setMinPrice(minPrice);
								dayInfo.setMaxPrice(maxPrice);
								dayInfo.setOpenPrice(openPrice);
								
								infoParamList.add(dayInfo);
							}
						}
						calcdayline.info("系统自动计算到第【" + (j + 1) + "】,还有 " + (stocks.size() - (j + 1)) + "条待完成");
					}
					if (infoParamList != null && infoParamList.size() > 0) {
						this.batchCommitDB(1000,infoParamList);
						//dayLineDao.insertBatch(infoParamList);
					}
					
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	public static String getWorkDay(Date startDate, int workDay) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		for (int i = 0; i < workDay; i++) {
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
			if (Calendar.SATURDAY == c1.get(Calendar.SATURDAY) || Calendar.SUNDAY == c1.get(Calendar.SUNDAY)) {
				workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
				continue;
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
		c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
//		System.out.println(df.format(c1.getTime()) + " " + getWeekOfDate(c1.getTime()));
		return df.format(c1.getTime());
	}
	
	
	private <T> void batchCommitDB(int commitCountEveryTime, List<T> list) {
		int commitCount = (int) Math.ceil(list.size() / (double) commitCountEveryTime);
		List<T> tempList = new ArrayList<T>(commitCountEveryTime);
		int start, stop;
		Long startTime = System.currentTimeMillis();
		for (int i = 0; i < commitCount; i++) {
			tempList.clear();
			start = i * commitCountEveryTime;
			stop = Math.min(i * commitCountEveryTime + commitCountEveryTime - 1, list.size() - 1);
			for (int j = start; j <= stop; j++) {
				tempList.add(list.get(j));
			}
			dayLineDao.insertBatch(tempList);
		}
		Long endTime = System.currentTimeMillis();
		calcdayline.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
	}

}
