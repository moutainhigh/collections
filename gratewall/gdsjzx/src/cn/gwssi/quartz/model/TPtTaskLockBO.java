package cn.gwssi.quartz.model;


import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_TASK_LOCK表对应的实体类
 */
@Entity
@Table(name = "T_PT_TASK_LOCK")
public class TPtTaskLockBO extends AbsDaoBussinessObject {
	
	public TPtTaskLockBO(){}

	private String taskid;	
	private String node;	
	
	@Id
	@Column(name = "TaskId")
	public String getTaskid(){
		return taskid;
	}
	public void setTaskid(String taskid){
		this.taskid = taskid;
		markChange("taskid", taskid);
	}
	@Column(name = "node")
	public String getNode(){
		return node;
	}
	public void setNode(String node){
		this.node = node;
		markChange("node", node);
	}
}