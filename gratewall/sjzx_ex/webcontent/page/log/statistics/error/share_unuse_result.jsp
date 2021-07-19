<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/FusionCharts.js"></script>
<title>查询系统日志列表</title>
<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
	DataBus chart = context.getRecord("chart");
	String chartXML = chart.getValue("chartXML");
	DataBus select = context.getRecord("select-key");
	int show_num=5;
	if(select!=null){
      System.out.println("aaaaaaa:"+context.getRecordset("record").size());
      show_num=(context.getRecordset("record").size()<5 ? 5 : context.getRecordset("record").size());
	}
	int height= (show_num==5) ? 300 : show_num*40;
	String show_type=StringUtils.isBlank(select.getValue("show_type"))? "chart" :select.getValue("show_type");
%>
</head>

<script type="text/javascript">



// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>

<freeze:errors/>
<freeze:form action="/txn6020001">
<%if(show_type.equals("chart")){%>
<div id="chartContainer" style="height:<%=height%>px;"></div> 
<script>
    var myChart = new FusionCharts( "/FusionCharts/Bar2D.swf", "c001", "100%", "100%", "0", "1" );
    var xml="<%=chartXML%>";
    myChart.setXMLData(xml);  
    myChart.render("chartContainer");   
</script>
<%}else{%>
<freeze:grid property="record" checkbox="false" caption="未用服务统计" keylist="service_targets_id" width="95%"   fixrow="false">
      <freeze:cell property="service_targets_name" caption="服务对象名称" style="width:15%" />
      <freeze:cell property="serv_name" caption="服务名称" style="width:25%" />
      <freeze:cell property="last_use_time" caption="最近调用时间" style="width:15%" />
      <freeze:cell property="no_use_months" caption="未使用月数" style="width:10%;text-align:center" />
      <freeze:cell property="no_use_days" caption="未使用天数" style="width:10%;text-align:center" />
  </freeze:grid>
<%}%>
</freeze:form>
</freeze:body>
</freeze:html>
