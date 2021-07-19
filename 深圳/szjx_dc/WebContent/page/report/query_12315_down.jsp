<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<style type="text/css">
body{
	font: 15px/21px Arial, Helvetica, simsun, sans-serif;
}
a{font-size: 18px;}
table{border:solid #000; border-width:1px 0px 0px 1px;border-collapse:collapse;border-spacing:0}
td{border:solid #000; border-width:0px 1px 1px 0px; padding:10px 0px;white-space: nowrap;text-align: center;}
</style>
<script type="text/javascript">
	function downReport(begintime, endtime) {
		var url = rootPath + "/quert12315Controller/downExcel.do?begintime="
				+ begintime + "&endtime=" + endtime;
		location = url;
	}
</script>

</head>
<body>

</body>
</html>