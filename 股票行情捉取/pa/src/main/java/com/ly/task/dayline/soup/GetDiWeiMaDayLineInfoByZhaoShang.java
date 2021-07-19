package com.ly.task.dayline.soup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.common.utils.CalcUtil;
import com.ly.dao.StockDao;
import com.ly.dao.StockDayLineDatasMaDao;
import com.ly.pojo.StockDayLineDatasMa;
import com.ly.pojo.Stocks;
import com.ly.stocktrade.getdaylinema.GetDayLineByZhaoShangMa;
import com.ly.task.dayline.GetDayLineInfoByMaService;

@Component
public class GetDiWeiMaDayLineInfoByZhaoShang {
	private static Logger logger = Logger.getLogger("dayDiWei");

	@Autowired
	private StockDao stockDao;
	@Autowired
	private GetDayLineByZhaoShangMa getWeekLineByZhaoShang;
	@Autowired
	private StockDayLineDatasMaDao stockMonthDataListDao;

	
	@Autowired
	private GetDayLineInfoByMaService monthLineCalcTaskByByAuto;

	//@Scheduled(cron="30 38 21 * * ?")
	@Scheduled(cron = "10 30 17 ? * MON,WED,FRI")
	public void task() throws InterruptedException {
		logger.info("开始获取日线低位MA5线数据=====>采集开始");
		long startTime = System.currentTimeMillis(); // 获取开始时间
		stockMonthDataListDao.truncateTableData();
		Stocks sb = null;
		List<StockDayLineDatasMa> infoParamList = null;
		StockDayLineDatasMa weekInfo = null;
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
					infoParamList = new ArrayList<StockDayLineDatasMa>();
					/*
					 * if(i%15==0&&i>0){ Thread.sleep(1000*10); }
					 */
					sb = sbLists.get(j);
					logger.info("需要获日线数据的股票代码【 " + sb.getCode() + " 】，名称 【" + sb.getName() + "】,所属市场编码 【 " + sb.getMarketType() + " 】");
					List resultList = getWeekLineByZhaoShang.getDayLineInfo(sb.getCode(), sb.getName());
					logger.info("当前爬虫到第" + (j + 1) + "条数据,共" + sbLists.size() + "条数据，还有" + (sbLists.size() - (j + 1)) + "条数据没爬虫到，当前代码【" + sb.getCode() + "】,名称【" + sb.getName() + "】返回信息" + resultList);
					if (resultList != null && resultList.size() > 0) {
						Map map = (Map) resultList.get(0);
						String name = (String) map.get("name");
						String code = (String) map.get("code");
						ArrayList arrayListTemp = (ArrayList) map.get("array");
						if (arrayListTemp != null && arrayListTemp.size() > 0) {
							for (int k = 0; k < arrayListTemp.size(); k++) {
								ArrayList temp = (ArrayList) arrayListTemp.get(k);
								String date = String.valueOf(temp.get(0));
								Double open = (Double) temp.get(1);
								Double close = (Double) temp.get(2);
								Double max = (Double) temp.get(3);
								Double min = (Double) temp.get(4);
								Double prevClose = (Double) temp.get(5);
								Double ma5 = (Double) temp.get(10);
								Double ma10 = (Double) temp.get(11);
								Double ma20 = (Double) temp.get(12);
								Double ma30 = (Double) temp.get(13);
								Double ma60 = (Double) temp.get(14);

								weekInfo = new StockDayLineDatasMa();
								weekInfo.setCode(code);
								weekInfo.setName(name);
								weekInfo.setOpenPrice(CalcUtil.formateDouleToStringFloor(open));
								weekInfo.setClosePrice(CalcUtil.formateDouleToStringFloor(close));
								weekInfo.setDate(date);
								weekInfo.setMaxPrice(CalcUtil.formateDouleToStringFloor(max));
								weekInfo.setMinPrice(CalcUtil.formateDouleToStringFloor(min));

								weekInfo.setMa5(ma5 + "");
								weekInfo.setMa10(ma10 + "");
								weekInfo.setMa20(ma20 + "");
								weekInfo.setMa30(ma30 + "");
								weekInfo.setMa60(ma60 + "");

								infoParamList.add(weekInfo);
							}
						}
					}
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

		/////////////

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
