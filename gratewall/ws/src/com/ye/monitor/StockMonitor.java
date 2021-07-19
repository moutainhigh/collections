package com.ye.monitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bo.domain.EveryDayTip;
import com.bo.domain.Holiday;
import com.bo.domain.SMSStock;
import com.bo.domain.SMSStockEveryDayDeal;
import com.bo.domain.SMSStockEveryDayInfo;
import com.bo.domain.Stock;
import com.bo.domain.StockEveryDayDeal;
import com.bo.domain.StockEveryDayInfo;
import com.bo.domain.StockMail;
import com.ye.monitor.from.JuHeWebDem2;
import com.ye.monitor.from.PingAnWeb;
import com.ye.monitor.from.SinaWeb;
import com.ye.monitor.from.TencentWeb;
import com.ye.monitor.from.WangYiWeb;
import com.ye.monitor.from.XueQiuWeb;
import com.ye.monitor.from.ZhaoShangWeb;
import com.ye.service.StockService;
import com.ye.util.PingYinTools;

//http://blog.csdn.net/ycb1689/article/details/51248547
//http://blog.csdn.net/u011116672/article/details/52517247
//https://www.cnblogs.com/liuyitian/p/4108391.html

//http://blog.csdn.net/prisonbreak_/article/details/49180307
//http://blog.csdn.net/qq_33556185/article/details/51852537
//http://blog.csdn.net/dongye2016/article/details/77860372
//定时http://blog.csdn.net/u010648555/article/details/52162840
@Component
public class StockMonitor {

	public static Logger log = LogManager.getLogger(StockMonitor.class);
	@Autowired
	private XueQiuWeb xueQiu;

	@Autowired
	private SinaWeb sina;

	@Autowired
	private WangYiWeb wangYi;

	@Autowired
	private ZhaoShangWeb zhaoShang;

	@Autowired
	private PingAnWeb pingAn;

	@Autowired
	private TencentWeb tencent;

	@Autowired
	private JuHeWebDem2 juHeWeb;

	@Autowired
	private PingAnWeb pingAnWeb;
	@Autowired
	private ZhaoShangWeb zhaoShangWeb;

	@Autowired
	private StockService stockService;

	public boolean getIsBegin() {
		boolean flag = false;

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY); // http://blog.csdn.net/jiangeeq/article/details/53103069
		int minute = c.get(Calendar.MINUTE);
		flag = hour == 11 && minute >= 30 || hour == 9 && minute < 25;
		// flag = false;
		return flag;
	}

	public boolean getIsHoliday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		//System.out.println("当前日期=============>" + df.format(new Date()));
		Holiday h = stockService.getHoliday();
		if (h != null) {
			//log.log(Level.OFF, "当前节日" + h.getHolidayName() + ",系统不监控 ");
			return true;
		} else {
			return false;
		}
	}

	@Scheduled(cron = "0/2 * * * * ?")
	public void upateSMSStockName() {
		// log.log(Level.WARN, "按2秒定时任务开始====短信股==更新名字");
		List<SMSStock> stockList = stockService.getNoNameSMSStock();
		if (stockList != null && stockList.size() > 0) {
			List<SMSStock> wrapStock = new ArrayList<SMSStock>();
			for (SMSStock smsstock : stockList) {
				String str = tencent.smsStockJianYao(smsstock);
				// log.error(dailyStock.getStockCode() + " , " +
				// str.toString());
				String[] temp = str.split(",");
				SMSStock tempStock = new SMSStock();
				tempStock.setStockName(temp[0]);
				tempStock.setStockCode(smsstock.getStockCode().trim());
				// tempStock.setStockCode(temp[1]);

				if (smsstock.getStockCode().indexOf("6") == 0) {
					tempStock.setStockBelong(1);
				} else {
					tempStock.setStockBelong(2);
				}

				tempStock.setCurrentPrice(Double.valueOf(temp[2]));
				tempStock.setPrevClose(Double.valueOf(temp[3]));
				tempStock.setTodayMaxPrice(Double.valueOf(temp[4]));
				tempStock.setTodayMinPrice(Double.valueOf(temp[5]));
				tempStock.setRates(Double.valueOf(temp[6]));
				tempStock.setFudu(Double.valueOf(temp[7]));
				tempStock.setThumbsImg(smsstock.getThumbsImg());
				String pinYin =  PingYinTools.getPinYinHeadChar(temp[0]);
				tempStock.setPinYin(pinYin);
				wrapStock.add(tempStock);
				stockService.update(tempStock);// 更新短信股名称，价格和幅度
			}
		}
	}

	// 监控早上和下午的短信股
	@Scheduled(cron = "0 0/3 9,10,11,13,14  ? * MON-FRI")
	public void monitorDailyStockByMinute() {
		boolean flag = this.getIsBegin();
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
			if (!flag) {
				log.log(Level.WARN, "按3分种定时任务开始====短信股==发邮件");
				List<SMSStock> stockList = stockService.selectAllSMSStockList();
				List<SMSStock> wrapStock = new ArrayList<SMSStock>();
				for (SMSStock tempStock : stockList) {
					String str = tencent.smsStockJianYao(tempStock);
					// log.error(dailyStock.getStockCode() + " , " +
					// str.toString());
					String[] temp = str.split(",");
					/*SMSStock tempStock = new SMSStock();

					tempStock.setStockName(temp[0]);
					tempStock.setStockCode(dailyStock.getStockCode());
					//tempStock.setStockCode(temp[1]);
					tempStock.setCurrentPrice(Double.valueOf(temp[2]));
					tempStock.setPrevClose(Double.valueOf(temp[3]));
					tempStock.setTodayMaxPrice(Double.valueOf(temp[4]));
					tempStock.setTodayMinPrice(Double.valueOf(temp[5]));
					tempStock.setRates(Double.valueOf(temp[6]));
					tempStock.setFudu(Double.valueOf(temp[7]));
					tempStock.setTypes(dailyStock.getTypes());
					tempStock.setAddDate(dailyStock.getAddDate());*/
					tempStock.setCurrentPrice(Double.valueOf(temp[2]));
					tempStock.setPrevClose(Double.valueOf(temp[3]));
					tempStock.setTodayMaxPrice(Double.valueOf(temp[4]));
					tempStock.setTodayMinPrice(Double.valueOf(temp[5]));
					tempStock.setRates(Double.valueOf(temp[6]));
					tempStock.setFudu(Double.valueOf(temp[7]));
					tempStock.setTypes(tempStock.getTypes());
					stockService.update(tempStock);// 更新短信股类别
					
					Calendar c = Calendar.getInstance();
					int hour = c.get(Calendar.HOUR_OF_DAY); // http://blog.csdn.net/jiangeeq/article/details/53103069
				/*	if (tempStock.getRates() > 2) {
						if (hour >= 13) {
							//tempStock.setTypes(1);// 早上还是下午的 0 早上，1下午
							//stockService.update(tempStock);// 更新短信股类别
						}
					}*/

					// 保存成交记录
					SMSStockEveryDayDeal stockEveryDayDeal = new SMSStockEveryDayDeal();
					stockEveryDayDeal.setStockName(temp[0]);
					stockEveryDayDeal.setStockCode(tempStock.getStockCode());
					stockEveryDayDeal.setCurrentPrice(Double.valueOf(temp[2]));
					stockEveryDayDeal.setRates(Double.valueOf(temp[6]));
					int minute = c.get(Calendar.MINUTE);
					flag = hour == 11 && minute > 30 || hour == 9 && minute < 30;
					if (!flag) {
						stockService.saveSMSStockEveryDayDeal(stockEveryDayDeal);// 保存成交明细
						wrapStock.add(tempStock);
					}
				}
				stockService.senderShortEmailByMinute(wrapStock); // 发邮件

			}
		}

	}

	// 每周工作日每天17:00点更新短信股票表的信息
	@Scheduled(cron = "0 0 17  ? * MON-FRI ")
	public void updateSMSDayInfo() {
		List<SMSStock> stockList = stockService.selectAllSMSStockList();
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
			if (stockList != null && stockList.size() > 0) {
				//List<SMSStock> wrapStock = new ArrayList<SMSStock>();
				for (SMSStock dailyStock : stockList) {
					String str = tencent.smsStockJianYao(dailyStock);
					// log.error(dailyStock.getStockCode() + " , " +
					// str.toString());
					String[] temp = str.split(",");
					
					dailyStock.setCurrentPrice(Double.valueOf(temp[2]));
					dailyStock.setPrevClose(Double.valueOf(temp[3]));
					dailyStock.setTodayMaxPrice(Double.valueOf(temp[4]));
					dailyStock.setTodayMinPrice(Double.valueOf(temp[5]));
					dailyStock.setRates(Double.valueOf(temp[6]));
					if(Double.isNaN(Double.valueOf(temp[7]))){
						System.out.println("当前股票幅度设置为-9999  == ，表示更新失败,股票停牌" + dailyStock.getStockCode());
						dailyStock.setFudu(-999);
					}else{
						dailyStock.setFudu(Double.valueOf(temp[7]));
					}
					
					
					/*SMSStock tempStock = new SMSStock();

					tempStock.setStockName(temp[0]);
					tempStock.setStockCode(dailyStock.getStockCode());
					// tempStock.setStockCode(temp[1]);
					tempStock.setCurrentPrice(Double.valueOf(temp[2]));
					tempStock.setPrevClose(Double.valueOf(temp[3]));
					tempStock.setTodayMaxPrice(Double.valueOf(temp[4]));
					tempStock.setTodayMinPrice(Double.valueOf(temp[5]));
					tempStock.setRates(Double.valueOf(temp[6]));
					tempStock.setFudu(Double.valueOf(temp[7]));
					wrapStock.add(tempStock);*/
					stockService.update(dailyStock);// 更新短信股名称，价格和幅度等所有的信息，归类一个方法

					SMSStockEveryDayInfo recordDaily = new SMSStockEveryDayInfo();

					recordDaily.setStockCode(dailyStock.getStockCode());
					recordDaily.setStockName(dailyStock.getStockName());
					recordDaily.setTodayMaxPrice(Double.valueOf(temp[4]));
					recordDaily.setTodayMinPrice(Double.valueOf(temp[5]));
					recordDaily.setClosePrice(Double.valueOf(temp[2]));
					recordDaily.setClosePrevPrice(Double.valueOf(temp[3]));
					if (dailyStock.getStockCode().indexOf("6") != 0) {
						recordDaily.setStockBelong(1);
					}

					stockService.saveSMSEveryDayInfo(recordDaily);
				}
			}
		}
	}

	// 每天的短信股邮件 20:00分发邮件
	@Scheduled(cron = "0 0 20  ? * MON-FRI ")
	public void senderSMSEmailByDay() {
		log.log(Level.INFO, "按 工作日定时任务开始===短信股==发邮件");
		List<SMSStock> stockList = stockService.selectAllSMSStockList();
		stockService.senderShortEmailByDay(stockList); // 发邮件
	}

	// 每周的短信股邮件 周五,周日22:00发邮件
	@Scheduled(cron = "0 0 22  ? * FRI,SUN ")
	public void senderSMSEmailByWeek() {
		log.log(Level.INFO, "按周定时任务开始===短信股票==发邮件");
		List<SMSStock> stockList = stockService.selectAllSMSStockList();
		stockService.senderShortEmailByWeek(stockList); // 发邮件

	}

	/*********************************** 正常的股票自定义监控的股票开始 *************************************/

	// 更新股票的名字
	@Scheduled(cron = "0/2 * * * * ?")
	public void updateStockName() {
		// log.log(Level.INFO, "按秒定时任务开始===正常的股票===更新股票名称");
		List<Stock> stockList = stockService.selectNoNameStockList();
		if (stockList != null && stockList.size() > 0) {
			for (Stock stock : stockList) {
				String str = sina.getEmailSendInfo(stock);
				System.out.println(str + "=============================");
				String[] temp = str.split(",");
				System.out.println("+++++++++++++++++++++++++++++++++++++++" + stock.getIsRecentImport());
				stock.setStockName(temp[1]);
				if (stock.getStockCode().indexOf("6") == 0) {
					stock.setStockBelong(1);
				} else {
					stock.setStockBelong(2);
				}
				//stock.setBuyPrice(stock.getBuyPrice());
				//stock.setIsRecentImport(stock.getIsRecentImport());
				stock.setCurrentPrice(Double.valueOf(temp[2]));
				stock.setPrevClose(Double.valueOf(temp[3]));
				//stock.setIsHistoryBuy(stock.getIsHistoryBuy());
				stock.setStockCode(stock.getStockCode().trim());
				stock.setTodayMaxPrice(Double.valueOf(temp[4]));
				stock.setTodayMinPrice(Double.valueOf(temp[5]));
				if(!Double.isNaN(Double.valueOf(temp[6]))){
					stock.setFudu(Double.valueOf(temp[6]));
				}else{
					System.out.println("当前股票幅度设置为-9999  == ，表示更新失败,股票停牌" + stock.getStockCode());
					stock.setFudu(-999);
				}
				//stock.setIsconsole(stock.getIsconsole());
				//stock.setIsRecentImport(stock.getIsRecentImport());
				//stock.setIspub(stock.getIsImp());
				//stock.setMostImp(stock.getMostImp());
				//stock.setWaitImp(stock.getWaitImp());
				//stock.setStockBelong(stock.getStockBelong());
				//stock.setLonghuBang(stock.getLonghuBang());
				//stock.setThumbsImg(stock.getThumbsImg());
				String pinYin =  PingYinTools.getPinYinHeadChar(temp[1]);
				//System.out.println(pinYin);
				stock.setPinYin(pinYin);
				stockService.update(stock);
				
				
				
				/*Stock tempStock = new Stock();
				tempStock.setStockName(temp[1]);
				if (stock.getStockCode().indexOf("6") == 0) {
					tempStock.setStockBelong(1);
				} else {
					tempStock.setStockBelong(2);
				}
				tempStock.setBuyPrice(stock.getBuyPrice());
				tempStock.setIsRecentImport(stock.getIsRecentImport());
				tempStock.setCurrentPrice(Double.valueOf(temp[2]));
				tempStock.setPrevClose(Double.valueOf(temp[3]));
				tempStock.setIsHistoryBuy(stock.getIsHistoryBuy());
				tempStock.setStockCode(stock.getStockCode().trim());
				tempStock.setTodayMaxPrice(Double.valueOf(temp[4]));
				tempStock.setTodayMinPrice(Double.valueOf(temp[5]));
				tempStock.setFudu(Double.valueOf(temp[6]));
				tempStock.setIsconsole(stock.getIsconsole());
				tempStock.setIsRecentImport(stock.getIsRecentImport());
				tempStock.setIspub(stock.getIsImp());
				tempStock.setMostImp(stock.getMostImp());
				tempStock.setWaitImp(stock.getWaitImp());
				tempStock.setStockBelong(stock.getStockBelong());
				tempStock.setLonghuBang(stock.getLonghuBang());
				tempStock.setThumbsImg(stock.getThumbsImg());
				String pinYin =  PingYinTools.getPinYinHeadChar(temp[1]);
				//System.out.println(pinYin);
				tempStock.setPinYin(pinYin);
				stockService.update(tempStock);*/
			}
		}
	}

	// http://nuff.eastmoney.com/EM_Finance2015TradeInterface/JS.ashx?id=0025582&token=4f1862fc3b5e77c150a2b985b12db0fd&cb=jQ&_=1513746002668&callback=jQuery17206986464398918095_1513745890374
	/*
	 * https://zhidao.baidu.com/question/429163614.html
	 * http://blog.csdn.net/qq_33556185/article/details/51852537
	 * http://blog.csdn.net/prisonbreak_/article/details/49180307
	 * http://blog.csdn.net/u012129031/article/details/53925412
	 * http://blog.csdn.net/qq_33556185/article/details/51852537
	 * http://www.zx017.net/p/zgzz/?f=sNm2yMvRy_cwNg==&u=Ymot1tC4u73wyq8wNg==&p=
	 * TS02v6rNt7T6wuu0yjEt0MI=&g=ucnGsbT6wustNjAzODY2&k=NjAzODY2&fcpic=dantu
	 */
	@SuppressWarnings("rawtypes")
	@Scheduled(cron = "0/25 * 9,10,11,13,14  ? * MON-FRI ")
	public void monitorBySeconds() {
		boolean flag = this.getIsBegin();
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
			if (!flag) {
				log.log(Level.INFO, "按秒定时任务开始===正常的股票===邮件,根据指定的幅度发邮件=============================");
				log.log(Level.INFO, "====================================");
				List<Stock> stockList = stockService.selectStockAllList();
				List<Stock> wrapStockList = new ArrayList<Stock>();
				pingAn.getDaPanZhiShu(); //得到大盘指数
				//log.log(Level.INFO, "大盘指数===》 "+mapDapan);
				for (Stock stock : stockList) {
					Map map = pingAn.getStockInfo(stock);

					StockEveryDayDeal stockEveryDayDeal = new StockEveryDayDeal();
					String code = (String) map.get("code");
					String name = (String) map.get("stockName");
					if(name.length()<4){
						name = name + "  ";
					}

					stockEveryDayDeal.setStockCode(code);
					stockEveryDayDeal.setStockName(name);

					String currentPrice = (String) map.get("currentPrice");
					String prevClosePrice = (String) map.get("prevClose");
					String maxPrice = (String) map.get("maxPrice");
					String minPrice = (String) map.get("minPrice");
					double rate = 0.0;
					double cuPrice = 0.0;
					double preClosePrice = 0.0;
					double todayMaxPrice = 0.0;
					double todayMinPrice = 0.0;

					if (!currentPrice.contains("-")) {
						cuPrice = Double.valueOf(currentPrice);
					}
					if (!prevClosePrice.contains("-")) {
						preClosePrice = Double.valueOf(prevClosePrice);
					}
					if (!maxPrice.contains("-")) {
						todayMaxPrice = Double.valueOf(maxPrice);
					}
					if (!minPrice.contains("-")) {
						todayMinPrice = Double.valueOf(minPrice);
					}
					rate = 100 * (cuPrice - preClosePrice) / preClosePrice;
					double zhangDie = (cuPrice - preClosePrice)*10;
					if (stock.getIsconsole() != 0) {
						//System.out.println("===========================================================================================================");
						//log.log(Level.INFO, "代码:" + map.get("code") + ",名称:" + map.get("stockName") + ",当前:" + map.get("currentPrice") + ",开盘:" + map.get("open") + ",最低:" + map.get("minPrice") + ",昨收" + map.get("prevClose") + ",当前幅度" + rate);
						log.log(Level.INFO, "代码:" + map.get("code") + ",名称:" + name + ",当前:" + map.get("currentPrice") + ",开盘:" + map.get("open") + ",最低:" + map.get("minPrice")+"最高"+map.get("maxPrice") + ",昨收" + map.get("prevClose") + ",当前幅度" + rate);
						/*System.out.println("代码:" + map.get("code") + ",名称:" + map.get("stockName") + ",当前:" + map.get("currentPrice") + ",开盘:" + map.get("open") + ",最低:" + map.get("minPrice") + ",昨收" + map.get("prevClose") + ",当前幅度" + rate);*/
					}
					Calendar c = Calendar.getInstance();
					int hour = c.get(Calendar.HOUR_OF_DAY); // http://blog.csdn.net/jiangeeq/article/details/53103069
					int minute = c.get(Calendar.MINUTE);
					flag = hour == 11 && minute >= 30 || hour == 9 && minute < 30;
					if (!flag) {
						if (code.indexOf("6") == 0) {
							stockEveryDayDeal.setStockBelong(1);
							stock.setStockBelong(0);
						} else {
							stockEveryDayDeal.setStockBelong(0);
							stock.setStockBelong(1);
						}
						if (!Double.isNaN(rate)) {
							stockEveryDayDeal.setRates(rate);
						}else{
							System.out.println("当前股票幅度设置为-9999  == ，表示更新失败,股票停牌[记录股票的每日成交]" + stock.getStockCode());
							stockEveryDayDeal.setRates(-99999);
						}
						stockEveryDayDeal.setCurrentPrice(cuPrice);
						stockEveryDayDeal.setZhangdieFu(cuPrice - preClosePrice);
						stockService.saveRecordStockDealBus(stockEveryDayDeal);// 保存成交明细
					}
					if (Double.isNaN(rate)) {
						System.out.println("当前股票幅度设置为-9999  == ，表示更新失败,股票停牌" + stock.getStockCode());
						stock.setFudu(-999);
					} else {
						stock.setFudu(rate);
					}
					stock.setZhangDie(cuPrice - preClosePrice);// 计算涨跌
					stock.setTodayMaxPrice(todayMaxPrice);
					stock.setTodayMinPrice(todayMinPrice);
					stock.setCurrentPrice(cuPrice);
					stock.setPrevClose(preClosePrice);
					
					stockService.update(stock);
					// 发送短信
				}

				// 得到能发送的邮箱地址
				List<StockMail> mailLists = stockService.getMailAdressAllowByStockRate();
				for (StockMail mail : mailLists) {
					mail.setStockRateTimes(0);
					stockService.diableEmailByStockRate(mail);
				}
				HashMap map = new HashMap();
				stockService.sendEmailByRate(wrapStockList, map); // 发邮件
			}
		}
	}

	// 每8分中更新能发的金针邮件列表
	@Scheduled(cron = "0 0/8 9,10,11,13,14  ? * MON-FRI ")
	public void enableRateStockMail() {
		List<StockMail> mailLists = stockService.getMailAdressNotAllowByStockRate();
		for (StockMail mail : mailLists) {
			mail.setStockRateTimes(1);
			stockService.enableEmailByStockRate(mail);
		}
	}

	// 5分钟查盘一次
	@SuppressWarnings("rawtypes")
	@Scheduled(cron = "0 0/3 9,10,11,13,14  ? * MON-FRI ")
	public void senderStockMailByMinute() {
		boolean flag = this.getIsBegin();
		if (!flag) {
			log.log(Level.ERROR, "按5分钟定时任务开始===正常的股票===具备发布发送邮件功能");
			List<Stock> stockList = stockService.selectStockAllList();
			Map map = new HashMap();
			stockService.senderEmailByMinute(stockList, map); // 发邮件
		}
	}

	// 每天16:00分更新股票表
	@Scheduled(cron = "0 4 17 ? * MON-FRI ")
	public void updateStockDayInfo() {
		log.log(Level.ERROR, "按 工作日定时任务开始更新股票表");
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
			List<Stock> stockList = stockService.selectStockAllList();
			if (stockList != null && stockList.size() > 0) {
				for (Stock stock : stockList) {
					String str = sina.getEmailSendInfo(stock);
					System.out.println(str + "=============================");
					String[] temp = str.split(",");
					stock.setCurrentPrice(Double.valueOf(temp[2]));
					stock.setPrevClose(Double.valueOf(temp[3]));
					stock.setTodayMaxPrice(Double.valueOf(temp[4]));
					stock.setTodayMinPrice(Double.valueOf(temp[5]));
					
					if (Double.isNaN(Double.valueOf(temp[6]))) {
						System.out.println("当前股票幅度设置为-9999  == ，表示更新失败,股票停牌" + stock.getStockCode());
						stock.setFudu(-99999);
					} else {
						stock.setFudu(Double.valueOf(temp[6]));
					}
				
					/*Stock tempStock = new Stock();
					// tempStock.setStockName(temp[1]);
					tempStock.setStockName(stock.getStockName());
					tempStock.setBuyPrice(stock.getBuyPrice());
					tempStock.setCurrentPrice(Double.valueOf(temp[2]));
					tempStock.setPrevClose(Double.valueOf(temp[3]));
					tempStock.setIsRecentImport(stock.getIsRecentImport());
					tempStock.setIsHistoryBuy(stock.getIsHistoryBuy());
					tempStock.setStockCode(stock.getStockCode());
					tempStock.setTodayMaxPrice(Double.valueOf(temp[4]));
					tempStock.setTodayMinPrice(Double.valueOf(temp[5]));
					tempStock.setFudu(Double.valueOf(temp[6]));
					tempStock.setIsconsole(stock.getIsconsole());
					tempStock.setIsRecentImport(stock.getIsRecentImport());
					tempStock.setIspub(stock.getIsImp());
					tempStock.setMostImp(stock.getMostImp());
					tempStock.setWaitImp(stock.getWaitImp());
					tempStock.setStockBelong(stock.getStockBelong());
					tempStock.setLonghuBang(stock.getLonghuBang());
					tempStock.setThumbsImg(stock.getThumbsImg());
					tempStock.setPinYin(stock.getStockCode());*/
					stockService.update(stock);

					StockEveryDayInfo recordDaily = new StockEveryDayInfo();

					recordDaily.setStockCode(stock.getStockCode());
					recordDaily.setStockName(stock.getStockName());
					recordDaily.setTodayMaxPrice(Double.valueOf(temp[4]));
					recordDaily.setTodayMinPrice(Double.valueOf(temp[5]));
					recordDaily.setClosePrice(Double.valueOf(temp[2]));
					recordDaily.setClosePrevPrice(Double.valueOf(temp[3]));
					if (stock.getStockCode().indexOf("6") != 0) {
						recordDaily.setStockBelong(1);
					}

					stockService.saveStockEveryDayInfo(recordDaily);

				}
			}
		}
	}

	// 每个周工作日发送邮件
	@SuppressWarnings("rawtypes")
	@Scheduled(cron = "0 0 21  ? * MON-FRI ")
	public void senderStockMailByDay() {
		log.log(Level.ERROR, "按 工作日定时任务开始===正常的股票===发邮件");
		List<Stock> stockList = stockService.selectStockAllList();
		Map map = new HashMap();
		stockService.senderEmailByDay(stockList, map); // 发邮件
	}

	// 周五,周日22:00发邮件
	@Scheduled(cron = "0 0 22  ? * FRI,SUN ")
	@SuppressWarnings({ "rawtypes" })
	public void senderStockMailByWeek() {
		log.log(Level.ERROR, "按周定时任务开始===正常的股票");
		List<Stock> stockList = stockService.selectStockAllList();
		Map map = new HashMap();
		stockService.senderEmailByWeek(stockList, map); // 发邮件
	}

	// http://blog.csdn.net/aya19880214/article/details/46341905
	// 拿来玩的
	// https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E8%A1%A8%E6%A0%BC%E7%BE%8E%E5%8C%96&hs=2&pn=1&spn=0&di=22734082470&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=2906374161%2C847301876&os=687503784%2C2755905892&simid=3448705495%2C199329529&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=&bdtype=0&oriquery=%E8%A1%A8%E6%A0%BC%E7%BE%8E%E5%8C%96&objurl=http%3A%2F%2Fimg.htmleaf.com%2F1412%2F201412152054.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bip4sjwu_z%26e3Bv54AzdH3Fpw2f_z%26e3Brir%3FAzdH3F%25Eb%25A8%25Ab%25Em%25Aa%25BC%25E0%25BE%25bE%25Ec%25bC%25lmAzdH3F&gsm=0
	// http://blog.csdn.net/kongwei521/article/details/12582285
	//http://blog.csdn.net/prisonbreak_/article/details/49180307
	@SuppressWarnings({ "unchecked", "rawtypes" })
	//@Scheduled(cron = "0 0/20 0,1,2,3,4,5,6,7,8,15,16,17,18,19,20,21,22,23 * * ? ")
	//@Scheduled(cron = "0/15 * 0,1,2,3,4,5,6,7,8,15,16,17,18,19,20,21,22,23 * * ? ")
	//@Scheduled(cron="0/5 * *  * * ? ")
	public void testMy() {
		//log.log(Level.ERROR, "按 工作日定时任务开始===正常的股票===发邮件");
		List<Stock> stockList = stockService.selectStockAllList();
		//boolean isHoliday = this.getIsHoliday();
		//if(isHoliday){
			Map map = new HashMap();
			map.put("key1", "每日故事");
			int i = (int) (33 * Math.random() + 1);// 1-32的随机数
			EveryDayTip de = stockService.getEveryDayTipById(i);
			map.put("tips", de.getContents());
			try {
				//stockService.senderEmailByMinute(stockList, map); // 发邮件
				
				List<SMSStock> ssstockList = stockService.selectAllSMSStockList();
				//stockService.senderShortEmailByMinute(ssstockList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//}
		
	}

}
