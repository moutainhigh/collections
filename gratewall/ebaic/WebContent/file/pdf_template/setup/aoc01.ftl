<a name='aoc01'></a>
<div class='aoc-notes'>
	本章程内容由股东在《股东确认暨指定（委托）书》中予以确认。
	<hr/>
</div>
<div class='chapter-aoc'>
	<div class='second-title'><span class='u'>${(ent.entName)!""}</span>章程 </div>
	<div class='aoc-subtitle' >第一章    &nbsp;总     &nbsp;则&nbsp;</div>
	<div class='aoc-text'>
	 第一条  依据《中华人民共和国公司法》（以下简称《公司法》）及有关法律、法规的规定，由
	  <span class='u'>
	  	<#assign isFirst=true><#if natInvList??>
	  	 <#list  natInvList as ni>
	  	 	<#if isFirst><#assign isFirst=false><#else>、</#if>${(ni.inv)!""}</#list></#if>
	    	 <#if unnatInvList??>
	    <#list unnatInvList as unni>
	    	<#if isFirst><#assign isFirst=false><#else>、</#if>${(unni.inv)!""}</#list></#if>
	    </span>等
	     <span class='u'>&nbsp;${((natInvList?size)!0)+((unnatInvList?size)!0)}&nbsp;</span>方共同出资，设立<span class='u'> ${(ent.entName)!""}&nbsp;</span>，（以下简称公司）特制定本章程。
	</div>
	<div class='aoc-text'>
		第二条  本章程中的各项条款与法律、法规、规章不符的，以法律、法规、规章的规定为准。
	</div>
	
	
	<div class='aoc-subtitle'>&nbsp;&nbsp;&nbsp;&nbsp;第二章  公司名称和住所</div>
	<div class='aoc-text'>
                          第三条  公司名称：<span class='u'> ${(ent.entName)!""}&nbsp;</span>。
			
	</div>
	<div class='aoc-text'> 
		 第四条  住所： <span class='u'>${(ent.opLoc)!""}&nbsp;</span>。
	</div>
	
	<div class='aoc-subtitle'>&nbsp;&nbsp;&nbsp;&nbsp;第三章  公司经营范围</div>
	
	<div class='aoc-text'> 
	 第五条  公司经营范围：<span class='u'>${(ent.businessScope)!""}</span>（以工商行政管理机关核定的经营范围为准。）<br/>
	</div>
	
	<div class='aoc-subtitle'>第四章	公司注册资本及股东的姓名（名称）、<br/>出资方式、出资额、出资时间</div>
	<div class='aoc-text'>
	 第六条  公司注册资本：<span class='u'>${(ent.regCap)!""}&nbsp;</span>万元人民币。
	</div>	
	<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
	</#if>
	<div class='split'></div>
	   
	<div class='aoc-text'>
		第七条  股东的姓名（名称）、认缴的出资额、出资期限、出资方式如下：
		</div>
	<table class='aoc-table'>
		<tr>
			<td rowspan="2">股东姓名或名称</td><td colspan="3">认缴情况</td>
		</tr>
		<tr>
			<td>认缴出资数额（万元）</td><td>出资期限</td><td>出资方式</td>
		</tr>
		
		<#if stag??>
		<#assign var=0>
		<#assign sum=0>
		<#list stag as s>
		<#assign sum=sum+s.curActConAm>
		<#assign var=var+1>
		<tr>
			<td>${(s.inv)!""}</td><td>${(s.curActConAm)!""}</td><td>${(s.conDate)!""}</td><td>${(s.wb)!""}</td>
		</tr>
		<#if var==4 && s_has_next>
		</table>
				<br/><br/><br/>
				<table class='aoc-table'>
					<tr>
						<td rowspan="2">股东姓名或名称</td><td colspan="3">认缴情况</td>
					</tr>
					<tr>
						<td>认缴出资数额<br /></td><td>出资期限</td><td>出资方式</td>
					</tr>
		</#if>
		</#list>
		</#if>
		<tr>
			<td>合计</td><td>${(sum)!"0"}</td><td></td><td></td>
		</tr>
	</table>	
	<br/><br/>
	<div class='aoc-notes1'>
	本章程内容由股东在《股东确认暨指定（委托）书》中予以确认。
	<hr/>
	</div>
	<div class='aoc-subtitle'>第五章  公司的机构及其产生办法、职权、议事规则</div>
	<div class='aoc-text'>
		第八条  股东会由全体股东组成，是公司的权力机构，行使下列职权：</div><div class='aoc-text'>
		（一）决定公司的经营方针和投资计划；</div><div class='aoc-text'>
		 （二）选举和更换非由职工代表担任的执行董事、监事，决定有关执行董事、监事的报酬事项；</div><div class='aoc-text'>
		 （三）审议批准执行董事的报告；</div><div class='aoc-text'>
		 （四）审议批准监事会的报告；</div><div class='aoc-text'>
		（五）审议批准公司的年度财务预算方案、决算方案；</div><div class='aoc-text'>
		 （六）审议批准公司的利润分配方案和弥补亏损的方案；</div><div class='aoc-text'>
		 （七）对公司增加或者减少注册资本作出决议；</div><div class='aoc-text'>
		（八）对发行公司债券作出决议；</div>
		<div class='aoc-text'>
		 （九）对公司合并、分立、解散、清算或者变更公司形式作出决议；</div><div class='aoc-text'>
		 （十）修改公司章程。
		 </div>
	<div class='aoc-text'>	 
	 第九条  股东会的首次会议由出资最多的股东召集和主持。</div><div class='aoc-text'>
	 第十条  股东会会议由股东按照出资比例行使表决权。</div><div class='aoc-text'>
	 第十一条  股东会会议分为定期会议和临时会议。</div><div class='aoc-text'>
	 召开股东会会议，应当于会议召开十五日以前通知全体股东。</div><div class='aoc-text'>
	 定期会议按每年定时召开。代表十分之一以上表决权的股东，执行董事，监事提议召开临时会议的，应当召开临时会议。</div>
	 
	 <div class='aoc-text'>
	 第十二条  股东会会议由执行董事召集和主持。</div>
	 <#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
	</#if>
	<div class='split'></div>
	   
	 <div class='aoc-text'>
	 执行董事不能履行或者不履行召集股东会会议职责的，由监事召集和主持；监事不召集和主持<br/>的，代表十分之一以上表决权的股东可以自行召集和主持。</div>
	
	 <div class='aoc-text'>
	 第十三条  股东会会议作出修改公司章程、增加或者减少注册资本的决议，以及公司合并、分<br/>立、解散或者变更公司形式的决议，必须经代表三分之二以上表决权的股东通过。</div><div class='aoc-text'>
	 第十四条  公司不设董事会，设执行董事一人，由股东会选举产生。执行董事任期<span class='u'>&nbsp;3 &nbsp;</span>年，任期届满，可连选连任。</div><div class='aoc-text'>
	 第十五条  执行董事行使下列职权：</div><div class='aoc-text'>
	  （一）负责召集股东会，并向股东会议报告工作； </div><div class='aoc-text'>
	  （二）执行股东会的决议； </div><div class='aoc-text'>
	  （三）审定公司的经营计划和投资方案； </div><div class='aoc-text'>
	 （四）制订公司的年度财务预算方案、决算方案； </div><div class='aoc-text'>
	  （五）制订公司的利润分配方案和弥补亏损方案； </div><div class='aoc-text'>
	  （六）制订公司增加或者减少注册资本以及发行公司债券的方案； </div><div class='aoc-text'>
	 （七）制订公司合并、分立、变更公司形式、解散的方案； </div>
	 <br/>
	  <div class='aoc-notes1'>
		本章程内容由股东在《股东确认暨指定（委托）书》中予以确认。
		<hr/>
	  </div>
	 <div class='aoc-text'>
	 （八）决定公司内部管理机构的设置； </div>
	
	 <div class='aoc-text'>
	  （九）决定聘任或者解聘公司经理及其报酬事项，并根据经理的提名决定聘任或者解聘公司副经理、财务负责人及其报酬事项； </div><div class='aoc-text'>
	  （十）制定公司的基本管理制度； </div><div class='aoc-text'>
	 第十六条  公司设经理，由执行董事决定聘任或者解聘。经理对执行董事负责，行使下列职权：</div><div class='aoc-text'>
	（一）主持公司的生产经营管理工作，组织实施股东会决议；</div><div class='aoc-text'>
	 （二）组织实施公司年度经营计划和投资方案；</div><div class='aoc-text'>
	 （三）拟订公司内部管理机构设置方案；</div><div class='aoc-text'>
	（四）拟订公司的基本管理制度；</div><div class='aoc-text'>
	 （五）制定公司的具体规章；</div><div class='aoc-text'>
	 （六）提请聘任或者解聘公司副经理、财务负责人；</div><div class='aoc-text'>
	 （七）决定聘任或者解聘除应由股东会决定聘任或者解聘以外的负责管理人员；</div><div class='aoc-text'>
	（八）股东会授予的其他职权。</div><div class='aoc-text'>
	<#assign bsc=0>
	<#assign sc=0>
	<#if suspInfo??>
	<#list suspInfo as s>
		<#if (s.supsType)??&&(s.supsType=="1")>
			<#assign bsc=bsc+1>
		</#if>
		<#if (s.supsType)??&&(s.supsType=="2")>
			<#assign sc=sc+1>
		</#if>
	</#list>
	</#if>
	 第十七条  公司设监事会，成员   <span class='u'>&nbsp;${(susp.suspCnt)!'0'}&nbsp;</span>人，由股东选举产生。监事会设主席一人，由全体监事过半数选举产生。监事会中股东代表监事与职工代表监事的比例为  <span class='u'>&nbsp;${(bsc)!"0"}&nbsp;</span> ： <span class='u'>&nbsp;${(sc)!"0"}&nbsp;</span>。
	监事的任期每届为三年，任期届满，可连选连任。
	</div>
	<div class='aoc-text'>
	 第十八条  监事会行使下列职权：</div><div class='aoc-text'>
	  （一）检查公司财务；</div><div class='aoc-text'>
	  （二）对执行董事、高级管理人员执行公司职务的行为进行监督，对违反法律、行政法规、公司章程或者股东决定的执行董事、高级管理人员提出罢免的建议；</div>
	 
	  <div class='aoc-text'>
	 
	 （三）当执行董事、高级管理人员的行为损害公司的利益时，要求执行董事、高级管理人员予以纠正；</div>
	 <#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
	</#if>
	<div class='split'></div>
	    
	 <div class='aoc-text'>
	 （四）向股东议提出提案；</div>
	
	 <div class='aoc-text'>
	  （五）依照《公司法》第一百五十二条的规定，对执行董事、高级管理人员提起诉讼；</div><div class='aoc-text'>
	 第十九条 监事会每年度至少召开一次会议，监事可以提议召开临时监事会会议。</div><div class='aoc-text'>
	 第二十条 监事会决议应当经半数以上监事通过。</div>
	
	<div class='aoc-subtitle'>第六章  公司的法定代表人</div>
	
	<div class='aoc-text'>
		 第二十一条 <span class='u'> &nbsp;${((le.leRepJob)!"")}&nbsp;</span>为公司的法定代表人。 
	</div>
	
	<div class='aoc-subtitle'>第七章  股东会会议认为需要规定的其他事项</div>
	
	<div class='aoc-text'>
	 第二十二条  股东之间可以相互转让其部分或全部出资。</div>
	 <br/><br/><br/><br/>
	 <div class='aoc-notes1'>
	 	本章程内容由股东在《股东确认暨指定（委托）书》中予以确认。
		 <hr/>
    </div>
	 <div class='aoc-text'>
	 第二十三条  股东向股东以外的人转让股权，应当经其他股东过半数同意。股东应就其股权转让事项书面通知其他股东征求同意，其他股东自接到书面通知之日起满三十日未答复的，视为同意转<br/>让。其他股东半数以上不同意转让的，不同意的股东应当购买该转让的股权；不购买的，视为同意转<br/>让。</div><div class='aoc-text'>
	 经股东同意转让的股权，在同等条件下，其他股东有优先购买权。两个以上股东主张行使优先购买权的，协商确定各自的购买比例；协商不成的，按照转让时各自的出资比例行使优先购买权。</div><div class='aoc-text'>
	 第二十四条  公司的营业期限<#if ((ent.tradeTerm)!"")=="长期"><span class='u'>&nbsp;长期&nbsp;</span><#else><span class='u'>&nbsp;${(ent.tradeTerm)!""}&nbsp;</span>年</#if>，自公司营业执照签发之日起计算。</div><div class='aoc-text'>
	 第二十五条  有下列情形之一的，公司清算组应当自公司清算结束之日起30日内向原公司登记机关申请注销登记：</div><div class='aoc-text'>
	 （一）公司被依法宣告破产；</div><div class='aoc-text'>
	 （二）公司章程规定的营业期限届满或者公司章程规定的其他解散事由出现，但公司通过修改公司章程而存续的除外；</div><div class='aoc-text'>
	 （三）股东会决议解散或者一人有限责任公司的股东决议解散；</div><div class='aoc-text'>
	 （四）依法被吊销营业执照、责令关闭或者被撤销；</div><div class='aoc-text'>
	 （五）人民法院依法予以解散；</div><div class='aoc-text'>
	 （六）法律、行政法规规定的其他解散情形。</div>
	
	
	<div class='aoc-subtitle'>第八章  附    则</div>
	<div class='aoc-text'>
	 第二十六条  公司登记事项以公司登记机关核定的为准。</div><div class='aoc-text'>
	</div>
	<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
	</#if>
	<div class='split'></div>
	   <div class='p'></div>
</div>