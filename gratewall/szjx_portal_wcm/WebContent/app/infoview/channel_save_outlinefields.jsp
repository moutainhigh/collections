<%
/** Title:			channel_save_outlinefields.jsp
 *  Description:
 *		WCM5.2 栏目，保存栏目的概览字段。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			FCR
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *		fcr@2006-06-22	create
 *
 *  Parameters:
 *		see channel_save_outlinefields.xml
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.service.IWCMProcessService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.components.infoview.InfoViewHelper" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<%@include file="./infoview_public_include.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化（获取数据）
	int		nChannelId		= currRequestHelper.getInt("ChannelId", 0);
	String	sOutlineFields	= currRequestHelper.getString("SelectedFields");
	String  sLastUrl        = currRequestHelper.getString("url");
	Channel	currChannel	= Channel.findById(nChannelId);
	if (currChannel == null) {
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("save_outlinefields.channel.getFailed","获取ID为[{0}]的栏目失败！"),new int[]{nChannelId}));
	}

//5.权限校验
	if(!AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.CHNL_EDIT)){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, CMyString.format(LocaleServer.getString("save_outlinefields.noRight","您没有权限修改ID为[{0}]的栏目！"),new int[]{nChannelId}));
	}
	if(!currChannel.canEdit(loginUser)){
		throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, CMyString.format(LocaleServer.getString("save_outlinefields.channel.locked","栏目[{0}][{1}]被用户［{2}］锁定！您不能修改！"),new String[]{String.valueOf(nChannelId),currChannel.getDesc(),currChannel.getLockerUserName()}));
	}

//6.业务代码
	String sErrorInfo = null;
	InfoView oInfoView = null;
	try{
		currChannel.setOutlineFields(sOutlineFields);
		oInfoView = InfoViewHelper.getInfoView(currChannel);
		oInfoView.setOutlineFields(sOutlineFields);
		if(oInfoView!=null){
			currChannel.setProperty("OutlineDBFields", InfoViewHelper.getDBFieldsByFields(oInfoView, sOutlineFields, true));
		}
	}catch(Exception ex){
		sErrorInfo = LocaleServer.getString("channel_save_outline.jsp.label.toolong", LocaleServer.getString("save_outlinefields.toomany","您选择的视图字段太多，请减少字段的选择，这次设置失败！"));
		//throw new WCMException("您选择的概览字段太多，请减少字段的选择！", ex);
	}
	if(sErrorInfo == null){
		ServiceHelper.createChannelService().save(currChannel);
		oInfoView.save(loginUser);
	}

//7.结束
	out.clear();
%>
<script>
<%if(sErrorInfo != null){%>
alert("<%=sErrorInfo%>");
<%}%>
var sLastUrl = '<%=CMyString.filterForJs(sLastUrl)%>';
window.location.href = sLastUrl;
</script>
<!-- 没有输出 -->