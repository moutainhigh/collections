package com.gwssi.webservice.server;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.ShareConstants;

public class ParamAnalyzer
{
	/** keyΪ�ֶ�����valueΪ�û��ṩ��ֵ */
	private Map					paramMap		= null;

	private String				sql				= null;

	public static final String	USER_PARAM_SIGN	= "������ֵ��";

	public ParamAnalyzer(Map params)
	{
		paramMap = params;
	}

	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(ParamAnalyzer.class
											.getName());

	public String createSQL()
	{
		String originSQL = "" + paramMap.get("QUERY_SQL");
		String sql = analyseUserParams(originSQL);

		return sql.toString();
	}

	/**
	 * �����õ�SQL����еĲ����滻Ϊʵ��ֵ
	 * @param sql
	 * @return
	 */
	private String analyseUserParams(String sql)
	{
		logger.debug("����sqlΪ��" + sql);
		//System.out.println("����sqlΪ��" + sql);
		Pattern p = Pattern
				.compile("(\\w+\\.\\w+\\s+(([=><])|(>=)|(>)|(<)|(<=)|(<>)|(like)|(LIKE)|(in)|(IN))\\s+"
						+ "(\\s*(\\('"
						+ USER_PARAM_SIGN
						+ "'\\)\\s*)|(\\s*'"
						+ USER_PARAM_SIGN
						+ "'\\s*)|(\\s*'%"
						+ USER_PARAM_SIGN
						+ "%'\\s*)|(\\s*'"
						+ USER_PARAM_SIGN
						+ "%'\\s*)|(\\s*'%"
						+ USER_PARAM_SIGN
						+ "'\\s*)|("
						+ USER_PARAM_SIGN
						+ ")))"
						+ "|(\\s+\\w+\\(\\s*\\w+.\\w+\\s*,\\s*'\\w+-\\w+-\\w+(\\s\\w+:\\w+:\\w+)*\\s*\\'\\s*\\)"
						+ "\\s+(([=><])|(<>)|(>=)|(>)|(<)|(=)|(<=)|(like)|(LIKE)|(in)|(IN))\\s+\\w+\\(\\s*'"
						+ USER_PARAM_SIGN
						+ "'\\s*,\\s*'\\w+-\\w+-\\w+(\\s\\w+:\\w+:\\w+)+\\s*\\'\\s*\\))"
						+ "|(\\s*\\w+\\(\\s*substr\\(\\s*\\w+.\\w+\\s*,\\s*0\\s*,\\s*10\\s*\\)"
						+ "\\s*,\\s*'\\w+-\\w+-\\w+(\\s\\w+:\\w+:\\w+)*\\s*\\'\\s*\\)\\s+(([=><])|(<>)|(>=)|(>)|(<)|(=)|(<=)|(like)|(LIKE)|(in)|(IN))\\s+\\w+\\(\\s*'"
						+ USER_PARAM_SIGN
						+ "'\\s*,\\s*'\\w+-\\w+-\\w+(\\s\\w+:\\w+:\\w+)+\\s*\\'\\s*\\))");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			String one = m.group();
			logger.debug("����oneΪ��" + one);
			//System.out.println("����oneΪ��" + one);
			Pattern p2 = Pattern.compile("\\.\\w+");
			Matcher m2 = p2.matcher(one);
			if (m2.find()) {
				String key = m2.group().substring(1);
				logger.debug("����keyΪ��" + key);
				//System.out.println("����keyΪ��" + key);
				String v = paramMap.get(key) == null ? "" : (String) paramMap
						.get(key);		
				 
				if(StringUtils.isBlank(v)){
					//���VΪ�գ�˵�����õĲ����������ݿ��е��ֶ���������û�����ò���ֵ
					logger.info("��������û���ҵ�'"+key+"'��Ӧ���ֶλ��Ӧ�ֶ�û�����ò���ֵ");
				}else {
					logger.debug("����vΪ��" + v);
				}
				
				//System.out.println("����vΪ��" + v);
				if (isINPattern(one)) {
					logger.debug("is in pattern......");
					v = v.replaceAll(ShareConstants.MULTI_COLUMN_IN_SEPARATOR,
							"','");
					sql = sql.replaceFirst(USER_PARAM_SIGN, v);
					logger.debug("in �µ�sql:" + sql);
				} else if (v.indexOf(ShareConstants.MULTI_COLUMN_CONDITION_SEPARATOR) != -1) {
					String first = v.substring(0,
							v.indexOf(ShareConstants.MULTI_COLUMN_CONDITION_SEPARATOR));
					logger.debug("����firstΪ:" + first);
					//System.out.println("����firstΪ:" + first);
					first = fixDateFormat(one, first);
					logger.debug("����first fixDateFormat��Ϊ:" + first);
					//System.out.println("����first fixDateFormat��Ϊ:" + first);
					/*
					 * �������������˵��������� = �� <> ����ֻ���������ո�ʽ���� 2013-05-01 �����������㣬������Ϊ
					 * 2013-05-01 12:30:15 ��ʽ
					 */
					if (one.indexOf("substr") == -1) {
						sql = sql.replaceFirst(USER_PARAM_SIGN, first);

					} else {
						String tmp = sql.substring(sql.indexOf(one), sql
								.indexOf(one)
								+ one.length());
						tmp = tmp.replaceFirst(USER_PARAM_SIGN, first);
						sql = sql.substring(0, sql.indexOf(one))
								+ tmp
								+ sql.substring(
										sql.indexOf(one) + one.length(), sql
												.length());
					}
					// sql = sql.replaceFirst(USER_PARAM_SIGN, first);
					paramMap.put(key,
							v.substring(v.indexOf(ShareConstants.MULTI_COLUMN_CONDITION_SEPARATOR) + 1));
				} else {
					v = fixDateFormat(one, v);
					/*
					 * �������������˵��������� = �� <> ����ֻ���������ո�ʽ���� 2013-05-01 �����������㣬������Ϊ
					 * 2013-05-01 12:30:15 ��ʽ
					 */
					if (one.indexOf("substr") == -1) {
						sql = sql.replaceFirst(USER_PARAM_SIGN, v);
					} else {
						String tmp = sql.substring(sql.indexOf(one), sql
								.indexOf(one)
								+ one.length());
						tmp = tmp.replaceFirst(USER_PARAM_SIGN, v);
						sql = sql.substring(0, sql.indexOf(one))
								+ tmp
								+ sql.substring(
										sql.indexOf(one) + one.length(), sql
												.length());
					}
					// sql = sql.replaceFirst(USER_PARAM_SIGN, v);
				}
			}
			// �����ʱ��,������Ҫ�ع� - -
		}
		System.out.println(" === sql: " + sql);
		return sql;
	}

	/**
	 * ����������޸��������� �������2012��3��12�� ��ӦΪ > 2012-03-12 00:00:00
	 * 
	 * @param one
	 * @param v
	 * @return
	 */
	public static String fixDateFormat(String one, String v)
	{
		// System.out.println("here is "+ one);
		// ExConstant.INTERFACE_TIME;
		// logger.debug("here is one:" + one);
		//System.out.println("one="+one+"---v="+v);
		String sql_test = one.substring(one.indexOf(USER_PARAM_SIGN) - 15, one
				.indexOf(USER_PARAM_SIGN));
		if (sql_test.indexOf("to_date") > -1) {
			if (sql_test.indexOf(">=") > -1) {
				v += " ";
				v += ExConstant.INTERFACE_TIME;
				// ���� 2010-01-01��ʵ���� ���� 2010-01-01 00:00:00
				// logger.debug(">= v:" + v);
			} else if (sql_test.indexOf("<=") > -1
					|| (sql_test.indexOf("<>") == -1 && sql_test.indexOf(">") > -1)) {
				v += " ";
				v += ExConstant.INTERFACE_TIME;
				// С�ڵ��ڻ��ߴ��ڣ� ʵ���� С�ڵ��ڻ���� 2010-01-01 23:59:59
				// logger.debug("<= <> > v:" + v);
			} else if (sql_test.indexOf("=") == -1
					&& sql_test.indexOf("<>") == -1) {
				v += " ";
				v += ExConstant.INTERFACE_TIME;
				// ���˵��ںͲ����ڣ�����С�ںʹ��� ������Ϊ 2010-01-01 00:00:00
				// logger.debug("= <> v:" + v);
			}
		}
		logger.debug("ʱ���ʽ��Ϊ��" + v);
		return v;
	}

	public static boolean isINPattern(String s)
	{
		Pattern p2 = Pattern.compile("\\w+\\.\\w+\\s+((IN)||(in))\\s+(\\s*\\('"
				+ USER_PARAM_SIGN + "'\\)\\s*)");
		Matcher m2 = p2.matcher(s);
		return m2.find();
	}

	public static boolean isBetweenAndParam(String v)
	{
		Pattern p = Pattern
				.compile("^\\s*\\w+\\.\\w+\\s+(between|BETWEEN)\\s+\\?\\s+(and|AND)\\+\\?\\s*$");
		Matcher m = p.matcher(v);

		return m.matches();
	}

	public static void main(String[] args) throws IOException
	{

		// String testStr =
		// "[{columns:[{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1001\",name_en:\"REG_BUS_ENT_ID\",name_cn:\"��ҵ(����)ID\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1007\",name_en:\"ENT_NAME\",name_cn:\"��ҵ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"100\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1004\",name_en:\"REG_NO\",name_cn:\"��ҵע���\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"15\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1008\",name_en:\"ENT_TYPE\",name_cn:\"��ҵ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1026\",name_en:\"REG_CAP\",name_cn:\"ע���ʱ�(��Ԫ)\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1009\",name_en:\"INDUSTRY_PHY\",name_cn:\"��ҵ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1010\",name_en:\"INDUSTRY_CO\",name_cn:\"��ҵ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1033\",name_en:\"EST_DATE\",name_cn:\"��������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1038\",name_en:\"REG_ORG\",name_cn:\"�Ǽǻ���\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1021\",name_en:\"POSTAL_CODE\",name_cn:\"��������\"},alias:\"\",column_type:\"�ַ���\",column_length:\"6\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1022\",name_en:\"TEL\",name_cn:\"��ϵ�绰\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1023\",name_en:\"EMAIL\",name_cn:\"��������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1025\",name_en:\"PT_BUS_SCOPE\",name_cn:\"һ�㾭Ӫ��Χ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"3000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1024\",name_en:\"OP_SCOPE\",name_cn:\"��ɾ�Ӫ��Χ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"3000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1028\",name_en:\"OP_FROM\",name_cn:\"��Ӫ(פ��)������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1029\",name_en:\"OP_TO\",name_cn:\"��Ӫ(פ��)������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1052\",name_en:\"LOCAL_ADM\",name_cn:\"���ؼ�ܹ�����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1036\",name_en:\"INS_FORM\",name_cn:\"������ʽ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1020\",name_en:\"DOM_DISTRICT\",name_cn:\"ס��������������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1012\",name_en:\"DOM\",name_cn:\"ס��(ȫ)\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"300\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1018\",name_en:\"DOM_PRO_RIGHT\",name_cn:\"ס��ʹ�÷�ʽ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1030\",name_en:\"OP_LOC\",name_cn:\"��Ӫ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"300\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"f18aa2d08ccd4d6a940f0fbf20f00b87\",name_en:\"CORP_RPT_CER_TYPE\",name_cn:\"����������֤������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"096388738c14434fb3e8a93f487bccfd\",name_en:\"CORP_RPT_TEL\",name_cn:\"���������˵绰\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"25fab913034b46f48c5a1d3e974ac1ef\",name_en:\"CORP_RPT_SEX\",name_cn:\"�����������Ա�\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"e89d2587ec734f3699889a25ad126e38\",name_en:\"CORP_RPT_CER_NO\",name_cn:\"֤������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"400\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1003\",name_en:\"PRI_P_ID\",name_cn:\"������ݴ���\"},alias:\"\",column_type:\"�ַ���\",column_length:\"24\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1002\",name_en:\"ORGAN_CODE\",name_cn:\"��֯��������\"},alias:\"\",column_type:\"�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1005\",name_en:\"OLD_REG_NO\",name_cn:\"ԭע���\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"15\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1006\",name_en:\"LIC_REG_NO\",name_cn:\"ִ��ע���\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"15\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1011\",name_en:\"CORP_RPT\",name_cn:\"����������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"1000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1013\",name_en:\"DOM_STREET\",name_cn:\"ס��(�ֵ�)\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1014\",name_en:\"DOM_VILLAGE\",name_cn:\"ס��(��)\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"20\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1015\",name_en:\"DOM_NO\",name_cn:\"ס��(���ƺ�)\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"20\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1016\",name_en:\"DOM_BUILDING\",name_cn:\"ס��(¥��)\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1017\",name_en:\"DOM_OTHER\",name_cn:\"ס��(����)\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"100\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1019\",name_en:\"DOM_OWNER\",name_cn:\"ס����Ȩ��λ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"100\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1027\",name_en:\"REC_CAP\",name_cn:\"ʵ���ʱ�(��Ԫ)\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1031\",name_en:\"TRADE_TERM\",name_cn:\"Ӫҵ����(��)\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1032\",name_en:\"OP_SUFFIX\",name_cn:\"��Ӫ��Χ���±�ע\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"3000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1034\",name_en:\"CANCEL_DATE\",name_cn:\"ע������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1035\",name_en:\"REVOKE_DATE\",name_cn:\"��������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1037\",name_en:\"ENT_STATE\",name_cn:\"��ҵ״̬\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1039\",name_en:\"ENT_MEMO\",name_cn:\"��ע\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"1000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1040\",name_en:\"ENT_SORT\",name_cn:\"��ҵ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1041\",name_en:\"UPDATE_DATE\",name_cn:\"���ݸ�������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"20\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1042\",name_en:\"CHANGE_SIGN\",name_cn:\"���������־\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1043\",name_en:\"APPROVE_DATE\",name_cn:\"�Ǽ�ʱ��\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"20\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1044\",name_en:\"LOCK_SIGN\",name_cn:\"������ʶ\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1045\",name_en:\"LOCK_TIME\",name_cn:\"����ʱ��\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1046\",name_en:\"LOCK_ORG\",name_cn:\"��������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1047\",name_en:\"LOCK_PSN\",name_cn:\"������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1048\",name_en:\"OLD_ENT_TYPE\",name_cn:\"����ҵ����--������ʱ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1049\",name_en:\"ENTER_TYPE\",name_cn:\"����ϵͳ��ʽ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1050\",name_en:\"ENTER_DATE\",name_cn:\"����ϵͳ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1051\",name_en:\"NAME_APP_ID\",name_cn:\"����Ԥ���ĺ�\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"50\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1053\",name_en:\"ENT_TYPE_MEMO\",name_cn:\"��ҵ����˵��\"},alias:\"\",column_type:\"�ַ���\",column_length:\"8\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1054\",name_en:\"RECORD_NO\",name_cn:\"������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"15\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"��ҵ(����)\"},column:{id:\"QYDJT001C1055\",name_en:\"SOURCE\",name_cn:\"��Դϵͳ
		// -Ϊ��������\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1010\",name_en:\"HYPOTAXIS\",name_cn:\"������ϵ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1009\",name_en:\"DEP_IN_CHA\",name_cn:\"���ܲ���\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"100\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1018\",name_en:\"OP_FORM\",name_cn:\"��Ӫ��ʽ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"255\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"f2fc27df715c44d79773d93d3a78574f\",name_en:\"RES_PAR_SEC_SIGN\",name_cn:\"���������˵���֯��Ǳ�־\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"38fa2d56bea448c08de6d901a84b3b2f\",name_en:\"RES_PAR_M_SIGN\",name_cn:\"���������˵�Ա��־\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"8a30e990cc1e4cdd8bb09782100dc654\",name_en:\"PAR_ORG_W\",name_cn:\"����֯�齨��ʽ\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"f22e413b44ff46e4bd77ede51031feb0\",name_en:\"PAR_INS\",name_cn:\"����֯����\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"ba5190113bbe419a9046b10c90bcb606\",name_en:\"AN_ORG_PAR_SIGN\",name_cn:\"���������齨����֯��־\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1001\",name_en:\"REG_BUS_ENT_APP_ID\",name_cn:\"��ҵ(����)����ID\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1002\",name_en:\"REG_BUS_ENT_ID\",name_cn:\"��ҵ(����)ID\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1003\",name_en:\"LEAGUE_NUMBER\",name_cn:\"��ҵ��Ա����Ա����\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1004\",name_en:\"UNION_NUMBER\",name_cn:\"�����Ա����\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1005\",name_en:\"INCITY_NUMBER\",name_cn:\"������Ա\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1006\",name_en:\"LAIDOFF_NUMBER\",name_cn:\"�¸���Ա\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1007\",name_en:\"MEMBERS\",name_cn:\"��ҵ��Ա\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1008\",name_en:\"OTHERCITY_NUMBER\",name_cn:\"�Ⲻ��Ա\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1011\",name_en:\"HAS_PARTY\",name_cn:\"�Ƿ�������֯\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1012\",name_en:\"HAS_LEAGUE\",name_cn:\"�Ƿ�������֯\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1013\",name_en:\"HAS_UNION\",name_cn:\"�Ƿ���������֯\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1014\",name_en:\"PARTY_NUMBER\",name_cn:\"��ҵ��Ա�е�Ա����\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1015\",name_en:\"PRO_LOC\",name_cn:\"��������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"200\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1016\",name_en:\"DOM_ACREAGE\",name_cn:\"Ӫҵ���(ƽ����)\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"��ҵ(����)����\"},column:{id:\"QYDJT002C1017\",name_en:\"DOM_TERM\",name_cn:\"ס��ʹ������\"},alias:\"\",column_type:\"��ֵ��\",column_length:\"22\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1001\",name_en:\"REG_BUS_FOR_CAP_ID\",name_cn:\"������ҵ(����)����ID\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1002\",name_en:\"REG_BUS_ENT_ID\",name_cn:\"��ҵ(����)ID\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1003\",name_en:\"FOR_CAP_IND_CODE\",name_cn:\"���ʲ�ҵ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1004\",name_en:\"MID_PRE_IND_CODE\",name_cn:\"���������Ʋ�ҵ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1005\",name_en:\"CAP_CUR\",name_cn:\"����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1006\",name_en:\"LIC_AFF_CON\",name_cn:\"ִ�ո�������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"300\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1007\",name_en:\"DEL_NAM_ENG\",name_cn:\"�������(Ӣ��)\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"120\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1008\",name_en:\"PROJECT_TYPE\",name_cn:\"��Ŀ����--û��\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1009\",name_en:\"FAX\",name_cn:\"����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"30\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1010\",name_en:\"STA_ADD_ENG\",name_cn:\"���ĵ�ַ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"1000\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1011\",name_en:\"DEP_IN_CHA\",name_cn:\"���ܲ���\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1012\",name_en:\"EXA_AUTH\",name_cn:\"��������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1013\",name_en:\"CHA_MEC_DATE\",name_cn:\"ת������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1014\",name_en:\"SAN_DOC_NO\",name_cn:\"��׼�ĺ�\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"50\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1015\",name_en:\"SAN_DATE\",name_cn:\"��׼����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"10\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1016\",name_en:\"FOR_ENT_NAME\",name_cn:\"�ɳ���ҵ����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1017\",name_en:\"COUNTRY\",name_cn:\"����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1018\",name_en:\"BOARD_ADD\",name_cn:\"�ɳ���ҵ��ַ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1019\",name_en:\"BOARD_CHA\",name_cn:\"�ɳ���ҵ���³�����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"40\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1020\",name_en:\"BOARD_WOR\",name_cn:\"�ɳ���ҵ�Ƿ�Ϊ����500ǿ\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1021\",name_en:\"FOR_ENT_BUS\",name_cn:\"�����ҵ��Ӫ��ҵ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1022\",name_en:\"ITEMOF_OPORC_PRO\",name_cn:\"�а����̻�Ӫ������Ŀ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"512\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1023\",name_en:\"OPE_ACT_TYPE\",name_cn:\"��Ӫ�����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1024\",name_en:\"CONOF_CONTR_PRO\",name_cn:\"�а����̻�Ӫ��������\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"512\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1025\",name_en:\"FOR_DOM\",name_cn:\"����ס��\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1026\",name_en:\"FOR_REGE_CAP\",name_cn:\"����ע���ʱ�\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1027\",name_en:\"FOR_OP_SCOPE\",name_cn:\"���⾭Ӫ��Χ\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"1000\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1028\",name_en:\"TEC_DEV_ZONE\",name_cn:\"���ھ�����\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"6\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1029\",name_en:\"GYZC_BL\",name_cn:\"�����ʲ�����\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1030\",name_en:\"CHINA_KG_BL\",name_cn:\"�з��عɱ���\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"������ҵ(����)������\"},column:{id:\"QYDJT005C1031\",name_en:\"SFZFKG\",name_cn:\"�Ƿ��з��ع�\"},alias:\"\",column_type:\"�ַ���\",column_length:\"1\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1001\",name_en:\"REG_BUS_FOR_CAP_INV_ID\",name_cn:\"������ҵ(����)Ͷ��ID\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1002\",name_en:\"REG_BUS_ENT_ID\",name_cn:\"��ҵ(����)ID\"},alias:\"\",column_type:\"�䳤�ַ���\",column_length:\"32\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1003\",name_en:\"REC_CAP_RMB\",name_cn:\"ʵ���ʱ�(��)�������\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1004\",name_en:\"DOME_REG_CAP\",name_cn:\"�з�ע���ʱ�(��)\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1005\",name_en:\"DOME_REC_CAP_INV_PROP\",name_cn:\"�з�ʵ���ʱ����ʱ���\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1006\",name_en:\"DOME_REG_CAP_INV_PROP\",name_cn:\"�з�ע���ʱ�(��)���ʱ���\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1007\",name_en:\"DOME_REG_CAP_USD\",name_cn:\"�з�ע���ʱ�(��)������Ԫ\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1008\",name_en:\"DOME_REC_CAP\",name_cn:\"�з�ʵ���ʱ�\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1009\",name_en:\"DOME_REC_CAP_USD\",name_cn:\"�з�ʵ���ʱ�������Ԫ\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1010\",name_en:\"FOR_REG_CAP\",name_cn:\"�ⷽע���ʱ�(��)\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1011\",name_en:\"FOR_REG_CAP_INV_PROP\",name_cn:\"�ⷽע���ʱ�(��)���ʱ���\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1012\",name_en:\"FOR_REC_CAP_USD\",name_cn:\"�ⷽʵ���ʱ�������Ԫ\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1013\",name_en:\"FOR_REG_CAP_USD\",name_cn:\"�ⷽע���ʱ�(��)������Ԫ\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1014\",name_en:\"FOR_REC_CAP_CON_PROP\",name_cn:\"�ⷽʵ���ʱ����ʱ���\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1015\",name_en:\"FOR_REC_CAP\",name_cn:\"�ⷽʵ���ʱ�\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1016\",name_en:\"REC_CAP_USD\",name_cn:\"ʵ���ʱ�(��)������\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1017\",name_en:\"REC_CAP\",name_cn:\"ʵ���ʱ�\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1018\",name_en:\"REG_CAP_RMB\",name_cn:\"ע���ʱ�(��)�������\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1019\",name_en:\"CON_GRO_RMB\",name_cn:\"Ͷ���ܶ��������\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1020\",name_en:\"CON_GRO_USD\",name_cn:\"Ͷ���ܶ�������Ԫ\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1021\",name_en:\"CON_GRO\",name_cn:\"Ͷ���ܶ�\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1022\",name_en:\"REG_CAP\",name_cn:\"ע���ʱ�\"},alias:\"\",column_type:\"������\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"����(����)Ͷ����ҵͶ��\"},column:{id:\"QYDJT006C1023\",name_en:\"REG_CAP_USD\",name_cn:\"ע���ʱ�(��)������\"},alias:\"\",column_type:\"������\",column_length:\"22\"}],conditions:[],params:[]}]";
		// System.out.println(testStr.length());

		// Map map = new HashMap();
		// map.put("REG_BUS_ENT_ID",
		// "A3000000000000100000000028000249|A3000000000000100000000028000250");
		// String sql = new
		// ParamAnalyzer(map).analyseUserParams("SELECT
		// REG_BUS_ENT.REG_BUS_ENT_ID, REG_BUS_ENT.ORGAN_CODE,
		// REG_BUS_ENT.PRI_P_ID, REG_BUS_ENT.REG_NO, REG_BUS_ENT.OLD_REG_NO,
		// REG_BUS_ENT.LIC_REG_NO FROM REG_BUS_ENT WHERE (
		// REG_BUS_ENT.REG_BUS_ENT_ID IN '������ֵ��' )");
		// System.out.println(sql);
		//
		/*
		 * Map map = new HashMap(); map.put("ENT_NAME", "��"); map.put("REG_NO",
		 * "12"); map.put("UPDATE_DATE", "2013-01-05,2013-01-07"); // String
		 * sql="FOOD_CONTR_SUSPE.FOOD_NAME LIKE '% ������ֵ��%' "; String sql="SELECT
		 * REG_BUS_ENT.REG_BUS_ENT_ID ,REG_BUS_ENT_APP.HYPOTAXIS
		 * ,REG_BUS_FOR_CAP.REG_BUS_FOR_CAP_ID
		 * ,REG_BUS_FOR_CAP_INV.REG_BUS_FOR_CAP_INV_ID
		 * ,REG_BUS_FOR_CAP_INV.DOME_REG_CAP
		 * ,REG_BUS_FOR_CAP_INV.DOME_REC_CAP_INV_PROP
		 * ,REG_BUS_FOR_CAP_INV.DOME_REG_CAP_INV_PROP
		 * ,REG_BUS_FOR_CAP_INV.DOME_REG_CAP_USD
		 * ,REG_BUS_FOR_CAP_INV.DOME_REC_CAP
		 * ,REG_BUS_FOR_CAP_INV.DOME_REC_CAP_USD
		 * ,REG_BUS_FOR_CAP_INV.FOR_REG_CAP
		 * ,REG_BUS_FOR_CAP_INV.FOR_REG_CAP_INV_PROP
		 * ,REG_BUS_FOR_CAP_INV.FOR_REC_CAP_USD
		 * ,REG_BUS_FOR_CAP_INV.FOR_REG_CAP_USD
		 * ,REG_BUS_FOR_CAP_INV.FOR_REC_CAP_CON_PROP
		 * ,REG_BUS_FOR_CAP_INV.FOR_REC_CAP ,REG_BUS_FOR_CAP_INV.REC_CAP_USD
		 * ,REG_BUS_FOR_CAP_INV.REC_CAP ,REG_BUS_FOR_CAP_INV.REG_CAP_RMB
		 * ,REG_BUS_FOR_CAP_INV.CON_GRO_RMB ,REG_BUS_FOR_CAP_INV.CON_GRO_USD
		 * ,REG_BUS_FOR_CAP_INV.CON_GRO FROM
		 * REG_BUS_ENT,REG_BUS_ENT_APP,REG_BUS_FOR_CAP,REG_BUS_FOR_CAP_INV WHERE
		 * REG_BUS_ENT.REG_BUS_ENT_ID = REG_BUS_ENT_APP.REG_BUS_ENT_ID(+) AND
		 * REG_BUS_ENT.REG_BUS_ENT_ID=REG_BUS_FOR_CAP.REG_BUS_ENT_ID AND
		 * REG_BUS_ENT.REG_BUS_ENT_ID = REG_BUS_FOR_CAP_INV.REG_BUS_ENT_ID(+)
		 * AND (REG_BUS_ENT.DOM_DISTRICT = '110113' ) AND (
		 * REG_BUS_ENT.DOM_DISTRICT <> '110103' ) AND (
		 * to_date(REG_BUS_ENT.UPDATE_DATE,'YYYY-MM-DD HH24:mi:ss') >=
		 * to_date('������ֵ��','YYYY-MM-DD HH24:mi:ss') AND
		 * to_date(REG_BUS_ENT.UPDATE_DATE,'YYYY-MM-DD HH24:mi:ss') <=
		 * to_date('������ֵ��','YYYY-MM-DD HH24:mi:ss') AND REG_BUS_ENT.REG_NO <>
		 * '������ֵ��' AND REG_BUS_ENT.ENT_NAME = '������ֵ��' )"; System.out.println(new
		 * ParamAnalyzer(map).analyseUserParams(sql));
		 */
		//
		// String a ="'%������ֵ��%'";
		// String b ="'%������ֵ��%'";
		//
		// System.out.println(a.equals(b));
		//
		// Map map = new HashMap();
		// // map.put("ENT_NAME", "��");
		// map.put("REG_NO", "AbC");
		//
		// String sql = "SELECT REG_BUS_ENT.ENT_NAME, REG_BUS_ENT.INDUSTRY_PHY
		// "+
		// "FROM REG_BUS_ENT, REG_BUS_ENT_APP "+
		// "WHERE REG_BUS_ENT.REG_BUS_ENT_ID = REG_BUS_ENT_APP.REG_BUS_ENT_ID "+
		// "AND (REG_BUS_ENT.EST_DATE > '2001-01-01') "+
		// "AND (REG_BUS_ENT.DOM_DISTRICT <> '110102') "+
		// "AND (to_date(REG_BUS_ENT.UPDATE_DATE, 'YYYY-MM-DD HH24:mi:ss') >= "+
		// "to_date('������ֵ��', 'YYYY-MM-DD HH24:mi:ss') AND "+
		// "to_date(REG_BUS_ENT.UPDATE_DATE, 'YYYY-MM-DD HH24:mi:ss') <= "+
		// "to_date('������ֵ��', 'YYYY-MM-DD HH24:mi:ss') AND "+
		// "REG_BUS_ENT.REG_BUS_ENT_ID in ('������ֵ��'))";
		// sql =
		// " AND to_date(REG_BUS_ENT.UPDATE_DATE1,'YYYY-MM-DD HH24:mi:ss') >
		// to_date('������ֵ��','YYYY-MM-DD HH24:mi:ss') AND
		// to_date(REG_BUS_ENT.UPDATE_DATE,'YYYY-MM-DD HH24:mi:ss') <=
		// to_date('������ֵ��','YYYY-MM-DD HH24:mi:ss')";
		// String sql
		// ="SELECT
		// REG_BUS_ENT.REG_BUS_ENT_ID,REG_BUS_ENT.ENT_NAME,REG_BUS_ENT.EST_DATE,REG_BUS_ENT.APPROVE_DATE
		// FROM REG_BUS_ENT,REG_BUS_ENT_APP WHERE
		// REG_BUS_ENT.REG_BUS_ENT_ID=REG_BUS_ENT_APP.REG_BUS_ENT_ID AND
		// REG_BUS_ENT.EST_DATE>='2009-01-01' AND REG_BUS_ENT.ENT_NAME LIKE
		// '%������ֵ��%' ";
		// Syst8em.out.println(new ParamAnalyzer(map).analyseUserParams(sql));
		// System.out.println("\n\n\nsql: "+sql);
		// Map map = new HashMap();
		// map.put("UPDATE_DATE", "2013-01-05,2013-01-07");
		// map.put("REG_BUS_ENT_ID", "33333|44444|55555");
		// System.out.println(new ParamAnalyzer(map).analyseUserParams(sql));
		// {SVR_CODE=service38, JSJLS=5, KSJLS=1,
		// COLNAMEENARRAY=ENT_NAME,REG_NO,EST_DATE,CANCEL_DATE,REVOKE_DATE,
		// PASSWORD=111111,
		// LOGIN_NAME=ssjbuser,
		// COLNAMECNARRAY=��ҵ����,��ҵע���,��������,ע������,��������}
		// String sql
		// ="select
		// REG_BUS_ENT.ENT_NAME,REG_BUS_ENT.REG_NO,REG_BUS_ENT.EST_DATE,REG_BUS_ENT.CANCEL_DATE,REG_BUS_ENT.REVOKE_DATE
		// FROM REG_BUS_ENT,REG_BUS_ENT_APP WHERE
		// REG_BUS_ENT.REG_BUS_ENT_ID=REG_BUS_ENT_APP.REG_BUS_ENT_ID AND
		// (REG_BUS_ENT.EST_DATE>'2001-01-01') and ( REG_BUS_ENT.CANCEL_DATE >
		// '������ֵ��' )";
		// Map map = new HashMap();
		// map.put("ENT_NAME", "��");
		// FOOD_CONTR_SUSPE.FOOD_NAME LIKE '%������ֵ��%' ORDER BY
		// FOOD_CONTR_SUSPE.FOOD_CONTR_SUSPE_ID
		// REG_BUS_ENT.ENT_NAME like '%������ֵ��%' ";
		// sql = new ParamAnalyzer(map).analyseUserParams(sql);
		// System.out.println(sql);
		// UserParamAnalyzer.isINPattern("aaa.aa in ('11','22')");
	}

}
