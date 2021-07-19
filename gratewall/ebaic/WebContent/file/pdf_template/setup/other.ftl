<a name='other'></a>
<div class='chapter-other'>
		<div class='second-title'>住所相关证明文件</div>
		<#if fileList??>
		<div class='other-content'>
		<#list fileList as file>
				<#if ((file.fileId)!"")!="">
				<img class='up-img' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((file.fileId)!"")}'/>
				</#if>
			<#if file_has_next>
			</div>
			<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
		</#if>
		<div class='split'></div>
			
			<div class='p'></div>	
			<div class='second-title'>住所相关证明文件</div>
			<div class='other-content'>
			</#if>
		</#list>
		</div>
		</#if>
		<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
		</#if>
		<div class='split'></div>
		
		<div class='p'></div>
</div>