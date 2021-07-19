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
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 删除字段库字段时，同步进行删除资源结构字段 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<link href="libfield_delete.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="libfield_delete">
		<div class="top">
			<p>总共有<%= objs.size()%>个资源结构有此字段，本次删除不关联到资源结构的字段！</p>
			<p class="warn">确定要删除字段库中的所选字段吗？</p>
		</div>
		<div class="content">
			<p>资源结构列表如下：</p>
			<ul>
				<%
					for (int i = currPager.getFirstItemIndex(),temp = 0; i <= currPager.getLastItemIndex(); i++) {
							MetaView obj = (MetaView)objs.getAt(i - 1);
							
							if (obj == null)
								continue;
							String sMetaViewName = obj.getName();
				%>
				<li class="<%=temp%3==0 ?"first-li":""%> <%=temp%3==2?"last-li":""%>">
					<label for="<%=sMetaViewName%>" title="<%=sMetaViewName%>"><%=CMyString.transDisplay(sMetaViewName)%></label>
				</li>
				<% temp++;} %>
			</ul>
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
					this.callback();
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