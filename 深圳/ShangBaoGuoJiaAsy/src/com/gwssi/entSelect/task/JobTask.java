package com.gwssi.entSelect.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gwssi.entSelect.service.MyBatisEntSelectSerivceForTask;




//http://localhost:8080/out/show.do
@Component
public class JobTask {

	@Autowired
	private MyBatisEntSelectSerivceForTask myBatisEntSelectSerivceForTask;
	
	 //@Scheduled(cron = "0/50 * * * * ?")
	//@Scheduled(cron = "40 01 10 ? * *")
	@Scheduled(cron = "40 01 1 ? * *")
	public void task() throws Exception {
		
		//String queryParams = " where   t.lastdate >=sysdate -1";
		
		
		String queryParams = " where t.lastdate >=sysdate -1 ";
		
/*		
		String queryParams = " where  NOT EXISTS\n" + 
				"(SELECT 1 FROM E_ALTER_RECODER_SJ S WHERE T.ID = S.ALTID)";*/

		
		
		String  totalStr = myBatisEntSelectSerivceForTask.queryTotal(queryParams);
		int  total = Integer.valueOf(totalStr);
		int pageSize = 5000;
		int step = (int)total/pageSize +1;
	
		for (int i = 1; i <= step; i++) {
			System.out.println("======== 总步数===> " + step + "  ,每页大小===> "+ pageSize + " , 总记录数===> " + total + "条 ,还有条没有爬虫到 "  + (total-i*pageSize));
			//entSelectService.queryBiangengDetail(pageSize,i);
			myBatisEntSelectSerivceForTask.insert(pageSize, i,queryParams);
		}
		System.out.println("本次同步数===》 "  + total);
	}

}
