<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_processor.jsp"%>
<%
	MetaViews objs = (MetaViews)processor.excute("MetaDataDef", "queryViewsUsingClassInfo");
	//ClassInfos oClassInfos = (ClassInfos)processor.excute("MetaDataDef", "queryClassInfosUsingMetaView");
	out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="viewclassinfo.html.title">WCM通用列表页面-分类法</title>
    <script src="../js/runtime/myext-debug.js"></script>
    <script src="../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/data/locale/metarecdata.js"></script>
	<script src="../js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../js/source/wcmlib/core/AuthServer.js"></script>
	<script src="../../app/js/data/locale/tree.js"></script>
	<script src="../js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
	<link href="../js/source/wcmlib/com.trs.tree/resource/TreeNav.css" rel="stylesheet" type="text/css" />
	<script src="../js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
    <script src="viewclassinfo.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
	<link href="viewclassinfo.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="bodyContainer" class="layout_container">
	<div class="layout_north">
		<div class="nav_options"></div>
	</div>
	<div class="layout_center_container">
<div class="TreeView" id="box" style="height:100%;overflow:auto;">
<%
	//根据系统配置来决定是否显示
	boolean bShowClassInfos = ConfigServer.getServer().getSysConfigValue(
                "SHOWCLASSINFOS_ALLVIEW", "false").trim().equals("true");
	if(bShowClassInfos){
%>
	<div title="分类法" id="cls_0" classPre="classinfo" isRoot="true" classId="0" WCMAnt:paramattr="title:viewclassinfo.jsp.classification">
        <a href="#" name="acls_0" WCMAnt:param="viewclassinfo.jsp.classfy_function">分类法</a>
    </div>
	<ul ViewId="0"></ul>
<%
	}
%>
<%
	//5. 遍历生成表现
    for (int i = 0, length = objs.size(); i < length; i++) {
		try{
			MetaView obj = (MetaView)objs.getAt(i);
			if (obj == null)
				continue;
			int objId = obj.getId();
			String sDesc = obj.getDesc();
			String sTitle = CMyString.format(LocaleServer.getString("viewclassinfo.jsp.stitle", "名称：{0}\n创建者：{1}\n创建时间：{2}"), new String[]{sDesc,obj.getCrUserName(),obj.getCrTime()+""});
%>
    <div title="<%=CMyString.filterForHTMLValue(sTitle)%>" id="v_<%=objId%>" classPre="ViewType" ViewId="<%=objId%>">
        <a href="#" name="av_<%=objId%>"><%=CMyString.transDisplay(sDesc)%></a>
    </div>
    <ul ViewId="<%=objId%>"></ul>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	if(objs.size() == 0){
%>
		<span style="color:gray;font-size:12px;" WCMAnt:param="viewclassinfo.jsp.noView">系统没有使用分类法的视图</span>
<%
	}
%>
</div>
	</div>
</div>
</body>
</html>