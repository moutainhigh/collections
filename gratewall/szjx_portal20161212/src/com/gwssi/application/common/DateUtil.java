package com.gwssi.application.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * @author 日期辅助类
 * 
 */
public class DateUtil {

	/**
	 * 定义常见的时间格式
	 */
	private static String[] dateFormat = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
			"yyyy/MM/dd HH:mm:ss", "yyyy年MM月dd日HH时mm分ss秒", "yyyy/MM/dd",
			"yy-MM-dd", "yy/MM/dd", "yyyy年MM月dd日", "HH:mm:ss",
			"yyyyMMddHHmmss", "yyyyMM", "yyyyMMdd", "yyyy.MM.dd", "yy.MM.dd",
			"yyyy年M月d日" };

	/**
	 * 获得当前时间 类型:"2007-06-25 16:09" 精确到分钟
	 * 
	 * @return
	 */
	public static final String getYMDHMTime() {
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
	 * 获得当前时间 类型:"2007-06-25 16:09:09" 精确到秒
	 * 
	 * @return
	 */
	public static final String getYMDHMSTime() {
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
	 * 获得当前时间 类型:"2007-06-25" 精确到天
	 * 
	 * @return
	 */
	public static final String getYMDTime() {
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
	public static final String getHHmmssTime() {
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
	 * 获取指定日期的上一天
	 * 
	 * @throws TxnException
	 */
	public static String getYesterdayDate(String strDate) {
		DateFormat df = DateFormat.getDateInstance();
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cd = Calendar.getInstance();
		if (strDate != null && !"".equals(strDate)) {
			String[] tmp = strDate.split("-");
			cd.set(Calendar.YEAR, Integer.parseInt(tmp[0]));
			cd.set(Calendar.MONTH, Integer.parseInt(tmp[1]) - 1);
			cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tmp[2]));
			cd.add(Calendar.DATE, -1);
		}

		// cd.setTime(date);
		// cd.add(Calendar.MILLISECOND, -24 * 60 * 60 * 1000);
		Date yesterday = cd.getTime();
		System.out.println("----------" + yesterday);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strYesterday = sdf.format(yesterday);
		return strYesterday;
	}

	/**
	 * 获取指定日期的下一天
	 * 
	 * @throws TxnException
	 */
	public static String getTomorrowDate(String strDate) {
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
	 * 获得time2 距离 time1的天数
	 * 
	 * @param dateFormat
	 * @param time1
	 *            目标日期
	 * @param time2
	 *            基本日期
	 * @return
	 */
	public static long getQuot(String dateFormat, String time1, String time2) {
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

	public static String getToday() {
		String time = "";
		time = getToday("yyyy-MM-dd");
		return time;
	}

	/**
	 * 
	 * getWeek 获取系统当前日期是星期几
	 * 
	 * @return 1,2,3,...,7 代表星期一,星期二,星期三,...,星期天
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String getWeek() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return day == 0 ? "1" : day + "";
	}

	/**
	 * 
	 * @param format
	 *            根据指定的格式时间类型返回当前时间
	 * @return
	 */
	public static String getToday(String format) {
		String time = "";
		SimpleDateFormat df = null;

		Calendar cal = new GregorianCalendar();
		df = new SimpleDateFormat(format);
		time = df.format(cal.getTime());
		return time;
	}

	/**
	 * DATE to String，支持多种格式
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, int index) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(dateFormat[index]).format(date);
	}

	/**
	 * 将一个日期转成日期格式，格式这样 2002-08-05
	 * 
	 * @param date
	 *            期望格式化的日期对象
	 * @return 返回格式化后的字符串 <br>
	 * <br>
	 *         例： <br>
	 *         调用： <br>
	 *         Calendar date = new GregorianCalendar(); <br>
	 *         String ret = DateUtils.toDateStr(calendar); <br>
	 *         返回： <br>
	 *         ret = "2002-12-04";
	 */
	public static String toDateStr(Calendar date) {
		if (date == null)
			return "";
		return new SimpleDateFormat(dateFormat[0]).format(date.getTime());
	}

	public static String toDateStr(Calendar date, int index) {
		if (date == null)
			return "";
		if (index >= dateFormat.length)
			index = 1;
		return new SimpleDateFormat(dateFormat[index]).format(date.getTime());
	}

	/**
	 * 将日期格式从 java.util.Timestamp 转到 java.util.Calendar 格式
	 * 
	 * @param date
	 *            java.sql.Timestamp 格式表示的日期
	 * @return java.util.Calendar 格式表示的日期
	 */
	public static Calendar convSqlTimestampToUtilCalendar(Timestamp date) {
		if (date == null)
			return null;
		else {
			java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return gc;
		}
	}

	/**
	 * 解析一个字符串，形成一个Calendar对象，适应各种不同的日期表示法
	 * 
	 * @param dateStr
	 *            期望解析的字符串，注意，不能传null进去，否则出错
	 * @return 返回解析后的Calendar对象 <br>
	 * <br>
	 *         可输入的日期字串格式如下： <br>
	 *         "yyyy-MM-dd HH:mm:ss", <br>
	 *         "yyyy/MM/dd HH:mm:ss", <br>
	 *         "yyyy年MM月dd日HH时mm分ss秒", <br>
	 *         "yyyy-MM-dd", <br>
	 *         "yyyy/MM/dd", <br>
	 *         "yy-MM-dd", <br>
	 *         "yy/MM/dd", <br>
	 *         "yyyy年MM月dd日", <br>
	 *         "HH:mm:ss", <br>
	 *         "yyyyMMddHHmmss", <br>
	 *         "yyyyMMdd", <br>
	 *         "yyyy.MM.dd", <br>
	 *         "yy.MM.dd"
	 */
	public static Calendar parseDate(String dateStr) {
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
	 * 内部方法，根据某个索引中的日期格式解析日期
	 * 
	 * @param dateStr
	 *            期望解析的字符串
	 * @param index
	 *            日期格式的索引
	 * @return 返回解析结果
	 */
	public static Date parseDate(String dateStr, int index) {
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
	 * 内部方法，根据某个索引中的日期格式解析日期
	 * 
	 * @param dateStr
	 *            期望解析的字符串
	 * @param index
	 *            日期格式的索引
	 * @return 返回解析结果
	 */
	public static Timestamp parseTimestamp(String dateStr, int index) {
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
	 * 内部方法，根据默认的日期格式“yyyy-MM-dd”解析日期
	 * 
	 * @param dateStr
	 *            期望解析的字符串
	 * @return 返回解析结果
	 */
	public static Timestamp parseTimestamp(String dateStr) {
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
	 * 根据传入的时间类型和具体时间串 返回对应的时间区间
	 * 
	 * @param query_date
	 * @param query_type
	 * @return
	 */
	public static String[] getDateRegionByType(String query_date,
			String query_type) {
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
	 * 根据传入的时间串转换显示格式 返回对应的时间描述
	 * 
	 * @param query_date
	 * @param query_type
	 * @return
	 * @throws ParseException
	 */
	public static String getDateRegionRemark(String query_date,
			String query_index) throws ParseException {
		String return_Date = "";
		if (StringUtils.isBlank(query_index) || query_index.equals("month")) {
			if (StringUtils.isBlank(query_date)) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				DateFormat df = new SimpleDateFormat("yyyy年MM月");
				return_Date = df.format(calendar.getTime());
			} else {
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(query_date));
				df = new SimpleDateFormat("yyyy年MM月");
				return_Date = df.format(calendar.getTime());
			}
		} else {
			if (StringUtils.isBlank(query_date)) {
				Calendar calendar = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("yyyy年");
				return_Date = df.format(calendar.getTime());
			} else {
				if (query_index.equals("year")) {
					return_Date = query_date + "年";
				} else if (query_index.equals("quarter")) {
					return_Date = query_date.split("-")[0] + "年-第"
							+ query_date.split("-")[1] + "季度";
				} else if (query_index.equals("halfYear")) {
					return_Date = query_date.split("-")[0]
							+ "年-"
							+ (query_date.split("-")[1].equals("1") ? "上半年"
									: "下半年");
				} else {
					if (query_date.indexOf(",") == -1) {
						return_Date = query_date.replace("-", "年") + "月";
					} else {
						if (query_date.split(",").length == 1) {
							return_Date = query_date.split(",")[0] + "之后";
						} else {
							if (StringUtils.isBlank(query_date.split(",")[0])) {
								return_Date = query_date.split(",")[1] + "之前";
							} else {
								return_Date = query_date.replace(",", "至");
							}
						}
					}
				}
			}
		}
		return return_Date;
	}

	public static String[] getQuarter(int year, int month) {
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
	 * 根据选择的时间区间类型获取默认的时间区间
	 * 
	 * @param query_index
	 * @return
	 */
	public static String[] getDefaultRegion(String query_index) {
		String startDate = "";
		String endDate = "";
		// 如果是月，默认为上月
		if (query_index.equals("month")) {
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			endDate = df.format(new Date()) + "-01";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			startDate = df.format(calendar.getTime()) + "-01";
		}// 如果是年，默认为当年
		else if (query_index.equals("year")) {
			DateFormat df = new SimpleDateFormat("yyyy");
			startDate = df.format(new Date()) + "-01-01";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 1);
			endDate = df.format(calendar.getTime()) + "-01-01";
		}// 如果是季度，默认为当季度
		else if (query_index.equals("quartor")) {
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			String[] times = getQuarter(year, month);
			startDate = times[0];
			endDate = times[1];
		}// 如果是半年，默认为当半年
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
	 * 根据传入的时间类型和具体时间串 返回对应的时间区间
	 * 
	 * @param query_date
	 * @param twoMonth
	 *            是否返回两个月的区间
	 * @return
	 * @throws ParseException
	 */
	public static String[] getDateRegionByMonth(String query_date,
			String query_index) throws ParseException {
		String startDate = "";
		String endDate = "";
		// 如果没有传入日期，获取默认的时间区间
		if (StringUtils.isBlank(query_date)) {
			return DateUtil.getDefaultRegion(query_index);
		} else {
			// 月
			if (query_index.equals("month")) {
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(query_date));
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(df.parse(query_date));
				startDate = df.format(calendar.getTime()) + "-01";
				calendar2.add(Calendar.MONTH, 1);
				endDate = df.format(calendar2.getTime()) + "-01";
			}// 年
			else if (query_index.equals("year")) {
				startDate = query_date + "-01-01";
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(startDate));
				calendar.add(Calendar.YEAR, 1);
				endDate = df.format(calendar.getTime());
			}// 季度
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
			String query_index) throws ParseException {
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
			String query_index) throws ParseException {
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

	public static void test(String query_index) {
		if (StringUtils.isBlank(query_index) || query_index.equals("month")) {
			System.out.println("a");
		} else {
			System.out.println("b");
		}
	}

	/**
	 * 根据传入的时间类型和具体时间串 返回同比上期的时间区间
	 * 
	 * @param query_date
	 * @param t
	 * @return
	 * @throws ParseException
	 */
	public static String[] getDateRegionByLast(String query_date,
			String query_index) throws ParseException {
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
	 * 
	 * @param datestr
	 *            传入的时间串
	 * @param isHHmmss
	 *            是否要返回带时分秒的串
	 * @return
	 */
	public static String[] getDateRegionByDatePicker(String datestr,
			boolean isHHmmss) {
		String beginTime = "";
		String endTime = "";
		if (StringUtils.isNotBlank(datestr)) {
			if (!datestr.trim().equals("点击选择日期")) {
				if (datestr.indexOf("至") == -1) {
					beginTime = datestr.trim() + (isHHmmss ? " 00:00:00" : "");
					endTime = datestr.trim() + (isHHmmss ? " 23:59:59" : "");
				} else {
					beginTime = datestr.split("至")[0].trim()
							+ (isHHmmss ? " 00:00:00" : "");
					endTime = datestr.split("至")[1].trim()
							+ (isHHmmss ? " 23:59:59" : "");
				}
			}
		}
		// 如果没有至 说明选的是当天
		return new String[] { beginTime, endTime };
	}

	/**
	 * 将指定日期转换为文字描述格式
	 * 
	 * @param strData
	 *            只接收"yyyy-MM-dd"或"yyyy-MM-dd HH:mm:ss"格式的字符串 返回格式为:yyyy年M月d日
	 * @throws TxnException
	 */
	public static String getDateDes(String strDate) {
		if (StringUtils.isBlank(strDate)) {
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
	 * 将指定日期转换为文字描述
	 * 
	 * @param strDate
	 *            按sindex标示的格式的日期字符串
	 * @param sindex
	 *            输入格式序号
	 * @param dindex
	 *            返回格式序号
	 * @throws TxnException
	 */
	public static String getDateDes(String strDate, int sindex, int dindex) {
		if (sindex < 0 || dindex < 0 || sindex > dateFormat.length
				|| dindex > dateFormat.length) {
			return null;
		}
		if (StringUtils.isBlank(strDate)) {
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

	public static void main(String[] args) {
		String d = getDateDes("201312", 10, 14);
		System.out.println(d);
		// String[] times =
		// DateUtil.getDateRegionByDatePicker("2013-07-18   ",true);
		// System.out.println(times[0] + "------" + times[1]);
	}

}
