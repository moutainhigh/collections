<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%
	String sViewId = request.getParameter("ViewId");
	out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 批量设置资源属性 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-ui-extend.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<link href="../css/blue/list_common.css" rel="stylesheet" type="text/css" />

	<link href="../js/button/button.css" rel="stylesheet" type="text/css" />
	<link href="../js/crashboard/crashboard.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.container .header{height:30px;line-height:30px;}
		.container .header .desc{color:#563C00;font-size:16px;font-weight:bold;padding-left:30px;}
		.jquery-strict .content{top:25px;bottom:0px;}
		.container .footer{position:absolute;bottom:0px;text-align:center;width:100%;}
		.container .footer button{margin:0 5px;outline:0;background: url(../images/btn.gif) no-repeat;padding-bottom:6px; width: 84px; height: 35px; border: 0px;}
		.grid_table .grid_body TR TD:first-child{text-align:center;color:#002C99;}
	</style>
	<script language="javascript">
	<!--
		var m_cbCfg = {
			btns : [
				{
					text: '确定',
					cmd : function(){
						//this.callback(PageContext.getSelectValue());
						PageContext.save();
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
			save : function(){
				$("tr").each(function(){
					var $this = $(this);
					if(!$this.attr("ObjectId"))return;
					//准备数据
					var data = {ObjectId:$this.attr("ObjectId"),ViewId:$.getParameter("ViewId")};
					$this.find("input").each(function(){
						var name = $(this).attr("name");
						data[name] = this.checked?1:0;
					})
					$.wcmAjax("wcm61_MetaField","save",data,function(data,dataType){
						alert(data);
					});
				});
			}
		});
	//-->
	</script>
</head>

<body>
<div class="container" id="container">
	<!-- 页面头部区域，提供的是操作按钮栏，描述信息和检索框 -->
	<div class="header">
		<span class="desc">在这里，您可以批量设置字段的属性</span>
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
		<td class="head_td">显示名称</td>
		<td class="head_td">概览显示</td>
		<td class="head_td">细览显示</td>
		<td class="head_td">标题字段</td>
		<td class="head_td">检索字段</td>
		<td class="head_td">不可编辑</td>
		<td class="head_td">隐藏字段</td>
		<td class="head_td">唯一标识字段</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		MetaViewField obj = (MetaViewField)objs.getAt(i - 1);
		if (obj == null)
			continue;
		int nFieldId = obj.getId();
		String sFieldName = obj.getAnotherName();
		boolean isInDetail = obj.isInDetail();
		boolean isInOutline = obj.isInOutline();
		boolean isNotNull = obj.isNotNull();
		boolean isSearchField = obj.isSearchField();
		boolean isTitleField = obj.isTitleField();
		boolean isIdentityField = obj.isIdentityField();
		boolean isEditable = obj.isEditable();
		boolean isHidden = obj.isHidden();
%>
		<tr ObjectId="<%=nFieldId%>">
			<td class="body_td"><span><%=CMyString.transDisplay(sFieldName)%></span></td>
			<td class="body_td"><span><input type="checkbox" name="INOUTLINE" <%=isInOutline?"checked='checked'":""%> value="1"></span></td>
			<td class="body_td"><span><input type="checkbox" name="InDetail" <%=isInDetail?"checked='checked'":""%> value="1"></span></td>
			<td class="body_td"><span><input type="radio" name="TitleField" <%=isTitleField?"checked='checked'":""%> value="1"></span></td>
			<td class="body_td"><span><input type="checkbox" name="SearchField" <%=isSearchField?"checked='checked'":""%> value="1"></span></td>
			<td class="body_td"><span><input type="checkbox" name="NotEdit" <%=isEditable?"":"checked='checked'"%> value="1"></span></td>
			<td class="body_td"><span><input type="checkbox" name="HiddenField" <%=isHidden?"checked='checked'":""%> value="1"></span></td>
			<td class="body_td"><span><input type="radio" name="IdentityField" <%=isIdentityField?"checked='checked'":""%> value="1"></span></td>
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
</div>
</body>
</html>