package com.ye.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bo.domain.Stock;
import com.ye.monitor.from.PingAnWeb;

//http://blog.csdn.net/SHE_WithWings/article/details/52544118
//https://segmentfault.com/q/1010000006014259
public class TestPingAn {

	
	public static void main(String[] args) {
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");

		PingAnWeb p = ac.getBean(PingAnWeb.class);
		
		p.getDaPanZhiShu();
		
		/*Stock s = new Stock();
		s.setStockCode("002547");*/
		
		//String str = p.getStockInfo(s);
	//	System.out.println(str);
		
	//	System.out.println(p.getStockInfo(s));
		  // ������������
       /* double str = Math.random();
        String s = String.valueOf(str);
        String t = String.valueOf("0.14183076130039773");
        System.out.println("==" + s.length());
        System.out.println(t.length());*/
		
		
		/* double d = 0.14183076130039773;
         // �½���ʽ���������ø�ʽ
         BigDecimal decimal=new BigDecimal(Math.random());
         // ��������������Ϊ��λС˵��doubleֵ
         double d2=decimal.setScale(21,BigDecimal.ROUND_HALF_UP).doubleValue();
         // �����Ϣȷ��
         System.out.println(d2);
         System.out.println("0.5349256011768853".length());
         System.out.println("0.14183076130039773".length());*/
         
         
         
         
		/* DecimalFormat df = new DecimalFormat( "0.00000000000000000");
         BigDecimal decimal=new BigDecimal(Math.random());
         System.out.println(df.format(decimal));
         System.out.println("0.14183076130039773".length());
         System.out.println("0.00000000000000000".length());
         System.out.println("0.26015285772859786".length());*/
	
	}
}
