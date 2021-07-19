<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.ProcessConstants" %>
<%@ page import="com.trs.cms.process.FlowContentFactory" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.process.definition.FlowNode"%>
<%@ page import="com.trs.components.infoview.InfoViewDataHelper"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	int nFlowDocId = currRequestHelper.getInt("FlowDocId", 0);
	int nShowButton = currRequestHelper.getInt("ShowButton", 0);
	FlowDoc flowdoc = null;
	if(nFlowDocId > 0 && (flowdoc = FlowDoc.findById(nFlowDocId)) == null) {
		throw new WCMException(CMyString.format(LocaleServer.getString("workflow.process.render.4wcm52.label.errorinfo","处理流转文档失败！没有找到指定[FlowDocId= {0} ]参数的工作流轨迹！"),new int[]{nFlowDocId}));
	}
	int nDocumentId = 0;
	if(flowdoc != null){
		IFlowContent content = FlowContentFactory.makeFlowContent(flowdoc
						.getContentType(), flowdoc.getContentId());

		if (content == null || content.isDeleted()) {
				   throw new WCMException(CMyString.format(LocaleServer.getString("workflow.process.render.label.deletedinfo","您要处理的内容可能是给其它人的或者被删除了！ObjectIds= {0} ") ,new int[]{flowdoc.getPropertyAsInt("objId", 0)}));
		}
		nDocumentId = content.getSubinstanceId();
	}
	boolean bFromUrl = (flowdoc != null);
	boolean bStartNode = false;
	boolean bCanSelectSepNode = true;
	int nNodeModal = 0;
	if(flowdoc != null){
		FlowNode oNode = flowdoc.getNode();
		if(oNode!= null){
			bStartNode = oNode.isStartNode();
			bCanSelectSepNode = oNode.isBReasignSepNodes();
			nNodeModal = oNode.getWorkModal();
		}
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="workflow_process_4wcm52.jsp.title">TRS WCM 内容管理新篇章</TITLE>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSHashtable.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<script src="../../app/js/wcm52/CTRSArray.js"></script>
<script src="../../app/js/wcm52/CTRSString.js"></script>
<!--myimport-->
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
<script src="../../app/js/easyversion/crashboard.js"></script>
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<script src="workflow_process_render_wcm61.js"></script>
<script src="workflow_process_render_4wcm52.js"></script>
<script language="javascript">
<!--
	var bStartNode = '<%=bStartNode%>';
	var bCanSelectSepNode = '<%=bCanSelectSepNode%>';
//-->
</script>
<%
	//构造参数，然后进行数据绑定
	//System.out.println(nFlowDocId + ", " + flowdoc);
	if (bFromUrl){
		try{
			IFlowContent flow = flowdoc.getFlowContent();
%>
		<style>
		</style>
		<script>
			var params = {
				flowid: <%=flowdoc.getId()%>,
				title: '<%=CMyString.transDisplay(flow.getDesc())%>',
				nodeid: <%=flowdoc.getNodeId()%>,
				prenodeid:<%=flowdoc.getPreNodeId()%>,
				nodename: '<%=flowdoc.getNode().getName()%>',
				nodemodal:<%=nNodeModal%>,
				reworked: <%=(flowdoc.getFlag() == ProcessConstants.FLAG_BACK) ? "1" : "0"%>,
				refuse: <%=(flowdoc.getFlag() == ProcessConstants.FLAG_REFUSED) ? "1" : "0"%>,
				applyExtension:<%=(flowdoc.getFlag() == ProcessConstants.APPLY_EXTENSION) ? "1" : "0"%>,
				workmodal: <%=flowdoc.getWorkModal()%>,
				ctype: <%=flow.getContentType()%>,
				cid: <%=flow.getId()%>,
				accepted: <%=flowdoc.isAccepted() ? "1" : "0"%>,
				resubmited: false,
				takebacked: <%=(flowdoc.getFlag() == ProcessConstants.FLAG_TAKE_BACK) ? "1" : "0" %>
			}
			PageContext.FromUrl = true;
			document.title = params['title'] + (wcm.LANG['IFLOWCONTENT_33'] ||' - 处理流转文档');
			Event.observe(window, 'load', function(){
				window.initPage(params);
			});
		</script>

<%		
		}catch(Throwable ex){
			throw new WCMException(CMyString.format(LocaleServer.getString("workflow.process.render.label.errodealinfo","处理流转文档失败！读取[{0}]的工作流轨迹属性失败！"),new int[]{nFlowDocId}), ex);
		}
	}//end of "if"
%>
<%
	//获取文档
	Document currDocument = null;
	if(nDocumentId > 0){
		currDocument = Document.findById(nDocumentId);
		if(currDocument==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("workflow_process_render_4wcm52.jsp", "获取ID为[{0}]的文档失败！"),new int[]{nDocumentId}));
		}
	}
	else{
		currDocument = Document.createNewInstance();
	}

	//获取文档类型
	int nDocumentType = currDocument.getWCMType();

	//获取流转情况
	IFlowContent content = FlowContentFactory.makeFlowContent(nDocumentType,nDocumentId);
	if(content==null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("workflow_process_render_4wcm52.jsp.nocontentfond","指定的内容[Type= {0} , Id= {1}]没有找到"),new String[]{String.valueOf(nDocumentType),String.valueOf(nDocumentId)}));
	}	
	
	//获取指定文档的流转轨迹
	IFlowServer m_oFlowServer = (IFlowServer) DreamFactory.createObjectById("IFlowServer");
	FlowDocs oFlowDocs = m_oFlowServer.getFlowDocs(content, null);
%>
<STYLE>
	*{
		margin:0px;
		padding:0px;
	}
	html,body{
		width:100%;
		background: #fff;
		margin:0px !important;
		padding:0px !important;
		overflow-y:auto;
		overflow-x:hidden;
	}
	table{
		font-size: 12px;
	}
	.font_red{
		color: red;
	}
	.options_box{
		width: 100%;
		border-left:none;
		border-right:none;
	}
	.box_header_left{
		width:20px;	
	}
	.box_header_center{
		width:100px;	
		padding:0px;
	}
	.box_header_right{
		width:123px;	
	}

	.spOptionTip{
		width: 80px;
		color: gray;
	}
	.spOptionItem{
		margin-right: 10px;
	}
	.dvOption{
		width:100%;
		padding:3px 3px 0px 3px;
		border-bottom: aliceblue 1px solid;
	}
	.head_box{
		height:22px;	
		width:100%;
	}
	.box_body{
		width:230px;
		height:90px;
		overflow:hidden;
	}
	.process_body{
		height:auto;
		overflow:hidden;
	}
	#spSeperatorSelection{
		width:100%;
		white-space:normal;
		word-break:break-all;
		display:inline-block;
	}
	.commenttable{
		width:100%;
		height:100%;
		table-layout:fixed;
	}
	.OptionTitle{
		display:block;
		margin-bottom:10px;
	}
</STYLE>
</HEAD>
<BODY>
<script>
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
function getUserTrueName(truename, username){
	if(truename.trim()=='') return username;
	else return truename;
}
</script>
<div id="dvContainer" style="border: 0px solid #DEDCDD; text-align: left; margin-top: 5px;">
<div style="width:245px;padding: 0px; border: 1px solid silver;overflow: hidden; text-align: left;" id="divContent">
	<div class="options_box" style="height:122px;margin-top:0px;">
		<div class="head_box">
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_render_4wcm52.jsp.allpersonadvice">所有人意见</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body">
			<table border="0" cellspacing="0" cellpadding="5" style="table-layout:fixed;height:100%;width:225px;">
			<tbody>
				<tr>
					<td width="100%" style="WORD-WRAP:break-word;word-break:break-all;"><%
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
	<div class="options_box" style="margin-top: 10px;height:122px;">
		<div class="head_box">
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_4wcm52.jsp.desc">意见</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body">
			<table border="0" cellspacing="0" cellpadding="5"  style="table-layout:fixed;height:100%;width:225px;">
			<tbody>
				<tr>
					<td align="center" width="100%" height="100%"><textarea id="txtComment" style="width:225px;height:70px;"></textarea></td>
				</tr>
			</tbody>
			</table>	
		</div>
	</div>
	<div class="options_box">
		<div>
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_4wcm52.jsp.process">流转</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body process_body">
			<div id="divSeperatorSelection" class="dvOption" style="display: none; border-bottom: 0px solid silver; margin-bottom: 2px;width:100%;">
				<fieldset style="padding: 3px;" id="fdsSeperatorSelection"><legend WCMAnt:param="workflow_process_4wcm52.jsp.spSeperatorSelection">并联审批</legend>
					<span id="spSeperatorSelection"></span>
				</fieldset>
			</div>
			<div id="dvNextNode" class="dvOption" >
				<span class="spOptionTip" id="spOptionTip" WCMAnt:param="workflow_process_4wcm52.jsp.nextnode" style="WHITE-SPACE: nowrap">下一个节点：</span>
				<span>
					<select id="selNextNodes" style="width:100px;">
					</select>
				</span>
			</div>
			<div id="dvReferredToNodes" class="dvOption" style="display: none;">
				<span class="spOptionTip" style="WHITE-SPACE: nowrap" WCMAnt:param="workflow_process_render_4wcm52.jsp.transnode">转交给节点：</span>
				<span>
					<select id="selReferredToNodes" style="width:100px;">
					</select>
				</span>
			</div>
			<div id="dvWorkModalSepratorEndNodes" class="dvOption" style="display: none;">
				<span class="spOptionTip" style="WHITE-SPACE: nowrap">选择审批结束节点：</span>
				<span>
					<select id="selWorkModalSepratorEndNodes" style="width:100px;">
					</select>
				</span>
			</div>
<div id="dvTogetherInfo" style="display: none;">
			<div class="dvOption">
				<span class="spOptionTip" WCMAnt:param="workflow_process_4wcm52.jsp.spPassedUsers">已签的人：</span>
				<span id="spPassedUsers"></span>
			</div>
			<div class="dvOption">
				<span class="spOptionTip" WCMAnt:param="workflow_process_4wcm52.jsp.spUnpassedUsers">未签的人：</span>
				<span id="spUnpassedUsers"></span>
			</div>
</div>
<div id="divOptionalParams">
			<div class="dvOption">
				<span class="spOptionTip OptionTitle" WCMAnt:param="workflow_process_4wcm52.jsp.notifyway">&nbsp;通知方式：</span>
				<span>
					<span class="spOptionItem"><input type="checkbox" id="chk_email" value="email"><span WCMAnt:param="workflow_process_4wcm52.jsp.email">邮件</span></span>
					<span class="spOptionItem"><input type="checkbox" id="chk_message" value="message"><span WCMAnt:param="workflow_process_4wcm52.jsp.message">在线短消息</span> </span>
					<span class="spOptionItem"><input type="checkbox" id="chk_sms" value="sms"><span WCMAnt:param="workflow_process_4wcm52.jsp.sms">手机短信</span></span>
				</span>
			</div>
			<div class="dvOption" style="margin-top:0px;">
				&nbsp;<span class="OptionTitle"><span WCMAnt:param="workflow_process_4wcm52.jsp.dealuser">处理人员：</span><span id="sp_asRule" style="display:none"><input id="cb_asRule" name="cb_asRule" type="checkbox"><label for="cb_asRule" WCMAnt:param="workflow_process_render_4wcm52.jsp.cbasrule">沿用规则</label></span>
				</span>
				<span id="spUsers"></span>
				<span id="SpResign">
						(<a href="#" onclick="reasignUsers();return false;" id="lnkResign" WCMAnt:param="workflow_process_4wcm52.jsp.reasignUsers">指定</a>)
				</span>
				<textarea id="template_users" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<input name="selectedUserIds" value="{#UserId}" type="checkbox">
						<span _uid="{#UserId}" _uname="{#UserName}" _truename="{@getUserTrueName(#TrueName,#UserName)}" title="{@getUserTrueName(#TrueName,#UserName)}">{@getUserTrueName(#TrueName,#UserName)}</span>&nbsp;&nbsp;
					</for>
				</textarea>
				<textarea id="template_users1" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<span _uid="{#UserId}" _uname="{#UserName}" _truename="{@getUserTrueName(#TrueName,#UserName)}" title="{@getUserTrueName(#TrueName,#UserName)}">{@getUserTrueName(#TrueName,#UserName)}</span>&nbsp;&nbsp;
					</for>
				</textarea>
				<textarea id="template_users2" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<span _uid="{#UserId}" _uname="{#UserName}" _truename="{@getUserTrueName(#TrueName,#UserName)}" title="{@getUserTrueName(#TrueName,#UserName)}">{@getUserTrueName(#TrueName,#UserName)}</span>&nbsp;&nbsp;
					</for>
				</textarea>
			</div>
</div>
		</div>
	</div>
</div>
<div id="dvNoUser" style="display: none;">
	<span style="color: gray" WCMAnt:param="workflow_process_4wcm52.jsp.none">无</span>
</div>
</BODY>
</HTML>