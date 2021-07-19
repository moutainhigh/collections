<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="widget_select_index.title">选择资源</title>
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/easyversion/elementmore.js"></script>
<script src="../js/adapter4Top.js"></script>
<script src="../../js/source/wcmlib/tabpanel/TabPanel.js"></script>
<script language="javascript" src="./widget_select_index.js" type="text/javascript"></script>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/tabpanel.css" rel="stylesheet" type="text/css" />
<link href="./widget_select_index.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="box" id="data" action="">
	<!-- 左侧检索框 -->
	<div id="left_query" class="left_query">
		<div id="selectTitle" class="selectTitle">
			选择分类
		</div>
		<div id="widgetSelect_All" class="select_btn">全部</div>
		<div id="widgetSelect_Recently" class="select_btn">最近使用</div>
		<%
			//文字列表#1,图片列表#2,栏目导航#3,单篇文章#4,其他#1218
			String sWidgetCategorys = ConfigServer.getServer().getSysConfigValue("WIDGET_CATEGORYS", "");
			if(!CMyString.isEmpty(sWidgetCategorys)){
				String sItemSplit = ",";
				String sValueSplit = "#";
				String[] aWidgetCategorys = sWidgetCategorys.split(sItemSplit);
				String sWidgetCategoryName = "";
				String sWidgetCategoryId = "";
				for (int i = 0; i < aWidgetCategorys.length; i++) {
					String[] aWidgetCategory = aWidgetCategorys[i].split(sValueSplit);
					sWidgetCategoryName = aWidgetCategory[0];
					sWidgetCategoryId = aWidgetCategory[1];
		%>
		<div id="widgetSelect_<%=CMyString.filterForHTMLValue(sWidgetCategoryId)%>" class="select_btn"><%=CMyString.transDisplay(sWidgetCategoryName)%></div>
		<%
				}
			}
		%>

	</div>
	<!-- 右侧query界面 -->
	<div id="right_query" class="right_query">
		<iframe src="" id="right_query_iframe" frameborder="0" style="height:100%;width:100%"></iframe>
	</div>
</div>
</body>
</html>