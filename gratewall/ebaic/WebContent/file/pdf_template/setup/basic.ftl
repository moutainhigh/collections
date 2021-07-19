<a name='basic'></a>
<div class="chapter-basic">
		<div class='second-title'>基本信息表</div>
		<table class='chapter-table'>
			<tr>
				<th style='width:120px;'>公&nbsp;司&nbsp;名&nbsp;称</th>
				<td style='width:auto;' colspan='3'>${(ent.entName)!""}</td>
			</tr>
			<tr>
				<td>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所</td>
				<td colspan='3'>&nbsp;${(ent.opLoc)!""}</td>
			</tr>
			<tr>
				<td>法定代表人</td>
				<td style='width:80px;'>&nbsp;${(le.leRepName)!""}</td>
				<td style='width:220px;'>法定代表人移动电话</td>
				<td style='width:auto;'>&nbsp;${(le.leRepMob)!""}</td>
			</tr>
			<tr>
				<td>注&nbsp;册&nbsp;资&nbsp;本</td>
				<td colspan='3'>&nbsp;${(ent.regCap)!""}万元</td>
			</tr>
			<tr>
				<td>公&nbsp;司&nbsp;类&nbsp;型</td>
				<td colspan='3'>&nbsp;${(ent.entTypeText)!""}</td>
			</tr>
			<tr>
				<td style='height:600px'>经&nbsp;营&nbsp;范&nbsp;围</td>
				<td colspan='3' style="text-align: left;padding:5px;">&nbsp;&nbsp;&nbsp;&nbsp;${(ent.businessScope)!""}（以工商行政管理机关核定的经营范围为准。）</td>
			</tr>
			<tr>
				<td>营&nbsp;业&nbsp;期 &nbsp;限</td>
				<td><#if ((ent.tradeTerm)!"")=="长期">长期<#else><span class='u'>&nbsp;${(ent.tradeTerm)!""}&nbsp;</span>年</#if></td>
				<td>申请副本数</td>
				<td><span class='u'>&nbsp;${(ent.copyNo)!""}&nbsp;</span>份</td>
			</tr>
		</table>
				<#if ((req.state)!"")=="8">
					<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
				</#if>
				<div class='split'></div>
				<div class='p'></div>
</div> 


