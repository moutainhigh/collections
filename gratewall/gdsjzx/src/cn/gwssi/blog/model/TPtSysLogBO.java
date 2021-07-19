package cn.gwssi.blog.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * T_PT_SYS_LOG表对应的实体类
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_PT_SYS_LOG")
public class TPtSysLogBO extends AbsDaoBussinessObject implements Serializable {
	
	public TPtSysLogBO(){}

	private String logid;	
	private String logtype;	
	private String operatetime;	
	private String ip;	
	private String sourceplatform;	
	private String userid;	
	private String username;	
	private String orgcode;	
	private String orgname;	
	private String starttime;	
	private String endtime;	
	private String content;	
	private String function;	
	private String url;	
	private String errcode;	
	private String errdesc;	
	private String result;	
	private String req;	
	private String res;	
	private String servicename;	
	private String servicecontent;	
	private String serviceobject;	
	private String servicetype;	
	private String servicestate;	
	private BigDecimal times;	
	private String isfalg;	
	private String runstate;	
	private BigDecimal countsum;	
	
	@Id
	@Column(name = "logid")
	public String getLogid(){
		return logid;
	}
	public void setLogid(String logid){
		this.logid = logid;
		markChange("logid", logid);
	}
	@Column(name = "logtype")
	public String getLogtype(){
		return logtype;
	}
	public void setLogtype(String logtype){
		this.logtype = logtype;
		markChange("logtype", logtype);
	}
	@Column(name = "operatetime")
	public String getOperatetime(){
		return operatetime;
	}
	public void setOperatetime(String operatetime){
		this.operatetime = operatetime;
		markChange("operatetime", operatetime);
	}
	@Column(name = "ip")
	public String getIp(){
		return ip;
	}
	public void setIp(String ip){
		this.ip = ip;
		markChange("ip", ip);
	}
	@Column(name = "sourceplatform")
	public String getSourceplatform(){
		return sourceplatform;
	}
	public void setSourceplatform(String sourceplatform){
		this.sourceplatform = sourceplatform;
		markChange("sourceplatform", sourceplatform);
	}
	@Column(name = "userid")
	public String getUserid(){
		return userid;
	}
	public void setUserid(String userid){
		this.userid = userid;
		markChange("userid", userid);
	}
	@Column(name = "username")
	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username = username;
		markChange("username", username);
	}
	@Column(name = "orgcode")
	public String getOrgcode(){
		return orgcode;
	}
	public void setOrgcode(String orgcode){
		this.orgcode = orgcode;
		markChange("orgcode", orgcode);
	}
	@Column(name = "orgname")
	public String getOrgname(){
		return orgname;
	}
	public void setOrgname(String orgname){
		this.orgname = orgname;
		markChange("orgname", orgname);
	}
	@Column(name = "starttime")
	public String getStarttime(){
		return starttime;
	}
	public void setStarttime(String starttime){
		this.starttime = starttime;
		markChange("starttime", starttime);
	}
	@Column(name = "endtime")
	public String getEndtime(){
		return endtime;
	}
	public void setEndtime(String endtime){
		this.endtime = endtime;
		markChange("endtime", endtime);
	}
	@Column(name = "content")
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
		markChange("content", content);
	}
	@Column(name = "function")
	public String getFunction(){
		return function;
	}
	public void setFunction(String function){
		this.function = function;
		markChange("function", function);
	}
	@Column(name = "url")
	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url = url;
		markChange("url", url);
	}
	@Column(name = "errcode")
	public String getErrcode(){
		return errcode;
	}
	public void setErrcode(String errcode){
		this.errcode = errcode;
		markChange("errcode", errcode);
	}
	@Column(name = "errdesc")
	public String getErrdesc(){
		return errdesc;
	}
	public void setErrdesc(String errdesc){
		this.errdesc = errdesc;
		markChange("errdesc", errdesc);
	}
	@Column(name = "result")
	public String getResult(){
		return result;
	}
	public void setResult(String result){
		this.result = result;
		markChange("result", result);
	}
	@Column(name = "req")
	public String getReq(){
		return req;
	}
	public void setReq(String req){
		this.req = req;
		markChange("req", req);
	}
	@Column(name = "res")
	public String getRes(){
		return res;
	}
	public void setRes(String res){
		this.res = res;
		markChange("res", res);
	}
	@Column(name = "servicename")
	public String getServicename(){
		return servicename;
	}
	public void setServicename(String servicename){
		this.servicename = servicename;
		markChange("servicename", servicename);
	}
	@Column(name = "servicecontent")
	public String getServicecontent(){
		return servicecontent;
	}
	public void setServicecontent(String servicecontent){
		this.servicecontent = servicecontent;
		markChange("servicecontent", servicecontent);
	}
	@Column(name = "serviceobject")
	public String getServiceobject(){
		return serviceobject;
	}
	public void setServiceobject(String serviceobject){
		this.serviceobject = serviceobject;
		markChange("serviceobject", serviceobject);
	}
	@Column(name = "servicetype")
	public String getServicetype(){
		return servicetype;
	}
	public void setServicetype(String servicetype){
		this.servicetype = servicetype;
		markChange("servicetype", servicetype);
	}
	@Column(name = "servicestate")
	public String getServicestate(){
		return servicestate;
	}
	public void setServicestate(String servicestate){
		this.servicestate = servicestate;
		markChange("servicestate", servicestate);
	}
	@Column(name = "times")
	public BigDecimal getTimes(){
		return times;
	}
	public void setTimes(BigDecimal times){
		this.times = times;
		markChange("times", times);
	}
	@Column(name = "isfalg")
	public String getIsfalg(){
		return isfalg;
	}
	public void setIsfalg(String isfalg){
		this.isfalg = isfalg;
		markChange("isfalg", isfalg);
	}
	@Column(name = "runstate")
	public String getRunstate(){
		return runstate;
	}
	public void setRunstate(String runstate){
		this.runstate = runstate;
		markChange("runstate", runstate);
	}
	@Column(name = "countsum")
	public BigDecimal getCountsum(){
		return countsum;
	}
	public void setCountsum(BigDecimal countsum){
		this.countsum = countsum;
		markChange("countsum", countsum);
	}
}