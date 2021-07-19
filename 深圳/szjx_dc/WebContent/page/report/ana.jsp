<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
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
	function getUrlParam(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	    if (r != null) return unescape(r[2]); return null; //返回参数值
	}
	var baseUrl = "http://mqsapp01:9300/p2pd/servlet/dispatch?b_action=xts.run&m=portal/launch.xts&ui.tool=CognosViewer&ui.action=run&ui.object=/content/";
	var reportPath = getUrlParam("reportPath");
	var path = reportPath.split("@@");
	var i = 0;
	for(; i < path.length - 1; i ++){
		baseUrl += "folder[@name='" + path[i] + "']/";
	}
	baseUrl += "report[@name='" + path[i] + "']&ui=h1h2h3t4";
	document.forms["myform"].action=baseUrl;
    document.forms["myform"].submit(); 
</script>
</html>