package com.gwssi.dw.runmgr.webservices.common;

import java.util.ArrayList;
import java.util.List;

import com.gwssi.common.util.StringUtil;
import com.gwssi.common.util.UuidGenerator;

public class CollectionLog
{
	private String sys_clt_log_id;			/* 主键 */
	private String sys_clt_user_id;			/* 采集策略外键 */
	private String clt_startdate;			/* 采集数据开始日期 */
	private String clt_enddate;			/* 采集数据结束日期 */
	private String exc_starttime;			/* 采集执行开始时间 */
	private String exc_endtime;			/* 采集执行结束时间 */
	private String state;			/* 采集状态（0表示失败1表示成功） */
	private String logdesc;			/* 日志描述 */
	
	private List logDetail;

	public CollectionLog(){
		sys_clt_log_id = UuidGenerator.getUUID();
		logDetail = new ArrayList();
	}
	
	public void addLogDetail(String code,String name,int num){
		CollectionLogDetail detail = new CollectionLogDetail(sys_clt_log_id,code,name,num);
		logDetail.add(detail);
	}
	
	public String getClt_enddate()
	{
		return clt_enddate;
	}
	public void setClt_enddate(String clt_enddate)
	{
		this.clt_enddate = clt_enddate;
	}
	public String getClt_startdate()
	{
		return clt_startdate;
	}
	public void setClt_startdate(String clt_startdate)
	{
		this.clt_startdate = clt_startdate;
	}
	public String getExc_endtime()
	{
		return exc_endtime;
	}
	public void setExc_endtime(String exc_endtime)
	{
		this.exc_endtime = exc_endtime;
	}
	public String getExc_starttime()
	{
		return exc_starttime;
	}
	public void setExc_starttime(String exc_starttime)
	{
		this.exc_starttime = exc_starttime;
	}
	public String getLogdesc()
	{
		return logdesc;
	}
	public void setLogdesc(String logdesc)
	{
		this.logdesc = logdesc;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getSys_clt_log_id()
	{
		return sys_clt_log_id;
	}
	public void setSys_clt_log_id(String sys_clt_log_id)
	{
		this.sys_clt_log_id = sys_clt_log_id;
	}
	public String getSys_clt_user_id()
	{
		return sys_clt_user_id;
	}
	public void setSys_clt_user_id(String sys_clt_user_id)
	{
		this.sys_clt_user_id = sys_clt_user_id;
	}
	public List getLogDetail()
	{
		return logDetail;
	}
	public void setLogDetail(List logDetail)
	{
		this.logDetail = logDetail;
	}

	public List getLogSql()
	{
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer(
		"insert into sys_clt_log(SYS_CLT_LOG_ID,SYS_CLT_USER_ID,CLT_STARTDATE ,CLT_ENDDATE ,EXC_STARTTIME ,EXC_ENDTIME ,STATE ,LOGDESC) values(");
        sql
		.append("'")
		.append(StringUtil.nullToEmpty(sys_clt_log_id))
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(sys_clt_user_id))		
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(clt_startdate))
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(clt_enddate))
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(exc_starttime))
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(exc_endtime))
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(state))
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(logdesc))
		.append("')");	
        list.add(sql.toString());
        
        if("1".equals(state)){
        	for(int i=0;i<logDetail.size();i++){
        		CollectionLogDetail detail = (CollectionLogDetail)logDetail.get(i);
        		list.add(detail.getLogSql());
        	}
        }
		// TODO Auto-generated method stub
		return list;
	}
}
