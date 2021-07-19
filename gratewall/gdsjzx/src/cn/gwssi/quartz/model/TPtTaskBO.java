package cn.gwssi.quartz.model;


import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_Task表对应的实体类
 */
@Entity
@Table(name = "T_PT_Task")
public class TPtTaskBO extends AbsDaoBussinessObject {
	
	public TPtTaskBO(){}

	private String taskid;	
	private String name;	
	private String createtime;	
	private String classname;	
	private String methodname;	
	private String timeparamer;
	private String paramers;
	private String starttime;	
	private String state;	
	private String nexttime;	
	private String lockcolumn;	
	private String lasttime;	
	private String excutestate;	
	
	@Id
	@Column(name = "taskid")
	public String getTaskid(){
		return taskid;
	}
	public void setTaskid(String taskid){
		this.taskid = taskid;
		markChange("taskid", taskid);
	}
	@Column(name = "name")
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
		markChange("name", name);
	}
	@Column(name = "createTime")
	public String getCreatetime(){
		return createtime;
	}
	public void setCreatetime(String createtime){
		this.createtime = createtime;
		markChange("createtime", createtime);
	}
	@Column(name = "className")
	public String getClassname(){
		return classname;
	}
	public void setClassname(String classname){
		this.classname = classname;
		markChange("classname", classname);
	}
	@Column(name = "methodName")
	public String getMethodname(){
		return methodname;
	}
	public void setMethodname(String methodname){
		this.methodname = methodname;
		markChange("methodname", methodname);
	}
	@Column(name = "timeParamer")
	public String getTimeparamer(){
		return timeparamer;
	}
	public void setTimeparamer(String timeparamer){
		this.timeparamer = timeparamer;
		markChange("timeparamer", timeparamer);
	}
	@Column(name = "starttime")
	public String getStarttime(){
		return starttime;
	}
	public void setStarttime(String starttime){
		this.starttime = starttime;
		markChange("starttime", starttime);
	}
	@Column(name = "state")
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
		markChange("state", state);
	}
	@Column(name = "nexttime")
	public String getNexttime(){
		return nexttime;
	}
	public void setNexttime(String nexttime){
		this.nexttime = nexttime;
		markChange("nexttime", nexttime);
	}
	@Column(name = "lockColumn")
	public String getLockcolumn(){
		return lockcolumn;
	}
	public void setLockcolumn(String lockcolumn){
		this.lockcolumn = lockcolumn;
		markChange("lockcolumn", lockcolumn);
	}
	@Column(name = "lasttime")
	public String getLasttime(){
		return lasttime;
	}
	public void setLasttime(String lasttime){
		this.lasttime = lasttime;
		markChange("lasttime", lasttime);
	}
	@Column(name = "excuteState")
	public String getExcutestate(){
		return excutestate;
	}
	public void setExcutestate(String excutestate){
		this.excutestate = excutestate;
		markChange("excutestate", excutestate);
	}




	@Column(name = "paramers")
	public String getParamers(){
		return paramers;
	}
	public void setParamers(String paramers){
		this.paramers = paramers;
		markChange("paramers", paramers);
	}
}