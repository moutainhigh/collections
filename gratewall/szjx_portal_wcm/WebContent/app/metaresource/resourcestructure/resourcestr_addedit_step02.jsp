<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%out.clear();%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 新建资源结构：步骤二 </title>
	<link href="../css/blue/list_common.css" rel="stylesheet" type="text/css" />
	<link href="../js/button/button.css" rel="stylesheet" type="text/css" />
	<link href="../js/crashboard/crashboard.css" rel="stylesheet" type="text/css" />
	<link href="resourcestr_addedit_step02.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-ui-extend.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<script language="javascript" src="resourcestr_addedit_step02.js" type="text/javascript"></script>
	<script language="javascript">
	<!--
		var m_viewId = $.getParameter("ViewId");
	//-->
	</script>
</head>

<body>
<div class="container" id="container">
	<!-- 页面头部区域，提供的是操作按钮栏，描述信息和检索框 -->
	<div class="header">
		<!-- 描述信息区域 -->
		<div class="desc">步骤二 选择或新建字段</div>
		<div class="oper_field">
			<!-- 操作按钮栏区域 -->
			<div class="toolbar" id="toolbar">
				<span cmd="select_fields" class="btn" >选择字段</span>
				<span cmd="new_field" class="btn">新建字段</span>
				<span cmd="set_properties" class="btn">批量设置字段属性</span>
				<span cmd="del" class="btn">删除</span>
			</div>
			<!-- 检索框区域 -->
			<div class="querySearch" id="querySearch"></div>
		</div>
	</div>
	<!-- 数据区域 -->
	<div class="content" id="list-data">
<%@include file="../../include/public_processor.jsp"%>
<%
	//调用服务，获取字段集合
	MetaViewFields objs = (MetaViewFields) processor.excute("wcm6_MetaDataDef", "queryViewFieldInfos");

	//将字段集合进行分页设置
	CPager currPager = new CPager(processor.getParam("PageSize", 20));
	currPager.setCurrentPageIndex(processor.getParam("CurrPage", 1));
	currPager.setItemCount(objs.size());
%>

<table cellspacing=0 border="0" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="" width="50" class="head_td selAll">全选</td>
		<td class="head_td">编辑</td>
		<td class="head_td">字段名称</td>
		<td class="head_td">显示名称</td>
		<td class="head_td">类型</td>
		<td class="head_td">概览显示</td>
		<td class="head_td">库中类型</td>
		<td class="head_td_last">删除</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		MetaViewField obj = (MetaViewField)objs.getAt(i - 1);
		if (obj == null)
			continue;
		int nFieldId = obj.getId();
		String sFieldName = obj.getName();
		String sShowName = obj.getAnotherName();
		String sFieldType = obj.getTypeDesc();
		String sDBType = obj.getDBTypeDesc();
		boolean isInOutline = obj.isInOutline();
%>
		<tr ObjectId="<%=nFieldId%>">
			<td onclick="" width="50" class="body_td"><input type="checkbox" class="select" name="" id="" value="" /> <span><%=i%></span></td>
			<td class="body_td"><span class="edit" cmd="edit">&nbsp;</span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sFieldName)%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sShowName)%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sFieldType)%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(isInOutline?"是":"否")%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sDBType)%></span></td>
			<td class="body_td"><span class="delete" cmd="delete">&nbsp;</span></td>
		</tr>
<%
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

	</div>

	<!-- 底部区域，主要是当需要的时候，做翻页的区域 -->
	<div class="footer">
		<div class="page-nav" id="list_navigator"></div>
		<button onclick="PageContext.pre_step()">上一步</button><button onclick="PageContext.next_step()">下一步</button><button onclick="">取&nbsp;消</button>
	</div>
</div>
</body>
</html>