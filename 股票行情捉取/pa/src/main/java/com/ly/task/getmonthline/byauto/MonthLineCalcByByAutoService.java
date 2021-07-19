package com.ly.task.getmonthline.byauto;

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
import com.ly.pojo.StockMonthLineByAutoDataList;
import com.ly.pojo.StockMonthLineUpByAuto;
import com.ly.pojo.Stocks;

@Component
public class MonthLineCalcByByAutoService {
	private static Logger calcmonth = Logger.getLogger("calcmonth");
	@Autowired
	private StockDao stockDao;

	@Autowired
	private MonthLineUpByAutoDataDao stockMonthDataListDao;

	@Autowired
	private MonthLineUpByAutoDao weekLineUpDao;

	public void getBuyStockInfo() {
		calcmonth.info("定时采集月线向上的信息 ===自动化");
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map map = null;
		weekLineUpDao.truncateTable();
		Stocks stocks = null;
		StockMonthLineUpByAuto infos = null;
		Map<String, Object> pageMap = new HashMap<String, Object>();
		int total = stockDao.getTotalsForDay();
		int rows = 150;
		int step = total / rows + 1;

		List<StockMonthLineUpByAuto> upList = null;
		StockMonthLineByAutoDataList firstWeek1 = null;
		StockMonthLineByAutoDataList firstWeek2 = null;
		StockMonthLineByAutoDataList firstWeek3 = null;
		for (int i = 1; i <= step; i++) {
			pageMap.put("pageIndex", (i - 1) * rows);
			pageMap.put("pageSize", rows);
			calcmonth.info("当前迭代次数==========>  " + i + " ,还需要" + ((step - i) + 1) + " 次迭代爬虫");
			List<Stocks> lists = stockDao.getListForDay(pageMap);
			upList = new ArrayList<StockMonthLineUpByAuto>();
			if (lists != null && lists.size() > 0) {
				for (int j = 0; j < lists.size(); j++) {
					stocks = (Stocks) lists.get(j);
					map = new HashMap();
					map.put("stocks", stocks);
					List weeks = stockMonthDataListDao.getAll(map);
					if (weeks != null && weeks.size() > 3) {

						firstWeek1 = (StockMonthLineByAutoDataList) weeks.get(0);
						firstWeek2 = (StockMonthLineByAutoDataList) weeks.get(1);
						firstWeek3 = (StockMonthLineByAutoDataList) weeks.get(2);

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
							infos = new StockMonthLineUpByAuto();
							infos.setCode(stocks.getCode());
							infos.setName(stocks.getName());
							infos.setClosePrice(firstWeek1.getClosePrice());
							infos.setOpenPrice(firstWeek1.getOpenPrice());
							infos.setPrevClose(firstWeek2.getClosePrice());
							upList.add(infos);
						}
						calcmonth.info("系统自动计算到第【" + (j + 1) + "】,还有 " + (lists.size() - (j + 1)) + "条待完成");
					}

				}
			}

			if (upList != null && upList.size() > 0) {
				this.batchCommitToDB(1000, upList);
			}
		}
		long endTime = System.currentTimeMillis(); // 获取结束时间
		long time = endTime - startTime;
		double hour = time / 1000 / 60.0 / 60.0;
		calcmonth.debug("程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间
	}

	private <T> void batchCommitToDB(int commitCountEveryTime, List<T> list) {
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
		calcmonth.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
	}
}
