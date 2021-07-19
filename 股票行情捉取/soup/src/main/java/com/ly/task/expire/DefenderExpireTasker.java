package com.ly.task.expire;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.impl.AccountDao;
import com.ly.pojo.Account;
import com.ly.task.expire.http.DefenderCookieExpire;

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
	//@Scheduled(cron = "0 0/50 * * * ?")
	//@Scheduled(fixedDelay=2*60*60*1000)
	//@Scheduled(cron="0/20 * * ? * *")
	@Scheduled(cron = "40 1 0/2 * * ?")
	public void taskerForDefender() throws Exception {
		List<Account> accounts = accountDao.find("from Account where isWeiTuo > 0");
		if(accounts!=null&&accounts.size()>0){
			for (int i = 0; i < accounts.size(); i++) {
				Account ac = accounts.get(i);
				Map map = defenderCookieExpire.getStockLastestInfo("SZ"+"00000"+(i+4), ac);
				/*String code = (String) map.get("stockCode")  ;
				String name = (String) map.get("stockName");
				String newPrice = (String) map.get("newPrice");
				logger.debug(code +"==========>"+ name + "==> "+ newPrice +"===>" + ac.getAccount() + " ," + ac.getName());*/
				logger.debug(map);
				Thread.sleep(1000*3);
			}
		}
	}
	
}
