package com.ly.task.getweeklineinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.batch.mybatis.BatchUtil;
import com.ly.dao.StockBasicDao;
import com.ly.dao.StockWeekLineInfoDao;
import com.ly.pojo.StockBasic;
import com.ly.pojo.StockWeekLineInfo;
import com.ly.stocktrade.getweekline.GetWeekLineInfoByTencent;
import com.ly.stocktrade.getweekline.GetWeekLineInfoByPingAn;

//https://blog.csdn.net/dragonpeng2008/article/details/70256800
//https://www.jb51.net/article/107461.htm

@Component
public class GetWeekLineInfoTaskByTencent {
	private static Logger logger = Logger.getLogger(GetWeekLineInfoTaskByTencent.class);
	
	@Autowired
	private StockBasicDao stockBasicDao;
	
	@Autowired
	private StockWeekLineInfoDao stockWeekLineInfoDao;
	@Autowired
	private GetWeekLineInfoByTencent getWeekLineInfoByTencent;
	
	@Autowired
	private BatchUtil batchUtil;
	
	
	
	
	
	//@Scheduled(cron="*/5 * * * * ?")
	//@Scheduled(cron="50 48 21 ? * SUN")
	//@Scheduled(cron="0 54 21 ? * SUN")
	@Scheduled(cron="0 20 9 ? * MON")
	public void task() throws InterruptedException{
		logger.debug("开始获取周线数据=====>采集开始");
		long startTime = System.currentTimeMillis();    //获取开始时间
		StockWeekLineInfo  info = null;
		List<StockBasic> list = stockBasicDao.getAll();
		List<StockWeekLineInfo> infoParamList = new ArrayList<StockWeekLineInfo>();
		if(list!=null&&list.size()>0){
			for (StockBasic stockBasic : list) {
				logger.debug("需要获取周线数据的股票代码【 " + stockBasic.getCode() + " 】,所属市场编码 【 " + stockBasic.getMarketType()+" 】");
				//Thread.sleep(1000);//每20秒爬一下数据
				List<Map<String,String>> resultList = getWeekLineInfoByTencent.getWeekLineInfo(stockBasic.getCode());
				logger.debug(resultList);
				if(resultList!=null&&resultList.size()>0){
					for (Map<String,String> temp : resultList) {
						info  = new StockWeekLineInfo();
						info.setCode(stockBasic.getCode());
						info.setName(stockBasic.getName());
						info.setOpenPrice(temp.get("openPrice"));
						
						info.setClosePrice(temp.get("closePrice"));
						info.setMinPrice(temp.get("minPrice"));
						info.setMaxPrice(temp.get("maxPrice"));
						info.setMa5(temp.get("ma5"));
						info.setMa10(temp.get("ma10"));
						info.setMa30(temp.get("ma30"));
						info.setDate(temp.get("date"));
						
						infoParamList.add(info);
					}
					
				}
			}
			long endTime = System.currentTimeMillis();    //获取结束时间
			logger.debug("程序运行时间：" + (endTime - startTime) + "毫秒 , 共 "+(endTime - startTime)/1000/60  + "分");    //输出程序运行时间
			//stockWeekLineInfoDao.deleteByBatch(infoParamList);
		//	stockWeekLineInfoDao.insertBatch(infoParamList);
			
			
			stockWeekLineInfoDao.truncateTableData();
			batchUtil.batchCommit("com.ly.dao.StockWeekLineInfoDao.insertBatch",1000,infoParamList);
		}
	}
}
