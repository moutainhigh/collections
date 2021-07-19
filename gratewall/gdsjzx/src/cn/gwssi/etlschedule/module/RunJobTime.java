package cn.gwssi.etlschedule.module;

public class RunJobTime{
	//…Ë÷√≤Œ ˝
	private String jobName=null;
	private String runJobTime=null;
	private String runJobFlag=null;
	
	public RunJobTime(String jobName,String runJobTime,String runJobFlag){
		this.jobName=jobName;
		this.runJobTime=runJobTime;
		this.runJobFlag=runJobFlag;
	}
	
	public String getJobName(){
		return this.jobName;
	}
	
	public String getRunJobTime(){
		return this.runJobTime;
	}
	
	public String getRunJobFlag(){
		return this.runJobFlag;
	}
}