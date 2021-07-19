package com.gwssi.dw.aic.bj.newmon.quest.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoQuestType extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_QUS_TYPE_ID = "qus_type_id";			/* ���⴦��ʽID */
	public static final String ITEM_TYPE_ID = "type_id";			/* ����ʽID */
	public static final String ITEM_QUEST_TYPE = "quest_type";			/* ����ʽ */
	public static final String ITEM_QUS_ID = "qus_id";			/* ����ID */

	public VoQuestType(DataBus value)
	{
		super(value);
	}

	public VoQuestType()
	{
		super();
	}

	/* ���⴦��ʽID */
	public String getQus_type_id()
	{
		return getValue( ITEM_QUS_TYPE_ID );
	}

	public void setQus_type_id( String qus_type_id1 )
	{
		setValue( ITEM_QUS_TYPE_ID, qus_type_id1 );
	}

	/* ����ʽID */
	public String getType_id()
	{
		return getValue( ITEM_TYPE_ID );
	}

	public void setType_id( String type_id1 )
	{
		setValue( ITEM_TYPE_ID, type_id1 );
	}

	/* ����ʽ */
	public String getQuest_type()
	{
		return getValue( ITEM_QUEST_TYPE );
	}

	public void setQuest_type( String quest_type1 )
	{
		setValue( ITEM_QUEST_TYPE, quest_type1 );
	}

	/* ����ID */
	public String getQus_id()
	{
		return getValue( ITEM_QUS_ID );
	}

	public void setQus_id( String qus_id1 )
	{
		setValue( ITEM_QUS_ID, qus_id1 );
	}

}

