<!DOCTYPE html>
<head>
<title>董事、经理、监事信息</title>
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
	<div class='title'>董事、经理、监事信息表</div>
	<br>
	<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
		<#assign var=0>
		<#if mbrList??>
		<#list mbrList as mbr>
		<#assign var=var+1>
				<tr>
					<td>姓名</td><td>${(mbr.name)!""}</td><td>职务</td><td>${(mbr.position)!""}</td>
				</tr>
				<tr>
					<td>证件类型</td><td>${(mbr.cerType)!""}</td><td>证件号码</td><td>${(mbr.cerNo)!""}</td>
				</tr>
				<tr>
					<td colspan='4' height='450'>
					<#list ((mbr.srcId)!"")?split(",") as src>
					<#if (((src)!"")!="")>
						<img height='350' width='450' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((src)!"")}'/> &nbsp;
					</#if>				
					</#list>
					</td>
				</tr>
			<#if var%2==0 && mbr_has_next>
				</table>
				<div class='paging_separator' />
				<div class='title'>董事、经理、监事信息表</div>
				<br>
				<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
			</#if>
		</#list>
		</#if>
	</table>
</body>
</html>