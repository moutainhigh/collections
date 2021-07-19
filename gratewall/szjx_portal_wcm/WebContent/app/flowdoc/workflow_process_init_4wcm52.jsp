<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.ProcessConstants" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	int nFlowId = currRequestHelper.getInt("FlowId", 0);
	Flow flow = null;
	if(nFlowId <= 0 || (flow = Flow.findById(nFlowId)) == null) {
		throw new WCMException(CMyString.format(LocaleServer.getString("workflow.process.init.label.erroinfo","处理流转文档失败！没有找到指定[FlowId= {0} ]参数的工作流！"),new int[]{nFlowId}));
	}
	String sFlowContextInfo = null;
	String sErrMsg = null;
	try{
		sFlowContextInfo = WCMProcessServiceHelper.makeFlowContextInfo(flow);
	}catch(Exception ex){
		sErrMsg = CMyString.format(LocaleServer.getString("workflow.process.init.for.doc.label.erroinfo", "您的工作流设计有误,将导致文档无法正常流转，详细信息如下：{0}"),new String[]{ex.getMessage()});
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="workflow_process_init_4wcm52.jsp.title">TRS WCM 内容管理新篇章</TITLE>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/easyversion/cssrender.js"></script>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSHashtable.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<script src="../../app/js/wcm52/CTRSArray.js"></script>
<script src="../../app/js/wcm52/CTRSString.js"></script>
<script src="../js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/iflowcontent.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.workflow/WorkFlow.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<script src="workflow_process_init_4wcm52.js"></script>
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
		margin-right: 3px;
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
	.errorMsg{
		font-size: 15px;
		color: red;
		width: 200px;
		padding-left: 5px;
	}
	.OptionTitle{
		display:block;
		margin-top:5px;
		margin-bottom:5px;
	}
</STYLE>
</HEAD>
<BODY align="left">
<%
	if(sErrMsg!=null){
%>
	<div class="errorMsg"><%=sErrMsg%></div>
	<script language="javascript">
	<!--
		if(window.parent.m_bFlowDocInFlow)window.parent.m_bFlowDocInFlow = false;
		if(window.parent.m_bNewDocInFlow)window.parent.m_bNewDocInFlow = false;

	//-->
	</script>
<%
}else{	
%>
<div id="dvContainer" style="border: 0px solid #DEDCDD; text-align: left; margin-top: 5px;">
<div style="width:245px;padding: 0px; border: 1px solid silver;overflow: hidden; text-align: left;" id="divContent">
	<div class="options_box" style="margin-top: 0px;height:122px;">
		<div>
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_init_4wcm52.jsp.desc">意见</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body">
			<table border="0" cellspacing="0" cellpadding="5"  style="table-layout:fixed;height:100%;width:100%;">
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
			<span class="box_header_center" WCMAnt:param="workflow_process_init_4wcm52.jsp.processs">流转</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body process_body">
			<div id="dvNextNode" class="dvOption">
				<span class="spOptionTip" id="spOptionTip" WCMAnt:param="workflow_process_init_4wcm52.jsp.receivenode">接收节点：</span>
				<span id="spFirstNodeName" style="margin-top: 5px; width: 100%;"></span>
			</div>
			<div class="dvOption">
				<span class="spOptionTip OptionTitle" WCMAnt:param="workflow_process_init_4wcm52.jsp.notifyway">通知方式：</span>
				<span>
					<span class="spOptionItem"><input type="checkbox" id="chk_email" value="email">&nbsp;<span WCMAnt:param="workflow_process_init_4wcm52.jsp.email">邮件</span></span>
					<span class="spOptionItem"><input type="checkbox" id="chk_message" value="message"><span WCMAnt:param="workflow_process_init_4wcm52.jsp.message">在线短消息 </span></span>
					<span class="spOptionItem"><input type="checkbox" id="chk_sms" value="sms"><span WCMAnt:param="workflow_process_init_4wcm52.jsp.sms">手机短信</span></span>
				</span>
			</div>
			<div class="dvOption">
				<span class="spOptionTip OptionTitle" WCMAnt:param="workflow_process_init_4wcm52.jsp.dealpeaple">处理人员：</span><span id="sp_asRule" style="display:none"><input id="cb_asRule" name="cb_asRule" type="checkbox"><label for="cb_asRule">沿用规则</label></span><br>
				<span id="spUsers"></span>
				<span>
						(<a href="#" onclick="reasignUsers()" id="lnkResign" WCMAnt:param="workflow_process_init_4wcm52.jsp.resign">指定</a>)
				</span>
				<textarea id="template_users" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<input name="selectedUserIds" value="{#UserId}" type="checkbox" _name="{#UserName}">
						<span _uid="{#UserId}" _uname="{#UserName}" title="{#UserName}">{#UserName}</span>&nbsp;&nbsp;
					</for>
				</textarea>
				<textarea id="template_users1" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<span _uid="{#UserId}" _uname="{#UserName}" _truename="{@getUserTrueName(#TrueName,#UserName)}" title="{@getUserTrueName(#TrueName,#UserName)}">{@getUserTrueName(#TrueName,#UserName)}</span>&nbsp;&nbsp;
					</for>
				</textarea>
			</div>
		</div>
	</div>
</div>
<script language="javascript">
<!--
	PageContext.loadPage('<%=sFlowContextInfo%>');
//-->
</script>
<%
	}
%>
</BODY>
</HTML>