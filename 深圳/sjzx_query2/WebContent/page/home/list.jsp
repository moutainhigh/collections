<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会-快搜</title>
<%
	String rootPath = request.getContextPath();
%>
<style type="text/css">
html,body{
	margin:0px;
	padding:0px;
}

.content{
	margin:0 auto;
}

.search {
	height:39px;
	padding-left:10px;
	margin-top:20px;
}

.search .img{
	float:left;
	width:172px;
	height:100%;
	background-image:url(<%=rootPath%>/static/images/icons/search_nav.png);
}

.search .button{
	margin-left:10px;
	float:left;
	height:100%;
	width:100px;
	background-color:blue;
	float:left;
	line-height:39px;
	text-align:center;
	color:#ffffff;
	cursor:pointer;
	font-size:14px;
}

.search .input{
	margin-left:10px;
	float:left;
	height:33px;
	width:50%;
	float:left;
	line-height:33px;
	font-size:12px;
}

.filter{
	height:35px;
	padding-left:193px;
}

.filter span{
	margin:0px 8px;
	line-height:35px;
	cursor:pointer;
}

.pages{
	height:30px;
	line-height:30px;
	background-color:rgb(238,238,238);
	text-align:center;
	font-size:12px;
}

.content .footer{
	height:100px;
	line-height:100px;
	text-align:center;
}

.result{
	padding:0px 193px;
}

.result .list{
	margin-top:30px;
	border-bottom: 1px dashed;
}

</style>
<script type="text/javascript">
function search(){
	var url = "<%=rootPath%>/page/home/info.jsp";
	window.open(url);
}
</script>
</head>
<body>
	<div class="content">
		<div class="search">
			<div class="img"></div><input type="text" class="input"></input><div class="button">搜 一 下</div>
		</div>
		<div class="filter">
			结果筛选:<span>状态</span><span>登记机关</span><span>行业</span><span>类型</span>
		</div>
		<div class="pages">总记录数:3274&nbsp;&nbsp;&nbsp;&nbsp;消耗时间:62&nbsp;mm 当前页为第1页&nbsp;&nbsp;&nbsp;&nbsp;<a href="" style="text-decoration:none;">下一页</a></div>
		<div class="result">
			<div class="list">
				<div><a href="javascript:search();">广州欢网科技有限公司&nbsp;4000000000010&nbsp;企业族谱查看</a></div>
				<div>
					<span>成立日期:</span>2012-12-10&nbsp;&nbsp;<span>地址:</span>广州市白云区
				</div>
				<div>
					<span>企业类型:</span>2012-12-10&nbsp;&nbsp;<span>行业:</span>广州市白云区&nbsp;&nbsp;<span>登记机关:</span>广州市白云区&nbsp;&nbsp;<span>企业状态:</span>广州市白云区
				</div>
				<div>
					<span>企业法定代表人:</span>2012-12-10&nbsp;&nbsp;<span>曾用名:</span>广州市白云区&nbsp;&nbsp;<span>出资人:</span>广州市白云区
				</div>
				<div>
					<span>经营范围:</span>2012-12-10
				</div>
			</div>
			<div class="list">
				<div>广州欢网科技有限公司&nbsp;4000000000010&nbsp;企业族谱查看</div>
				<div>
					<span>成立日期:</span>2012-12-10&nbsp;&nbsp;<span>地址:</span>广州市白云区
				</div>
				<div>
					<span>企业类型:</span>2012-12-10&nbsp;&nbsp;<span>行业:</span>广州市白云区&nbsp;&nbsp;<span>登记机关:</span>广州市白云区&nbsp;&nbsp;<span>企业状态:</span>广州市白云区
				</div>
				<div>
					<span>企业法定代表人:</span>2012-12-10&nbsp;&nbsp;<span>曾用名:</span>广州市白云区&nbsp;&nbsp;<span>出资人:</span>广州市白云区
				</div>
				<div>
					<span>经营范围:</span>2012-12-10
				</div>
			</div>
			<div class="list">
				<div>广州欢网科技有限公司&nbsp;4000000000010&nbsp;企业族谱查看</div>
				<div>
					<span>成立日期:</span>2012-12-10&nbsp;&nbsp;<span>地址:</span>广州市白云区
				</div>
				<div>
					<span>企业类型:</span>2012-12-10&nbsp;&nbsp;<span>行业:</span>广州市白云区&nbsp;&nbsp;<span>登记机关:</span>广州市白云区&nbsp;&nbsp;<span>企业状态:</span>广州市白云区
				</div>
				<div>
					<span>企业法定代表人:</span>2012-12-10&nbsp;&nbsp;<span>曾用名:</span>广州市白云区&nbsp;&nbsp;<span>出资人:</span>广州市白云区
				</div>
				<div>
					<span>经营范围:</span>2012-12-10
				</div>
			</div>
		</div>
		<div class="footer">
			版权所有：广州市工商管理局              主办单位：广州市工商管理总局 经济信息中心
		</div>
	</div>
</body>
</html>