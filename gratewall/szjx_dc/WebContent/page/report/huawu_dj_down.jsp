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
<style id="表样1_5167_Styles">
table {
	mso-displayed-decimal-separator: "\.";
	mso-displayed-thousand-separator: "\,";
}

#pluginurl {
	font-size: 20px;
	font-family: inherit;
	display: inner;
	margin-top: 5px;
	padding-top: 10px;
	margin-left: 5px;
}

.font55167 {
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 等线;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
}

.xl155167 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 11.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 等线;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: general;
	vertical-align: bottom;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl635167 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 20.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 等线;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: 1.0pt solid windowtext;
	border-right: none;
	border-bottom: 1.0pt solid windowtext;
	border-left: 1.0pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl645167 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 20.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 等线;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: 1.0pt solid windowtext;
	border-right: none;
	border-bottom: 1.0pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl655167 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 20.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 等线;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: 1.0pt solid windowtext;
	border-right: 1.0pt solid windowtext;
	border-bottom: 1.0pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl665167 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 11.0pt;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-left: 1.0pt solid windowtext;
	border-right: 1.0pt solid windowtext;
	border-bottom: 1.0pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl675167 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 11.0pt;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: 1.0pt solid windowtext;
	border-right: 1.0pt solid windowtext;
	border-bottom: 1.0pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl685167 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 11.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: justify;
	vertical-align: middle;
	border-top: none;
	border-right: 1.0pt solid windowtext;
	border-bottom: 1.0pt solid windowtext;
	border-left: 1.0pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl695167 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 11.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: justify;
	vertical-align: middle;
	border-top: none;
	border-right: 1.0pt solid windowtext;
	border-bottom: 1.0pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

ruby {
	ruby-align: left;
}

rt {
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 等线;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-char-type: none;
}
</style>
<script type="text/javascript">
	<!-- <table id='tablee'></table> -->
	<!-- $(function(){ -->
	<!--  var _html  = $.post(); -->
	<!-- }) -->
	function downReport(begintime,endtime,regcode){
		var url= rootPath+"/quert12315Controller/downExcelHuaWuDj.do?begintime="+begintime+"&endtime="+endtime+"&regcode="+regcode + "&id=" + Math.random();
		location=url;
	}
</script>
</head>
<body id='huawu_num'>

</body>
</html>