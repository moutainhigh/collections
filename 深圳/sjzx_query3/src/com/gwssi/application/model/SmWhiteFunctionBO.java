package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.Lob;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * SM_WHITE_FUNCTION表对应的实体类
 */
@Entity
@Table(name = "SM_WHITE_FUNCTION")
public class SmWhiteFunctionBO extends AbsDaoBussinessObject {
	
	public SmWhiteFunctionBO(){}

	private String pkWhiteFunction;	
	private String functionUrl;	
	private String state;	
	private String createrId;	
	private Calendar createTime;	
	private String functionName;	
	private String lastUpdaterId;	
	private Calendar lastUpdateTime;	
	private String isExactMatch;	
	
	@Id
	@Column(name = "PK_WHITE_FUNCTION")
	public String getPkWhiteFunction(){
		return pkWhiteFunction;
	}
	public void setPkWhiteFunction(String pkWhiteFunction){
		this.pkWhiteFunction = pkWhiteFunction;
		markChange("pkWhiteFunction", pkWhiteFunction);
	}
	@Column(name = "FUNCTION_URL")
	public String getFunctionUrl(){
		return functionUrl;
	}
	public void setFunctionUrl(String functionUrl){
		this.functionUrl = functionUrl;
		markChange("functionUrl", functionUrl);
	}
	@Column(name = "STATE")
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
		markChange("state", state);
	}
	@Column(name = "CREATER_ID")
	public String getCreaterId(){
		return createrId;
	}
	public void setCreaterId(String createrId){
		this.createrId = createrId;
		markChange("createrId", createrId);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Calendar getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Calendar createTime){
		this.createTime = createTime;
		markChange("createTime", createTime);
	}
	@Column(name = "FUNCTION_NAME")
	public String getFunctionName(){
		return functionName;
	}
	public void setFunctionName(String functionName){
		this.functionName = functionName;
		markChange("functionName", functionName);
	}
	@Column(name = "LAST_UPDATER_ID")
	public String getLastUpdaterId(){
		return lastUpdaterId;
	}
	public void setLastUpdaterId(String lastUpdaterId){
		this.lastUpdaterId = lastUpdaterId;
		markChange("lastUpdaterId", lastUpdaterId);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_TIME")
	public Calendar getLastUpdateTime(){
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Calendar lastUpdateTime){
		this.lastUpdateTime = lastUpdateTime;
		markChange("lastUpdateTime", lastUpdateTime);
	}
	@Column(name = "IS_EXACT_MATCH")
	public String getIsExactMatch(){
		return isExactMatch;
	}
	public void setIsExactMatch(String isExactMatch){
		this.isExactMatch = isExactMatch;
		markChange("isExactMatch", isExactMatch);
	}
}