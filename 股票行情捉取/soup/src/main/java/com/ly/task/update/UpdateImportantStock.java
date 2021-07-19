package com.ly.task.update;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.impl.Important_Stock_Dao;
import com.ly.dao.impl.StockDao;
import com.ly.pojo.Important_Stock;
import com.ly.pojo.Stock;

@Component
public class UpdateImportantStock {
	
	@Autowired
	private StockDao stockDao;
	@Autowired
	private Important_Stock_Dao importantDao;
	
	
	//@Scheduled(cron = "0 0 1 17 * ?")
	//@Scheduled(cron = "0 */5 * * * ?")
	//@Scheduled(cron="*/50 * * * * ?")
	@Scheduled(cron = "0 */5 * * * ?")
	public void task01() {
		List<Important_Stock> list = importantDao.find("from Important_Stock");
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Important_Stock imp = list.get(i);
				List<Stock> rets = stockDao.find("from Stock where code = ? ", imp.getCode());
				if(rets!=null&&rets.size()>0){
					Stock sts = rets.get(0);
					imp.setName(sts.getName());
					importantDao.update(imp);
				}
			}
		}
	}
}
