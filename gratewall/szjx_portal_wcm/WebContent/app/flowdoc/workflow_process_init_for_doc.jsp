<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
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
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="workflow_process_init_4wcm52.jsp.title">TRS WCM 内容管理新篇章</TITLE>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" />
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
<script src="workflow_process_init_for_doc.js"></script>
<STYLE>
	table{
		font-size: 12px;
	}
	.font_red{
		color: red;
	}
	.spOptionItem{
		margin-right: 3px;
	}
	.dvOption{
		padding: 3px;
		margin-top: 10px;
	}
	body{
		margin: 0;
		padding: 0;
		width:100%;
	}
	.options_box{
		border: 0px solid silver; 
		margin-top: 0px;
		background-color:#EDEDED;
		width:100%;
	}
	.box_body{
		background: #EDEDED;
		padding: 5px;
		overflow: hidden;
		height:auto;
	}
	.errorMsg{
		font-size: 15px;
		color: red;
		width: 200px;
		padding-left: 5px;
	}
</STYLE>
</HEAD>
<BODY align="center">
<script language="javascript">
<!--
	var bCanInFlow = true;
//-->
</script>
<%
	if(sErrMsg!=null){
%>
	<div class="errorMsg"><%=sErrMsg%></div>
	<script language="javascript">
	<!--
		bCanInFlow = false;

	//-->
	</script>
<%
}else{	
%>
<center>
<div id="dvContainer" style="border: 0px solid #DEDCDD; text-align: center;">
<div style="border: 0px solid;overflow:hidden; text-align: left;" id="divContent">
	<div class="options_box">
		<div class="box_body">
			<div id="dvNextNode" class="dvOption">
				<span class="spOptionTip" id="spOptionTip" WCMAnt:param="workflow_process_init_4wcm52.jsp.receivenode" style="WHITE-SPACE: nowrap">接收节点：</span>
				<span id="spFirstNodeName" style="margin-top: 5px;"></span>
			</div>
			<div class="dvOption" style="display:none">
				<span class="spOptionTip" WCMAnt:param="workflow_process_init_4wcm52.jsp.notifyway">通知方式：</span><br>
				<span style="width:100%;display:inline-block;white-space:normal;word-break:break-all;">
					<span class="spOptionItem"><input type="checkbox" id="chk_email" value="email">
					<span WCMAnt:param="workflow_process_init_4wcm52.jsp.email">邮件</span></span>
					<span class="spOptionItem"><input type="checkbox" id="chk_message" value="message">
					<span WCMAnt:param="workflow_process_init_4wcm52.jsp.message">在线短消息 </span></span>
					<span class="spOptionItem"><input type="checkbox" id="chk_sms" value="sms">
					<span WCMAnt:param="workflow_process_init_4wcm52.jsp.sms">手机短信</span></span>
				</span>
			</div>
			<div class="dvOption">
				<span class="spOptionTip" WCMAnt:param="workflow_process_init_4wcm52.jsp.dealpeaple">处理人员：</span><span id="sp_asRule" style="display:none"><input id="cb_asRule" name="cb_asRule" type="checkbox"><label for="cb_asRule">沿用规则</label></span><br>
				<span id="spUsers"></span>
				<span>
						(<a href="#" onclick="reasignUsers();return false;" id="lnkResign" WCMAnt:param="workflow_process_init_4wcm52.jsp.resign">指定</a>)
				</span>
				<textarea id="template_users" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<input name="selectedUserIds" value="{#UserId}" type="checkbox" _name="{#UserName}">
						<span _uid="{#UserId}" _uname="{#UserName}" title="{#UserName}">{#UserName}</span>&nbsp;&nbsp;
					</for>
				</textarea>
				<textarea id="template_users1" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<span _uid="{#UserId}" _uname="{#UserName}" title="{#UserName}">{#UserName}</span>&nbsp;&nbsp;
					</for>
				</textarea>
			</div>
		</div>
	</div>
</div>
</center>
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