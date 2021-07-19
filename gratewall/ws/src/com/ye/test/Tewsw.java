package com.ye.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bo.domain.EveryDayTip;
import com.bo.domain.SMSStock;
import com.bo.domain.SMSStockEveryDayDeal;
import com.bo.domain.Stock;
import com.ye.monitor.from.TencentWeb;
import com.ye.service.StockService;

public class Tewsw {
	
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
		
		StockService stockService = ac.getBean(StockService.class);
		TencentWeb tencent = ac.getBean(TencentWeb.class);
		
		List<Stock> stockList = stockService.selectStockAllList();
		Map map = new HashMap();
		map.put("key1", "每日故事");

		int i = (int) (33 * Math.random() + 1);// 1-32的随机数
		EveryDayTip de = stockService.getEveryDayTipById(i);
		map.put("tips", de.getContents());
		System.out.println(i);
		System.out.println(de.getContents());
		stockService.senderEmailByMinute(stockList, map);
		
		
		
		
		
		
		
		//
		
		
		List<SMSStock> stockList2 = stockService.selectAllSMSStockList();
		List<SMSStock> wrapStock = new ArrayList<SMSStock>();
		for (SMSStock dailyStock : stockList2) {
			String str = tencent.smsStockJianYao(dailyStock);
			// log.error(dailyStock.getStockCode() + " , " +
			// str.toString());
			String[] temp = str.split(",");
			SMSStock tempStock = new SMSStock();

			tempStock.setStockName(temp[0]);
			tempStock.setStockCode(dailyStock.getStockCode());
			// tempStock.setStockCode(temp[1]);
			tempStock.setCurrentPrice(Double.valueOf(temp[2]));
			tempStock.setPrevClose(Double.valueOf(temp[3]));
			tempStock.setTodayMaxPrice(Double.valueOf(temp[4]));
			tempStock.setTodayMinPrice(Double.valueOf(temp[5]));
			tempStock.setRates(Double.valueOf(temp[6]));
			tempStock.setFudu(Double.valueOf(temp[7]));
			wrapStock.add(tempStock);
		}
		stockService.senderShortEmailByMinute(wrapStock); // 发邮件
	}
	
	
}
