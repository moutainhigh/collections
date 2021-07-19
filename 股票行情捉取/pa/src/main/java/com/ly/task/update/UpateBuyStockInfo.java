package com.ly.task.update;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.BuyDao;
import com.ly.http.JuheHttp;
import com.ly.pojo.Buy;

@Component
public class UpateBuyStockInfo {
	private static final Logger logger = Logger.getLogger("juhe");

	@Autowired
	private BuyDao buyDao;

	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 */15 * * * ?")
	public void updatesBMinute() {
		List<Buy> buys = buyDao.getBuyList();
		logger.debug("==========> 需要更新的买单列表===> " + buys);
		if (buys != null && buys.size() > 0) {
			for (int i = 0; i < buys.size(); i++) {
				Buy buy = buys.get(i);
				String code = buy.getCode();

				if (code.indexOf("6") == 0) {
					code = "sh" + code;
				} else {
					code = "sz" + code;
				}

				Map<String, String> map = JuheHttp.getRequest1(code);
				String name = map.get("name");
				buy.setName(name);
				buyDao.save(buy);
			}
		}
	}
}
