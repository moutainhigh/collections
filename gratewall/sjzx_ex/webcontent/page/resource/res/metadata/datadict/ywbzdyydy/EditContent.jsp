<%@ page contentType="text/html; charset=GBK" %>
<head>
<title>����ҵ���ֶ���Ϣ</title>
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
		<div>��ֵ��</div>
		<div class="contentDiv">
			<li><input type="radio" name="radio1" />��</li>
			<li><input type="radio" name="radio1" />����ȡֵ��Χ��
				<input type="text" id="text1-1" size="4" />��
				<input type="text" id="text1-2" size="4" /></li>
		</div>
		<div>�༭���ʽ��<input id="expTxt1" type="text" size="24" /></div>
		<div>
			<input type="button" value="ȷ��" />
			<input type="button" value="ȡ��" />
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
