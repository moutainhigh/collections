package com.gwssi.common.task.vo;

import java.util.Calendar;

public class VoJhrw {
	/* 计划任务id */
	private String jhrwid;
	
	/* 计划任务名称 */
	private String jhrwmc;
	
	/* job类名 */
	private String job_class_name;
	
	
	
	/* 计划任务类型 */
	private String jhrwlx;
	
	/* 计划执行日期 */
	private String jhrwrq;
	
	/* 计划执行时间 */
	private String jhrwsj;
	
	/* 计划执行周期 */
    private Long jhrwZq;

    /* 计划执行周天 */
    private String jhrwZt;
    
	/* 任务结束时间 */
    private String jhrwjsSj;
    
    /* 计划任务执行次数 */
    private String jhrwzxCs;

    /* 有效标记。0无效，1有效 */
    private String yxBj;

    /* 创建人ID */
    private String cjrId;

    /* 创建时间 */
    private Calendar cjSj;

    /* 最后修改人ID */
    private String zhxgrId;

    /* 最后修改时间 */
    private Calendar zhxgSj;

	public String getJhrwid()
	{
		return jhrwid;
	}

	public void setJhrwid(String jhrwid)
	{
		this.jhrwid = jhrwid;
	}

	public String getJhrwmc()
	{
		return jhrwmc;
	}

	public void setJhrwmc(String jhrwmc)
	{
		this.jhrwmc = jhrwmc;
	}

	public String getjob_class_name()
	{
		return job_class_name;
	}

	public void setjob_class_name(String job_class_name)
	{
		this.job_class_name = job_class_name;
	}

	public String getJhrwlx()
	{
		return jhrwlx;
	}

	public void setJhrwlx(String jhrwlx)
	{
		this.jhrwlx = jhrwlx;
	}

	public String getJhrwrq()
	{
		return jhrwrq;
	}

	public void setJhrwrq(String jhrwrq)
	{
		this.jhrwrq = jhrwrq;
	}

	public String getJhrwsj()
	{
		return jhrwsj;
	}

	public void setJhrwsj(String jhrwsj)
	{
		this.jhrwsj = jhrwsj;
	}

	public Long getJhrwZq()
	{
		return jhrwZq;
	}

	public void setJhrwZq(Long jhrwZq)
	{
		this.jhrwZq = jhrwZq;
	}

	public String getJhrwZt()
	{
		return jhrwZt;
	}

	public void setJhrwZt(String jhrwZt)
	{
		this.jhrwZt = jhrwZt;
	}

	public String getJhrwjsSj()
	{
		return jhrwjsSj;
	}

	public void setJhrwjsSj(String jhrwjsSj)
	{
		this.jhrwjsSj = jhrwjsSj;
	}

	public String getYxBj()
	{
		return yxBj;
	}

	public void setYxBj(String yxBj)
	{
		this.yxBj = yxBj;
	}

	public String getCjrId()
	{
		return cjrId;
	}

	public void setCjrId(String cjrId)
	{
		this.cjrId = cjrId;
	}

	public Calendar getCjSj()
	{
		return cjSj;
	}

	public void setCjSj(Calendar cjSj)
	{
		this.cjSj = cjSj;
	}

	public String getZhxgrId()
	{
		return zhxgrId;
	}

	public void setZhxgrId(String zhxgrId)
	{
		this.zhxgrId = zhxgrId;
	}

	public Calendar getZhxgSj()
	{
		return zhxgSj;
	}

	public void setZhxgSj(Calendar zhxgSj)
	{
		this.zhxgSj = zhxgSj;
	}

	

	public String getJhrwzxCs()
	{
		return jhrwzxCs;
	}

	public void setJhrwzxCs(String jhrwzxCs)
	{
		this.jhrwzxCs = jhrwzxCs;
	}
}
