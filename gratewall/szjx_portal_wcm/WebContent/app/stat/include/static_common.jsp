<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.database.DBType" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>

<%!
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(this.getClass());

	private DebugTimer timer = new DebugTimer();

	/*
	*  通过文档的状态值，获取文档状态对应的名称
	*/
	public String getDOCStatusName(int _stat){
		String sName ="";
		switch(_stat){
			case 0: sName = LocaleServer.getString("static_common.unknow","未知");break;
			case 1: sName = LocaleServer.getString("static_common.new.doc","新稿");break;
			case 2: sName = LocaleServer.getString("static_common.edited","已编");break;
			case 3: sName = LocaleServer.getString("static_common.return","返工");break;
			case 10: sName = LocaleServer.getString("static_common.sent","已发");break;
			case 15: sName = LocaleServer.getString("static_common.denied","已否");break;
			case 16: sName = LocaleServer.getString("static_common.signed","已签");break;
			case 18: sName = LocaleServer.getString("static_common.check","正审");break;
		}
		return sName;
	}
	/*
	*  通过文档的类型值，获取文档类型对应的文字描述
	*/
	public String getDOCFormName(int _stat){
		String sName ="";
		switch(_stat){
			case 0: sName = LocaleServer.getString("static_common.unknow.type","未知类型");break;
			case 1: sName = LocaleServer.getString("static_common.word","文字型");break;
			case 2: sName = LocaleServer.getString("static_common.image","图片型");break;
			case 3: sName = LocaleServer.getString("static_common.audio","音频型");break;
			case 4: sName = LocaleServer.getString("static_common.video","视频型");break;
		}
		return sName;
	}
	/*
	*  通过传入的时间参数，获取当前
	*/
	public String[] getDateTimeFromParame(RequestHelper currRequestHelper)throws Exception{
		try{
			String sStartTime = currRequestHelper.getString("StartTime");
			String sEndTime = currRequestHelper.getString("EndTime");
			String sTimeItem = currRequestHelper.getString("TimeItem");
			// 如果没有传入TimeItem，默认是无限
			if(CMyString.isEmpty(sTimeItem))
				sTimeItem = "0";
			CMyDateTime dtQueryStart = CMyDateTime.now(),dtQueryEnd=CMyDateTime.now();
			if(CMyString.isEmpty(sStartTime)){
				if("0".equals(sTimeItem))//不限
					sStartTime = "2000-01-01 00:00:00";
				else if("1".equals(sTimeItem))//今日
					sStartTime = dtQueryStart.getDateTimeAsString("yyyy-MM-dd 00:00:00");
				else if("2".equals(sTimeItem))//当月
					sStartTime = dtQueryStart.getDateTimeAsString("yyyy-MM-01 00:00:00");
				else if("3".equals(sTimeItem)){//当季
					int month = dtQueryStart.getMonth();
					String sMonth="";
					if(month>=10)sMonth="10";
					else if(month>=7)sMonth="07";
					else if(month>=4)sMonth="04";
					else sMonth="01";
					sStartTime = dtQueryStart.getDateTimeAsString("yyyy-"+sMonth+"-01 00:00:00");
				}else if("4".equals(sTimeItem)){//当年
					sStartTime = dtQueryStart.getDateTimeAsString("yyyy-01-01 00:00:00");
				}
			}
			if(CMyString.isEmpty(sEndTime))
				sEndTime = dtQueryEnd.getDateTimeAsString(CMyDateTime.DEF_DATETIME_FORMAT_PRG);
			return new String[]{sStartTime,sEndTime};
		}catch(Exception ex){
			throw new Exception(LocaleServer.getString("static_common.getTime.error","获取时间出现错误！"),ex);
		}
	}

	/**
	*  在走势图上如果没有传入步长，需要获取最佳的步长
	*  
	*/
	public int getBestTimeStep(RequestHelper currRequestHelper)throws WCMException{
		long longTime = 0;
		int timeStep = 0;
		try{
			timeStep = currRequestHelper.getInt("TimeStep",0);
			if(timeStep>0) return timeStep;
			String[] arrTimes = getDateTimeFromParame(currRequestHelper);
			String sStartTime = arrTimes[0],sEndTime = arrTimes[1];
			CMyDateTime start = new CMyDateTime(),end = new CMyDateTime();
			start.setDateTimeWithString(sStartTime);
			end.setDateTimeWithString(sEndTime);
			longTime = end.compareTo(start);
		}catch(Exception ex){
			throw new WCMException(LocaleServer.getString("static_common.getTime.exception","获取时间出现异常"),ex);
		}
		float dayNum = (float)longTime/CMyDateTime.ADAY_MILLIS;
		
		// 如果大于5年，则以年为单位
		if(dayNum/365>5)timeStep = 3;
		// 如果大于2年，则以季度为单位
		else if(dayNum/365>2)timeStep = 4;
		// 如果大于70天，则以月为单位
		else if(dayNum/365>0.2)timeStep = 2;
		// 否则使用天为单位
		else timeStep = 1;

		return timeStep;
	}
	/**
	*  颜色数组
	*/
	private final static String[] COLORS = {
		"0xCC9933", "0x006666", "0x3399FF", "0x993300",
		"0xBBBB55", "0xCC6600", "0x9999FF", "0x0066CC",
		"0x663366", "0x9999CC", "0xAAAAAA", "0x669999",
		"0x99CC33", "0xFF9900", "0x999966", "0x66CCCC",
		"0x336699", "0x88AACC", "0x999933", "0x666699",
		"0xAAAA77", "0x666666", "0xFFCC66", "0x6699CC",		
		"0x99CCCC", "0x999999", "0xFFCC00", "0x009999",		
		"0x339966", "0xCCCC33", "0x996699", "0x669966" 
	};
	private final static int COLORS_LEN = 32;
	/*
	*  获取随机颜色
	*/
	public String getRandomColor(){
		int nIndex = (int)Math.ceil(Math.random()*COLORS_LEN)-1;
		return COLORS[nIndex];
	}
	
	/*
	*  把所有的图形的基本头部样式放在统一控制这里
	*/
	public String getChartTitleStyle(){
		return "\"{font-size:20px;color:#222222;font-family:微软雅黑;text-align:center;}\"";
	}

	/*
	*  获取柱状图的JSON字符串
	*  params @   
	*  _sTitle:表示柱状图的名称
	*  _sValues:表示鼠标放上去以后显示出来的信息
	*  _sXlaybel:表示下标显示出来的信息
	*  _nYmax:表示Y轴的最大值
	*  _nYstep:表示Y轴的每一格单位的大小，如：为2表示每隔两个单位为一个标识
	*/
	public String getBarChartJson(String _sTitle,String _sValues,String _sXlaybel,int _nYmax,int _nYstep){
		if(_sTitle==null)_sTitle="";
		return 		"{\"title\":{\"text\":\""+_sTitle+"\",\"style\":\"{font-size:20px;color:#222222;font-family:微软雅黑;text-align:center;}\"},"
		+
		"\"y_legend\":{\"text\": \" \",\"style\": \"{color: #736AFF; font-size: 12px;}\"},"
		+
		"\"elements\":[{\"type\":\"bar_3d\",\"font-size\":20,\"alpha\":1,\"colour\":\"#27547f\",\"values\" :["+_sValues+"]}],"
		+
		"\"x_axis\":{\"stroke\":1,\"tick-height\":5,\"colour\":\"#7c7c7c\",\"font-size\":20, \"labels\":{\"size\":12,\"colour\":\"#993300\",\"labels\": ["+_sXlaybel+"]},\"3d\":5},"
		+
		"\"y_axis\":{\"stroke\":2,\"tick-length\": 0,\"colour\":\"#b53636\",\"offset\":0,\"max\":"+_nYmax+",\"steps\":"+_nYstep+"}}";
	}
	
	/*
	*  获取饼状图的JSON字符串
	*/
	public String getPieChartJson(String _sTitle,String _sColor,String _sValues){
		if(_sTitle==null)_sTitle="";
		return "{\"title\":{ \"text\":\""+_sTitle+"\",\"style\":\"{font-size:20px;color:#222222;font-family:微软雅黑;text-align:center;}\"},"
		+
		"\"elements\":[{\"type\":\"pie\",\"colours\":["+_sColor+"],\"alpha\":0.8,\"font-size\": 12,\"tip\":\"#label#<br>总共(#total#)<br>百分比(#percent#)<br>\",\"border\":100,\"start-angle\": 35,\"values\" :["+_sValues+"]}]}";
	}
	
	/*
	*  获取走势图的JSON字符串
	*  
	*/
	public String getTrendChartJson(String _sTitle,String _sValues,String _sXlaybel,int _nYmax,int _nYstep){
		if(_sTitle==null)_sTitle="";
		return "{\"title\":{\"text\":\""+_sTitle+"\",\"style\":\"{font-size:20px;color:#222222;font-family:微软雅黑;text-align:center;}\"},"
		+"\"elements\":["+_sValues+"],"
		+"\"x_axis\":{\"labels\":{\"rotate\": \"-30\",\"labels\":"+_sXlaybel+"}},"
		+"\"y_axis\":{\"max\":"+_nYmax+",\"steps\":"+_nYstep+"}}";
	}
	
	/*
	*  获取走势图的值，用于一个图上有多条走势图的情况，这个需要传入这个对象的类型，sTitle，对象的名称sName和这个对象的一系列值。例如是站点对象传入的参数可能是getTrendChartValue("站点","演示站点","[45,12,42,16]") 注意值中需要有中括号
	*  
	*/
	public String getTrendChartValue(String _sTitle,String _sName,String sValue){
		return "{\"type\":\"line\",\"text\":\""+_sName+"\",\"width\":1,\"colour\":\""+getRandomColor()+"\",\"values\":"+sValue+",\"dot-style\":{\"tip\":\""+_sTitle+":"+_sName+"<br>数值：#val#<br>时间：#x_label#\"}}";
	}
	/*
	*  用于做总量的走势图，只有一条线，只需要传入值序列即可，
		例如"[12,41,52,56,15,12]" 注意需要带上中括号
	*
	*/
	public String getTrendChartValue(String sValue){
		return "{\"type\":\"line\",\"width\":1,\"colour\":\"#3399FF\",\"values\":"+sValue+",\"dot-style\":{\"tip\":\"数值：#val#<br>时间：#x_label#\"}}";
	}
	
	/**
	*替换时间约束
	*/
	private String formatTimeWhere(String sTimeWhere, String[] sTimeSpan)throws WCMException{
		CMyDateTime startDate = new CMyDateTime();
		CMyDateTime endDate = new CMyDateTime();

		try {
			startDate.setDateTimeWithString(sTimeSpan[0]);
			endDate.setDateTimeWithString(sTimeSpan[1]);
		} catch (Exception e) {
			throw new WCMException(LocaleServer.getString("static_common.getTime.time.exception","解析统计时间出现异常"), e);
		}

		// 格式化时间参数,从而屏蔽底层数据库的差异
		DBType currDBType = DBManager.getDBManager().getDBType();
		String sStartTime = currDBType.sqlDate(startDate.toString());
		String sEndTime = currDBType.sqlDate(endDate.toString());

		// 构造需要进行SQL时间标识替换的变量
		Map dateMap = new HashMap();
		dateMap.put("STARTTIME", sStartTime);
		dateMap.put("ENDTIME", sEndTime);

		// 获取实际统计的SQL
		return CMyString.parsePageVariables(sTimeWhere, dateMap);
	}
%>