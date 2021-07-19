package cn.gwssi.etlschedule.module;


import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * ETL_LOG表对应的实体类
 */
@Entity
@Table(name = "ETL_LOG")
public class EtlLogBO extends AbsDaoBussinessObject {
	
	public EtlLogBO(){}

	private String id;	
	private String jobname;	
	private String state;	
	private String excutetime;	
	private String starttime;	
	private String endtime;	
	private String jobtype;	
	private String area;
	private String flag;
	
	@Id
	@Column(name = "ID")
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
		markChange("id", id);
	}
	@Column(name = "JOBNAME")
	public String getJobname(){
		return jobname;
	}
	public void setJobname(String jobname){
		this.jobname = jobname;
		markChange("jobname", jobname);
	}
	@Column(name = "STATE")
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
		markChange("state", state);
	}
	@Column(name = "EXCUTETIME")
	public String getExcutetime(){
		return excutetime;
	}
	public void setExcutetime(String excutetime){
		this.excutetime = excutetime;
		markChange("excutetime", excutetime);
	}
	@Column(name = "STARTTIME")
	public String getStarttime(){
		return starttime;
	}
	public void setStarttime(String starttime){
		this.starttime = starttime;
		markChange("starttime", starttime);
	}
	@Column(name = "ENDTIME")
	public String getEndtime(){
		return endtime;
	}
	public void setEndtime(String endtime){
		this.endtime = endtime;
		markChange("endtime", endtime);
	}
	@Column(name = "JOBTYPE")
	public String getJobtype(){
		return jobtype;
	}
	public void setJobtype(String jobtype){
		this.jobtype = jobtype;
		markChange("jobtype", jobtype);
	}
	
	@Column(name = "AREA")
	public String getArea(){
		return area;
	}
	public void setArea(String area){
		this.area = area;
		markChange("area", area);
	}
	
	
	@Column(name = "FLAG")
	public String getFlag(){
		return flag;
	}
	public void setFlag(String flag){
		this.flag = flag;
		markChange("flag", flag);
	}

}