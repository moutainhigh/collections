package cn.gwssi.quartz.inter;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import com.gwssi.optimus.core.exception.OptimusException;

/**job管理类
 * cn.gwssi.quartz
 * JobManager.java
 * 上午10:24:33
 * @author wuminghua
 */


public interface JobManagerInterface extends Job {
	
	
	/* (non-Javadoc)具体执行job接口类
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0);
			
		
		

}
