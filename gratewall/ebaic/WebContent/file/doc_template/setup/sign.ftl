<!DOCTYPE html>
<head>
<title>代理人承诺书</title>
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
	<div class='title'>代表或代理人承诺书</div>
	<p class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;本人<U>${(req.linkman)!""}${(req.certNo)!""}</U>郑重承诺：受<U>${(ent.entName)!""}</U>全体股东委托，办理该企业<U>${(reqOpe.wb)!""}</U>登记。本人了解办理工商登记的相关法律、政策及规定，确认本次申请中所提交申请材料真实，有关证件、签字、盖章属实，不存在伪造或出具虚假文件、证件，提供非法或虚假住所（经营场所）等违法行为，否则将依法承担相应责任。<br> 
	</p>
	<div class='right-text'>代表或代理人签字：&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<div class='right-text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;</div>
</body>
</html>