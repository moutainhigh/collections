package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[opb_subject]的数据对象类
 * @author Administrator
 *
 */
public class VoOpbSubject extends VoBase
{
	private static final long serialVersionUID = 200805300950390002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SUBJ_NAME = "subj_name" ;		/* 文件夹名称 */
	public static final String ITEM_SUBJ_DESC = "subj_desc" ;		/* 文件夹描述 */
	public static final String ITEM_SUBJ_ID = "subj_id" ;			/* 文件夹主键 */
	
	/**
	 * 构造函数
	 */
	public VoOpbSubject()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoOpbSubject(DataBus value)
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

	/* 文件夹描述 : String */
	public String getSubj_desc()
	{
		return getValue( ITEM_SUBJ_DESC );
	}

	public void setSubj_desc( String subj_desc1 )
	{
		setValue( ITEM_SUBJ_DESC, subj_desc1 );
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

