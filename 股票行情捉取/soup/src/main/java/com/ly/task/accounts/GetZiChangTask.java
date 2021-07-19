package com.ly.task.accounts;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.core.StockTradeCore;
import com.ly.dao.impl.AccountDao;
import com.ly.pojo.Account;
//https://blog.csdn.net/sinat_15153911/article/details/76223529
//https://404.life/
//http://www.cssmoban.com/tags.asp?n=404
@Component
public class GetZiChangTask {
	private static final Logger logger = Logger.getLogger("stockrefs");
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private StockTradeCore stockTradeCore;
	
	
	// 
	//@Scheduled(cron = "0 03 22 ? * MON-FRI")
	//@Scheduled(cron="0/50 * * * * ?")
	@Scheduled(cron = "0 */20 * * * ?")
	public void getZiChang() throws InterruptedException {
		List<Account> accounts = accountDao.find("from Account");
		if (accounts != null && accounts.size() > 0) {
			for (int i = 0; i < accounts.size(); i++) {
				Account ac = accounts.get(i);
				Map<String, String> map = stockTradeCore.getZiChan(ac);
				logger.debug("当前账户可用资产===>" +map);
				if (map != null) {
					String CURRENCY= map.get("CURRENCY");
					String POSITION_VAL= map.get("POSITION_VAL");
					String ASSERT_VAL= map.get("ASSERT_VAL");
					String  KEYONG= map.get("KEYONG");
					String KEQU= map.get("KEQU");
					ac.setKeyong(KEYONG);
					accountDao.update(ac);
				}
				Thread.sleep(1000*3);
			}
		}

	}
	
	
	
	
	/*
	{"errmsg":"查询资产成功","results":{"money_type":"0","current_balance":"6742.19","enable_balance":"301.06","fetch_balance":"301.06","asset_balance":"35737.06","fetch_cash":null,"fund_balance":null,"market_value":"35436.00","opfund_market_value":null,"frozen_balance":"0","income_balance":null},"status":1}

		*/
}
