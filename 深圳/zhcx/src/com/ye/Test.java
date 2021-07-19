package com.ye;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ye.dc.bus.ZhcxBus;
import com.ye.dc.dao.UserDao;
import com.ye.dc.dao.Zhcx_Asy_LogDao;
import com.ye.dc.pojo.Zhcx_Asy_Log;
import com.ye.from.bus.ZhcxFromBus;
import com.ye.from.dao.ZHCXUserHyDao;
import com.ye.from.pojo.ZHCXUserHy;

public class Test {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");

		
		ZhcxFromBus from = ac.getBean(ZhcxFromBus.class);
		
		 ZhcxBus zhcxBus = ac.getBean(ZhcxBus.class);
		 
		 
		 from.saveToDb();
		 
		 zhcxBus.saveToDbFromLog();
		
	}
}
