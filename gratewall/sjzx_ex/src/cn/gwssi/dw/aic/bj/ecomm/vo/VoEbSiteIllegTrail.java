package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_site_illeg_trail]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbSiteIllegTrail extends VoBase
{
	private static final long serialVersionUID = 201209241647280014L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* ����ID(��վID) */
	public static final String ITEM_DOM_SUBST = "dom_subst" ;		/* ��Ͻ�־� */
	public static final String ITEM_DOM_LOCAL_ADM = "dom_local_adm" ;	/* ��Ͻ������ */
	public static final String ITEM_IMAGE_PATH = "image_path" ;		/* ͼƬ·�� */
	public static final String ITEM_EVIDE_TYPE = "evide_type" ;		/* EVIDE_TYPE */
	public static final String ITEM_ENTRY_PERSON = "entry_person" ;	/* ¼���� */
	public static final String ITEM_ENTRY_DATE = "entry_date" ;		/* ¼��ʱ�� */
	public static final String ITEM_INFO_LEVEL = "info_level" ;		/* ��Ϣ�ȼ� */
	public static final String ITEM_ATTEN_LEVEL = "atten_level" ;	/* ��ע�ȼ� */
	public static final String ITEM_INFO_CATEG_BIG = "info_categ_big" ;	/* ��Ϣ������� */
	public static final String ITEM_INFO_CATEG_MID = "info_categ_mid" ;	/* ��Ϣ�������� */
	public static final String ITEM_INFO_CATEG_SMALL = "info_categ_small" ;	/* ��Ϣ����С�� */
	public static final String ITEM_OVERVIEW = "overview" ;			/* ��Ҫ˵�� */
	public static final String ITEM_IS_RECORD_CASE = "is_record_case" ;	/* �������� */
	public static final String ITEM_APPRO_LEADER = "appro_leader" ;	/* �����쵼 */
	public static final String ITEM_APPRO_STATUS = "appro_status" ;	/* ����״̬(0 δ���� 1 ������) */
	public static final String ITEM_APPRO_COMME = "appro_comme" ;	/* ������ע */
	public static final String ITEM_APPRO_DATE = "appro_date" ;		/* ����ʱ�� */
	public static final String ITEM_IS_CONTR_MEASU = "is_contr_measu" ;	/* ��ȡ���ƴ�ʩ */
	public static final String ITEM_IS_NO_TREAT = "is_no_treat" ;	/* ���账�� */
	public static final String ITEM_IS_PUBLI_OPINI = "is_publi_opini" ;	/* ���� */
	public static final String ITEM_INFO_TYPE = "info_type" ;		/* ��Դ��0����ҳ�ж�1���ص���3���� 4��Ϊ �� */
	public static final String ITEM_IS_JUDGE_MECHA = "is_judge_mecha" ;	/* �������л��ƣ�0����1����2�������У� */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* ����id */
	public static final String ITEM_CASE_NO = "case_no" ;			/* ������� */
	public static final String ITEM_ADMIN_TIPS = "admin_tips" ;		/* ������ʾ */
	public static final String ITEM_ORDER_CORRE = "order_corre" ;	/* ������� */
	public static final String ITEM_BACK_SEARC_CYCLE = "back_searc_cycle" ;	/* �ز����� */
	public static final String ITEM_PROPO_TRANS = "propo_trans" ;	/* ������ת */
	public static final String ITEM_EXECU_APPOI = "execu_appoi" ;	/* ��������Լ�� */
	public static final String ITEM_ADMIN_WARNI = "admin_warni" ;	/* ����������� */
	public static final String ITEM_LABEL_DEAL = "label_deal" ;		/* �������ʱ��ע���� */
	public static final String ITEM_APPRO_RESULT = "appro_result" ;	/* �������(0 ��ͬ�� 1 ͬ��) */
	public static final String ITEM_REMARK = "remark" ;				/* �����������ע */
	public static final String ITEM_HANDLE_DESC = "handle_desc" ;	/* �������� */
	public static final String ITEM_HANDLE_STATUS = "handle_status" ;	/* ����״̬(0δ���� 1�Ѵ���) */
	
	/**
	 * ���캯��
	 */
	public VoEbSiteIllegTrail()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbSiteIllegTrail(DataBus value)
	{
		super(value);
	}
	
	/* ����ID(��վID) : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

	/* ��Ͻ�־� : String */
	public String getDom_subst()
	{
		return getValue( ITEM_DOM_SUBST );
	}

	public void setDom_subst( String dom_subst1 )
	{
		setValue( ITEM_DOM_SUBST, dom_subst1 );
	}

	/* ��Ͻ������ : String */
	public String getDom_local_adm()
	{
		return getValue( ITEM_DOM_LOCAL_ADM );
	}

	public void setDom_local_adm( String dom_local_adm1 )
	{
		setValue( ITEM_DOM_LOCAL_ADM, dom_local_adm1 );
	}

	/* ͼƬ·�� : String */
	public String getImage_path()
	{
		return getValue( ITEM_IMAGE_PATH );
	}

	public void setImage_path( String image_path1 )
	{
		setValue( ITEM_IMAGE_PATH, image_path1 );
	}

	/* EVIDE_TYPE : String */
	public String getEvide_type()
	{
		return getValue( ITEM_EVIDE_TYPE );
	}

	public void setEvide_type( String evide_type1 )
	{
		setValue( ITEM_EVIDE_TYPE, evide_type1 );
	}

	/* ¼���� : String */
	public String getEntry_person()
	{
		return getValue( ITEM_ENTRY_PERSON );
	}

	public void setEntry_person( String entry_person1 )
	{
		setValue( ITEM_ENTRY_PERSON, entry_person1 );
	}

	/* ¼��ʱ�� : String */
	public String getEntry_date()
	{
		return getValue( ITEM_ENTRY_DATE );
	}

	public void setEntry_date( String entry_date1 )
	{
		setValue( ITEM_ENTRY_DATE, entry_date1 );
	}

	/* ��Ϣ�ȼ� : String */
	public String getInfo_level()
	{
		return getValue( ITEM_INFO_LEVEL );
	}

	public void setInfo_level( String info_level1 )
	{
		setValue( ITEM_INFO_LEVEL, info_level1 );
	}

	/* ��ע�ȼ� : String */
	public String getAtten_level()
	{
		return getValue( ITEM_ATTEN_LEVEL );
	}

	public void setAtten_level( String atten_level1 )
	{
		setValue( ITEM_ATTEN_LEVEL, atten_level1 );
	}

	/* ��Ϣ������� : String */
	public String getInfo_categ_big()
	{
		return getValue( ITEM_INFO_CATEG_BIG );
	}

	public void setInfo_categ_big( String info_categ_big1 )
	{
		setValue( ITEM_INFO_CATEG_BIG, info_categ_big1 );
	}

	/* ��Ϣ�������� : String */
	public String getInfo_categ_mid()
	{
		return getValue( ITEM_INFO_CATEG_MID );
	}

	public void setInfo_categ_mid( String info_categ_mid1 )
	{
		setValue( ITEM_INFO_CATEG_MID, info_categ_mid1 );
	}

	/* ��Ϣ����С�� : String */
	public String getInfo_categ_small()
	{
		return getValue( ITEM_INFO_CATEG_SMALL );
	}

	public void setInfo_categ_small( String info_categ_small1 )
	{
		setValue( ITEM_INFO_CATEG_SMALL, info_categ_small1 );
	}

	/* ��Ҫ˵�� : String */
	public String getOverview()
	{
		return getValue( ITEM_OVERVIEW );
	}

	public void setOverview( String overview1 )
	{
		setValue( ITEM_OVERVIEW, overview1 );
	}

	/* �������� : String */
	public String getIs_record_case()
	{
		return getValue( ITEM_IS_RECORD_CASE );
	}

	public void setIs_record_case( String is_record_case1 )
	{
		setValue( ITEM_IS_RECORD_CASE, is_record_case1 );
	}

	/* �����쵼 : String */
	public String getAppro_leader()
	{
		return getValue( ITEM_APPRO_LEADER );
	}

	public void setAppro_leader( String appro_leader1 )
	{
		setValue( ITEM_APPRO_LEADER, appro_leader1 );
	}

	/* ����״̬(0 δ���� 1 ������) : String */
	public String getAppro_status()
	{
		return getValue( ITEM_APPRO_STATUS );
	}

	public void setAppro_status( String appro_status1 )
	{
		setValue( ITEM_APPRO_STATUS, appro_status1 );
	}

	/* ������ע : String */
	public String getAppro_comme()
	{
		return getValue( ITEM_APPRO_COMME );
	}

	public void setAppro_comme( String appro_comme1 )
	{
		setValue( ITEM_APPRO_COMME, appro_comme1 );
	}

	/* ����ʱ�� : String */
	public String getAppro_date()
	{
		return getValue( ITEM_APPRO_DATE );
	}

	public void setAppro_date( String appro_date1 )
	{
		setValue( ITEM_APPRO_DATE, appro_date1 );
	}

	/* ��ȡ���ƴ�ʩ : String */
	public String getIs_contr_measu()
	{
		return getValue( ITEM_IS_CONTR_MEASU );
	}

	public void setIs_contr_measu( String is_contr_measu1 )
	{
		setValue( ITEM_IS_CONTR_MEASU, is_contr_measu1 );
	}

	/* ���账�� : String */
	public String getIs_no_treat()
	{
		return getValue( ITEM_IS_NO_TREAT );
	}

	public void setIs_no_treat( String is_no_treat1 )
	{
		setValue( ITEM_IS_NO_TREAT, is_no_treat1 );
	}

	/* ���� : String */
	public String getIs_publi_opini()
	{
		return getValue( ITEM_IS_PUBLI_OPINI );
	}

	public void setIs_publi_opini( String is_publi_opini1 )
	{
		setValue( ITEM_IS_PUBLI_OPINI, is_publi_opini1 );
	}

	/* ��Դ��0����ҳ�ж�1���ص���3���� 4��Ϊ �� : String */
	public String getInfo_type()
	{
		return getValue( ITEM_INFO_TYPE );
	}

	public void setInfo_type( String info_type1 )
	{
		setValue( ITEM_INFO_TYPE, info_type1 );
	}

	/* �������л��ƣ�0����1����2�������У� : String */
	public String getIs_judge_mecha()
	{
		return getValue( ITEM_IS_JUDGE_MECHA );
	}

	public void setIs_judge_mecha( String is_judge_mecha1 )
	{
		setValue( ITEM_IS_JUDGE_MECHA, is_judge_mecha1 );
	}

	/* ����id : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

	/* ������� : String */
	public String getCase_no()
	{
		return getValue( ITEM_CASE_NO );
	}

	public void setCase_no( String case_no1 )
	{
		setValue( ITEM_CASE_NO, case_no1 );
	}

	/* ������ʾ : String */
	public String getAdmin_tips()
	{
		return getValue( ITEM_ADMIN_TIPS );
	}

	public void setAdmin_tips( String admin_tips1 )
	{
		setValue( ITEM_ADMIN_TIPS, admin_tips1 );
	}

	/* ������� : String */
	public String getOrder_corre()
	{
		return getValue( ITEM_ORDER_CORRE );
	}

	public void setOrder_corre( String order_corre1 )
	{
		setValue( ITEM_ORDER_CORRE, order_corre1 );
	}

	/* �ز����� : String */
	public String getBack_searc_cycle()
	{
		return getValue( ITEM_BACK_SEARC_CYCLE );
	}

	public void setBack_searc_cycle( String back_searc_cycle1 )
	{
		setValue( ITEM_BACK_SEARC_CYCLE, back_searc_cycle1 );
	}

	/* ������ת : String */
	public String getPropo_trans()
	{
		return getValue( ITEM_PROPO_TRANS );
	}

	public void setPropo_trans( String propo_trans1 )
	{
		setValue( ITEM_PROPO_TRANS, propo_trans1 );
	}

	/* ��������Լ�� : String */
	public String getExecu_appoi()
	{
		return getValue( ITEM_EXECU_APPOI );
	}

	public void setExecu_appoi( String execu_appoi1 )
	{
		setValue( ITEM_EXECU_APPOI, execu_appoi1 );
	}

	/* ����������� : String */
	public String getAdmin_warni()
	{
		return getValue( ITEM_ADMIN_WARNI );
	}

	public void setAdmin_warni( String admin_warni1 )
	{
		setValue( ITEM_ADMIN_WARNI, admin_warni1 );
	}

	/* �������ʱ��ע���� : String */
	public String getLabel_deal()
	{
		return getValue( ITEM_LABEL_DEAL );
	}

	public void setLabel_deal( String label_deal1 )
	{
		setValue( ITEM_LABEL_DEAL, label_deal1 );
	}

	/* �������(0 ��ͬ�� 1 ͬ��) : String */
	public String getAppro_result()
	{
		return getValue( ITEM_APPRO_RESULT );
	}

	public void setAppro_result( String appro_result1 )
	{
		setValue( ITEM_APPRO_RESULT, appro_result1 );
	}

	/* �����������ע : String */
	public String getRemark()
	{
		return getValue( ITEM_REMARK );
	}

	public void setRemark( String remark1 )
	{
		setValue( ITEM_REMARK, remark1 );
	}

	/* �������� : String */
	public String getHandle_desc()
	{
		return getValue( ITEM_HANDLE_DESC );
	}

	public void setHandle_desc( String handle_desc1 )
	{
		setValue( ITEM_HANDLE_DESC, handle_desc1 );
	}

	/* ����״̬(0δ���� 1�Ѵ���) : String */
	public String getHandle_status()
	{
		return getValue( ITEM_HANDLE_STATUS );
	}

	public void setHandle_status( String handle_status1 )
	{
		setValue( ITEM_HANDLE_STATUS, handle_status1 );
	}

}

