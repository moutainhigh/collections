<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java"%>
<%@ page import="com.trs.components.special.Special" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page import="com.trs.components.special.ISpecialMgr" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nObjectId = currRequestHelper.getInt("ObjectId",0);
	Special special = Special.findById(nObjectId);
	if(special == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("getStyle.jsp.special_notfound", "没有找到ID为[{0}]的专题!"), new int[]{nObjectId}));
	}
	//获取页面风格
	ISpecialMgr m_oSpecialMgr = (ISpecialMgr) DreamFactory
		.createObjectById("ISpecialMgr");
	PageStyle pageStyle = m_oSpecialMgr.getStyle(special);
	int nSytleId = 0;
	if(pageStyle != null){
		nSytleId = pageStyle.getId();
	}
	out.clear();
	out.println(nSytleId);
%>