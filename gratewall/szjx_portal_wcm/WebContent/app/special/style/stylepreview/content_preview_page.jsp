<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.common.publish.widget.ContentStyle" %>
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
	<title WCMAnt:param="content_preview_page.doc.content"> 普通文档列表-内容样式预览 </title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script src="../../../js/easyversion/lightbase.js"></script>
	<script src="../../../js/easyversion/extrender.js"></script>
	<script src="../../../js/easyversion/elementmore.js"></script>
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
		body,table{
			scrollbar-face-color: #f6f6f6; 
			scrollbar-highlight-color: #ffffff; 
			scrollbar-shadow-color: #cccccc; 
			scrollbar-3dlight-color: #cccccc; 
			scrollbar-arrow-color: #330000; 
			scrollbar-track-color: #f6f6f6; 
			scrollbar-darkshadow-color: #ffffff;
		}
		/*此处设置了资源框架的宽度，以便查看当文档标题超出宽度时候截断的效果是否正常*/
		.p_index_doc_list{
			width:468px;
			height:300px;
			margin:0px;
		}
		/*框架样式*/
		.p_w_box{width:100%;height:100%;}
		.p_w_box .p_w_head{
			width:100%;overflow:hidden;
			height:26px;
			background:url(<%=sServerWcmPath%>/app/special/style/images/head-left.gif) repeat-x 0 0;
		}
		.p_w_box .p_w_head .p_w_title{
			float:left;line-height:100%;
			font-size:16px;
			font-weight:bold;
			padding-top:6px;
			padding-left:25px;
		}
		.p_w_box .p_w_head .p_w_title a:visited{
			text-decoration:none;
			color:#000000;
		}
		.p_w_box .p_w_head .p_w_title a:link{
			text-decoration:none;
			color:#000000;
		}
		.p_w_box .p_w_head .p_w_title a:active{
			text-decoration:none;
			color:#000000;
		}
		.p_w_box .p_w_head .p_w_title a:hover{
			text-decoration:none;
			color:#000000;
		}
		.p_w_box .p_w_head .p_w_more{
			float:right;height:100%;line-height:100%;
			font-size:12px;
			font-weight:normal;
			padding-top:8px;
			padding-right:10px;
			background:url(<%=sServerWcmPath%>/app/special/style/images/head-right.gif) right top no-repeat;
		}
		.p_w_box .p_w_head .p_w_more a:link{
			text-decoration:none;
			color:#000000;
		}
		.p_w_box .p_w_head .p_w_more a:visited{
			text-decoration:none;
			color:#000000;
		}
		.p_w_box .p_w_head .p_w_more a:active{
			text-decoration:none;
			color:#000000;
		}
		.p_w_box .p_w_head .p_w_more a:hover{
			text-decoration:underline;
			color:#ff0000;
		}
		.p_w_box .p_w_body{
			border-top:none;
			vertical-align:top;
			border-style:solid;
			border-width:1px;
			border-color:#A1D1FF;
			border-top:none;
		}
	 
		.p_w_box .p_w_body .p_w_body_bg .p_w_content_box{
			zoom:1;
		}
		.p_w_box .p_w_body .p_w_body_bg .p_w_content_box:after{
			content:".";
			font-size:0;
			line-height:0;
			height:0;
			clear:both;
		}
		.p_w_box .p_w_body .p_w_body_bg .p_w_content_box .p_w_content{
			background-color:#ffffff;
			padding:10px;
		}
		.p_w_box .p_w_foot{
			display:none;
		}
		.p_w_box .p_w_foot .p_w_foot_bg{
			display:none;
		}
		.p_doc_title_li{
			line-height:24px;
		}
	</style>
</head>
<body>
<div class="p_index_doc_list">
	<div class="p_w_box">
		<h2 class="p_w_head">
			<span class="p_w_title"><a href="#" target="_blank" WCMAnt:param="content_preview_page.docList">文档列表</a></span>
			<span class="p_w_more"><a href="#" target="_blank" WCMAnt:param="content_preview_page.more">更多&gt;&gt;</a></span>
		</h2>
		<div class="p_w_body">
			<div class="p_w_body_bg <%=CMyString.filterForHTMLValue(sCssFlag)%>">
			<!-- content begin -->
				<div class="p_w_content_box">
					<div class="p_w_content" >
						<h1 WCMAnt:param="content_preview_page.doc.not.found">没有找到符合条件的文档</h1>
						<ul>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" WCMAnt:param="content_preview_page.title1">穆里尼奥：弗格森卫冕欧冠将成史上最伟大</A></SPAN><SPAN class=datetime>09-05-25</SPAN><span class='doc_new_icon'></span></LI><li class="c_line"></li>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" WCMAnt:param="content_preview_page.title2">马队向圣西罗看台竖中指？ 告别夜他被谁激怒(图)</A></SPAN><SPAN class=datetime>09-05-25</SPAN><span class='doc_new_icon'></span></LI><li class="c_line"></li>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" WCMAnt:param="content_preview_page.title3">印度官员闪电出访斯里兰卡要跟中国争影响力</A></SPAN><SPAN class=datetime>09-05-25</SPAN><span class='doc_new_icon'></span></LI><li class="c_line"></li>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" WCMAnt:param="content_preview_page.title4">俄决定订购48架苏-35战机 空军编号苏-27SM2</A></SPAN><SPAN class=datetime>09-05-25</SPAN><span class='doc_new_icon'></span></LI><li class="c_line"></li>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" WCMAnt:param="content_preview_page.title5">巴军方称绝对不允许恐怖分子靠近中巴公路(图)</A></SPAN><SPAN class=datetime>09-05-25</SPAN><span class='doc_new_icon'></span></LI><li class="c_line"></li>
							<LI class=p_doc_title_li><SPAN class=p_doc_title><A href="#" WCMAnt:param="content_preview_page.title6">乌允许俄续用航母飞行员训练场 曾计划租给中国</A></SPAN><SPAN class=datetime>09-05-25</SPAN><span class='doc_new_icon'></span></LI><li class="c_line c_last_line"></li>
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