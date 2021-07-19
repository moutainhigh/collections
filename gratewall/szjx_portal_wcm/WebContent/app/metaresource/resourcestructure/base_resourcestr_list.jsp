<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>

<%@include file="../../include/public_processor.jsp"%>
<%
	//1.1 获取到指定参数检索的字段和值
	String sSelectItem = request.getParameter("SelectItem");
	String sSearchValue = request.getParameter("SearchValue");

	//1.2 如果sSearchValue不为空，则传值sSelectItem和sSearchValue。否则不传
	if(!CMyString.isEmpty(sSearchValue)) {
		processor.setAppendParameters(new String[] {sSelectItem,sSearchValue});
	}

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

	<script src="base_resourcestr_list.js"></script>

	<link href="../css/thumb_list_common.css" rel="stylesheet" type="text/css" />
	<link href="base_resourcestr_list.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.toolbar{float:left;}
		.toolbar a{text-decoration:none;text-align:center;}
		.ui-button{
			height:25px;
			line-height:25px;
			display:inline-block;
		}
		.ui-button *{
			vertical-align:middle;
		}
		.ui-button-icon-primary{
			width:3px;
			height:100%;
			background:url(../images/list/cz_btn_l.gif) center center no-repeat;
			display:inline-block;
		}
		.ui-button-text{
			padding:0px 12px;
			height:100%;
			background:url(../images/list/cz_btn_c.gif) center center repeat-x;
			display:inline-block;
		}
		.ui-button-icon-secondary{
			width:3px;
			height:100%;
			background:url(../images/list/cz_btn_r.gif) center center no-repeat;
			display:inline-block;
		}
		.toolbar .hasmore{
			width:23px;
			background:url(../images/list/cz_btn_more.gif) center center no-repeat;
			display:inline-block;
			margin-left:-9px;
			vertical-align:middle;
		}
		.toolbar .hasmore .ui-button-text{
			display:none;
		}

	</style>
</head>
<script language="javascript">
<!--
	$(function() {
		$( ".toolbar a:first" ).button({
            icons: {
                primary: "ui-icon-gear",
                secondary: "ui-icon-triangle-1-s"
            },
            text: false
        }).click(function(){
			alert(1);
		})
		.next().button({
            text: false
        }).click(function(){
			alert(5);
		});
	});

	
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
		<!-- 页面头部区域，提供的是操作按钮栏，描述信息和检索框 -->
		<div class="header">
			<div class="oper_field">
				<!-- 操作按钮栏区域 -->
				<div class="toolbar" id="toolbar">
					<a href="#">增量发布这个资源结构</a>
					<a href="#" class="hasmore"></a>
				</div>
				<!-- 检索框区域 -->
				<div class="querySearch" id="querySearch">
					<form method="post" id="search_form" action="base_resourcestr_list.jsp">
						<input type="text" name="SearchValue" id="search_input" class="search_input" value="" />
						<select name="SelectItem" id="search_select" class="search_select">
							<option value="VIEWDESC">资源描述</option>
							<option value="VIEWNAME">资源名称</option>
						</select>
						<input type="button" name="" id="search_btn" class="search_btn" value="" />
					</form>
				</div>
			</div>
			<!-- 描述信息区域 -->
			<div class="desc">您可以管理以下结构的资源</div>
		</div>

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