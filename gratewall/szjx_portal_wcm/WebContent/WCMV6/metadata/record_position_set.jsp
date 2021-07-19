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
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nDocumentId	  = currRequestHelper.getInt("DocumentId", 0);
	Document currDocument = Document.findById(nDocumentId);
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到ID为["+nDocumentId+"]的记录！");
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
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到ID为["+nChannelId+"]的频道！");
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

<script src="../js/com.trs.util/Common.js"></script>
<script>
	$import('com.trs.wcm.FloatPanel');
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.wcm.ProcessBar');
	$import('com.trs.dialog.Dialog');
</script>
<script>
FloatPanel.addCloseCommand();
FloatPanel.addCommand('exportBtn', '确定', 'docpositionset',null);

function docpositionset(){
	var sURL="record_position_set_dowith.jsp";
	var frmData = $('frmData');

	if(frmData.DocumentOrderMode.value != 1){
		frmData.DocOrder.value = frmData.DocumentOrderMode.value;
	}else{
		if(!/^-?\d+$/.test($F('DocOrder'))){
			$alert("请输入一个整数.", function(){
				$dialog().hide();
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
			onSuccess:function(transport){

			   $MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
			   FloatPanel.close(true);
			   }
		    }); 
	FloatPanel.close(false);
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
<INPUT TYPE="hidden" name="DocumentId" value="<%=nDocumentId%>">
<INPUT TYPE="hidden" name="ChannelId" value="<%=nChannelId%>">

	<table width="100%" cellspacing="1" cellpadding="2" style="marging-top:10px; font-size:12px">
		<tr>
			<td align="left" nowrap> 为记录<font color=blue>[<%=sTitle%></font>]调整顺序:  </td> 
		</tr>
		<!--~--- ROW4 ---~-->
		<tr>
			<td>
				让记录处于频道[<font color=blue><%=currChannel.getName()%></font>]
				<select name="DocumentOrderMode" onchange="onModeChannge(document.frmData.DocumentOrderMode.value);">
					<option value="0">最前面</option>
					<option value="-1">最后面</option>
					<option value="1">指定位置</option>
				</select><BR><BR>
				<span id="DocumentOrderRegion" style="display:none">第<input type="text" name="DocOrder" value="<%=(nDocOrder+1)%>" style="border:1px gray solid;width:60px">位,(共<span class="num"><%=nSize%></span>篇记录)</span>
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