<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
    DataBus condition=context.getRecord("condition");
%>
<freeze:html>
  <head>
    <title>自定义查询结果</title>
    <style>
		.frame-body { 
		word-break:break-all;
		}
#totalTableDiv table td{
	line-height: 150%;
}
	</style> 
	<script type="text/javascript">
	 function showQuery(){
	    var url='<%=request.getContextPath() %>/dw/aic/gjcx/cxtjdy/executeQuery.jsp?isUpdate=true&record:sys_advanced_query_id=<%=context.getRecord("record").getValue("sys_advanced_query_id")%>';
	    document.getElementById('pageList_frameX').src=url;
	    document.getElementById('pageList_frameX').style.display="block";
	 }
	
	
	</script>
  </head>
  <freeze:body>
  	<freeze:title caption="高级查询详情"/>
	<Div><%=condition.getValue("sqlCondition")%></Div>
	<div align="center">
	<table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
		<input type="button" class="menu" value=" 关闭 " onclick="goBack();"/>
		</td>
		<td class="btn_right"></td>
	</tr>
</table>
	</div>
</freeze:body>
</freeze:html>
