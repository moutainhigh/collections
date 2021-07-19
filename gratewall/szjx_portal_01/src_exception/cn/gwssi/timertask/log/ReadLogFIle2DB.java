package cn.gwssi.timertask.log;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * com.gwssi.application.timertask.log
 * ReadLogFIle2DB.java
 * 下午5:54:42
 * @author wuminghua
 */
public class ReadLogFIle2DB implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//读取文件方法
		System.out.println("读取文件====================");
		
	}

	
	
	
}
