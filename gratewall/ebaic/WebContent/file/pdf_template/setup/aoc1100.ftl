<a name='aoc1100'></a>
<div class='aoc-notes'>
	 本章程内容由股东在《股东确认暨指定（委托）书》中予以确认。
	 <hr/>
</div>  
<div class="chapter-aoc">
		<div class='second-title'>制定国有独资公司章程须知</div>
		<br/><br/>
		<div class='aoc-text'>
			一、为方便投资人，北京市工商行政管理局制作了国有独资公司章程参考格式。国有资产监督管理机构可以参照章程参考格式制定章程，也可以根据实际情况自行制定，但章程中必须具备本须知第二条所列事项。</div><br/><div class='aoc-text'>     
			二、根据《中华人民共和国公司法》有关规定，国有独资公司章程应当载明下列事项：     </div><br/><div class='aoc-text'>
			&nbsp;&nbsp;&nbsp;&nbsp;(一)公司名称和住所；     </div><div class='aoc-text'>
			&nbsp;&nbsp;&nbsp;&nbsp;(二)公司经营范围；     </div><div class='aoc-text'>
			&nbsp;&nbsp;&nbsp;&nbsp;(三)公司注册资本；    </div><div class='aoc-text'> 
			&nbsp;&nbsp;&nbsp;&nbsp;(四)股东名称；     </div><div class='aoc-text'>
			&nbsp;&nbsp;&nbsp;&nbsp;(五)出资方式、出资额和出资时间；     </div><div class='aoc-text'>
			&nbsp;&nbsp;&nbsp;&nbsp;(六)公司的机构及其产生办法、职权、议事规则；     </div><div class='aoc-text'>
			&nbsp;&nbsp;&nbsp;&nbsp;(七)公司法定代表人；     </div><div class='aoc-text'>
			&nbsp;&nbsp;&nbsp;&nbsp;(八)董事会会议认为需要规定的其他事项。     </div><br/><div class='aoc-text'>
			三、公司章程中应当载明“本章程与法律法规不符的，以法律法规的规定为准”。经营范围条款中应当注明“以工商行政管理机关核定的经营范围为准”。     </div><br/><div class='aoc-text'>
			四、公司章程应由国有资产监督管理机构加盖公章。     </div><br/><div class='aoc-text'>
			五、公司章程应提交原件，并应使用A4规格纸张打印。     </div><br/><div class='aoc-text'>
			
			附：《国有独资公司章程》参考格式     
		</div>
		<div class='aoc1100-title'>北京市工商行政管理局<br/>
			BEIJING ADMINISTRATION FOR INDUSTRY AND COMMERCE<br/>
			(2006年第一版)<br/>    
		</div>
		 <#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
	</#if>
	<div class='split'></div>
		<div class='p'></div>
		<div class='aoc-notes1'>
			 本章程内容由股东在《股东确认暨指定（委托）书》中予以确认。
			 <hr/>
		</div> 
		<div class='second-title'><span class='u'>${(ent.entName)!""}</span>章程</div>
		
		
		<div class='aoc-subtitle'>第一章    &nbsp;总     &nbsp;则&nbsp;</div>
		<div class='aoc-text'>
			第一条  依据《中华人民共和国公司法》（以下简称《公司法》）及有关法律、法规的规定，由 
		<span class='u'>    </span>人民政府国有资产监督管理机构单独出资设立（以下简称公司），特制定本章程。</div>     
		<div class='aoc-text'>
			第二条  本章程中的各项条款与法律、法规、规章不符的，以法律、法规、规章的规定为准。     
		</div>
		          
		<div class='aoc-subtitle'>&nbsp;&nbsp;&nbsp;&nbsp;第二章  公司名称和住所</div>
		<div class='aoc-text'>
			第三条  公司名称：<span class='u'>${(ent.entName)!""}</span>。
		</div> 
		<div class='aoc-text'>    
			第四条  住所：<span class='u'>${(ent.opLoc)!""}</span>。     
		</div>
		          
		<div class='aoc-subtitle'>&nbsp;&nbsp;&nbsp;&nbsp;第三章  公司经营范围</div>
		<div class='aoc-text'>
			第五条  公司经营范围：<span class='u'>${(ent.businessScope)!""}</span>（以工商行政管理机关核定的经营范围为准。）<br/>     
		</div>
		 <#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
	</#if>
	<div class='split'></div>
		
		<div class='aoc-subtitle'>第四章  公司注册资本</div>
		<div class='aoc-text'>
			第六条  公司注册资本： <span class='u'> ${(ent.regCap)!""}</span>万元人民币。     
		</div>
		<div class='aoc-subtitle'>第五章  股东的姓名（名称）、认缴的出资额、出资时间、出资方式</div>
		<div class='aoc-text'>
			第七条  股东的姓名（名称）、认缴的出资额、出资时间、出资方式如下：     
		</div>
		<table class='aoc-table' >
				<tr>
					<td rowspan="2" style='width:110px'>股东姓名或名称</td><td colspan="3" style='width:155px'>认缴情况</td>
				</tr>
				<tr>
					<td style='width:80px'>认缴出资数额（万元）</td><td>出资期限</td><td>出资方式</td>
				</tr>
			<#assign var=0>
			<#assign sum=0>
			<#if stag??>
				<#list stag as s>
					<#assign sum=sum+s.curActConAm>
					<#assign var=var+1>
					<tr>
						<td>${(s.inv)!""}</td><td style='width:80px'>${(s.curActConAm)!""}</td><td>${(s.conDate)!""}</td><td>${(s.wb)!""}</td>
					</tr>
				<#if var==4 && s_has_next>
		</table>
				<br/><br/>  
				<table class='aoc-table' >
				<thead>
					<tr>
						<td rowspan="2">股东姓名或名称</td><td colspan="3">认缴情况</td>
					</tr>
					<tr>
						<td>认缴出资数额</td><td>出资期限</td><td>出资方式</td>
					</tr>
					</thead>
		</#if>
		</#list>
		
		</#if>
		<tr>
			<td>合计</td><td>${(sum)!"0"}</td><td></td><td></td>
		</tr>
	</table>
		 <#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
	</#if>
	<div class='split'></div>
		<div class='aoc-notes1'>
			 本章程内容由股东在《股东确认暨指定（委托）书》中予以确认。
			 <hr/>
		</div> 
		<div class='aoc-subtitle'>第六章  公司的机构及其产生办法、职权、议事规则</div>
		<div class='aoc-text'>
			第八条   <span class='u'></span>国有资产监督管理构的职权：    
		</div> 
		<div class='aoc-text'>
			    （一）决定公司的经营方针和投资计划；
		</div>
		<div class='aoc-text'>     
			    （二）委派非由职工代表担任的董事、监事，决定有关董事、监事的报酬事项；
		</div>
		<div class='aoc-text'>	         
			    （三）审议批准董事会的报告；
		</div>
		<div class='aoc-text'>     
			    （四）审议批准监事会的报告；     
		</div>
		<div class='aoc-text'>
			    （五）审议批准公司的年度财务预算方案、决算方案；     
		</div>
		<div class='aoc-text'>
			    （六）审议批准公司的利润分配方案和弥补亏损的方案；     
		</div>
		<div class='aoc-text'>
			    （七）对公司增加或者减少注册资本作出决定；     
		</div>
		<div class='aoc-text'>
			    （八）对发行公司债券作出决定；     
		</div>
		<div class='aoc-text'>
			    （九）对公司合并、分立、解散、清算或者变更公司形式作出决定。    </div><div class='aoc-text'>
			    第九条  重要国有独资公司合并、分立、解散、申请破产的，应当由国有资产监督管理机构审核后，报本级人民政府批准。</div><div class='aoc-text'>     
			    第十条  公司设董事会，成员为<span class='u'>${(board.boardCnt)!"0"}</span>人，由<span class='u'>    </span>国有资产监督管理机构委派。其中职工代表董事由公司职工代表大会选举产生。董事任期 <span class='u'>3 &nbsp;</span> 年，任期届满，可连任。</div><div class='aoc-text'>     
			    董事会设董事长一人，由 <span class='u'>    </span>国有资产监督管理机构从董事会成员中指定。    </div><div class='aoc-text'> 
		              第十一条  董事会行使下列职权：</div><div class='aoc-text'>     
			    （一）审定公司的经营计划和投资方案；     </div><div class='aoc-text'>
			    （二）制订公司的年度财务预算方案、决算方案；    </div><div class='aoc-text'> 
			    （三）制订公司的利润分配方案和弥补亏损方案；    </div><div class='aoc-text'> 
			    （四）制订公司增加或者减少注册资本以及发行公司债券的方案；</div><div class='aoc-text'>     
			    （五）制订公司合并、分立、变更公司形式、解散的方案；</div><div class='aoc-text'>     
			    （六）决定公司内部管理机构的设置； </div><div class='aoc-text'>    
			    （七）决定聘任或者解聘公司经理及其报酬事项，并根据经理的提名决定聘任或者解聘公司副经理、财务负责人及其报酬事项；</div><div class='aoc-text'>     
			    （八）制定公司的基本管理制度；</div><div class='aoc-text'>     
			    （九）国有资产监督管理机构授权的职权。</div><div class='aoc-text'>     
			      第十二条  董事会会议由董事长召集和主持；董事长不能履行职务或者不履行职务的，由半数以上董事共同推举一名董事召集和主持。</div><div class='aoc-text'>     
			</div>
			
		<div class='aoc-text'>
			   第十三条  董事会决议的表决，实行一人一票。</div><div class='aoc-text'>     
			         董事会的议事方式和表决程序。</div><div class='aoc-text'>     
			    第十四条  公司设经理，由董事会聘任或者解聘。经理对董事会负责，行使下列职权：</div><div class='aoc-text'>     
			    （一）主持公司的生产经营管理工作，组织实施董事会决议；</div><div class='aoc-text'>     
			    （二）组织实施公司年度经营计划和投资方案；</div>
			    <div class='aoc-notes1'>
					 本章程内容由股东在《股东确认暨指定（委托）书》中予以确认。
					 <hr/>
				</div> 
			    <#if ((req.state)!"")=="8">
					<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
				</#if>
				<div class='split'></div>
			    
			    <div class='aoc-text'>     
			    （三）拟订公司内部管理机构设置方案；</div><div class='aoc-text'>     
			    （四）拟订公司的基本管理制度；   </div><div class='aoc-text'>  
			    （五）制定公司的具体规章；</div><div class='aoc-text'>     
			    （六）提请聘任或者解聘公司副经理、财务负责人； </div><div class='aoc-text'>    
			    （七）决定聘任或者解聘除应由董事会决定聘任或者解聘以外的负责管理人员；  </div><div class='aoc-text'>   
			    （八）董事会授予的其他职权。</div><div class='aoc-text'>     
			  经理列席董事会会议。</div><div class='aoc-text'>     
			<#assign bsc=0>
			<#assign sc=0>
			<#if suspInfo??>
			<#list suspInfo as s>
				<#if ((s.supsType??)&&(s.supsType=='1'))>
					<#assign bsc=bsc+1>
				</#if>
				<#if ((s.supsType??)&&(s.supsType=='2'))>
					<#assign sc=sc+1>
				</#if>
			</#list>
			</#if>
			   第十五条  公司设监事会，成员<span class='u'>${(susp.suspCnt)!'0'}</span>人，监事会成员由<span class='u'>    </span>国有资产监督管理机构委派，其中职工代表监事由公司职工代表大会选举产生。监事会设主席一人，由全体监事过半数选举产生。监事会中股东代表监事与职工代表监事的比例为<span class='u'>${(bsc)!""}</span>：<span class='u'>${(sc)!""}</span>。</div><div class='aoc-text'>     
			    监事的任期每届为三年，任期届满，可连任。</div>
				
			    <div class='aoc-text'>     
		
		   第十六条  监事会行使下列职权：   </div><div class='aoc-text'>  
		    （一）检查公司财务；     </div><div class='aoc-text'>
		    （二）对董事、高级管理人员执行公司职务的行为进行监督，对违反法律、行政法规、公司章程或者股东会决议的董事、高级管理人员提出罢免的建议；</div><div class='aoc-text'>     
		    （三）当董事、高级管理人员的行为损害公司的利益时，要求董事、高级管理人员予以纠正；</div><div class='aoc-text'>      
		    （四）国务院规定的其他职权。    </div><div class='aoc-text'> 
		    监事可以列席董事会会议。</div><div class='aoc-text'>     
		    第十七条　监事会每年度至少召开一次会议，监事可以提议召开临时监事会会议。 </div><div class='aoc-text'>     
		    第十八条　监事会决议应当经半数以上监事通过。</div>    
		<div class='aoc-subtitle'>第七章  公司的法定代表人</div>
		
		<div class='aoc-text'>
			    第十九条 <span class='u'>${(le.leRepJob)!""}</span>为公司的法定代表人，任期 3年，任期届满，可连任。</div><div class='aoc-text'>     
			    第二十条  法定代表人行使下列职权：</div><div class='aoc-text'>     
		</div>
		<div class='aoc-subtitle'>第八章  出资人认为需要规定的其他事项</div>
		<div class='aoc-text'>
		   	 第二十一条  公司的营业期限<#if ((ent.tradeTerm)!"")=="长期"><span class='u'>&nbsp;长期&nbsp;</span><#else><span class='u'>&nbsp;${(ent.tradeTerm)!""}&nbsp;</span>年</#if>，自公司营业执照签发之日起计算。  
	    </div>
	    <div class='aoc-text'>   
		 	   第二十二条  公司的解散事由与清算办法。
	    </div>
	    <div class='aoc-text'>     
		            第二十三条  公司登记事项以公司登记机关核定的为准。
	    </div>
		   
		   
		<div class='aoc-text'>     
		国有资产监督管理机构盖章：     
		</div>
		<div class='right-text'>      
		                                                  &nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日     
		</div>
		 <#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
	</#if>
	<div class='split'></div>
		<div class='p'></div>
</div>