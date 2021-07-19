package com.ye.from.bus;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ye.dc.dao.Zhcx_Asy_LogDao;
import com.ye.dc.pojo.Zhcx_Asy_Log;
import com.ye.from.dao.ZHCXUserHyDao;
import com.ye.from.pojo.ZHCXUserHy;

@Service
public class ZhcxFromBus {
	private Logger debug = Logger.getLogger(ZhcxFromBus.class);
	@Autowired
	private ZHCXUserHyDao userHyDao;
	
	@Autowired
	private Zhcx_Asy_LogDao zhcx_Asy_LogDao;

	public void saveToDb() {

		List<ZHCXUserHy> list = userHyDao.getList();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Zhcx_Asy_Log log = new Zhcx_Asy_Log();

				ZHCXUserHy userHy = list.get(i);

				log.setUserid(userHy.getUserid().trim());
				log.setName(userHy.getName().trim());
				log.setDeptname(userHy.getDeptname());
				log.setTime(userHy.getTime());
				log.setIsAdd("0");
				
				zhcx_Asy_LogDao.save(log);
			}

		}
		
		debug.debug("同步完成");
	}

}
