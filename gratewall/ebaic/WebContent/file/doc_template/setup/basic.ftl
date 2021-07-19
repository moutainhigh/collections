<!DOCTYPE html>
<title>登记基本信息表</title>
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
	.table-bg{
		font-size:20px;
		font-family:方正书宋简体;
		margin-left:20px;
		margin-right:20px;
		margin-top:80px;
		margin-bottom:40px;
		border:1px solid #ffffff;
	}
	.rgm{
		text-align:left;
	}
	.table-bg td{
		border:1px solid #000000;
		height:60px;	
		text-align:center;
	}
	
</style>
</head>
<body>
	<div class='title'>基本信息表</div>
	<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
		<tr>
			<td>公&nbsp;司&nbsp;名&nbsp;称</td>
			<td colspan='3'>&nbsp;&nbsp;${(ent.entName)!""}</td>
		</tr>
		<tr>
			<td>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所</td>
			<td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(ent.opLoc)!""}&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td>法定代表人</td>
			<td>&nbsp;&nbsp;${(le.leRepName)!""}</td>
			<td>法定代表人移动电话</td>
			<td>&nbsp;&nbsp;${(le.leRepMob)!""}</td>
		</tr>
		<tr>
			<td>注册资本</td>
			<td colspan='3'>&nbsp;&nbsp;${(ent.regCap)!""}万元</td>
		</tr>
		<tr>
			<td>公&nbsp;司&nbsp;类&nbsp;型</td>
			<td colspan='3'>&nbsp;&nbsp;${(ent.entTypeText)!""}</td>
		</tr>
		<tr class="rgm">
			<td style='height:400px' >经&nbsp;营&nbsp;范&nbsp;围</td>
			
			<td colspan=3 ><div class="rgm">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(ent.businessScope)!""}</div></td>
		</tr>
		<tr>
			<td>营&nbsp;业&nbsp;期 &nbsp;限</td>
			<td><#if ((ent.tradeTerm)!"")=="长期">长期<#else><U>${(ent.tradeTerm)!""}</U>年</#if></td>
			<td>申请副本数</td>
			<td><U>${(ent.copyNo)!""}</U>份</td>
		</tr>
	</table>

</body>
</html>