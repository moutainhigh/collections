/**
 * 
 */
package com.gwssi.dw.component.quartz;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
/**
 * @author Administrator
 *
 */
public class JobContainer {
	private JobDetail jobDetail;
	private Trigger trigger;
	
	public JobContainer(JobDetail jobDetail,Trigger trigger){
		this.jobDetail = jobDetail;
		this.trigger = trigger;
		this.trigger.setJobName(jobDetail.getName());
		this.trigger.setJobGroup(jobDetail.getGroup());
	}
	
	public JobDetail getJobDetail() {
		return jobDetail;
	}
	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}
	public Trigger getTrigger() {
		return trigger;
	}
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	public JobDataMap getDataMap()
	{
		return (this.jobDetail==null)?null:this.jobDetail.getJobDataMap();
	}
	
	
	public String toString()
	{
		return "[job:"+jobDetail+"  trigger:"+trigger+"]";
	}
}
