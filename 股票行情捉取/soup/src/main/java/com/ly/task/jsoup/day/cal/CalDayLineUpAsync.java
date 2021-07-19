package com.ly.task.jsoup.day.cal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.ly.task.jsoup.day.cal.computing.CalStock_ClosePrice_Ma20_Up;
import com.ly.task.jsoup.day.cal.computing.CalStock_ClosePrice_Ma5_Up;
import com.ly.task.jsoup.day.cal.computing.CalStock_Close_Price_Big_Ma30_Up;
import com.ly.task.jsoup.day.cal.computing.CalStock_Da_Yang_Computing;
import com.ly.task.jsoup.day.cal.computing.CalStock_Shou_Yang_Day_One_DayComputing;
import com.ly.task.jsoup.day.cal.computing.CalStock_Shou_Yang_Day_Two_DayComputing;
import com.ly.task.jsoup.day.cal.computing.CalcStockMaInfo;
import com.ly.task.jsoup.day.cal.computing.Guan_Zhu_Stock_One_Day_UpCalc;

@EnableAsync
@Component
public class CalDayLineUpAsync {
	
	/*@Autowired
	private CalcStockMaInfo calcStockMaInfo;*/
	
	@Autowired
	private CalStock_Close_Price_Big_Ma30_Up maInfo;
	
	@Autowired
	private CalStock_Shou_Yang_Day_One_DayComputing oneday;
	
	@Autowired
	private CalStock_Shou_Yang_Day_Two_DayComputing twoday;
	
	@Autowired
	private CalStock_Da_Yang_Computing dayang;
	
	@Autowired
	private  Guan_Zhu_Stock_One_Day_UpCalc guanZhu;
	
	@Autowired
	private  CalStock_ClosePrice_Ma5_Up closePriceMa5;
	
	@Autowired
	private  CalStock_ClosePrice_Ma20_Up closePriceMa20;
	
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public void taskMaInfoCal() throws Exception {
		maInfo.getStockInfo();
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public void taskOneDayCal() throws Exception {
		oneday.getStockInfo();
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public void taskTwoDayCalc() throws Exception {
		twoday.getStockInfo();
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public void taskDaYang() throws Exception {
		dayang.getStockInfo();
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public void taskGuanZhu() throws Exception {
		guanZhu.getStockInfo();
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public void taskClosePriceMa5() throws Exception {
		closePriceMa5.getStockInfo();
	}
	
	@Async // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
	public void taskClosePriceMa20() throws Exception {
		closePriceMa20.getStockInfo();
	}
	
}
