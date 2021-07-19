<a name='dom_prove_file'></a>

<div class='chapter-upload_file'>

	<#if invSignList??>
			<#list invSignList as file>
			<div class='second-title'>股东确认暨指定（委托）书</div><br/><br/>
				<#if ((file.fileId)!"")!="">
					<img class='up-img' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((file.fileId)!"")}'/>
				</#if>
				<div class='p'></div>
				
			</#list>
	</#if>
	
</div>
