<a name='upload_file'></a>

<div class='chapter-upload_file'>

	<#if uploadList??>
			<#list uploadList as file>
				<div class='second-title'>法定代表人承诺书</div><br/><br/>
				<#if ((file.fileId)!"")!="">
					<img class='up-img' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((file.fileId)!"")}'/>
				</#if>
				
				<#if ((req.state)!"")=="8">
					<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
				</#if>
				<div class='split'></div>
				<div class='p'></div>
				
			</#list>
	</#if>
	
</div>
