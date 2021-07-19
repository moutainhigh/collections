package cn.gwssi.etlschedule.module;

public class Job{
	/**���ò���**/
	private String jobId=null;
	private String jobName=null;
	private long jobInterval=30;
	private String jobStTm=null;
	private String cubeName=null;
	private String tableName=null;
	private String areaIdx=null;
	
	//��ҵ״̬��0������ִ�У�1��δ��ִ�У�2�����гɹ���3������ʧ�ܣ�
	private int jobStatus=1;
	private int jobRunNum=1;
	
	//��ҵ�Ƿ�ɴ�����0���ɴ�����1�����ɴ�����
	private String isRunAble="0";
	
	/**���췽��**/
	public Job(String jobId,String jobName,long jobInterval,int jobRunNum,String jobStTm,String cubeName,String tableName,String areaIdx){
		this.jobId=jobId;
		this.jobName=jobName;
		this.jobInterval=jobInterval;
		this.jobRunNum=jobRunNum;
		this.jobStTm=jobStTm;
		this.cubeName=cubeName;
		this.tableName=tableName;
		this.areaIdx=areaIdx;
	}
	
	public String getJobId(){
		return this.jobId;
	}
	
	public String getJobName(){
		return this.jobName;
	}
	
	public long getJobInterval(){
		return this.jobInterval;
	}
	
	public int getJobStatus(){
		return this.jobStatus;
	}
	
	public int getJobRunNum(){
		return this.jobRunNum;
	}
	
	public String getJobStTm(){
		return this.jobStTm;
	}
	
	public String getCubeName(){
		return this.cubeName;
	}
	
	public String getTableName(){
		return this.tableName;
	}
	
	public String getAreaIdx(){
		return this.areaIdx;
	}
	
	public void setJobStatus(int newJobStatus){
		this.jobStatus=newJobStatus;
	}
	
	public void setJobRunNum(int jobRunNum){
		this.jobRunNum=jobRunNum;
	}
	
	public void setJobInterval(long jobInterval){
		this.jobInterval=jobInterval;
	}
	
	public String getIsRunAble(){
		return this.isRunAble;
	}
	
	public void setIsRunAble(String isRunAble){
		this.isRunAble=isRunAble;
	}
}