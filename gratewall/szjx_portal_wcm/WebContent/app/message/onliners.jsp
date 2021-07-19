<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@include file="../include/public_server.jsp"%>
<%
	Users oUsers = LoginHelper.getOnlineUsers();
	if(oUsers==null){
		throw new WCMException(LocaleServer.getString("onliners.noLogging","当前没有用户处于登录状态！"));
	}
%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>multi-selector for outline-template</TITLE>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/message.js"></script>
<!-- CarshBoard Inner Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Inner End -->
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<script language="javascript"> 
	window.m_cbCfg = {
        btns : [
            {
                text :  wcm.LANG['MESSAGE_REFRESH'] || '刷新列表',
                cmd : function(){
					//document.URL = location.href;
					window.location.href = location.href;
					return false;
				}
            },
            {
                text :  wcm.LANG['MESSAGE_CLOSE'] || '关闭窗口'
            }
        ]
    };
</script>
<style type="text/css">
.liner_row{
	height: 25px;
	line-height: 25px;
	font-size: 12px;
	float: left;
	width: 210px;
	font-family: Georgia;
	overflow: hidden;
	text-overflow:ellipsis;
	white-space:nowrap;
}
.sp_name{
	cursor: pointer;
}
body{
	padding:0px;
	margin:0px;
	background:#fff;
}
A:link{
	color: #1571D5;
	text-decoration: none;
}
A:hover{
	color: #ff6600
}

</style>
</HEAD>
<BODY>
<div id="dy_adjust">
</div>
<script>
function onSendMsg(_nUserId, _sUserName){
	var _params = {
		uid: _nUserId,
		uname: _sUserName
	};
	var cbr = wcm.CrashBoarder.get("Onliners_Show"); 
	cbr.hide();
	setTimeout(function(){
		var sUrl = WCMConstants.WCM6_PATH + 'message/message_sending.html';
		wcm.CrashBoarder.get('Send_Msg').show({
			title :  wcm.LANG['MESSAGE_1'] || '发送在线短消息',
			src : sUrl,
			width: '700px',
			height: '500px',
			params : _params,
			maskable : true,
			callback : function(){
			}
		}); 
	}, 1);
}
</script>
<div>
	<table border=0 align="center" cellspacing=0 cellpadding=3 style="font-size:12px;width:100%;border:0px solid silver; margin-top:5px;table-layout:fixed">
	<tbody>
		<tr>
			<td colspan="2">
				<div id="onliners" class="container_onliners" style="overflow-x: hidden;overflow-y: auto; padding: 5px; font-size: 14px; font-weight: normal; padding-top: 8px;display: none; width: 100%;">
				</div>
		<% 
			int nIndex = 1;
			for(int i=0; i<oUsers.size(); i++){ 
				User oUser = (User)oUsers.getAt(i);
				if(oUser == null)continue;
				int nUserId = oUser.getId();
				String sUserName = oUser.getName();
				String sNickName = oUser.getNickName();
				String showName = "";
				if(sNickName==null || sNickName.equals(""))
					showName = sUserName + " (" + sUserName + ")";
				else showName = sUserName + " (" + sNickName + ")";
				String sLoginTime = oUser.getLoginTime().toString("MM-dd HH:mm");
				String sLoginIp = oUser.getLoginIP();

		%>
					<div class="liner_row">
						<span><%=nIndex++%>.&nbsp;</span>
						<span id="parentsp"><span style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" title="<%=LocaleServer.getString("onlines.label.time", "登录时间")%>: <%=sLoginTime%>&#13;<%=LocaleServer.getString("onlines.label.loginIp", "登录地址")%>: <%=sLoginIp%>">
							<a href="#" onclick="onSendMsg(<%=nUserId%>, '<%=sUserName%>'); return false;"><%=showName%></a>
						</span></span>
					</div>
		<%}%>
			</td>
		</tr>
	</tbody>
	</table>
</div>
</BODY>
</HTML>