package com.gwssi.application.log.aspect;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 日志记录表(Log_operation)对应Bo实体类
 * @author yangzihao
 */
@Entity
@Table(name = "LOG_OPERATION")
public class LogBo extends AbsDaoBussinessObject {
	
	String sysFlag;//系统代码  综合查询-ZHCX  和  报表-WDY
	String userId;//用户id
	String userIp;//用户Ip
	String operationDescribe;//执行操作描述 比如商事主体查询
	String operationSql;//执行sql
	String operationSqlParatemer;//执行sql传入参数
	
	@Column(name = "SYS_FLAG")
	public String getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
		markChange("sysFlag", sysFlag);
	}
	
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
		markChange("userId", userId);
	}
	
	@Column(name = "USER_IP")
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
		markChange("userIp", userIp);
	}
	
	@Column(name = "OPERATION_DESCRIBE")
	public String getOperationDescribe() {
		return operationDescribe;
	}
	public void setOperationDescribe(String operationDescribe) {
		this.operationDescribe = operationDescribe;
		markChange("operationDescribe", operationDescribe);
	}
	
	@Column(name = "OPERATION_SQL")
	public String getOperationSql() {
		return operationSql;
	}
	public void setOperationSql(String operationSql) {
		this.operationSql = operationSql;
		markChange("operationSql", operationSql);
	}
	
	@Column(name = "OPERATION_SQL_PARATEMER")
	public String getOperationSqlParatemer() {
		return operationSqlParatemer;
	}
	public void setOperationSqlParatemer(String operationSqlParatemer) {
		this.operationSqlParatemer = operationSqlParatemer;
		markChange("operationSqlParatemer", operationSqlParatemer);
	}
}
