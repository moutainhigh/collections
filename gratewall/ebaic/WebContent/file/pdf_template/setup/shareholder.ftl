<a name='sharerholder'></a>
<div class='chapter-sharerholder'>
	<div class='second-title'>股东确认暨指定（委托）书</div>
		<!-- 普通委托 -->
				<div class='left-text'>
						<span class='u'>&nbsp;${(ent.entName)!""}&nbsp;</span>全体股东现向工商行政管理机关提出公司设立申请，并对以下内容予以确认：
					</div>
					<div class='left-text'>
						1、同意委托 
						<#if req.agentName??>
							<span class='u'>${(req.agentName)!""}</span>
						<#else>
							<span class='u'>${(req.linkman)!""}（身份证号码：${(req.certNo)!""}）</span>
						</#if>
						办理<span class='u'>&nbsp;${(ent.entName)!""}&nbsp;</span>的<span class='u'>&nbsp;开业（设立）</span> 登记注册手续。
						委托事项为：
						<img src="${(reqUrl)!""}/static/image/checkboxpicture1.png"/>&nbsp;网上申报；
						<img src="${(reqUrl)!""}/static/image/checkboxpicture1.png"/>&nbsp;领取营业执照和有关文书；
						<img src="${(reqUrl)!""}/static/image/checkboxpicture.png"/>&nbsp;其他事项  
						<span class='u'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>。&nbsp;(如有其他委托事项请在□内划“√”并填写具体内容)
					</div>	 
					<div class='left-text'>	
						2、股东共同制定<span class='u'>&nbsp;${(ent.entName)!""}&nbsp;</span>章程，在本确认书中的签字盖章视同在公司章程上签名、盖章。
					</div>	 
					<div class='left-text'>	
						3、董事、经理、监事信息表中人员及其职务，全体股东予以认可。
					</div><br/>
					<div class='gudong-right-text'>
					    全体股东盖章（签字）：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    <br/>
					  （盖章或签字是对全部申请内容的确认）&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
					
					<div class="inv-interval">
					  <#assign var=0>
					  <#assign a=0>
					  <#assign b=0>
					  <#if Inv??>
					  		<ul class='sharerholder'>
						 	<#list Inv as n>
						 		<#if (((n.inv)!"")!="")&&(((n.invType)!"")!="")&&(n.invType)='20'>
								<#assign var=var+1>
								 	<li><span >${n.inv}签字：</span></li>
								 	<#if var%3==0>
								 	<#assign a=a+1>
										<br/>
										<#if a%5==0>
											<div class='p'></div>
											<div class='sharerholder-notes'>
												(盖章、签字接上页)
											</div>
										</#if>
									</#if>
								 </#if>
							</#list>
							</ul>
					 		<br/>
							<#list Inv as n>
						 		<#if (((n.inv)!"")!="")&&(((n.invType)!"")!="")&&(n.invType)!='20'>
								  	<span class='sharerholder-span'>${n.inv}盖章：</span>
								  	<#assign b=b+1>
								  		<#if (a+b)%5==0>
											<div class='p'></div>
											<div class='sharerholder-notes'>
												(盖章、签字接上页)
											</div>
										<#else>
											<br/>
										</#if>
								  	
								</#if>
							</#list>
							
					 	</#if>	
					 	</div>
					 	
					 	
					 <div class='right-text'>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月  
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
</div>
<div class='p'></div>