<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
		Recordset rs = context.getRecordset("record");
		//Recordset rs_ws = context.getRecordset("ws-record");
		DataBus chart = context.getRecord("chart");
		String chartXML = chart.getValue("chartXML");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/FusionCharts.js"></script>
    <title></title>
  </head>
  
  <body>
    <div style="background:#BBD0DB; height:450px;" id="chart"></div>
    
                    
	<script type="text/javascript">

   //var chart = new FusionCharts("<%=request.getContextPath()%>/FusionCharts/MSLine.swf", "ChartId", "100%", "450", "0", "0");
   var chart = new FusionCharts("<%=request.getContextPath()%>/FusionCharts/Pie2D.swf", "ChartId", "100%", "450", "0", "0");
   		   chart.setXMLData('<%=chartXML%>');		   
		   chart.render("chart");
		   </script>
  </body>
</html>
