<a name='agent'></a>
<div class='chapter-agent'>
	<div class='second-title'>代理机构营业执照</div><br/><br/>
	<#if agentYyzz??>
			<#if ((agentYyzz.fileId)!"")!="">
					<img height='700' width='600' src='${(reqUrl)!""}/upload/showPic.do?fileId=${((agentYyzz.fileId)!"")}'/>
			</#if>
	</#if>
	<div class='p'></div>
</div>
