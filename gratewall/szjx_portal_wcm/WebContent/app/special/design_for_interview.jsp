<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.presentation.util.ResponseHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.DreamFactory" %> 
<%@ page import="com.trs.presentation.locale.LocaleServer" %> 
<%@ page import="com.trs.service.ServiceHelper" %> 
<%@ page import="com.trs.components.special.Special" %>
<%@ page import="com.trs.components.common.publish.PublishConstants" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.Map, java.util.HashMap" %>
<%@include file="../include/public_server.jsp"%>
<%
	//1.页面状态设定
	ResponseHelper rspsHelper = new ResponseHelper(response);
	rspsHelper.initCurrentPage(request);
	//2.参数获取
	//RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	currRequestHelper.doValid();
	//很严重的安全性问题处理。。先注释掉
	/*
	//先取当前用户访问，如果不存在就用admin
	String sUserKey = String.valueOf(request.getSession().getAttribute(LoginHelper.SESSION_NAME_USER));
	int nUserKey = 0;
	try{
		nUserKey = Integer.valueOf(sUserKey).intValue();
	}catch(Exception ex){}
	User loginUser = User.findById(nUserKey);
	if(loginUser == null){
		loginUser = User.findByName("admin");
	}
	if(loginUser == null){
		loginUser	= User.getSystem();
	}
	ContextHelper.clear();
	ContextHelper.initContext(loginUser);

	request.getSession().setAttribute(LoginHelper.SESSION_NAME_USER,
                loginUser.getKey());
	*/
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
%>
<%@include file="design_init_data_build_include.jsp"%>
<%out.clear();%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><%=sTitle%></title>
	<link href="css/widgets.css" rel="stylesheet" type="text/css" />	
	<link href="css/common.css" rel="stylesheet" type="text/css" />
	<link href="design.css" rel="stylesheet" type="text/css" />	
	<link href="toolbar.css" rel="stylesheet" type="text/css" />	
	<link href="../js/source/wcmlib/simplemenu/resource/SimpleMenu.css" rel="stylesheet" type="text/css" />
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/lightbase.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/data/locale/system.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/extrender.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/elementmore.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/Observable.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/FixFF.js"></script>
	<!-- Component Start -->
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/Component.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
	<!-- Component End -->
	<script language="javascript" type="text/javascript" src="../js/source/wcmlib/simplemenu/SimpleMenu.js"></script>
	<!--Ajax Start-->
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/ajax.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/basicdatahelper.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/web2frameadapter.js"></script>
	<script language="javascript" type="text/javascript" src="toolbar.js"></script>
	<script language="javascript" type="text/javascript" src="design.js"></script>
	<script language="javascript" type="text/javascript" src="pagestyle.js"></script>
	<script language="javascript" type="text/javascript" src="../template/trsad_config.jsp"></script>
	<!--Ajax End-->
	<script language="javascript">
	<!--
		var sEmptyColumnHtml = '<%=CMyString.filterForJs(com.trs.components.common.publish.widget.ILayoutGenerator.EMPTY_COLUMN_HTML)%>';
		var nHostId = <%=nHostId%>;
		var nHostType = <%=nHostType%>;
		var nTemplateId = <%=nTemplateId%>;
		var nSpecialId = <%=nSpecialId%>;
		var sVisualTemplateContent = "<%=CMyString.filterForJs(sVisualTemplateContent)%>";
		<%String sUrlParams = "HostType="+nHostType+"&HostId="+nHostId+"&TemplateId="+nTemplateId;%>
	//-->
	</script>
	<style type="text/css">
		.s-menu .item .desc{
			width:auto;
		}
	</style>
</head>
<body>
<div class="container">
	<div class="header" id="toolbar"></div>
	<div class="main hideNavTree" id="main">
		<div class="left">
			<iframe src="tree.html" id="TreeNav" frameborder="0" style="width:100%;height:100%;"></iframe>
		</div>
		<div class="sidebar"></div>
		<div class="seperator"><div class="toggleNav" id="toggleNav"></div></div>
		<div class="content">
			<iframe src="page.html?<%=sUrlParams%>" id="page" frameborder="0" style="width:100%;height:100%;"></iframe>
		</div>
	</div>
</div>
</body>
</html>
<%!
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(com.trs.webframework.controler.JSPRequestProcessor.class);

	private Object excuteMult(JSPRequestProcessor processor, String serviceid, String methodname){
		try{
			return processor.excuteMult(serviceid, methodname);
		}catch(Exception ex){
			logger.error("Error to execute " + serviceid + "." + methodname, ex);
			//Just skip it.
		}
		return null;
	}
	private Object[] excuteArrayMult(JSPRequestProcessor processor, String serviceid, String methodname){
		try{
			return processor.excuteArrayMult(serviceid, methodname);
		}catch(Exception ex){
			logger.error("Error to execute " + serviceid + "." + methodname, ex);
			//Just skip it.
		}
		return null;
	}
%>