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

<%@include file="../include/public_processor.jsp"%>
<%@include file="element_processor.jsp"%>

<%
//变量声明
int nViewId = 0;
String sViewDesc = "";
boolean bIsMutiTable = false;
List oMetaViewFields = null;



try{
	String ObjectId = request.getParameter("objectId");
	String documentId = request.getParameter("DocumentId");
	if(!CMyString.isEmpty(documentId)){
		ObjectId = documentId;
	}
	processor.setAppendParameters(new String[]{"ObjectId",ObjectId});
	MetaViewData obj = (MetaViewData) processor.excute("wcm61_MetaViewData", "findViewDataById");
	MetaView oMetaView = obj.getMetaView();
	nViewId = oMetaView.getId();
	bIsMutiTable = oMetaView.isMultiTable();
	sViewDesc = oMetaView.getDesc();
	IMetaDataDefCacheMgr m_oDataDefCacheMgr = (IMetaDataDefCacheMgr) DreamFactory
            .createObjectById("IMetaDataDefCacheMgr");
	oMetaViewFields = m_oDataDefCacheMgr.getSortedMetaViewFields(nViewId);


	processor.reset();
	processor.setAppendParameters(new String[]{"ContentType" , "605","ContentId",ObjectId});
	ContentProcessInfo oContentProcessInfo = (ContentProcessInfo)processor.excute("process", "getProcessInfoOfContent");
	int nFlowId =  oContentProcessInfo.getContentFlowId();
	processor.reset();

	Appendixes oImageAppendixes = null;
	Appendixes oDocAppendixes = null;
	Appendixes oLinkAppendixes = null;
	//
	processor.reset();
	processor.setAppendParameters(new String[]{
			"AppendixType", "20","DocumentId", ObjectId
	});
	oImageAppendixes = (Appendixes)processor.excute("viewdocument", "queryAppendixes");
	processor.reset();
	//
	processor.setAppendParameters(new String[]{
		"AppendixType", "10","DocumentId", ObjectId
	});
	oDocAppendixes = (Appendixes)processor.excute("viewdocument", "queryAppendixes");
	processor.reset();
	//
	processor.setAppendParameters(new String[]{
		"AppendixType", "40","DocumentId", ObjectId
	});
	oLinkAppendixes = (Appendixes)processor.excute("viewdocument", "queryAppendixes");
	processor.reset();out.clear();%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="viewdata_detail.jsp.watchsviewdescviewdata">查看[<%=CMyString.transDisplay(sViewDesc)%>]视图数据</title>

<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metaviewdata.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/core/SysOpers.js"></script>
<!-- CarshBoard Outer Start -->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Outer End -->

<link href="viewdata_detail.css" rel="stylesheet" type="text/css" />

<script language="javascript" src="FileUploader.js" type="text/javascript"></script>
<script language="javascript" src="viewdata_detail.js" type="text/javascript"></script>

<!-- ~Page JavaScript Begin~    -->
<script src="../../../workflow/domain/WorkProcessor.js"></script>
<script language="javascript" src="viewdata_detail.js" type="text/javascript"></script>
<!-- ~Page JavaScript END~    -->

<script language="javascript">
	top.actualTop = top;
	function getPageParams(){
		return {
			viewId : <%=nViewId%>
		};
	}
</script>
</HEAD>

<BODY>


<div id="objectContainer" class="objectContainer">
		
		<%
			if(nFlowId>0){
		%>	
				<iframe id="gunter_template" src="" style="display:block;height:70px;" scrolling="no" frameborder="0" allowtransparency="true">
				</iframe>

		<%
			}
		%>
		
<%
	int nFieldType = 0;
	int nDBLength = 0;
	int nDBType = 0;
	String sFieldName = "";
	String sAnotherName = "";
	String sEnumValue  = "";
	String sFieldValue ="";
	
	for(int i = 1;i <= oMetaViewFields.size(); i++ ){
		MetaViewField oMetaViewField = (MetaViewField)oMetaViewFields.get(i - 1);
		if (oMetaViewField == null)
			continue;
		if(oMetaViewField.isInDetail()){
			if(bIsMutiTable){
				sFieldName = CMyString.transDisplay(oMetaViewField.getPropertyAsString("FIELDNAME"));				
			}else{
				sFieldName = CMyString.transDisplay(oMetaViewField.getPropertyAsString("DBFIELDNAME"));				
			}
			//sFieldName = CMyString.transDisplay(oMetaViewField.getPropertyAsString("FIELDNAME"));
			nDBType = oMetaViewField.getPropertyAsInt("DBTYPE", 0);
			sAnotherName = CMyString.transDisplay(oMetaViewField.getPropertyAsString("ANOTHERNAME"));
			nFieldType = oMetaViewField.getPropertyAsInt("FIELDTYPE", 0);
			nDBLength = oMetaViewField.getPropertyAsInt("DBLENGTH", 0);
			sEnumValue = CMyString.transDisplay(oMetaViewField.getPropertyAsString("ENMVALUE"));
			sFieldValue = CMyString.showNull(obj.getRealProperty(sFieldName));
			//To add  more field and status 
%>
			<div class="row">
			<span class="label"><%=sAnotherName%>：</span>
			<span class="value">&nbsp;
<%
			switch(nFieldType){
				case MetaDataConstants.FIELD_TYPE_SELFDEFINE:
					 %>
						    <%if(nDBType == 2005){%>
							<!--可视化编辑器-->
							<iframe src="page/blank.html" class="editorContainer" id="<%=sFieldName%>_frame" frameborder="0"></iframe>
							<script>SetValueByEditor("<%=sFieldName%>","<%=CMyString.filterForJs(makeHtmlCon(sFieldValue))%>");</script>
							<%}
							else if(nDBType == 93){
								out.print(CMyString.isEmpty(sFieldValue) ? "" : CMyString.transDisplay(sFieldValue.substring(0,16)));
							}else{
							out.print(CMyString.transDisplay(sFieldValue));
							}%>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_NORMALTEXT:
				case MetaDataConstants.FIELD_TYPE_PASSWORD:
				case MetaDataConstants.FIELD_TYPE_TIMESTAMP:
					out.print("<span>");
					out.print(CMyString.transDisplay(sFieldValue));
					out.print("</span>");
					break;
				case MetaDataConstants.FIELD_TYPE_MULTITEXT:
					out.println("<textarea readonly rows='6' cols='50'>");
					out.print(CMyString.filterForHTMLValue(sFieldValue));
					out.println("</textarea>");
					break;
				case MetaDataConstants.FIELD_TYPE_TRUEORFALSE:

					out.print("<span>");
					if(sFieldValue.equals("")){
						out.print("");
					}else{
						sFieldValue = sFieldValue.equals("1")==true ? LocaleServer.getString("metaviewdata.label.yes", "是") : LocaleServer.getString("metaviewdata.label.no", "否");
						out.print(sFieldValue);
					}
					out.print("</span>");
					break;
				case MetaDataConstants.FIELD_TYPE_RADIO:
				case MetaDataConstants.FIELD_TYPE_CHECKBOX:
				case MetaDataConstants.FIELD_TYPE_SELECT:
				case MetaDataConstants.FIELD_TYPE_INPUT_SELECT:
				case MetaDataConstants.FIELD_TYPE_SUGGESTION: 
					out.print(dealWith_onlyNode(oMetaViewField, obj));
					break;
				case MetaDataConstants.FIELD_TYPE_CLASS:
					out.print("<span class='classinfo_container'>");
					out.print(dealWith_class_onlyNode(sFieldValue,obj.getPropertyAsString(sFieldName)));
					out.print("</span>");
					break;
				case MetaDataConstants.FIELD_TYPE_HTML_CHAR:
				case MetaDataConstants.FIELD_TYPE_HTML:
					 %>
						<iframe src="page/blank.html" class="editorContainer" id="<%=sFieldName%>_frame" frameborder="0"></iframe>
						<script>SetValueByEditor("<%=sFieldName%>","<%=CMyString.filterForJs(makeHtmlCon(sFieldValue))%>");</script>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_RELDOC:
					int[] aRelDocIds = CMyString.splitToInt(sFieldValue, ",");
					StringBuffer buffer = new StringBuffer();
					for(int loop = 0;loop< aRelDocIds.length;loop++){
						Document oDoc = Document.findById(aRelDocIds[loop]);
						if(oDoc == null){
							buffer.append("<font color=red>").append(LocaleServer.getString("viewdata_detail.jsp.label.involve_doc_noexists","相关文档不存在")).append("[ID=").append(aRelDocIds[loop]).append("]").append("</font>");
							break;
						}
						String sTitle = CMyString.transDisplay(oDoc.getTitle());
						buffer.append("<span class='relationDocument'><a href='page/detail_show_redirection.jsp?objectId=" + aRelDocIds[loop] + "' target='_blank' title='id:" + aRelDocIds[loop] + "'>" + Document.findById(aRelDocIds[loop]).getTitle() + "</a></span>");
					}
					out.print("<span>");
					out.print(buffer.toString());
					out.print("</span>");
					break;
				case MetaDataConstants.FIELD_TYPE_APPENDIX:
					%>
					<!-- 添加控制 -->
					<a name="controller" _name="<%=CMyString.filterForHTMLValue(sFieldName)%>" _value="<%=CMyString.filterForHTMLValue(sFieldValue)%>" _type="appendix"	style="display:none;"></a>
					<%
					out.print(dealWith_appendix_onlyNode(oMetaViewField, obj));
					break;
				default:
					out.print("");

			}
%>
			</span>
				
			</div> 
<%
		}
	}
	if(!(oMetaView.getPropertyAsBoolean("HIDDENFILEAPPENDIX", false) || oMetaView.getPropertyAsBoolean("HIDDENIMGAPPENDIX", false) || oMetaView.getPropertyAsBoolean("HIDDENLINKAPPENDIX", false))){
%>	   
		<center id="appendixesContent">
			<table border=0 cellspacing=0 cellpadding=0 style="width:85%;font-size:14px;">
		
				<tr>
					<td class="appendix_label" valign="top" WCMAnt:param="viewdata_detail.jsp.imageAppendixes_a">图片附件：</td>
					<td class="appendix_value">
					<div id="document_image_appendixes">
							<%
								for(int k=0;k<oImageAppendixes.size();k++){
									Appendix oImageAppendix = (Appendix)oImageAppendixes.getAt(k);
									String sAppfileURL = oImageAppendix.getPropertyAsString("APPFILE");
							%>
								<div style="height:90px; width:120px; float:left; margin-right:5px;">
									<a href="/wcm/file/read_image.jsp?FileName=<%=CMyString.URLEncode(sAppfileURL)%>" target="_blank"><img src="<%=mapRollImage(sAppfileURL)%>" border=0 alt="<%=CMyString.filterForHTMLValue(oImageAppendix.getAlt())%>" style="height:90px;" title="<%=CMyString.filterForHTMLValue(oImageAppendix.getAlt())%>"></a>
								</div>
							<%
								}
							%>
					</div>
					</td>
				</tr>
				<tr>
					<td class="appendix_label" valign="top" WCMAnt:param="viewdata_detail.jsp.docAppendixes_a">文档附件：</td>
					<td class="appendix_value">
						<div id="document_doc_appendixes">
							<%
								for(int m=0;m<oDocAppendixes.size();m++){
									Appendix oDocAppendix = (Appendix)oDocAppendixes.getAt(m);
							%>
								 <div style="display:block;">
									<li><a href="/wcm/file/read_file.jsp?FileName=<%=CMyString.URLEncode(oDocAppendix.getFile())%>" title="<%=CMyString.filterForHTMLValue(oDocAppendix.getAlt())%>"
									><%=CMyString.transDisplay(oDocAppendix.getDesc())%></a>
								 </div>
							<%
								}
							%>
						</div>
					</td>
				</tr>
				<tr>
					<td class="appendix_label" valign="top" WCMAnt:param="viewdata_detail.jsp.linkAppendixes">链接附件：</td>
					<td class="appendix_value">
						<div id="document_link_appendixes">
						<%
							for(int n=0;n<oLinkAppendixes.size();n++){
								Appendix oLinkAppendix = (Appendix)oLinkAppendixes.getAt(n);
						%>
							<div style="display:block;">
								<li><a href="<%=CMyString.filterForHTMLValue(oLinkAppendix.getDesc())%>" target="about:blank" title="<%=CMyString.filterForHTMLValue(oLinkAppendix.getAlt())%>" target="_blank" class="url"><%=CMyString.transDisplay(oLinkAppendix.getDesc())%></a>
							</div>
						<%
							}
						%>
						</div>
					</td>
				</tr>
		
		</table>
	</center>
	<%}%>
</div>

<script language="javascript">
<!--
	if(<%=nFlowId%> > 0){
		var oPostData = "ctype=605"
						+"&cid="+ <%=ObjectId%>
						+"&PageSize= -1";
		$('gunter_template').src= "../flowdoc/document_detail_show_tracing.jsp?"+oPostData;
	}
//-->
</script>

</BODY>
</HTML>

<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("viewdata_detail.jsp.label.runtimeexception", "metaviewdata_query.jsp运行期异常!"), tx);
}
%>

<%!
	private String mapRollImage(String _fns) throws WCMException{
			return "/wcm/file/read_image.jsp?FileName=" + _fns;
	}
	private String makeHtmlCon(String _sHtml) throws Exception{
		if(CMyString.isEmpty(_sHtml)) return _sHtml;
		com.trs.cms.content.HTMLContent htmlCon = new com.trs.cms.content.HTMLContent(_sHtml);
		return htmlCon.parseHTMLContent(null);
	}
%>