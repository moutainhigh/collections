<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import=" com.trs.components.stat.StatAnalysisTool"%>
<%@ page import=" com.trs.components.stat.IStatResults"%>
<%@ page import="com.trs.components.wcm.resource.Status"%>
<%@ page import="com.trs.cms.CMSConstants"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.trs.infra.util.CMyDateTime"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%

	String[] pStatSQL = new String[] {
		// 统计总量，结果中只有两列数据
		"Select count(*) DataCount ,SiteId from WCMDocument "
				// 统计指定时间段的数据
				+ " Where CrTime>= ${STARTTIME}" + " And CrTime<= ${ENDTIME}"
				+ " and DocStatus>0 and CHNLID>0"
				+ "group by SiteId",
		// 获取某一段时间创建的不同文档状态的总数
		"Select count(*) DataCount ,SiteId,DocStatus from WCMDocument "
				// 统计指定时间段的数据
				+ " Where CrTime>= ${STARTTIME}" + " And CrTime<= ${ENDTIME}"
				+ " and DocStatus>0 and CHNLID>0" 
				+ "group by SiteId ,DocStatus",
		// 获取某一段时间创建的不同文档类型的总数
		"Select count(*) DataCount ,SiteId,DocForm from WCMDocument "
				// 统计指定时间段的数据
				+ " Where CrTime>= ${STARTTIME}" + " And CrTime<= ${ENDTIME}"
				+ " and DocStatus>0 and CHNLID>0" 
				+ "group by SiteId ,DocForm"
		};
	String[] arrTime=getDateTimeFromParame(currRequestHelper);
	// 传入时间步长单位
	int nTimeStep = currRequestHelper.getInt("TimeStep",1); 
	int nSqlIndex = currRequestHelper.getInt("SqlIndex",1); 
	// 获取文档状态值或者文档类型值
	String sValue = currRequestHelper.getString("value"); 
	StatAnalysisTool tool = new StatAnalysisTool(pStatSQL);
	IStatResults result = tool.stat(arrTime[0], arrTime[1], nTimeStep);
	// 获取主体，做分页需要以这个主体做为总数
	List mainObjList = result.list();
	
	StringBuffer elements = new StringBuffer(2000);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	// TODO ----需要做分页处理----
	// TODO ----需要做分页处理----
	for (int i = 0; i < mainObjList.size(); i++){
		String sKey = String.valueOf(mainObjList.get(i));
		List list = new ArrayList();
		// 统计总量时需要做特殊处理
		if(nSqlIndex==1)
			 list = result.getResult(nSqlIndex,sKey);
		else
			list = result.getResult(nSqlIndex,new String[]{sKey,sValue});
		for (int j = 0; j < list.size(); j++){
			int num = ((Integer)list.get(j)).intValue();
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
		}
		WebSite currSite = WebSite.findById(Integer.parseInt(sKey));
		
		if(currSite==null)continue;
		String sSiteName = currSite.getName();
		elements.append(","+getTrendChartValue("站点",sSiteName,list.toString()));
	}
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");

	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10>0?y_max/10:1;
	y_max += y_step;
	out.clear();
	List x_labels= result.getXlabels();
	// 设置走势图相关信息
	response.setHeader("XlabelsNum",String.valueOf(x_labels.size()));
	response.setHeader("ChartType","trendchart");
	String sElements = elements.length()>0?elements.substring(1):"";
	out.print(getTrendChartJson(sFlashDesc,sElements,x_labels.toString(),y_max,y_step));
%>