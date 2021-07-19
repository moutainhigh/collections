<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../../include/public_server.jsp"%>
<%try{%>
<%
	int nWebsiteId = currRequestHelper.getInt("WebsiteId",0);
	String sBaseProps = currRequestHelper.getString("BASEPROPS");
	String sOtherProps = currRequestHelper.getString("OTHERPROPS");
	String sAdvanceProps = currRequestHelper.getString("ADVANCEPROPS");
	String sNeededProps = currRequestHelper.getString("NeededProps");
	WebSite currWebsite = WebSite.findById(nWebsiteId);
	if(currWebsite == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.website", "没有找到ID为{0}的站点"), new int[]{nWebsiteId}));
	}
	if(currWebsite != null){
		if(!currWebsite.canEdit(loginUser)){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED, CMyString.format(LocaleServer.getString("document_props_showset_dowith.jsp.chnlblockedbyuser","站点被用户[{0}]锁定！"),new String[]{currWebsite.getLockerUserName()}));
		}
	}
	currWebsite.setPropertyWithString("BASEPROPS",sBaseProps);
	currWebsite.setPropertyWithString("OTHERPROPS",sOtherProps);
	currWebsite.setPropertyWithString("ADVANCEPROPS",sAdvanceProps);
	currWebsite.setPropertyWithString("NeededProps",sNeededProps);
	currWebsite.save(loginUser);

	out.clear();
%><%=nWebsiteId%>
<%
	}catch(Throwable tx){
		tx.printStackTrace();
		throw new WCMException(LocaleServer.getString("document_props_showset_dowith.nosave", "保存属性设置失败."), tx);
	}
%>