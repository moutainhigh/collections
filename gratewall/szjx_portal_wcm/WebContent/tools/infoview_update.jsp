<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;
%>
<HTML>
<HEAD>
<TITLE>表单升级工具</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
function doSuccess(_oIframe){
	var nStep = _oIframe.step;
	if(nStep){
		var spanResult = document.getElementById('output_'+nStep);
		spanResult.innerHTML = '表单升级操作第'+(nStep==1?'一':'二')+'步成功';
	}
}
</script>
</HEAD>

<BODY>
<table border="0" cellspacing="12" cellpadding="0" style="font-family: 'Courier New'">
<tbody>
	<tr>
		<td style="color: red; font-weight: bold">表单升级操作第一步</td>
	</tr>
	<tr>
		<td>
		<ul>
			<li>此操作将对WCM系统中所有表单同步创建元数据表及其他相关的处理
			<li>一般情况下执行比较快，执行完成后将输出“表单升级操作第一步成功”字样
			<li>也可以监控后台日志输出，执行完成将输出“表单升级操作第一步成功”字样。
			<li>* 1.为系统所有视图创建元数据表结构
			<li>* 2.替换视图的检索字段，概览字段，自动生成相应的DB字段
			<li>* 3.替换栏目的检索字段，自动生成相应的DB字段
			<li>* 4.同步文档的DocFlag到ChnlDoc表中
		</ul>
		</td>
	</tr>
	<tr>
		<td>
			<form method=get action="../infoview.do" target="target" style="margin:0;" onsubmit="document.getElementById('target').step=1;">
				<input type="hidden" name="serviceid" value="wcm6_infoviewdatatool">
				<input type="hidden" name="methodname" value="adapterHistory">
				<table border="0" cellspacing="5" cellpadding="8" style="border:1px solid gray;width:600px">
				<tbody>
					<tr>
						<td align="center"><input type="submit" value="执行第一步"></td>
					</tr>
					<tr>
						<td>
							<div>执行结果：<span style="color: green; font-weight: bold" id="output_1"></span></div>
						</td>
					</tr>
				</tbody>
				</table>
			</form>
		</td>
	</tr>
</tbody>
</table>

<table border="0" cellspacing="12" cellpadding="0" style="font-family: 'Courier New'">
<tbody>
	<tr>
		<td style="color: red; font-weight: bold">表单升级操作第二步</td>
	</tr>
	<tr>
		<td>
		<ul>
			<li>此操作将对WCM系统中指定条件的所有表单文档进行同步创建元数据操作
			<li>在做此操作前请确认“操作一”已经成功执行
			<li>在表单文档条数较多时(如2000条以上)该操作将持续较长时间，请耐心等待
			<li>可以监控后台日志输出，执行完成将输出“表单升级操作第二步成功”字样。
		</ul>
		</td>
	</tr>
	<tr>
		<td>
			<form method=get action="../infoview.do" target="target" style="margin:0;" onsubmit="document.getElementById('target').step=2;">
				<input type="hidden" name="serviceid" value="wcm6_infoviewdatatool">
				<input type="hidden" name="methodname" value="autoCreateHistoryDocs">
				<table border="0" cellspacing="5" cellpadding="8" style="border:1px solid gray;width:600px">
				<tbody>
					<tr>
						<td>请填入ChannelIds： &nbsp;&nbsp;(不填表示全部栏目)</td>
						<td><input type="text" name="ChannelIds" value=""></td>
					</tr>
					<tr>
						<td>请填入InfoViewId： &nbsp;&nbsp;(不填表示全部栏目)</td>
						<td><input type="text" name="InfoViewId" value=""></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="submit" value="执行第二步"></td>
					</tr>
					<tr>
						<td colspan="2">
							<div>执行结果：<span style="color: green; font-weight: bold" id="output_2"></span></div>
						</td>
					</tr>
				</tbody>
				</table>
			</form>
		</td>
	</tr>
</tbody>
</table>
<div>
</div>
<iframe src="about:blank" id="target" name="target" style="display:none" onload="doSuccess(this);"></iframe>
</BODY>
</HTML>