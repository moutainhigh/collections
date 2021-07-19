<%
/** Title:				stylename_used_info.jsp
 *  Description:
 *        检查StyleName是否可用。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:				Archer
 *  Created:			2010年5月6日
 *  Vesion:				1.0
 *  Last EditTime	:none
 *  Update Logs:	none
 *  Parameters:		see stylename_used_info.xml
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page  import="com.trs.infra.common.WCMException" %>
<%@ page  import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page  import="com.trs.components.common.publish.widget.ResourceStyle" %>
<%@ page  import="com.trs.components.common.publish.widget.ResourceStyles" %>
<%@ page  import="com.trs.components.common.publish.widget.ContentStyle" %>
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

	out.clear();
%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<meta name="author" content="TRSArcher">
	<title WCMAnt:para="stylename_used_info.jsp.currnamehasbeenused">当前名称已经被使用</title>
	<style type="text/css">
		body{
			background:#ffffff;
			font-size:14px;
			font-weight:normal;
			scrollbar-face-color: #f6f6f6; 
			scrollbar-highlight-color: #ffffff; 
			scrollbar-shadow-color: #cccccc; 
			scrollbar-3dlight-color: #cccccc; 
			scrollbar-arrow-color: #330000; 
			scrollbar-track-color: #f6f6f6; 
			scrollbar-darkshadow-color: #ffffff;
		}
		body,div,ul,li,span{padding:0;margin:0;}
		.StyleName{
			color:#FF8010;
		}
		.message{
			border-bottom:1px solid #9D9D9D;
			padding:0 0 5px 15px;
		}
		.info_con{
			padding:10px 15px 5px 15px;
			height:260px;
			width:100%;
			overflow:auto;
		}
		.info_con ul{
			margin:5px 0 5px 35px;
		}
		.style_type{
			color:#097F12;
		}
	</style>
	<script type="text/javascript">
	<!--
		// 自定义 crashboard 的按钮
		window.m_cbCfg = {
			btns : [
				{
					text : '关闭'
				}
			]
		};
	//-->
	</script>
</head>
<body>
	<div class="message"  WCMAnt:para="stylename_used_info.jsp.namehasbeenuseddetailinfoasfollow">
		名称[<span class="StyleName"><%=CMyString.filterForHTMLValue(sStyleName)%></span>]已经被使用，详细信息如下:
	</div>
	<div class="info_con">
		<%
			if(oResourceStyles.size()>0){
		%>
		<div class="style_type" WCMAnt:para="stylename_used_info.jsp.resourcestyle">资源风格</div>
		<ul>
		<%
			}
			for(int i=0;i<oResourceStyles.size();i++){
				String sStyleInfo = "";
				ResourceStyle oResourceStyle = (ResourceStyle) oResourceStyles.getAt(i);
				if (oResourceStyle == null) {
					continue;
				}
				int nCurrPageStyleId = oResourceStyle.getPageStyleId();
				// 当前的风格隶属于某个页面风格
				if (nCurrPageStyleId > 0) {
					PageStyle oCurrPageStyle = PageStyle.findById(nCurrPageStyleId);
					if (oCurrPageStyle == null) {
						continue;
					}
					sStyleInfo = oCurrPageStyle.getStyleDesc() + "->";
				} else {
                    
					sStyleInfo += LocaleServer.getString("stylename_used_info.jsp.systemlevel","系统级别->");
				}
				sStyleInfo += oResourceStyle.getStyleName();
		%>
			<li><%=CMyString.filterForHTMLValue(sStyleInfo)%></li>
		<%
			}
			if(oResourceStyles.size()>0){
		%>
		</ul>
		<%
			}
			if(oContentStyles.size()>0){
		%>
		<div class="style_type"  WCMAnt:para="stylename_used_info.jsp.contentstyle">内容风格</div>
		<ul>
		<%
			}
			for(int i=0;i<oContentStyles.size();i++){
				String sStyleInfo = "";
				ContentStyle oContentStyle = (ContentStyle) oContentStyles.getAt(i);
				if (oContentStyle == null) {
					continue;
				}
				int nCurrPageStyleId = oContentStyle.getPageStyleId();
				// 当前的风格隶属于某个页面风格
				if (nCurrPageStyleId > 0) {
					PageStyle oCurrPageStyle = PageStyle.findById(nCurrPageStyleId);
					if (oCurrPageStyle == null) {
						continue;
					}
					sStyleInfo = oCurrPageStyle.getStyleDesc() + "->";
				} else {
					sStyleInfo += LocaleServer.getString("stylename_used_info.jsp.systemlevel","系统级别->");
				}
				sStyleInfo += oContentStyle.getStyleName();
		%>
			<li><%=CMyString.filterForHTMLValue(sStyleInfo)%></li>
		<%
			}
			if(oContentStyles.size()>0){
		%>
		</ul>
		<%
			}
		%>
	</div>
</body>
</html>