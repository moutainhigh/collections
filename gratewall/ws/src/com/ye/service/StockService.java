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
	private EmailService emailService;// ������

	@Autowired
	private RateStockEmailService rateStockEmailService;// �����ɰ��������

	@Autowired
	private SMSEmailService smsEmailService;// ���Ź�

	@Autowired
	private SMSRateStockEmailService smsRateStockEmailService;// ���Źɰ��������

	@Autowired
	private TemplateSendEmail templateSendEmail;

	@Autowired
	private RecordStockBus recordStockBus;

	@Autowired
	private StockMailBus stockMailBus;

	@Autowired
	private RecordDailyBus recordDailyBus; // ��¼��Ʊ��ÿһ�ʼ�¼

	@Autowired
	private SMSStockBus smsStockBus; // ���Ź�

	@Autowired
	private SMSStockEveryDayInfoDao recordDailyStockDao;// ���Ź�ÿ����Ϣ

	@Autowired
	private SMSStockEveryDayDealDao dailyStockEveryDayDealDao;// ���Ź�ÿ��ɽ�����

	@Autowired
	private EveryDayTipBus everyDayTipBus;// ÿ��һ��

	//controller �㣬�õ�
	public Map getAllSMSStockByPage(int index, int pageSize) {
		Map map = smsStockBus.getAllSMSStockByPage(index,pageSize);
		return map;
	}
	
	// ���Ź�Ʊ�������--����
	public void sendSMSEmailByRate(List<SMSStock> stocList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsRateStockEmailService.senderShortEmailByRate(
					mail.getMailAddress(), stocList);
		}
	}

	// ���ȹ���Ķ��Źɵ�����--����ָ�����ȵĴ����ݿ������趨�ķ���
	public void senderSMSEmailByRecentImportantWatchByRate(
			List<SMSStock> stocList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsEmailService.senderShortEmailByMinute(mail.getMailAddress(),
					stocList);
		}
	}

	// ����ÿ�ն��Źɵĳɽ���¼��
	public int saveSMSStockEveryDayDeal(
			SMSStockEveryDayDeal dailyStockEveryDayDeal) {
		return dailyStockEveryDayDealDao.save(dailyStockEveryDayDeal);
	}

	// ����ÿ�ն��ŹɵĽ��׼�¼�����ͼ۵�
	public int saveSMSEveryDayInfo(SMSStockEveryDayInfo stock) {
		return recordDailyStockDao.save(stock);
	}

	// ���Ź�---���ͽ�����׵��ʼ���--������ָ�����ȵ���������б�
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

	// ����ÿ�ն��Ź����̼ۣ���ߣ���ͼ�,�������̼����̼۵���Ϣ
	public int update(SMSStock dailyStock) {
		return smsStockBus.updateSMSStock(dailyStock);
	}

	// ��ѯ����Ϊ�յĶ��Ź�
	public List<SMSStock> getNoNameSMSStock() {
		return smsStockBus.getNoNameSMSStock();
	}

	// ��ѯÿ�ն��Ź�
	public List<SMSStock> selectAllSMSStockList() {
		List<SMSStock> list = smsStockBus.selectAllSMSStockList();
		return list;
	}

	// �����ӷ��Ͷ��Ź���Ϣ
	public void senderShortEmailByMinute(List<SMSStock> stockList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsEmailService.senderShortEmailByMinute(mail.getMailAddress(),stockList);
		}
	}

	// ���췢�Ͷ��Ź���Ϣ
	public void senderShortEmailByDay(List<SMSStock> stocList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsEmailService.senderShortEmailByDay(mail.getMailAddress(),
					stocList);
		}
	}
	
	//controller ==����
	public Map getSMSDieFuStock(Integer index, Integer size,String codes) {
		return smsStockBus.getDieFuStock(index,size,codes);
	}
	//controller ==�Ƿ�
	public Map getSMSZhangFuStock(Integer index, Integer size,String codes) {
		return smsStockBus.getZhangFuStock(index,size,codes);
	}

	// ���췢�Ͷ��Ź���Ϣ
	public void senderShortEmailByWeek(List<SMSStock> stocList) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			smsEmailService.senderShortEmailByWeek(mail.getMailAddress(),
					stocList);
		}
	}

	// ///////////////////////////////////////������Ʊ��ʼ//////////////////////////////////////////////////
	//�����޸Ĺ�Ʊ����ʾ����
	public Stock getStockByStockCode(String code){
		return stockBus.getStockByStockCode(code);
	}
	
	public Map getStockByCodeOrName(Integer index,Integer size,String prama) {
		Map map  = stockBus.getStockByCodeOrName(index,size,prama);
		return map;
	}
	
	// ��ѯ����Ϊ�յĹ�Ʊ
	public List<Stock> selectNoNameStockList() {
		List<Stock> list = stockBus.selectNoNameStockList();
		return list;
	}
	
	
	// ȡ�����е�
	public List<Stock> selectStockAllList() {
		List<Stock> list = stockBus.selectStockAllList();
		return list;
	}
	
	//controller �����е�
   //����̨��ʾ�� controller �����е�
	public Map getConsolesByPage(int index, int pageSize,String codes) {
		Map map = stockBus.getConsolesByPage(index, pageSize,codes);
		return map;
	}
   //��Ҫ�� controller 
	public Map imp(int index, int pageSize,String codes) {
		Map map = stockBus.imp(index, pageSize,codes);
		return map;
	}
	//�Ƿ�
	public Map getZhangFuStock(int index, int pageSize,String codes) {
		Map map = stockBus.getZhangFuStock(index, pageSize,codes);
		return map;
	}
	//����
	public Map getDieFuStock(int index, int pageSize,String codes) {
		Map map = stockBus.getDieFuStock(index, pageSize,codes);
		return map;
	}

	//controller �����е� �����ע��
    public Map getRecentImpListByPage(Integer index,Integer size,String codes){
	    Map map = stockBus.getRecentImpListByPage(index,size,codes);
		return map;
    }
	
	
	//������ controller �����е�
	public Map getLongHuByPage(int index, int pageSize,String codes) {
		 Map map = stockBus.getLongHuByPage(index,pageSize,codes);
			return map;
	}

	
	//��Ѱ� , controller
	public Map getMostImpStockByPage(int index, int pageSize,String codes) {
		Map map = stockBus.getMostImpStockByPage(index,pageSize,codes);
		return map;
	}
	
	//controller ȡ�������ע��
	public Map getRecentWaitImpByPage(int index, int pageSize,String codes) {
		Map map = stockBus.getRecentWaitImpByPage(index,pageSize,codes);
		return map;
	}

	
	// ����ÿ���Ʊ�ճ���Ϣ����Ϣ
	public int saveStockEveryDayInfo(StockEveryDayInfo recordDaily) {
		return recordDailyBus.saveDailyRecode(recordDaily);
	}

	public int saveStock(Stock stock) {
		return stockBus.save(stock);
	}

	// ����ÿһ�ʽ��׼�¼
	public int saveRecordStockDealBus(StockEveryDayDeal recordStock) {
		return recordStockBus.save(recordStock);
	}

	// ����stock���ÿ�����̣���ߣ���ͣ����յȡ����ݴ���ͬ�����ݸ��±����治ͬ���ֶ�
	public int update(Stock stock) { // ���ع�����
		return stockBus.updateStock(stock);
	}

	// ���ͽ�����׵��ʼ���--������ָ�����ȵ���������б�
	public List<StockMail> getMailAdressAllowByStockRate() {
		return stockMailBus.getMailAdressAllowByStockRate();
	}

	public List<StockMail> getMailAdressNotAllowByStockRate() {
		return stockMailBus.getMailAdressNotAllowByStockRate();
	}

	// �������ʼ�
	public int enableEmailByStockRate(StockMail stockMail) {
		return stockMailBus.enableEmailByStockRate(stockMail);
	}

	// ��ֹ�����ʼ�
	public int diableEmailByStockRate(StockMail stockMail) {
		return stockMailBus.diableEmailByStockRate(stockMail);
	}

	// ������Ʊ�������---�����
	public void sendEmailByRate(List<Stock> stocList,Map map) {
		List<StockMail> eLists = stockMailBus.getMailAdressAllowByStockRate();
		for (StockMail mail : eLists) {
			System.out.println(mail.getMailAddress());
			rateStockEmailService.senderEmailByRate(mail.getMailAddress(),stocList,map);
		}
	}

	// ����ָ�����ȵĽ���---�ӿ����ѯ��
	public void senderEmailByRecentImportantWatchByRate(List<Stock> stocList,
			Map<String, String> map) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService.senderEmailByMinute(mail.getMailAddress(), stocList,
					map);
		}
	}

	// �����ӷ��������Ĺ�Ʊ����
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void senderEmailByMinute(List<Stock> stocList, Map map) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService.senderEmailByMinute(mail.getMailAddress(), stocList,map);
		}
	}

	
	
	// ÿ��18��
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void senderEmailByDay(List<Stock> stocList, Map map) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService.senderEmailByDay(mail.getMailAddress(), stocList, map);
		}
	}

	// ÿ�������� 20:30
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void senderEmailByWeek(List<Stock> stocList, Map map) {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService
					.senderEmailByWeek(mail.getMailAddress(), stocList, map);
		}
	}

	// �õ�ÿ��һ��
	public EveryDayTip getEveryDayTipById(Integer id) {
		return everyDayTipBus.getEveryDayTipById(id);
	}

	// �ʼ�����====���˷��õ�

	public void testEmail() {
		List<StockMail> eLists = stockMailBus.selectSenderMail();
		for (StockMail mail : eLists) {
			emailService.emailManage(mail.getMailAddress());
		}
	}

	

	//�õ��Ƿ��ǽ���
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
