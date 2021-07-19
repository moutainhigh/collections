<%--
/** Title:			channelcontentlink_get_by_name.jsp
 *  Copyright: 		北京拓尔思(TRS)信息技术有限公司(www.trs.com.cn) 
 *	History				Who				What
 *	2007-07-26			wenyh			created.
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelContentLink" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelContentLinks" %>
<%@ page import="com.trs.infra.persistent.ObjToXmlConverter" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	int nSiteId = currRequestHelper.getInt("SiteId", 0);
	int nLinkId = currRequestHelper.getInt("LinkId",0);	
	String sLinkName = currRequestHelper.getString("LinkName");
	if(!CMyString.isEmpty(sLinkName)) sLinkName = sLinkName.toUpperCase();

	String sWhere = "";
	if(nChannelId > 0){
		sWhere = "ChannelId=? AND LinkName=?";
	}else{
		sWhere = "SiteId=? AND LinkName=?";
	}
	if(nLinkId > 0){
		sWhere += " AND NOT LinkId=?";
	}
	WCMFilter filter = new WCMFilter("", sWhere, "", "LINKID,CHANNELID,LINKNAME,LINKURL", 1);
	if(nChannelId > 0){
		filter.addSearchValues(0,nChannelId);
	}else{
		filter.addSearchValues(0,nSiteId);
	}
	filter.addSearchValues(1,sLinkName);
	if(nLinkId > 0){
		filter.addSearchValues(2,nLinkId);
	}

	ChannelContentLinks allLinks = new ChannelContentLinks(loginUser,1,1);
	allLinks.open(filter);

	ChannelContentLink currLink = null;
	if(allLinks != null && !allLinks.isEmpty()){
		currLink = (ChannelContentLink) allLinks.getAt(0);
	}	

//7.结束
	out.clear();
%>
<%
	if(currLink != null){
		ObjToXmlConverter aXMLConvert = new ObjToXmlConverter();
		out.print(aXMLConvert.toXMLString(currLink, null, "CONTENTLINKID,SITEID,LINKNAME,LINKURL"));
	} else {
		out.clear();
		out.print("null");
	}
	if(true){
		return;
	}
%>