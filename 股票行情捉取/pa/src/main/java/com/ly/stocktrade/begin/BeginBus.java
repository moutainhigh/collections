package com.ly.stocktrade.begin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.common.utils.KPTimeUtils;
import com.ly.dao.HolidayDao;
import com.ly.pojo.Holiday;


@Component
public class BeginBus {
	@Autowired
	private HolidayDao holidayDao;
	
	
	public  boolean isRun(){
		boolean flag = false;
		Holiday h = holidayDao.getHoliday();
		flag = KPTimeUtils.getIsHoliday(h);
		if(flag){
			flag =	KPTimeUtils.getIsBegin();
		}
		return flag;
	}
}
