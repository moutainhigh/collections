<%
/** Title:			user_select_index.jsp
 *  Description:
 *		标准WCM5.2 用户选择首页
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-26 20:17
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-26/2004-12-26
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *
 *  Parameters:
 *		see user_select_index.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>

<%
//4.初始化(获取数据)	
	int nTransferName = currRequestHelper.getInt("TransferName", 0);
	int nTreeType = currRequestHelper.getInt("TreeType", 0);
	boolean bTransferName	= currRequestHelper.getInt("TransferName", 0) > 0;
	int nbAllUser	= currRequestHelper.getInt("AllUser", 0);
	int nFilterRight =  currRequestHelper.getInt("FilterRight", 0);
%>


<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE></TITLE>
</HEAD>

<%@include file="../wcm52/include/public_client_normal.jsp"%>
<script src="../../js/CTRSArray.js"></script>
<script src="../../js/CTRSRequestParam.js"></script>

<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/auth.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'onOK',
		name : wcm.LANG.AUTH_TRUE||'确定'
	}],
	size : [850, 440]
};
</script>

<script>
	var m_bTransferName = <%=bTransferName%>;
	var m_arUserId		= new Array();
	var m_arUserName	= new Array();

	function init(){
		var dialogArguments = FloatPanel.dialogArguments;
		if(!dialogArguments.split){//Array			
			if(m_bTransferName && dialogArguments.length<2){
				alert(wcm.LANG.AUTH_ALERT_5||"您传入的数据有误！\n应该为二维数组，第一个为用户ID，第二个为用户名");
				return;
			}						
			if(m_bTransferName){
				m_arUserId = dialogArguments[0];
				m_arUserName = dialogArguments[1];
			}
			else{
				m_arUserId = dialogArguments;
				m_arUserName = TRSArray.clone(m_arUserId);
			}			
		}
		else{//String
			if(m_bTransferName){
				alert(wcm.LANG.AUTH_ALERT_5||"您传入的数据有误！\n应该为二维数组，第一个为用户ID，第二个为用户名");
				return;
			}
			m_arUserId = dialogArguments.split(",");
			m_arUserName = TRSArray.clone(m_arUserId);
		}
	}

	init();
	
	function onOK(){
		clearNullItem();
		var returnValue = null;
		if(m_bTransferName){
			var arReturnValue = new Array();
			arReturnValue[0] = m_arUserId;
			arReturnValue[1] = m_arUserName;
			returnValue = arReturnValue;
		}else{
			var sUserIds = getUserIds();
			if(sUserIds == "null")
				sUserIds = "";
			returnValue = sUserIds;
		}
		notifyFPCallback(returnValue);
	}

	function addUserId(_nUserId, _sUserName){
		var nIndex = m_arUserId.length;
		if(m_arUserId[0] == null || m_arUserId[0] == "null"){
			nIndex = 0;
		}
		m_arUserId[nIndex] = _nUserId;
		if(_sUserName == null){
			m_arUserName[nIndex] = _nUserId;
		}else{
			m_arUserName[nIndex] = _sUserName;
		}
	}

	function getUserIdArray(){
		return m_arUserId;
	}

	function getUserIds(){
		return m_arUserId.join(",");
	}

	function removeUserId(_nUserId, _sUserName){
		TRSArray.remove(m_arUserId, _nUserId);
		if(_sUserName)
			TRSArray.remove(m_arUserName, _sUserName);
		else
			TRSArray.remove(m_arUserName, _nUserId);
	}

	function clearNullItem(){
		if(m_arUserId != null){
			for(var i=0; i<m_arUserId.length; i++){
				if(m_arUserId[i] == null) TRSArray.splice(m_arUserId, i, 1);
				if(m_arUserName != null && m_arUserName[i] == null) TRSArray.splice(m_arUserName, i, 1);
			}
		}
	}
</script>

<FRAMESET cols="200,*" frameborder="NO" border="0" framespacing="0">
	<FRAME src="user_select_left.jsp?TreeType=<%=nTreeType%>&FilterRight=<%=nFilterRight%>" name="left" scrolling="no" noresize>
	<FRAME src="user_select_list.jsp?TransferName=<%=nTransferName%>&AllUser=<%=nbAllUser%>&FilterRight=<%=nFilterRight%>" name="main" scrolling="no">
</FRAMESET>
<NOFRAMES>
<BODY>
</BODY>
</NOFRAMES>
</HTML>