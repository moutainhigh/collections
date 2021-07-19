<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%-- ----- LUCENE IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDocs" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.bigtable.BigTableServer" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;
%>
<%

int nChannelId = currRequestHelper.getInt("ChannelId", 0);
ChnlDocs docs = null;
String sInfo = null;
int nCount = 0;
if (nChannelId > 0 ){
	//System.out.println("nChannelId: " + nChannelId);
	BigTableServer bt = (BigTableServer) DreamFactory
			.createObjectById("BigTableServer");
	docs = ChnlDocs.openWCMObjs(null, new WCMFilter(null,
			"ChnlId=" + nChannelId, null));
	if (!docs.isEmpty()) {
		sInfo = "";
		for (int i = 0; i < docs.size(); i++) {
			ChnlDoc doc = (ChnlDoc) docs.getAt(i);
			if (doc == null) {
				continue;
			}
			// else
			bt.updateDocumentQuoted(doc.getDocId(), doc.getChannelId(), doc
					.getDocOrder());

			if(doc.isDeleted()) {
				continue;
			}
			sInfo += "\n[" + i + "-更新文档成功(order-" + doc.getDocOrder() + ")][" + doc.getDocId() + "]" + doc.getDocument().getTitle();
			nCount++;
		}
	}
}	
//*/
%>
<HTML>
<HEAD>
<TITLE>表单更新DocOrder</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</HEAD>

<BODY>
<div>
<form method=get action="infoview_order.jsp">
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
	总共操作了[<%=nCount%>]篇文档，详细信息： <hr>
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