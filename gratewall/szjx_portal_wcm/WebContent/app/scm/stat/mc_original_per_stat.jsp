<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>

<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.components.stat.IStatResultsFilter" %>
<%@ page import="java.util.HashMap" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.text.DecimalFormat" %>

<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.components.stat.IStatResults" %>
<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.TimeRange" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>

<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%@ include file="static_common.jsp"%>
<%@ include file="public_stat_include.jsp"%>
<%@ include file="query_original_per_include.jsp"%>
<%
	String sMessage = "";
	if(CMyString.isEmpty(sData)){
		sMessage = "暂时没有数据!";
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>微博被转发数统计</title>
		<link rel="stylesheet" href="<%=CMyString.filterForHTMLValue(sBasePath)%>app/scm/css/public.css">
	<link rel="stylesheet" href="<%=CMyString.filterForHTMLValue(sBasePath)%>app/scm/css/stat_common.css">
	<script src="<%=CMyString.filterForHTMLValue(sBasePath)%>app/scm/js/jquery-1.7.2.min.js"></script>
	<script src="<%=CMyString.filterForHTMLValue(sBasePath)%>app/scm/js/scm_common.js"></script>
	<script src="<%=CMyString.filterForHTMLValue(sBasePath)%>app/scm/js/excanvas.compiled.js" type="text/javascript"></script>
	<script src="<%=CMyString.filterForHTMLValue(sBasePath)%>app/scm/js/highcharts.js" type="text/javascript"></script>
	<script src="<%=CMyString.filterForHTMLValue(sBasePath)%>app/scm/js/exporting.js" type="text/javascript"></script>
	<script src="<%=CMyString.filterForHTMLValue(sBasePath)%>app/scm/js/iframe_public.js"></script>
	<script src="<%=CMyString.filterForHTMLValue(sBasePath)%>app/scm/js/stat_chart.js"></script>

	<script>
		$(window).load(function(){
			/*画柱状图*/
			var columnContainer="columnContainer";//柱状图的容器ID
			var columnSubTitle="<%=CMyString.filterForJs(sMessage)%>";//柱状图的副标题
			var columnYAxis="";//y坐标呈现的值
			var columnXAxis = <%=sXLabels%>;
			var columnSeries = [<%=sData%>];
			var columnTitle = "<b style='font-size:20px'><%=CMyString.transDisplay(sTitle)%></b>";
			var columnUnit=" %";
			columnChart1(columnContainer,columnTitle,columnSubTitle,columnXAxis,columnYAxis,columnSeries,columnUnit,'<%=CMyString.filterForHTMLValue(sBaseHost)%>');

			/*画线形图*/
			var lineContainer="lineContainer";//线形图的容器ID
			var lineSubTitle="<%=CMyString.filterForJs(sMessage)%>";//线形图的副标题
			var lineYAxis="";//y坐标呈现的值
			var lineUnit=" %";
			var lineTitle =  "<b style='font-size:20px'><%=CMyString.transDisplay(sTitle)%></b>";
			var lineSeries = [<%=sData%>];
			var lineXAxis = <%=sXLabels%>;
			lineChart1(lineContainer,lineTitle,lineSubTitle,lineXAxis,lineYAxis,lineSeries,lineUnit,'<%=CMyString.filterForHTMLValue(sBaseHost)%>');
			setHeight();
		});
	</script>
</head>

<body>
	<div class="statSelectContainer marginLR18Px marginTop10Px">
		<div>平台：</div>
		<div class="first">
			<ul id="platSelect">
				<li <%if(sPlatform.equals("all")){%> class="selected"<%}%>><a href="<%=CMyString.filterForHTMLValue(sBaseUrl)%>/mc_original_per_stat.jsp?StatYear=<%=nStatYear%>&SCMGroupId=<%=nSCMGroupId%>&StatPlatform=all">全部</a></li>
				<%
					// 获取系统中的平台信息 TODO
					List oPlatformList = PlatformFactory.getPlatforms();
					for(int i= 0; i < oPlatformList.size(); i++){
						Platform oPlatform = (Platform)oPlatformList.get(i);
						if(oPlatform == null) continue;
						String sChineseName = oPlatform.getChineseName();
						String sName = oPlatform.getName();
				%>
					<li <%if(sPlatform.equals(sName)){%> class="selected"<%}%>><a href="<%=CMyString.filterForHTMLValue(sBaseUrl)%>/mc_original_per_stat.jsp?StatYear=<%=nStatYear%>&SCMGroupId=<%=nSCMGroupId%>&StatPlatform= <%=CMyString.filterForHTMLValue(sName)%>"><%=CMyString.transDisplay(sChineseName)%></a></li>
				<%}%>
			</ul>
			<div class="clearFloat"></div>
		</div>
		<div class="clearFloat"></div>
	</div>
	<div class="statSelectContainer marginLR18Px">
		<div>分组：</div>
		<div class="first">
			<ul id="groupSelect">
				<li <%if(nSCMGroupId == -1){%> class="selected"<%}%>><a href="<%=CMyString.filterForHTMLValue(sBaseUrl)%>/mc_original_per_stat.jsp?StatYear=<%=nStatYear%>&SCMGroupId=-1&StatPlatform=<%=CMyString.filterForHTMLValue(sPlatform)%>">全部</a></li>
				<%
				for(int i=0;i<oGroups.size();i++){
					SCMGroup oSCMGroup =(SCMGroup) oGroups.getAt(i);
					int nTempSCMGroupId = oSCMGroup.getId();
				%>
					<li <%if(oSCMGroup.getId() == nSCMGroupId){%> class="selected"<%}%>><a href="<%=CMyString.filterForHTMLValue(sBaseUrl)%>/mc_original_per_stat.jsp?StatYear=<%=nStatYear%>&SCMGroupId=<%=nTempSCMGroupId%>&StatPlatform=<%=CMyString.filterForHTMLValue(sPlatform)%>"><%=CMyString.filterForHTMLValue(oSCMGroup.getGroupName())%></a></li>
				<%
				}
				%>
			</ul>
			<div class="clearFloat"></div>
		</div>
		<div class="clearFloat"></div>
	</div>
	<div class="statSelectContainer marginLR18Px">
		<div>时间：</div>
		<div class="first">
			<ul id="timeSelect">
				<%for(int i=nFirstYear;i<=now.getYear();i++){%>
					<li <%if(nStatYear == i){%> class="selected"<%}%>><a href="<%=CMyString.filterForHTMLValue(sBaseUrl)%>/mc_original_per_stat.jsp?StatYear=<%=i%>&SCMGroupId=<%=nSCMGroupId%>&StatPlatform=<%=CMyString.filterForHTMLValue(sPlatform)%>"><%=i%></a></li>
				<%}%>
			</ul>
			<div class="clearFloat"></div>
		</div>
		<div class="clearFloat"></div>
	</div>
	<div class="statSource">数据来源于SCM平台</div>
	<div class="clearFloat"></div>
	<div id="columnContainer" class="statContainter"></div>
	<div id="lineContainer" class="statContainter"></div>
	<div class="statTableContainter">
		<div class="tableTitle">
			<div class="tableTitleFistDiv">
				<%=CMyString.transDisplay(sTitle)%>
				<div  class="export_excel" onclick="exportExcel(<%=nStatYear%>,<%=nSCMGroupId%>,'<%=sPlatform%>','export_account_original_per_stat.jsp')">Excel导出</div>
				<div style="clear:both"></div>
			</div>
			<div style="font-size:12px;color:#6D869F;text-align:center;"><%=CMyString.transDisplay(sMessage)%></div>
		</div>
		<%if(CMyString.isEmpty(sMessage)){%>
		<table id="statTable" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<td width="20%">帐号/月份</td>
				<%
					DecimalFormat df = new DecimalFormat( "0.00 "); 
					List oXLabels = oStatResults.getXlabels();
					if(oXLabels == null){
						return;
					}
					List oNewList = transXLabels(oXLabels);
					int nSize = oNewList.size();
					for(int i= 0; i< nSize; i++){
				%>
					<td><%=oNewList.get(i)%></td>
				<%
					}
				%>
			</tr>
			<%
				int nListSize = oAccountList.size();
				for(int i=0; i < nListSize; i++){
					String sAccount = (String)oAccountList.get(i);
					List oList = (List)oAccountDataMap.get(sAccount);
					if(oList == null){
						continue;
					}
			%>
				<tr>
					<td><%=CMyString.transDisplay(sAccount)%></td>
				<%
					for(int j=0; j< oList.size() && j < nSize; j++){
						Float oTempData = (Float)oList.get(j);
				%>
					<td><%=df.format(oTempData.floatValue())%>%</td>
				<%
					}
				%>
				</tr>
			<%
				}
			%>
		</table>
		<%}else{%>
		<div style="height:160px;"><div>
		<%}%>
	</div>
</body>
</html>