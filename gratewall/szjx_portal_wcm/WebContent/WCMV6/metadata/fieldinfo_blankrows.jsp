<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBField" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBFields" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.webframework.xmlserver.ConvertException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataTypes" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataType" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.components.metadata.definition.IClassInfoMgr" %>
<%@ page import="com.trs.DreamFactory" %>
<%
	out.clear();
	CPager currPager = null;
	String sBlankStartIndex = request.getParameter("START_INDEX");
	int nBlankStartIndex = Integer.parseInt(sBlankStartIndex);
	String sDBTypeContainer = getDBTypeContainer();
	String sFieldTypeContainer = getFieldTypeContainer();
	String sClassInfoContainer = getClassInfoContainer();
%>
<%@include file="./fieldinfo_blankrows_include.jsp"%>