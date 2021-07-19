package com.ye.test;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bo.domain.SMSStock;
import com.ye.bus.SMSStockBus;

public class TestSMS {

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");

		SMSStockBus s = ac.getBean(SMSStockBus.class);
		List<SMSStock> list = s.selectAllSMSStockList();
		for (SMSStock se : list) {
			System.out.println(se.getAddDate());
		}
	}
}
