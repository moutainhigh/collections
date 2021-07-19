package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[opb_subject]的数据对象类
 * @author Administrator
 *
 */
public class VoOpbSubjectPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200805300950400004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SUBJ_ID = "subj_id" ;			/* 文件夹主键 */
	
	/**
	 * 构造函数
	 */
	public VoOpbSubjectPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoOpbSubjectPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 文件夹主键 : String */
	public String getSubj_id()
	{
		return getValue( ITEM_SUBJ_ID );
	}

	public void setSubj_id( String subj_id1 )
	{
		setValue( ITEM_SUBJ_ID, subj_id1 );
	}

}

