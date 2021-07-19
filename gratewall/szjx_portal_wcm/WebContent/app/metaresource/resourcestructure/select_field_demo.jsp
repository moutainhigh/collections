<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 自定义布局时，选择字段 </title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<script src="../js/jquery-wcm-ui-extend.js"></script>
	<link href="../js/crashboard/crashboard.css" rel="stylesheet" type="text/css" />
	<script language="javascript">
	<!--
		function show(){
				$.CrashBoard({
					id : 'selectField-crashboard',
					maskable : true,
					draggable : true,
					url : 'select_field.jsp',
					width : '500px',
					height : '200px',
					params : {viewId: 24, pagesize: -1},
					callback : function(result){
						var sMsg = "";
						for (var i=0; i<result.length; i++) {
							sMsg += 'id:' + result[i].id + ', fieldName:' + result[i].fieldName + ', anotherName:'+ result[i].anotherName + "\n";
						}
						alert(sMsg);
					}
				}).show();
		}		
	//-->
	</script>
</head>
<body>
	<button onclick="show();">点击添加字段</button>
</body>
</html>