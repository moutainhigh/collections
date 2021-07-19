<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../../include/error_for_dialog.jsp"%>
<%@ page import = "com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import = "com.trs.components.metadata.definition.MetaView"%>
<%@ page import = "com.trs.DreamFactory"%>
<%@ page import = "com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "com.trs.components.wcm.util.Categorys"%>
<%@ page import = "com.trs.components.wcm.util.Category"%>
<%@include file="../../include/public_processor.jsp"%>
<%
	//调用服务，获取字段集合
	HashMap parameters = new HashMap();
	parameters.put("ParentId", "0");
	parameters.put("HostType", String.valueOf(MetaView.OBJ_TYPE));

	Categorys objs = (Categorys) processor.excute("wcm61_category", "query", parameters);
	Category root = objs != null && objs.size() > 0 ? (Category) objs.getAt(0) : null;
%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>资源结构分类树</title>
	<script src="../../../app/js/runtime/myext-debug.js"></script>
	<script src="../../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../../app/js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../../../app/js/data/locale/ajax.js"></script>
	<script src="../../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<script src="../../../app/js/data/locale/tree.js"></script>
	<script src="../../../app/js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
	<link rel="stylesheet" type="text/css" href="../../../app/js/source/wcmlib/com.trs.tree/resource/TreeNav.css">
	<script language="javascript">
	<!--
		com.trs.tree.TreeNav.methodType = 'get';
		com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(divElement){
			var urlPrefix = '/'+(location.pathname.split('/')[1]||'wcm')+'/center.do?serviceid=wcm61_category&methodname=createCategoryTreeHTML'; 
			var params = "&parentId=" + divElement.getAttribute("objectId");
			return urlPrefix + params;
		}		
	//-->
	</script>
</head>

<body>
<%if(root != null){%>
<div class="TreeView" id="TreeView">
	<div objectId="<%=root.getId()%>" title="ID:<%=root.getId()%>"><a href="#"><%=CMyString.transDisplay(root.getCName())%></a></div><UL></UL>
</div>
<%}else{%>
<div>尚未定义资源结构的分类</div>
<%}%>
</body>
</html>