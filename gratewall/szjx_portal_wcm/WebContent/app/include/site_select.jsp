<%
/** Title:			site_select.jsp
 *  Description:
 *		WCM5.2 站点选择页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wenyh
 *  Created:		2006-04-30 13:34
 *  Vesion:			1.0
 *  Last EditTime:	2006-04-30 / 2006-04-30
 *	Update Logs:
 *		
 *
 *  Parameters:
 *		see site_select.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
	String sSiteIds = currRequestHelper.getString("SiteIds");
	sSiteIds = "," + sSiteIds + ",";

	IChannelService service = (IChannelService)ServiceHelper.createChannelService();
	WCMFilter aFilter = new WCMFilter("", "SiteType=0","");
	WebSites sites = service.getUserSites(loginUser,aFilter);
%>

<html>
<head>
<title WCMAnt:param="site_select.jsp.title">TRSWCM61 站点选择:::::::::::::::::::::::::..</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
html,body,iframe{font-size:12px;border:0;background:transparent;}
td{font-size:12px;}
</style>
<script src="../js/data/locale/include.js"></script>
<script>
var m_cb = null;
function init(param, cb){
	m_cb = cb;
}
function onOk(){
	var inputels = document.getElementsByTagName("input");
	var inputel = null;
	var result = [];
	for(var i=0;i<inputels.length;i++){
		inputel = inputels[i];
		if(inputel == null || !inputel.checked) continue;
		result.push(inputel.value);
	}
	if(result.length==0){
		alert(wcm.LANG.INCLUDE_ALERT_4 || '没有选中任何站点，请选择！');
		return false;
	}
	m_cb.callback(result.join(","));
	return false;
}
</script>
</head>
<body style="margin:0px">
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR><TD>
	<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
	<TR id="tr_website" name="tr_website" style="display:">
		<TD width="100%" valign="top" bgcolor="white">
<%
	WebSite currSite = null;
	int nCurrSiteId = 0;
	for(int i=0;i<sites.size();i++){
		currSite = (WebSite)sites.getAt(i);
		if(currSite != null){
			nCurrSiteId = currSite.getId();
%>
			<span><input type="checkbox" value=<%=nCurrSiteId%> <%=sSiteIds.indexOf(","+nCurrSiteId+",")!=-1?"checked":""%>><%=currSite%></span><br />
<%
		}	
	}
%>
	</TD></TR></TABLE>
</TD></TR>
</TABLE>
</body>
</html>