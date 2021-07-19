<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.ajaxservice.ProcessService" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.FlowContentFactory" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.process.FlowEngineHelper" %>
<%@ page import="com.trs.cms.process.engine.ContentProcessInfo" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="java.text.*" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@include file="../include/public_server.jsp"%>
<%
	int nContentId = currRequestHelper.getInt("cid",0);
	int nContentType = currRequestHelper.getInt("ctype",0);
	int nShowOper = currRequestHelper.getInt("showOper",1);
	Document document = Document.findById(nContentId);
	String sDocTitle = document.getTitle();
	int nFlowDocId = currRequestHelper.getInt("FlowDocId",0);
	IFlowContent content = FlowContentFactory.makeFlowContent(document);
	if (content == null) {
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,
				CMyString.format(LocaleServer.getString("document.detail.show.tracing","指定的内容[Type={0} , Id={1},]没有找到"),new String[]{WCMTypes.getLowerObjName(nContentType),String.valueOf(nContentId)}));
	}
	IFlowServer m_oFlowServer = (IFlowServer) DreamFactory.createObjectById("IFlowServer");
	ContentProcessInfo oProcessInfo = m_oFlowServer.getProcessInfoOfContent(content);

	int nFlowId =  oProcessInfo.getContentFlowId();
	if(oProcessInfo==null || nFlowId ==0) return;

	boolean bCanInFlow = false;
	boolean bReInFlow = false;
	boolean bStopFlow = false;
	boolean bIsAdministrator = loginUser.isAdministrator();
	boolean bIsStarter = loginUser.getName().equals(oProcessInfo.getStartUserName());
    boolean bOnlyAdmin = "true".equalsIgnoreCase(ConfigServer.getServer().getSysConfigValue("ONLY_ADMIN_CTR_FLOW", "false"));
	bCanInFlow = oProcessInfo.canInFlow() && (bIsAdministrator || loginUser.equals(oProcessInfo.getContent().getCrUser()));
	bReInFlow = oProcessInfo.canReInFlow() && (bIsAdministrator || loginUser.equals(oProcessInfo.getContent().getCrUser()));
	bStopFlow =  oProcessInfo.canStopFlow() && ((!bOnlyAdmin && bIsStarter) || bIsAdministrator);

	FlowDocs oFlowDocs = m_oFlowServer.getFlowDocs(content, null);	
%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="document_detail_show.jsp.title">查看流转轨迹</title>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<!--my import-->
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/iflowcontent.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<!-- CarshBoard Outer Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/css/list-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Outer End -->
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<link href="./document_detail_show_tracing.css" WCMAnt:locale="./document_detail_show_tracing_$locale$.css" rel="stylesheet" type="text/css" />
</HEAD>
<BODY align="center">
<script language="javascript">
if(window.parent != null && window.parent.location.href.indexOf("/viewdata_detail.jsp")>=0){
	var sURL  = "./workflow_process_tracing.jsp";
	var oParameters = {
		flowid	: <%=nFlowDocId%>,
		title	: '<%=CMyString.filterForJs(sDocTitle)%>',
		ctype	: <%=nContentType%>,
		cid		: <%=nContentId%>,
		cruser	: '<%=CMyString.filterForJs(document.getCrUserName())%>',
		crtime	: '<%=document.getCrTime()%>',
		isend	: 0,
		gunter_view:'false'
	};

	sURL = sURL + "?" + $toQueryStr(oParameters);
	window.location.href = sURL;	
}

_params = {
		ContentId: '<%=nContentId%>'
	} 
function reFlow(){
	 Ext.Msg.confirm(wcm.LANG['IFLOWCONTENT_1'] || '您确实要将当前文档重新进行流转吗？', {
		yes : function(){
			_startDocInFlow(_params);
		},
		no : function(){
			 return;
		}
	});
}
function startFlow(){
	Ext.Msg.confirm(wcm.LANG['IFLOWCONTENT_2'] ||'您确实要将当前文档投入流转吗？', {
		yes : function(){
			_startDocInFlow(_params);
		},
		no : function(){
			 return;
		}
	});
}
function  _startDocInFlow(_params){
	var postData = {
		objectid: _params['ContentId']
	}
	//ge gfc add @ 2007-9-21 11:14 兼容在52中使用
	var sServiceId = _params['ServiceId']|| 'wcm6_document';
	BasicDataHelper.call(sServiceId, _params['MethodName']||'startDocumentInFlow', postData, true, function(){
		 window.location.reload(true);
	});
	return false;
}
function ceaseFlow(){
	var params = {
		ObjectIds: '<%=nFlowId%>',
		titles : '<%=CMyString.filterForJs(sDocTitle)%>',
		docs: [
			{id: <%=nFlowId%> , title: '<%=CMyString.filterForJs(sDocTitle)%>'}//TODO
		],
		ctype:  '<%=nContentType%>',
		cid:  '<%=nContentId%>',
		option: 'cease'
	};
	promptOption(wcm.LANG['IFLOWCONTENT_3'] || '结束文档流转', params);	
}
function promptOption(_sTitle,_params){
	var sTitle = _sTitle;
	var sUrl = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_option.html';
	wcm.CrashBoarder.get('Process_Option').show({
		title :  sTitle,
		src : sUrl,
		width: '480px',
		height: '350px',
		params : _params,
		maskable : true,
		callback : function(){
			window.location.reload(true);
		}
	});	
}
	
//-->
</script>
<%if(nShowOper == 1){%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="function_area" id="document_top_nav" style="padding-top:5px">
		<tr id="trOptions">
			<td align="left"  style="white-space: nowrap">
		<%
			if(bReInFlow == true){
		%>
				<div id="spReflow">
					<span style="cursor:pointer;" onClick="reFlow();return false;" _flag="workflow" href="#" _function="reflow" class="flow_reflow <%=(nContentType==605?"docnomal" : "docphoto")%>" WCMAnt:param="document_detail_show.jsp.reflow">重新流转</span> 

				</div>
		<%
			}
			if(bCanInFlow == true){
		%>

				<div id="spStartFlow">
					<span style="cursor:pointer;" onClick="startFlow();return false;" _flag="workflow" href="#" title="开始流转" _function="startFlow" class="flow_start <%=(nContentType==605?"docnomal" : "docphoto")%>" WCMAnt:param="document_detail_show.jsp.startFlow" WCMAnt:paramattr="title:document_detail_show.jsp.startFlow.title">开始流转</span>
				</div>

		<%
			}
			if(bStopFlow == true){
		%>
				<div id="spCease">
					<span style="cursor:pointer;" onClick="ceaseFlow();return false;" _flag="workflow" href="#" class="flow_cease <%=(nContentType==605?"docnomal" :"docphoto")%>" title="结束流转" _function="ceaseflow" WCMAnt:param="document_detail_show.jsp.ceaseFlow" WCMAnt:paramattr="title:document_detail_show.jsp.ceaseFlowtitle">结束流转</span>

				</div>
		<%
			}
		%>
			</td>																
		</tr>
	</table>
<%}%>
	<div id="divGunter">
		<%
			for(int i=oFlowDocs.size(); i > 0; i--){
				FlowDoc doc = (FlowDoc)oFlowDocs.getAt(i-1);
				boolean isCurrDoc = false;
				if(i == 1) isCurrDoc = true;
		%>
			<span class="gunter_node_common" id="gunter_node_<%=doc.getId()%>" _flag="<%=doc.getFlag()%>" <%if(isCurrDoc==true){%>style="BACKGROUND-IMAGE: url(../images/workflow/bg_tracing_s<%=doc.getFlag()%>.gif)"<%}%>  _worked="<%=doc.getWorkModal()%>">
				<span class="gunter_node_title" title="<%=LocaleServer.getString("flowdoc.label.posttime", "时间")%>:<%=doc.getPostTime().toString("MM-dd hh:mm")%> &#13;<%=LocaleServer.getString("message.label.receiver", "接收用户")%>:<%=doc.getPostUserTrueName()%>&#13;<%=LocaleServer.getString("flowdoc.label.postdesc", "意见")%>:<%=doc.getPostDesc()%>">
					<SPAN class="icon_flag_<%=doc.getFlag()%>"><%=doc.getFlagDesc()%></span>
				</span>
			</span>	
		<%}%>
	</div>
</div>
</BODY>
</HTML>