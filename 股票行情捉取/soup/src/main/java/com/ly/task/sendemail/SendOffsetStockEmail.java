package com.ly.task.sendemail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.json.JSONUtils;
import com.ly.dao.impl.StockDao;
import com.ly.dao.impl.StockPointerDao;
import com.ly.dao.impl.Stock_K_line_Day_Data_List_Dao;
import com.ly.email.EmailTemplate;
import com.ly.vo.StockVo;

//@Component
public class SendOffsetStockEmail {
	
	
	@Autowired
	private EmailTemplate template;
	@Autowired
	private Stock_K_line_Day_Data_List_Dao datasDao;
	
	public String getTime() {
		List times = datasDao.find("select max(date) from  Stock_K_line_Day_DataList");
		String time = (String) times.get(0);
		return time;// new Date()为获取当前系统时间
	}

	public String getTitle(String title) {
		title = title + ",数据生成时间【" + getTime() + "】";
		return title;
	}
	
	public void task(){
		List<StockVo> retList = new ArrayList<StockVo>();
		StockVo vo = null;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");                
	    Calendar c = Calendar.getInstance();  
	    Date time = c.getTime();         
	    String preDay = sdf.format(time); 
		sb.append(" select s1.code,s1.name,s2.closePrice,s2.ma5,s2.ma10,s2.ma20,s2.ma30,s2.totalHand,s1.liuTongGu from stock s1 INNER JOIN stock_k_line_day_datalist s2 ");
		sb.append(" on s1.code = s2.code ");
		sb.append(" INNER JOIN  (select t.code,count(1)  from stockpointer t ");
		sb.append(" GROUP BY t.code  ");
		sb.append(" ORDER BY count(1) desc) x ");
		sb.append(" on x.code = s1.code ");
		sb.append(" where s2.closePrice - s2.openPrice > 0 ");
		sb.append(" and  s2.date='"+preDay+"'");
		sb.append(" order by  s2.closePrice desc");

		List<Object[]> list2 = datasDao.getStock(sb.toString());
		
		for (int k = 0; k < list2.size(); k++) {
			Object[] obj = list2.get(k);	
			
			
			
			//for (int j = 0; j < obj.length; j++) {
				vo = new StockVo();
				vo.setCode(obj[0].toString());
				vo.setName(obj[1].toString());
				vo.setClosePrice(obj[2].toString());
				vo.setMa5(obj[3].toString());
				vo.setMa10(obj[4].toString());
				vo.setMa20(obj[5].toString());
				vo.setMa30(obj[6].toString());
				vo.setTotalHand(obj[7].toString());
				vo.setLiuTongGu(obj[8].toString());
				
				System.out.println(vo.getName());
				
				retList.add(vo);
			//}
		}
		
		String title = "趋势上30日线，趋势慢向上的,共" + retList.size() + "条";
		title = getTitle(title);
		
		try {
			template.send(title, retList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
