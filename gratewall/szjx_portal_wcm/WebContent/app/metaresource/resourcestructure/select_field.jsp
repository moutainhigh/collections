<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@include file="../../include/public_processor.jsp"%>
<%
	//调用服务，获取字段集合
	MetaViewFields objs = (MetaViewFields) processor.excute("wcm61_metafield", "query");

	//将字段集合进行分页设置
	CPager currPager = new CPager(processor.getParam("PageSize", 20));
	currPager.setCurrentPageIndex(processor.getParam("CurrPage", 1));
	currPager.setItemCount(objs.size());
	out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 选择字段 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<script language="javascript" src="select_field.js" type="text/javascript"></script>
	<link href="select_field.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="selectField">
		<!-- 检索框区域 -->
		<div class="top">
			<form method="post" id="searchField" action="select_field.jsp?viewId=<%=processor.getParam("ViewId")%>&pagesize=-1">
				<input type="text" name="anotherName" id="anotherName" value="" />
				<img src="../images/list/cz_an10.gif" border=0 alt="点击进行检索" id="search_btn" class="search_btn"/>
			</form>
		</div>
		<div class="content">
			<ul>
				<%
					for (int i = currPager.getFirstItemIndex(),temp = 0; i <= currPager.getLastItemIndex(); i++) {
							MetaViewField obj = (MetaViewField)objs.getAt(i - 1);
							if (obj == null)
								continue;
							String sFieldName = obj.getName();
							String sShowName = obj.getAnotherName();
				%>
				<li class="<%=temp/4%2==0?"odd":"even"%> <%=temp%4==0 ?"first-li":""%> <%=temp%4==3?"last-li":""%>">
					<input type="checkbox" name="fieldName" value="" id="field-<%=obj.getId()%>" fieldId="<%=obj.getId()%>"  fieldName="<%=CMyString.filterForHTMLValue(sFieldName)%>"  anotherName="<%=CMyString.filterForHTMLValue(sShowName)%>"/>
					<label for="field-<%=obj.getId()%>" title="<%=CMyString.filterForHTMLValue(sShowName)%>"><%=CMyString.transDisplay(sShowName)%></label>
				</li>
				<%temp++;}%>
			</ul>
			<%if(objs.size() == 0){%>
				 <div class="no_object_found">不好意思, 没有找到符合条件的对象!</div>
			<%}%>
					

		</div>
	</div>
</body>
<script language="javascript">
<!--
	var m_cbCfg = {
		btns : [
			{
				text: '确  定',
				cmd : function(){
					//这里需要将选择的字段作为参数传过去
					var result = [];
					$("input[name='fieldName']").each(function(){
						var temp = $(this);
						if(temp.attr('checked')){
							result.push({id : temp.attr('fieldId'), fieldName : temp.attr('fieldName'), anotherName : temp.attr('anotherName')});
						}
					});
					this.callback(result);
				}
			}, {
				text: '取  消',
				cmd : function(){}
			}
		]
	};
//-->
</script>
</html>