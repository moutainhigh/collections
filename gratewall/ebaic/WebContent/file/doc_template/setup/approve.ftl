<!DOCTYPE html>
<title>内资公司设立登记审核表</title>
<style>
	body{
		background-image:url("${(reqUrl)!""}/upload/showPic.do?fileId=pdfbackgroundpicture");
	}
	.title{
		text-align:center;
		font-size:25px;
		font-family:方正小标宋简体;
		margin-top:50px;
	}
	.table-bg{
		font-size:20px;
		font-family:方正书宋简体;
		margin-left:15px;
		margin-right:20px;
		margin-top:40px;
		margin-bottom:40px;
		border:1px solid #ffffff;
	}
	.table-bg td{
		border:1px solid #000000;
		height:50px;	
		text-align:center;
	}
</style>
</head>
<body>
	<div class='title'>内资公司设立登记审核表</div>
	<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
		<tr>
			<td>统一社会信用代码</td>
			<td colspan='3'>&nbsp;&nbsp;${(ent.uniScid)!""}</td>
		</tr>
		<tr>
			<td>名&nbsp;称</td>
			<td colspan='3'>&nbsp;&nbsp;${(ent.entName)!""}</td>
		</tr>
		<tr>
			<td>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所</td>
			<td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(ent.opLoc)!""}&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td>法定代表人姓名</td>
			<td>&nbsp;&nbsp;${(le.leRepName)!""}</td>
			<td>法定代表人联系方式</td>
			<td>&nbsp;&nbsp;${(le.leRepMob)!""}</td>
		</tr>
		<tr>
			<td>注册资本</td>
			<td colspan='3'>&nbsp;&nbsp;${(ent.regCap)!""}万元</td>
		</tr>
		<tr>
			<td>企&nbsp;业&nbsp;类&nbsp;型</td>
			<td colspan='3'>&nbsp;&nbsp;${(ent.entTypeText)!""}</td>
		</tr>
		<tr>
			<td style='height:350px'>经&nbsp;营&nbsp;范&nbsp;围</td>
			<td colspan=3>&nbsp;&nbsp;${(ent.businessScope)!""}</td>
		</tr>
		<tr>
			<td>营&nbsp;业&nbsp;期 &nbsp;限</td>
			<td><#if ((ent.tradeTerm)!"")=="长期">长期<#else><U>${(ent.tradeTerm)!""}</U>年</#if></td>
			<td>副本数</td>
			<td><U>${(ent.copyNo)!""}</U>份</td>
		</tr>
		<tr>
			<td>受&nbsp;理&nbsp;意&nbsp;见</td>
			<td colspan='3' style="text-align:left;">
			<br>&nbsp;&nbsp;&nbsp;&nbsp;经审查，材料齐全、符合法定形式。建议准予设立登记。
			<br>		<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;受理人： &nbsp;&nbsp;${(ceName.censorName)!""}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日<br>&nbsp;</td>
			
		</tr>
		<tr>
			<td>核&nbsp;准&nbsp;意&nbsp;见</td>
			<td colspan='3'>
			<br>准予设立登记
			<br>
			<br>
			<div style="text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;核准人：<#if signPic??><img height='80' width='222' src='${(reqUrl)!""}${(signUrl.signPicUrl)!""}'/></#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</div><br></td>
		</tr>
		<tr>
			<td style='height:80px'>备注</td>
			<td colspan='3'></td>
		</tr>
	</table>

</body>
</html>