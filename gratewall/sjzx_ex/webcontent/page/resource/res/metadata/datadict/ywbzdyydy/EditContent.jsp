<%@ page contentType="text/html; charset=GBK" %>
<head>
<title>增加业务字段信息</title>
<style type="text/css">
body
{
	font-size:12px;
	
}

.outerDiv
{
	width:300px;
	height:200px;
	display:block;
	padding-left:10px;
	padding-top:10px;
}

.contentDiv
{
	border:1px solid blue;
	width:95%;
	padding:10 5;
}

li
{
	list-style:none;
}

<script language="javascript" >
	window.onload = Ready;
	
	function Ready(){
		alert("OnReadyEvent");
	}
</script>
</style>
</head>


<body>
	<div id="div1" class="outerDiv">
		<div>数值型</div>
		<div class="contentDiv">
			<li><input type="radio" name="radio1" />无</li>
			<li><input type="radio" name="radio1" />数据取值范围：
				<input type="text" id="text1-1" size="4" />至
				<input type="text" id="text1-2" size="4" /></li>
		</div>
		<div>编辑表达式：<input id="expTxt1" type="text" size="24" /></div>
		<div>
			<input type="button" value="确定" />
			<input type="button" value="取消" />
		</div>
	</div>
	<div id="div2" class="outerDiv">
	</div>
	<div id="div3" class="outerDiv">
	</div>
	<div id="div4" class="outerDiv">
	</div>
	<div id="div5" class="outerDiv">
	</div>
</body>
