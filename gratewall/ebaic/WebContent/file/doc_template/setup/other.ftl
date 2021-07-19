<!DOCTYPE html>
<head>
<title>证明材料</title>
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
	.div-content{
		text-align:center;
		margin-top:200px;
	}
</style>
</head>
<body>
	<div class='title'>住所相关证明文件</div>
	<#if fileList??>
	<div style='text-align:center;margin-top:60px'>
	<#list fileList as file>
			<#if ((file.fileId)!"")!="">
			<img height='1100' width='800' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((file.fileId)!"")}'/>
			</#if>
		<#if file_has_next>
		</div>
		<div class='paging_separator' />
		<div class='div-content'>
		</#if>
	</#list>
	</#if>
	</div>
</body>
</html>