package com.gwssi.log4j;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.LoggingEvent;
/**
 * 解决log4j的单引号问题
 * @author chaihw
 */
public class Log4JdbcAppender extends JDBCAppender {
	/**
	 * 实现只报该级别文件的日志
	 * 例如  log4j.appender.debug.Threshold = DEBUG    只报为debug的日志不报 info 和error的
	 */
	 @Override  
	 public boolean isAsSevereAsThreshold(Priority priority) {  
	  //只判断是否相等，而不判断优先级  
	  return this.getThreshold().equals(priority);  
	 }  

    @Override
    protected String getLogStatement(LoggingEvent event) {
        String fqnOfCategoryClass=event.fqnOfCategoryClass;
        Category logger=Category.getRoot();
        Priority level=event.getLevel();
        Object message=event.getMessage();
        Throwable throwable=null;
        MyLoggingEvent bEvent=new MyLoggingEvent(fqnOfCategoryClass,logger,level,message,throwable);
        return super.getLogStatement(bEvent);
       // return null;
    }
}