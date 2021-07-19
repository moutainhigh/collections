<a name='person-inv'></a>
<div class="chapter-person-inv">
	<div class='second-title'>自然人股东信息表</div>
	<table class='person-inv-table'>
		<#assign var=0>
		<#if natInvList??>
		<#list natInvList as ni>
		
		<#assign var=var+1>
						<tr>
							<th>姓&nbsp;&nbsp;&nbsp;&nbsp;名</th><td>${(ni.inv)!""}</td>
							<th>证件号码</th><td>${(ni.cerNo)!""}</td>
						</tr>
						<tr style='height: 380px'>
							<td colspan='4' >
								<#list ((ni.srcId)!"")?split(",") as src>
								<#if (((src)!"")!="")>
									<span class="directLetter-img-span"><img class='agent-direct-img' src='${(webUrl)!""}/upload/showPic.do?fileId=${((src)!"")}'/></span>
								</#if>
								</#list>
								<#if ((ni.srcId)!"")=="">
								（请在系统中上传身份证明图片，此处请勿黏贴）
								</#if>
							</td>
						</tr>
			<#if var%2==0 && ni_has_next>
		</table>
		<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
		</#if>
		<div class='split'></div>
     <div class='p'></div>
		<div class='second-title'>自然人股东信息表</div>
		<table class='person-inv-table'>
			</#if>
		</#list>
		</#if>
		</table>
		<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
		</#if>
		<div class='split'></div>
		<div class='p'></div>
</div>