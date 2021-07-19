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

boolean bRender = currRequestHelper.getBoolean("render", false);
String sInfo = null;
if (bRender){
	LuceneRestoreWorker workder = LuceneRestoreWorker.getInstance();
	long lStartTime = System.currentTimeMillis();
	sInfo = workder.renderAll();
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
<table border="0" cellspacing="12" cellpadding="0" style="font-family: 'Courier New'">
<tbody>
	<tr>
		<td style="color: red; font-weight: bold">注意事项</td>
	</tr>
	<tr>
		<td>
		<ul>
			<li>此操作将对WCM系统中的所有表单文档进行刷新
			<li>请妥善使用此操作，在表单文档条数太多时(如2000条以上)该操作将持续很长时间
			<li>请在执行此操作前对 <font size="" color="red">系统配置>>目录配置>>BT</font> 目录下的数据进行备份，以防刷新失败时恢复
		</ul>
		</td>
	</tr>
</tbody>
</table>
<div>
<form method=get action="infoview_docs_restore_all.jsp">
	<table border="0" cellspacing="5" cellpadding="8">
	<tbody>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="开始执行" style="width: 120px;"><input type="hidden" name="render" value="1"></td>
		</tr>
	</tbody>
	</table>
</form>
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