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

int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
org.apache.lucene.document.Document	idxDocument = null;
Document doc = null;
if ( nDocumentId > 0 ){
	doc = Document.findById(nDocumentId);
	if( doc==null )
		throw new WCMException("No Document [" + nDocumentId + "] found!" );

	String sSearchXML = "<SearchXML><SearchItem key=\"DOCUMENTID\" value=\""
		+ nDocumentId + "\"></SearchItem></SearchXML>";
	currRequestHelper.setValue("SearchXML", sSearchXML);

	IndexSearcher idxSearcher = InfoViewQueryHelper.getIndexSearcher();
	BooleanQuery query = InfoViewQueryHelper.buildQuery(currRequestHelper);
	//String sOrderField = CMyString.showNull(currRequestHelper.getOrderField());
	//String sOrderType = CMyString.showNull(currRequestHelper.getOrderType());
	//Sort sort = InfoViewQueryHelper.buildSort(sOrderField, sOrderType);
	//Hits hitDocuments = idxSearcher.search(query, sort);
	Hits hitDocuments = idxSearcher.search(query, (Sort)null);

	idxDocument	= hitDocuments.doc(0);
}	

%>
<HTML>
<HEAD>
<TITLE>表单检索测试</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</HEAD>

<BODY>
<div>
<form method=get action="infoview.jsp">
	<table border="0" cellspacing="5" cellpadding="8">
	<tbody>
		<tr>
			<td>请填入DocumentId： &nbsp;&nbsp;</td>
			<td><input type="text" name="DocumentId" value=""></td>
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
	if(doc != null){
%>
<table border="0" cellspacing="3" cellpadding="2" width="100%">
<tbody>
	<tr>
		<td width="150" align=right style="font-size: 14px; white-space: nowrap; font-weight: bold">WCM文档: </td>
		<td align=left style="font-size: 14px;">
			[ID=<%=doc.getId() %>]<%= doc.getTitle()%>
		</td>							
	</tr>
	<tr>
		<td align=right style="font-size: 14px; white-space: nowrap; font-weight: bold">其他属性: </td>
		<td align=left style="font-size: 14px;">
			<%=doc.getProperties() %>
		</td>							
	</tr>
</tbody>
</table>
<%	
	}
%>
<br>
<%
	if(idxDocument != null){
%>
<table border="0" cellspacing="3" cellpadding="2" width="100%">
<tbody>
	<tr>
		<td align=right width="150" style="font-size: 14px; white-space: nowrap; font-weight: bold">Lucene文档属性: </td>
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
%>
</BODY>
</HTML>