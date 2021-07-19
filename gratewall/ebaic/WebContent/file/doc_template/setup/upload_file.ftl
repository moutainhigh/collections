<!DOCTYPE html>
<head>
<title>用户上传的签字盖章页</title>
<style>
	body{
		background-image:url("${(reqUrl)!""}/upload/showPic.do?fileId=pdfbackgroundpicture");
	}
	.div-content{
		text-align:center;
		margin-top:50px;
	}
</style>
</head>
<body>
	<#if uploadList??>
	<div class='div-content'>
	<#list uploadList as file>
			<#if ((file.fileId)!"")!="">
			<img height='1300' width='1000' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((file.fileId)!"")}'/>
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