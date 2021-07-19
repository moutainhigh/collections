<a name='directLetter'></a>
<div class='chapter-directLetter'>
	<div class='second-title'>指派函</div>
			<div class='left-text'>
					<span class='u'>${(req.linkman)!""}</span>&nbsp;&nbsp;经北京经纪人协会考核、评审， 
					取得企业登记代理资格，指派其代理：<span class='u'>${(ent.entName)!""}</span>开业（设立）登记注册手续。
			</div>
			
			<div class='left-text'>	
					
					<div class='text'>
						代理事项为：&nbsp;<span><img src="${(reqUrl)!""}/static/image/checkboxpicture1.png"/>&nbsp;网上申报；</span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><img src="${(reqUrl)!""}/static/image/checkboxpicture1.png"/>&nbsp;领取营业执照和有关文书；</span>
					</div>
					<div class='text1'>
						<span><img src="${(reqUrl)!""}/static/image/checkboxpicture.png"/>&nbsp;其他事项<span class='u'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span>
					</div>
					
			</div>	
					 
			<div class='left-text'>	
					代理的权限不超过原委托人的委托。
			</div>
				
			<div class='directLetter-img'>
				<#if agentAppList??>
					<#list agentAppList as file>
						<#if ((file.fileId)!"")!="">
							<span class="directLetter-img-span"><img class='agent-direct-img' src='http://160.100.0.92:7001/upload/showPic.do?fileId=${((file.fileId)!"")}'/></span>
						</#if>
					</#list>	
				</#if>
			</div>
			
			<div class='right-text'> ${(req.agentName)!""}（盖章）：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div class='right-text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</div>
			 
</div>
<div class='p'></div>