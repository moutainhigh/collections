package com.ly.task.getweekline.choose.byhand;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.WeekLineUpByHandDao;
import com.ly.pojo.StockWeekLineUpByHand;
import com.ly.stocktrade.getweek.byhand.GetStockWeekLineUpByHandCore;


@Component
public class ChooseStockConditionByHandTask {
	private static Logger logger = Logger.getLogger("recharge");
	
	@Autowired
	private WeekLineUpByHandDao getStockWeekLineUpByHandDao;
	
	@Autowired
	private GetStockWeekLineUpByHandCore getStockWeekLineUpByHandCore;
	
	
	
	//@Scheduled(cron="0 0/30 * * * ?")
	//@Scheduled(cron="0/10 * * * * ?")
	//@Scheduled(cron="0/50 * * * * ?")
	//@Scheduled(cron="0 0/10 * * * ?")
	@Scheduled(cron="0/10 * * * * ?")
	public void getStockCodeAndNameByTxtFile(){
		List list = getStockWeekLineUpByHandCore.getWeekLineUpByTxtFile();
		logger.debug("===>执行自定义选股公式任务，开始执行自定义选股公式数据");
		String code = null;
		String name = null;
		String open = null;
		String currentPrice = null;
		String prevClose = null;
		String max = null;
		String min = null;
		String hangYe = null;
		String area = null;
		String shiyinlv = null;
		List<StockWeekLineUpByHand> paramList = new ArrayList<StockWeekLineUpByHand>();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				String [] temp = list.get(i).toString().split("	");
					code = temp[0];
					name =temp[1];
					currentPrice = temp[3];
					open = temp[11];
					max = temp[12];
					min = temp[13];
					prevClose = temp[14];
					hangYe = temp[16];
					area = temp[17];
					
					StockWeekLineUpByHand hand = new StockWeekLineUpByHand();
					
					hand.setName(name);
					hand.setCode(code);
					hand.setClosePrice(currentPrice);
					hand.setMaxPrice(max);
					hand.setMinPrice(min);
					//hand.setArea(area);
					hand.setOpenPrice(open);
					hand.setPrevClose(prevClose);
					//hand.setHangYe(hangYe);
					//hand.setShiYingLv(shiyinlv);
					
					paramList.add(hand);
			}
			
			getStockWeekLineUpByHandDao.truncateTable();
			batchCommit(500,paramList);
				
		}else{
			logger.debug("暂无存在手动导入的周线数据，系统不更新");
		}
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
			getStockWeekLineUpByHandDao.insertBatch(tempList);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
