package com.gwssi.dw.component.quartz;
import org.quartz.Job;
public interface IJobManager {
	void scheduleJob(JobContainer job)throws SchedulingException;
	void pauseJob(String jobName,String jobGroupName)throws SchedulingException;
	void resumeJob(String jobName,String jobGroupName)throws SchedulingException;
	boolean deleteJob(String jobName,String jobGroupName)throws SchedulingException;
	void pauseJobGroup(String groupName)throws SchedulingException;
	void resumeJobGroup(String groupName)throws SchedulingException;
	void pauseAll()throws SchedulingException;
	void resumeAll()throws SchedulingException;
	void triggerJob(String jobName,String jobGroupName)throws SchedulingException;
}
