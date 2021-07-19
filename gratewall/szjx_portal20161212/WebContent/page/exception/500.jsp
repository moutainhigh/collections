<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ page import="java.io.*"%>
<%@ include file="/page/common/base.jsp"%>



<html>
<head>
<title></title>
<title>Error 500 !!</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>

<script type="text/javascript">
	var url = window.location.href;
	function onloadUrl(){
		document.getElementById("url").innerHTML=url;
	}
	window.onload=onloadUrl;

	
	
	function returnIndex(){
		$.ajax({
			url:"<%=request.getContextPath()%>/admin/returnIndex.do",
			data:{
			},
			type:"post",
			success:function(res){
				if(res.back=='success'){
					top.location.href =  "<%=request.getContextPath()%>" + res.url;
				} else {
					jazz.error(res.msg);
				}
			}
		});
	}
</script>
<style>
* {
	margin: 0;
	padding: 0
}

html, code {
	font: 15px/22px arial, sans-serif
}

html {
	background: #fff;
	color: #222;
	padding: 15px
}

body {
	margin: 7% auto 0;
	max-width: 390px;
	min-height: 180px;
	padding: 30px 0 15px
}

*>body {
	background: url(<%=request.getContextPath()%>/page/exception/image/robot.png)
		100% 5px no-repeat;
	padding-right: 205px
}

p {
	margin: 11px 0 22px;
	overflow: hidden
}

ins {
	color: #777;
	text-decoration: none
}

a img {
	border: 0
}

@media screen and (max-width:772px) {
	body {
		background: none;
		margin-top: 0;
		max-width: none;
		padding-right: 0
	}
}

#logo {
	background: url(<%=request.getContextPath()%>/page/exception/image/logo_sm_2.png)
		no-repeat
}

@media only screen and (min-resolution:192dpi) {
	#logo {
		background: url(image/logo_sm_2_hr.png) no-repeat 0% 0%/100% 100%;
		-moz-border-image: url(<%=request.getContextPath()%>/page/exception/image/logo_sm_2_hr.png)
			0
	}
}

@media only screen and (-webkit-min-device-pixel-ratio:2) {
	#logo {
		background: url(<%=request.getContextPath()%>/page/exception/image/logo_sm_2_hr.png)
			no-repeat;
		-webkit-background-size: 100% 100%
	}
}

#logo {
	display: inline-block;
	height: 100px;
	width: 100px
}
</style>
</head>
<body>
	<a href="#"><span id="logo" aria-label="Google"></span></a>
	<p>
		<b>500.</b>
		<ins>服务器内部错误</ins>
	</p>
	<p>
		The requested URL
		<code id="url">/dasd/asd.html</code>
		was error on this server.
	</p>
	<p>
		<ins>
			<a href="#" onclick="returnIndex()">返回首页</a>
		</ins>
	</p>
	<p>
		错误信息：<%=exception.getMessage()%>
		<br />
		<%=exception%>
	</p>


</body>
</html>