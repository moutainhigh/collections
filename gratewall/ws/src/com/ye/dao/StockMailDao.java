package com.ye.dao;

import java.util.List;

import com.bo.domain.StockMail;

public interface StockMailDao {

	public List<StockMail> selectSenderMail();

	public List<StockMail> getMailAdressAllowByStockRate();

	public List<StockMail> getMailAdressNotAllowByStockRate();

	public List<StockMail> getMailAdressAllowBySMSStockRate();

	public List<StockMail> getMailAdressNotAllowBySMSStockRate();

	public int diableEmailByStockRate(StockMail stockMail);

	public int diableBySMSStockRate(StockMail stockMail);

	public int enableEmailByStockRate(StockMail stockMail);

	public int enableBySMSStockRate(StockMail stockMail);

	public int updateMailAddress(StockMail stockMail);

	public int isPermitted(StockMail stockMail);

	public void save(StockMail stockMail);

}
