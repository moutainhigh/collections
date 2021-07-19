<%--
/** Title:			Document_addedit.jsp
 *  Description:
 *		记录位置设置页面
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
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nDocumentId	  = currRequestHelper.getInt("DocumentId", 0);
	Document currDocument = Document.findById(nDocumentId);
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,  CMyString.format(LocaleServer.getString("record_position_set.id.zero","没有找到ID为[{0}]的记录！"),new int[]{nDocumentId}));
	}
	String sTitle = currDocument.getTitle();

	MetaView oMetaView = MetaView.findById(currDocument.getKindId());
	if(oMetaView != null){
		MetaViewField oViewField = oMetaView.getTitleField();
		if(oViewField != null){
			MetaViewData oMetaViewData = MetaViewData.findById(nDocumentId);
			String _sTitle = oMetaViewData.getPropertyAsString(oViewField.getName());
			if(!CMyString.isEmpty(_sTitle)){
				sTitle = _sTitle;
			}
		}
	}

	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("record_position_set.channel.notFound","没有找到ID为[{0}]的栏目！"),new int[]{nChannelId}));
	}

//5.权限校验

//6.业务代码
	WCMFilter filter = new WCMFilter("", "", "", "DocId");
	IChannelService channelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	Documents documents = channelService.getDocuments(currChannel, filter);
	int nDocOrder = documents.indexOf(nDocumentId);
	int nSize = documents.size();
	
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
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metaviewdata.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
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
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
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
			name : wcm.LANG.METAVIEWDATA_1 || '确定'
		}],
		size : [400,140]
	};	
</script>
<script>
function docpositionset(){
	var sURL="record_position_set_dowith.jsp";
	var frmData = $('frmData');

	if(frmData.DocumentOrderMode.value != 1){
		frmData.DocOrder.value = frmData.DocumentOrderMode.value;
	}else{
		if($F('DocOrder') <= 0 || !/^-?\d+$/.test($F('DocOrder'))){
			Ext.Msg.alert((wcm.LANG.METAVIEWDATA_16 || "请输入一个正整数."), function(){
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
    para="DocumentId=<%=nDocumentId%>&ChannelId=<%=nChannelId%>&DocOrder="+frmData.DocOrder.value; 
    new Ajax.Request(sURL, {  
			method: 'post', 
			parameters:para,
			contentType : 'application/x-www-form-urlencoded',
			onSuccess:function(transport){

			   notifyFPCallback(transport);
			   FloatPanel.close();
			   }
		    }); 
	return false;
     
}

function showDocs(){
	var oparams = {
		siteType : 4,
		bCkxOrRadio : 'radio',
		docId : <%=nDocumentId%>,
		channelId : <%=nChannelId%>
	};
	var cbr = wcm.CrashBoarder.get('setposition_docs');
	cbr.show({
		title : wcm.LANG.record_position_set_1000 || '选择插入的文档位置',
		src : WCMConstants.WCM6_PATH + 'application/<%=currDocument.getKindId()%>/metaviewdata4select_list.html',
		width:'680px',
		height:'580px',
		maskable:false,
		params : oparams,
		callback : function(params){
			this.close();
			$('DocOrder').value = params;
		}
	});
}

</script>

</head>

<body>  

<SCRIPT LANGUAGE="JavaScript">
<!--
function onModeChannge(_nMode){
	var oRegion = document.getElementById("DocumentOrderRegion");
	if(_nMode == 1) $("DocOrder").value = <%=(nDocOrder+1)%>;
	oRegion.style.display = _nMode == 1? "inline":"none";
}
//-->
</SCRIPT>

<form  name="frmData" action="document_position_set_dowith.jsp" onsubmit="return false;">
<INPUT TYPE="hidden" name="DocumentId" value="<%=nDocumentId%>">
<INPUT TYPE="hidden" name="ChannelId" value="<%=nChannelId%>">

	<table width="100%" cellspacing="1" cellpadding="2" style="marging-top:10px; font-size:12px">
		<tr>
			<td align="left" nowrap><span WCMAnt:param="record_position_set.jsp.record">为记录</span><font color=blue>[<%=sTitle%></font>]<span WCMAnt:param="record_position_set.jsp.adjust">调整顺序:</span> </td> 
		</tr>
		<!--~--- ROW4 ---~-->
		<tr>
			<td>
				<span WCMAnt:param="record_position_set.jsp.setOrder">让记录处于栏目</span>[<font color=blue><%=currChannel.getName()%></font>]
				<select name="DocumentOrderMode" onchange="onModeChannge(document.frmData.DocumentOrderMode.value);">
					<option value="0" WCMAnt:param="record_position_set.jsp.top">最前面</option>
					<option value="-1" WCMAnt:param="record_position_set.jsp.latest">最后面</option>
					<option value="1" WCMAnt:param="record_position_set.jsp.currPosition">指定位置</option>
				</select><BR><BR>
				<span id="DocumentOrderRegion" style="display:none"><span WCMAnt:param="record_position_set.jsp.tip1">调整至:</span><input type="text" id="DocOrder" name="DocOrder" value="<%=(nDocOrder+1)%>" style="border:1px gray solid;width:60px"><span WCMAnt:param="record_position_set.jsp.tip2">,(总数为:</span><span class="num"><%=nSize%></span>)<BR><BR>
				<span style="text-decoration:underline;cursor:pointer;" onclick="showDocs();return false;" WCMAnt:param="record_position_set.jsp.setto_forward_doc">调整到指定文档的前面</span>
				</span>
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