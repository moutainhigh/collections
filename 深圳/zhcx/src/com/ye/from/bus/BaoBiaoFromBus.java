package com.ye.from.bus;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ye.dc.dao.BiaoBao_Asy_LogDao;
import com.ye.dc.pojo.BaoBiao_Asy_Log;
import com.ye.dc.pojo.Zhcx_Asy_Log;
import com.ye.from.dao.BiaoBiaoUserHyDao;
import com.ye.from.pojo.BaoBiaoUserHy;

@Service
public class BaoBiaoFromBus {
	private Logger debug = Logger.getLogger(BaoBiaoFromBus.class);
	
	
	@Autowired
	private BiaoBiaoUserHyDao	 baoBiaoHyDao;
	
	@Autowired
	private BiaoBao_Asy_LogDao baobiao_Asy_LogDao;

	public void saveToDb() {

		List<BaoBiaoUserHy> list = baoBiaoHyDao.getList();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BaoBiao_Asy_Log log = new BaoBiao_Asy_Log();

				BaoBiaoUserHy userHy = list.get(i);

				log.setUserid(userHy.getUserid());
				log.setName(userHy.getName());
				log.setDeptname(userHy.getDeptname());
				log.setTime(userHy.getTime());
				log.setIsAdd("0");
				
				baobiao_Asy_LogDao.save(log);
			}

		}
		
		debug.debug("同步完成");
	}

}
