<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%-- ----- LUCENE IMPORTS BEGIN ---------- --%>
<%@ page import="org.apache.lucene.analysis.Analyzer" %>
<%@ page import="org.apache.lucene.analysis.standard.StandardAnalyzer" %>
<%@ page import="org.apache.lucene.document.DateTools" %>
<%@ page import="org.apache.lucene.index.Term" %>
<%@ page import="org.apache.lucene.queryParser.QueryParser" %>
<%@ page import="org.apache.lucene.search.BooleanClause" %>
<%@ page import="org.apache.lucene.search.BooleanQuery" %>
<%@ page import="org.apache.lucene.search.Hits" %>
<%@ page import="org.apache.lucene.search.IndexSearcher" %>
<%@ page import="org.apache.lucene.search.RangeQuery" %>
<%@ page import="org.apache.lucene.search.Sort" %>
<%@ page import="org.apache.lucene.search.SortField" %>
<%@ page import="org.apache.lucene.search.TermQuery" %>
<%@ page import="org.apache.lucene.search.Query" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.infoview.helper.InfoViewQueryHelper" %>
<%@ page import="com.trs.components.infoview.InfoViewDataHelper" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;
%>
<%

int nChannelId = currRequestHelper.getInt("ChannelId", 0);
org.apache.lucene.document.Document	idxDocument = null;
Channel chnl = null;
Hits hitDocuments = null;
if (nChannelId > 0 ){
	
	chnl = Channel.findById(nChannelId);
	if(chnl ==null )
		throw new WCMException("No Channel [" + nChannelId + "] found!" );

	currRequestHelper.setValue("ChannelId", String.valueOf(nChannelId));
	IndexSearcher idxSearcher = InfoViewQueryHelper.getIndexSearcher();
	BooleanQuery query = InfoViewQueryHelper.buildQuery(currRequestHelper);
	//String sOrderField = CMyString.showNull(currRequestHelper.getOrderField());
	//String sOrderType = CMyString.showNull(currRequestHelper.getOrderType());
	Sort sort = InfoViewQueryHelper.buildSort("DOCORDER", "desc", SortField.INT);
	hitDocuments = idxSearcher.search(query, sort);

	//idxDocument	= hitDocuments.doc(0);

	//*/
}	

%>
<HTML>
<HEAD>
<TITLE>表单检索测试</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</HEAD>

<BODY>
<div>
<form method=get action="infoviews.jsp">
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
	if(chnl != null) {
%>
<table border="0" cellspacing="3" cellpadding="2" width="100%">
<tbody>
	<tr>
		<td width="150" align=right style="font-size: 14px; white-space: nowrap; font-weight: bold">WCM栏目: </td>
		<td align=left style="font-size: 14px;">
			[ID=<%=chnl.getId() %>]<%= chnl.getDesc()%>
		</td>							
	</tr>
	<tr>
		<td align=right style="font-size: 14px; white-space: nowrap; font-weight: bold">其他属性: </td>
		<td align=left style="font-size: 14px;">
			<%=chnl.getProperties() %>
		</td>							
	</tr>
</tbody>
</table>
<%	
	}
%>
<%
if(hitDocuments != null) {
	for(int i = 0; i <= hitDocuments.length(); i++){
		try{
			idxDocument	= hitDocuments.doc(i);
		} catch(Exception ex){
			continue;
			//throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取第[" + i + "]篇文档失败！", ex);
		}
		if(idxDocument == null) {
			continue;
		}
%>
<hr>
<table border="0" cellspacing="3" cellpadding="2" width="100%">
<tbody>
	<tr>
		<td align=right width="30" style="font-size: 14px; white-space: nowrap; font-weight: bold; font-family: georgia"><%= (i+1)%></td>
		<td align=left style="font-size: 14px;">
		<ul>
<%
		String[] arFieldNames = {"DOCTITLE", "INFOVIEWID", "CRUSER", "CRTIME", 
				 "ROOTELEMENT", "POSITIONCODE", "ARCHIVESERIAL", "QUOTEDCHANNELIDS", "DOCORDER"};
		for (int column = 0; column < arFieldNames.length; column++) {
			String sFieldName = arFieldNames[column];
%>
			<li>
			<span style="font-weight: bold"><%=sFieldName%></span>:
			<%=PageViewUtil.toHtml(InfoViewDataHelper.getLuceneFieldValue(idxDocument, sFieldName)) %>
<%
		}	
%>	
		</ul>
		</td>
	</tr>
</tbody>
</table>
<%	
	}
}
%>
</BODY>
</HTML>