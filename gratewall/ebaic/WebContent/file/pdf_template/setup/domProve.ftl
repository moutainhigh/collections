<a name='dom_prove_file'></a>

<div class='chapter-upload_file'>
	<div class='second-title'>住所证明</div><br/><br/>
	<#if uploadList??>
			<#list domProveList as file>
				<#if ((file.fileId)!"")!="">
					<img class='up-img' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((file.fileId)!"")}'/>
				</#if>
				<div class='p'></div>
				
			</#list>
	</#if>
	
</div>
