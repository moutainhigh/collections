package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_sys_right_user]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSysRightUser extends VoBase
{
	private static final long serialVersionUID = 201209261622180002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EB_SYS_RIGHT_USER_ID = "eb_sys_right_user_id" ;	/* 用户id */
	public static final String ITEM_DEP_CODE = "dep_code" ;			/* 部门代码 */
	public static final String ITEM_SYS_USER_NAME = "sys_user_name" ;	/* 系统用户名 */
	public static final String ITEM_USER_NAME = "user_name" ;		/* 用户姓名 */
	public static final String ITEM_USER_SEX = "user_sex" ;			/* 用户性别 */
	public static final String ITEM_SYS_PASS_WORD = "sys_pass_word" ;	/* 用户密码 */
	public static final String ITEM_USER_POS = "user_pos" ;			/* user_pos */
	public static final String ITEM_USER_ORDER = "user_order" ;		/* 用户顺序 */
	public static final String ITEM_OFFIC_TEL = "offic_tel" ;		/* 办公电话 */
	public static final String ITEM_FAX = "fax" ;					/* 传真 */
	public static final String ITEM_MOB_TEL = "mob_tel" ;			/* 移动电话 */
	public static final String ITEM_CONTA_ADD = "conta_add" ;		/* 联系地址 */
	public static final String ITEM_POSTAL_CODE = "postal_code" ;	/* 邮政编码 */
	public static final String ITEM_EMAIL = "email" ;				/* email */
	public static final String ITEM_VALIDITY = "validity" ;			/* validity */
	public static final String ITEM_EMAIL_PASSW = "email_passw" ;	/* email_passw */
	public static final String ITEM_WGGL_USER_ID = "wggl_user_id" ;	/* wggl_user_id */
	public static final String ITEM_APPRO_SIGN = "appro_sign" ;		/* appro_sign */
	
	/**
	 * 构造函数
	 */
	public VoEbSysRightUser()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSysRightUser(DataBus value)
	{
		super(value);
	}
	
	/* 用户id : String */
	public String getEb_sys_right_user_id()
	{
		return getValue( ITEM_EB_SYS_RIGHT_USER_ID );
	}

	public void setEb_sys_right_user_id( String eb_sys_right_user_id1 )
	{
		setValue( ITEM_EB_SYS_RIGHT_USER_ID, eb_sys_right_user_id1 );
	}

	/* 部门代码 : String */
	public String getDep_code()
	{
		return getValue( ITEM_DEP_CODE );
	}

	public void setDep_code( String dep_code1 )
	{
		setValue( ITEM_DEP_CODE, dep_code1 );
	}

	/* 系统用户名 : String */
	public String getSys_user_name()
	{
		return getValue( ITEM_SYS_USER_NAME );
	}

	public void setSys_user_name( String sys_user_name1 )
	{
		setValue( ITEM_SYS_USER_NAME, sys_user_name1 );
	}

	/* 用户姓名 : String */
	public String getUser_name()
	{
		return getValue( ITEM_USER_NAME );
	}

	public void setUser_name( String user_name1 )
	{
		setValue( ITEM_USER_NAME, user_name1 );
	}

	/* 用户性别 : String */
	public String getUser_sex()
	{
		return getValue( ITEM_USER_SEX );
	}

	public void setUser_sex( String user_sex1 )
	{
		setValue( ITEM_USER_SEX, user_sex1 );
	}

	/* 用户密码 : String */
	public String getSys_pass_word()
	{
		return getValue( ITEM_SYS_PASS_WORD );
	}

	public void setSys_pass_word( String sys_pass_word1 )
	{
		setValue( ITEM_SYS_PASS_WORD, sys_pass_word1 );
	}

	/* user_pos : String */
	public String getUser_pos()
	{
		return getValue( ITEM_USER_POS );
	}

	public void setUser_pos( String user_pos1 )
	{
		setValue( ITEM_USER_POS, user_pos1 );
	}

	/* 用户顺序 : String */
	public String getUser_order()
	{
		return getValue( ITEM_USER_ORDER );
	}

	public void setUser_order( String user_order1 )
	{
		setValue( ITEM_USER_ORDER, user_order1 );
	}

	/* 办公电话 : String */
	public String getOffic_tel()
	{
		return getValue( ITEM_OFFIC_TEL );
	}

	public void setOffic_tel( String offic_tel1 )
	{
		setValue( ITEM_OFFIC_TEL, offic_tel1 );
	}

	/* 传真 : String */
	public String getFax()
	{
		return getValue( ITEM_FAX );
	}

	public void setFax( String fax1 )
	{
		setValue( ITEM_FAX, fax1 );
	}

	/* 移动电话 : String */
	public String getMob_tel()
	{
		return getValue( ITEM_MOB_TEL );
	}

	public void setMob_tel( String mob_tel1 )
	{
		setValue( ITEM_MOB_TEL, mob_tel1 );
	}

	/* 联系地址 : String */
	public String getConta_add()
	{
		return getValue( ITEM_CONTA_ADD );
	}

	public void setConta_add( String conta_add1 )
	{
		setValue( ITEM_CONTA_ADD, conta_add1 );
	}

	/* 邮政编码 : String */
	public String getPostal_code()
	{
		return getValue( ITEM_POSTAL_CODE );
	}

	public void setPostal_code( String postal_code1 )
	{
		setValue( ITEM_POSTAL_CODE, postal_code1 );
	}

	/* email : String */
	public String getEmail()
	{
		return getValue( ITEM_EMAIL );
	}

	public void setEmail( String email1 )
	{
		setValue( ITEM_EMAIL, email1 );
	}

	/* validity : String */
	public String getValidity()
	{
		return getValue( ITEM_VALIDITY );
	}

	public void setValidity( String validity1 )
	{
		setValue( ITEM_VALIDITY, validity1 );
	}

	/* email_passw : String */
	public String getEmail_passw()
	{
		return getValue( ITEM_EMAIL_PASSW );
	}

	public void setEmail_passw( String email_passw1 )
	{
		setValue( ITEM_EMAIL_PASSW, email_passw1 );
	}

	/* wggl_user_id : String */
	public String getWggl_user_id()
	{
		return getValue( ITEM_WGGL_USER_ID );
	}

	public void setWggl_user_id( String wggl_user_id1 )
	{
		setValue( ITEM_WGGL_USER_ID, wggl_user_id1 );
	}

	/* appro_sign : String */
	public String getAppro_sign()
	{
		return getValue( ITEM_APPRO_SIGN );
	}

	public void setAppro_sign( String appro_sign1 )
	{
		setValue( ITEM_APPRO_SIGN, appro_sign1 );
	}

}

