package cn.gwssi.etlschedule.module;


import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * ETL_RWXX表对应的实体类
 */
@Entity
@Table(name = "ETL_RWXX")
public class EtlRwxxBO extends AbsDaoBussinessObject {
	
	public EtlRwxxBO(){}

	private String id;	
	private String jobname;	
	private String state;	
	private String flag;	
	private String area;	
	private String type;	
	private Long runnum;	
	private String tablename;	
	private String datasource;	
	private String sourceflag;	
	private String dbuser;	
	private String dbpassword;	
	private String targetusername;	
	private String targetpassword;	
	private String targetserver;	
	private String targetdb;	
	private String jobtype;	
	private String runtime;
	
	private String lastetlruntime;	
	
	
	private String newFlag;
	private String timestamp;//建模所用，时间节点
	
	@Id
	@Column(name = "id")
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
		markChange("id", id);
	}
	@Column(name = "jobname")
	public String getJobname(){
		return jobname;
	}
	public void setJobname(String jobname){
		this.jobname = jobname;
		markChange("jobname", jobname);
	}
	@Column(name = "state")
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
		markChange("state", state);
	}
	@Column(name = "flag")
	public String getFlag(){
		return flag;
	}
	public void setFlag(String flag){
		this.flag = flag;
		markChange("flag", flag);
	}
	@Column(name = "area")
	public String getArea(){
		return area;
	}
	public void setArea(String area){
		this.area = area;
		markChange("area", area);
	}
	@Column(name = "type")
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
		markChange("type", type);
	}
	@Column(name = "runnum")
	public Long getRunnum(){
		return runnum;
	}
	public void setRunnum(Long runnum){
		this.runnum = runnum;
		markChange("runnum", runnum);
	}
	@Column(name = "tablename")
	public String getTablename(){
		return tablename;
	}
	public void setTablename(String tablename){
		this.tablename = tablename;
		markChange("tablename", tablename);
	}
	@Column(name = "datasource")
	public String getDatasource(){
		return datasource;
	}
	public void setDatasource(String datasource){
		this.datasource = datasource;
		markChange("datasource", datasource);
	}
	@Column(name = "sourceFlag")
	public String getSourceflag(){
		return sourceflag;
	}
	public void setSourceflag(String sourceflag){
		this.sourceflag = sourceflag;
		markChange("sourceflag", sourceflag);
	}
	@Column(name = "dbUser")
	public String getDbuser(){
		return dbuser;
	}
	public void setDbuser(String dbuser){
		this.dbuser = dbuser;
		markChange("dbuser", dbuser);
	}
	@Column(name = "dbPassword")
	public String getDbpassword(){
		return dbpassword;
	}
	public void setDbpassword(String dbpassword){
		this.dbpassword = dbpassword;
		markChange("dbpassword", dbpassword);
	}
	@Column(name = "targetusername")
	public String getTargetusername(){
		return targetusername;
	}
	public void setTargetusername(String targetusername){
		this.targetusername = targetusername;
		markChange("targetusername", targetusername);
	}
	@Column(name = "targetpassword")
	public String getTargetpassword(){
		return targetpassword;
	}
	public void setTargetpassword(String targetpassword){
		this.targetpassword = targetpassword;
		markChange("targetpassword", targetpassword);
	}
	@Column(name = "targetserver")
	public String getTargetserver(){
		return targetserver;
	}
	public void setTargetserver(String targetserver){
		this.targetserver = targetserver;
		markChange("targetserver", targetserver);
	}
	@Column(name = "targetdb")
	public String getTargetdb(){
		return targetdb;
	}
	public void setTargetdb(String targetdb){
		this.targetdb = targetdb;
		markChange("targetdb", targetdb);
	}
	
	
	@Column(name = "lastetlruntime")
	public String getLastetlruntime(){
		return lastetlruntime;
	}
	public void setLastetlruntime(String lastetlruntime){
		this.lastetlruntime = lastetlruntime;
		markChange("lastetlruntime", lastetlruntime);
	}
	
	
	@Column(name = "jobtype")
	public String getJobtype() {
		return jobtype;
	}
	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
		markChange("jobtype", jobtype);

	}
	@Column(name = "runtime")
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
		markChange("runtime", runtime);
	}
	
	
	
	public String toString() {
		return "EtlRwxxBO [id=" + id + ", jobname=" + jobname + ", state="
				+ state + ", flag=" + flag + ", area=" + area + ", type="
				+ type + ", runnum=" + runnum + ", tablename=" + tablename
				+ ", datasource=" + datasource + ", sourceflag=" + sourceflag
				+ ", dbuser=" + dbuser + ", dbpassword=" + dbpassword
				+ ", targetusername=" + targetusername + ", targetpassword="
				+ targetpassword + ", targetserver=" + targetserver
				+ ", targetdb=" + targetdb + ", jobtype=" + jobtype
				+ ", runtime=" + runtime + ", lastetlruntime=" + lastetlruntime
				+ "]";
	}
	public String getNewFlag() {
		return newFlag;
	}
	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	

	
	
	
	
}