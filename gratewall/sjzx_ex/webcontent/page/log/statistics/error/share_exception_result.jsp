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
    DataBus record = context.getRecord("record");
	DataBus chart = context.getRecord("chart");
	String chartXML = chart.getValue("chartXML");
	DataBus select = context.getRecord("select-key");
	int show_num=5;
	if(select!=null){
      show_num=(context.getRecordset("record").size()<10 ? 10 : context.getRecordset("record").size());
	}
	int height= (show_num==10) ? 350 : show_num*40;
	String show_type=StringUtils.isBlank(select.getValue("show_type"))? "chart" :select.getValue("show_type");
%>
</head>

<script type="text/javascript">
function toDetail(code){
	alert(code);
}



// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body >

<freeze:errors/>
<freeze:form action="/txn6020001">
<%
if(!record.isEmpty()){
	if(show_type.equals("chart")){%>
	<div id="chartContainer" style="height:<%=height%>px;" align="center"></div> 
	<script>
	    var myChart = new FusionCharts( "/FusionCharts/Pie2D.swf", "c001", "100%", "100%", "0", "1" );
	    var xml="<%=chartXML%>";
	    myChart.setXMLData(xml);  
	    myChart.render("chartContainer");   
	</script>
<%}else{%>
	<freeze:grid property="record" checkbox="false" caption="未用服务统计"  width="95%"  fixrow="false">
	      <freeze:cell property="error_name" caption="异常原因描述" style="width:15%" align="center"/>
	      <freeze:cell property="error_code" caption="异常代码" style="width:15%" align="center"/>
	      <freeze:cell property="error_count" caption="异常发生数量" style="width:15%" align="center"/>
	  </freeze:grid>
<%}}else{%>
  <div style="font-size: 14px;text-align: center;">查无相关数据！</div>
<%}%>
</freeze:form>
</freeze:body>
</freeze:html>
