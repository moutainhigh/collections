package com.gwssi.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.database.DBPoolConnection;

/**
 * @author ���ڸ�����
 * 
 */
public class DateUtil
{

	/**
	 * ���峣����ʱ���ʽ
	 */
	private static String[]	dateFormat	= { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss",
			"yyyy��MM��dd��HHʱmm��ss��", "yyyy/MM/dd", "yy-MM-dd", "yy/MM/dd",
			"yyyy��MM��dd��", "HH:mm:ss", "yyyyMMddHHmmss", "yyyyMM", "yyyyMMdd",
			"yyyy.MM.dd", "yy.MM.dd","yyyy��M��d��"	};

	/**
	 * ��õ�ǰʱ�� ����:"2007-06-25 16:09" ��ȷ������
	 * 
	 * @return
	 */
	public static final String getYMDHMTime()
	{
		Date aDate = new Date();
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * ��õ�ǰʱ�� ����:"2007-06-25 16:09:09" ��ȷ����
	 * 
	 * @return
	 */
	public static final String getYMDHMSTime()
	{
		Date aDate = new Date();
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * ��õ�ǰʱ�� ����:"2007-06-25" ��ȷ����
	 * 
	 * @return
	 */
	public static final String getYMDTime()
	{
		Date aDate = new Date();
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat("yyyy-MM-dd");
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 
	 * @return
	 */
	public static final String getHHmmssTime()
	{
		Date aDate = new Date();
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat("HH:mm:ss");
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * ��ȡָ�����ڵ���һ��
	 * 
	 * @throws TxnException
	 */
	public static String getYesterdayDate(String strDate)
	{
		DateFormat df = DateFormat.getDateInstance();
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cd = Calendar.getInstance();
		if(strDate!=null && !"".equals(strDate)){
			String []tmp =strDate.split("-");
			cd.set(Calendar.YEAR, Integer.parseInt(tmp[0]));
			cd.set(Calendar.MONTH, Integer.parseInt(tmp[1])-1);
			cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tmp[2]));
			cd.add(Calendar.DATE, -1);
		}
		
		
		//cd.setTime(date);
		//cd.add(Calendar.MILLISECOND, -24 * 60 * 60 * 1000);
		Date yesterday = cd.getTime();
		System.out.println("----------"+yesterday);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strYesterday = sdf.format(yesterday);
		return strYesterday;
	}
	
	/**
	 * ��ȡָ�����ڵ���һ��
	 * 
	 * @throws TxnException
	 */
	public static String getTomorrowDate(String strDate)
	{
		DateFormat df = DateFormat.getDateInstance();
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.MILLISECOND, 24 * 60 * 60 * 1000);
		Date yesterday = cd.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strYesterday = sdf.format(yesterday);
		return strYesterday;
	}	

	/**
	 * ���time2 ���� time1������
	 * 
	 * @param dateFormat
	 * @param time1
	 *            Ŀ������
	 * @param time2
	 *            ��������
	 * @return
	 */
	public static long getQuot(String dateFormat, String time1, String time2)
	{
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat(dateFormat);
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	public static String getToday()
	{
		String time = "";
		time = getToday("yyyy-MM-dd");
		return time;
	}

	/**
	 * 
	 * getWeek ��ȡϵͳ��ǰ���������ڼ�
	 * 
	 * @return 1,2,3,...,7 ��������һ,���ڶ�,������,...,������
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static String getWeek()
	{
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return day == 0 ? "7" : day + "";
	}

	/**
	 * 
	 * @param format
	 *            ����ָ���ĸ�ʽʱ�����ͷ��ص�ǰʱ��
	 * @return
	 */
	public static String getToday(String format)
	{
		String time = "";
		SimpleDateFormat df = null;

		Calendar cal = new GregorianCalendar();
		df = new SimpleDateFormat(format);
		time = df.format(cal.getTime());
		return time;
	}

	/**
	 * DATE to String��֧�ֶ��ָ�ʽ
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, int index)
	{
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(dateFormat[index]).format(date);
	}

	/**
	 * ��һ������ת�����ڸ�ʽ����ʽ���� 2002-08-05
	 * 
	 * @param date
	 *            ������ʽ�������ڶ���
	 * @return ���ظ�ʽ������ַ��� <br>
	 * <br>
	 *         ���� <br>
	 *         ���ã� <br>
	 *         Calendar date = new GregorianCalendar(); <br>
	 *         String ret = DateUtils.toDateStr(calendar); <br>
	 *         ���أ� <br>
	 *         ret = "2002-12-04";
	 */
	public static String toDateStr(Calendar date)
	{
		if (date == null)
			return "";
		return new SimpleDateFormat(dateFormat[0]).format(date.getTime());
	}

	public static String toDateStr(Calendar date, int index)
	{
		if (date == null)
			return "";
		if (index >= dateFormat.length)
			index = 1;
		return new SimpleDateFormat(dateFormat[index]).format(date.getTime());
	}

	/**
	 * �����ڸ�ʽ�� java.util.Timestamp ת�� java.util.Calendar ��ʽ
	 * 
	 * @param date
	 *            java.sql.Timestamp ��ʽ��ʾ������
	 * @return java.util.Calendar ��ʽ��ʾ������
	 */
	public static Calendar convSqlTimestampToUtilCalendar(Timestamp date)
	{
		if (date == null)
			return null;
		else {
			java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return gc;
		}
	}

	/**
	 * ����һ���ַ������γ�һ��Calendar������Ӧ���ֲ�ͬ�����ڱ�ʾ��
	 * 
	 * @param dateStr
	 *            �����������ַ�����ע�⣬���ܴ�null��ȥ���������
	 * @return ���ؽ������Calendar���� <br>
	 * <br>
	 *         ������������ִ���ʽ���£� <br>
	 *         "yyyy-MM-dd HH:mm:ss", <br>
	 *         "yyyy/MM/dd HH:mm:ss", <br>
	 *         "yyyy��MM��dd��HHʱmm��ss��", <br>
	 *         "yyyy-MM-dd", <br>
	 *         "yyyy/MM/dd", <br>
	 *         "yy-MM-dd", <br>
	 *         "yy/MM/dd", <br>
	 *         "yyyy��MM��dd��", <br>
	 *         "HH:mm:ss", <br>
	 *         "yyyyMMddHHmmss", <br>
	 *         "yyyyMMdd", <br>
	 *         "yyyy.MM.dd", <br>
	 *         "yy.MM.dd"
	 */
	public static Calendar parseDate(String dateStr)
	{
		if (dateStr == null || "".equals(dateStr)
				|| dateStr.trim().length() == 0) {
			return null;
		}

		Date result = parseDate(dateStr, 0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(result);

		return cal;
	}

	/**
	 * �ڲ�����������ĳ�������е����ڸ�ʽ��������
	 * 
	 * @param dateStr
	 *            �����������ַ���
	 * @param index
	 *            ���ڸ�ʽ������
	 * @return ���ؽ������
	 */
	public static Date parseDate(String dateStr, int index)
	{
		if (dateStr == null || "".equals(dateStr)
				|| dateStr.trim().length() == 0) {
			return null;
		}

		DateFormat df = null;
		try {
			df = new SimpleDateFormat(dateFormat[index]);

			return df.parse(dateStr);
		} catch (ParseException pe) {
			return parseDate(dateStr, index + 1);
		} catch (ArrayIndexOutOfBoundsException aioe) {
			return null;
		}
	}

	/**
	 * �ڲ�����������ĳ�������е����ڸ�ʽ��������
	 * 
	 * @param dateStr
	 *            �����������ַ���
	 * @param index
	 *            ���ڸ�ʽ������
	 * @return ���ؽ������
	 */
	public static Timestamp parseTimestamp(String dateStr, int index)
	{
		DateFormat df = null;
		try {
			df = new SimpleDateFormat(dateFormat[index]);

			return new Timestamp(df.parse(dateStr).getTime());
		} catch (ParseException pe) {
			return new Timestamp(parseDate(dateStr, index + 1).getTime());
		} catch (ArrayIndexOutOfBoundsException aioe) {
			return null;
		}
	}

	/**
	 * �ڲ�����������Ĭ�ϵ����ڸ�ʽ��yyyy-MM-dd����������
	 * 
	 * @param dateStr
	 *            �����������ַ���
	 * @return ���ؽ������
	 */
	public static Timestamp parseTimestamp(String dateStr)
	{
		DateFormat df = null;
		try {
			df = new SimpleDateFormat(dateFormat[0]);
			return new Timestamp(df.parse(dateStr).getTime());
		} catch (ParseException pe) {
			return null;
		} catch (ArrayIndexOutOfBoundsException aioe) {
			return null;
		}
	}

	/**
	 * ���ݴ����ʱ�����ͺ;���ʱ�䴮 ���ض�Ӧ��ʱ������
	 * 
	 * @param query_date
	 * @param query_type
	 * @return
	 */
	public static String[] getDateRegionByType(String query_date,
			String query_type)
	{
		String startDate = "";
		String endDate = "";
		if (StringUtils.isBlank(query_date)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			endDate = df.format(new Date()) + "-01";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			startDate = df.format(calendar.getTime()) + "-01";
		} else {
			if (query_type.equals("month")) {
				startDate = query_date + "-01";
				Calendar calendar = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					calendar.setTime(df.parse(startDate));
					calendar.add(Calendar.MONTH, 1);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				endDate = df.format(calendar.getTime());
			} else if (query_type.equals("year")) {
				startDate = query_date + "-01-01";
				Calendar calendar = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					calendar.setTime(df.parse(startDate));
					calendar.add(Calendar.YEAR, 1);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				endDate = df.format(calendar.getTime());
			} else {
				System.out.println(query_date.split(",").length);
				if (query_date.split(",").length == 2) {
					startDate = StringUtils
							.isNotBlank(query_date.split(",")[0]) ? query_date
							.split(",")[0] : "1970-01-01";
					endDate = StringUtils.isNotBlank(query_date.split(",")[1]) ? query_date
							.split(",")[1] : "2020-01-01";
				} else {
					startDate = query_date.split(",")[0];
					endDate = "2020-01-01";
				}
			}
		}
		return new String[] { startDate, endDate };
	}

	/**
	 * ���ݴ����ʱ�䴮ת����ʾ��ʽ ���ض�Ӧ��ʱ������
	 * 
	 * @param query_date
	 * @param query_type
	 * @return
	 * @throws ParseException
	 */
	public static String getDateRegionRemark(String query_date,
			String query_index) throws ParseException
	{
		String return_Date = "";
		if (StringUtils.isBlank(query_index) || query_index.equals("month")) {
			if (StringUtils.isBlank(query_date)) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				DateFormat df = new SimpleDateFormat("yyyy��MM��");
				return_Date = df.format(calendar.getTime());
			} else {
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(query_date));
				df = new SimpleDateFormat("yyyy��MM��");
				return_Date = df.format(calendar.getTime());
			}
		} else {
			if (StringUtils.isBlank(query_date)) {
				Calendar calendar = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("yyyy��");
				return_Date = df.format(calendar.getTime());
			} else {
				if (query_index.equals("year")) {
					return_Date = query_date + "��";
				} else if (query_index.equals("quarter")) {
					return_Date = query_date.split("-")[0] + "��-��"
							+ query_date.split("-")[1] + "����";
				} else if (query_index.equals("halfYear")) {
					return_Date = query_date.split("-")[0]
							+ "��-"
							+ (query_date.split("-")[1].equals("1") ? "�ϰ���"
									: "�°���");
				} else {
					if (query_date.indexOf(",") == -1) {
						return_Date = query_date.replace("-", "��") + "��";
					} else {
						if (query_date.split(",").length == 1) {
							return_Date = query_date.split(",")[0] + "֮��";
						} else {
							if (StringUtils.isBlank(query_date.split(",")[0])) {
								return_Date = query_date.split(",")[1] + "֮ǰ";
							} else {
								return_Date = query_date.replace(",", "��");
							}
						}
					}
				}
			}
		}
		return return_Date;
	}

	public static String[] getQuarter(int year, int month)
	{
		String startDate = "";
		String endDate = "";
		if (month <= 3) {
			startDate = year + "-01-01";
			endDate = year + "-04-01";
		} else if (month > 3 && month <= 6) {
			startDate = year + "-04-01";
			endDate = year + "-07-01";
		} else if (month > 3 && month <= 9) {
			startDate = year + "-07-01";
			endDate = year + "-10-01";
		} else {
			startDate = year + "-10-01";
			endDate = (year + 1) + "-01-01";
		}
		return new String[] { startDate, endDate };
	}

	/**
	 * ����ѡ���ʱ���������ͻ�ȡĬ�ϵ�ʱ������
	 * 
	 * @param query_index
	 * @return
	 */
	public static String[] getDefaultRegion(String query_index)
	{
		String startDate = "";
		String endDate = "";
		// ������£�Ĭ��Ϊ����
		if (query_index.equals("month")) {
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			endDate = df.format(new Date()) + "-01";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			startDate = df.format(calendar.getTime()) + "-01";
		}// ������꣬Ĭ��Ϊ����
		else if (query_index.equals("year")) {
			DateFormat df = new SimpleDateFormat("yyyy");
			startDate = df.format(new Date()) + "-01-01";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 1);
			endDate = df.format(calendar.getTime()) + "-01-01";
		}// ����Ǽ��ȣ�Ĭ��Ϊ������
		else if (query_index.equals("quartor")) {
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			String[] times = getQuarter(year, month);
			startDate = times[0];
			endDate = times[1];
		}// ����ǰ��꣬Ĭ��Ϊ������
		else {
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			if (month <= 6) {
				startDate = year + "-01-01";
				endDate = year + "-07-01";
			} else {
				startDate = year + "-07-01";
				endDate = (year + 1) + "-01-01";
			}
		}
		return new String[] { startDate, endDate };
	}

	/**
	 * ���ݴ����ʱ�����ͺ;���ʱ�䴮 ���ض�Ӧ��ʱ������
	 * 
	 * @param query_date
	 * @param twoMonth
	 *            �Ƿ񷵻������µ�����
	 * @return
	 * @throws ParseException
	 */
	public static String[] getDateRegionByMonth(String query_date,
			String query_index) throws ParseException
	{
		String startDate = "";
		String endDate = "";
		// ���û�д������ڣ���ȡĬ�ϵ�ʱ������
		if (StringUtils.isBlank(query_date)) {
			return DateUtil.getDefaultRegion(query_index);
		} else {
			// ��
			if (query_index.equals("month")) {
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(query_date));
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(df.parse(query_date));
				startDate = df.format(calendar.getTime()) + "-01";
				calendar2.add(Calendar.MONTH, 1);
				endDate = df.format(calendar2.getTime()) + "-01";
			}// ��
			else if (query_index.equals("year")) {
				startDate = query_date + "-01-01";
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(startDate));
				calendar.add(Calendar.YEAR, 1);
				endDate = df.format(calendar.getTime());
			}// ����
			else if (query_index.equals("quarter")) {
				int year = Integer.parseInt(query_date.split("-")[0]);
				int quartor = Integer.parseInt(query_date.split("-")[1]) * 3;
				String[] times = getQuarter(year, quartor);
				startDate = times[0];
				endDate = times[1];
			} else {
				int year = Integer.parseInt(query_date.split("-")[0]);
				int half = Integer.parseInt(query_date.split("-")[1]);
				if (half == 1) {
					startDate = year + "-01-01";
					endDate = year + "-07-01";
				} else {
					startDate = year + "-07-01";
					endDate = (year + 1) + "-01-01";
				}
			}
		}
		return new String[] { startDate, endDate };
	}

	public static String[] getDateMomRegion(String query_date,
			String query_index) throws ParseException
	{
		String[] times = DateUtil.getDateRegionByMonth(query_date, query_index);
		String startDate = times[0];
		String endDate = times[1];
		if (query_index.equals("month")) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(startDate));
			calendar.add(Calendar.MONTH, -1);
			startDate = df.format(calendar.getTime());
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(df.parse(endDate));
			calendar1.add(Calendar.MONTH, -1);
			endDate = df.format(calendar1.getTime());
		} else if (query_index.equals("quarter")) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(startDate));
			calendar.add(Calendar.MONTH, -3);
			startDate = df.format(calendar.getTime());
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(df.parse(endDate));
			calendar1.add(Calendar.MONTH, -3);
			endDate = df.format(calendar1.getTime());
		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(startDate));
			calendar.add(Calendar.YEAR, -1);
			startDate = df.format(calendar.getTime());
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(df.parse(endDate));
			calendar1.add(Calendar.YEAR, -1);
			endDate = df.format(calendar1.getTime());
		}
		return new String[] { startDate, endDate };
	}

	public static String[] getDateMomYearRegion(String query_date,
			String query_index) throws ParseException
	{
		String[] times = DateUtil.getDateRegionByMonth(query_date, query_index);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = times[0];
		String endDate = times[1];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(startDate));
		calendar.add(Calendar.YEAR, -1);
		startDate = df.format(calendar.getTime());

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(df.parse(endDate));
		calendar1.add(Calendar.YEAR, -1);
		endDate = df.format(calendar1.getTime());
		return new String[] { startDate, endDate };
	}

	public static void test(String query_index)
	{
		if (StringUtils.isBlank(query_index) || query_index.equals("month")) {
			System.out.println("a");
		} else {
			System.out.println("b");
		}
	}

	/**
	 * ���ݴ����ʱ�����ͺ;���ʱ�䴮 ����ͬ�����ڵ�ʱ������
	 * 
	 * @param query_date
	 * @param t
	 * @return
	 * @throws ParseException
	 */
	public static String[] getDateRegionByLast(String query_date,
			String query_index) throws ParseException
	{
		String startDate = "";
		String endDate = "";
		if (StringUtils.isBlank(query_date)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -1);
			endDate = df.format(calendar.getTime()) + "-01";

			Calendar calendar2 = Calendar.getInstance();
			calendar2.add(Calendar.MONTH, -1);
			calendar2.add(Calendar.YEAR, -1);
			startDate = df.format(calendar2.getTime()) + "-01";
		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(query_date));
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.YEAR, -1);
			endDate = df.format(calendar.getTime()) + "-01";

			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(df.parse(query_date));
			calendar2.add(Calendar.YEAR, -1);
			startDate = df.format(calendar2.getTime()) + "-01";
		}
		return new String[] { startDate, endDate };
	}

	/**
	 * ��ȡ�������ҹ���ʱ������sql
	 * 
	 * @param query_index
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getTimeRegionSql(String query_index, String startDate,
			String endDate)
	{

		// oracle��ͬ�汾�����ݿ⣬��ѯ����sql�����һ�£�Ҫ���ж�һЩsql�Ľ��
		DBPoolConnection conn = new DBPoolConnection(
				CollectConstants.DATASOURCE_DEFAULT);
		String count_sql = "select rownum from dual connect by rownum<=2 ";
		List list = conn.selectBySql(count_sql);
		String stn = list.size() == 2 ? "<=" : "<";
		// System.out.println("���:"+list.size()+"----"+stn);
		StringBuffer sql = new StringBuffer();
		// ��
		if (StringUtils.isBlank(query_index) || query_index.equals("month")) {
			sql.append("select to_char(to_date('").append(startDate);
			sql.append("', 'yyyy-MM-dd')+rownum-1,'yyyy-MM-dd') dd from dual");
			sql.append(" connect by rownum ").append(stn).append(" (to_date('")
					.append(endDate);
			sql.append("', 'yyyy-MM-dd') -to_date('").append(startDate)
					.append("', 'yyyy-MM-dd'))");
		}// ��
		else if (query_index.equals("year")) {
			sql.append("select rownum dd from dual connect by rownum ")
					.append(stn).append("12");
		}// ����򼾶�
		else {
			sql.append("select to_char(add_months(to_date('").append(startDate);
			sql.append(
					"', 'yyyy-MM-dd'), rownum - 1),'MM') dd from dual connect by rownum ")
					.append(stn);
			sql.append("months_between(to_date('").append(endDate)
					.append("', 'yyyy-MM-dd'),to_date('");
			sql.append(startDate).append("', 'yyyy-MM-dd')) ");
		}
		return sql.toString();
	}

	/**
	 * 
	 * @param datestr
	 *            �����ʱ�䴮
	 * @param isHHmmss
	 *            �Ƿ�Ҫ���ش�ʱ����Ĵ�
	 * @return
	 */
	public static String[] getDateRegionByDatePicker(String datestr,
			boolean isHHmmss)
	{
		String beginTime = "";
		String endTime = "";
		if (StringUtils.isNotBlank(datestr)) {
			if (!datestr.trim().equals("���ѡ������")) {
				if (datestr.indexOf("��") == -1) {
					beginTime = datestr.trim() + (isHHmmss ? " 00:00:00" : "");
					endTime = datestr.trim() + (isHHmmss ? " 23:59:59" : "");
				} else {
					beginTime = datestr.split("��")[0].trim()
							+ (isHHmmss ? " 00:00:00" : "");
					endTime = datestr.split("��")[1].trim()
							+ (isHHmmss ? " 23:59:59" : "");
				}
			}
		}
		// ���û���� ˵��ѡ���ǵ���
		return new String[] { beginTime, endTime };
	}
	/**
	 * ��ָ������ת��Ϊ����������ʽ
	 * @param strData ֻ����"yyyy-MM-dd"��"yyyy-MM-dd HH:mm:ss"��ʽ���ַ���
	 * ���ظ�ʽΪ:yyyy��M��d��
	 * @throws TxnException
	 */
	public static String getDateDes(String strDate)
	{
		if(StringUtils.isBlank(strDate)){
			return null;
		}
		DateFormat df = DateFormat.getDateInstance();
		Date date = null;
		try {
			date = df.parse(strDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SimpleDateFormat(dateFormat[14]).format(date);
	}
	/**
	 * ��ָ������ת��Ϊ��������
	 * @param strDate ��sindex��ʾ�ĸ�ʽ�������ַ���
	 * @param sindex �����ʽ���
	 * @param dindex ���ظ�ʽ���
	 * @throws TxnException
	 */
	public static String getDateDes(String strDate,int sindex,int dindex)
	{
		if(sindex<0 || dindex<0 ||sindex>dateFormat.length || dindex>dateFormat.length ){
			return null;
		}
		if(StringUtils.isBlank(strDate)){
			return null;
		}
		DateFormat df = new SimpleDateFormat(dateFormat[sindex]);
		Date date = null;
		try {
			date = df.parse(strDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SimpleDateFormat(dateFormat[dindex]).format(date);
	}
	public static void main(String[] args)
	{
		String d = getDateDes("201312" ,10,14);
		System.out.println(d);
		//String[] times = DateUtil.getDateRegionByDatePicker("2013-07-18   ",true);
		//System.out.println(times[0] + "------" + times[1]);
	}

}
