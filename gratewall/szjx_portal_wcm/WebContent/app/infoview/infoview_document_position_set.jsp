<%--
/** Title:			Document_addedit.jsp
 *  Description:
 *		文档位置设置页面
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
<!------- WCM IMPORTS END ------------>
<%@include file="./infoview_public_include.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	Document currDocument = Document.findById(nDocumentId);
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("position_set.document.notFound","没有找到ID为[{0}]的文档！"),new int[]{nDocumentId}));
	}

	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("position_set.channed.notFound", "没有找到ID为[{0}]的栏目！"),new int[]{nChannelId}));
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
<script language="javascript">

var m_cb = null;
function init(_args, cb) {
	m_cb = cb;
}
function onOk(){
	var _oForm = document.getElementById('frmData');
	if(_oForm.DocumentOrderMode.value != 1){
		_oForm.DocumentOrder.value = _oForm.DocumentOrderMode.value;
	}
	var oReturnValue = [<%=nDocumentId%>, _oForm.DocumentOrder.value];
	if(!m_cb)return;
	if(m_cb.callback)
	m_cb.callback(oReturnValue);
	m_cb.close();
	return false;
}
function onModeChannge(_nMode){
	var oRegion = document.getElementById("DocumentOrderRegion");
	oRegion.style.display = _nMode == 1? "inline":"none";
}
</script>
<style type="text/css">
	body{
		padding: 0; 
		margin: 0;
		overflow: auto
	}
</style>
</head>
<body style="background-color: transparent;"> 
<form  name="frmData" onsubmit="return false;" id="frmData">
	<table align="center" cellspacing="0" cellpadding="5" style="marging-top:0px; font-size:12px; width: 100%; height: 100%">
		<tr>
			<td align="left"> 
				<div style="margin-bottom: 8px;">
					<% out.print(CMyString.format(LocaleServer.getString("position_set.Set","调整文档[<font color=blue>{0}</font>]在栏目[<font color=blue{1}</font>]中的顺序"),new String[]{currDocument.getTitle(),currChannel.getName()}));%>
				</div>
				<div style="height: 35px;">
					<select name="DocumentOrderMode" onchange="onModeChannge(document.frmData.DocumentOrderMode.value);">
						<option value="0" WCMAnt:param="infoview_document_position_set.jsp.top">最前面</option>
						<option value="-1" WCMAnt:param="position_set.last">最后面</option>
						<option value="1" WCMAnt:param="position_set.target">指定位置</option>
					</select>
					<span id="DocumentOrderRegion" style="display:none"><% out.print(CMyString.format(LocaleServer.getString("position_set.total.document","第<input type='text' name='DocumentOrder' value='{0}' style='width:25px; text-align: center;' onfocus='this.select();'>位（共{1}篇文档）"),new int[]
					{nDocOrder+1,nSize}));%></span>
				</div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>