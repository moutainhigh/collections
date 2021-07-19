<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title></title>
<style type="text/css">
</style>
</head>
<%
	String id = (String) request.getParameter("id");
%>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>
<script language="javascript">
	$(function() {
		 var url = "../../advQuery/getQueryById.do?id="+'<%= id%>';
		$('#queryInfoFormPannel').formpanel('option','dataurl',url);
		$('#queryInfoFormPannel').formpanel('reload');
	 }); 
</script>
<body>
	<div id="queryInfoFormPannel"  name="queryInfoFormPannel"      vtype="formpanel" 
	  	 width="100%" layoutconfig="{cols:1, columnwidth: ['100%']}" height="100%" 
	  	layout="table"  height="460" title="查询信息">
		<div name='queryInfoName' 		 vtype="textfield"  label="查询名称" 	labelalign="right" width="100%" readonly="true"></div>
		<div name='createdTime' 		 vtype="textfield"	label="创建时间" 	labelalign="right" width="100%" readonly="true"></div>
		<div name='creatorId'			 vtype="textfield" 	label="创建人"  	labelalign="right" width="100%" readonly="true"></div>
		<div name="queryInfoDescription" vtype="textfield" 	label="查询描述" 	labelalign="right" width="100%" readonly="true"></div>
		<div name="tableNameCn" 		 vtype="textfield"  label="所查询表" 	labelalign="right" width="100%" readonly="true" rowspan="2" ></div>
		<div name='columnNameCn' 		 vtype="textfield" 	label="查询字段"  labelalign="right" width="100%" readonly="true" rowspan="1" ></div>
		<div name='sql' 				 vtype="textfield" 	label="SQL语句"  labelalign="right" width="100%" readonly="true" rowspan="2" ></div>
	</div> 
	  <script type="text/javascript">
    $(function(){
   	 	$("tr:even").css({background:"#EFF6FA"});
	 	$("tr:odd").css({background:"#FBFDFD"});
	});
    </script>
</body>
</html>

