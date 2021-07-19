package cn.gwssi.plugin.auth.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_YHJS表对应的实体类
 */
@Entity
@Table(name = "T_PT_YHJS")
public class TPtYhjsBO extends AbsDaoBussinessObject {
	
	public TPtYhjsBO(){}

	private String id;	
	private String userId;	
	private String jsId;	
	
	@Id
	@Column(name = "ID")
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
		markChange("id", id);
	}
	@Column(name = "USER_ID")
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
		markChange("userId", userId);
	}
	@Column(name = "JS_ID")
	public String getJsId(){
		return jsId;
	}
	public void setJsId(String jsId){
		this.jsId = jsId;
		markChange("jsId", jsId);
	}
}