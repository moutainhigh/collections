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
	DataBus shareInfo = context.getRecord("shareInfo");
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

<script language="javascript">
function toDetail(code){
	alert(code);
}



// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
 /* var rowids=document.getElementsByName('span_charts:@rowid');
  for(var i=0;i<rowids.length;i++){
	  document.getElementsByName('span_charts:@rowid')[i].innerHTML=(i+1);
  }*/
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body >

<freeze:errors/>
<freeze:form action="/txn6020001">
<div>
<%
 if(!shareInfo.isEmpty()){
%>
<div style="width: 95%;text-align: center;padding: 5px;font-size: 14px;font-weight: bold;"><freeze:out value="${select-key.date_remark}"/>[<freeze:out value="${select-key.service_targets_name}"/>]共享情况</div>
<table style="width: 100%">
<tr><td>
<table style="width: 100%" cellspacing="0" cellpadding="0" align="left"  border='1' class="frame-body">
   <tr>
    <tr>
	  <th rowspan="4" class="odd12" width="200px;" valign="middle">
	     共享总量：
		  <span style="font-size: 24px;color: #3599FF;">
		    <freeze:out value="${shareInfo.sum_num}"/>
		  </span><span style="font-size: 18px;color: #3599FF;">条</span>
	 </th>
	  <td class="odd12" align="right">本期服务天数：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.exec_day}"/>天</td>
	  <td class="odd12" align="right">上期共享数据量：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.last_sum_num}"/>条</td>
    </tr>
    <tr>
      <td class="odd12" align="right">平均响应时间：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.avg_time}"/>秒</td>
	  <td class="odd12" align="right">响应请求次数：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.sum_count}"/>次	</td>
    </tr>
     <tr>
	  <td class="odd12" align="right">出错次数：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.error_count}"/></td>
	  <td class="odd12" align="right">出错比例(%)：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.error_rate}"/>%</td>
    </tr>
    <tr>
	  <td class="odd12" align="right">最早响应请求时间：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.first_use_time}"/></td>
	  <td class="odd12" align="right">最后响应请求时间：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.last_use_time}"/></td>
    </tr>
    <tr>
      <th rowspan="2" class="odd12" width="200px;">
		  环比：
		 <span style="font-size: 18px;color: #3599FF;">
		   <freeze:out value="${shareInfo.num_rate}"/>%
		 </span><br/>
		  同比：
		 <span style="font-size: 18px;color: #3599FF;">
		  <freeze:out value="${shareInfo.lastyear_num_rate}"/>%
		 </span>
		
	  </th>
	  <td class="odd12" align="right">平均共享数据量：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.avg_num}"/>条</td>
	  <td class="odd12" align="right">环比增幅：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.num_rate}"/>%</td>
      
    </tr>
     <tr>
      <td class="odd12" align="right">同期共享数据量：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.lastyear_sum_num}"/>条</td>
	  <td class="odd12" align="right">同比增幅：</td>
	  <td class="odd12"><freeze:out value="${shareInfo.lastyear_num_rate}"/>%</td>
    </tr>
    
</table>
</td></tr>
<tr><td>
<%if(show_type.equals("chart")){%>

<div id="chartContainer" style="height:<%=height%>px;" align="center"></div> 
<script>
    var myChart = new FusionCharts( "/FusionCharts/MSCombiDY2D.swf", "c001", "100%", "100%", "0", "1" );
    var xml="<%=chartXML%>";
    myChart.setXMLData(xml);  
    myChart.render("chartContainer");   
</script>
<%}else{%>
  <freeze:grid property="charts" checkbox="false" caption="共享情况统计" keylist="log_date" width="100%"  fixrow="false">
      <freeze:cell property="log_date" caption="日期" style="width:15%" align="center"/>
      <freeze:cell property="se" caption="响应请求次数(次)" style="width:15%" align="center"/>
      <freeze:cell property="st" caption="共享数据总量(条)" style="width:15%" align="center"/>
  </freeze:grid>
<%}}else{%>
<div style="font-size: 14px;text-align: center;">  该服务对象,查无相关数据！</div>
<%} %>


</td></tr>
</table>
</div>


</freeze:form>
</freeze:body>
</freeze:html>
