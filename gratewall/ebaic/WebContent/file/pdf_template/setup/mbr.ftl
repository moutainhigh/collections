<a name='mbr'></a>
<div class='chapter-mbr'>
		<div class='second-title'>董事、经理、监事信息表</div>
		<div class='mbr-notes' style='font-weight:bold'>
		股东在《股东确认暨指定（委托）书》的盖章或签字视为对下列人员职务的确认。
		</div>
		<table class='mbr-table'>
			<#assign var=0>
			<#if mbrList??>
			<#list mbrList as mbr>
			<#assign var=var+1>
					<tr class="mbr-table-info">
						<th>姓名</th><td>${(mbr.name)!""}</td><th>职务</th><td>${(mbr.position)!""}</td>
					</tr>
					<tr class="mbr-table-info">
						<th>证件类型</th><td>${(mbr.cerType)!""}</td><th>证件号码</th><td>${(mbr.cerNo)!""}</td>
					</tr>
					<tr class="mbr-table-img" >
						<td colspan='4'>
							&nbsp;
							<#list ((mbr.srcId)!"")?split(",") as src>
							<#if (((src)!"")!="")>
								<span class="directLetter-img-span"><img class='agent-direct-img' src='http://160.100.0.92:7001/upload/showPic.do?fileId=${((src)!"")}'/></span>
							</#if>				
							</#list>
							
							<#if ((mbr.srcId)!"")=="">
							（请在系统中上传身份证明图片，此处请勿黏贴）
							</#if>
						</td>
					</tr>
				<#if var%2==0 && mbr_has_next>
		</table>
		<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
		</#if>
		<div class='split'></div>
		
	<div class='p'></div>
		<div class='paging_separator' />
		<div class='second-title'>董事、经理、监事信息表</div>
		<div class='mbr-notes'>
		股东在《股东确认暨指定（委托）书》的盖章或签字视为对下列人员职务的确认。
		</div>
		<table class='mbr-table'>
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