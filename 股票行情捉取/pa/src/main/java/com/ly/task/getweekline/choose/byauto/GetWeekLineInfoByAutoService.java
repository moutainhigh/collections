package com.ly.task.getweekline.choose.byauto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.dao.StockDao;
import com.ly.dao.WeekLineUpByAutoDao;
import com.ly.dao.WeekLineUpByAutoDataDao;
import com.ly.pojo.StockWeekLineByAutoDataList;
import com.ly.pojo.StockWeekLineUpByAuto;
import com.ly.pojo.Stocks;

@Component
public class GetWeekLineInfoByAutoService {
	private static Logger logger = Logger.getLogger("calcweek");
	@Autowired
	private StockDao stockDao;

	@Autowired
	private WeekLineUpByAutoDataDao stockWeekLineInfoDao;

	@Autowired
	private WeekLineUpByAutoDao weekLineUpDao;

	public void getBuyStockInfo() {
		logger.info("定时采集周线向上的信息 ===自动化");
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> pageMap = new HashMap<String, Object>();
		Map map = null;
		weekLineUpDao.truncateTable();
		Stocks stocks = null;
		StockWeekLineUpByAuto infos = null;
		int total = stockDao.getTotalsForDay();
		int rows = 150;
		int step = total / rows + 1;
		List<StockWeekLineUpByAuto> upList = null;
		StockWeekLineByAutoDataList firstWeek1 = null;
		StockWeekLineByAutoDataList firstWeek2 = null;
		StockWeekLineByAutoDataList firstWeek3 = null;

		for (int i = 1; i <= step; i++) {
			upList = new ArrayList<StockWeekLineUpByAuto>();
			pageMap.put("pageIndex", (i - 1) * rows);
			pageMap.put("pageSize", rows);
			logger.info("当前迭代次数==========>  " + i + " ,还需要" + ((step - i) + 1) + " 次迭代爬虫");
			List<Stocks> lists = stockDao.getListForDay(pageMap);
			if (lists != null && lists.size() > 0) {
				for (int j = 0; j < lists.size(); j++) {
					stocks = (Stocks) lists.get(j);
					map = new HashMap();
					map.put("stocks", stocks);
					List weeks = stockWeekLineInfoDao.getAll(map);
					if (weeks != null && weeks.size() > 3) {

						firstWeek1 = (StockWeekLineByAutoDataList) weeks.get(0);
						firstWeek2 = (StockWeekLineByAutoDataList) weeks.get(1);
						firstWeek3 = (StockWeekLineByAutoDataList) weeks.get(2);

						String p1 = firstWeek1.getClosePrice();
						String p2 = firstWeek2.getClosePrice();
						String p3 = firstWeek3.getClosePrice();

						Double price1 = Double.valueOf(p1);
						Double price2 = Double.valueOf(p2);
						Double price3 = Double.valueOf(p3);

						Double result1 = price1 - price2;
						Double result2 = price2 - price3;

						// logger.debug(result1 + " " +result2 + " " +
						// weeks.toString());
						if (result1 > 0 && result2 > 0) {
							infos = new StockWeekLineUpByAuto();
							infos.setCode(stocks.getCode());
							infos.setName(stocks.getName());
							infos.setClosePrice(firstWeek1.getClosePrice());
							infos.setOpenPrice(firstWeek1.getOpenPrice());
							infos.setPrevClose(firstWeek2.getClosePrice());
							upList.add(infos);
						}
						logger.info("系统自动计算到第【" + (j + 1) + "】,还有 " + (lists.size() - (j + 1)) + "条待完成");
					}
					
				}
				if (upList != null && upList.size() > 0) {
					this.batchCommit(1000, upList);
				}
			}
		}

		long endTime = System.currentTimeMillis(); // 获取结束时间
		long time = endTime - startTime;
		double hour = time / 1000 / 60.0 / 60.0;
		logger.debug("程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间
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
			weekLineUpDao.insertBatch(tempList);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
	}
}
