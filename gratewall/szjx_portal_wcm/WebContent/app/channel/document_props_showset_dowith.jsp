<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../../include/public_server.jsp"%>
<%try{%>
<%
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	String sBaseProps = currRequestHelper.getString("BASEPROPS");
	String sOtherProps = currRequestHelper.getString("OTHERPROPS");
	String sAdvanceProps = currRequestHelper.getString("ADVANCEPROPS");
	String sNeededProps = currRequestHelper.getString("NeededProps");
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.channel", "没有找到ID为{0}的栏目"), new int[]{nChannelId}));
	}
	if(currChannel != null){
		if(!currChannel.canEdit(loginUser)){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED, CMyString.format(LocaleServer.getString("document_props_showset_dowith.jsp.chnlblockedbyuser","栏目被用户[{0}]锁定！"),new String[]{currChannel.getLockerUserName()}));
		}
	}
	currChannel.setPropertyWithString("BASEPROPS",sBaseProps);
	currChannel.setPropertyWithString("OTHERPROPS",sOtherProps);
	currChannel.setPropertyWithString("ADVANCEPROPS",sAdvanceProps);
	currChannel.setPropertyWithString("NeededProps",sNeededProps);
	currChannel.save(loginUser);
	out.clear();
%><%=nChannelId%>
<%
	}catch(Throwable tx){
		tx.printStackTrace();
		throw new WCMException(LocaleServer.getString("document_props_showset_dowith.nosave", "保存属性设置失败."), tx);
	}
%>