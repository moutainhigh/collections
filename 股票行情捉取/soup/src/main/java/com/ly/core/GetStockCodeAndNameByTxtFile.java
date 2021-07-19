package com.ly.core;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ly.common.util.Constrant;
import com.ly.common.util.MyDateUtils;
import com.ly.common.util.ReadTxtUtils;

@Component
public class GetStockCodeAndNameByTxtFile {
	//private static Logger logger = Logger.getLogger(GetStockCodeAndNameByTxtFile.class);
	private static Logger logger = Logger.getLogger("getStockCode");
	
	public List  getMarketAllStockCodeAndNameByTxtFile(){
		Date date = new Date();
		String timeStr = MyDateUtils.getTimeString(date, "yyyyMMdd");
		String filepath = Constrant.TDX_EXPORT_ALL_STOCK_TXT.replace("TIMESTR", timeStr);
		List list = null;
		File file = new File(filepath);
		if(file.exists()){
			logger.debug("===> 从导出的Txt中读取沪A和深A的所有股票代码及名称【"+filepath+"】文件定时任务开始");
			list = ReadTxtUtils.ReadTxtFile(filepath);
			logger.debug("读取完成，系统自动删除"+file.getName()+"文件");
			if(list!=null&&list.size()>0){
				list.remove(0);
				list.remove(list.size()-1);
			}
			file.delete();
		}/*else{
			logger.debug("从通达信导出的Txt中读取沪A和深A的所有股票代码及名称【"+filepath+"】文件不存在，系统将从招商中取。");
			filepath = Constrant.ZHAO_SHANG_EXPORT_ALL_STOCK_TXT.replace("TIMESTR", timeStr);
			file = new File(filepath);
			if(file.exists()){
				list = ReadTxtUtils.ReadTxtFile(filepath);
				logger.debug("读取完成，系统自动删除"+file.getName()+"文件");
				if(list!=null&&list.size()>0){
					list.remove(0);
					list.remove(list.size()-1);
				}
				file.delete();
			}
		}*/
		return list;
	}	
}
