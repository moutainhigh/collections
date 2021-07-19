<a name='domicile'></a>
<div class='chapter-domicile'>
	<div class='second-title'>住所证明</div><br/><br/>
	<#if ent??>
	<#if (ent.domOwnType??)&&(ent.domOwnType=='1')>
				<div class='left-text'>
				    住所产权人证明：同意将<span class='u'> &nbsp;${(ent.opLoc)!""}&nbsp; </span>提供给<span class='u'>&nbsp;${(ent.entName)!""}&nbsp; </span>使用。
				</div><br/><br/><br/>
			    <div class='right-text'>
			    	<div class='domicile-right-text'>
			                                 产权人盖章（签字）： <br/><br/>
			    	</div>
			                   年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<div class='domicile-info'>
			       	注：&nbsp;产权人为单位的，应在“产权人盖章”处加盖公章；产权人为自然人的，由产权人亲笔签字。同时需在系统中上传《房屋所有权证》原件或复印件图片。<br/>
				</div>
	</#if>
	<#if (ent.domOwnType??)&&(ent.domOwnType=='2')>
	<div class='left-text'>
		住所产权人证明：同意将 <span class='u'>&nbsp;${(ent.opLoc)!""}&nbsp;</span>提供给<span class='u'>&nbsp;${(ent.entName)!""}&nbsp;</span>使用。
	</div>
         <div class='right-text'>
         	<div class='domicile-right-text'>
                                  产权人盖章（签字）：<br/><br/>
            </div>
                                 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;
	     </div>
	<div class='left-text'>
		需要证明情况：<span class='u'>&nbsp;${(ent.opLoc)!""}&nbsp;</span>住所产权人为<span class='u'>&nbsp;${(ent.domOwner)!""}&nbsp;</span>，房屋用途为<span class='u'>&nbsp;${(ent.domUsageType)!""}&nbsp;</span>。<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;特此证明。
	</div>
	<div class='right-text'>
		<div class='domicile-right-text'>
			证明单位公章：&nbsp;&nbsp;&nbsp;&nbsp;<br/><br/>
	                    证明单位负责人签字：<br/><br/>  
        </div>             
                    年 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;<br/>
	</div>
	<div class='domicile-info'>
		注：<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、产权人为单位的，应在“产权人盖章”处加盖公章；产权人为自然人的，由产权人亲笔签字。同时需在系统中上传《房屋所有权证》原件或复印件图片。<br/>
      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、若住所暂未取得《房屋所有权证》，可由有关部门在证明单位公章处盖章并由证明单位负责人签字，视为对该房屋权属、用途合法性的确认。具体可出证的情况请参见《投资办照通用指南及风险提示》。<br/>
	</div>
	</#if>
	</#if>
</div>
<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
		</#if>
		<div class='split'></div>

<div class='p'></div>