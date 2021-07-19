<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>
<%@include file="../../include/public_processor.jsp"%>
<%
	//调用服务，获取字段集合
	MetaViews objs = (MetaViews) processor.excute("wcm61_resourcestructure", "queryByField");

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
	<title> 修改字段库字段时，同步进行修改资源结构字段 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<link href="libfield_edit.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="libfield_edit">
		<div class="top">
			<p>总共有<%= objs.size()%>个资源结构，请选择需要进行同步修改的资源结构</p>
		</div>
		<div class="content">
			<ul>
				<%
					for (int i = currPager.getFirstItemIndex(),temp = 0; i <= currPager.getLastItemIndex(); i++) {
							MetaView obj = (MetaView)objs.getAt(i - 1);
							
							if (obj == null)
								continue;
							String sMetaViewName = obj.getName();
							String sMetaViewDesc = obj.getDesc();
				%>
				<li class="<%=temp%3==0 ?"first-li":""%> <%=temp%3==2?"last-li":""%>">
					<input type="checkbox" name="metaView" value="" id="metaView-<%=obj.getId()%>"  metaViewId ="<%=obj.getId()%> "  sMetaViewName="<%=CMyString.filterForHTMLValue(sMetaViewName)%>"   sMetaViewDesc="<%=CMyString.filterForHTMLValue(sMetaViewDesc)%>"/>
					<label for="metaView-<%=obj.getId()%>" title="<%=CMyString.filterForHTMLValue(sMetaViewName)%>"><%=CMyString.transDisplay(sMetaViewName)%></label>
				</li>
				<% temp++;} %>
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
					var results = [];
					$("input[name='metaView']").each(function() {
						var temp = $(this);
						if(temp.attr('checked')){
							results.push({viewInfoId: temp.attr("metaViewId"), viewName: temp.attr("sMetaViewName"), viewDesc: temp.attr("sMetaViewDesc")});
						}
						
					});
					this.callback(results);
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