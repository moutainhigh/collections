<a name='cp-inv'></a>
<div class="chapter-cp-inv">
<#if unnatInvList??>
<#list unnatInvList as unnatInv>
		<div class='second-title'>非自然人股东信息表</div>
		<table class='cp-inv-table'>
			<tr>
				<th>名&nbsp;&nbsp;&nbsp;&nbsp;称</th><td>${(unnatInv.inv)!""}</td>
				<th>证照号码</th><td>${(unnatInv.bLicNo)!""}</td>
			</tr>
			<tr style='height: 800px'>
				<td colspan='4' >
					<#if (((unnatInv.srcId)!"")=="")>
					  （请在系统中上传股东资格证明图片，此处请勿黏贴）
					</#if>
					<#if (((unnatInv.srcId)!"")!="")>
						<img class='cp-cer-img' 
							src='http://160.100.0.92:7001/upload/showPic.do?fileId=${((unnatInv.srcId)!"")}'/> 
					</#if>
				</td>
			</tr>
		</table>
		<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
		</#if>
		<div class='split'></div>
		
	<div class='p'></div>	
</#list>
</#if>
</div>