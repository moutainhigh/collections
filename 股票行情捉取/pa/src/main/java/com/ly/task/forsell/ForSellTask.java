package com.ly.task.forsell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.common.utils.CalcUtil;
import com.ly.dao.AccountDao;
import com.ly.dao.SMSDao;
import com.ly.dao.SellDao;
import com.ly.email.MailSenderSrv;
import com.ly.pojo.Account;
import com.ly.pojo.SMSPhone;
import com.ly.pojo.Sell;
import com.ly.sms.MyTencentSMSUtils;
import com.ly.stocktrade.getdayline.PingAnWithouAuthInfo;
import com.ly.stocktrade.trade.core.StockTradeCore;

//https://www.cnblogs.com/zy-jiayou/p/7007303.html

@Component
public class ForSellTask {

	//private static Logger logger = Logger.getLogger(ForSell.class);
	private static final Logger logger = Logger.getLogger("sts");

	@Autowired
	private SellDao sellDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private StockTradeCore stockTradeBus;

	@Autowired
	private PingAnWithouAuthInfo pinanHttpUtil;
	
	@Autowired
	private MailSenderSrv srv;
	
	@Autowired
	private MyTencentSMSUtils tencentSMS;
	
	@Autowired
	private SMSDao smsDao;
	
	//@Scheduled(cron = "30 24 16 ? * MON-FRI")
	//@Scheduled(cron = "*/50 * * * * ?")
	//@Scheduled(cron = "*/30 * * * * ?")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Scheduled(cron = "30 29 9 ? * MON-FRI")
	public void taskSell() {
		SMSPhone smsPhone = null;
		List<Account> accounts = accountDao.getAccountList();
		List<Sell> sells = sellDao.getSellList();
		if (sells != null && sells.size() > 0) {
			for (int i = 0; i < sells.size(); i++) {
				Sell sell = sells.get(i);
				String code = sell.getCode();
				String exchange_type = sell.getExchange_type();
				Map<String, String> codeMap = new HashMap<String, String>();
				codeMap.put("code", sell.getCode());
				codeMap.put("marketType", sell.getMarketType());
				Map<String, String> map;
				try {
					map = pinanHttpUtil.getInfo(codeMap);
					
					String nameStr = map.get("stockName");
					String prevCloseStr = map.get("prevClose");
					String openStr = map.get("open");
					String newPriceStr = map.get("newPrice"); // 当前价
					String weiTuoBuyPriceStr = sell.getWeiTuoBuyPrice();//委托买入价
					
					
					Double newPrice = Double.valueOf(newPriceStr);
					Double openPrice = Double.valueOf(openStr);
					Double prevClosePrice = Double.valueOf(prevCloseStr);
					Double weiTuoBuyPrice  = Double.valueOf(weiTuoBuyPriceStr);
					Double zhangDe = openPrice - prevClosePrice;
					
					Double fuDu = (zhangDe / prevClosePrice)*100;
					String fuDuStr = CalcUtil.formatDouble(fuDu);
					Double zhangDieLv = Double.valueOf(fuDuStr);
					
					
					logger.debug("代码【"+code+"】，名称【"+nameStr+"】, 本日开盘【"+openStr+"】，开盘涨跌百分比【"+zhangDieLv+"】， 本日开盘涨跌===> 【 " + zhangDe+" 】 ,当前价【"+newPriceStr+"】，,本日涨停价 【"+ prevClosePrice*1.1 +"】 ,本日跌停价 【" +prevClosePrice*0.9+ "】,昨收 【"+prevClosePrice+"】 ");
					Double priceForSell = null;
					if (zhangDe > 0) {
						if(zhangDieLv>9&&zhangDieLv<=10){
							logger.debug("代码【" + code + "】,名称【" + nameStr + "】 当前开盘已经达到本日涨停价【"+ prevClosePrice*1.1 +"】，系统不再挂单");
						}else if(zhangDieLv>8&&zhangDieLv<9){
							priceForSell = weiTuoBuyPrice *(1+8.8/100);
						}else if(zhangDieLv>7&&zhangDieLv<=8){
							priceForSell = weiTuoBuyPrice * (1+7.88/100);// 
						}else if(zhangDieLv<=7&&zhangDieLv>=5){
							priceForSell = weiTuoBuyPrice *(1+6.81/100);// 
						}else if(zhangDieLv<5&&zhangDieLv>3){
							priceForSell = weiTuoBuyPrice *(1+4.8/100);// 
						}else if(zhangDieLv<=3&&zhangDieLv>2){
							priceForSell = weiTuoBuyPrice *(1+2.6/100);//
						}else if(zhangDieLv<=2&&zhangDieLv>1){
							priceForSell = weiTuoBuyPrice *(1+1.44/100);// 
						}else{
							priceForSell = weiTuoBuyPrice *(1+0.86/100);//
						}
					} else if (zhangDe < 0) {
						priceForSell = weiTuoBuyPrice * (1+0.86/100);// 卖出价为委托买入价的0.08个点
					} else {
						priceForSell = weiTuoBuyPrice * (1+0.86/100);// 卖出价为委托买入价高0.08个点
					}
					
					String weiTuoPrice = null;// 卖出价

					Map<String, String> lastestMap = new HashMap<String, String>();
					lastestMap.put("stockCode", code);
					lastestMap.put("stockName", nameStr);
					lastestMap.put("exchangeType", exchange_type);
					lastestMap.put("prevClosePrice", prevCloseStr);
					if(priceForSell!=null){
						/*if(priceForSell>prevClosePrice*1.1){
							priceForSell = prevClosePrice*1.1-0.01;
						}*/
						weiTuoPrice = CalcUtil.formatDouble(priceForSell);
						
						int sellNum =  0;
						if (accounts != null && accounts.size() > 0) {
							for (int j = 0; j < accounts.size(); j++) {
								Account ac = accounts.get(j);
								sellNum = stockTradeBus.doSell(code, weiTuoPrice, ac, lastestMap);
								logger.debug("代码【" + code + "】,名称【" + nameStr + "】卖出委托,委托价" + weiTuoPrice);
								sell.setWeiTuoTodaySellPrice(weiTuoPrice);
								sell.setWeiTuoBuyNum(sellNum+"");
								sellDao.save(sell);
							}
						}
						
						
						srv.sendEmail("1039288191@qq.com", "1039288191@qq.com", "代码【" + code + "】,名称【" + nameStr + "】卖出委托,委托价" + weiTuoPrice, "代码【" + code + "】,名称【" + nameStr + "】卖出委托,委托价" + weiTuoPrice);
						srv.sendEmail("782587058@qq.com", "1039288191@qq.com", "代码【" + code + "】,名称【" + nameStr + "】卖出委托,委托价" + weiTuoPrice, "代码【" + code + "】,名称【" + nameStr + "】卖出委托,委托价" + weiTuoPrice);
						srv.sendEmail("1065291560@qq.com", "1039288191@qq.com", "代码【" + code + "】,名称【" + nameStr + "】卖出委托,委托价" + weiTuoPrice, "代码【" + code + "】,名称【" + nameStr + "】卖出委托,委托价" + weiTuoPrice);
						
						//String  [] params = {code,"【"+nameStr+"】","【"+weiTuoPrice+"】賣"};
					   //tencentSMS.sendSell("18269215167", params);
						
						if (nameStr.contains("神州")) {
							nameStr = nameStr.replace("神州", "sz");
						}
						String  [] params = {code,"【"+nameStr+"】","【"+weiTuoPrice+"】卖"};
						List<SMSPhone> phones = smsDao.getSMSListByParam();
						ArrayList phoneList = new ArrayList();
						if (phoneList != null && phones.size() > 0) {
							for (int k = 0; k < phones.size(); k++) {
								smsPhone = phones.get(k);
								phoneList.add(smsPhone.getPhone());
							}
						}
						logger.debug("查询要发送的短信手机号码列表" + phoneList);
					    tencentSMS.sendSell(phoneList, params);
					}
				
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("定时查询到不存在需要卖的列表,当前不需要挂单");
		}
		
		
		
		
	}
}
