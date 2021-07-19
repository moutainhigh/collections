<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.resource.domain.Fields" %>
<%@ page import="com.trs.components.metadata.resource.domain.Field" %>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 字段库管理 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<script src="../js/jquery-wcm-ui-extend.js"></script>
	<script src="libfield_list.js"></script>

	<link href="../css/blue/list_common.css" rel="stylesheet" type="text/css" />
	<link href="../js/crashboard/crashboard.css" rel="stylesheet" type="text/css" />
	<link href="../js/button/button.css" rel="stylesheet" type="text/css" />
	<link href="libfield_list.css" rel="stylesheet" type="text/css" />

	<script language="javascript">
<!--
	$(function() {
		$(".toolbar a:first").imgbutton();
		$(".toolbar a:eq(1)").imgbutton();
		$(".toolbar a:last").morebutton();
		/*$( ".toolbar a:first" ).button({
            icons: {
                primary: "ui-icon-gear",
                secondary: "ui-icon-triangle-1-s"
            },
            text: false
        }).click(function(){
			alert(1);
		})
		.next().button({
            icons: {
                primary: "ui-icon-gear",
                secondary: "ui-icon-triangle-1-s"
            },
            text: false
        }).click(function(){
			alert(2);
		})
		.next().button({
            icons: {
                primary: "ui-icon-gear",
                secondary: "ui-icon-triangle-1-s"
            },
            text: false
        }).click(function(){
			alert(3);
		})
		.next().button({
            icons: {
                primary: "ui-icon-gear"
            },
            text: false
        }).click(function(){
			alert(4);
		})
		.next().button({
            text: false
        }).click(function(){
			alert(5);
		});*/
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
</head>

<body>
<div class="container shrinkNavTree" id="container">
<div class="left">
	&nbsp;
</div>
<div class="seperator">
	<div class="sepBar" id="sepBar">
		
	</div>
</div>
<div class="right">
	<!-- 页面头部区域，提供的是操作按钮栏，描述信息和检索框 -->
	<div class="header">
		<!-- 描述信息区域 -->
		<div class="desc"></div>
		<div class="oper_field">
			<!-- 操作按钮栏区域 -->
			<div class="toolbar" id="toolbar">
				<a href="#"  onclick="add();">新建新建新建新建</a>
				<div id="new_field" class="btn"></div>
				<div id="create_view" class="btn"></div>
				<div id="delete_field" class="btn"></div>
				<div id="manger_field_class" class="btn"></div>
			</div>
			<!-- 检索框区域 -->
			<div class="querySearch" id="querySearch"></div>
		</div>
	</div>

	<!-- 数据区域 -->
	<div class="content" id="list-data">
	
<%
try{
%>

<%@include file="../../include/public_processor.jsp"%>

<%
	//调用服务，获取字段集合
	Fields objs = (Fields) processor.excute("wcm61_field", "query");

	//将字段集合进行分页设置
	CPager currPager = new CPager(processor.getParam("PageSize", 20));
	currPager.setCurrentPageIndex(processor.getParam("CurrPage", 1));
	currPager.setItemCount(objs.size());
%>

<table cellspacing=0 border="0" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" class="head_td">全选</td>
		<td class="head_td">编辑</td>
		<td class="head_td">字段名称</td>
		<td class="head_td">显示名称</td>
		<td class="head_td">类型</td>
		<td class="head_td">库中类型</td>
		<td class="head_td_last">删除</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Field obj = (Field)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nFieldId = obj.getId();
			String sFieldName = obj.getFieldName();
			String sShowName = obj.getAnotherName();
			String sFieldType = obj.getFieldTypeDesc();
			String sDBType = obj.getDBTypeDesc();
%>
		<tr ObjectId="<%= nFieldId %>">
			<td onclick="wcm.Grid.selectAll();" width="50" class="body_td"><input type="checkbox" name="" id="" value="" /> 1</td>
			<td class="body_td"><span class="edit">&nbsp;</span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sFieldName)%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sShowName)%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sFieldType)%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sDBType)%></span></td>
			<td class="body_td"><span class="delete" cmd="delete" style="cursor: pointer;" >&nbsp;</span></td>
		</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>

	</tbody>
<%if(objs.isEmpty()){%>
	<tbody id="grid_NoObjectFound">
		<tr><td colspan="7" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
<%}%>
</table>
<script>
	try{
		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
	}catch(err){
		//alert(err.message);
		//Just skip it.
	}
</script>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException("libfield_list.jsp运行期异常!", tx);
}
%>

	</div>

	<!-- 底部区域，主要是当需要的时候，做翻页的区域 -->
	<div class="footer">
		<div class="page-nav" id="list_navigator"></div>
	</div>
</div>
</div>
</body>
</html>