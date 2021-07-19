package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[opb_subject]的数据对象类
 * @author Administrator
 *
 */
public class VoOpbSubjectSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805300950400003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SUBJ_NAME = "subj_name" ;		/* 文件夹名称 */
	
	/**
	 * 构造函数
	 */
	public VoOpbSubjectSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoOpbSubjectSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 文件夹名称 : String */
	public String getSubj_name()
	{
		return getValue( ITEM_SUBJ_NAME );
	}

	public void setSubj_name( String subj_name1 )
	{
		setValue( ITEM_SUBJ_NAME, subj_name1 );
	}

}

