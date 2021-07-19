package com.ye.bus;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ye.constrant.Constrant;
import com.ye.excel.UUIDUtils;
import com.ye.redis.dao.RedisDao;
import com.ye.serivce.DataService;



/**
 * 异步处理数据抽到redis
 * @author changruan
 *
 */
@Component
public class ReportCacheToRedisBusAync {

	
	
	@Autowired
	private DataService dataService;
	

	public void queryFromDbToRedis(String month ) {
		String fileOutPath = Constrant.EXCEL_FILE_Out_Path + month + "\\";
		
		File file = new File(fileOutPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String fileSavePath = fileOutPath;
		String fileSavePathWithName01 = fileSavePath + "1.深圳各区优势产业分布【" + month + "】" + UUIDUtils.getUUID32() + ".xls";
		dataService.getData01(month,fileSavePathWithName01);
		dataService.getData02(month);
		dataService.getData03(month);
		dataService.getData04(month);
		dataService.getData05(month);
		dataService.getData06Taiwan(month);
	}
}
