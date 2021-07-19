package com.ye.bus;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bo.domain.Holiday;
import com.ye.dao.HolidayDao;

@Transactional
@Repository
public class HolidayBus {

	@Autowired
	private HolidayDao holidayDao;

	public Holiday getIsHoliday() {
		Holiday holiday = holidayDao.getIsHoliday();
		if(holiday!=null){
			return holiday;
		}else{
			return null;
		}
	}
}
