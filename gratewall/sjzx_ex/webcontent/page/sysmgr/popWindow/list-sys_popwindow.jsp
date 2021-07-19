<%@ page contentType="text/html; charset=GBK" %>

<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@page import="cn.gwssi.common.context.Recordset"%>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
Recordset rs = context.getRecordset("record");
%>
<freeze:html width="650" height="350">
<head>
<title>公告栏</title>
</head>

<script language="javascript">
</script>
<freeze:body>
<freeze:errors/>
<freeze:form action="/txn60800006">
<br/>
  <freeze:grid property="record" caption="通知公告" keylist="sys_popwindow_id" width="95%" navbar="bottom" rowselect="false" checkbox="false" fixrow="false">
      <freeze:cell property="sys_popwindow_id" caption="系统任务ID" visible="false" style="width:35%" />
      <freeze:cell property="@rowid" caption="序号" style="width:5%" align="center"/>
      <freeze:cell property="content" caption="内容" style="width:35%" />
      <freeze:cell property="PUBLISH_DEPART" caption="发布部门" style="width:10%" />
      <freeze:cell property="publish_date" caption="发布日期" style="width:10%" datatype="date"/>
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
