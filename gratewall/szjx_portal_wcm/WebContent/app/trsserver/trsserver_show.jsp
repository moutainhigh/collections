<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.trsserver.DatasFromTRSServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.HashMap" %>
<%@include file="../include/public_server.jsp"%>

<%
	int _nPageSize = currRequestHelper.getInt("PageSize",20);
	int _nPageIndex = currRequestHelper.getInt("PageIndex",1);
	String _sId = currRequestHelper.getString("ServerId");

	DatasFromTRSServer currServer = new DatasFromTRSServer();
	currServer.setWhere("IR_SID=" + _sId);
	currServer.setSelect("IR_SID,IR_URLTITLE,IR_URLTIME,IR_CONTENT");
	ArrayList result = new ArrayList();
	result = (ArrayList)currServer.getDatasFromTRSServer(0, _nPageSize);

	if(result.size() <= 0){
		throw new WCMException(CMyString.format(LocaleServer.getString("trsserver_show.jsp.trsserver_record_notfound", "未找到IR_SID为[{0}]的TRSServer记录，请确认!"), new String[]{_sId}));
	}

	HashMap currMap = (HashMap)result.get(0);
	String _sTitle = (String)currMap.get("IR_URLTITLE");
	String _sCrtime = currMap.get("IR_URLTIME").toString();
	String _sCruser = loginUser.getName();
	String _SDocContent = (String)currMap.get("IR_CONTENT");
%><html>
<head>
<title WCMAnt:param="document_detail.jsp.title">TRS WCM 文档查看页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</title>
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/document/document_detail.css" rel="stylesheet" type="text/css" WCMAnt:locale="./document_detail_$locale$.css"/>
</head>
<body>		  
<div id="docDetail" style="width:100%;height:100%;">
<table width="80%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="view_area" id="view_area">
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right" valign="top" bgcolor="F3F1F1">
						<div id="document_head">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							<td><div align="center" class="title" id="divDocTitle"><%=CMyString.filterForHTMLValue(CMyString.showNull(_sTitle))%></div></td>
						  </tr>
						</table>
						<table width="100%" height="24" border="0" cellpadding="0" cellspacing="0">
						  <tr>
							<td><div align="center" class="info"><span class="info_name" WCMAnt:param="document_detail.jsp.author">作者:</span><%=CMyString.filterForHTMLValue(CMyString.showNull(_sCruser))%>  &nbsp;&nbsp;<span class="info_name" WCMAnt:param="document_detail.jsp.crtime">撰写时间:</span> <%=CMyString.showNull(_sCrtime)%>  &nbsp;&nbsp; </div></td>
						  </tr>
						</table>
						</div>
					</td>
				</tr>
			</table>
			<div id="document_body">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="content_outer table_fixed">
				<tr>
					  <td class="content_inner">
					  <%=CMyString.transDisplay(_SDocContent)%>
					</td>
				</tr>
				</table>
			</div>

			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
</body>
</html>