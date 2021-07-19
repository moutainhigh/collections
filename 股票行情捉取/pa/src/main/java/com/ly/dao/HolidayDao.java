package com.ly.dao;

import com.ly.pojo.Holiday;

public interface HolidayDao {

	public Holiday getHoliday();

	public void saveHoliday(Holiday h);

	public void delete();
}
