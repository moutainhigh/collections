<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.resource.domain.Fields" %>
<%@ page import="com.trs.components.metadata.resource.domain.Field" %>
<%out.clear();%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 字段选择 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<link href="../css/blue/list_common.css" rel="stylesheet" type="text/css" />

	<style type="text/css">
		.container .header .desc{height: 80px; line-height:80px; padding:0; padding-left: 20px;  color: #155C00; font-size: 25px; font-weight:bold;}
		.jquery-strict .content{bottom:0px;}
	</style>
	<script language="javascript">
	<!--
		var m_cbCfg = {
			btns : [
				{
					text: '确定',
					cmd : function(){
						this.callback(PageContext.getSelectValue());
					}
				},{
					text: "取消",
					cmd : function(){
					}
				}
			]
		};
		var PageContext = window.PageContext || {};
		$.extend(PageContext,{
			"selectAll" : function(){
			},
			getSelectValue : function(){
				var sReturnValues = [];
				$(".select:checked").each(function(){
					sReturnValues.push($(this).val());
				});
				return sReturnValues.join(",");
			}
		});
	//-->
	</script>
</head>

<body>
<div class="container" id="container">
	<!-- 页面头部区域，提供的是操作按钮栏，描述信息和检索框 -->
	<div class="header">
		<div class="oper_field">
			<!-- 检索框区域 -->
			<div class="querySearch" id="querySearch"></div>
		</div>
	</div>

	<!-- 数据区域 -->
	<div class="content" id="list-data">
<%@include file="../../include/public_processor.jsp"%>
<%
	//调用服务，获取字段集合
	Fields objs = (Fields) processor.excute("wcm61_Field", "query");

	//将字段集合进行分页设置
	CPager currPager = new CPager(processor.getParam("PageSize", 20));
	currPager.setCurrentPageIndex(processor.getParam("CurrPage", 1));
	currPager.setItemCount(objs.size());
%>

<table cellspacing=0 border="0" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="" width="50" class="head_td">全选</td>
		<td class="head_td">字段名称</td>
		<td class="head_td">显示名称</td>
		<td class="head_td">类型</td>
		<td class="head_td">库中类型</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		Field obj = (Field)objs.getAt(i - 1);
		if (obj == null)
			continue;
		int nFieldId = obj.getId();
		String sFieldName = obj.getFieldName();
		String sShowName = obj.getAnotherName();
		String sFieldType = obj.getFieldTypeDesc();
		String sDBType = obj.getDBTypeDesc();
%>
		<tr ObjectId="<%=nFieldId%>">
			<td onclick="" width="50" class="body_td"><input type="checkbox" class="select" name="" id="" value="<%=nFieldId%>" /> <span><%=i%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sFieldName)%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sShowName)%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sFieldType)%></span></td>
			<td class="body_td"><span><%=CMyString.transDisplay(sDBType)%></span></td>
		</tr>
<%
	}
%>
	</tbody>
<%if(objs.isEmpty()){%>
	<tbody id="grid_NoObjectFound">
		<tr><td colspan="5" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
<%}%>
</table>
	</div>
</div>
</body>
</html>
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