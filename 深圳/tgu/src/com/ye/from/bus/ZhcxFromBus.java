package com.ye.from.bus;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ye.dc.dao.WCM_Asy_LogDao;
import com.ye.dc.pojo.WCM_Asy_Log;
import com.ye.from.dao.WCMUserHyDao;
import com.ye.from.pojo.WCMUserHy;

@Service
public class ZhcxFromBus {
	private Logger debug = Logger.getLogger(ZhcxFromBus.class);
	@Autowired
	private WCMUserHyDao userHyDao;
	
	@Autowired
	private WCM_Asy_LogDao zhcx_Asy_LogDao;

	public void saveToDb() {

		List<WCMUserHy> list = userHyDao.getList();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				WCM_Asy_Log log = new WCM_Asy_Log();

				WCMUserHy userHy = list.get(i);

				log.setUserid(userHy.getUserid());
				log.setName(userHy.getName());
				log.setDeptname(userHy.getDeptname());
				log.setTime(userHy.getTime());
				log.setIsAdd("0");
				
				zhcx_Asy_LogDao.save(log);
			}

		}
		
		debug.debug("同步完成");
	}

}
