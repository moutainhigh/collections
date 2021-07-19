<!DOCTYPE html>
<head>
<title>法定代表人确认授权委托书</title>
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
	<div class='title'>法定代表人确认授权委托书</div>
	<p class='left-text'>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<U>${(le.leRepName)!""}</U>拟任<U>${(ent.entName)!""}</U>的法定代表人，现委托&nbsp;&nbsp;<U>${(req.linkman)!""}，（${(req.certNo)!""}）</U>&nbsp;&nbsp;作为本人的合法代理人，对<U>${(ent.entName)!""}</U>设立申报材料进行确认。本人对代理人确认的结果均予以认可，并承担相应的法律责任。<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;委托期限：自签字之日起至<U>${(ent.entName)!""}</U>设立登记事项办结为止。<br><br><br><br>
		法定代表人（委托人）签字：<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;代理人签字：
	</p>
	<div class='right-text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;</div>
</body>
</html>