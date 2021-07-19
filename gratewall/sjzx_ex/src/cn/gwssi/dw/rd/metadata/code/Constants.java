package cn.gwssi.dw.rd.metadata.code;

public class Constants {
	/** ---------------------------数据源变更类型 -------------------------------*/
	//表主键变更
	public static final String COMPARE_STATUS_PK_CHANGED = "1";
	//表索引变更
	public static final String COMPARE_STATUS_IDX_CHANGED = "2";
	//表删除
	public static final String COMPARE_STATUS_TAB_DELETE = "3";
	//字段增加
	public static final String COMPARE_STATUS_COL_ADD = "4";
	//字段类型变更
	public static final String COMPARE_STATUS_OBJ_CHANGED = "5";
	//字段长度变更
	public static final String COMPARE_STATUS_LEN_CHANGED = "6";
	//字段删除
	public static final String COMPARE_STATUS_COL_DELETE = "7";
	
	/** ---------------------------变更处理状态-------------------------------*/
	//已处理
	public static final String CHANGE_STATE_YES = "1";
	//未处理
	public static final String CHANGE_STATE_NO = "2";
	
	/** ---------------------------webBrosebox 分隔符 -------------------------------*/
	/**
	 * Freeze brosebox 分隔符，
	 */
	public static final String FREEZE_BROSEBOX_COMMA =",";
	/**
	 * Freeze brosebox 分隔符.
	 */
	public static final String FREEZE_BROSEBOX_DOT =".";
	/**
	 * Freeze brosebox 分隔符;
	 */
	public static final String FREEZE_BROSEBOX_SEMICOLON = ";";
	
	/** ---------------------------数据库对象类型 -------------------------------*/
	/**
	 * 表
	 */
	public static final String DB_OBJECT_TABLE ="T";
	/**
	 * 视图
	 */
	public static final String DB_OBJECT_VIEW ="V";
	/**
	 * 存储过程
	 */
	public static final String DB_OBJECT_PROCEDURES ="P";
	/**
	 * 函数
	 */
	public static final String DB_OBJECT_FUNCTION ="F";
	/**
	 * 触发器
	 */
	public static final String DB_OBJECT_TRIGGERS ="R";
	
	/** ---------------------------是否主外键标识 -------------------------------*/
	//NO 主键
	public static final String COLUMN_IS_PK ="0";
	//YES 
	public static final String COLUMN_NOT_PK ="1";
	public static final String COLUMN_IS_FK ="0";
	
	/** ---------------------------字段数据类型 -------------------------------*/
	//字符串类型
	public static final String[] ARR_DATA_VARCHAR ={"1","12","-1"};
	
	//数字
	public static final String[] ARR_DATA_NUMBER = {"2","3","4","5","6","7","8","-5","-6","-7"};
	//日期
	public static final String[] ARR_DATA_DATE = {"91","92","93"};
	
	//字符型	C
	public static final String COL_DATATYPE_CHAR = "C";
	//字符型	C
	public static final String COL_DATATYPE_VARCHAR = "VC";
	//数字型字符	n
	public static final String COL_DATATYPE_NUMBER = "n";
	//数值型	N
	public static final String COL_DATATYPE_DECIMAL = "N";
	//日期时间型	YYYYMMDDhhmmss
	public static final String COL_DATATYPE_DATE = "DT";
	//布尔型	B
	public static final String COL_DATATYPE_BOOLEAN = "B";
	//二进制流	BY
	public static final String COL_DATATYPE_BINARY = "BY";
	//其他
	public static final String COL_DATATYPE_OTHER = "OT";
	
}
