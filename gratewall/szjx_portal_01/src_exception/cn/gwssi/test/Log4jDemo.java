package cn.gwssi.test;



import java.util.Calendar;

import org.apache.log4j.Logger;

import cn.gwssi.mian.model.SmExceptionLogBO;
import cn.gwssi.mian.model.SmInterfaceLogBO;
import cn.gwssi.mian.model.SmOperationLogBO;

public class Log4jDemo {
	private static Logger logger = Logger.getLogger(Log4jDemo.class);   
public static void main(String[] args) {
	
		Calendar calendar=Calendar.getInstance();  
	
		SmOperationLogBO bo=new SmOperationLogBO();
		bo.setPkOperationLog("111");
		bo.setOperationTime(calendar);
		bo.setOperationState("1");
		logger.info(bo);
//
		
		SmInterfaceLogBO bo1=new SmInterfaceLogBO();
		bo1.setPkInterfaceLog("111");
		bo1.setOperationTime(calendar);
		bo1.setOperationState("1");

		logger.info(bo1);
		
		
		SmExceptionLogBO bo2=new SmExceptionLogBO();
		bo2.setPkExceptionLog("121");
		bo2.setOperationTime(calendar);
		bo2.setOperationState("1");
		logger.info(bo2);
		
		
//		Map<String, Object> map1=new HashMap<String, Object>();
//		map1.put(JDBCExtApender.TYPE,JDBCExtApender.OPERATER_LOG );
//		SmOperationLogBO bo1=new SmOperationLogBO();
//		bo1.setPkOperationLog("222");
//		bo1.setOperationTime(Calendar.getInstance());
//		map1.put(JDBCExtApender.OBJECT, bo1);
//		logger.info(map1);
//		
//		Map<String, Object> map2=new HashMap<String, Object>();
//		map2.put(JDBCExtApender.TYPE,JDBCExtApender.OPERATER_LOG );
//		SmOperationLogBO bo2=new SmOperationLogBO();
//		bo2.setPkOperationLog("333");
//		bo2.setOperationTime(Calendar.getInstance());
//		map2.put(JDBCExtApender.OBJECT, bo2);
//		logger.info(map2);
//		
//		Map<String, Object> map3=new HashMap<String, Object>();
//		map3.put(JDBCExtApender.TYPE,JDBCExtApender.OPERATER_LOG );
//		SmOperationLogBO bo3=new SmOperationLogBO();
//		bo3.setPkOperationLog("444");
//		bo3.setOperationTime(Calendar.getInstance());
//		map3.put(JDBCExtApender.OBJECT, bo3);
//		logger.info(map3);
//		
//		Map<String, Object> map4=new HashMap<String, Object>();
//		map4.put(JDBCExtApender.TYPE,JDBCExtApender.OPERATER_LOG );
//		SmOperationLogBO bo4=new SmOperationLogBO();
//		bo4.setPkOperationLog("555");
//		bo4.setOperationTime(Calendar.getInstance());
//		map4.put(JDBCExtApender.OBJECT, bo4);
//		logger.info(map4);
//		
//		Map<String, Object> map5=new HashMap<String, Object>();
//		map5.put(JDBCExtApender.TYPE,JDBCExtApender.OPERATER_LOG );
//		SmOperationLogBO bo5=new SmOperationLogBO();
//		bo5.setPkOperationLog("666");
//		bo5.setOperationTime(Calendar.getInstance());
//		map5.put(JDBCExtApender.OBJECT, bo5);
//		logger.info(map5);
//		
//		Map<String, Object> map6=new HashMap<String, Object>();
//		map6.put(JDBCExtApender.TYPE,JDBCExtApender.OPERATER_LOG );
//		SmOperationLogBO bo6=new SmOperationLogBO();
//		bo6.setPkOperationLog("777");
//		bo6.setOperationTime(Calendar.getInstance());
//		map6.put(JDBCExtApender.OBJECT, bo6);
//		logger.info(map6);
//		
//		
//		Map<String, Object> map7=new HashMap<String, Object>();
//		map7.put(JDBCExtApender.TYPE,JDBCExtApender.OPERATER_LOG );
//		SmOperationLogBO bo7=new SmOperationLogBO();
//		bo7.setPkOperationLog("888");
//		bo7.setOperationTime(Calendar.getInstance());
//		map7.put(JDBCExtApender.OBJECT, bo7);
//		logger.info(map7);
//		
//		Map<String, Object> map8=new HashMap<String, Object>();
//		map8.put(JDBCExtApender.TYPE,JDBCExtApender.OPERATER_LOG );
//		SmOperationLogBO bo8=new SmOperationLogBO();
//		bo8.setPkOperationLog("999");
//		bo8.setOperationTime(Calendar.getInstance());
//		map8.put(JDBCExtApender.OBJECT, bo8);
//		logger.info(map8);
//		
//		Map<String, Object> map9=new HashMap<String, Object>();
//		map9.put(JDBCExtApender.TYPE,JDBCExtApender.OPERATER_LOG );
//		SmOperationLogBO bo9=new SmOperationLogBO();
//		bo9.setPkOperationLog("10011");
//		bo.setOperationTime(Calendar.getInstance());
//		map9.put(JDBCExtApender.OBJECT, bo9);
//		logger.info(map9);
//	    logger.error("This is error message.");   
}
}
