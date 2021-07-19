package com.ly.task.jsoup.getallmarketstock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.core.GetStockCodeAndNameByTxtWithCondition;
import com.ly.dao.impl.StockDao;
import com.ly.pinyin.YePinYinUtils;
import com.ly.pojo.Stock;


//除掉停牌的和*ST的

@Component
public class GetStockCodeAndNameByConditionTask {
	
	private static Logger logger = Logger.getLogger("getStockCode");
	
	@Autowired
	private StockDao stockImpl;
	
	
	@Autowired
	private GetStockCodeAndNameByTxtWithCondition getStockWeekLineUpByHandCore;
	
	
	
	//@Scheduled(cron="0 0/30 * * * ?")
	//@Scheduled(cron="0/10 * * * * ?")
	//@Scheduled(cron="0/50 * * * * ?")
	//@Scheduled(cron="0 0/10 * * * ?")
	@Scheduled(cron="0/45 * * * * ?")
	public void getStockCodeAndNameByTxtFile(){
		
		long startTime = System.currentTimeMillis();    //获取开始时间
		List list = getStockWeekLineUpByHandCore.getWeekLineUpByTxtFile();
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
		String diQu= "";
		String liuTongGu = "";
		String shiyinglvJing = "";
		String zongGuBen = "";
		
		List<Stock> paramList = null;
		if(list!=null&&list.size()>0){
			
			paramList = new ArrayList<Stock>(); 
			logger.debug("执行导入沪A和深A股票所有股票代码数据");
			stockImpl.truncateTable(Stock.class);
			
			
			logger.debug("===>执行自定义选股公式任务，开始执行自定义选股公式数据");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String timeStrs = df.format(new Date());
			for (int i = 0; i < list.size(); i++) {
				logger.debug("读取的数据===> " +list.get(i));
				//String [] temp = list.get(i).toString().split("	");
				String tempStr = list.get(i).toString();
				
				String [] temp = tempStr.split("	");
				code = temp[0];
				name =temp[1];
				
				hangYe = temp[18];
				diQu = temp[19];
				liuTongGu= temp[32].trim(); //亿
				//shiyinglvDong= temp[46];
				shiyinglvJing= temp[47].trim();
				zongGuBen= temp[63].trim();
				
				
				
				Stock hand = new Stock();
				
				hand.setName(name);
				hand.setCode(code);
				hand.setHangye(hangYe);
				hand.setDiQu(diQu);
				hand.setLiuTongGu(liuTongGu);
				hand.setShiyinglvJing(shiyinglvJing);
				hand.setZongGuBen(zongGuBen);
				
				if(code.indexOf("6")==0){
					hand.setPrefix("sh");
				}else{
					hand.setPrefix("sz");
				}
				
				
				if(code.indexOf("000")==0||code.indexOf("001")==0){
					hand.setExchangeType("2");
					hand.setMarketType("4609");
				}else if(code.indexOf("002")==0){
					hand.setExchangeType("2");
					hand.setMarketType("4614");
				}else if(code.indexOf("300")==0){
					hand.setExchangeType("2");
					hand.setMarketType("4621");
				}else if(code.indexOf("60")==0){
					hand.setExchangeType("1");
					hand.setMarketType("4353");
				}
				
				
				String nameStr = name;
				if(nameStr.contains("Ａ")){
					nameStr = nameStr.replace("Ａ", "A");
				}
				YePinYinUtils.convertChineseToPinyin(nameStr);
				String headP = YePinYinUtils.getHeadPinyin();
				hand.setPinyin(headP);
				
				paramList.add(hand);
				
			}
			///insert here
			
			
			stockImpl.batchSave(paramList);
			logger.debug("===>读取完成");
			
			stockImpl.deleKeChuang();
			long endTime = System.currentTimeMillis();    //获取结束时间
			long time = endTime - startTime;
			double hour = time/1000/60.0/60.0;
			
			
			logger.debug("程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间
			logger.info("系统运行完成");
				
		}
		
		
	}
	
	
}
