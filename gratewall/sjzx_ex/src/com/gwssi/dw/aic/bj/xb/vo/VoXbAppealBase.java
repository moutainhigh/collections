package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xb_appeal_base]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXbAppealBase extends VoBase
{
	private static final long serialVersionUID = 200809111008440002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_XB_APPEAL_BASE_ID = "xb_appeal_base_id" ;	/* ���߻�����ϢID */
	public static final String ITEM_REGI_NO = "regi_no" ;			/* ������ */
	public static final String ITEM_ACC_REG_PER = "acc_reg_per" ;	/* �Ǽ��� */
	public static final String ITEM_REG_DEP = "reg_dep" ;			/* �Ǽǵ�λ */
	public static final String ITEM_APL_REG_DATE = "apl_reg_date" ;	/* �Ǽ����� */
	public static final String ITEM_NAME = "name" ;					/* ���߷����� */
	public static final String ITEM_SEX = "sex" ;					/* �Ա� */
	public static final String ITEM_PER_IDE = "per_ide" ;			/* ���߷�����1������...�� */
	public static final String ITEM_APL_PER_VACA = "apl_per_vaca" ;	/* ���߷�ְҵ */
	public static final String ITEM_APL_PER_LINK = "apl_per_link" ;	/* ���߷���ϵ��ʽ */
	public static final String ITEM_ADDR = "addr" ;					/* ���߷���ϵ��ַ */
	public static final String ITEM_APL_POSTAL_CODE = "apl_postal_code" ;	/* ���߷��������� */
	public static final String ITEM_INFO_ORI = "info_ori" ;			/* ������Ϣ��Դ */
	public static final String ITEM_REG_NO = "reg_no" ;				/* ���߷�ע��� */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* ���߷����� */
	public static final String ITEM_APL_ENT_ADDR = "apl_ent_addr" ;	/* ���߷�ע���ַ */
	public static final String ITEM_INV_OB_TYPE_LARGE = "inv_ob_type_large" ;	/* ���߱�Ĵ��� */
	public static final String ITEM_INV_OB_TYPE_MID = "inv_ob_type_mid" ;	/* ���߱������ */
	public static final String ITEM_INV_OB_TYPE_SMALL = "inv_ob_type_small" ;	/* ���߱��С�� */
	public static final String ITEM_INV_OB_TYPE_MOST_SMALL = "inv_ob_type_most_small" ;	/* ���߱��СС�� */
	public static final String ITEM_MDSE_NAME = "mdse_name" ;		/* ��Ʒ/�������� */
	public static final String ITEM_BRAND_NAME = "brand_name" ;		/* ��ƷƷ�� */
	public static final String ITEM_APL_OTHE_BRAN = "apl_othe_bran" ;	/* ����Ʒ�� */
	public static final String ITEM_TYPE_SPF = "type_spf" ;			/* ��Ʒ�ͺ� */
	public static final String ITEM_QUAN = "quan" ;					/* ��Ʒ���� */
	public static final String ITEM_ACC_TIME = "acc_time" ;			/* ����(����)���� */
	public static final String ITEM_BRI_ST = "bri_st" ;				/* ������� */
	public static final String ITEM_APL_QUE_CLAS = "apl_que_clas" ;	/* ������� */
	public static final String ITEM_APL_QUE_DETA_CLAS = "apl_que_deta_clas" ;	/* �������ϸ�� */
	public static final String ITEM_APL_PER_REQU = "apl_per_requ" ;	/* ����Ҫ�� */
	public static final String ITEM_APL_IMPT = "apl_impt" ;			/* �ش�Ͷ�� */
	public static final String ITEM_APL_IS_HEAD_STATE = "apl_is_head_state" ;	/* Ӧ��Ԥ�� */
	public static final String ITEM_APL_REC_DEPT2 = "apl_rec_dept2" ;	/* ���յ�λ���� */
	public static final String ITEM_ETL_ID = "etl_id" ;				/* ETL���к� */
	public static final String ITEM_ETL_FLAG = "etl_flag" ;			/* ETL����״̬ */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp" ;	/* ETLʱ��� */
	
	/**
	 * ���캯��
	 */
	public VoXbAppealBase()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXbAppealBase(DataBus value)
	{
		super(value);
	}
	
	/* ���߻�����ϢID : String */
	public String getXb_appeal_base_id()
	{
		return getValue( ITEM_XB_APPEAL_BASE_ID );
	}

	public void setXb_appeal_base_id( String xb_appeal_base_id1 )
	{
		setValue( ITEM_XB_APPEAL_BASE_ID, xb_appeal_base_id1 );
	}

	/* ������ : String */
	public String getRegi_no()
	{
		return getValue( ITEM_REGI_NO );
	}

	public void setRegi_no( String regi_no1 )
	{
		setValue( ITEM_REGI_NO, regi_no1 );
	}

	/* �Ǽ��� : String */
	public String getAcc_reg_per()
	{
		return getValue( ITEM_ACC_REG_PER );
	}

	public void setAcc_reg_per( String acc_reg_per1 )
	{
		setValue( ITEM_ACC_REG_PER, acc_reg_per1 );
	}

	/* �Ǽǵ�λ : String */
	public String getReg_dep()
	{
		return getValue( ITEM_REG_DEP );
	}

	public void setReg_dep( String reg_dep1 )
	{
		setValue( ITEM_REG_DEP, reg_dep1 );
	}

	/* �Ǽ����� : String */
	public String getApl_reg_date()
	{
		return getValue( ITEM_APL_REG_DATE );
	}

	public void setApl_reg_date( String apl_reg_date1 )
	{
		setValue( ITEM_APL_REG_DATE, apl_reg_date1 );
	}

	/* ���߷����� : String */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
	}

	/* �Ա� : String */
	public String getSex()
	{
		return getValue( ITEM_SEX );
	}

	public void setSex( String sex1 )
	{
		setValue( ITEM_SEX, sex1 );
	}

	/* ���߷�����1������...�� : String */
	public String getPer_ide()
	{
		return getValue( ITEM_PER_IDE );
	}

	public void setPer_ide( String per_ide1 )
	{
		setValue( ITEM_PER_IDE, per_ide1 );
	}

	/* ���߷�ְҵ : String */
	public String getApl_per_vaca()
	{
		return getValue( ITEM_APL_PER_VACA );
	}

	public void setApl_per_vaca( String apl_per_vaca1 )
	{
		setValue( ITEM_APL_PER_VACA, apl_per_vaca1 );
	}

	/* ���߷���ϵ��ʽ : String */
	public String getApl_per_link()
	{
		return getValue( ITEM_APL_PER_LINK );
	}

	public void setApl_per_link( String apl_per_link1 )
	{
		setValue( ITEM_APL_PER_LINK, apl_per_link1 );
	}

	/* ���߷���ϵ��ַ : String */
	public String getAddr()
	{
		return getValue( ITEM_ADDR );
	}

	public void setAddr( String addr1 )
	{
		setValue( ITEM_ADDR, addr1 );
	}

	/* ���߷��������� : String */
	public String getApl_postal_code()
	{
		return getValue( ITEM_APL_POSTAL_CODE );
	}

	public void setApl_postal_code( String apl_postal_code1 )
	{
		setValue( ITEM_APL_POSTAL_CODE, apl_postal_code1 );
	}

	/* ������Ϣ��Դ : String */
	public String getInfo_ori()
	{
		return getValue( ITEM_INFO_ORI );
	}

	public void setInfo_ori( String info_ori1 )
	{
		setValue( ITEM_INFO_ORI, info_ori1 );
	}

	/* ���߷�ע��� : String */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* ���߷����� : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* ���߷�ע���ַ : String */
	public String getApl_ent_addr()
	{
		return getValue( ITEM_APL_ENT_ADDR );
	}

	public void setApl_ent_addr( String apl_ent_addr1 )
	{
		setValue( ITEM_APL_ENT_ADDR, apl_ent_addr1 );
	}

	/* ���߱�Ĵ��� : String */
	public String getInv_ob_type_large()
	{
		return getValue( ITEM_INV_OB_TYPE_LARGE );
	}

	public void setInv_ob_type_large( String inv_ob_type_large1 )
	{
		setValue( ITEM_INV_OB_TYPE_LARGE, inv_ob_type_large1 );
	}

	/* ���߱������ : String */
	public String getInv_ob_type_mid()
	{
		return getValue( ITEM_INV_OB_TYPE_MID );
	}

	public void setInv_ob_type_mid( String inv_ob_type_mid1 )
	{
		setValue( ITEM_INV_OB_TYPE_MID, inv_ob_type_mid1 );
	}

	/* ���߱��С�� : String */
	public String getInv_ob_type_small()
	{
		return getValue( ITEM_INV_OB_TYPE_SMALL );
	}

	public void setInv_ob_type_small( String inv_ob_type_small1 )
	{
		setValue( ITEM_INV_OB_TYPE_SMALL, inv_ob_type_small1 );
	}

	/* ���߱��СС�� : String */
	public String getInv_ob_type_most_small()
	{
		return getValue( ITEM_INV_OB_TYPE_MOST_SMALL );
	}

	public void setInv_ob_type_most_small( String inv_ob_type_most_small1 )
	{
		setValue( ITEM_INV_OB_TYPE_MOST_SMALL, inv_ob_type_most_small1 );
	}

	/* ��Ʒ/�������� : String */
	public String getMdse_name()
	{
		return getValue( ITEM_MDSE_NAME );
	}

	public void setMdse_name( String mdse_name1 )
	{
		setValue( ITEM_MDSE_NAME, mdse_name1 );
	}

	/* ��ƷƷ�� : String */
	public String getBrand_name()
	{
		return getValue( ITEM_BRAND_NAME );
	}

	public void setBrand_name( String brand_name1 )
	{
		setValue( ITEM_BRAND_NAME, brand_name1 );
	}

	/* ����Ʒ�� : String */
	public String getApl_othe_bran()
	{
		return getValue( ITEM_APL_OTHE_BRAN );
	}

	public void setApl_othe_bran( String apl_othe_bran1 )
	{
		setValue( ITEM_APL_OTHE_BRAN, apl_othe_bran1 );
	}

	/* ��Ʒ�ͺ� : String */
	public String getType_spf()
	{
		return getValue( ITEM_TYPE_SPF );
	}

	public void setType_spf( String type_spf1 )
	{
		setValue( ITEM_TYPE_SPF, type_spf1 );
	}

	/* ��Ʒ���� : String */
	public String getQuan()
	{
		return getValue( ITEM_QUAN );
	}

	public void setQuan( String quan1 )
	{
		setValue( ITEM_QUAN, quan1 );
	}

	/* ����(����)���� : String */
	public String getAcc_time()
	{
		return getValue( ITEM_ACC_TIME );
	}

	public void setAcc_time( String acc_time1 )
	{
		setValue( ITEM_ACC_TIME, acc_time1 );
	}

	/* ������� : String */
	public String getBri_st()
	{
		return getValue( ITEM_BRI_ST );
	}

	public void setBri_st( String bri_st1 )
	{
		setValue( ITEM_BRI_ST, bri_st1 );
	}

	/* ������� : String */
	public String getApl_que_clas()
	{
		return getValue( ITEM_APL_QUE_CLAS );
	}

	public void setApl_que_clas( String apl_que_clas1 )
	{
		setValue( ITEM_APL_QUE_CLAS, apl_que_clas1 );
	}

	/* �������ϸ�� : String */
	public String getApl_que_deta_clas()
	{
		return getValue( ITEM_APL_QUE_DETA_CLAS );
	}

	public void setApl_que_deta_clas( String apl_que_deta_clas1 )
	{
		setValue( ITEM_APL_QUE_DETA_CLAS, apl_que_deta_clas1 );
	}

	/* ����Ҫ�� : String */
	public String getApl_per_requ()
	{
		return getValue( ITEM_APL_PER_REQU );
	}

	public void setApl_per_requ( String apl_per_requ1 )
	{
		setValue( ITEM_APL_PER_REQU, apl_per_requ1 );
	}

	/* �ش�Ͷ�� : String */
	public String getApl_impt()
	{
		return getValue( ITEM_APL_IMPT );
	}

	public void setApl_impt( String apl_impt1 )
	{
		setValue( ITEM_APL_IMPT, apl_impt1 );
	}

	/* Ӧ��Ԥ�� : String */
	public String getApl_is_head_state()
	{
		return getValue( ITEM_APL_IS_HEAD_STATE );
	}

	public void setApl_is_head_state( String apl_is_head_state1 )
	{
		setValue( ITEM_APL_IS_HEAD_STATE, apl_is_head_state1 );
	}

	/* ���յ�λ���� : String */
	public String getApl_rec_dept2()
	{
		return getValue( ITEM_APL_REC_DEPT2 );
	}

	public void setApl_rec_dept2( String apl_rec_dept21 )
	{
		setValue( ITEM_APL_REC_DEPT2, apl_rec_dept21 );
	}

	/* ETL���к� : String */
	public String getEtl_id()
	{
		return getValue( ITEM_ETL_ID );
	}

	public void setEtl_id( String etl_id1 )
	{
		setValue( ITEM_ETL_ID, etl_id1 );
	}

	/* ETL����״̬ : String */
	public String getEtl_flag()
	{
		return getValue( ITEM_ETL_FLAG );
	}

	public void setEtl_flag( String etl_flag1 )
	{
		setValue( ITEM_ETL_FLAG, etl_flag1 );
	}

	/* ETLʱ��� : String */
	public String getEtl_timestamp()
	{
		return getValue( ITEM_ETL_TIMESTAMP );
	}

	public void setEtl_timestamp( String etl_timestamp1 )
	{
		setValue( ITEM_ETL_TIMESTAMP, etl_timestamp1 );
	}

}

