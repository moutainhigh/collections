package com.ly.task.update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.StockDao;
import com.ly.pinyin.YePinYinUtils;
import com.ly.pojo.Stocks;

@Component
public class UpdateStockPinYinTask {
	private static Logger logger = Logger.getLogger(UpdateStockPinYinTask.class);
	@Autowired
	private StockDao stockDao;

	//@Scheduled(cron = "0/10 * * * * ?")
	//0 0 23 * * ?
	@Scheduled(cron = "0 3 16 * * ?")
	public void task() {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		int total = stockDao.getTotalsForDay();
		Map pageMap = new HashMap();
		int rows = 150;
		int step = total / rows + 1;
		// int step = (int) Math.ceil(total / (double) rows);
		for (int i = 1; i <= step; i++) {
			pageMap.put("pageIndex", (i - 1) * rows);
			pageMap.put("pageSize", rows);
			logger.info("当前迭代次数==========>  " + i + " ,还需要" + ((step - i) + 1) + " 次迭代爬虫");
			List<Stocks> stocks = stockDao.getListForDay(pageMap);
			if (stocks != null && stocks.size() > 0) {
				for (int j = 0; j < stocks.size(); j++) {
					Stocks sts = stocks.get(j);
					
					String name = sts.getName();
					if(name.contains("Ａ")){
						name = name.replace("Ａ", "A");
					}
					YePinYinUtils.convertChineseToPinyin(name);
					String headP = YePinYinUtils.getHeadPinyin();
					sts.setPinyin(headP);
					
					stockDao.updatePinyin(sts);
				}
				
			}
		}
	}
}
