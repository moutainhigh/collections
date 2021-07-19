package com.ly.task.forbuy;

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
import com.ly.dao.BuyDao;
import com.ly.dao.SMSDao;
import com.ly.email.MailSenderSrv;
import com.ly.pojo.Account;
import com.ly.pojo.Buy;
import com.ly.pojo.SMSPhone;
import com.ly.sms.MyTencentSMSUtils;
import com.ly.stocktrade.getdayline.PingAnWithouAuthInfo;
import com.ly.stocktrade.trade.core.StockTradeCore;

@Component
public class ForbuyTask {

	//private static Logger logger = Logger.getLogger(ForbuyTask.class);
	
	private static final Logger logger = Logger.getLogger("sts");
	
	@Autowired
	private StockTradeCore stockTradeBus;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private BuyDao buyDao;

	@Autowired
	private PingAnWithouAuthInfo pinanHttpUtil;

	@Autowired
	private MailSenderSrv srv;
	
	@Autowired
	private SMSDao smsDao;

	/*
	 * @Autowired private SMSTips sms;
	 */

	@Autowired
	private MyTencentSMSUtils tencentSMS;
	

	//@Scheduled(cron="0/40 * * * * ?")
	//@Scheduled(cron="40 26 9 ? * MON-FRI")
	//@Scheduled(cron="0/20 * * * * ?")
	@Scheduled(cron="40 26 9 ? * MON-FRI")
	public void taskForBuy() {
		List<Account> accounts = accountDao.getAccountList();
		List<Buy> buys = buyDao.getBuyList();
		SMSPhone smsPhone = null;
		
		
		if (buys != null && buys.size() > 0) {
			for (int i = 0; i < buys.size(); i++) {
				Buy mo = buys.get(i);
				String code = mo.getCode();
				Map<String, String> codeMap = new HashMap<String, String>();
				codeMap.put("code", code);
				codeMap.put("marketType", mo.getMarketType());
				try {
					Map<String, String> map = pinanHttpUtil.getInfo(codeMap);

					String nameStr = map.get("stockName");
					String prevClose = map.get("prevClose");
					String openStr = map.get("open");
					String buyPrice3 = map.get("buyPrice3");
					String newPriceStr = map.get("newPrice"); // 当前价

					Double openPrice = Double.valueOf(openStr);
					Double newPrice = Double.valueOf(newPriceStr);
					Double prevClosePrice = Double.valueOf(prevClose);

					Double zhangDe = openPrice - prevClosePrice;
					Double fuDu = (zhangDe / prevClosePrice) * 100;
					String fuDuStr = CalcUtil.formatDouble(fuDu);
					Double zhangDieLv = Double.valueOf(fuDuStr);
					
					Double buyRef = prevClosePrice;

					// logger.debug("当前涨跌幅度===> 【 " + zhangDe+" 】 ,本日涨停价
					// 【"+ prevClosePrice*1.1 +"】 ,本日跌停价 【"
					// +prevClosePrice*0.9+ "】 ");
					logger.debug("代码【" + code + "】，名称【" + nameStr + "】, 本日开盘【" + openStr + "】，开盘涨跌百分比【" + zhangDieLv + "】， 本日开盘涨跌===> 【 " + zhangDe + " 】 ,当前价【" + newPriceStr + "】，,本日涨停价 【" + prevClosePrice * 1.1 + "】 ,本日跌停价 【" + prevClosePrice * 0.9 + "】,昨收 【" + prevClosePrice + "】 ");
					Double priceForBuy = null;
					if (zhangDe > 0) { // 高开
						// priceForBuy =  openPrice*0.998;//买入价为开盘价跌0.2点的价（开盘）
						// 计算各种高开点位
						if (zhangDieLv > 8 && zhangDieLv <= 10) {
							priceForBuy = buyRef * (1 - 5.2 / 100);
						} else if (zhangDieLv > 6 && zhangDieLv <= 8) {
							priceForBuy = buyRef * (1 - 4.2 / 100);
						} else if (zhangDieLv > 4 && zhangDieLv <= 6) {
							priceForBuy = buyRef * (1 - 3.5 / 100);
						} else if (zhangDieLv > 2 && zhangDieLv <= 4) {
							priceForBuy = buyRef * (1 - 3.4 / 100);
						}else if (zhangDieLv > 1 && zhangDieLv <= 2) {
							priceForBuy = buyRef * (1 - 1.92 / 100);
						}else {
							priceForBuy = buyRef * (1 - 1.94 / 100);
						}
					} else if (zhangDe < 0) { // 低开
						if (zhangDieLv > -10 && zhangDieLv < -9) {
							logger.debug("该股票本日跌停，系统不挂单");
						}
						if (zhangDieLv > -9 && zhangDieLv < -8) {
							priceForBuy = buyRef * (1 - 9.4 / 100);
						} else if (zhangDieLv > -8 && zhangDieLv < -7) {
							priceForBuy = buyRef * (1 - 7.6 / 100);
						} else if (zhangDieLv > -6 && zhangDieLv < -7) {
							priceForBuy = buyRef * (1 - 6.6 / 100);
						} else if (zhangDieLv > -5 && zhangDieLv <= -4) {
							priceForBuy = buyRef * (1 - 5.6 / 100);
						} else if (zhangDieLv > -4 && zhangDieLv <= -3) {
							priceForBuy = buyRef * (1 - 4.7 / 100);
						} else if (zhangDieLv > -3 && zhangDieLv <= -2) {
							priceForBuy = buyRef * (1 - 3.7 / 100);
						} else if (zhangDieLv > -2 && zhangDieLv <= -1) {
							priceForBuy = buyRef * (1 - 2.92 / 100);
						} else if (zhangDieLv > -1 && zhangDieLv < -0.1) {
							priceForBuy = buyRef * (1 - 1.99 / 100);
						}else{
							priceForBuy = buyRef * (1 - 1.98 / 100);
						}
					} else { // 平开
						priceForBuy = buyRef * (1 - 1.94 / 100);
					}

					String weiTuoPrice = CalcUtil.formatDouble(priceForBuy);

					Map<String, String> lastestMap = new HashMap<String, String>();
					lastestMap.put("stockCode", mo.getCode());
					lastestMap.put("stockName", nameStr.trim());
					lastestMap.put("exchangeType", mo.getExchange_type());
					lastestMap.put("prevClosePrice", prevClose);

					mo.setName(nameStr);
					mo.setWeituoprice(weiTuoPrice);
					buyDao.save(mo);
					
					if (accounts != null && accounts.size() > 0) {
						for (int j = 0; j < accounts.size(); j++) {
							Account ac = accounts.get(j);
							if (weiTuoPrice != null) {
								String nums = stockTradeBus.doBuy(mo.getCode(), weiTuoPrice, ac, lastestMap);
								logger.debug("代码【" + code + "】，名称【" + nameStr + "】,当前买入价为====> " + weiTuoPrice +",账号【"+ac.getAccount()+"】,买入量为 【" + nums+"】股");
							}
						}
					}
					
					
					srv.sendEmail("1039288191@qq.com", "1039288191@qq.com", "代码【" + code + "】,名称【" + nameStr + "】买入委托,委托价" + weiTuoPrice, "代码【" + code + "】,名称【" + nameStr + "】买入委托,委托价" + weiTuoPrice);
					srv.sendEmail("782587058@qq.com", "1039288191@qq.com", "代码【" + code + "】,名称【" + nameStr + "】买入委托,委托价" + weiTuoPrice, "代码【" + code + "】,名称【" + nameStr + "】买入委托,委托价" + weiTuoPrice);
					srv.sendEmail("1065291560@qq.com", "1039288191@qq.com", "代码【" + code + "】,名称【" + nameStr + "】买入委托,委托价" + weiTuoPrice, "代码【" + code + "】,名称【" + nameStr + "】买入委托,委托价" + weiTuoPrice);

					// sms.sendSMS(code, weiTuoPrice, "13123925527");
					// https://blog.csdn.net/Tian_fourpieces/article/details/79925395
					if (nameStr.contains("神州")) {
						nameStr = nameStr.replace("神州", "sz");
					}
					String[] params = {nameStr, "【涨跌" + zhangDieLv + "】", "【开" + openPrice + "】", "【" + weiTuoPrice + "】买" };
					List<SMSPhone> phones = smsDao.getSMSListByParam();
					
					ArrayList phoneList = new ArrayList();
					if (phones != null && phones.size() > 0) {
						for (int k = 0; k < phones.size(); k++) {
							smsPhone = phones.get(k);
							phoneList.add(smsPhone.getPhone());
						}
					}
					logger.debug("查询要发送的短信手机号码列表" + phoneList);
					tencentSMS.sendBuy(phoneList, params);
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}

	}

}
