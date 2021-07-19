<%--
/** Title:			Document_addedit.jsp
 *  Description:
 *		栏目位置设置页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2006-03-28 19:56:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-28 / 2006-03-28
 *	Update Logs:
 *		TRS WCM 5.2@2006-03-28 产生此文件
 *
 *  Parameters:
 *		see document_position_set.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.presentation.util.ResponseHelper" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_processor.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//1.初始化(获取数据)
	RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	currRequestHelper.doValid();
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.channel", "没有找到ID为{0}的栏目"), new int[]{nChannelId}));
	}
	int nStatus = currChannel.getStatus();
	if(nStatus < 0){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("channel_position_set.object.not.found", "当前栏目[ID={0}]已经被删除了."), new String[]{String.valueOf(currChannel.getId()), WCMTypes.getLowerObjName(Channel.OBJ_TYPE)}));
	}
//2.获取兄弟栏目列表
	processor.setAppendParameters(new String[]{
		"pagesize", "-1", 
		"selectfields", "ChannelId,ChnlDesc,ChnlOrder",
		"excludetoporpic", "true"
	});
	processor.setEscapeParameters(new String[]{"channelid", "siblingchannelid", "parentid", "channelid"});
	processor.setSelectParameters(new String[]{"siblingchannelid", "parentid", "channelid", "siteid"}, true);
	Channels oSiblings = (Channels) processor.excute("channel", "query");	
//3.结束
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
<script src="../js/data/locale/channel.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
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
			cmd : 'chnlpositionset',
			name : wcm.LANG.CHANNEL_TRUE || '确定'
		}],
		size : [400,120]
	};	
</script>
<script>
function chnlpositionset(){
	var sURL="channel_position_set_dowith.jsp";
	var frmData = $('frmData');
	currOrder = frmData.ChnlOrder.value;
	var oldOderValue = <%=currChannel.getOrder()%>;
	if(currOrder == oldOderValue) return false;
    para="ChannelId=<%=nChannelId%>&newChnlOrder=" + currOrder; 
    new Ajax.Request(sURL, {  
		method: 'get', 
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
	oRegion.style.display = _nMode == 1? "inline":"none";
}
//-->
</SCRIPT>
<form  name="frmData" action="document_position_set_dowith.jsp" onsubmit="return false;"> 
	<table width="100%" cellspacing="1" cellpadding="2" style="marging-top:10px; font-size:12px">
		<tr>
			<td align="left"><span WCMAnt:param="channel_position_set.jsp.for">为栏目</span><font color=blue>[<%=currChannel.getDesc()%></font>]<span WCMAnt:param="channel_position_set.jsp.adjustOrder">调整顺序: </span> </td> 
		</tr>
		<tr>
			<td>
				<div id="divBasicChannelOrder">
					<span WCMAnt:param="channel_position_set.jsp.prechannel">
						前一栏目：
					</span>
					<span class="spAttrItem">
						<select name="ChnlOrder" style="width:170px;" _value="<%=currChannel.getOrder()%>" value="<%=currChannel.getOrder()%>">
							<option value="-1" WCMAnt:param="channel_position_set.jsp.first">最前面</option>
							<%
								for (int i = 0, length = oSiblings.size(); i < length; i++) {
									Channel channel = (Channel) oSiblings.getAt(i);
									if (channel == null || channel.getId() == currChannel.getId())
										continue;
							%>
								<option value="<%=channel.getOrder()%>"><%=CMyString.transDisplay(channel.getDesc())%></option>
							<%
								}
							%>
						</select>
					</span>
				</div>
			</td>
		</tr>		
		<tr>
			<td align="center" height="5px">&nbsp; </td>
		<tr>
	</table>
</form>
<script language="javascript">
<!--
	var selectNodeOptions = $('ChnlOrder').options;
	for(var index = 0; index < selectNodeOptions.length; index ++){
		var optionNode = selectNodeOptions[index];
		if(optionNode.value != (parseInt($('ChnlOrder')._value)+1)) continue;
		$('ChnlOrder').selectedIndex = index;
	}
//-->
</script>
</body>
</html>