package test;

import java.text.ParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.report.service.Data01Service;
import com.report.service.Query12315Service;

public class Test {

	public static void main(String[] args) throws ParseException {
		
		
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
		
		
		Query12315Service a = ac.getBean(Query12315Service.class);
		
		a.getRecode(null,null);

	}

}
