package com.ly.task.getmonthline.byauto.soup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.dao.MonthLineUpByAutoDao;
import com.ly.dao.MonthLineUpByAutoDataDao;
import com.ly.dao.StockDao;
import com.ly.dao.WeekLineUpByAutoDataDao;
import com.ly.pojo.StockMonthLineByAutoDataList;
import com.ly.pojo.StockMonthLineUpByAuto;
import com.ly.pojo.StockWeekLineByAutoDataList;
import com.ly.pojo.Stocks;
import com.ly.stocktrade.getmonth.GetMonthLineInfoByTencent;
import com.ly.stocktrade.getweek.byauto.GetWeekLineInfoByTencent;
import com.ly.task.getmonthline.byauto.MonthLineCalcByByAutoService;

//https://blog.csdn.net/dragonpeng2008/article/details/70256800

//@Component
public class GetMonthLineInfoByTencentTask {
	private static Logger logger = Logger.getLogger("month");
	@Autowired
	private StockDao stockDao;
	@Autowired
	private GetMonthLineInfoByTencent getWeekLineInfoByTencent;

	@Autowired
	private MonthLineUpByAutoDataDao stockMonthDataListDao;

	
	@Autowired
	private MonthLineCalcByByAutoService monthLineCalcTaskByByAuto;

	// @Scheduled(cron="*/5 * * * * ?")
	// @Scheduled(cron="40 33 12 ? * SUN")
	// @Scheduled(cron="40 41 9 ? * MON")
	public void task() throws InterruptedException {
		logger.debug("开始获取周线数据=====>采集开始");
		long startTime = System.currentTimeMillis(); // 获取开始时间
		stockMonthDataListDao.truncateTableData();
		logger.info("开始获取周线数据=====>采集开始");
		Stocks sb = null;
		List<StockMonthLineByAutoDataList> infoParamList = null;
		StockMonthLineByAutoDataList weekInfo = null;
		int total = stockDao.getTotalsForDay();
		Map<String, Object> pageMap = new HashMap<String, Object>();
		int rows = 150;
		int step = total / rows + 1;
		for (int i = 1; i <= step; i++) {
			pageMap.put("pageIndex", (i - 1) * rows);
			pageMap.put("pageSize", rows);
			logger.info("当前迭代次数==========>  " + i + " ,还需要" + ((step - i) + 1) + " 次迭代爬虫");
			if (i > 1) {
				Thread.sleep(1000 * 10);
			}
			List<Stocks> sbLists = stockDao.getListForDay(pageMap);
			if (sbLists != null && sbLists.size() > 0) {
				for (int j = 0; j < sbLists.size(); j++) {
					infoParamList = new ArrayList<StockMonthLineByAutoDataList>();
					if (i % 15 == 0 && i > 0) {
						Thread.sleep(1000 * 10);
					}
					sb = sbLists.get(i);
					logger.info("需要获取周线数据的股票代码【 " + sb.getCode() + " 】，名称 【" + sb.getName() + "】,所属市场编码 【 " + sb.getMarketType() + " 】");
					List<Map<String, String>> resultList = getWeekLineInfoByTencent.getWeekLineInfo(sb.getCode());
					logger.info("当前爬虫到第" + (i + 1) + "条数据,共" + sbLists.size() + "条数据，还有" + (sbLists.size() - (i + 1)) + "条数据没爬虫到，当前代码【" + sb.getCode() + "】,名称【" + sb.getName() + "】返回信息" + resultList);
					if (resultList != null && resultList.size() > 0) {
						for (Map<String, String> temp : resultList) {
							weekInfo = new StockMonthLineByAutoDataList();
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
					// stockWeekLineInfoDao.deleteByBatch(infoParamList);
					// stockWeekLineInfoDao.insertBatch(infoParamList);
					// this.batchDelCommit(1000,infoParamList);
					if (infoParamList != null && infoParamList.size() > 0) {
						this.batchCommit(1000, infoParamList);
					}

				}
			}
		}
		long endTime = System.currentTimeMillis(); // 获取结束时间
		long time = endTime - startTime;
		double hour = time / 1000 / 60.0 / 60.0;
		logger.debug("程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间

		//////////////
		monthLineCalcTaskByByAuto.getBuyStockInfo();
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
			stockMonthDataListDao.insertBatch(tempList);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
	}


}
