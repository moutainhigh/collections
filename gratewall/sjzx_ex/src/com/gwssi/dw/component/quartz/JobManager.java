package com.gwssi.dw.component.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
public class JobManager implements IJobManager{
	private Scheduler scheduler;
	public JobManager(){}
	
	public JobManager(Scheduler scheduler)
	{
		this.scheduler=scheduler;
	}
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	private void ensureInit()throws SchedulingException
	{
		if(this.scheduler==null)
		{
			throw new SchedulingException("scheduler is null");
		}
	}
	public void rescheduleJob(JobContainer job)throws SchedulingException {
		ensureInit();
		try
		{
			this.scheduler.rescheduleJob(job.getTrigger().getName(),job.getTrigger().getGroup(),job.getTrigger());
		}
		catch(SchedulerException e)
		{
			e.printStackTrace();
			throw new SchedulingException("reschedule job fail.",e);
		}
		
	}
	
	public boolean unscheduleJob(String triggerName, String triggerGroupName)throws SchedulingException {
		ensureInit();
		try
		{
			return this.scheduler.unscheduleJob(triggerName, triggerGroupName);
		}
		catch(SchedulerException e)
		{
			e.printStackTrace();
			throw new SchedulingException("unschedule job fail.",e);
		}
		
	}
	
	public boolean deleteJob(String jobName, String jobGroupName)throws SchedulingException {
		ensureInit();
		try
		{
			return this.scheduler.deleteJob(jobName, jobGroupName);
		}
		catch(SchedulerException e)
		{
			e.printStackTrace();
			throw new SchedulingException("delete job fail.",e);
		}
		
	}

	public void pauseAll() throws SchedulingException{
		ensureInit();
		try
		{
			this.scheduler.pauseAll();
		}
		catch(SchedulerException e)
		{
			throw new SchedulingException("pause all jobs fail.",e);
		}
	}

	public void pauseJob(String jobName, String jobGroupName) throws SchedulingException{
		ensureInit();
		try
		{
			this.scheduler.pauseJob(jobName, jobGroupName);
		}
		catch(SchedulerException e)
		{
			throw new SchedulingException("pause job "+jobName+" fail.",e);
		}
	}

	public void pauseJobGroup(String groupName) throws SchedulingException{
		ensureInit();
		try
		{
			this.scheduler.pauseJobGroup(groupName);
		}
		catch(SchedulerException e)
		{
			throw new SchedulingException("pause job group "+groupName+" fail.",e);
		}
	}

	public void resumeAll() throws SchedulingException{
		ensureInit();
		try
		{
			this.scheduler.resumeAll();
		}
		catch(SchedulerException e)
		{
			throw new SchedulingException("resume all jobs fail.",e);
		}
	}

	public void resumeJob(String jobName, String jobGroupName) throws SchedulingException{
		ensureInit();
		try
		{
			this.scheduler.resumeJob(jobName, jobGroupName);
		}
		catch(SchedulerException e)
		{
			throw new SchedulingException("resume job "+jobName+" fail.",e);
		}
	}

	public void resumeJobGroup(String groupName) throws SchedulingException{
		ensureInit();
		try
		{
			this.scheduler.resumeJobGroup(groupName);
		}
		catch(SchedulerException e)
		{
			throw new SchedulingException("resume job group "+groupName+" fail.",e);
		}

	}
	public void scheduleJob(JobContainer job) throws SchedulingException{
		ensureInit();
		if(job==null)
		{
			throw new SchedulingException("schedule job "+job+" fail.");
		}
		try
		{
			
			this.scheduler.scheduleJob(job.getJobDetail(), job.getTrigger());
		}
		catch(SchedulerException e)
		{
			e.printStackTrace();
			throw new SchedulingException("schedule job "+job+" fail.",e);
		}
	}
	
	public void triggerJob(String jobName,String jobGroupName)throws SchedulingException
	{
		ensureInit();
		try
		{
			
			this.scheduler.triggerJob(jobName, jobGroupName);
		}
		catch(SchedulerException e)
		{
			throw new SchedulingException("trigger job "+jobName+" fail.",e);
		}
	}
	
}
