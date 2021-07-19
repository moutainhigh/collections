package com.ly.task.defender;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.AccountDao;
import com.ly.pojo.Account;
import com.ly.stocktrade.exprire.DefenderCookieExpire;

//https://www.cnblogs.com/superman66/p/4565723.html
//https://blog.csdn.net/u010416101/article/details/80625862
@Component
public class DefenderExpireTasker {
	
	//private static Logger logger = Logger.getLogger(DefenderExpireTasker.class);
	
	private static final Logger logger = Logger.getLogger("expire");
	
	
	@Autowired
	private DefenderCookieExpire defenderCookieExpire;
	
	@Autowired
	private AccountDao accountDao;
	
	
	//https://blog.csdn.net/baidu_31301039/article/details/78982068
	//0 30 0/2 * * ?      
	//@Scheduled(cron = "0/5 * * * * ?")
	//@Scheduled(cron = "0 0/50 * * * ?")
	//@Scheduled(cron = "0/5 * * * * ?")
	@Scheduled(cron = "40 10 0/1 * * ?")
	public void taskerForDefender() {
		List<Account> accounts = accountDao.getAccountList();
		if(accounts!=null&&accounts.size()>0){
			for (int i = 0; i < accounts.size(); i++) {
				Account ac = accounts.get(i);
				Map map = defenderCookieExpire.getStockLastestInfo("601398", ac);
				logger.debug(map + "===" + ac);
			}
		}
	}
	
}
