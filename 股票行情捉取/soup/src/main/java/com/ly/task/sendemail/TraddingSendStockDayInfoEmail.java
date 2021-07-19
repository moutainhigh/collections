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
import com.ly.email.TraddingEmailTemplate;
import com.ly.pojo.Important_Stock;
import com.ly.pojo.Important_Stock_Up;
import com.ly.pojo.Stock_ClosePrice_Ma20_Comp;
import com.ly.pojo.Stock_ClosePrice_Ma5_Comp;
import com.ly.pojo.Stock_Close_Price_Big_Ma30;
import com.ly.pojo.Stock_Da_Yang_Inf;
import com.ly.pojo.Stock_Shou_Yang_One_Day;
import com.ly.pojo.Stock_Shou_Yang_Two_Day;

@Component
public class TraddingSendStockDayInfoEmail {
	
	@Autowired
	private Stock_K_line_Day_Data_List_Dao datasDao;
	@Autowired
	private TraddingEmailTemplate template;
	@Autowired
	private Stock_Shou_Yang_One_DayDao oneDayDao;
	
	@Autowired
	private Stock_Shou_Yang_Two_DayDao twoDayDao;
	
	@Autowired
	private Stock_Close_Price_Big_Ma30Dao maDao;
	
	@Autowired
	private Stock_Da_Yang_Dao daYangDao;
	
	@Autowired
	private Stock_ClosePrice_Ma5_Comp_Dao stockClosePriceMaComp;
	
	
	@Autowired
	private Stock_ClosePrice_Ma20_Comp_Dao stockClosePriceMa20Comp;
	
	
	@Autowired
	private Important_Stock_Up_Dao impStockUpDao;
	
	public String getTime() {
		List times = datasDao.find("select max(date) from  Stock_K_line_Day_DataList");
		String time = (String) times.get(0);
		return time;// new Date()为获取当前系统时间
	}

	public String getTitle(String title) {
		title = title + ",数据生成时间【" + getTime() + "】";
		return title;
	}
	
	//@Scheduled(cron="*/10 * * * * ?")
	public void taskMaEail(){
		List<Stock_Close_Price_Big_Ma30> listDay = maDao.find("from Stock_Close_Price_Big_MaInfo "+SystemConfig.orderStr);
		String title = "现价大于ma30的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//@Scheduled(cron="*/10 * * * * ?")
	public void taskOneDayEail(){
		List<Stock_Shou_Yang_One_Day> listDay = oneDayDao.find("from Stock_Shou_Yang_One_Day "+SystemConfig.orderStr);
		String title = "当天涨幅度大于1%的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//@Scheduled(cron="*/10 * * * * ?")
	public void taskTwoDayEail(){
		List<Stock_Shou_Yang_Two_Day> listDay = twoDayDao.find("from Stock_Shou_Yang_Two_Day "+SystemConfig.orderStr);
		String title = "两天涨幅度大于1%的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stockDaYangEmail(){
		List<Stock_Da_Yang_Inf> listDay = daYangDao.find("from Stock_Da_Yang_Inf "+SystemConfig.orderStr);
		String title = "本日大阳线的（涨幅超过5%的）,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Scheduled(cron="*/10 * * * * ?")
	public void guanZhuEmail(){

		

		List<Important_Stock_Up> listDay = impStockUpDao.find("from Important_Stock_Up " +SystemConfig.orderStr);
		String title = "关注的股票的走势,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	public void stockClosePirceMa5Comp(){
		List<Stock_ClosePrice_Ma5_Comp> listDay = stockClosePriceMaComp.find("from Stock_ClosePrice_Ma5_Comp "+SystemConfig.orderStr);
		String title = "现价ma5超过2%的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stockClosePirceMa20Comp(){
		List<Stock_ClosePrice_Ma20_Comp> listDay = stockClosePriceMa20Comp.find("from Stock_ClosePrice_Ma20_Comp "+SystemConfig.orderStr);
		String title = "现价在ma20附近的,共"+listDay.size()+"条";
		title  = getTitle(title);
		try {
			template.send(title, listDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
