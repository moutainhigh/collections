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
<title>������</title>
</head>

<script language="javascript">
</script>
<freeze:body>
<freeze:errors/>
<freeze:form action="/txn60800006">
<br/>
  <freeze:grid property="record" caption="֪ͨ����" keylist="sys_popwindow_id" width="95%" navbar="bottom" rowselect="false" checkbox="false" fixrow="false">
      <freeze:cell property="sys_popwindow_id" caption="ϵͳ����ID" visible="false" style="width:35%" />
      <freeze:cell property="@rowid" caption="���" style="width:5%" align="center"/>
      <freeze:cell property="content" caption="����" style="width:35%" />
      <freeze:cell property="PUBLISH_DEPART" caption="��������" style="width:10%" />
      <freeze:cell property="publish_date" caption="��������" style="width:10%" datatype="date"/>
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
