<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询列表</title>
<style type="text/css">
	#select1, #select2, #select3{
		width:100px;
	}
	
	div{
		width:100px;
	}
</style>
<link href="C:\beacon\module\layout\layout-weiqiang\css\gwssi.css" rel="stylesheet" />
<script type="text/javascript" src="pageScroll.js"></script>
<script type="text/javascript" src="selectTitlePlugin.js"></script>
</head>
<freeze:body>
	<h1>下拉框帮助页面：</h1>
	<p><b>使用方法</b>：<br />绑定select对象即可</p>
	<div id="testDiv"></div>
<script type="text/javascript">
	window.onload = function(){
		setShowTitleSelectList("select1","select2","select3",false);
	}
</script>
<div>
<select id="select1" >
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
</select>
</div>
<div>
<select id="select2">
	<option value="2222222222222222222222222222222222222222222222222222222222222222222222221">2222222222222222222222222222222222222222222222222222222222222222222222221</option>
	<option value="2222222222222222222222222222222222222222222222222222222222222222222222222">2222222222222222222222222222222222222222222222222222222222222222222222222</option>
	<option value="2222222222222222222222222222222222222222222222222222222222222222222222223">2222222222222222222222222222222222222222222222222222222222222222222222223</option>
	<option value="2222222222222222222222222222222222222222222222222222222222222222222222224">2222222222222222222222222222222222222222222222222222222222222222222222224</option>
	<option value="2222222222222222222222222222222222222222222222222222222222222222222222225">2222222222222222222222222222222222222222222222222222222222222222222222225</option>
</select>
</div>
<div>
<select id="select3" >
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
</select>
</div>
<IFRAME id='optionContainerFrame' name='optionContainerFrame' frameborder='0' style='position: absolute; z-index: 9998; display: none'></IFRAME>
</freeze:body>
</freeze:html>