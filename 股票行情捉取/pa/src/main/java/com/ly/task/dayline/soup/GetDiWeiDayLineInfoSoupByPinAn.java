package com.ly.task.dayline.soup;

/**
 * 用于计算五日线
 * @author Administrator
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.StockDao;
import com.ly.dao.StockDayLineDatasMaDao;
import com.ly.pojo.StockDayLineDatasMa;
import com.ly.pojo.Stocks;
import com.ly.stocktrade.getdaylinema.GetDayLineInfoByPingAnMa;
import com.ly.task.dayline.GetDayLineInfoByMaService;


@Component
public class GetDiWeiDayLineInfoSoupByPinAn {
	private static Logger week = Logger.getLogger("dayDiWei");
	
	@Autowired
	private StockDao stockDao;
	@Autowired
	private StockDayLineDatasMaDao stockWeekInfoDataListDao;
	@Autowired
	private GetDayLineInfoByPingAnMa stockGetDayDataBigInt;
	@Autowired
	private GetDayLineInfoByMaService getWeekStockInfoByAuto;
	
	//https://blog.csdn.net/sanyuesan0000/article/details/47060853
	
	//@Scheduled(cron="30 05 18 ? * TUE,THU")
	//@Scheduled(cron="30 16 20 * * ?")
	@Scheduled(cron="30 30 17 ? * TUE,THU")
	public void taskWeekByBatch() throws InterruptedException{
		week.info("开始获取日线低位MA5数据=====>采集开始");
		long startTime = System.currentTimeMillis();    //获取开始时间
		stockWeekInfoDataListDao.truncateTableData();
		List<StockDayLineDatasMa> infoParamList = null;
		Stocks sb = null;
		StockDayLineDatasMa weekInfo = null;
		Map<String,Object> pageMap = new HashMap<String,Object> ();
		int total = stockDao.getTotalsForDay();
		int rows = 150;
		int step = total / rows + 1;
		for (int i = 1; i <= step; i++) {
			pageMap.put("pageIndex", (i - 1) * rows);
			pageMap.put("pageSize", rows);
			week.info("当前迭代次数==========>  " +  i +" ,还需要"+((step-i)+1)+" 次迭代爬虫");
			if(i>1){
				Thread.sleep(1000*10);
			}
			List<Stocks> sbLists = stockDao.getListForDay(pageMap);
			if(sbLists!=null&&sbLists.size()>0){
				for (int j = 0; j < sbLists.size(); j++) {
					infoParamList =new ArrayList<StockDayLineDatasMa>();
					/*if(i%15==0&&i>0){
						Thread.sleep(1000*10);
					}*/
					sb = sbLists.get(j);
					week.info("需要获取MA5数据的股票代码【 " + sb.getCode() + " 】，名称 【"+sb.getName()+"】,所属市场编码 【 " + sb.getMarketType()+" 】");
					List<Map<String,String>> resultList = stockGetDayDataBigInt.getDayLineMaInfo(sb.getCode(), sb.getMarketType());
					week.info("当前爬虫到第" +(j+1) +"条数据,共"+sbLists.size()+"条数据，还有" +(sbLists.size()-(j+1)) +"条数据没爬虫到，当前代码【"+sb.getCode()+"】,名称【"+sb.getName()+"】返回信息"+ resultList);
					if(resultList!=null&&resultList.size()>0){
						for (Map<String,String> temp : resultList) {
							weekInfo  = new StockDayLineDatasMa();
							weekInfo.setCode(sb.getCode());
							weekInfo.setName(sb.getName());
							
							
							weekInfo.setOpenPrice(temp.get("openPrice"));
							weekInfo.setClosePrice(temp.get("closePrice"));
							weekInfo.setMinPrice(temp.get("minPrice"));
							weekInfo.setMaxPrice(temp.get("maxPrice"));
							weekInfo.setMa5(temp.get("ma5"));
							weekInfo.setMa10(temp.get("ma10"));
							weekInfo.setMa30(temp.get("ma30"));
							weekInfo.setDate(temp.get("date"));
							
							infoParamList.add(weekInfo);
						}
						
					}
					//stockWeekLineInfoDao.deleteByBatch(infoParamList);
					
					if (infoParamList != null && infoParamList.size() > 0) {
						this.batchCommit(1000,infoParamList);
					}
				}
				
			}
		}
		long endTime = System.currentTimeMillis();    //获取结束时间
		long time = endTime - startTime;
		double hour = time/1000/60.0/60.0;
		week.debug("程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间
		week.info("系统运行完成");
		
		getWeekStockInfoByAuto.getBuyStockInfo();
	}
	
	
	
	
	

	private <T> void batchCommit(int commitCountEveryTime, List<T> list) {
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
			stockWeekInfoDataListDao.insertBatch(tempList);
		}
		Long endTime = System.currentTimeMillis();
		week.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
	}
	
	
	
	
}
