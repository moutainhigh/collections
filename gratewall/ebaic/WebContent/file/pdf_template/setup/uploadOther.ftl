<a name='upload_other_file'></a>

<div class='chapter-upload_file'>

	<#if uploadOtherList??>
			<#list uploadOtherList as file>
				<div class='second-title'>其他</div><br/><br/>
				<#if ((file.fileId)!"")!="">
					<img class='up-img' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((file.fileId)!"")}'/>
				</#if>
				<div class='p'></div>
			</#list>
	</#if>
	
</div>
