<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.ProcessConstants" %>
<%@ page import="com.trs.cms.process.FlowContentFactory" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.process.definition.FlowNode"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<%
try{
%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	int nFlowDocId = currRequestHelper.getInt("FlowDocId", 0);
	int nshowButtons = currRequestHelper.getInt("_FROMCB_", 0);
	String bResubmited = currRequestHelper.getString("resubmited");
	
	FlowDoc flowdoc = null;
	flowdoc = FlowDoc.findById(nFlowDocId);
	if(nFlowDocId > 0 && (flowdoc == null)) {
		throw new WCMException(CMyString.format(LocaleServer.getString("workflow.process.render.label.erroinfo","处理流转文档失败！没有找到指定[FlowId={0}]参数的工作流轨迹！"),new int[]{nFlowDocId}));
	}
	IFlowContent content = null;
	if(flowdoc != null){
		content = FlowContentFactory.makeFlowContent(flowdoc
						.getContentType(), flowdoc.getContentId());

		if (content == null || content.isDeleted()) {
				   throw new WCMException(CMyString.format(LocaleServer.getString("workflow.process.render.label.deletedinfo","您要处理的内容可能是给其它人的或者被删除了！ObjectIds= {0} "), new int[]{flowdoc.getPropertyAsInt("objId", 0)}));
		}		
	}
	boolean bFromUrl = (flowdoc != null);
	boolean bStartNode = false;
	if(flowdoc != null){
		FlowNode oNode = flowdoc.getNode();
		if(oNode!= null)
			bStartNode = oNode.isStartNode();
	}
%>

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="workflow_process_render.jsp.title">TRS WCM 内容管理新篇章</TITLE>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSHashtable.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<script src="../../app/js/wcm52/CTRSArray.js"></script>
<script src="../../app/js/wcm52/CTRSString.js"></script>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/easyversion/list.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/iflowcontent.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.workflow/WorkFlow.js"></script>
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
 <!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<script src="workflow_process_render_wcm61.js"></script>
<script src="workflow_process_render_4wcm52.js"></script>
<script language="javascript">
<!--
	var bStartNode = '<%=bStartNode%>';
//-->
</script>
<%
	IFlowContent flow = null;
	if (bFromUrl){
		try{
			flow = flowdoc.getFlowContent();
%>
<%		
		}catch(Throwable ex){
			throw new WCMException(CMyString.format(LocaleServer.getString("workflow.process.render.label.errodealinfo","处理流转文档失败！读取[{0}]的工作流轨迹属性失败！"),new int[]{nFlowDocId}), ex);
		}
	}//end of "if"
%>
<%
	//获取指定文档的流转轨迹
	IFlowServer m_oFlowServer = (IFlowServer) DreamFactory.createObjectById("IFlowServer");
	FlowDocs oFlowDocs = m_oFlowServer.getFlowDocs(content, null);
%>

<STYLE>

	html,body{
		width:100%;
		height:100%;
	}
	table{
		font-size: 12px;
	}
	.font_red{
		color: red;
	}
	.spOptionTip{
		width: 80px;
	}
	.dvOption{
		height: 20px;
		padding: 3px;
		border-bottom: aliceblue 1px solid;
	}
	body{
		margin: 0px;
		padding: 0px;
		background: #fff;
	}
	#dvContainer{
		margin:0px 5px;
		padding:0;
	}
	.box_header_right{
		width:66.5% !important;
	}
	#txtComment{
		width: 100%;
		height: 100%;
	}
	#txtallComment{
		width: 100%;
		height: 100%;
	}
	.options_box_comment{
		height:125px;
	}
	.head_option{
		height:25px;
	}
	.box_header_center{
		padding:0px;
	}
	.box_body{
		text-align:left;
	}
</STYLE>
</HEAD>
<BODY align="center">
<div id="dy_adjust">
</div>
<script>
	function forceReplain(el){
		Element.removeClassName(el, 'xx');
		Element.addClassName(el, 'xx');
	}
	function getUserTrueName(truename, username){
		if(truename.trim()=='') 
			return username;
		else 
			return truename;
	}
	Object.clone = function(_o,_bDeep){
	if(typeof _o!='object'){
		return _o;
	}
	if(!_bDeep){
		if(Array.isArray(_o)){
			return Array.apply(null,_o);
		}
		return Object.extend({},_o);
	}
	else{
		var oReturn = null;
		if(Array.isArray(_o)){
			oReturn = [];
			for(var i=0;i<_o.length;i++){
				oReturn.push(Object.clone(_o[i],true));
			}
		}
		else{
			oReturn = {};
			for(var prop in _o){
				oReturn[prop] = Object.clone(_o[prop],true);
			}
		}
		return oReturn;
	}
};
</script>
<center>
<div id="dvContainer" style="border: 1px solid #DEDCDD; text-align: center; margin-top: 5px;">
<div style="padding: 5px; border: 0px solid silver;overflow: hidden; text-align: center; margin-left: 3px;height:100%;" id="divContent">
	<div class="options_box options_box_comment" style="margin-top: 10px;">
		<div class="head_option">
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_render.jsp.allpersonadvice">所有人意见</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body" style="height: 90px;">
			<table border="0" cellspacing="0" cellpadding="5" height="100%" width="100%">
			<tbody>
				<tr>
					<td width="100%" style="WORD-WRAP: break-word;word-break:break-all;vertical-align:top;"><%
			for(int i=0;i< oFlowDocs.size();i++){
				FlowDoc doc = (FlowDoc)oFlowDocs.getAt(i);
				if(doc.getPostDesc()==null || doc.getPostDesc().equals("")) continue;
			else {%>
<%=doc.getPostUserTrueName()%>:<br/>
<%=CMyString.transDisplay(doc.getPostDesc())%><%}%><br/><%}%>
					 &nbsp;</td>
				</tr>
			</tbody>
			</table>
		</div>
	</div>
	<div class="options_box options_box_comment" style="margin-top: 10px;">
		<div class="head_option">
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_render.jsp.desc">意见</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body" style="height: 90px;overflow:hidden;">
			<table border="0" cellspacing="0" cellpadding="5" height="100%" width="100%;">
			<tbody>
				<tr>
					<td align="center" width="100%"><textarea id="txtComment" rows="5"></textarea></td>
				</tr>
			</tbody>
			</table>
		</div>
	</div>
	<div class="options_box" style="margin-top:10px;">
		<div class="head_option">
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_render.jsp.process">流转</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body" style="height:133px;overflow:auto;" onscroll="forceReplain('selNextNodes');">
			<div id="divSeperatorSelection" class="dvOption" style="display: none; border-bottom: 0px solid silver; margin-bottom: 2px;">
				<fieldset style="padding: 3px;" id="fdsSeperatorSelection"><legend WCMAnt:param="workflow_process_render.jsp.fdsSeperatorSelection">并联审批</legend>
					<span id="spSeperatorSelection"></span>
				</fieldset>
			</div>
			<div id="dvNextNode" class="dvOption" style="padding-top:20px;">
				<span class="spOptionTip" id="spOptionTip" WCMAnt:param="workflow_process_render.jsp.nextnode_a" style="WHITE-SPACE: nowrap">下一个节点：</span>
				<span>
					<select id="selNextNodes" style="width:100px;">
					</select>
				</span>
			</div>
			<div id="dvReferredToNodes" class="dvOption" style="display: none;">
				<span class="spOptionTip" style="WHITE-SPACE: nowrap" WCMAnt:param="workflow_process_render.jsp.spoptiontip">打回给节点：</span>
				<span>
					<select id="selReferredToNodes">
					</select>
				</span>
			</div>
<div id="dvTogetherInfo" style="display: none;">
			<div class="dvOption">
				<span class="spOptionTip" WCMAnt:param="workflow_process_render.jsp.spPassedUsers">已签的人：</span>
				<span id="spPassedUsers"></span>
			</div>
			<div class="dvOption">
				<span class="spOptionTip" WCMAnt:param="workflow_process_render.jsp.spUnpassedUsers">未签的人：</span>
				<span id="spUnpassedUsers"></span>
			</div>
</div>
<div id="divOptionalParams">
			<div class="dvOption">
				&nbsp;<span class="spOptionTip"  WCMAnt:param="workflow_process_render.jsp.notifyway">通知方式：</span>
				<span>
					<input type="checkbox" id="chk_email" value="email">&nbsp;<span WCMAnt:param="workflow_process_render.jsp.email">邮件</span> &nbsp;&nbsp;
					<input type="checkbox" id="chk_message" value="message">&nbsp;<span  WCMAnt:param="workflow_process_render.jsp.message">在线短消息</span> &nbsp;&nbsp;
					<input type="checkbox" id="chk_sms" value="sms">&nbsp;<span WCMAnt:param="workflow_process_render.jsp.sms">手机短信</span> &nbsp;&nbsp;
				</span>
			</div>
			<div class="dvOption">
				<span class="spOptionTip">&nbsp;<span WCMAnt:param="workflow_process_render.jsp.dealuser">处理人员：</span><span id="sp_asRule" style="display:none"><input id="cb_asRule" name="cb_asRule" type="checkbox"><label for="cb_asRule" WCMAnt:param="workflow_process_render.jsp.cbasrule">沿用规则</label></span>
				</span>
				<span id="spUsers"></span>
				<span id="SpResign">
						(<a href="#" onclick="reasignUsers();return false;" id="lnkResign" WCMAnt:param="workflow_process_render.jsp.reasignUsers">指定</a>)
				</span>
				<textarea id="template_users" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<input name="selectedUserIds" value="{#UserId}" type="checkbox">
						<span _uid="{#UserId}" _uname="{#UserName}" _truename="{@getUserTrueName(#TrueName,#UserName)}" title="{@getUserTrueName(#TrueName,#UserName)}">{@getUserTrueName(#TrueName,#UserName)}</span>&nbsp;
					</for>
				</textarea>
				<textarea id="template_users2" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<span _uid="{#UserId}" _uname="{@getUserTrueName(#TrueName,#UserName)}" _truename="{@getUserTrueName(#TrueName,#UserName)}" title="{@getUserTrueName(#TrueName,#UserName)}">{@getUserTrueName(#TrueName,#UserName)}</span>&nbsp;&nbsp;
					</for>
				</textarea>
			</div>
</div>
		</div>
	</div>
</div>
</div>
<div id="dvNoUser" style="display: none;">
	<span style="color: gray" WCMAnt:param="workflow_process_render.jsp.none">无</span>
</div>
</center>
<%
	if(nFlowDocId > 0){
%>

<script>
	var params = {
		flowid: <%=flowdoc.getId()%>,
		title: '<%=CMyString.filterForJs(flow.getDesc())%>',
		nodeid: <%=flowdoc.getNodeId()%>,
		nodename: '<%=flowdoc.getNode()!=null?flowdoc.getNode().getName():""%>',
		reworked: <%=(flowdoc.getFlag() == ProcessConstants.FLAG_BACK) ? "1" : "0"%>,
		workmodal: <%=flowdoc.getWorkModal()%>,
		ctype: <%=flow.getContentType()%>,
		cid: <%=flow.getId()%>,
		accepted: <%=flowdoc.isAccepted() ? "1" : "0"%>,
		resubmited: <%=bResubmited%>,
		takebacked: <%=(flowdoc.getFlag() == ProcessConstants.FLAG_TAKE_BACK) ? "1" : "0" %>
	}
	PageContext.FromUrl = true;
	document.title = params['title'] + (wcm.LANG['IFLOWCONTENT_33'] || ' - 处理流转文档');
	initPage(params);
</script>
<%
	}//end if
%>
</BODY>
</HTML>
<%
}catch(Throwable tx){
	throw tx;
}
%>