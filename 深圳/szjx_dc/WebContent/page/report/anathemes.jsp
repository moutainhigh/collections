<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=5">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<style type="text/css">
td {
	text-align: center;
}

.jazz-pagearea {
	height: 0px;
}
.style1 {font-family: "宋体";
	font-size: 12px;
}
.style2 {font-family: "宋体"}
.style3 {font-size: 12px}
.STYLE5 {font-size: 12px; color: #0000FF; }
.STYLE7 {font-family: "宋体"; font-size: 12px; color: #0000FF; }
</style>
</style>

</head>
<body>
<form method="post" id="myform" style="display: none"
	action="">
<input type="submit" style="display:none;" value="Submit" /></form>


</body>
<script type="text/javascript" charset="UTF-8" >   
	var baseUrl = "http://mqsapp01:9300/p2pd/servlet/dispatch?b_action=xts.run&m=portal/launch.xts&ui.gateway=%2fp2pd%2fservlet%2fdispatch&ui.tool=AnalysisStudio&ui.object=/content/folder[@name=%27cube%27]/folder[@name=%27ReportAnalysis%27]/package[@name=%27%e5%b8%82%e5%9c%ba%e4%b8%bb%e4%bd%93%e5%88%86%e6%9e%90%e6%8a%a5%e8%a1%a8%27]&ui.action=new&launch.openJSStudioInFrame=true&CAMNamespace=GDSJFX";
	//	var baseUrl = "http://mqsapp01:9300/p2pd/servlet/dispatch?b_action=xts.run&amp;m=portal/launch.xts&amp;ui.gateway=%2fp2pd%2fservlet%2fdispatch&amp;ui.tool=AnalysisStudio&amp;ui.object=/content/folder[@name=%27cube%27]/folder[@name=%27ReportAnalysis%27]/package[@name=%27%e5%b8%82%e5%9c%ba%e4%b8%bb%e4%bd%93%e5%88%86%e6%9e%90%e6%8a%a5%e8%a1%a8%27]&amp;ui.action=new&amp;launch.openJSStudioInFrame=true&amp;CAMNamespace=GDSJFX";
		//var newWim = open("anatest.jsp", "_blank");
	//"http://mqsapp01:9300/p2pd/servlet/dispatch?b_action=xts.run&m=portal/launch.xts&ui.tool=CognosViewer&ui.action=run&ui.object=/content/";
	document.forms["myform"].action=baseUrl;
    document.forms["myform"].submit(); 
</script>
</html>