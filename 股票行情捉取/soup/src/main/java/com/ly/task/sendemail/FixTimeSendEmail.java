package com.ly.task.sendemail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.SystemConfig;
import com.ly.dao.impl.Important_Stock_Dao;
import com.ly.dao.impl.Important_Stock_Up_Dao;
import com.ly.dao.impl.Stock_ClosePrice_Ma20_Comp_Dao;
import com.ly.dao.impl.Stock_ClosePrice_Ma5_Comp_Dao;
import com.ly.dao.impl.Stock_Close_Price_Big_Ma30Dao;
import com.ly.dao.impl.Stock_Da_Yang_Dao;
import com.ly.dao.impl.Stock_K_line_Day_Data_List_Dao;
import com.ly.dao.impl.Stock_Shou_Yang_One_DayDao;
import com.ly.dao.impl.Stock_Shou_Yang_Two_DayDao;
import com.ly.email.EmailTemplate;
import com.ly.pojo.Important_Stock;
import com.ly.pojo.Important_Stock_Up;
import com.ly.pojo.Stock_ClosePrice_Ma20_Comp;
import com.ly.pojo.Stock_ClosePrice_Ma5_Comp;
import com.ly.pojo.Stock_Close_Price_Big_Ma30;
import com.ly.pojo.Stock_Da_Yang_Inf;
import com.ly.pojo.Stock_Shou_Yang_One_Day;
import com.ly.pojo.Stock_Shou_Yang_Two_Day;
@Component
public class FixTimeSendEmail {
	@Autowired
	private Stock_K_line_Day_Data_List_Dao datasDao;
	@Autowired
	private EmailTemplate template;
	@Autowired
	private Stock_Shou_Yang_One_DayDao oneDayDao;
	
	@Autowired
	private Stock_Shou_Yang_Two_DayDao twoDayDao;
	
	@Autowired
	private Stock_Close_Price_Big_Ma30Dao maDao;
	
	@Autowired
	private Stock_Da_Yang_Dao dayangDao;
	
	@Autowired
	private Important_Stock_Up_Dao impStockUpDao;
	
	
	@Autowired
	private Stock_ClosePrice_Ma5_Comp_Dao ma5Dao;
	
	
	@Autowired
	private Stock_ClosePrice_Ma20_Comp_Dao ma20UpDao;
	
	public String getTime() {
		List times = datasDao.find("select max(date) from  Stock_K_line_Day_DataList" );
		String time = (String) times.get(0);
		return time;// new Date()为获取当前系统时间
	}

	public String getTitle(String title) {
		title = title + ",数据生成时间【" + getTime() + "】";
		return title;
	}
	
	
	@Scheduled(cron = "0 0 7,17 * * ?")
	public void task01(){
		List<Stock_Shou_Yang_One_Day> listDay = oneDayDao.find("from Stock_Shou_Yang_One_Day " +SystemConfig.orderStr);
		String title = "当天涨幅度大于1%的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//@Scheduled(cron="*/10 * * * * ?")
	@Scheduled(cron = "0 05 7,17 * * ?")
	public void task02(){
		List<Stock_Shou_Yang_Two_Day> listDay = twoDayDao.find("from Stock_Shou_Yang_Two_Day " +SystemConfig.orderStr);
		String title = "两天涨幅度大于1%的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Scheduled(cron = "0 10 7,17 * * ?")
	public void task03(){
		List<Stock_Close_Price_Big_Ma30> listDay = maDao.find("from Stock_Close_Price_Big_MaInfo " +SystemConfig.orderStr);
		String title = "现价占上20日线且大于ma30的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 15 7,17 * * ?")
	public void task04(){
		List<Stock_Da_Yang_Inf> listDay = dayangDao.find("from Stock_Da_Yang_Inf " +SystemConfig.orderStr);
		String title = "本日收大阳的,即大于5%及以上的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Scheduled(cron="*/10 * * * * ?")
	@Scheduled(cron = "0 20 7,17 * * ?")
	public void task05(){
		/*List<Important_Stock> list = impDao.find("from Important_Stock");
		StringBuffer sb = new StringBuffer();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Important_Stock sts = list.get(i);
				sb.append("'"+sts.getCode() +"'" + ",");
			}
		}
		String sql = sb.toString().substring(0,sb.toString().length()-1);
		List<Stock_Shou_Yang_One_Day> listDay = oneDayDao.find("from Stock_Shou_Yang_One_Day where code in ( "+ sql+") " +SystemConfig.orderStr);
		String title = "关注的股票的走势,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		List<Important_Stock_Up> listDay = impStockUpDao.find("from Important_Stock_Up " +SystemConfig.orderStr);
		String title = "关注的股票的走势,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Scheduled(cron = "0 25 7,17 * * ?")
	public void task09(){
		List<Stock_ClosePrice_Ma5_Comp> listDay = ma5Dao.find("from Stock_ClosePrice_Ma5_Comp " +SystemConfig.orderStr);
		String title = "本日与ma5相比大于2%的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Scheduled(cron = "0 30 7,17 * * ?")
	public void task10(){
		List<Stock_ClosePrice_Ma20_Comp> listDay = ma20UpDao.find("from Stock_ClosePrice_Ma20_Comp " +SystemConfig.orderStr);
		String title = "股价上20日均线的（需要稳住20日均线且趋势向上）,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
