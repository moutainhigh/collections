package cn.gwssi.resource;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtil {
	public final static String YYYYMMDDHHMISS="yyyy-MM-dd HH:mm:ss";
	public final static DecimalFormat df = new DecimalFormat("0");

	/**
	 * 判断是否为时间类型的字符串
	 * @param str
	 * @param flag
	 * @return
	 */
	public static boolean isValidDate(String str,String flag) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；yyyy/MM/dd HH:mm:ss
		SimpleDateFormat format = new SimpleDateFormat(flag);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	/**
	 * 时间转换字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
		//formatter.getDateInstance(DateFormat.DATE_FIELD);
	    return formatter.format(date);
	}
	
	public static String getYesterday(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    return formatter.format(date);
	}
	
	/**
	 * 时间格式转换
	 * @param str
	 * @param beferStr
	 * @param afterStr
	 * @return
	 */
	public static String strToString(String str,String beferStr,String afterStr) {
		SimpleDateFormat formatter=new SimpleDateFormat(beferStr);  
		SimpleDateFormat formatter1=new SimpleDateFormat(afterStr);
		String returnStr = null;
		try {
			returnStr = formatter1.format(formatter.parse(str));
		} catch (ParseException e) {
			returnStr = "返回时间格式错误！";
		}
	    return returnStr;
	}
	
	/**
	 * 时间格式判断
	 * @param pDateObj
	 * @return
	 */
	public static boolean checkValidDate(String pDateObj) {
		boolean ret = true;
		if (pDateObj == null || pDateObj.length() != 8) {
			ret = false;
		}
		try {
			int year = new Integer(pDateObj.substring(0, 4)).intValue();
			int month = new Integer(pDateObj.substring(4, 6)).intValue();
			int day = new Integer(pDateObj.substring(6)).intValue();
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false); // 允许严格检查日期格式
			cal.set(year, month - 1, day);
			cal.getTime();// 该方法调用就会抛出异常
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	
	/**
	 * 时间转换字符串
	 * @param date
	 * @return
	 */
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String str = format.format(date);
	   return str;
	} 
	
	public static long strToLong(String characterString,String dateFormat){
		long millionSeconds=0;
		if(StringUtils.isNotBlank(characterString)){
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			try {
				millionSeconds = sdf.parse(characterString).getTime();//毫秒
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return millionSeconds;
	}
	
	public static long calToUTC(String characterString,String dateFormat) {
		//1、取得时间Calendar 
		Calendar cal = Calendar.getInstance();
		//2、设定对应的时间  //2015-05-28
		if(!StringUtils.isNotBlank(characterString)){
			characterString="1970-01-01 00:00:00";
		}
		if(characterString.trim().length()==10){
			dateFormat = "yyyy-MM-dd";
		}
		SimpleDateFormat formatter=new SimpleDateFormat(dateFormat);
		long returnmillionSeconds=0L;
		try {
			//3、获得当前时间
			cal.setTime(formatter.parse(characterString));
			long millionSeconds = cal.getTimeInMillis();
			//4、获得UTC时间
			String utc = DateFormatUtils.formatUTC(millionSeconds,dateFormat);
			cal.setTime(formatter.parse(utc));
			long utcMmillionSeconds = cal.getTimeInMillis();
			
			returnmillionSeconds = millionSeconds+(millionSeconds-utcMmillionSeconds);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnmillionSeconds;
	}
	
	public static void main(String[] args) throws ParseException {
		//System.out.println("123.345.777".replace(".", "-"));
		//System.out.println(DateUtil.isValidDate("1999.04.01","yyyy.MM.dd"));
		/*calToUTC("2015-07-04 00:00:01",YYYYMMDDHHMISS);
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");//开始
		sbf.append("chart:{renderTo: 'container',type: 'spline'},");
		sbf.append("title:{text:title},");
		sbf.append("credits:{enabled: false,href: 'http://www.msnui.tk/Admin',text: '微源网络科技'},");
		sbf.append("yAxis: [{title: {text: 'GOOGL'}}],");
		sbf.append("tooltip:{xDateFormat: '%Y-%m-%d %H:%M:%S',enabled:false},");
		sbf.append("xAxis:{tickPixelInterval: 200,title:{text:''},labels: {  formatter: function() {var vDate=new Date(this.value);return vDate.getFullYear()+'-'+(vDate.getMonth()+1)+'-'+vDate.getDate();},align: 'center'}},");
		
		sbf.append("}");//结束
*/		
		System.out.println(Conts.NDBGJBXX);
	}
	
	/*在我们平时开发过程中，经常需要进行时间转换，这里主要介绍本地时间转UTC时间。
	如果只是本地当前时间转UTC时间，如下方式即可：
	//1、取得本地时间：
	Calendar cal = Calendar.getInstance();
	//2、取得时间偏移量：
	int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
	//3、取得夏令时差：
	int dstOffset = cal.get(Calendar.DST_OFFSET);
	//4、从本地时间里扣除这些差量，即可以取得UTC时间：
	cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
	//之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
	System.out.println("UTC:"+new Date(cal.getTimeInMillis()));
	大家看到上面这一段，就有疑问，如果将上面的cal直接传入对应的时间，不是就可以直接转换任意时间点的本地时间了么，然而实际情况是什么样子的呢？
	如果本地所在时区有冬夏令时切换，由于Calendar本身会根据冬夏令时的时间会做微调整，就可能会导致我们获得的时间跟我们的预想的时间提前或者延后一个小时，那么怎么来解决呢？
	Apache Common Lang包中提供了方法：
	//1、取得时间Calendar 
	Calendar cal = Calendar.getInstance();
	//2、设定对应的时间
	cal.setTime(strToDate(args[0], format));
	//3、获得UTC时间
	System.out.println(DateFormatUtils.formatUTC(cal.getTimeInMillis(), format));
	还有一种做法是不管冬夏令时的切换，直接以当前时间所在的令时进行UTC时间的转换：
	//1、获得当前所在的令时偏移量-(zoneOffset + dstOffset)
	Calendar cal = Calendar.getInstance();
	int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
	int dstOffset = cal.get(Calendar.DST_OFFSET);        
	//2、生成Calendar，并传入对应的时间，使用GMT时区进行时间计算，防止令时切换导致的微调整
	Calendar cal1 = Calendar.getInstance();
	cal1.setTimeZone(TimeZone.getTimeZone(TIMEZONE_GMT));
	cal1.setTime(strToDate(args[0], FORMAT_1));
	cal1.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
	//3、获取对应的UTC时间
	System.out.println(dateToString(cal1.getTime(), FORMAT_1));
	这样，就可以得到对应UTC时间。
	其他
	获得当前时区：
	//方法1
	Calendar calendar = Calendar.getInstance();
	TimeZone tz = calendar.getTimeZone();
	System.out.println(tz.getID());
	//方法2
	TimeZone my=TimeZone.getDefault();
	System.out.println(my.getID());
	获得支持的时区列表：
	//取得java所支持的所有时区ID
	String[] ids=TimeZone.getAvailableIDs();
	System.out.println(Arrays.toString(ids));
	先到这里，有其他再补充。*/
}
