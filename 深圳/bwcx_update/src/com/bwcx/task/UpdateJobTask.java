package com.bwcx.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bwcx.bus.FileCopy;
import com.bwcx.bus.ServiceBus;
import com.bwcx.dao.UpdateDao;

@Component
public class UpdateJobTask {

	
	@Autowired
	private UpdateDao updateDao;
	
	
	
	//@Scheduled(fixedDelay=5000)
	@Scheduled(cron="0 26 9,11,14,16,20 * * MON-FRI ")
	public void taskInsert() {
		String sql = ServiceBus.insertSQL();
		
		updateDao.insert(sql);
		
		
		String sql3 = ServiceBus.getThumbPic();
		
		List list =  updateDao.select(sql3);
		
		
		if(list!=null&&list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				
				Map map = (Map) list.get(i);
				
				String fileName = (String) map.get("FILENAME");
				FileCopy.fileCopySwitch(fileName);
			}
			
			
		}
		
		
		
		
		
		String sql2 = ServiceBus.updateSQL(); 
		updateDao.update(sql2);
		
		
		
		
	}

}
