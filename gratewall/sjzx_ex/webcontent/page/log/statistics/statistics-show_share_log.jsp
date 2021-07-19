<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
	DataBus select = context.getRecord("select-key");
	String query_index = select.getValue("query_index");
	String statistical_granularity = select.getValue("statistical_granularity");
	Recordset rs = context.getRecordset("record");
	DataBus chart = context.getRecord("chart");
	String chartXML = chart.getValue("chartXML");
	String show_type=StringUtils.isBlank(select.getValue("show_type"))? "chart" :select.getValue("show_type");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<freeze:html width="650" height="350">
  <head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/FusionCharts.js"></script>
    <title></title>
    <script type="text/javascript">
    
    function showQuery(type)
    {
        if(type=="table")
        {
            document.getElementById('chart').style.display = 'none';
            document.getElementById('table').style.display = "";
        }
        else
        {
            document.getElementById('chart').style.display = "";
            document.getElementById('table').style.display = 'none';
        }
    }
    </script>
  </head>

  
<freeze:body >

<freeze:errors/>
<freeze:form action="/txn6020002">
<%if(show_type.equals("chart")){%>	
		    <div style="background:#BBD0DB; height:400px;" id="chart"></div>
    <script type="text/javascript">
      var chart = new FusionCharts("<%=request.getContextPath()%>/FusionCharts/MSColumn3DLineDY.swf", "ChartId", "100%", "400", "0", "0");
   		   chart.setXMLData("<%=chartXML%>");		   
		   chart.render("chart");
		   </script>
	<%}else{%>
		
		
     <freeze:grid property="record" checkbox="false" caption="�������ͳ��" keylist="mon" width="100%" navbar="bottom" fixrow="false">
      <freeze:cell property="mon" caption="����" style="width:15%" align="center"/>
      <%if(query_index.indexOf("���ô���")>=0) {%>
      <freeze:cell property="s" caption="���ô���(��)" style="width:15%" align="center"/>
      <%} 
      if(query_index.indexOf("����������")>=0){ %>
      <freeze:cell property="n" caption="����������(��)" style="width:15%" align="center"/>
      <%} 
       if(query_index.indexOf("ƽ����Ӧʱ��")>=0){ %>
      <freeze:cell property="ts" caption="ƽ����Ӧʱ��(��)" style="width:15%" align="center"/>
      <%} %>
      
     </freeze:grid>   
<%}%>            
	
</freeze:form>
</freeze:body>
</freeze:html>