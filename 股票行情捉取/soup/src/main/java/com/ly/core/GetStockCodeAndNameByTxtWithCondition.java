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
public class GetStockCodeAndNameByTxtWithCondition {
	//Logger.getLogger(LoggerUtil.class.getName());
	private static Logger logger = Logger.getLogger("recharge");
	
	//https://zhidao.baidu.com/question/516517037.html
	public List  getWeekLineUpByTxtFile(){
		Date date = new Date();
		String timeStr = MyDateUtils.getTimeString(date, "yyyyMMdd");
		List list = null;
		String filepath = Constrant.TDX_ZHOU_XIAN_EXPORT_TXT_FILEPATH.replace("TIMESTR", timeStr);
		File file = new File(filepath);
		if(file.exists()){
			logger.debug("===> 从手动导出的Txt中读取股票代码及名称【"+filepath+"】文件定时任务开始");
			list = ReadTxtUtils.ReadTxtFile(filepath);
			logger.debug("读取股票代码及名称等信息读取完成，系统自动删除"+file.getName()+"文件");
			if(list!=null&&list.size()>0){
				list.remove(0);
				list.remove(list.size()-1);
			}
			file.delete();
		}/*else{
			logger.debug("通达信的周线低位向上涨的股票"+filepath+"文件不存在，系统从招商中读取。");
			filepath = Constrant.ZHAO_SHANG_ZHOU_XIAN_EXPORT_TXT_FILEPATH.replace("TIMESTR", timeStr);
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
