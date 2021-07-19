package com.ly.task.getstockcodeandname.sys;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.StockDao;
import com.ly.pojo.Stocks;
//https://www.jianshu.com/p/041bec8ae6d3
//https://www.cnblogs.com/admol/articles/4248159.html
//https://www.cnblogs.com/junrong624/p/4239517.html
//https://jingyan.baidu.com/article/7f41ecec0d0724593d095c19.html
@Component
public class GetStockCodeAndNameTask {
	//private static Logger logger = Logger.getLogger(GetStockCodeAndNameTask.class);
	
	private static Logger logger = Logger.getLogger("recharge");
	
	@Autowired
	private StockDao stockBasicDao;
	@Autowired
	private GetStockCodeAndNameByTxtFile getStockCodeAndNameByTxtFile;
	
	
	
	
	
	//@Scheduled(cron="0 0 1 * * ?")
	//@Scheduled(cron="*/15 * * * * ?")
	//@Scheduled(cron="0 */15 * * * ?")
	@Scheduled(cron="*/15 * * * * ?")
	public void getStockCodeAndNameByTxtFile(){
		List list = getStockCodeAndNameByTxtFile.getMarketAllStockCodeAndNameByTxtFile();
		List<Stocks> stockParamList = new ArrayList<Stocks>();
		String code = null;
		String name = null;
		/*String open = null;
		String currentPrice = null;
		String prevClose = null;
		String max = null;
		String min = null;
		String hangYe = null;
		String area = null;
		String shiyinlv = null;*/
		if(list!=null&&list.size()>0){
			logger.info("执行导入沪A和深A股票所有股票代码数据");
			for (int i = 0; i < list.size(); i++) {
				//logger.debug(list.get(i));
				String [] temp = list.get(i).toString().split("	");
				code = temp[0];
				name =temp[1];
			/*	currentPrice = temp[3];
				open = temp[11];
				max = temp[12];
				min = temp[13];
				prevClose = temp[14];
				hangYe = temp[16];
				area = temp[17];*/
				
				Stocks hand = new Stocks();
				
				hand.setName(name);
				hand.setCode(code);
				
				stockParamList.add(hand);
			}
			
			stockBasicDao.truncateTable();
			batchCommit(1000,stockParamList);
		}else{
			logger.debug("暂无存在导出的市场代码数据，系统不更新");
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
			stockBasicDao.insertBatch(tempList);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
	}
	
	
}
