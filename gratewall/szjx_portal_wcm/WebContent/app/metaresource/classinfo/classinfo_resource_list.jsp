<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>

<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@include file="../../include/public_processor.jsp"%>

<%
	int nClassInfoId = processor.getParam("CLASSINFOID",0);
out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 分类资源列表 </title>
	<script src="../../metaresource/js/jquery.js"></script>
	<script src="../../metaresource/js/jquery-wcm-extend.js"></script>
	<link href="../css/blue/thumb_list_common.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		li{
			padding-left:3px;
			background:url(../css/blue/images/tab_bg_l.gif) left center no-repeat;
			float:left;
			height:27px;
			line-height:27px;
			margin-left:4px;
			display:block;
		}
		.actived {
			background:url(../css/blue/images/tab_bg_l_act.gif) left center no-repeat;
			zoom:1;
		}
		li a{
			padding-right:3px;
			height:27px;
			display:inline-block;
			text-decoration:none;
			background:url(../css/blue/images/tab_bg_r.gif) right center no-repeat;
		}
		.actived a{
			background:url(../css/blue/images/tab_bg_r_act.gif) right center no-repeat;
		}
		li a span{
			height:27px;
			display:inline-block;
			background:url(../css/blue/images/tab_bg_c.gif) center center repeat-x;
			min-width:40px;
			text-align:center;
			padding:0px 5px;
		}
		.actived a span{
			background:url(../css/blue/images/tab_bg_c_act.gif) center center repeat-x;
		}
		ul{
			margin-left:20px;
			margin-top:8px;
			height:28px;
			position:absolute;/*for covering*/
			left:0px;
			top:1px;
			right:0px;
			bottom:0px;
		}
		.viewtab{
			position:absolute;
			top:0px;
			left:0px;
			right:0px;
			bottom:0px;
			border:1px solid #BABABA; /*#a8e3fe;*/
			margin:5px;
		}
		.tab_content{

			position:absolute;
			left:0px;
			top:35px;
			right:0px;
			bottom:0px;
		}
	</style>

	<script language="javascript">
	<!--
		$(document).ready(function(){
			$('#sepBar').bind("click",function(){
				$('#container').toggleClass('shrinkNavTree');
			});
			$(".viewtab ul").bind('click',function(evt){
				evt = window.event||evt;
				var srcElement = evt.target||evt.srcElement;
				var jDom = $(srcElement).closest("li");
				if(jDom.length==0)return;
				removeActive();
				doClick(jDom);
			})
			function removeActive(){
				$(".viewtab li").each(function(){
					$(this).removeClass("actived");
				})
			}
			function doClick(jDom){
				jDom.addClass("actived");
				var sUrl = jDom.children("a").first().attr("href");
				sUrl = sUrl.substr(1);
				$("#ifrm_content").attr("src",sUrl);
			}
			function initTab(){
				var jDom = $(".viewtab li").first();
				if(jDom.length==0)return;
				doClick(jDom);
			}
			initTab();
		});
	//-->
	</script>
</head>

<body>
<script src="../../metaresource/js/cssrender.js"></script>
<div class="container " id="container">
	<div class="left">
		<iframe id="nav-tree" src="classinfo_tree.jsp?classInfoId=<%=nClassInfoId%>" width="100%" height="100%" frameborder="0"></iframe>
	</div>
	<div class="seperator">
		<div class="sepBar" id="sepBar"></div>
	</div>
	<div class="right">
		<!-- 页面头部区域，提供的是操作按钮栏，描述信息和检索框 -->
		<div class="viewtab" id="viewtab" >
			<ul style="z-index:9999;">
				<li><a href="#./classinfo_any_view_list.jsp?classInfoId=<%=nClassInfoId%>"><span>全部</span></a></li>
			<%
				processor.setAppendParameters(new String[]{"CLASSINFOID",""+nClassInfoId});
				MetaViews results = (MetaViews)processor.excute("wcm61_ClassInfoView", "queryViewsByClassInfo");
				for (int i = 0,nLen = results.size(); i < nLen; i++){
					MetaView oMetaView = (MetaView)results.getAt(i);
					if(oMetaView==null)continue;
					int nMetaViewId = oMetaView.getId();
					String sShowName = oMetaView.getDesc();
					int nChannelId = oMetaView.getOwnerId();
					String sUrl = "../../application/"+nMetaViewId+"/classinfo_metaviewdata_list.jsp?ChannelId="+nChannelId+"&SiteType=4&ChannelType=0&RightValue=111111111111111111111111111111111111111111111";
			%>
				<li><a href="#<%=sUrl%>"><span><%=CMyString.transDisplay(sShowName)%></span></a></li>
			<%
				}
			%>
			</ul>
			<div class="tab_content" style="z-index:999;"><iframe src="../../include/blank.html" id="ifrm_content" FRAMEBORDER=0 width="100%" height="100%" style="z-index:999;"></iframe></div>
		</div>
	</div>
</div>
</body>
</html>