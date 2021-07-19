<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title> </title>
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
<style id="话务员登记提交量统计报表_15891_Styles">
<!--
table {
	mso-displayed-decimal-separator: "\.";
	mso-displayed-thousand-separator: "\,";
}

.font515891 {
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 等线;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
}

.xl1515891 {
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

.xl6315891 {
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
	text-align: left;
	vertical-align: middle;
	border-top: none;
	border-right: 1.0pt solid windowtext;
	border-bottom: 1.0pt solid windowtext;
	border-left: 1.0pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl6415891 {
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
	text-align: right;
	vertical-align: middle;
	border-top: none;
	border-right: 1.0pt solid windowtext;
	border-bottom: 1.0pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl6515891 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 14.0pt;
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

.xl6615891 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 14.0pt;
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
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl6715891 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 14.0pt;
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
	border-right: 1.0pt solid windowtext;
	border-bottom: 1.0pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl6815891 {
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
	text-align: center;
	vertical-align: middle;
	border: 1.0pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl6915891 {
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

#pluginurl {
	font-size: 20px;
	font-family: inherit;
	display: inner;
	margin-top: 5px;
	padding-top: 10px;
	margin-left: 5px;
}
-->
</style>

<script type="text/javascript">
	function downReport(begintime, endtime,regcode) {
		var url = rootPath
				+ "/quert12315Controller/downExcelHuaWuTj.do?begintime="
				+ begintime + "&endtime=" + endtime+"&regcode="+regcode;
		location = url;
	}
</script>
</head>
<body id='huawu_num'>

</body>
</html>

