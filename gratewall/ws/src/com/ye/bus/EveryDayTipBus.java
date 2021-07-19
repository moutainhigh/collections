package com.ye.bus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bo.domain.EveryDayTip;
import com.ye.dao.EveryDayDao;

@Transactional
@Service
public class EveryDayTipBus {

	public static Logger log = LogManager.getLogger(EveryDayTipBus.class);

	@Autowired
	private EveryDayDao everyDayDao;

	public EveryDayTip getEveryDayTipById(Integer id) {
		return everyDayDao.getEveryDayTipById(id);
	}
}
