package com.gwssi.common.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

/**
 * ������ȼ���
 * @author elvislee
 *
 */
public class SimpleJobListener implements JobListener
{

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0){ }

	@Override
	public void jobToBeExecuted(JobExecutionContext arg0){ }
	
	@Override
	public String getName() { return "SimpleJobListener"; }
	
	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		if(jobException != null){
			try {
				//ֹͣScheduler
				context.getScheduler().shutdown();
				System.out.println(" Error occurs when executing jobs, shut down the scheduler ");
                // ֪ͨ�����Ա
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
}
