<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>

<%@include file="../../include/public_processor.jsp"%>
<%
	processor.setAppendParameters(new String[]{"OrderBy", "crtime desc"});
	MetaViews results = (MetaViews)processor.excute("wcm61_resourcestructure", "query");

	String selectItem = CMyString.showEmpty(processor.getParam("SelectItem"), "VIEWDESC");
	String searchValue = CMyString.showEmpty(processor.getParam(selectItem), "");
%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
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

	<link href="../css/blue/thumb_list_common.css" rel="stylesheet" type="text/css" />
	<link href="../js/button/button.css" rel="stylesheet" type="text/css" />
	<link href="../css/blue/jquery-wcm-ui-extend.css" rel="stylesheet" type="text/css" />
	<link href="resourcestr_list.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="container  shrinkNavTree" id="container">
	<!-- 左侧分类树区域 -->
	<div class="left">
		<iframe src="resourcestr_category.jsp" width="100%" height="100%" frameborder="0"></iframe>
	</div>
	<!-- 分隔符和打开、隐藏左侧分类树的操作区域 -->
	<div class="seperator">
		<div class="sepBar" id="sepBar"></div>
	</div>

	<!-- 右侧数据列表区域 -->
	<div class="right">
		<!-- 页面头部区域，提供的是操作按钮栏，描述信息和检索框 -->
		<div class="header">
			<div class="oper_field">
				<!-- 操作按钮栏区域 -->
				<div class="toolbar" id="toolbar">
					<a href="#"  onclick="newResStr();">新建资源结构</a>
					<a href="#"  onclick="importResStr();">导入</a>
					<a href="#"  onclick="exportResStr();">导出</a>
					<a href="#"  onclick="deleteResStr();">删除</a>
					<a href="#"  onclick="editResStrclassinfo();">维护资源结构分类</a>
				</div>
				<!-- 检索框区域 -->
				<div class="querySearch" id="querySearch">
					<form method="get" id="search_form" action="resourcestr_list.jsp">
						<input type="text" name="SearchValue" id="search_input" class="search_input" value="<%=CMyString.filterForHTMLValue(searchValue)%>" />
						<select name="SelectItem" id="search_select" class="search_select">
							<option value="VIEWDESC" <%="VIEWDESC".equals(selectItem)?"selected=true":""%>>资源名称</option>
							<option value="VIEWDESCCON" <%="VIEWDESCCON".equals(selectItem)?"selected=true":""%>>资源描述</option>
						</select>
						<input type="button" name="" id="search_btn" class="search_btn" value="" />
					</form>
				</div>
			</div>
			<!-- 描述信息区域 -->
			<div class="desc">您可以维护以下资源结构</div>
		</div>

		<!-- 数据区域 -->
		<div class="content" id="list-data">
			<%
				for(int i=0,nNum = results.size(); i<nNum; i++) {
					MetaView oMetaView = (MetaView)results.getAt(i);
					if(oMetaView==null)continue;
					int nMetaViewId = oMetaView.getId();
					String sShowName = oMetaView.getDesc();
					String sCrUser = oMetaView.getPropertyAsString("cruser");
					String sCrTime = oMetaView.getPropertyAsString("CrTime");
			%>
					<div class="thumb" itemid="<%=nMetaViewId%>" id="thumb_<%=nMetaViewId%>">
						<div class="resource_pic" title="编号: <%=nMetaViewId%>&#13;唯一标识: <%=CMyString.filterForHTMLValue(sShowName)%>&#13;创建者: <%=CMyString.filterForHTMLValue(sCrUser)%>&#13;创建时间: <%=CMyString.filterForHTMLValue(sCrTime)%>"></div>
						<div class="resource_desc">
							<span class="item_ckx"><input type="checkbox" name="metaView" value="<%=nMetaViewId%>" id="cbx_<%=nMetaViewId%>"  objectId="<%=nMetaViewId%>"/><label class="resource_title"  for="cbx_<%=nMetaViewId%>"><%=CMyString.transDisplay(sShowName)%></label></span>
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