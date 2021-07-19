package com.ly.task.forzicang;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.AccountDao;
import com.ly.pojo.Account;
import com.ly.stocktrade.trade.core.StockTradeCore;

@Component
public class GetZiChangTask {
	private static final Logger logger = Logger.getLogger("stockrefs");
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private StockTradeCore stockTradeCore;
	
	
	// @Scheduled(cron="0/50 * * * * ?")
	@Scheduled(cron = "0 0 5 ? * MON-FRI")
	public void getZiChang() {
		List<Account> accounts = accountDao.getAccountList();
		if (accounts != null && accounts.size() > 0) {
			for (int i = 0; i < accounts.size(); i++) {
				Account ac = accounts.get(i);
				Map<String, String> map = stockTradeCore.getZiChan(ac);
				logger.debug("当前账户可用资产===>" +map);
				if (map != null) {
					String enableZiChang = map.get("enable_balance");
					ac.setKeYongZiJing(enableZiChang);
					accountDao.save(ac);
				}
			}
		}

	}
	
	
	
	
	/*
	{"errmsg":"查询资产成功","results":{"money_type":"0","current_balance":"6742.19","enable_balance":"301.06","fetch_balance":"301.06","asset_balance":"35737.06","fetch_cash":null,"fund_balance":null,"market_value":"35436.00","opfund_market_value":null,"frozen_balance":"0","income_balance":null},"status":1}

		*/
}
