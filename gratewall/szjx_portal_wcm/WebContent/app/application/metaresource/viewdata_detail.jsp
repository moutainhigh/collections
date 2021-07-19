<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="java.sql.Types" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.cms.process.engine.ContentProcessInfo" %>
<%@ page import="com.trs.components.wcm.resource.DocLevel"%>
<%@ page import="com.trs.components.wcm.resource.DocLevels"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires",0); 
response.setHeader("Cache-Control","private"); 
%>
<%@include file="../../../app/include/public_processor.jsp"%>

<%
try{
	String ObjectId = request.getParameter("objectId");
	String documentId = request.getParameter("DocumentId");
	if(!CMyString.isEmpty(documentId)){
		ObjectId = documentId;
	}
	processor.setAppendParameters(new String[]{"ObjectId",ObjectId});
	//build global parameter obj.
	MetaViewData obj = (MetaViewData) processor.excute("wcm61_MetaViewData", "findViewDataById");
	//
	processor.reset();
	processor.setAppendParameters(new String[]{"ContentType" , "605","ContentId",ObjectId});
	ContentProcessInfo oContentProcessInfo = (ContentProcessInfo)processor.excute("process", "getProcessInfoOfContent");
	int nFlowId =  oContentProcessInfo.getContentFlowId();

	Document document = obj.getDocumnent();
	String sdocLevelName = "";
	if(document != null){
		sdocLevelName = document.getDocLevelName();
	}

	Appendixes oImageAppendixes = null;
	Appendixes oDocAppendixes = null;
	Appendixes oLinkAppendixes = null;
	//
	processor.reset();
	processor.setAppendParameters(new String[]{
			"AppendixType", "20","DocumentId", ObjectId
	});
	oImageAppendixes = (Appendixes)processor.excute("viewdocument", "queryAppendixes");
	//
	processor.reset();
	processor.setAppendParameters(new String[]{
		"AppendixType", "10","DocumentId", ObjectId
	});
	oDocAppendixes = (Appendixes)processor.excute("viewdocument", "queryAppendixes");
	//
	processor.reset();
	processor.setAppendParameters(new String[]{
		"AppendixType", "40","DocumentId", ObjectId
	});
	oLinkAppendixes = (Appendixes)processor.excute("viewdocument", "queryAppendixes");
	out.clear();%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="viewdata_detail.jsp.watchviewdescdata">查看视图[<TRS_VIEW FIELD="ViewDesc"/>]数据</title>

<script src="../../../app/js/runtime/myext-debug.js"></script>
<script src="../../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../../app/js/data/locale/metaviewdata.js"></script>
<script src="../../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../../app/js/source/wcmlib/core/CMSObj.js"></script>
<!-- CarshBoard Outer Start -->
<script src="../../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../../app/js/source/wcmlib/Component.js"></script>
<script src="../../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Outer End -->

<!-- ~Page JavaScript Begin~    -->
<script src="../../../app/workflow/domain/WorkProcessor.js"></script>
<script language="javascript" src="../base/FileUploader.js" type="text/javascript"></script>
<script language="javascript" src="viewdata_detail.js" type="text/javascript"></script>
<!-- ~Page JavaScript END~    -->
<link href="viewdata_detail.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table border="0" cellspacing="0" cellpadding="0" class="box">
<%
	if(nFlowId>0){
%>	
	<tr>
		<td class="label">工作流信息：</td>
		<td class="value">
		<iframe id="gunter_template" src="../../../app/flowdoc/document_detail_show_tracing.jsp?ctype=605&PageSize= -1&cid=<%=ObjectId%>" style="display:block;height:70px;width:100%;" scrolling="no" frameborder="0" allowtransparency="true"></iframe>
		</td>
	</tr>
<%
	}
%>
<TRS_ViewFields InDetail="true" type="view">
	<tr>
		<td class="label"><TRS_ViewField field="AnotherName" filterForHTML="true" />：</td>
		<td class="value">
			<TRS_ViewField field="_html" />
		</td>
	</tr>
</TRS_ViewFields>
<TRS_Condition Condition="@HIDDENIMGAPPENDIX" Type="INT" REFERENCE="0">
	<tr>
		<td class="label" WCMAnt:param="viewdata_detail.jsp.imageAppendixes_a">图片附件：</td>
		<td class="value">
			<%
				for(int k=0; k<oImageAppendixes.size(); k++){
					Appendix oImageAppendix = (Appendix)oImageAppendixes.getAt(k);
					String sAppfileURL = oImageAppendix.getPropertyAsString("APPFILE");
			%>
				<div style="height:90px; width:120px; float:left; margin-right:5px;">
					<a href="/wcm/file/read_image.jsp?FileName=<%=sAppfileURL%>" target="_blank"><img src="/wcm/file/read_image.jsp?FileName=<%=sAppfileURL%>" border=0 alt="<%=CMyString.showNull(oImageAppendix.getAlt())%>" style="height:90px;" title="<%=CMyString.showNull(oImageAppendix.getAlt())%>"></a>
				</div>
			<%
				}
			%>
		</td>
	</tr>
</TRS_Condition>
<TRS_Condition Condition="@HIDDENFILEAPPENDIX" Type="INT" REFERENCE="0">
	<tr>
		<td class="label" WCMAnt:param="viewdata_detail.jsp.docAppendixes_a">文档附件：</td>
		<td class="value">
			<%
				for(int m=0; m<oDocAppendixes.size(); m++){
					Appendix oDocAppendix = (Appendix)oDocAppendixes.getAt(m);
			%>
				<li><a href="/wcm/file/read_file.jsp?FileName=<%=CMyString.showNull(oDocAppendix.getFile())%>" title="<%=CMyString.showNull(oDocAppendix.getAlt())%>"
				><%=CMyString.showNull(oDocAppendix.getDesc())%></a>
			<%
				}
			%>
		</td>
	</tr>
</TRS_Condition>
<TRS_Condition Condition="@HIDDENLINKAPPENDIX" Type="INT" REFERENCE="0">
	<tr>
		<td class="label" WCMAnt:param="viewdata_detail.jsp.linkAppendixes">链接附件：</td>
		<td class="value">
			<%
				for(int n=0;n<oLinkAppendixes.size();n++){
					Appendix oLinkAppendix = (Appendix)oLinkAppendixes.getAt(n);
			%>
				<li><a href="<%=oLinkAppendix.getSrcFile()%>" title="<%=oLinkAppendix.getAlt()%>" target="_blank" class="url"><%=oLinkAppendix.getDesc()%></a>
			<%
				}
			%>
		</td>
	</tr>
</TRS_Condition>
	<tr>
		<td class="label" WCMAnt:param="viewdata_detail.jsp.belonglevel_a">所属密级：</td>
		<td class="value">
			<%=CMyString.transDisplay(sdocLevelName)%>
		</td>
	</tr>
</table>
</body>
</html>

<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException("metaviewdata_query.jsp运行期异常!", tx);
}
%>

<%!
	private String makeHtmlCon(String _sHtml) throws Exception{
		if(CMyString.isEmpty(_sHtml)) return _sHtml;
		com.trs.cms.content.HTMLContent htmlCon = new com.trs.cms.content.HTMLContent(_sHtml);
		return htmlCon.parseHTMLContent(null);
	}

	/**
	*获取文档的title信息
	*/
	private String getDocumentsHtml(String sDocIds){
		if(CMyString.isEmpty(sDocIds)) return "&nbsp;";
		int[] aDocIds = CMyString.splitToInt(sDocIds, ",");
		StringBuffer sb = new StringBuffer();
		Document doc = null;
		int channelId = 0;
		for(int index = 0, length = aDocIds.length; index< length; index++){
			try{
				doc = Document.findById(aDocIds[index], "docTitle,DOCCHANNEL");
				if(doc == null) continue;
				channelId = doc.getChannelId();
				sb.append("<div class='relatedDocument'>");
				sb.append("<a href='" + "/wcm/app/document/document_show.jsp" + "?DocumentId=" + aDocIds[index] + "&ChannelId=" + channelId);
				sb.append("' target='_blank' title='id:");
				sb.append(aDocIds[index]).append("'>");
				sb.append(CMyString.transDisplay(doc.getTitle()));
				sb.append("</a>");
				sb.append("</div>");
			}catch(Throwable tx){
				sb.append("<div class='relatedDocument-error'>");
				sb.append(CMyString.filterForHTMLValue(LocaleServer.getString("metaviewdata.label.reldocNotExist","指定的相关文档不存在"))).append("[").append(aDocIds[index]).append("]").append("</div>");
			}
		}
		return sb.toString();
	}
%>