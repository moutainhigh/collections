<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.resource.domain.Fields" %>
<%@ page import="com.trs.components.metadata.resource.domain.Field" %>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 修改字段库字段时，同步进行修改资源结构字段 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<script src="../js/jquery-wcm-ui-extend.js"></script>
	<link href="../js/crashboard/crashboard.css" rel="stylesheet" type="text/css" />
	<script language="javascript">
	<!--
		function show(){
			$.CrashBoard({
				id : 'edit-crashboard',
				maskable : true,
				draggable : true,
				url : 'libfield_edit.jsp',
				width : '500px',
				height : '200px',
				params : {ObjectId: 108, pagesize: -1},
				callback : function(results){
					var strMsg = "";
					for(var i=0; i< results.length; i++){
						strMsg += "viewInfoId =" + results[i].viewInfoId + ", viewName =" + results[i].viewName + ", viewDesc =" + results[i].viewDesc +". \n";
					}
					alert(strMsg);
				}
			}).show();
		}
	//-->
	</script>
</head>
<body>
	<button onclick="show();">自定义选择</button>
</body>
</html>