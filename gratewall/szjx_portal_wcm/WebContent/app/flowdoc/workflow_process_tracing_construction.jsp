<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../console/include/error.jsp"%>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.cms.process.engine.FlowContentHelper" %>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../console/include/public_server.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String sUserAgent = request.getHeader("User-Agent");
	if(sUserAgent != null && sUserAgent.indexOf("8.0") < 0 ){
%>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<%
}
%>
<title WCMAnt:param="workflow_process_tracing_construction.jsp.title">TRSWCM 查看流转情况</title>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" /> 
<script LANGUAGE="JavaScript">
	var m_sLoginUser = '<%= loginUser.getName()%>';
</script>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<script src="../../app/js/wcm52/CTRSArray.js"></script>
<script src="../js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/iflowcontent.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../js/data/locale/system.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.workflow/WorkFlow.js"></script>
<SCRIPT src="../../app/js/source/wcmlib/core/LockUtil.js"></SCRIPT>
<!-- CarshBoard Outer Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
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
</script>
<style type="text/css">
	body, table{
		font-size: 12px;
		margin: 0;
		padding: 0;
	}
	.attr_title{
		font-size: 14px;
	}
	#spTitle{
		font-weight: bold; 
		font-size: 16px; 
		text-align: center;
		line-height: 20px;
		height: 20px;	
	}
	#spFlowName{
		font-weight: bold;
		color: red
	}
	#spCrUser, #spCrTime{
		color: #010101;
	}
</style>
<script language="javascript" src="../system/status_create.jsp" type="text/javascript"></script>
<script language="javascript" src="workflow_process_tracing_construction.js" type="text/javascript"></script>
</HEAD>

<body style="background: #ffffff">
<div id="dy_adjust">
</div>
<script>
	function $style(_sCssText){
		var eStyle=document.createElement('STYLE');
		eStyle.setAttribute("type", "text/css");
		if(eStyle.styleSheet){// IE
			eStyle.styleSheet.cssText = _sCssText;
		} else {// w3c
			var cssText = document.createTextNode(_sCssText);
			eStyle.appendChild(cssText);
		}
		return eStyle;
	}
	function $removeChilds(_node){
		_node.innerHTML = "";
	}
	function adjustScale(){
		var sStyleId = 'dy_adjust';
		var eStyleDiv = $(sStyleId);
		var nBoxWidth = Element.getDimensions(document.body)["width"] - 20;
		var nBoxHeight = Element.getDimensions(document.body)["height"] - 120;
		var cssStr = '#flowEditor, #tdFlowEditor{\
			width: '  + (nBoxWidth) + 'px;\
			height: ' + (nBoxHeight) + 'px;\
		}';
		var eStyle = $style(cssStr);
		$removeChilds(eStyleDiv);
		eStyleDiv.appendChild(eStyle);
	}
	adjustScale();
</script>
<center>
<div id="divContainer" style="background: silver; padding:1px; margin-top: 0px; width: 100%; text-align: center">
<input TYPE="hidden" name="FlowId" value="0">
<table id="tblEditorContainer" style="background: #f6f6f6;" width="100%" height="100%" border="0" cellpadding="2" cellspacing="5" align="center">
	<tr>
		<td width="100%" valign="top" align="left" style="border-bottom: solid 1px silver">
			<div id="divHeader" style="text-align: center">
				<span id="spTitle"></span>
			</div>
			<div style="font-family: georgia; margin-top: 5px; color: gray; padding-bottom: 5px; text-align: right">
				(<span WCMAnt:param="workflow_process_tracing_construction.jsp.cruser">创建者: </span><span id="spCrUser"></span><span WCMAnt:param="workflow_process_tracing_construction.jsp.crtime">&nbsp&nbsp创建时间: </span><span id="spCrTime"></span><span WCMAnt:param="workflow_process_tracing_construction.jsp.flowname">&nbsp&nbsp工作流: </span><span id="spFlowName">none</span>)
			</div>
		</td>
	</tr>
	<tr>
		<td id="tdFlowEditor" width="100%" valign="top" align="center" style="padding-top: 0px; padding-left:2px; cursor: pointer">
			<div id="divFlowEditor" style="text-align: center; margin-top: 8px;">
				<OBJECT id="flowEditor" style="left: 0px; top: 0px; border: 0px solid gray" codeBase="../workflow/WorkFlowEditor.ocx#Version=1,0,0,25" classid="clsid:0ECFCF01-C9C8-40AF-9542-14C4EACF187D">
					<PARAM NAME="_Version" VALUE="65536">
					<PARAM NAME="_ExtentX" VALUE="21167">
					<PARAM NAME="_ExtentY" VALUE="15875">
					<PARAM NAME="_StockProps" VALUE="0">
				</OBJECT>
			</div>
		</td>
	</tr>
</table>
</div>
<div id="divTips" style="display: ; width: 100%; padding-top: 10px; font-size: 12px; color: gray; text-align: right; padding-right: 5px; background:#DEDCDD; height: 60px;">
	<span id="spCloseWin">&nbsp;&nbsp;&nbsp;<a href="#" onclick="doCancel(); return false" WCMAnt:param="workflow_process_tracing_construction.jsp.close">关闭窗口</a></span>
</div>
</center>
<script language="javascript">
<!--
	Event.observe(window, 'resize', adjustScale, false);
//-->
</script>
</body>
</html>