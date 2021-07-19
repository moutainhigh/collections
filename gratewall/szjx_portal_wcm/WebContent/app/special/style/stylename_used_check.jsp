<%
/** Title:				stylename_used_check.jsp
 *  Description:
 *        检查StyleName是否可用。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:				Archer
 *  Created:			2010年5月6日
 *  Vesion:				1.0
 *  Last EditTime	:none
 *  Update Logs:	none
 *  Parameters:		see stylename_used_check.xml
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page  import="com.trs.infra.common.WCMException" %>
<%@ page  import="com.trs.components.common.publish.widget.ResourceStyle" %>
<%@ page  import="com.trs.components.common.publish.widget.ContentStyle" %>
<%@ page  import="com.trs.components.common.publish.widget.ResourceStyles" %>
<%@ page  import="com.trs.components.common.publish.widget.ContentStyles" %>
<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	//获取参数
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
	int nCurrStyleId = currRequestHelper.getInt("CurrStyleId",0);
	int nStyleType = currRequestHelper.getInt("StyleType",ResourceStyle.OBJ_TYPE);
	String sStyleName = CMyString.showNull(currRequestHelper.getString("StyleName"),"");
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap parameters = new HashMap();
	parameters.put("PageStyleId", String.valueOf(nPageStyleId));
	parameters.put("StyleName", sStyleName);

	HashMap hmStylesOfStyleName = new HashMap();
	if(nStyleType==ResourceStyle.OBJ_TYPE){
		parameters.put("CurrResourceStyleId", String.valueOf(nCurrStyleId));
		hmStylesOfStyleName = (HashMap)processor.excute("wcm61_resourcestyle","findStyleNameUsedInfo", parameters);
	}else if(nStyleType==ContentStyle.OBJ_TYPE){
		parameters.put("CurrContentStyleId", String.valueOf(nCurrStyleId));
		hmStylesOfStyleName = (HashMap)processor.excute("wcm61_contentstyle","findStyleNameUsedInfo", parameters);
	}

	ResourceStyles oResourceStyles = (ResourceStyles)hmStylesOfStyleName.get("ResourceStyles");
	ContentStyles oContentStyles = (ContentStyles)hmStylesOfStyleName.get("ContentStyles");
	
	int size = 0;
	if(nStyleType==ResourceStyle.OBJ_TYPE){
		size = oResourceStyles.size();
	}else if(nStyleType==ContentStyle.OBJ_TYPE){
		size = oContentStyles.size();
	}
	out.clear();
%>
<%=size%>