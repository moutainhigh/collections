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
<%@include file="../../include/public_server.jsp"%>
<%@include file="../include/static_common.jsp"%>
<%

	String[] pStatSQL = new String[] {
		// 获取某一段时间创建的不同文档类型的总数
		"Select count(*) DataCount ,'DEFAULT' from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>= ${STARTTIME}" + " And CrTime<= ${ENDTIME}"
				+ " and DocStatus>0 and CHNLID>0" 
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
		};
	String[] arrTime=getDateTimeFromParame(currRequestHelper);
	int nTimeStep = currRequestHelper.getInt("TimeStep",1); 
	StatAnalysisTool tool = new StatAnalysisTool(pStatSQL);
	IStatResults result = tool.stat(arrTime[0], arrTime[1], nTimeStep);
	List mainObjList = result.list();
	
	StringBuffer elements = new StringBuffer(2000);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	for (int i = 0; i < mainObjList.size(); i++){
		List list = result.getResult(String.valueOf(mainObjList.get(i)));
		for (int j = 0; j < list.size(); j++){
			int num = ((Integer)list.get(j)).intValue();
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
		}
		elements.append(","+getTrendChartValue(list.toString()));
	}
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");

	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10>0?y_max/10:1;
	y_max += y_step;
	out.clear();
	response.setHeader("XlabelsNum",String.valueOf(result.getXlabels().size()));
	response.setHeader("ChartType","trendchart");
	String x_labels = result.getXlabels().toString();
	String sElements = elements.length()>0?elements.substring(1):"";
	out.print(getTrendChartJson(sFlashDesc,sElements,x_labels,y_max,y_step));
%>