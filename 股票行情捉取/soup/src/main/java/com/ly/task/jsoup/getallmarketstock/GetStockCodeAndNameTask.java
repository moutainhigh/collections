package com.ly.task.jsoup.getallmarketstock;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.core.GetStockCodeAndNameByTxtFile;
import com.ly.dao.impl.StockDao;
import com.ly.pinyin.YePinYinUtils;
import com.ly.pojo.Stock;
@Component
public class GetStockCodeAndNameTask {
	//private static Logger logger = Logger.getLogger(GetStockCodeAndNameTask.class);
	
	private static Logger logger = Logger.getLogger("getStockCode");
	
	@Autowired
	private StockDao stockImpl;
	
	@Autowired
	private GetStockCodeAndNameByTxtFile getStockCodeAndNameByTxtFile;
	
	
	
	@Scheduled(cron="*/10 * * * * ?")
	public void getStockCodeAndNameByTxtFile(){
		long startTime = System.currentTimeMillis();    //获取开始时间
		List list = getStockCodeAndNameByTxtFile.getMarketAllStockCodeAndNameByTxtFile();
		List<Stock> stockParamList =  null;
		if(list!=null&&list.size()>0){
			stockParamList = new ArrayList<Stock>(); 
			logger.debug("执行导入沪A和深A股票所有股票代码数据");
			stockImpl.truncateTable(Stock.class);
			
			String code = null;
			String name = null;
			
			String hangYe = "";
			String diQu= "";
			String liuTongGu = "";
			String shiyinglvJing = "";
			String zongGuBen = "";
			
			
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
				
				stockParamList.add(hand);
				
			}
			stockImpl.batchSave(stockParamList);
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
