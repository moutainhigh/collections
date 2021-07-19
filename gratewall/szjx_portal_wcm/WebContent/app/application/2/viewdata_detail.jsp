<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataDefCacheMgr" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
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
<%@ page import="com.trs.infra.support.file.FileHelper"%>
<%
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires",0); 
response.setHeader("Cache-Control","private"); 
%>
<%@include file="../../../app/include/public_processor.jsp"%>
<%@include file="../../../app/system/doclevel_locale.jsp"%>

<%
try{
	String ObjectId = request.getParameter("objectId");
	String documentId = request.getParameter("DocumentId");
	if(!CMyString.isEmpty(documentId)){
		ObjectId = documentId;
	}

	String flowDocId = request.getParameter("FlowDocId");
	int nFlowDocId = 0;
	if(!CMyString.isEmpty(flowDocId)){
		nFlowDocId = Integer.parseInt(flowDocId);
	}
	String sWorkListViewType = request.getParameter("WorklistViewType");
	int nWorkListViewType = 0;
	if(!CMyString.isEmpty(sWorkListViewType)){
		nWorkListViewType = Integer.parseInt(sWorkListViewType);
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
<title WCMAnt:param="viewdata_detail.jsp.watchviewdescdata">查看视图[政府公开信息]数据</title>

<meta http-equiv="X-UA-Compatible" content="IE=8">
<script src="../../../app/js/runtime/myext-debug.js"></script>
<script src="../../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../../app/js/data/locale/metaviewdata.js"></script>
<script src="../../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../../app/js/easyversion/ajax.js"></script>
<script src="../../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
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
<script language="javascript" src="../../../app/application/common/viewdata_detail.js" type="text/javascript"></script>
<script language="javascript" src="viewdata_detail.js" type="text/javascript"></script>
<!-- ~Page JavaScript END~    -->
<link href="../../../app/application/common/viewdata_detail.css" rel="stylesheet" type="text/css" />
<link href="viewdata_detail.css" rel="stylesheet" type="text/css" />
<!--jquery begin-->
<script src="../../../app/application/common/jquery/js/jquery-1.6.1.min.js"></script>
<script src="../../../app/application/common/jquery/js/jquery-ui-1.8.13.custom.min.js"></script>
<link href="../../../app/application/common/jquery/css/ui-lightness/jquery-ui-1.8.13.custom.css" rel="stylesheet" type="text/css" />
<!--jquery end-->
</head>
<script>
	var m_nObjectId = <%=documentId%>;
	var m_nFlowDocId = <%=nFlowDocId%>;
	jQuery.noConflict()(function() {
		jQuery("#tabs").tabs();
	})
</script>
<body>
<div class="box">
	<div id="tabs">
		<ul>
			
				<li>
					<a href="#tabs-1">政府信息公开</a>
				</li>
			
				<li>
					<a href="#tabs-2">详细内容</a>
				</li>
			
			
				<li>
					<a href="#tabs-picmgr" WCMAnt:param="metaviewdata_addedit.jsp.product.pic">图片管理</a>
				</li>
			
			
				<li>
					<a href="#tabs-filemgr" WCMAnt:param="metaviewdata_addedit.jsp.product.file">文件管理</a>
				</li>
			
			
				<li>
					<a href="#tabs-linkmgr" WCMAnt:param="metaviewdata_addedit.jsp.product.link">链接管理</a>
				</li>
			
			<li>
				<a href="#tabs-others" WCMAnt:param="metaviewdata_addedit.jsp.other">其他</a>
			</li>
			<%
				if(nFlowId>0){
			%>
				<li>
					<a href="#tabs-process" WCMAnt:param="metaviewdata_addedit.jsp.process">流转信息</a>
				</li>
			<%
				}	
			%>

		</ul>
		<%
			if(nFlowId>0){
		%>
		<div id="tabs-process">
			<div class="XProcessBox">
				<%
				//进行流转的处理
				if(nWorkListViewType == 1 && nFlowDocId > 0){
			%>
				<iframe src="../../../app/flowdoc/workflow_process_render_for_metadata.jsp?NoShowAllBtn=1&FlowDocId=<%=nFlowDocId%>" class="XProcess" id="XProcessIframe" scrolling="no" frameborder="no" border="0" height="100%" width="100%"></iframe>
			<%
				}else{	
				//简单的流转信息查看和处理
			%>
				<iframe id="gunter_template" src="../../../app/flowdoc/document_detail_show_tracing.jsp?ctype=605&PageSize= -1&cid=<%=ObjectId%>" height="100%" width="100%" scrolling="no" frameborder="0" allowtransparency="true"></iframe>
			<%
				}	
			%>
			</div>
		</div>
		<%
		}		
		%>
		
			<div id="tabs-1">
				
				
					<fieldset><legend>基本信息</legend>
						
							<div class="row" style=''>
								<div class="label">索引</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("idxID"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">名称</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("Title"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">文号</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("fileNum"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">生效期</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("efectdate"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">生成日期</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("PubDate"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">内容概述</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("Description"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">关键字</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("Keywords"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">发布机构</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("Publisher"), true)%>
								</div>
							</div>
						
					</fieldset>
				
					<fieldset><legend>所属关系</legend>
						
							<div class="row" style=''>
								<div class="label">所属主题</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("subcat"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">所属体裁</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("themecat"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">所属机构</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("organcat"), true)%>
								</div>
							</div>
						
					</fieldset>
				
					<fieldset><legend>其他信息</legend>
						
							<div class="row" style=''>
								<div class="label">著录日期</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("mdUpdTime"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">相关信息索引</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("RidxId"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">相关信息名称</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("Rtitle"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">位置关键词</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("placekey"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">获取方式</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("acesmthd"), true)%>
								</div>
							</div>
						
							<div class="row" style=''>
								<div class="label">废止日期</div>
								<div class="sep">：</div>
								<div class="value">
									<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("Abolidate"), true)%>
								</div>
							</div>
						
					</fieldset>
				
			</div>
		
			<div id="tabs-2">
				
					<div class="row" style=''>
						
						
								<!--可视化编辑器-->
<textarea name="Content" id="Content" style="display:none;"><%=CMyString.filterForHTMLValue(makeHtmlCon(obj.getRealProperty("Content")))%></textarea>
<iframe src="../core/com.trs.ui/ckeditor/blank.html?fieldname=Content" style="width:520px;height:250px;border:1px solid silver;" frameborder="0" class="editorIframe"></iframe>
						
						
					</div>
				
				
			</div>
		
		
		<!--图片管理显示-->
		
		<div id="tabs-picmgr">
			<div style="overflow:hidden;">
				<%
				for(int k=0; k<oImageAppendixes.size(); k++){
					Appendix oImageAppendix = (Appendix)oImageAppendixes.getAt(k);
					String sAppfileURL = oImageAppendix.getPropertyAsString("APPFILE");
					String sZoomFile = FileHelper.makeNewZoomInFileName(sAppfileURL, 208);
			%>
				<div style="float:left; margin-right:5px;">
					<a href="/wcm/file/read_image.jsp?FileName=<%=sAppfileURL%>" target="_blank">
					<img src="/wcm/file/read_image.jsp?FileName=<%=sZoomFile%>" border=0 alt="<%=CMyString.showNull(oImageAppendix.getAlt())%>" title="<%=CMyString.showNull(oImageAppendix.getAlt())%>" width="208px"></a>
				</div>
			<%
				}
			%>	
			</div>
		</div>
		
		
		<!--文件管理显示-->
		
		<div id="tabs-filemgr">
			<div style="overflow:hidden;">
			<%
				for(int k=0; k<oDocAppendixes.size(); k++){
					Appendix oDocAppendix = (Appendix)oDocAppendixes.getAt(k);
					String sAppfileURL = oDocAppendix.getPropertyAsString("APPFILE");
					String sAppfileDesc = oDocAppendix.getPropertyAsString("APPDESC");
			%>
				<div style="padding-top:5px;">
					<span> <%=k+1%> :  </span>
					<span class="appendix" title="<%=sAppfileURL%>" onclick="downloadAppendix(this);"><%=CMyString.transDisplay(sAppfileDesc)%></span>
				</div>
				
			<%
				}
			%>	
			</div>
		</div>
		

		<!--链接管理显示-->
		
		<div id="tabs-linkmgr">
			<div style="overflow:hidden;">
			<%
				for(int k=0; k<oLinkAppendixes.size(); k++){
					Appendix oDocLinkAppendix = (Appendix)oLinkAppendixes.getAt(k);
					String sAppLinkURL = oDocLinkAppendix.getPropertyAsString("APPFILE");
					String sAppLinkDesc = oDocLinkAppendix.getPropertyAsString("APPDESC");
					String sTitle = oDocLinkAppendix.getAlt();
			%>
				<div style="padding-top:5px;">
					<span> <%=k+1%> :  </span>
					<a href="<%=CMyString.filterForHTMLValue(sAppLinkURL)%>" target="_blank" title="<%=CMyString.filterForHTMLValue(sTitle)%>"><%=CMyString.transDisplay(sAppLinkDesc)%></a>
				</div>
			<%
				}
			%>	
			</div>
		</div>
		

		<div id="tabs-others">
			
			<div class="label" WCMAnt:param="viewdata_detail.jsp.belonglevel">所属密级</div>
			<div class="sep">：</div>
			<div class="value">
				<%=CMyString.transDisplay(getDocLevelLocale(sdocLevelName))%>
			</div>
		</div>
	</div>
	<div class="buttonBox" id="CommandButtons" >
		<%
			if(nFlowDocId > 0 && nWorkListViewType == 1){
		%>
		<button onclick="submitData(true);return false;" WCMAnt:param="viewdata_detail.jsp.confirm">确&nbsp;&nbsp;定</button>&nbsp;&nbsp;&nbsp;
		<button onclick="window.close();" WCMAnt:param="viewdata_detail.jsp.cancel">取&nbsp;&nbsp;消</button>&nbsp;&nbsp;&nbsp;
		<%
			}else{	
		%>
		<button onclick="window.close();" WCMAnt:param="viewdata_detail.jsp.close">关&nbsp;&nbsp;闭</button>
		<%
			}
		%>
	</div>
</div>
</body>
</html>

<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("viewdata_detail.jsp.label.runtimeexception","metaviewdata_query.jsp运行期异常!"), tx);
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
		sb.append("<div>");
		sb.append("<ul>");
		Document doc = null;
		int channelId = 0;
		for(int index = 0, length = aDocIds.length; index< length; index++){
			try{
				doc = Document.findById(aDocIds[index], "docTitle,DOCCHANNEL,DOCSTATUS");
				//by CC 20120417 过滤掉删除到废稿箱的文档
				if(doc == null || doc.getStatusId()<0) continue;
				channelId = doc.getChannelId();
				sb.append("<li class='relatedDocument'>");
				sb.append("<a href='" + "/wcm/app/document/document_show.jsp" + "?DocumentId=" + aDocIds[index] + "&ChannelId=" + channelId);
				sb.append("' target='_blank' title='id:");
				sb.append(aDocIds[index]).append("'>");
				sb.append(CMyString.transDisplay(doc.getTitle()));
				sb.append("</a>");
				sb.append("</li>");
			}catch(Throwable tx){
				sb.append("<i class='relatedDocument-error'>");
				sb.append(CMyString.filterForHTMLValue(LocaleServer.getString("metaviewdata.label.reldocNotExist","指定的相关文档不存在"))).append("[").append(aDocIds[index]).append("]").append("</div>");
			}
		}
		sb.append("</ul>");
		sb.append("</div>");
		return sb.toString();
	}
%>