<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会</title>
<%
	String rootPath = request.getContextPath();
%>
<script src="<%=rootPath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=rootPath%>/static/js/sczt/base64.js" type="text/javascript"></script>
<script src="<%=rootPath%>/static/script/home/index.js" type="text/javascript"></script>
<style type="text/css">
html,body{
	margin:0px;
	padding:0px;
	overflow:hidden;
}

.sjzx_header{
	height:80px;
}

.sjzx_header .title{
	line-height:80px;
    font-family:simsun;
    font-weight:bold;
    font-size:12px;
    padding-right: 50px;
}

.sjzx_header .title span{
	margin: 0 5px;
	float:right;
}

.sjzx_header .title span a{
	color:black;
	text-decoration:none;
}

.sjzx_header .title span a:hover{
	color:blue;
	text-decoration:none;
}

.sjzx_footer{
	height:100px;
	line-height:100px;
	text-align:center;
}

.sjzx_content{
	position:relative;
}

.sjzx_content .serach{
	position:absolute;
	top:100px;
	left:50%;
	width:510px;
	height:200px;
	margin-left:-255px;
}

.sjzx_content .serach .img{
	height:100px;
	width:300px;
	margin-left:100px;
	background-image:url(<%=rootPath%>/static/images/icons/search.png)
}

.sjzx_content .serach .content{
	border:1px solid rgb(166,166,166);
	height:45px;
	width:500px;
}
.sjzx_content .serach .content input{
	height:43px;
	width:390px;
	line-height:43px;
	border:none;
}

.sjzx_content .serach .content div{
	 height:100%;
	 width:100px;
	 background-color:blue;
	 float:right;
	 line-height:43px;
     text-align:center;
     color:#ffffff;
     cursor:pointer;
     font-size:14px;
}
</style>
</head>
<body>
	<div class="sjzx_header">
		<div class="title"></div>
	</div>
	<div class="sjzx_content">
		<div class="serach">
			<div class="img"></div>
			<div class="content"><div onclick="search();">搜 一 下</div><input id="search_txt" type="text"></div>
		</div>
	</div>
	<div class="sjzx_footer">
		版权所有：深圳市市场和质量监督管理委员会              主办单位：深圳市市场和质量监督管理委员会
	</div>
</body>
</html>