<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Keyword" %>
<%@ page import="com.trs.components.wcm.resource.Keywords" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.persistent.db.DBManager"%>
<%@ page import="com.trs.infra.util.CMyString"%>

<%@include file="../include/public_server.jsp"%>

<%
	// 获取数据
	int nSiteId = currRequestHelper.getInt("siteId",0);
	int nSiteType =  currRequestHelper.getInt("siteType",0);
	String sKname = currRequestHelper.getString("Kname");

	//权限校验

	//业务代码
	WCMFilter existFilter = new WCMFilter("XWCMKEYWORD",
                "siteId in(0,?) and KName=? and siteType=?", "", "keywordId");
	existFilter.addSearchValues(nSiteId);
	existFilter.addSearchValues(sKname);
	existFilter.addSearchValues(nSiteType);
	boolean bexists = (DBManager.getDBManager().sqlExecuteIntQuery(existFilter) > 0)?true:false;
	out.clear();
	out.print(bexists);

%>