<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page  import="com.trs.infra.common.WCMException" %>
<%@ page  import="com.trs.components.common.publish.widget.ResourceStyle" %>
<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	// 1、获取参数
	String sCssFlag = CMyString.showNull(currRequestHelper.getString("CssFlag"),"");
	String sHost = request.getServerName();
	int nPort = request.getServerPort();
	String sServerWcmdataTemplatePath = "http://" + sHost + ":" + nPort + "/template/style/style_common/";
	String sContextPath = request.getContextPath();
	String sServerWcmPath = "http://" + sHost + ":" + nPort + sContextPath;
	out.clear();
%>
<html>
<head>
	<title WCMAnt:param="resource_preview_page.doc-content"> 普通文档列表-资源样式预览 </title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script src="../../../js/easyversion/lightbase.js"></script>
	<script src="../../../js/easyversion/extrender.js"></script>
	<script src="../../../js/easyversion/elementmore.js"></script>
	<style type="text/css" id="CssStyle">
		
	</style>
	<script type="text/javascript">
	<!--
				// 定义 crashboard 的内容，标题、尺寸、按钮
		window.m_cbCfg = {
			btns : [
				{
					text : '关闭'
				}
			]
		}
		function init(_params){
			try{
				var sCssContent = _params['CssContent'] || "";
				var sStyleId = 'dy_adjust';
				var eStyleDiv = document.getElementById(sStyleId);
				var eStyle = document.createElement('STYLE');
				eStyle.setAttribute("type", "text/css");
				if(eStyle.styleSheet){// IE
					eStyle.styleSheet.cssText = sCssContent;
				} else {// w3c
					var cssText = document.createTextNode(sCssContent);
					eStyle.appendChild(cssText);
				}
				$removeChilds(eStyleDiv);
				eStyleDiv.appendChild(eStyle);
			}catch(ex){
				alert(ex);
			}
			Event.observe(document, 'click', function(event){
				var dom = Event.element(event);
				var link = findElement(dom, 'href');
				if(link && link.tagName == 'A'){
					Event.stop(event);	 
				}
			});
		}
		function findElement(dom, sIdentify){
			while(dom && dom.tagName != 'BODY'){
				if(dom.getAttribute(sIdentify) != null){
					return dom;
				}
				dom = dom.parentNode;
			}
			return null;
		}
		function $removeChilds(_node){
			_node.innerHTML = "";
		}
	//-->
	</script>
<link href="../../ext-res/common/RCStyleSameCSS.css" rel="stylesheet" type="text/css" />
<base HREF="<%=sServerWcmdataTemplatePath%>">
<div id="dy_adjust">
</div>
	<style type="text/css">
		/*此处设置了资源框架的宽度，以便查看当文档标题超出宽度时候截断的效果是否正常*/
		.p_index_doc_list{
			width:468px;
			height:300px;
			margin:0px;
		}
		div,ul,li{zoom:1;}
		/*列表样式*/
		.p_w_box .p_w_body .p_w_body_bg .p_w_content_box .p_w_content .p_w_list li{
			width:100%;overflow:hidden;text-overflow:ellipsis;word-break:breakall;white-space:nowrap;
			font-weight:normal;
			font-family:"宋体";
			font-size:14px;
			line-height:24px;
			padding-left:10px;
			background:url(<%=sServerWcmPath%>/app/special/style/images/dot.gif) left center no-repeat;
		}
		.p_w_box .p_w_body .p_w_body_bg .p_w_content_box .p_w_content .p_w_list li a:link{
			text-decoration:none;
			color: #0063b8;
		}
		.p_w_box .p_w_body .p_w_body_bg .p_w_content_box .p_w_content .p_w_list li a:active{
			text-decoration:none;
			color: #0063b8;
		}
		.p_w_box .p_w_body .p_w_body_bg .p_w_content_box .p_w_content .p_w_list li a:visited{
			text-decoration:none;
			color: #0063b8;
		}
		.p_w_box .p_w_body .p_w_body_bg .p_w_content_box .p_w_content .p_w_list li a:hover{
			text-decoration:underline;
			color:#ff8010;
		}
		.p_w_box .p_w_body .p_w_body_bg .p_w_content_box .p_w_content .p_w_list .p_doc_title .datetime{
			display:inline;
			font-size:12px;
			font-family:"宋体";
			color:#8b8b8b;
		}
		.p_doc_title_li{
			line-height:24px;
			position:relative;
		}
		.datetime{
			margin-left:10px;
			color:gray;
		}
	</style>
</head>
<body>
<div class="p_index_doc_list <%=CMyString.filterForHTMLValue(sCssFlag)%>">
	<div class="p_w_box">
		<h2 class="p_w_head">
			<span class="p_w_title"><a href="#" target="_blank" WCMAnt:param="resource_preview_page.docList">文档列表</a></span>
			<span class="p_w_more"><a href="#" target="_blank" WCMAnt:param="resource_preview_page.more">更多&gt;&gt;</a></span>
		</h2>
		<div class="p_w_body">
			<div class="p_w_body_bg">
			<!-- content begin -->
				<div class="p_w_content_box">
					<div class="p_w_content">
							<ul>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" target=_blank WCMAnt:param="resource_preview_page.title1">穆里尼奥：弗格森卫冕欧冠将成史上最伟大</A></SPAN><SPAN class=datetime>09-05-25</SPAN></LI>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" target=_blank WCMAnt:param="resource_preview_page.title2">马队向圣西罗看台竖中指？ 告别夜他被谁激怒(图)</A></SPAN><SPAN class=datetime>09-05-25</SPAN></LI>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" target=_blank WCMAnt:param="resource_preview_page.title3">印度官员闪电出访斯里兰卡要跟中国争影响力</A></SPAN><SPAN class=datetime>09-05-25</SPAN></LI>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" target=_blank WCMAnt:param="resource_preview_page.title4">俄决定订购48架苏-35战机 空军编号苏-27SM2</A></SPAN><SPAN class=datetime>09-05-25</SPAN></LI>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" target=_blank WCMAnt:param="resource_preview_page.title5">巴军方称绝对不允许恐怖分子靠近中巴公路(图)</A></SPAN><SPAN class=datetime>09-05-25</SPAN></LI>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" target=_blank WCMAnt:param="resource_preview_page.title6">乌允许俄续用航母飞行员训练场 曾计划租给中国</A></SPAN><SPAN class=datetime>09-05-25</SPAN></LI>
						</ul>
					</div>
				</div>
			<!-- content end -->
			</div>
		</div>
		<div class="p_w_foot">
			<div class="p_w_foot_bg"></div>
		</div>
	</div>
</div>
</body>
</html>