<%--
/** Title:			Contentextfield_addedit.jsp
 *  Description:
 *		扩展字段位置设置页面
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nObjectId = currRequestHelper.getInt("ObjectId",0);
	ContentExtField currContentExtField = ContentExtField.findById(nObjectId);
	if(currContentExtField == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nObjectId),WCMTypes.getLowerObjName(ContentExtField.OBJ_TYPE)}));
	}

	//int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	//Channel currChannel = Channel.findById(nChannelId);
	Channel currChannel = (Channel)currContentExtField.getHost();
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nChannelId),WCMTypes.getLowerObjName(Channel.OBJ_TYPE)}));
	}
    int nChannelId = currChannel.getId();
    
//5.权限校验

//6.业务代码
	String sWhere = "objType = 101 and objid = " + nChannelId;
	WCMFilter filter = new WCMFilter("", sWhere, "extorder desc", "");
	ContentExtFields contentExtFields = ContentExtFields.openWCMObjs(ContextHelper.getLoginUser(),filter);
	int nSize = contentExtFields.size();
	int nExtOrder = contentExtFields.indexOf(nObjectId);
//7.结束
	out.clear();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style>
	body{
		font-size:12px;
		overflow:auto;
	}
	div{
		width:100%;
		display:block;
		line-height:24px;
		height:24px;
	}
	SPAN{
		display:inline;
	}
	li{
		list-style-type: circle;
	}
	.num{
		font-weight:bold;
		color:red;
	}
</style>

<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/document.js"></script>
<!--FloatPanel Inner Start-->
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script>
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'docpositionset',
			name : wcm.LANG.DOCUMENT_PROCESS_31 || '确定'
		}],
		size : [400,120]
	};	
</script>
<script>

function docpositionset(){
	var sURL="contentextfield_position_set_dowith.jsp";
	var frmData = $('frmData');
	if(frmData.DocumentOrderMode.value != 1){
		frmData.DocOrder.value = frmData.DocumentOrderMode.value;
	}else{
		if(!/^-?\d+$/.test($F('DocOrder'))){
			Ext.Msg.alert(wcm.LANG.DOCUMENT_PROCESS_38 || "请输入一个整数.", function(){
				try{
					setTimeout(function(){
						$('DocOrder').focus();
					}, 10);
				}catch(error){
				}
			});
			return false;
		}	
		var currMaxOrder = <%=nSize%>;
		var currOrder = parseInt(frmData.DocOrder.value, 10);
		frmData.DocOrder.value = (currOrder > currMaxOrder ? currMaxOrder : (currOrder < -1 ? -1 : currOrder));
	}
	currOrder = frmData.DocOrder.value;
    para="ObjectId=<%=nObjectId%>&ChannelId=<%=nChannelId%>&DocOrder="+frmData.DocOrder.value; 
    new Ajax.Request(sURL, {  
		method: 'get', 
		contentType : 'application/x-www-form-urlencoded',
		parameters:para,
		onSuccess:function(transport){
		   notifyFPCallback(transport);
		   FloatPanel.close();
	   }
	}); 
	return false;
}
</script>
</head>
<body>  
<SCRIPT LANGUAGE="JavaScript">
<!--
function onModeChannge(_nMode){
	var oRegion = document.getElementById("DocumentOrderRegion");
	if(_nMode == 1) $("DocOrder").value = <%=(nExtOrder+1)%>;
	oRegion.style.display = _nMode == 1? "inline":"none";
}
//-->
</SCRIPT>
<form  name="frmData" action="contentextfield_position_set_dowith.jsp" onsubmit="return false;">
<INPUT TYPE="hidden" name="DocumentId" value="<%=nObjectId%>">
<INPUT TYPE="hidden" name="ChannelId" value="<%=nChannelId%>">
	<table width="100%" cellspacing="1" cellpadding="2" style="marging-top:10px; font-size:12px">
		<tr>
			<td align="left"><span WCMAnt:param="contentextfield_position_set.jsp.for">为扩展字段</span><font color=blue>&nbsp;[<%=CMyString.filterForHTMLValue(currContentExtField.getDesc())%></font>]&nbsp;<span WCMAnt:param="contentextfield_position_set.jsp.adjustOrder">调整顺序：</span> </td> 
		</tr>
		<!--~--- ROW4 ---~-->
		<tr>
			<td>
				<span WCMAnt:param="contentextfield_position_set.jsp.let">让扩展字段</span><span WCMAnt:param="contentextfield_position_set.jsp.inChannel">处于栏目</span>&nbsp;[<font color=blue><%=CMyString.filterForHTMLValue(currChannel.getDesc())%></font>]&nbsp;
				<select name="DocumentOrderMode" onchange="onModeChannge(document.frmData.DocumentOrderMode.value);">
					<option value="0" WCMAnt:param="contentextfield_position_set.jsp.top">最前面</option>
					<option value="-1" WCMAnt:param="contentextfield_position_set.jsp.latest">最后面</option>
					<option value="1" WCMAnt:param="contentextfield_position_set.jsp.currPosition">指定位置</option>
				</select><BR><BR>
				<span id="DocumentOrderRegion" style="display:none"><span WCMAnt:param="contentextfield_position_set.jsp.tip1">调整至：</span><input type="text" name="DocOrder" value="<%=nExtOrder+1%>" style="border:1px gray solid;width:60px"><span WCMAnt:param="contentextfield_position_set.jsp.tip2">(总数为：</span><span class="num"><%=nSize%></span>)</span>
			</td>
		</tr>		
		<!--~- END ROW4 -~-->
		<!--~--- ROW5 ---~-->
		<tr>
			<td align="center" height="5px">&nbsp; </td>
		<tr>
		<!--~- END ROW5 -~-->
	</table>
</form>
</body>
</html>