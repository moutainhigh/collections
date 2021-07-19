

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Keyword" %>
<%@ page import="com.trs.components.wcm.resource.Keywords" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	String sKeyWord = currRequestHelper.getString("kname");
	int nSiteId = currRequestHelper.getInt("siteId",0);
	int nSiteType = currRequestHelper.getInt("siteType",0);
	String sWhere = "SITEID in (0,?) and SITETYPE=? and KName like ?";
	WCMFilter filter = new WCMFilter("XWCMKEYWORD", sWhere, "KFREQ  desc","");
	filter.addSearchValues(0, nSiteId);
	filter.addSearchValues(1, nSiteType);
	filter.addSearchValues(2, sKeyWord + '%');
	Keywords currKeywords = Keywords.openWCMObjs(loginUser, filter);
	response.setContentType("text/javascript;charset=UTF-8");
	out.clear();
	out.println("[");
	boolean bFirst = true;
	if(currKeywords.size() > 0){
		for (int i = 0, nSize = currKeywords.size(); i < nSize; i++) {
			Keyword currKeyword = (Keyword) currKeywords.getAt(i);
			if (currKeyword == null)
				continue;
			if(!bFirst){
			out.println(",");
			}
			bFirst = false;
			out.println("{");
			out.println("value:\"" + CMyString.filterForHTMLValue(currKeyword.getKNAME())+"\",label:\"" + CMyString.filterForHTMLValue(currKeyword.getKNAME())+"\"");
			out.println("}");
		}
	}
	out.println("];");
%>