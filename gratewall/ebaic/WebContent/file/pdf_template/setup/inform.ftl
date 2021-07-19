<a name='inform'></a>
<div class='chapter-inform'>
	<div class='right-text'>受理号：<span class='u'>&nbsp;${(hearNum.acceptNo)!""}&nbsp;</span></div>
	<div class='second-title'>企业名称预先核准通知书</div>
	<div class='right-text'>&nbsp;&nbsp;&nbsp;&nbsp;${(hearNum.nameAppId)!""}</div>
	<div class='left-text' style='text-indent:0em;'>
	<span class='u'>${(hearNum.linkMan)!""}</span>：
	</div>
	<div class='left-text'>
		根据《企业名称登记管理规定》、《企业名称登记管理实施办法》及有关法<br/>律、行政法规规定，准予预先核准下列由<span class='u'>&nbsp;${(invCount.cInv)!""}&nbsp;</span>个投资人出资设立的企业名称为：<br/>
    <span class='u'>&nbsp;${(hearNum.entName)!""}&nbsp;</span>
	</div>
	<div class='left-text'>
		投资人姓名或名称：
	<#if invName??>
	<span class='u'>
	<#list invName as n>
		<#if ((n.inv)!"")!="">
			${n.inv}<#if n_has_next>、</#if>
		</#if>
	</#list>
	</span>
	</#if>
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;以上预先核准的企业名称有效期6个月，至<span class='u'>&nbsp;${(hearNum.sYear)!""}&nbsp;</span>年<span class='u'>&nbsp;${(hearNum.sMonth)!""}&nbsp;</span>月<span class='u'>&nbsp;${(hearNum.sDate)!""}&nbsp;</span>日有效期届满自动失效。 在有效期届满前30日，申请人可向登记机关申请延长有效期，有效期延长不超过6个月。<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;预先核准的企业名称不得用于经营活动，不得转让。经登记机关设立登记，颁发营业执照后企业名称正式生效。
	</div>
	<div class='right-text'>
	核准日期：&nbsp; <span class='u'>&nbsp;${(hearNum.tYear)!""}&nbsp;&nbsp;</span>年<span class='u'>&nbsp;&nbsp;${(hearNum.tMonth)!""}&nbsp;&nbsp;</span>月<span class='u'>&nbsp;&nbsp;${(hearNum.tDate)!""}&nbsp;&nbsp;</span>日
	</div>
	<div class='inform-info'>
		注：<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、本通知书不作为对出资人出资资格的确认文件，申请人应当认真阅读《一次性告知单》有关投资人出资资格的规定，投资人应符合法定出资资格，不具备出资资格的应当更换出资人。<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、设立登记时，有关事项与本通知书不一致的，登记机关不得以本通知书预先核准的企业名称登记。<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、企业名称涉及法律、行政法规规定必须报经审批，未能提交审批文件的，登记机关不得以预先核准的企业名称登记注册。
	</div> 
	
	<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
		</#if>
		<div class='split'></div>
	
	<div class='p'></div>
</div>