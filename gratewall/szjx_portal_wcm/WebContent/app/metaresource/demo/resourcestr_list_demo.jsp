<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>

<%@include file="../../include/public_processor.jsp"%>
<%
	MetaViews results = (MetaViews)processor.excute("wcm61_resourcestructure", "query");

%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title> 资源中心 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<script src="../js/jquery-wcm-ui-extend.js"></script>
	<script src="resourcestr_list.js"></script>
	<link href="../css/thumb_list_common.css" rel="stylesheet" type="text/css" />
	<link href="../js/button/button.css" rel="stylesheet" type="text/css" />
	<link href="resourcestr_list.css" rel="stylesheet" type="text/css" />
	
</head>
<script language="javascript">
<!--
	$(function() {
		$(".toolbar a:first").imgbutton();
		$(".toolbar a:eq(1)").imgbutton();
		$(".toolbar a:last").morebutton();
	});

	function add(){
		alert("1:add");
	}
	function editsome(){
		alert("2:edit");
	}
	function edit(){
		alert("edit");
	}
//-->
</script>

<body>
<div class="container" id="container">
	<!-- 左侧分类树区域 -->
	<div class="left">
		&nbsp;
	</div>
	<!-- 分隔符和打开、隐藏左侧分类树的操作区域 -->
	<div class="seperator">
		<div class="sepBar" id="sepBar"></div>
	</div>

	<!-- 右侧数据列表区域 -->
	<div class="right">
		<!-- 页面操作区域，包括按钮和检索框 -->
		<div class="oper_field">
			<!-- 操作按钮栏区域 -->
			<div class="toolbar" id="toolbar">
				<a href="#"  onclick="add();">新建新建新建新建</a>
				<a href="#"  onclick="edit();">修改</a>
				<a href="#" onclick="editsome();">批量修改</a>
				<div id="editsome" class="ui-btn-more" style="display:none;">
					<div onclick="add();" class="ui-btn-more-item">修改字段</div>
					<div onclick="edit();" class="ui-btn-more-item">修改布局</div>
				</div>
				<!--<a href="#">修改</a>
				<a href="#">删除</a>
				<a href="#">批量修改</a>
				<a href="#" class="hasmore"></a>-->
			</div>
			<!-- 检索框区域 -->
			<div class="querySearch" id="querySearch">
				<form method="post" id="search_form" action="resourcestr_list_demo.jsp">
					<input type="text" name="" id="search_input" class="search_input" value="" />
					<select name="" id="search_select" class="search_select">
						<option value="VIEWDESC">资源描述</option>
						<option value="VIEWNAME">资源名称</option>
					</select>
					<input type="button" name="" id="search_btn" class="search_btn" value="" />
				</form>
			</div>
		</div>
		<!-- 描述信息区域 -->
		<div class="desc">您可以管理以下结构的资源</div>

		<!-- 数据区域 -->
		<div class="content" id="list-data">
			<%
				for(int i=0,nNum = results.size(); i<nNum; i++) {
					MetaView oMetaView = (MetaView)results.getAt(i);
					if(oMetaView==null)continue;
					int nMetaViewId = oMetaView.getId();
					String sViewName = oMetaView.getName();
					String sShowName = oMetaView.getDesc();
			%>
					<div class="thumb" itemid="<%=nMetaViewId%>" id="thumb_<%=nMetaViewId%>">
						<div class="resource_pic"></div>
						<div class="resource_desc">
							<span class="item_ckx"><input type="checkbox" name="" value="<%=nMetaViewId%>" id="cbx_<%=nMetaViewId%>" /></span>
							<span class="resource_title"><%=CMyString.transDisplay(sViewName)%></span>
							<span class="add_resource"></span>
						</div>
					</div>
			<%
				}
			%>
		</div>

		<!-- 底部区域，主要是当需要的时候，做翻页的区域 -->
		<div class="footer">
			
		</div>
	</div>
</div>
</body>
</html>