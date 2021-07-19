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
<title>��ѯϵͳ��־�б�</title>
<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
	DataBus chart = context.getRecord("chart");
	String chartXML = chart.getValue("chartXML");
	DataBus select = context.getRecord("select-key");
	int show_num=5;
	if(select!=null){
      show_num=(context.getRecordset("record").size()<10 ? 10 : context.getRecordset("record").size());
	}
	int height= (show_num==10) ? 350 : show_num*30;
	System.out.println(height);
	String show_type=StringUtils.isBlank(select.getValue("show_type"))? "chart" :select.getValue("show_type");
	String query_index = select.getValue("query_index");
%>
</head>

<script type="text/javascript">
// ����������ӣ�ҳ�������ɺ���û���ʼ������
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
	<%if(query_index.equals("target_number")||query_index.equals("target_quantity")){%>
		var myChart = new FusionCharts( "/FusionCharts/Bar2D.swf", "c001", "100%", "100%", "0", "1" );
	<%}else{%>
		var myChart = new FusionCharts( "/FusionCharts/Pie3D.swf", "c001", "100%", "100%", "0", "1" );
	<%}%>
    	var xml="<%=chartXML%>";
    	myChart.setXMLData(xml);  
    	myChart.render("chartContainer");   
</script>
<%}else{%>
	<%if(query_index.equals("target_number")){%>
		<freeze:grid property="record" checkbox="false" caption="����ֲ�ͳ��" keylist="" width="95%"  rowselect="false" fixrow="false">
      		<freeze:cell property="name" caption="�������" style="width:50%" align="center"/>
      		<freeze:cell property="count" caption="��Ӧ�������" style="width:50%"  align="center"/>
  		</freeze:grid>
	<%}else if(query_index.equals("target_quantity")){%>
		<freeze:grid property="record" checkbox="false" caption="����ֲ�ͳ��" keylist="" width="95%"  rowselect="false" fixrow="false">
      		<freeze:cell property="name" caption="�������" style="width:50%" align="center"/>
      		<freeze:cell property="count" caption="����������" style="width:50%"  align="center"/>
  		</freeze:grid>
	<%}else if(query_index.equals("type_number")){%>
		<freeze:grid property="record" checkbox="false" caption="����ֲ�ͳ��" keylist="" width="95%"  rowselect="false" fixrow="false">
      		<freeze:cell property="name" caption="�����������" style="width:50%" valueset="��Դ����_�����������" align="center"/>
      		<freeze:cell property="count" caption="��Ӧ�������" style="width:50%"  align="center"/>
  		</freeze:grid>
	<%}else{%>
		<freeze:grid property="record" checkbox="false" caption="����ֲ�ͳ��" keylist="" width="95%"  rowselect="false" fixrow="false">
      		<freeze:cell property="name" caption="�����������" style="width:50%" valueset="��Դ����_�����������" align="center"/>
      		<freeze:cell property="count" caption="����������" style="width:50%" align="center"/>
  		</freeze:grid>
	<%}%>



<%}%>
</freeze:form>
</freeze:body>
</freeze:html>
