<!DOCTYPE html>
<head>
<title>非自然人股东信息表</title>
<style>
	body{
		background-image:url("${(reqUrl)!""}/upload/showPic.do?fileId=pdfbackgroundpicture");
	}
	.title{
		text-align:center;
		font-size:30px;
		font-family:方正小标宋简体;
		margin-top:100px;
	}
	.table-bg{
		font-size:20px;
		font-family:方正书宋简体;
		margin-left:20px;
		margin-right:20px;
		margin-top:60px;
		margin-bottom:40px;
		border:1px solid #ffffff;
	}
	.table-bg td{
		border:1px solid #000000;
		height:60px;	
		text-align:center;
	}
</style>
</head>
<body>
	<div class='title'>非自然人股东信息表</div>
	<br>
	<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
		<#if unnatInvList??>
		<#list unnatInvList as unnatInv>
		<tr>
			<td>名称</td><td>${(unnatInv.inv)!""}</td><td>证照号码</td><td>${(unnatInv.bLicNo)!""}</td>
		</tr>
		<tr>
			<td colspan='4' height='1000'>
			<#if (((unnatInv.srcId)!"")=="")>
			   <div>(非自然人股东资格证明图片)</div>
			</#if>
			<#if (((unnatInv.srcId)!"")!="")>
				<img height='800' width='600' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((unnatInv.srcId)!"")}'/> 
			</#if>
			</td>
		</tr>
		<#if unnatInv_has_next>
		</table>
		<div class='paging_separator' />
		<div class='title'>非自然人股东信息表</div>
		<br>
		<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
		</#if>
		</#list>
		</#if>
	</table>
</body>
</html>