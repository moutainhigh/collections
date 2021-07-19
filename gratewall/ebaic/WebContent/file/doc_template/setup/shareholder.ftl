<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>股东确认书</title>
<style>
	body{
		background-image:url("${(reqUrl)!""}/upload/showPic.do?fileId=pdfbackgroundpicture");
		line-height:40px;
	}
	.title{
		text-align:center;
		font-size:30px;
		font-family:方正小标宋简体;
		margin-top:100px;
		margin-bottom:45px;
	}
	.little-title{
		text-align:center;
		font-size:20px;
		font-family:方正小标宋简体;
		margin-top:60px;
	}
	.left-text{
		text-align:left;
		font-size:20px;
		font-family:方正书宋简体;
		margin-left:100px;
		margin-right:100px;
		margin-top:5px;
		margin-bottom:5px;
		width:100%;
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
	li{
		display:inline;
	}
</style>
</head>
<body>
	<div class='title'>股东确认书</div>

<p class='left-text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<U>&nbsp;${(ent.entName)!""}&nbsp;</U>全体股东现向工商行政管理机关提出公司设立申请，并对以下内容予以确认：<br><br>
<#if ((req.agentName)!"")!="">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、根据国家法律、行政法规的规定，现委托&nbsp;<U>&nbsp;${(req.linkman)!""}（身份证号码：${(req.certNo)!""}）&nbsp;</U>为代理人，办理企业开业（设立）登记注册手续。委托期限自 <U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>年 <U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>月<U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>日至<U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>年<U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>月<U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>日。<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;代理人更正有关材料的权限为：&nbsp;<img src="${(reqUrl)!""}/static/image/checkboxpicture.png"/>&nbsp;&nbsp;不得修改申报文件任何内容；<img src="${(reqUrl)!""}/static/image/checkboxpicture.png"/>&nbsp;&nbsp;同意修改申报文件的错误文字内容；<img src="${(reqUrl)!""}/static/image/checkboxpicture.png"/>&nbsp;&nbsp;其他有权更正的事项：   <U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </U>(请根据实际情况填写委托期限并在代理人更正有关材料权限的□内划“√”)。<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、同意<U>&nbsp;&nbsp;${(ent.entName)!""}&nbsp;&nbsp;</U>章程。<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、同意法定代表人、董事、经理、监事的任职。<br><br>
<#else>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、同意委托&nbsp;<U>${(req.linkman)!""}（身份证号码：${(req.certNo)!""}）</U>&nbsp;办理本公司的登记注册手续。委托期限自 <U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>年 <U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>月<U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>日至<U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>年<U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>月<U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U>日，委托事项包括：&nbsp;<img src="${(reqUrl)!""}/static/image/checkboxpicture.png"/>&nbsp;&nbsp;网上申报、<img src="${(reqUrl)!""}/static/image/checkboxpicture.png"/>&nbsp;&nbsp;修改申报内容中的文字错误、<img src="${(reqUrl)!""}/static/image/checkboxpicture.png"/>&nbsp;&nbsp;领取营业执照和有关文书、<img src="${(reqUrl)!""}/static/image/checkboxpicture.png"/>&nbsp;&nbsp;其他事项<U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </U>(请根据实际情况填写委托期限和其他委托事项)。<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、同意<U>&nbsp;&nbsp;${(ent.entName)!""}&nbsp;&nbsp;</U>章程。<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、同意法定代表人、董事、经理、监事的任职。<br><br>
</#if>
</p>
<div class='right-text'>
                             全体股东盖章（签字）：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br><br><br>
                                                     （盖章或签字是对全部申请内容的确认）<br><br><br>
 		       
                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年
							  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月  
							  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
</div>
</body>
</html>