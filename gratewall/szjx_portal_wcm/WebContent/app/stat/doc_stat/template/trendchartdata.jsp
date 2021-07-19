<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.IStatResultsFilter" %>
<%@ page import="com.trs.components.stat.IStatResults" %>
<%@ page import="com.trs.components.stat.IStatResult" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>

<%
	//获取时间
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];
	
	// 传入时间步长单位
	int nTimeStep = currRequestHelper.getInt("TimeStep",1);

	//其它信息/参数处理
	//int nSiteId = currRequestHelper.getInt("SiteId", 0);
	//WebSite currSite = WebSite.findById(nSiteId);
	//if(currSite == null) throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,"站点没有找到.");
%>
<%
	//TODO 定义SQL.
	String[] pStatSQL = new String[] {
		#STAT_SQL#
	};
	StatAnalysisTool tool = new StatAnalysisTool(pStatSQL);
	
	IStatResults result = tool.stat(arrTime[0], arrTime[1], nTimeStep);

	// 按来源过滤
	final String sSearchValue = CMyString.showNull(currRequestHelper.getString("SearchValue"));
	if(!CMyString.isEmpty(sSearchValue)){
		result = result.filter(new IStatResultsFilter() {
			public boolean accept(String sKey, IStatResults statResults) {
				//TODO 具体的过滤逻辑,需要的数据返回true
				return sKey != null && sKey.indexOf(sSearchValue) != -1;
			}
		});
	}

	// 获取主体，做分页需要以这个主体做为总数
	List mainObjList = result.list();
	
		//构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",10);
	int nCurrPage = currRequestHelper.getInt("CurrPage",1);
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nCurrPage);
	currPager.setItemCount(mainObjList.size());

	// 设置分页信息到 header
	response.setHeader("CurrPage",String.valueOf(nCurrPage));
	response.setHeader("PageSize",String.valueOf(nPageSize));
	response.setHeader("Num",String.valueOf(mainObjList.size()));

	StringBuffer elements = new StringBuffer(2000);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {		
		String sKey = String.valueOf(mainObjList.get(i-1));
		if(sKey == null) continue;
		List list = result.getResult(1,sKey);
		
		for (int j = 0; j < list.size(); j++){
			int num = ((Integer)list.get(j)).intValue();
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
		}
		
		elements.append(","+getTrendChartValue("#PREFIX_DESC#",sKey,list.toString()));
	}
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");

	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10>0?y_max/10:1;
	y_max += y_step;
	out.clear();
	String x_labels = result.getXlabels().toString();
	// 设置走势图相关信息
	response.setHeader("XlabelsNum",String.valueOf(result.getXlabels().size()));
	response.setHeader("ChartType","trendchart");
	String sElements = elements.length()>0?elements.substring(1):"";
	out.print(getTrendChartJson(sFlashDesc,sElements,x_labels,y_max,y_step));
%>