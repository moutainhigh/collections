package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import com.gwssi.optimus.core.persistence.annotation.Lob;
import java.math.BigDecimal;

/**
 * SM_TODO表对应的实体类
 */
@Entity
@Table(name = "SM_TODO")
public class SmTodoBO extends AbsDaoBussinessObject {
	
	public SmTodoBO(){}

	private String pkTodo;	
	private String systemCode;	
	private String todoUser;	
	private BigDecimal todoNum;	
	private String todoUrl;	
	
	@Id
	@Column(name = "PK_TODO")
	public String getPkTodo(){
		return pkTodo;
	}
	public void setPkTodo(String pkTodo){
		this.pkTodo = pkTodo;
		markChange("pkTodo", pkTodo);
	}
	@Column(name = "SYSTEM_CODE")
	public String getSystemCode(){
		return systemCode;
	}
	public void setSystemCode(String systemCode){
		this.systemCode = systemCode;
		markChange("systemCode", systemCode);
	}
	@Column(name = "TODO_USER")
	public String getTodoUser(){
		return todoUser;
	}
	public void setTodoUser(String todoUser){
		this.todoUser = todoUser;
		markChange("todoUser", todoUser);
	}
	@Column(name = "TODO_NUM")
	public BigDecimal getTodoNum(){
		return todoNum;
	}
	public void setTodoNum(BigDecimal todoNum){
		this.todoNum = todoNum;
		markChange("todoNum", todoNum);
	}
	@Column(name = "TODO_URL")
	public String getTodoUrl(){
		return todoUrl;
	}
	public void setTodoUrl(String todoUrl){
		this.todoUrl = todoUrl;
		markChange("todoUrl", todoUrl);
	}
}