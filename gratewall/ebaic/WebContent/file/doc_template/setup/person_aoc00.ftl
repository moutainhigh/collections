<!DOCTYPE html>
<head>
<title>公司章程</title>
<style>
	body{
		background-image:url("${(reqUrl)!""}/upload/showPic.do?fileId=pdfbackgroundpicture");
	}
	.title{
		text-align:center;
		font-size:30px;
		font-family:方正小标宋简体;
		margin-top:100px;
	}
	.little-title{
		text-align:center;
		font-size:20px;
		font-family:方正小标宋简体;
		margin-top:30px;
	}
	.table-bg{
		font-size:20px;
		font-family:方正书宋简体;
		margin-left:20px;
		margin-right:20px;
		margin-top:60px;
		margin-bottom:40px;
		border:1px solid #ffffff;
	}
	.table-bg td{
		border:1px solid #000000;
		height:60px;	
		text-align:center;
	}
	.left-text{
		text-align:left;
		font-size:16px;
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
	 <div class='title'> <U>&nbsp;&nbsp;${(ent.entName)!""}&nbsp;</U>章程 </div>
	 <br><br><br><br><br>
	<div class='little-title'>第一章  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;则</div>
	<p class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第一条  依据《中华人民共和国公司法》（以下简称《公司法》）及有关法律、法规的规定，由  <U>&nbsp;&nbsp;<#assign isFirst=true><#if natInvList??> <#list  natInvList as ni><#if isFirst><#assign isFirst=false>${(ni.inv)!""}</#if></#list></#if>
	<#if unnatInvList??><#list unnatInvList as unni><#if isFirst><#assign isFirst=false>${(unni.inv)!""}</#if></#list></#if>&nbsp;</U>出资设立 <U>&nbsp;&nbsp; ${(ent.entName)!""}&nbsp;</U>，（以下简称公司）特制定本章程。
	<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二条  本章程中的各项条款与法律、法规、规章不符的，以法律、法规、规章的规定为准。<br>
	</p>
	<br><br>
	<div class='little-title'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二章  公司名称和住所</div>
	<p class='left-text'> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第三条  公司名称： <U>&nbsp;&nbsp; ${(ent.entName)!""}&nbsp;</U>。<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第四条  住所： <U>&nbsp;&nbsp; ${(ent.opLoc)!""}&nbsp;</U>。<br>
	</p>
	<br><br>
	<div class='little-title'>&nbsp;&nbsp;&nbsp;第三章  公司经营范围</div>
	<p class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第五条  公司经营范围：<U>${(ent.businessScope)!""}</U>（以工商行政管理机关核定的经营范围为准。）<br>
	</p>
	<div class='paging_separator' />
	<div class='little-title'>第四章	公司注册资本及股东的姓名（名称）、出资方式、出资额</div>
	<p class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第六条  公司注册资本：  <U>&nbsp;&nbsp;${(ent.regCap)!""}&nbsp;</U> 万元人民币。<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第七条  股东的姓名（名称）、认缴的出资额、出资期限、出资方式如下：	
	</p>
	<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
		<thead>
		<tr>
			<td>股东姓名（或名称）</td><td>认缴出资数额（万元）</td><td>出资期限</td><td>出资方式</td>
		</tr>
		
		</thead>
		
		<#if stag??>
		<#list stag as s>
		<tr>
			<td>${(s.inv)!""}</td><td>${(s.curActConAm)!""}</td><td>${(s.conDate)!""}</td><td>${(s.wb)!""}</td>
		</tr>
		</#list>
		</#if>
	</table>
	<div class='paging_separator' />
	<div class='little-title'>第五章  公司的机构及其产生办法、职权、议事规则</div>
	<p class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第八条  股东行使下列职权：<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（一）决定公司的经营方针和投资计划；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（二）选举和更换非由职工代表担任的执行董事、监事，决定有关执行董事、监事的报酬事项；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（三）审议批准执行董事的报告；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（四）审议批准监事的报告；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（五）审议批准公司的年度财务预算方案、决算方案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（六）审议批准公司的利润分配方案和弥补亏损的方案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（七）对公司增加或者减少注册资本作出决议；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（八）对发行公司债券作出决议；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（九）对公司合并、分立、解散、清算或者变更公司形式作出决议；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（十）修改公司章程；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第九条  股东作出的公司经营方针和投资计划的决定，应当采用书面形式，并由股东签字后置备于公司。<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十条  公司不设董事会，设执行董事一人，由股东<U>&nbsp;&nbsp;<#assign isFirst=true><#if boardInfo??><#list boardInfo as board><#if isFirst><#assign isFirst=false>${(board.posBrForm)!""}</#if></#list></#if>&nbsp;</U>产生。执行董事任期<U>&nbsp;&nbsp;<#assign isFirst=true><#if boardInfo??><#list boardInfo as board><#if isFirst><#assign isFirst=false>${(board.offYears)!""}</#if></#list></#if> &nbsp;</U>年，任期届满，可连选连任。<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十一条  执行董事行使下列职权：<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（一）负责向股东报告工作；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（二）执行股东的决议；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（三）审定公司的经营计划和投资方案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（四）制订公司的年度财务预算方案、决算方案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（五）制订公司的利润分配方案和弥补亏损方案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（六）制订公司增加或者减少注册资本以及发行公司债券的方案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（七）制订公司合并、分立、变更公司形式、解散的方案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（八）决定公司内部管理机构的设置；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（九）决定聘任或者解聘公司经理及其报酬事项，并根据经理的提名决定聘任或者解聘公司副经理、财务负责人及其报酬事项；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（十）制定公司的基本管理制度；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十二条  公司设经理，由执行董事决定聘任或者解聘。经理对执行董事负责，行使下列职权：<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（一）主持公司的生产经营管理工作；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（二）组织实施公司年度经营计划和投资方案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（三）拟订公司内部管理机构设置方案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（四）拟订公司的基本管理制度；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（五）制定公司的具体规章；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（六）提请聘任或者解聘公司副经理、财务负责人；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（七）决定聘任或者解聘除应由股东决定聘任或者解聘以外的负责管理人员；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十三条  公司不设监事会，设监事<U>&nbsp;&nbsp; ${(susp.suspCnt)!'0'}&nbsp;</U>人，由股东<U>&nbsp;&nbsp; <#assign isFirst=true><#if suspInfo??><#list suspInfo as susp><#if isFirst><#assign isFirst=false>${(susp.posBrForm)!""}</#if></#list></#if>&nbsp;</U>产生；监事的任期每届为三年，任期届满，可连选连任。<br>
	</p>
	<div class='paging_separator' />
	<p class='left-text'>
	<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十四条  监事行使下列职权：<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（一）检查公司财务；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（二）对执行董事、高级管理人员执行公司职务的行为进行监督，对违反法律、行政法规、公司章程或者股东会决议的执行董事、高级管理人员提出罢免的建议；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（三）当执行董事、高级管理人员的行为损害公司的利益时，要求执行董事、高级管理人员予以纠正；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（四）向股东提出提案；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（五）依照《公司法》第一百五十二条的规定，对执行董事、高级管理人员提起诉讼。　<br>
	</p>
	
	<div class='little-title'>第六章  公司的法定代表人</div>
	<p class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十五条&nbsp;&nbsp;&nbsp;&nbsp;<U> ${((le.leRepJob)!"")}</U>为公司的法定代表人。
	 </p>
	<div class='little-title'>第七章  股东认为需要规定的其他事项</div>
	<p class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十六条&nbsp;&nbsp;&nbsp;&nbsp;公司的营业期限 <#if ((ent.tradeTerm)!"")=="长期">长期<#else><U>${(ent.tradeTerm)!""}</U>年</#if>，自公司营业执照签发之日起计算。<br>
	</p>
	<p class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十七条&nbsp;&nbsp;&nbsp;&nbsp;有下列情形之一的，公司清算组应当自公司清算结束之日起30日内向原公司登记机关申请注销登记：<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（一）公司被依法宣告破产；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（二）公司章程规定的营业期限届满或者公司章程规定的其他解散事由出现，但公司通过修改公司章程而存续的除外；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（三）股东决定解散；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（四）依法被吊销营业执照、责令关闭或者被撤销；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（五）人民法院依法予以解散；<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（六）法律、行政法规规定的其他解散情形。<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十八条&nbsp;&nbsp;&nbsp;&nbsp; 一人有限责任公司的股东不能证明公司财产独立于自己的财产的，应当对公司债务承担连带责任。
	</p>
	<div class='little-title'>第八章  附    则</div>
	<p class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第十九条  公司登记事项以公司登记机关核定的为准。<br>
</body>
</html>