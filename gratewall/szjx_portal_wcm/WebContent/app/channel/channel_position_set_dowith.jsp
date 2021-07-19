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

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.components.wcm.content.domain.DocumentMgr" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
	    throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.channel", "没有找到ID为{0}的栏目"), new int[]{nChannelId}));
	}
	int nCurrDocOrder = currChannel.getOrder();
	int nNewDocOrder = currRequestHelper.getInt("newChnlOrder", nCurrDocOrder);

//6.业务代码
	if(currChannel.canEdit(loginUser)){
		currChannel.setOrder(nNewDocOrder);
		currChannel.save(loginUser);
	}
	
//7.结束
	out.clear();
%>