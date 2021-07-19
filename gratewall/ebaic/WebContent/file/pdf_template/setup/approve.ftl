<a name='approve'></a>
<div class='chapter-approve'>
	<div class='second-title'>内资公司设立登记审核表</div>
	<table class='chapter-table' >
		<tr>
			<td>统一社会信用代码</td>
			<td colspan='3'>${(ent.uniScid)!""}</td>
		</tr>
		<tr>
			<td>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</td>
			<td colspan='3'>${(ent.entName)!""}</td>
		</tr>
		<tr>
			<td>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所</td>
			<td colspan='3'>${(ent.opLoc)!""}&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th>法定代表人姓名</th>
			<td class=''>${(le.leRepName)!""}</td>
			<th>法定代表人联系方式</th>
			<td>${(le.leRepMob)!""}</td>
		</tr>
		<tr>
			<td>注册资本</td>
			<td colspan='3'>${(ent.regCap)!""}万元</td>
		</tr>
		<tr>
			<td>企&nbsp;业&nbsp;类&nbsp;型</td>
			<td colspan='3'>${(ent.entTypeText)!""}</td>
		</tr>
		<tr>
			<td style='height:200px'>经&nbsp;营&nbsp;范&nbsp;围</td>
			<td colspan='3' style="text-align: left;padding:5px;" >&nbsp;&nbsp;&nbsp;&nbsp;${(ent.businessScope)!""}</td>
		</tr>
		<tr>
			<th>营&nbsp;业&nbsp;期 &nbsp;限</th>
			<td><#if ((ent.tradeTerm)!"")=="长期">长期<#else><U>${(ent.tradeTerm)!""}</U>年</#if></td>
			<th>副本数</th>
			<td><U>${(ent.copyNo)!""}</U>份</td>
		</tr>
		<tr>
			<td>受&nbsp;理&nbsp;意&nbsp;见</td>
			<td colspan='3' style="text-align:left;">
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;经审查，材料齐全、符合法定形式。建议准予设立登记。
			<br/><br/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;受理人： &nbsp;${(ceName.censorName)!""}
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(ceName.cendate)!""}<br/>&nbsp;</td>
			
		</tr>
		<tr>
			<td>核&nbsp;准&nbsp;意&nbsp;见</td>
			<td colspan='3'>
			<br/>准予设立登记
			<br/>
			<br/><br/>
			<div class='approve-img'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;核准人：&nbsp;
				
				<#if ceName.approveUserId?? >
					<img  src='http://160.100.0.92:7001/approve/signPic/show.do?userId=${(ceName.approveUserId)!""}' style="height:30px; width:80px" />
				</#if>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(ceName.appdate)!""}</div><br/></td>
		</tr>
		<tr>
			<td style='height:80px'>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</td>
			<td colspan='3'></td>
		</tr>
	</table>
</div>
<div class='p'></div>
