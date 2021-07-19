<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%-- ----- LUCENE IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.bigtable.LuceneRestoreWorker" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;
%>
<%

int nChannelId = currRequestHelper.getInt("ChannelId", 0);
String sInfo = null;
if (nChannelId > 0 ){
	LuceneRestoreWorker workder = LuceneRestoreWorker.getInstance();
	long lStartTime = System.currentTimeMillis();
	sInfo = workder.render(nChannelId);
	long lEndTime = System.currentTimeMillis();

	String sTemp = "\n总用时：" + (lEndTime - lStartTime) + " 毫秒";
	sTemp += "\n=====执行结果=====\n";
	sInfo = sTemp + sInfo;
}	
//*/
%>
<HTML>
<HEAD>
<TITLE>表单Luncene索引重建</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</HEAD>

<BODY>
<div>
<form method=get action="infoview_docs_restore.jsp">
	<table border="0" cellspacing="5" cellpadding="8">
	<tbody>
		<tr>
			<td>请填入ChannelId： &nbsp;&nbsp;</td>
			<td><input type="text" name="ChannelId" value=""></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="提交"></td>
		</tr>
	</tbody>
	</table>
</form>
</div>
<br>
<%
	if(sInfo != null){
%>
<div>
	<hr>
	<pre>
<%=sInfo%>
	</pre>
</div>
<%	
	}
%>
<br>
</BODY>
</HTML>