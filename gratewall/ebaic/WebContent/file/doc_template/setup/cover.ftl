<!DOCTYPE html>
<head>
<title>封面</title>
<style>
	body{
		background-image:url("${(reqUrl)!""}/upload/showPic.do?fileId=pdfbackgroundpicture");
	}
	.big-title{
		text-align:center;
		font-size:60px;
		font-family:方正小标宋简体;
		margin-top:100px;
	}
	.title{
		text-align:center;
		font-size:30px;
		font-family:方正小标宋简体;
		margin-top:20px;
	}
	.table-bg{
		font-size:20px;
		font-family:方正书宋简体;
		margin-left:20px;
		margin-right:20px;
		margin-top:120px;
		margin-bottom:40px;
		border:1px solid #ffffff;
	}
	.table-bg td{
		border:1px solid #000000;
		height:60px;	
		text-align:center;
	}
	.text{
		text-align:left;
		font-size:15px;
		font-family:方正书宋简体;
		margin-left:100px;
		margin-right:100px;
		margin-top:60px;
		margin-bottom:60px;
	}
	.img1{
		text-align:left;
		margin-left:50px;
		margin-top:60px;
	}
	.img2{
		text-align:center;
		padding-top:180px;
	}
</style>
</head>
<body>
	<div class="img1"><img src="${(reqUrl)!""}/static/image/logo.png"><div>
	<div class='big-title'>内资公司设立登记申请书</div>
	<div class='title'>公司名称：<U>&nbsp; &nbsp;${(ent.entName)!""} </U></div>                    
	<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
		<tr><td>
		<div class='title'>注意事项</div>
		<div class='text'>
		1、请您登录“北京工商”网站（<U>&nbsp;www.BAIC.gov.cn</U>）—“全程电子化登记注册”模块查询相关内容。<br>
		2、申请人和法定代表人需进行身份认证，可选择移动客户端认证或选择就近的工商所、登记注册大厅办理。<br>
		3、填写完成申报信息后即可生成 申请书，待上传文件中需签字的，请使用蓝黑或黑色墨水，保持字迹工整，避免涂改。请按系统提示准备相关证明文件，并根据提示上传证明文件和盖章（签字）页。<br>
		4、提交申请前，请您了解相关的法律、法规，确知所享有的权利和承担的义务。<br>
		5、请您如实反映情况，确保申请材料的真实性。<br>
		</div>
		</td></tr>
	</table>
	<div class="img2">
	<img src="${(reqUrl)!""}/static/image/gong.png">
	<div>
</body>
</html>