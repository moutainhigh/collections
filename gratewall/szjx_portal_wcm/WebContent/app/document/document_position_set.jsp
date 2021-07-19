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
	errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@page import="java.sql.Connection" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.ResultSet" %>

<%@page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@page import="com.trs.infra.persistent.db.DBManager" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nDocumentId	  = currRequestHelper.getInt("DocumentId", 0);
	Document currDocument = Document.findById(nDocumentId);
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nDocumentId),WCMTypes.getLowerObjName(Document.OBJ_TYPE)}));
	}

	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nChannelId),WCMTypes.getLowerObjName(Channel.OBJ_TYPE)}));
	}
	String sType =  currRequestHelper.getString("DocType");
	String sDesc =  currRequestHelper.getString("DocTypeDesc");
	if(CMyString.isEmpty(sDesc)){
		sDesc = LocaleServer.getString("document_position_set.label.document", "文档");
		if(sType.equals("photo")){
			sDesc = LocaleServer.getString("document_position_set.label.photo", "图片");
		};
	}

//5.权限校验

//6.业务代码
	//WCMFilter filter = new WCMFilter("", "", "", "DocId");
	//IChannelService channelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	//Documents documents = channelService.getDocuments(currChannel, filter);
	int[] posOfSize = getPosOfSize(nDocumentId,nChannelId);
	int nDocOrder = posOfSize[0];//documents.indexOf(nDocumentId);
	int nSize = posOfSize[1];//documents.size();
	
//7.结束
	out.clear();
%>

<%!
	
	private static int[] getPosOfSize(int nDocId,int nChnlId) throws Exception{
		String sql = "select count(recid) from wcmchnldoc where chnlid=? and docorder>? and docstatus>=1 and modal>0 and docorderpri=0";
		Connection conn = null;
		PreparedStatement pstmt = null;
		DBManager dbman = DBManager.getDBManager();
		try {
			conn = dbman.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nChnlId);
			pstmt.setInt(2,0); //docorder>0是所有文档.总是大于零的.
			ResultSet rs = pstmt.executeQuery();
			int size = 0;
			if (rs.next()) {
				size = rs.getInt(1);
			}
			rs.close();
						
			ChnlDoc chnldoc = ChnlDoc.findByDocAndChnl(nDocId, nChnlId);
			int nOrder = chnldoc.getDocOrder();			
			pstmt.setInt(1, nChnlId);
			pstmt.setInt(2,nOrder); 

			rs = pstmt.executeQuery();
			int pos = 1;
			if (rs.next()) {
				pos = rs.getInt(1);
			}
			
			rs.close();
			return new int[] {pos,size};		
		}finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}catch(Exception ex){}
			}
			if(conn != null){
				dbman.freeConnection(conn);
			}
		}
	}
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
			name : wcm.LANG.DOCUMENT_PROCESS_31 || '确定'
		}],
		size : [400,140]
	};	
</script>
<script>

function docpositionset(){
	var sURL="document_position_set_dowith.jsp";
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
    para="DocumentId=<%=nDocumentId%>&ChannelId=<%=nChannelId%>&DocOrder="+frmData.DocOrder.value; 
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

function showDocs(){
	var oparams = {
		channelId : <%=nChannelId%>,
		currDocOrder : <%=nDocOrder%>,
		positionSetting : true
	};
	var cbr = wcm.CrashBoarder.get('setposition_docs');
	cbr.show({
		title : '选择插入的文档位置',
		src : WCMConstants.WCM6_PATH + 'document/document4select_list.html',
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
			<td align="left"><span WCMAnt:param="document_position_set.jsp.for">为</span><%=CMyString.transDisplay(sDesc)%><font color=blue>&nbsp;[<%=CMyString.transDisplay(currDocument.getTitle())%></font>]&nbsp;<span WCMAnt:param="document_position_set.jsp.adjustOrder">调整顺序：</span> </td> 
		</tr>
		<!--~--- ROW4 ---~-->
		<tr>
			<td>
				<span WCMAnt:param="document_position_set.jsp.let">让</span><%=CMyString.transDisplay(sDesc)%><span WCMAnt:param="document_position_set.jsp.inChannel">处于栏目</span>&nbsp;[<font color=blue><%=CMyString.transDisplay(currChannel.getDesc())%></font>]&nbsp;
				<select name="DocumentOrderMode" onchange="onModeChannge(document.frmData.DocumentOrderMode.value);">
					<option value="0" WCMAnt:param="document_position_set.jsp.top">最前面</option>
					<option value="-1" WCMAnt:param="document_position_set.jsp.latest">最后面</option>
					<option value="1" WCMAnt:param="document_position_set.jsp.currPosition">指定位置</option>
				</select><BR><BR>
				<span id="DocumentOrderRegion" style="display:none"><span WCMAnt:param="document_position_set.jsp.tip1">调整至：</span><input type="text" id="DocOrder" name="DocOrder" value="<%=(nDocOrder+1)%>" style="border:1px gray solid;width:60px"><span WCMAnt:param="document_position_set.jsp.tip2">(总数为：</span><span class="num"><%=nSize%></span>)<BR><BR>
				<span style="text-decoration:underline;cursor:pointer;" onclick="showDocs();return false;" WCMAnt:param="document_position_set.jsp.ajustbeforespecialdoc">调整到指定文档的前面</span>
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