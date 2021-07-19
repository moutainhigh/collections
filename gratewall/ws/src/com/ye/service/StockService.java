package com.ye.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.domain.EveryDayTip;
import com.bo.domain.Holiday;
import com.bo.domain.SMSStock;
import com.bo.domain.SMSStockEveryDayDeal;
import com.bo.domain.SMSStockEveryDayInfo;
import com.bo.domain.Stock;
import com.bo.domain.StockEveryDayDeal;
import com.bo.domain.StockEveryDayInfo;
import com.bo.domain.StockMail;
import com.github.pagehelper.PageHelper;
import com.sender.mail.ftl.TemplateSendEmail;
import com.sender.mail.fudu.RateStockEmailService;
import com.sender.mail.normalmail.EmailService;
import com.sender.mail.smsfudu.SMSRateStockEmailService;
import com.sender.mail.smsmail.SMSEmailService;
import com.ye.bus.EveryDayTipBus;
import com.ye.bus.HolidayBus;
import com.ye.bus.RecordDailyBus;
import com.ye.bus.RecordStockBus;
import com.ye.bus.SMSStockBus;
import com.ye.bus.StockBus;
import com.ye.bus.StockMailBus;
import com.ye.dao.SMSStockEveryDayDealDao;
import com.ye.dao.SMSStockEveryDayInfoDao;

//redis http://blog.csdn.net/joyhen/article/details/47358999
//https://www.cnblogs.com/lixihuan/p/6815730.html
//https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=mybatis%20%3Cset%3E&rsv_pq=d6f7101c0000e7b1&rsv_t=d05cnluAshlJ4aYQCAhsps7%2Faz8GJK4NJSTUWUdSLJWMlbsE2VOvyCMKlQI&rqlang=cn&rsv_enter=1&rsv_sug3=19&rsv_sug1=28&rsv_sug7=100&sug=mybatis%2520%253Cset%253E&rsv_n=1
//SSM http://www.cnblogs.com/han-1034683568/p/6440157.html

@Service
public class StockService {
	public static Logger log = LogManager.getLogger(StockService.class);

	@Autowired
	private StockBus stockBus;
	
	@Autowired
	private HolidayBus holidayBus;

	@Autowired
	private EmailService emailService;// 正常股

	@Autowired
	private RateStockEmailService rateStockEmailService;// 正常股按金针见底

	@Autowired
	private SMSEmailService smsEmailService;// 短信股

	@Autowired
	private SMSRateStockEmailService smsRateStockEmailService;// 短信股按金针见底

	@Autowired
	private TemplateSendEmail templateSendEmail;

	@Autowired
	private RecordStockBus recordStockBus;

	@Autowired
	private StockMailBus stockMailBus;

	@Autowired
	private RecordDailyBus recordDailyBus; // 记录股票的每一笔记录

	@Autowired
	private SMSStockBus smsStockBus; // 短信股

	@Autowired
	private SMSStockEveryDayInfoDao recordDailyStockDao;// 短信股每日信息

	@Autowired
	private SMSStockEveryDayDealDao dailyStockEveryDayDealDao;// 短信股每天成交笔数

	@Autowired
	private EveryDayTipBus everyDayTipBus;// 每日一句

	//controller 层，得到
	public Map getAllSMSStockByPage(int index, int pageSize) {
		Map map = smsStockBus.getAllSMSStockByPage(index,pageSize);
		return map;
	}
	
	// 短信股票金针见底--当天
	public void sendSMSEmailByRate(List<SMSStock> stocList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsRateStockEmailService.senderShortEmailByRate(
					mail.getMailAddress(), stocList);
		}
	}

	// 幅度过大的短信股的提醒--到达指定幅度的从数据库里面设定的幅度
	public void senderSMSEmailByRecentImportantWatchByRate(
			List<SMSStock> stocList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsEmailService.senderShortEmailByMinute(mail.getMailAddress(),
					stocList);
		}
	}

	// 保存每日短信股的成交记录数
	public int saveSMSStockEveryDayDeal(
			SMSStockEveryDayDeal dailyStockEveryDayDeal) {
		return dailyStockEveryDayDealDao.save(dailyStockEveryDayDeal);
	}

	// 保存每日短信股的交易记录最高最低价的
	public int saveSMSEveryDayInfo(SMSStockEveryDayInfo stock) {
		return recordDailyStockDao.save(stock);
	}

	// 短信股---发送金针见底的邮件的--即到达指定幅度的情况邮箱列表
	public List<StockMail> getMailAdressAllowBySMSStockRate() {
		return stockMailBus.getMailAdressAllowBySMSStockRate();
	}

	public List<StockMail> getMailAdressNotAllowBySMSStockRate() {
		return stockMailBus.getMailAdressNotAllowBySMSStockRate();
	}

	public int enableBySMSStockRate(StockMail stockMail) {
		return stockMailBus.enableBySMSStockRate(stockMail);
	}

	public int diableBySMSStockRate(StockMail stockMail) {
		return stockMailBus.diableBySMSStockRate(stockMail);
	}

	//controller 
	public Map getSMSStockByCodeOrName(Integer index, Integer size,String param) {
		Map map = smsStockBus.getSMSStockByCodeOrName(index, size,param);
		return map;
	}

	public SMSStock getSMSStockByStockCode(String code) {
		return smsStockBus.getSMSStockByStockCode(code);
	}

	// 更新每日短信股收盘价，最高，最低价,含有收盘价收盘价等信息
	public int update(SMSStock dailyStock) {
		return smsStockBus.updateSMSStock(dailyStock);
	}

	// 查询名字为空的短信股
	public List<SMSStock> getNoNameSMSStock() {
		return smsStockBus.getNoNameSMSStock();
	}

	// 查询每日短信股
	public List<SMSStock> selectAllSMSStockList() {
		List<SMSStock> list = smsStockBus.selectAllSMSStockList();
		return list;
	}

	// 按分钟发送短信股信息
	public void senderShortEmailByMinute(List<SMSStock> stockList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsEmailService.senderShortEmailByMinute(mail.getMailAddress(),stockList);
		}
	}

	// 按天发送短信股信息
	public void senderShortEmailByDay(List<SMSStock> stocList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsEmailService.senderShortEmailByDay(mail.getMailAddress(),
					stocList);
		}
	}
	
	//controller ==跌幅
	public Map getSMSDieFuStock(Integer index, Integer size,String codes) {
		return smsStockBus.getDieFuStock(index,size,codes);
	}
	//controller ==涨幅
	public Map getSMSZhangFuStock(Integer index, Integer size,String codes) {
		return smsStockBus.getZhangFuStock(index,size,codes);
	}

	// 按天发送短信股信息
	public void senderShortEmailByWeek(List<SMSStock> stocList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsEmailService.senderShortEmailByWeek(mail.getMailAddress(),
					stocList);
		}
	}

	// ///////////////////////////////////////正常股票开始//////////////////////////////////////////////////
	//用于修改股票的提示类别的
	public Stock getStockByStockCode(String code){
		return stockBus.getStockByStockCode(code);
	}
	
	public Map getStockByCodeOrName(Integer index,Integer size,String prama) {
		Map map  = stockBus.getStockByCodeOrName(index,size,prama);
		return map;
	}
	
	// 查询名字为空的股票
	public List<Stock> selectNoNameStockList() {
		List<Stock> list = stockBus.selectNoNameStockList();
		return list;
	}
	
	
	// 取得所有的
	public List<Stock> selectStockAllList() {
		List<Stock> list = stockBus.selectStockAllList();
		return list;
	}
	
	//controller 层所有的
   //控制台显示的 controller 层所有的
	public Map getConsolesByPage(int index, int pageSize,String codes) {
		Map map = stockBus.getConsolesByPage(index, pageSize,codes);
		return map;
	}
   //重要的 controller 
	public Map imp(int index, int pageSize,String codes) {
		Map map = stockBus.imp(index, pageSize,codes);
		return map;
	}
	//涨幅
	public Map getZhangFuStock(int index, int pageSize,String codes) {
		Map map = stockBus.getZhangFuStock(index, pageSize,codes);
		return map;
	}
	//跌幅
	public Map getDieFuStock(int index, int pageSize,String codes) {
		Map map = stockBus.getDieFuStock(index, pageSize,codes);
		return map;
	}

	//controller 层所有的 最近关注的
    public Map getRecentImpListByPage(Integer index,Integer size,String codes){
	    Map map = stockBus.getRecentImpListByPage(index,size,codes);
		return map;
    }
	
	
	//龙虎榜 controller 层所有的
	public Map getLongHuByPage(int index, int pageSize,String codes) {
		 Map map = stockBus.getLongHuByPage(index,pageSize,codes);
			return map;
	}

	
	//最佳榜单 , controller
	public Map getMostImpStockByPage(int index, int pageSize,String codes) {
		Map map = stockBus.getMostImpStockByPage(index,pageSize,codes);
		return map;
	}
	
	//controller 取得最近关注的
	public Map getRecentWaitImpByPage(int index, int pageSize,String codes) {
		Map map = stockBus.getRecentWaitImpByPage(index,pageSize,codes);
		return map;
	}

	
	// 保存每天股票日常信息表信息
	public int saveStockEveryDayInfo(StockEveryDayInfo recordDaily) {
		return recordDailyBus.saveDailyRecode(recordDaily);
	}

	public int saveStock(Stock stock) {
		return stockBus.save(stock);
	}

	// 保存每一笔交易记录
	public int saveRecordStockDealBus(StockEveryDayDeal recordStock) {
		return recordStockBus.save(recordStock);
	}

	// 更新stock表的每天收盘，最高，最低，昨收等。根据传不同的内容更新表里面不同的字段
	public int update(Stock stock) { // 重载过来的
		return stockBus.updateStock(stock);
	}

	// 发送金针见底的邮件的--即到达指定幅度的情况邮箱列表
	public List<StockMail> getMailAdressAllowByStockRate() {
		return stockMailBus.getMailAdressAllowByStockRate();
	}

	public List<StockMail> getMailAdressNotAllowByStockRate() {
		return stockMailBus.getMailAdressNotAllowByStockRate();
	}

	// 允许发送邮件
	public int enableEmailByStockRate(StockMail stockMail) {
		return stockMailBus.enableEmailByStockRate(stockMail);
	}

	// 禁止发送邮件
	public int diableEmailByStockRate(StockMail stockMail) {
		return stockMailBus.diableEmailByStockRate(stockMail);
	}

	// 正常股票金针见底---当天的
	public void sendEmailByRate(List<Stock> stocList,Map map) {
		List<StockMail> eLists = stockMailBus.getMailAdressAllowByStockRate();
		for (StockMail mail : eLists) {
			System.out.println(mail.getMailAddress());
			rateStockEmailService.senderEmailByRate(mail.getMailAddress(),stocList,map);
		}
	}

	// 超过指定幅度的近期---从库里查询的
	public void senderEmailByRecentImportantWatchByRate(List<Stock> stocList,
			Map<String, String> map) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService.senderEmailByMinute(mail.getMailAddress(), stocList,
					map);
		}
	}

	// 按分钟发送正常的股票详情
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void senderEmailByMinute(List<Stock> stocList, Map map) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService.senderEmailByMinute(mail.getMailAddress(), stocList,map);
		}
	}

	
	
	// 每天18点
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void senderEmailByDay(List<Stock> stocList, Map map) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService.senderEmailByDay(mail.getMailAddress(), stocList, map);
		}
	}

	// 每周五周日 20:30
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void senderEmailByWeek(List<Stock> stocList, Map map) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService
					.senderEmailByWeek(mail.getMailAddress(), stocList, map);
		}
	}

	// 得到每日一句
	public EveryDayTip getEveryDayTipById(Integer id) {
		return everyDayTipBus.getEveryDayTipById(id);
	}

	// 邮件发送====》浪费用的

	public void testEmail() {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService.emailManage(mail.getMailAddress());
		}
	}

	

	//得当是否是节日
	public Holiday getHoliday(){
		Holiday holiday = holidayBus.getIsHoliday();
		if(holiday!=null){
			return holiday;
		}else{
			return null;
		}
		//return holidayBus.getIsHoliday(date);
	}

	

}
