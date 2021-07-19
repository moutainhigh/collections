package com.gwssi.dw.runmgr.webservices.common;

import com.gwssi.common.util.StringUtil;
import com.gwssi.common.util.UuidGenerator;

public class CollectionLogDetail
{
	private String sys_clt_log_detail_id;			/* 主键 */	
	private String sys_clt_log_id;			/* 主键 */
	private String inf_name;			/* 接口名称 */
	private String inf_desc;			/* 接口描述 */
	private String clt_num;			/* 采集数据量 */
	public CollectionLogDetail(String id,String code, String name, int num)
	{
		sys_clt_log_id = id;
		inf_name = code;
		inf_desc = name;
		clt_num = String.valueOf(num);
	}
	public String getClt_num()
	{
		return clt_num;
	}
	public void setClt_num(String clt_num)
	{
		this.clt_num = clt_num;
	}
	public String getInf_desc()
	{
		return inf_desc;
	}
	public void setInf_desc(String inf_desc)
	{
		this.inf_desc = inf_desc;
	}
	public String getInf_name()
	{
		return inf_name;
	}
	public void setInf_name(String inf_name)
	{
		this.inf_name = inf_name;
	}
	public String getSys_clt_log_detail_id()
	{
		return sys_clt_log_detail_id;
	}
	public void setSys_clt_log_detail_id(String sys_clt_log_detail_id)
	{
		this.sys_clt_log_detail_id = sys_clt_log_detail_id;
	}
	public String getLogSql()
	{
		StringBuffer sql = new StringBuffer(
		"insert into sys_clt_log_detail(SYS_CLT_LOG_DETAIL_ID ,SYS_CLT_LOG_ID ,INF_NAME ,INF_DESC ,CLT_NUM) values(");
        sql
		.append("'")
		.append(UuidGenerator.getUUID())
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(sys_clt_log_id))		
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(inf_name))
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(inf_desc))
		.append("',")
		.append("'")
		.append(StringUtil.nullToEmpty(clt_num))
		.append("')");	
        return sql.toString();
	}
	public String getSys_clt_log_id()
	{
		return sys_clt_log_id;
	}
	public void setSys_clt_log_id(String sys_clt_log_id)
	{
		this.sys_clt_log_id = sys_clt_log_id;
	}
}
