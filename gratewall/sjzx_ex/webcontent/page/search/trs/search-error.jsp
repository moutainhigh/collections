<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
System.out.println(context);
String errorMsg = "";
DataBus db = context.getRecord("error");
errorMsg = db.getValue("codeStr");
if(errorMsg==null) errorMsg="";
%>

<freeze:html>
<head><title>ȫ�ļ����쳣</title></head>
<freeze:body>
</freeze:body>
<script language="javascript">
var str = "<%=errorMsg%>"
if(str!=""){
alert("��������"+str);
}
</script>
</freeze:html>
