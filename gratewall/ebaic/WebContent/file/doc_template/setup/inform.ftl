<!DOCTYPE html>
<head>
<title>企业名称预先核准通知书</title>
<style>
	body{
		background-image:url("${(reqUrl)!""}/upload/showPic.do?fileId=pdfbackgroundpicture");
	}
	.title{
		text-align:center;
		font-size:30px;
		font-family:方正小标宋简体;
		margin-top:100px;
		margin-bottom:45px;
	}
	.left-text{
		text-align:left;
		font-size:20px;
		font-family:方正书宋简体;
		margin-left:100px;
		margin-right:100px;
		margin-top:5px;
		margin-bottom:5px;
	}
	.left1-text{
		text-align:left;
		font-size:15px;
		font-family:方正书宋简体;
		margin-left:100px;
		margin-right:100px;
		margin-top:5px;
		margin-bottom:5px;
	}
	.right-text{
		text-align:right;
		font-size:20px;
		font-family:方正书宋简体;
		margin-left:100px;
		margin-right:100px;
		margin-top:30px;
		margin-bottom:30px;
	}
</style>
</head>
<body>
	<div class='right-text'>受理号：<U>&nbsp;${(hearNum.acceptNo)!""}&nbsp;</U></div>
	<div class='title'>企业名称预先核准通知书</div>
	<div class='right-text'>&nbsp;&nbsp;&nbsp;&nbsp;${(hearNum.nameAppId)!""}</div>
	<div class='left-text'>
	<U>&nbsp;${(hearNum.linkMan)!""}&nbsp;</U>：
	</div>
	<div class='left-text'>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据《企业名称登记管理规定》、《企业名称登记管理实施办法》及有关法律、行政法规规定，准予预先核准下列由<U>&nbsp;${(invCount.cInv)!""}&nbsp;</U>个投资人出资设立的企业名称为：<br>
	    <U>&nbsp;${(hearNum.entName)!""}&nbsp;</U>
		</div>
		<div class='left-text'>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投资人姓名或名称：
		<#if invName??>
		<#assign b=0>
		<U>&nbsp;
		<#list invName as n>
			<#if ((n.inv)!"")!="">
				${n.inv}&nbsp;<#if n_has_next>、</#if>
			</#if>
		</#list>
		</U>
		</#if>
		<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;以上预先核准的企业名称有效期6个月，至<U>&nbsp;${(hearNum.sYear)!""}&nbsp;</U>年<U>&nbsp;${(hearNum.sMonth)!""}&nbsp;</U>月<U>&nbsp;${(hearNum.sDate)!""}&nbsp;</U>日有效期届满自动失效。 在有效期届满前30日，申请人可向登记机关申请延长有效期，有效期延长不超过6个月。<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预先核准的企业名称不得用于经营活动，不得转让。经登记机关设立登记，颁发营业执照后企业名称正式生效。
	</div>
	<div class='right-text'>
	核准日期：&nbsp;&nbsp; <U>&nbsp;${(hearNum.tYear)!""}&nbsp;&nbsp;</U>年<U>&nbsp;&nbsp;${(hearNum.tMonth)!""}&nbsp;&nbsp;</U>月<U>&nbsp;&nbsp;${(hearNum.tDate)!""}&nbsp;&nbsp;</U>日
	</div>
	<div class='left1-text'>
		注：<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1．本通知书不作为对出资人出资资格的确认文件，申请人应当认真阅读《一次性告知单》有关投资人出资资格的规定，投资人应符合法定出资资格，不具备出资资格的应当更换出资人。<br><br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2．设立登记时，有关事项与本通知书不一致的，登记机关不得以本通知书预先核准的企业名称登记。<br><br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3．企业名称涉及法律、行政法规规定必须报经审批，未能提交审批文件的，登记机关不得以预先核准的企业名称登记注册。
	</div>
	
</body>
</html>