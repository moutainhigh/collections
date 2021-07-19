<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询列表</title>
<style type="text/css">

</style>
<script type="text/javascript" src="processBarPlugin.js"></script>
</head>
<freeze:body>
	<h1>进度条帮助页面：</h1>
	<p><b>使用方法</b>：<br />首先通过new processBar生成一个processBar对象。</p>
	<p><b>内置方法</b>：<br />
	1. setPecent: 设置已完成百分比<br />
	2. setInfo	: 设置提示信息</p>
	<div id="testDiv"></div>
<script type="text/javascript">
   var pBar = new processBar({
       id					: "pBar",     // 组件的默认ID，如果一个页面中有多个该组件，注意该ID不可重复
       parentContainer		: "testDiv"  // 其父节点的ID
   });
   
   // 显示百分比所对应的节点字符串。不传入为不显示
   // pBar.setPecent(5);
   // 显示提示信息的节点字符串。不传入为不显示
   // pBar.setInfo("正在生成xls文件...");
   timeoutFunc(0);
   
	function timeoutFunc(pecent){
		pBar.setPercent(10 * ++pecent);
		pBar.setInfo("正在生成第" + pecent + "个xls文件...");
		if (pecent >= 10){
			pBar.setInfo("完成.");
			return;
		}
		setTimeout(function(){
	       timeoutFunc(pecent);
	    }, 500);
	}
</script>
</freeze:body>
</freeze:html>