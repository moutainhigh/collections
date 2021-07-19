package com.ye.bus;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bo.domain.SMSStockEveryDayInfo;
import com.ye.dao.SMSStockEveryDayInfoDao;

//短信股
@Transactional
@Component
public class RecordDailyStockBus {

	public static Logger log = LogManager.getLogger(RecordDailyStockBus.class);
	
	@Autowired
	private SMSStockEveryDayInfoDao recordDailyStockDao;

	public List<SMSStockEveryDayInfo> selectEveryDayPrice() {
		return recordDailyStockDao.selectEveryDayPrice();
	}

	public int saveDailyRecode(SMSStockEveryDayInfo recordDaily) {
		try {
			recordDailyStockDao.save(recordDaily);
			return 1;
		} catch (Exception e) {
			log.log(Level.ERROR,"发生异常了" + e);
			return 0;
		}
	}
}
