<%--
/** Title:			advisor_delete.jsp
 *  Description:
 *		删除Advisor的页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2011-07-03 00:24:48
 *  Vesion:			1.0
 *  Last EditTime:	2011-07-03 / 2011-07-03
 *	Update Logs:
 *		TRS WCM 5.2@2011-07-03 产生此文件
 *
 *  Parameters:
 *		see advisor_delete.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.advisor.Advisors" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_processor.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	String sAdvisorIds = processor.getParam("AdvisorIds");
	String sChannelId = processor.getParam("ChannelId");

	int nChannelId = 0;
	if(!CMyString.isEmpty(sChannelId)){
		nChannelId = Integer.parseInt(sChannelId);
		Channel oChannel = Channel.findById(nChannelId);
		if(oChannel == null){
			throw new WCMException(CMyString.format(LocaleServer.getString("advisor_delete.jsp.cannotfindchannelid", "无法找到ID为【{0}】的栏目！"), new int[]{nChannelId}));
		}
		boolean bHasRight = AuthServer.hasRight(loginUser, oChannel,
                WCMRightTypes.CHNL_EDIT);
		if (!bHasRight) {
			throw new WCMException(CMyString.format(LocaleServer.getString("advisor_delete.jsp.youhavenotchannlidoperatefilterinfo", "您没有权限在栏目【id={0}】下的操作过滤器信息！ "), new int[]{nChannelId}));
		}
	}
	processor.excute("wcm61_advisorcenter", "deleteAdvisor");
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="advisor_delete.jsp.trswcmdeleteadvisor">TRS WCM 删除Advisor</TITLE>
</HEAD>
<BODY>
<SCRIPT LANGUAGE="JavaScript">
top.returnValue = true;
top.close();
</SCRIPT>
</BODY>
</HTML>