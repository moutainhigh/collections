package com.baobiao.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baobiao.file.YeFileDirCopy;


@Component
public class YeTask {

	@Autowired
	private YeFileDirCopy yeFileDirCopy;
	
	//每个月的15号和25号中午12点执行
	@Scheduled(cron="0 00 12 15,25 * ?")
	public void task01(){
		yeFileDirCopy.toolsOfYe();
	}
}
