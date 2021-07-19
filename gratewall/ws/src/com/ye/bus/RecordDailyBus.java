package com.ye.bus;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bo.domain.StockEveryDayInfo;
import com.ye.dao.StockEveryDayInfoDao;

@Transactional
@Component
public class RecordDailyBus {

	public static Logger log = LogManager.getLogger(RecordDailyBus.class);

	@Autowired
	private StockEveryDayInfoDao recordDailyDao;

	public List<StockEveryDayInfo> selectEveryDayPrice() {
		try {
			return recordDailyDao.selectEveryDayPrice();
		} catch (Exception e) {
			return null;
		}
	}

	public int saveDailyRecode(StockEveryDayInfo recordDaily) {
		try {
			recordDailyDao.save(recordDaily);
			return 1;
		} catch (Exception e) {
			log.log(Level.ERROR, "发生异常了" + e);
			return 0;
		}
	}

}
