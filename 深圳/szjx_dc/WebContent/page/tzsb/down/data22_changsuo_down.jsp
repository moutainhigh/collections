<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
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
body {
	font-family: "微软雅黑";
	font-size: 14px;
}

table {
	width: 96%;
	margin: 0 auto;
}

table h1 {
	font-size: 18px;
}

td {
	padding: 0 8px;
	text-align: center;
}

tr:hover{
	background: #ccc;
}
</style>
<script type="text/javascript">
	<!-- <table id='tablee'></table> -->
	<!-- $(function(){ -->
	<!--  var _html  = $.post(); -->
	<!-- }) -->
	function downReport(begintime,endtime,regcode){
		var url= rootPath+"/tzsb/downExcelSheBeiQuYu.do?begintime="+begintime+"&endtime="+endtime+"&regcode="+regcode + "&id=" + Math.random();
		location=url;
	}
</script>
</head>
<body id='huawu_num'>

</body>
</html>