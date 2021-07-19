<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../../include/error_for_dialog.jsp"%>
<%@ page import = "com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import = "com.trs.components.metadata.definition.MetaView"%>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import = "com.trs.DreamFactory"%>
<%@ page import = "com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import = "java.util.HashMap"%>
<%@include file="../../include/public_processor.jsp"%>
<%
	//调用服务，获取字段集合
	HashMap parameters = new HashMap();
	int nClassInfoId = processor.getParam("CLASSINFOID",0);
	ClassInfo currClassInfo = ClassInfo.findById(nClassInfoId);
	if(currClassInfo==null)throw new WCMException("没有找到【ID="+nClassInfoId+"】的分类法。");
	int nRootId = currClassInfo.getRootId();

	if(nRootId==0){
		nRootId = nClassInfoId;
	}

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
	<script src="classinfo_tree.js"></script>
	<link rel="stylesheet" type="text/css" href="../../../app/js/source/wcmlib/com.trs.tree/resource/TreeNav.css">
	<link href="classinfo_tree.css" rel="stylesheet" type="text/css" />
	<script language="javascript">
	<!--
		var m_currRootId = <%=nRootId%>;
	//-->
	</script>
</head>

<body style="overflow:hidden;">
<div class="TreeView" id="TreeView">
	<div title="分类法" id="cls_0" classPre="classinfo" isRoot="true" classId="0">
        <a href="#" name="acls_0" id="acls_0">分类法</a>
    </div>
	<ul ViewId="0"></ul>
</div>
</body>
</html>