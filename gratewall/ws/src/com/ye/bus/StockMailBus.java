package com.ye.bus;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bo.domain.StockMail;
import com.ye.dao.StockMailDao;

@Component
@Transactional
public class StockMailBus {
	public static Logger log = LogManager.getLogger(StockMailBus.class);

	@Autowired
	private StockMailDao stockMailDao;

	public List<StockMail> selectSenderMail() {
		List<StockMail> lists = null;
		try {
			lists = stockMailDao.selectSenderMail();
			return lists;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}

	}

	public List<StockMail> getMailAdressAllowByStockRate() {
		List<StockMail> lists = null;
		try {
			lists = stockMailDao.getMailAdressAllowByStockRate();
			return lists;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}
	

	public List<StockMail> getMailAdressNotAllowByStockRate() {
		List<StockMail> lists = null;
		try {
			lists = stockMailDao.getMailAdressNotAllowByStockRate();
			return lists;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}

	public List<StockMail> getMailAdressAllowBySMSStockRate() {
		List<StockMail> lists = null;
		try {
			lists = stockMailDao.getMailAdressAllowBySMSStockRate();
			return lists;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}
	
	public List<StockMail> getMailAdressNotAllowBySMSStockRate() {
		List<StockMail> lists = null;
		try {
			lists = stockMailDao.getMailAdressNotAllowBySMSStockRate();
			return lists;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}


	public int diableEmailByStockRate(StockMail stockMail) {
		try {
			stockMailDao.diableEmailByStockRate(stockMail);
			return 1;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return 0;
		}
	}
	
	
	public int diableBySMSStockRate(StockMail stockMail) {
		try {
			stockMailDao.diableBySMSStockRate(stockMail);
			return 1;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return 0;
		}
	}

	
	
	public int enableEmailByStockRate(StockMail stockMail) {
		try {
			stockMailDao.enableEmailByStockRate(stockMail);
			return 1;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return 0;
		}
	}
	
	
	public int enableBySMSStockRate(StockMail stockMail) {
		try {
			stockMailDao.enableBySMSStockRate(stockMail);
			return 1;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return 0;
		}
	}
}
