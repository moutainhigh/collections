package com.ly.task.jsoup.day.cal.computing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.json.JSONUtils;
import com.ly.common.util.SystemSoupConstrant;
import com.ly.dao.impl.StockDao;
import com.ly.dao.impl.StockPointerDao;
import com.ly.dao.impl.Stock_K_line_Day_Data_List_Dao;
import com.ly.email.EmailTemplate;
import com.ly.pojo.Stock;
import com.ly.pojo.StockPointer;
import com.ly.task.sendemail.SendOffsetStockEmail;
import com.ly.vo.StockVo;

//@Component
public class CalcStockMaInfo {


	@Autowired
	private Stock_K_line_Day_Data_List_Dao datasDao;

	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private StockPointerDao stockPointerDao;
	
	@Autowired
	private SendOffsetStockEmail sendOffsetStockEmail;


	// @Scheduled(cron="*/10 * * * * ?")
	//@Scheduled(cron = "30 10 16 ? * *")
	public void getStockInfo() throws Exception {
		stockPointerDao.truncateTable(StockPointer.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");                
	    Calendar c = Calendar.getInstance();           
	    c.add(Calendar.DATE, - 5);           
	    Date time = c.getTime();         
	    String preDay = sdf.format(time); 
		
		StringBuffer columSb = new StringBuffer();
		columSb.append(" select ")	;	
		columSb.append("  t.code,");
		columSb.append(" t1.name,t.closePrice,t.ma5,t.ma10,t.ma20,t.ma30,t.totalHand,t1.liuTongGu,t.openPrice , ");
		columSb.append(" 	convert((t.closePrice - t.ma30)/t.ma30 * 100 ,decimal(6,2)), " );
		columSb.append(" 	convert((t.openPrice - t.ma30 )/t.ma30 * 100,decimal(6,2))  ");
		columSb.append(" from Stock_K_line_Day_DataList t inner join Stock t1 on t.code = t1.code  ");
		StringBuffer sb = new StringBuffer();
		StringBuffer sbTemp = null;
		
		StockPointer po = null;
		List<StockPointer> poList = new ArrayList<StockPointer>();;

		Long total = stockDao.findCount(Stock.class);
		int rows = SystemSoupConstrant.rows;
		int step = (int) (total / rows + 1);
		sb.append(" where  t.closePrice - t.openPrice > 0   ");
		sb.append(" and  (t.closePrice - t.ma30)/t.ma30*100 > 1 " );
		sb.append(" and (t.openPrice - t.ma30)/t.ma30*100 > 1  " );
		sb.append(" and t.date >= " + preDay);
		sb.append(" and t.code in ( ");
		
		for (int i = 1; i <= step; i++) {
			int pageNo = i;
			int pageSize = rows;
			sbTemp = new StringBuffer(); 
			List<Stock> list = stockDao.findByPage("from Stock", pageNo, pageSize);
			if (list != null && list.size() > 0) {
				for (Stock sts : list) {
					sbTemp.append("'"+sts.getCode()+"',");
				}
			}
			
			
			List<Object[]> list2 = datasDao.getStock(columSb.toString() +sb.toString()+ sbTemp.toString().substring(0,sbTemp.length()-1)+")  ");
			for (int k = 0; k < list2.size(); k++) {
				Object[] obj = list2.get(k);
				for (int j = 0; j < obj.length; j++) {
					
					po = new StockPointer();
					po.setCode(obj[0].toString());
					
					poList.add(po);
				}
			}
		}

		
		
		stockPointerDao.batchSave(poList);
		
		sendOffsetStockEmail.task();
		
	}
}
