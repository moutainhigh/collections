package com.ye.bus;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bo.domain.StockEveryDayDeal;
import com.ye.dao.StockEveryDayDealDao;

@Transactional
@Component
public class RecordStockBus {

	public static Logger log = LogManager.getLogger(RecordStockBus.class);
	
	@Autowired
	private StockEveryDayDealDao recordStockDao;

	public List<StockEveryDayDeal> selectRateList() {
		return recordStockDao.selectRateList();
	}

	public List<StockEveryDayDeal> selectEveryDayPrice() {
		return recordStockDao.selectEveryDayPrice();
	}

	public int save(StockEveryDayDeal stock) {
		try {
			recordStockDao.save(stock);
			return 1;
		} catch (Exception e) {
			log.log(Level.ERROR,e.getMessage());
			return 0;
		}
	}
	public int updateRecordStockDeal(StockEveryDayDeal stock) {
		try {
			recordStockDao.update(stock);
			return 1;
		} catch (Exception e) {
			log.log(Level.ERROR,e.getMessage());
			return 0;
		}
	}
}
