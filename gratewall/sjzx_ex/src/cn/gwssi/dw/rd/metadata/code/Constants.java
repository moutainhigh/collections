package cn.gwssi.dw.rd.metadata.code;

public class Constants {
	/** ---------------------------����Դ������� -------------------------------*/
	//���������
	public static final String COMPARE_STATUS_PK_CHANGED = "1";
	//���������
	public static final String COMPARE_STATUS_IDX_CHANGED = "2";
	//��ɾ��
	public static final String COMPARE_STATUS_TAB_DELETE = "3";
	//�ֶ�����
	public static final String COMPARE_STATUS_COL_ADD = "4";
	//�ֶ����ͱ��
	public static final String COMPARE_STATUS_OBJ_CHANGED = "5";
	//�ֶγ��ȱ��
	public static final String COMPARE_STATUS_LEN_CHANGED = "6";
	//�ֶ�ɾ��
	public static final String COMPARE_STATUS_COL_DELETE = "7";
	
	/** ---------------------------�������״̬-------------------------------*/
	//�Ѵ���
	public static final String CHANGE_STATE_YES = "1";
	//δ����
	public static final String CHANGE_STATE_NO = "2";
	
	/** ---------------------------webBrosebox �ָ��� -------------------------------*/
	/**
	 * Freeze brosebox �ָ�����
	 */
	public static final String FREEZE_BROSEBOX_COMMA =",";
	/**
	 * Freeze brosebox �ָ���.
	 */
	public static final String FREEZE_BROSEBOX_DOT =".";
	/**
	 * Freeze brosebox �ָ���;
	 */
	public static final String FREEZE_BROSEBOX_SEMICOLON = ";";
	
	/** ---------------------------���ݿ�������� -------------------------------*/
	/**
	 * ��
	 */
	public static final String DB_OBJECT_TABLE ="T";
	/**
	 * ��ͼ
	 */
	public static final String DB_OBJECT_VIEW ="V";
	/**
	 * �洢����
	 */
	public static final String DB_OBJECT_PROCEDURES ="P";
	/**
	 * ����
	 */
	public static final String DB_OBJECT_FUNCTION ="F";
	/**
	 * ������
	 */
	public static final String DB_OBJECT_TRIGGERS ="R";
	
	/** ---------------------------�Ƿ��������ʶ -------------------------------*/
	//NO ����
	public static final String COLUMN_IS_PK ="0";
	//YES 
	public static final String COLUMN_NOT_PK ="1";
	public static final String COLUMN_IS_FK ="0";
	
	/** ---------------------------�ֶ��������� -------------------------------*/
	//�ַ�������
	public static final String[] ARR_DATA_VARCHAR ={"1","12","-1"};
	
	//����
	public static final String[] ARR_DATA_NUMBER = {"2","3","4","5","6","7","8","-5","-6","-7"};
	//����
	public static final String[] ARR_DATA_DATE = {"91","92","93"};
	
	//�ַ���	C
	public static final String COL_DATATYPE_CHAR = "C";
	//�ַ���	C
	public static final String COL_DATATYPE_VARCHAR = "VC";
	//�������ַ�	n
	public static final String COL_DATATYPE_NUMBER = "n";
	//��ֵ��	N
	public static final String COL_DATATYPE_DECIMAL = "N";
	//����ʱ����	YYYYMMDDhhmmss
	public static final String COL_DATATYPE_DATE = "DT";
	//������	B
	public static final String COL_DATATYPE_BOOLEAN = "B";
	//��������	BY
	public static final String COL_DATATYPE_BINARY = "BY";
	//����
	public static final String COL_DATATYPE_OTHER = "OT";
	
}
