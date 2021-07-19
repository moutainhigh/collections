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
	/** key为字段名，value为用户提供的值 */
	private Map					paramMap		= null;

	private String				sql				= null;

	public static final String	USER_PARAM_SIGN	= "（参数值）";

	public ParamAnalyzer(Map params)
	{
		paramMap = params;
	}

	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(ParamAnalyzer.class
											.getName());

	public String createSQL()
	{
		String originSQL = "" + paramMap.get("QUERY_SQL");
		String sql = analyseUserParams(originSQL);

		return sql.toString();
	}

	/**
	 * 将配置的SQL语句中的参数替换为实际值
	 * @param sql
	 * @return
	 */
	private String analyseUserParams(String sql)
	{
		logger.debug("参数sql为：" + sql);
		//System.out.println("参数sql为：" + sql);
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
			logger.debug("参数one为：" + one);
			//System.out.println("参数one为：" + one);
			Pattern p2 = Pattern.compile("\\.\\w+");
			Matcher m2 = p2.matcher(one);
			if (m2.find()) {
				String key = m2.group().substring(1);
				logger.debug("参数key为：" + key);
				//System.out.println("参数key为：" + key);
				String v = paramMap.get(key) == null ? "" : (String) paramMap
						.get(key);		
				 
				if(StringUtils.isBlank(v)){
					//如果V为空，说明配置的参数名与数据库中的字段名不符或没有设置参数值
					logger.info("参数有误！没有找到'"+key+"'对应的字段或对应字段没有设置参数值");
				}else {
					logger.debug("参数v为：" + v);
				}
				
				//System.out.println("参数v为：" + v);
				if (isINPattern(one)) {
					logger.debug("is in pattern......");
					v = v.replaceAll(ShareConstants.MULTI_COLUMN_IN_SEPARATOR,
							"','");
					sql = sql.replaceFirst(USER_PARAM_SIGN, v);
					logger.debug("in 下的sql:" + sql);
				} else if (v.indexOf(ShareConstants.MULTI_COLUMN_CONDITION_SEPARATOR) != -1) {
					String first = v.substring(0,
							v.indexOf(ShareConstants.MULTI_COLUMN_CONDITION_SEPARATOR));
					logger.debug("参数first为:" + first);
					//System.out.println("参数first为:" + first);
					first = fixDateFormat(one, first);
					logger.debug("参数first fixDateFormat后为:" + first);
					//System.out.println("参数first fixDateFormat后为:" + first);
					/*
					 * 对日期类型做了调整，比如 = 和 <> 运算只保持年月日格式，即 2013-05-01 其他日期运算，均调整为
					 * 2013-05-01 12:30:15 形式
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
					 * 对日期类型做了调整，比如 = 和 <> 运算只保持年月日格式，即 2013-05-01 其他日期运算，均调整为
					 * 2013-05-01 12:30:15 形式
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
			// 如果有时间,这里需要重构 - -
		}
		System.out.println(" === sql: " + sql);
		return sql;
	}

	/**
	 * 根据运算符修改日期类型 比如大于2012年3月12日 对应为 > 2012-03-12 00:00:00
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
				// 大于 2010-01-01，实际上 大于 2010-01-01 00:00:00
				// logger.debug(">= v:" + v);
			} else if (sql_test.indexOf("<=") > -1
					|| (sql_test.indexOf("<>") == -1 && sql_test.indexOf(">") > -1)) {
				v += " ";
				v += ExConstant.INTERFACE_TIME;
				// 小于等于或者大于， 实际上 小于等于或大于 2010-01-01 23:59:59
				// logger.debug("<= <> > v:" + v);
			} else if (sql_test.indexOf("=") == -1
					&& sql_test.indexOf("<>") == -1) {
				v += " ";
				v += ExConstant.INTERFACE_TIME;
				// 除了等于和不等于，还有小于和大于 补日期为 2010-01-01 00:00:00
				// logger.debug("= <> v:" + v);
			}
		}
		logger.debug("时间格式化为：" + v);
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
		// "[{columns:[{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1001\",name_en:\"REG_BUS_ENT_ID\",name_cn:\"企业(机构)ID\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1007\",name_en:\"ENT_NAME\",name_cn:\"企业名称\"},alias:\"\",column_type:\"变长字符型\",column_length:\"100\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1004\",name_en:\"REG_NO\",name_cn:\"企业注册号\"},alias:\"\",column_type:\"变长字符型\",column_length:\"15\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1008\",name_en:\"ENT_TYPE\",name_cn:\"企业类型\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1026\",name_en:\"REG_CAP\",name_cn:\"注册资本(万元)\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1009\",name_en:\"INDUSTRY_PHY\",name_cn:\"行业门类\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1010\",name_en:\"INDUSTRY_CO\",name_cn:\"行业代码\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1033\",name_en:\"EST_DATE\",name_cn:\"成立日期\"},alias:\"\",column_type:\"变长字符型\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1038\",name_en:\"REG_ORG\",name_cn:\"登记机关\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1021\",name_en:\"POSTAL_CODE\",name_cn:\"邮政编码\"},alias:\"\",column_type:\"字符型\",column_length:\"6\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1022\",name_en:\"TEL\",name_cn:\"联系电话\"},alias:\"\",column_type:\"变长字符型\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1023\",name_en:\"EMAIL\",name_cn:\"电子邮箱\"},alias:\"\",column_type:\"变长字符型\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1025\",name_en:\"PT_BUS_SCOPE\",name_cn:\"一般经营范围\"},alias:\"\",column_type:\"变长字符型\",column_length:\"3000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1024\",name_en:\"OP_SCOPE\",name_cn:\"许可经营范围\"},alias:\"\",column_type:\"变长字符型\",column_length:\"3000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1028\",name_en:\"OP_FROM\",name_cn:\"经营(驻在)期限自\"},alias:\"\",column_type:\"变长字符型\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1029\",name_en:\"OP_TO\",name_cn:\"经营(驻在)期限至\"},alias:\"\",column_type:\"变长字符型\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1052\",name_en:\"LOCAL_ADM\",name_cn:\"属地监管工商所\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1036\",name_en:\"INS_FORM\",name_cn:\"设立方式\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1020\",name_en:\"DOM_DISTRICT\",name_cn:\"住所所在行政区划\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1012\",name_en:\"DOM\",name_cn:\"住所(全)\"},alias:\"\",column_type:\"变长字符型\",column_length:\"300\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1018\",name_en:\"DOM_PRO_RIGHT\",name_cn:\"住所使用方式\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1030\",name_en:\"OP_LOC\",name_cn:\"经营场所\"},alias:\"\",column_type:\"变长字符型\",column_length:\"300\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"f18aa2d08ccd4d6a940f0fbf20f00b87\",name_en:\"CORP_RPT_CER_TYPE\",name_cn:\"法定代表人证件类型\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"096388738c14434fb3e8a93f487bccfd\",name_en:\"CORP_RPT_TEL\",name_cn:\"法定代表人电话\"},alias:\"\",column_type:\"变长字符型\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"25fab913034b46f48c5a1d3e974ac1ef\",name_en:\"CORP_RPT_SEX\",name_cn:\"法定代表人性别\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"e89d2587ec734f3699889a25ad126e38\",name_en:\"CORP_RPT_CER_NO\",name_cn:\"证件号码\"},alias:\"\",column_type:\"变长字符型\",column_length:\"400\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1003\",name_en:\"PRI_P_ID\",name_cn:\"主体身份代码\"},alias:\"\",column_type:\"字符型\",column_length:\"24\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1002\",name_en:\"ORGAN_CODE\",name_cn:\"组织机构代码\"},alias:\"\",column_type:\"字符型\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1005\",name_en:\"OLD_REG_NO\",name_cn:\"原注册号\"},alias:\"\",column_type:\"变长字符型\",column_length:\"15\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1006\",name_en:\"LIC_REG_NO\",name_cn:\"执照注册号\"},alias:\"\",column_type:\"变长字符型\",column_length:\"15\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1011\",name_en:\"CORP_RPT\",name_cn:\"法定代表人\"},alias:\"\",column_type:\"变长字符型\",column_length:\"1000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1013\",name_en:\"DOM_STREET\",name_cn:\"住所(街道)\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1014\",name_en:\"DOM_VILLAGE\",name_cn:\"住所(村)\"},alias:\"\",column_type:\"变长字符型\",column_length:\"20\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1015\",name_en:\"DOM_NO\",name_cn:\"住所(门牌号)\"},alias:\"\",column_type:\"变长字符型\",column_length:\"20\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1016\",name_en:\"DOM_BUILDING\",name_cn:\"住所(楼宇)\"},alias:\"\",column_type:\"变长字符型\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1017\",name_en:\"DOM_OTHER\",name_cn:\"住所(其他)\"},alias:\"\",column_type:\"变长字符型\",column_length:\"100\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1019\",name_en:\"DOM_OWNER\",name_cn:\"住所产权单位\"},alias:\"\",column_type:\"变长字符型\",column_length:\"100\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1027\",name_en:\"REC_CAP\",name_cn:\"实收资本(万元)\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1031\",name_en:\"TRADE_TERM\",name_cn:\"营业期限(年)\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1032\",name_en:\"OP_SUFFIX\",name_cn:\"经营范围项下标注\"},alias:\"\",column_type:\"变长字符型\",column_length:\"3000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1034\",name_en:\"CANCEL_DATE\",name_cn:\"注销日期\"},alias:\"\",column_type:\"变长字符型\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1035\",name_en:\"REVOKE_DATE\",name_cn:\"吊销日期\"},alias:\"\",column_type:\"变长字符型\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1037\",name_en:\"ENT_STATE\",name_cn:\"企业状态\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1039\",name_en:\"ENT_MEMO\",name_cn:\"备注\"},alias:\"\",column_type:\"变长字符型\",column_length:\"1000\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1040\",name_en:\"ENT_SORT\",name_cn:\"企业分类\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1041\",name_en:\"UPDATE_DATE\",name_cn:\"数据更新日期\"},alias:\"\",column_type:\"变长字符型\",column_length:\"20\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1042\",name_en:\"CHANGE_SIGN\",name_cn:\"发生变更标志\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1043\",name_en:\"APPROVE_DATE\",name_cn:\"登记时间\"},alias:\"\",column_type:\"变长字符型\",column_length:\"20\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1044\",name_en:\"LOCK_SIGN\",name_cn:\"锁定标识\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1045\",name_en:\"LOCK_TIME\",name_cn:\"锁定时间\"},alias:\"\",column_type:\"变长字符型\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1046\",name_en:\"LOCK_ORG\",name_cn:\"锁定机关\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1047\",name_en:\"LOCK_PSN\",name_cn:\"锁定人\"},alias:\"\",column_type:\"变长字符型\",column_length:\"30\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1048\",name_en:\"OLD_ENT_TYPE\",name_cn:\"旧企业类型--导数据时候用\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1049\",name_en:\"ENTER_TYPE\",name_cn:\"进入系统方式\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1050\",name_en:\"ENTER_DATE\",name_cn:\"进入系统日期\"},alias:\"\",column_type:\"变长字符型\",column_length:\"10\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1051\",name_en:\"NAME_APP_ID\",name_cn:\"名称预核文号\"},alias:\"\",column_type:\"变长字符型\",column_length:\"50\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1053\",name_en:\"ENT_TYPE_MEMO\",name_cn:\"企业类型说明\"},alias:\"\",column_type:\"字符型\",column_length:\"8\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1054\",name_en:\"RECORD_NO\",name_cn:\"档案号\"},alias:\"\",column_type:\"变长字符型\",column_length:\"15\"},{table:{id:\"QYDJT001\",name_en:\"REG_BUS_ENT\",name_cn:\"企业(机构)\"},column:{id:\"QYDJT001C1055\",name_en:\"SOURCE\",name_cn:\"来源系统
		// -为导数据用\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1010\",name_en:\"HYPOTAXIS\",name_cn:\"隶属关系\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1009\",name_en:\"DEP_IN_CHA\",name_cn:\"主管部门\"},alias:\"\",column_type:\"变长字符型\",column_length:\"100\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1018\",name_en:\"OP_FORM\",name_cn:\"经营方式\"},alias:\"\",column_type:\"变长字符型\",column_length:\"255\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"f2fc27df715c44d79773d93d3a78574f\",name_en:\"RES_PAR_SEC_SIGN\",name_cn:\"法定代表人党组织书记标志\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"38fa2d56bea448c08de6d901a84b3b2f\",name_en:\"RES_PAR_M_SIGN\",name_cn:\"法定代表人党员标志\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"8a30e990cc1e4cdd8bb09782100dc654\",name_en:\"PAR_ORG_W\",name_cn:\"党组织组建方式\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"f22e413b44ff46e4bd77ede51031feb0\",name_en:\"PAR_INS\",name_cn:\"党组织建制\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"ba5190113bbe419a9046b10c90bcb606\",name_en:\"AN_ORG_PAR_SIGN\",name_cn:\"本年检年度组建党组织标志\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1001\",name_en:\"REG_BUS_ENT_APP_ID\",name_cn:\"企业(机构)附表ID\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1002\",name_en:\"REG_BUS_ENT_ID\",name_cn:\"企业(机构)ID\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1003\",name_en:\"LEAGUE_NUMBER\",name_cn:\"从业人员中团员人数\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1004\",name_en:\"UNION_NUMBER\",name_cn:\"工会会员人数\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1005\",name_en:\"INCITY_NUMBER\",name_cn:\"市内人员\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1006\",name_en:\"LAIDOFF_NUMBER\",name_cn:\"下岗人员\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1007\",name_en:\"MEMBERS\",name_cn:\"从业人员\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1008\",name_en:\"OTHERCITY_NUMBER\",name_cn:\"外埠人员\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1011\",name_en:\"HAS_PARTY\",name_cn:\"是否建立党组织\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1012\",name_en:\"HAS_LEAGUE\",name_cn:\"是否建立团组织\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1013\",name_en:\"HAS_UNION\",name_cn:\"是否建立工会组织\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1014\",name_en:\"PARTY_NUMBER\",name_cn:\"从业人员中党员人数\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1015\",name_en:\"PRO_LOC\",name_cn:\"生产场地\"},alias:\"\",column_type:\"变长字符型\",column_length:\"200\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1016\",name_en:\"DOM_ACREAGE\",name_cn:\"营业面积(平方米)\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT002\",name_en:\"REG_BUS_ENT_APP\",name_cn:\"企业(机构)附表\"},column:{id:\"QYDJT002C1017\",name_en:\"DOM_TERM\",name_cn:\"住所使用期限\"},alias:\"\",column_type:\"数值型\",column_length:\"22\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1001\",name_en:\"REG_BUS_FOR_CAP_ID\",name_cn:\"外资企业(机构)附属ID\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1002\",name_en:\"REG_BUS_ENT_ID\",name_cn:\"企业(机构)ID\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1003\",name_en:\"FOR_CAP_IND_CODE\",name_cn:\"外资产业代码\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1004\",name_en:\"MID_PRE_IND_CODE\",name_cn:\"中西部优势产业代码\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1005\",name_en:\"CAP_CUR\",name_cn:\"币种\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1006\",name_en:\"LIC_AFF_CON\",name_cn:\"执照附加内容\"},alias:\"\",column_type:\"变长字符型\",column_length:\"300\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1007\",name_en:\"DEL_NAM_ENG\",name_cn:\"代表机构(英文)\"},alias:\"\",column_type:\"变长字符型\",column_length:\"120\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1008\",name_en:\"PROJECT_TYPE\",name_cn:\"项目类型--没用\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1009\",name_en:\"FAX\",name_cn:\"传真\"},alias:\"\",column_type:\"变长字符型\",column_length:\"30\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1010\",name_en:\"STA_ADD_ENG\",name_cn:\"外文地址\"},alias:\"\",column_type:\"变长字符型\",column_length:\"1000\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1011\",name_en:\"DEP_IN_CHA\",name_cn:\"主管部门\"},alias:\"\",column_type:\"变长字符型\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1012\",name_en:\"EXA_AUTH\",name_cn:\"审批机关\"},alias:\"\",column_type:\"变长字符型\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1013\",name_en:\"CHA_MEC_DATE\",name_cn:\"转型日期\"},alias:\"\",column_type:\"变长字符型\",column_length:\"10\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1014\",name_en:\"SAN_DOC_NO\",name_cn:\"批准文号\"},alias:\"\",column_type:\"变长字符型\",column_length:\"50\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1015\",name_en:\"SAN_DATE\",name_cn:\"批准日期\"},alias:\"\",column_type:\"变长字符型\",column_length:\"10\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1016\",name_en:\"FOR_ENT_NAME\",name_cn:\"派出企业名称\"},alias:\"\",column_type:\"变长字符型\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1017\",name_en:\"COUNTRY\",name_cn:\"国籍\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1018\",name_en:\"BOARD_ADD\",name_cn:\"派出企业地址\"},alias:\"\",column_type:\"变长字符型\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1019\",name_en:\"BOARD_CHA\",name_cn:\"派出企业董事长姓名\"},alias:\"\",column_type:\"变长字符型\",column_length:\"40\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1020\",name_en:\"BOARD_WOR\",name_cn:\"派出企业是否为世界500强\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1021\",name_en:\"FOR_ENT_BUS\",name_cn:\"外国企业主营行业\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1022\",name_en:\"ITEMOF_OPORC_PRO\",name_cn:\"承包工程或经营管理项目\"},alias:\"\",column_type:\"变长字符型\",column_length:\"512\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1023\",name_en:\"OPE_ACT_TYPE\",name_cn:\"经营活动类型\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1024\",name_en:\"CONOF_CONTR_PRO\",name_cn:\"承包工程或经营管理内容\"},alias:\"\",column_type:\"变长字符型\",column_length:\"512\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1025\",name_en:\"FOR_DOM\",name_cn:\"境外住所\"},alias:\"\",column_type:\"变长字符型\",column_length:\"100\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1026\",name_en:\"FOR_REGE_CAP\",name_cn:\"境外注册资本\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1027\",name_en:\"FOR_OP_SCOPE\",name_cn:\"境外经营范围\"},alias:\"\",column_type:\"变长字符型\",column_length:\"1000\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1028\",name_en:\"TEC_DEV_ZONE\",name_cn:\"所在经济区\"},alias:\"\",column_type:\"变长字符型\",column_length:\"6\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1029\",name_en:\"GYZC_BL\",name_cn:\"国有资产比例\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1030\",name_en:\"CHINA_KG_BL\",name_cn:\"中方控股比例\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT005\",name_en:\"REG_BUS_FOR_CAP\",name_cn:\"外资企业(机构)附属表\"},column:{id:\"QYDJT005C1031\",name_en:\"SFZFKG\",name_cn:\"是否中方控股\"},alias:\"\",column_type:\"字符型\",column_length:\"1\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1001\",name_en:\"REG_BUS_FOR_CAP_INV_ID\",name_cn:\"外资企业(机构)投资ID\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1002\",name_en:\"REG_BUS_ENT_ID\",name_cn:\"企业(机构)ID\"},alias:\"\",column_type:\"变长字符型\",column_length:\"32\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1003\",name_en:\"REC_CAP_RMB\",name_cn:\"实收资本(金)折人民币\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1004\",name_en:\"DOME_REG_CAP\",name_cn:\"中方注册资本(金)\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1005\",name_en:\"DOME_REC_CAP_INV_PROP\",name_cn:\"中方实收资本出资比例\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1006\",name_en:\"DOME_REG_CAP_INV_PROP\",name_cn:\"中方注册资本(金)出资比例\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1007\",name_en:\"DOME_REG_CAP_USD\",name_cn:\"中方注册资本(金)折万美元\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1008\",name_en:\"DOME_REC_CAP\",name_cn:\"中方实收资本\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1009\",name_en:\"DOME_REC_CAP_USD\",name_cn:\"中方实收资本折万美元\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1010\",name_en:\"FOR_REG_CAP\",name_cn:\"外方注册资本(金)\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1011\",name_en:\"FOR_REG_CAP_INV_PROP\",name_cn:\"外方注册资本(金)出资比例\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1012\",name_en:\"FOR_REC_CAP_USD\",name_cn:\"外方实收资本折万美元\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1013\",name_en:\"FOR_REG_CAP_USD\",name_cn:\"外方注册资本(金)折万美元\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1014\",name_en:\"FOR_REC_CAP_CON_PROP\",name_cn:\"外方实收资本出资比例\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1015\",name_en:\"FOR_REC_CAP\",name_cn:\"外方实收资本\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1016\",name_en:\"REC_CAP_USD\",name_cn:\"实收资本(金)折美金\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1017\",name_en:\"REC_CAP\",name_cn:\"实收资本\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1018\",name_en:\"REG_CAP_RMB\",name_cn:\"注册资本(金)折人民币\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1019\",name_en:\"CON_GRO_RMB\",name_cn:\"投资总额折人民币\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1020\",name_en:\"CON_GRO_USD\",name_cn:\"投资总额折万美元\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1021\",name_en:\"CON_GRO\",name_cn:\"投资总额\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1022\",name_en:\"REG_CAP\",name_cn:\"注册资本\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"},{table:{id:\"QYDJT006\",name_en:\"REG_BUS_FOR_CAP_INV\",name_cn:\"外商(机构)投资企业投资\"},column:{id:\"QYDJT006C1023\",name_en:\"REG_CAP_USD\",name_cn:\"注册资本(金)折美金\"},alias:\"\",column_type:\"数字型\",column_length:\"22\"}],conditions:[],params:[]}]";
		// System.out.println(testStr.length());

		// Map map = new HashMap();
		// map.put("REG_BUS_ENT_ID",
		// "A3000000000000100000000028000249|A3000000000000100000000028000250");
		// String sql = new
		// ParamAnalyzer(map).analyseUserParams("SELECT
		// REG_BUS_ENT.REG_BUS_ENT_ID, REG_BUS_ENT.ORGAN_CODE,
		// REG_BUS_ENT.PRI_P_ID, REG_BUS_ENT.REG_NO, REG_BUS_ENT.OLD_REG_NO,
		// REG_BUS_ENT.LIC_REG_NO FROM REG_BUS_ENT WHERE (
		// REG_BUS_ENT.REG_BUS_ENT_ID IN '（参数值）' )");
		// System.out.println(sql);
		//
		/*
		 * Map map = new HashMap(); map.put("ENT_NAME", "密"); map.put("REG_NO",
		 * "12"); map.put("UPDATE_DATE", "2013-01-05,2013-01-07"); // String
		 * sql="FOOD_CONTR_SUSPE.FOOD_NAME LIKE '% （参数值）%' "; String sql="SELECT
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
		 * to_date('（参数值）','YYYY-MM-DD HH24:mi:ss') AND
		 * to_date(REG_BUS_ENT.UPDATE_DATE,'YYYY-MM-DD HH24:mi:ss') <=
		 * to_date('（参数值）','YYYY-MM-DD HH24:mi:ss') AND REG_BUS_ENT.REG_NO <>
		 * '（参数值）' AND REG_BUS_ENT.ENT_NAME = '（参数值）' )"; System.out.println(new
		 * ParamAnalyzer(map).analyseUserParams(sql));
		 */
		//
		// String a ="'%（参数值）%'";
		// String b ="'%（参数值）%'";
		//
		// System.out.println(a.equals(b));
		//
		// Map map = new HashMap();
		// // map.put("ENT_NAME", "密");
		// map.put("REG_NO", "AbC");
		//
		// String sql = "SELECT REG_BUS_ENT.ENT_NAME, REG_BUS_ENT.INDUSTRY_PHY
		// "+
		// "FROM REG_BUS_ENT, REG_BUS_ENT_APP "+
		// "WHERE REG_BUS_ENT.REG_BUS_ENT_ID = REG_BUS_ENT_APP.REG_BUS_ENT_ID "+
		// "AND (REG_BUS_ENT.EST_DATE > '2001-01-01') "+
		// "AND (REG_BUS_ENT.DOM_DISTRICT <> '110102') "+
		// "AND (to_date(REG_BUS_ENT.UPDATE_DATE, 'YYYY-MM-DD HH24:mi:ss') >= "+
		// "to_date('（参数值）', 'YYYY-MM-DD HH24:mi:ss') AND "+
		// "to_date(REG_BUS_ENT.UPDATE_DATE, 'YYYY-MM-DD HH24:mi:ss') <= "+
		// "to_date('（参数值）', 'YYYY-MM-DD HH24:mi:ss') AND "+
		// "REG_BUS_ENT.REG_BUS_ENT_ID in ('（参数值）'))";
		// sql =
		// " AND to_date(REG_BUS_ENT.UPDATE_DATE1,'YYYY-MM-DD HH24:mi:ss') >
		// to_date('（参数值）','YYYY-MM-DD HH24:mi:ss') AND
		// to_date(REG_BUS_ENT.UPDATE_DATE,'YYYY-MM-DD HH24:mi:ss') <=
		// to_date('（参数值）','YYYY-MM-DD HH24:mi:ss')";
		// String sql
		// ="SELECT
		// REG_BUS_ENT.REG_BUS_ENT_ID,REG_BUS_ENT.ENT_NAME,REG_BUS_ENT.EST_DATE,REG_BUS_ENT.APPROVE_DATE
		// FROM REG_BUS_ENT,REG_BUS_ENT_APP WHERE
		// REG_BUS_ENT.REG_BUS_ENT_ID=REG_BUS_ENT_APP.REG_BUS_ENT_ID AND
		// REG_BUS_ENT.EST_DATE>='2009-01-01' AND REG_BUS_ENT.ENT_NAME LIKE
		// '%（参数值）%' ";
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
		// COLNAMECNARRAY=企业名称,企业注册号,成立日期,注销日期,吊销日期}
		// String sql
		// ="select
		// REG_BUS_ENT.ENT_NAME,REG_BUS_ENT.REG_NO,REG_BUS_ENT.EST_DATE,REG_BUS_ENT.CANCEL_DATE,REG_BUS_ENT.REVOKE_DATE
		// FROM REG_BUS_ENT,REG_BUS_ENT_APP WHERE
		// REG_BUS_ENT.REG_BUS_ENT_ID=REG_BUS_ENT_APP.REG_BUS_ENT_ID AND
		// (REG_BUS_ENT.EST_DATE>'2001-01-01') and ( REG_BUS_ENT.CANCEL_DATE >
		// '（参数值）' )";
		// Map map = new HashMap();
		// map.put("ENT_NAME", "密");
		// FOOD_CONTR_SUSPE.FOOD_NAME LIKE '%（参数值）%' ORDER BY
		// FOOD_CONTR_SUSPE.FOOD_CONTR_SUSPE_ID
		// REG_BUS_ENT.ENT_NAME like '%（参数值）%' ";
		// sql = new ParamAnalyzer(map).analyseUserParams(sql);
		// System.out.println(sql);
		// UserParamAnalyzer.isINPattern("aaa.aa in ('11','22')");
	}

}
