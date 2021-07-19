<!DOCTYPE html>
<head>
<title>住所证明</title>
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
	.left{
		text-align:left;
		font-size:15px;
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
	.little-title{
		text-align:center;
		font-size:10px;
		font-family:方正小标宋简体;
		margin-top:30px;
	}
</style>
</head>
<body>
	<div class='title'>住所证明</div>
	<#if ent??>
	<#if (ent.domOwnType??)&&(ent.domOwnType=='1')>
	<div class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;住所产权人证明：同意将  <U> ${(ent.opLoc)!""}</U>提供给<U> ${(ent.entName)!""}</U>使用。</div>
                    <div class='right-text'>产权人盖章（签字）：&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br><br><br>
                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;
	</div>
	<div class='left'>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：&nbsp;产权人为单位的，应在“产权人证明”处内加盖公章；产权人为自然人的，由产权人亲笔签字。同时需提交《房屋所有权证》原件图片。<br>
	</div>
	</#if>
	<#if (ent.domOwnType??)&&(ent.domOwnType=='2')>
	<div class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;住所产权人证明：同意将  <U> ${(ent.opLoc)!""}</U>提供给<U> ${(ent.entName)!""}</U>使用。</div>
                    <div class='right-text'>产权人盖章（签字）：&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br><br><br>
                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;
	</div>
	<div class='left-text'>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;需要证明情况：<U> ${(ent.opLoc)!""}</U>住所产权人为 <U>${(ent.domOwner)!""}</U>，房屋用途为<U>${(ent.domUsageType)!""}&nbsp;&nbsp;</U>。<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;特此证明。
	</div>
	<div class='right-text'>证明单位公章：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br><br><br>  
                                         证明单位负责人签字：&nbsp;&nbsp;&nbsp;&nbsp;
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br><br><br>    
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;<br>
	</div>
	<div class='left'>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：&nbsp;① 产权人为单位的，应在“产权人证明”处内加盖公章；产权人为自然人的，由产权人亲笔签字。同时需提交《房屋所有权证》原件图片。<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;② 若住所暂未取得《房屋所有权证》，可由有关部门在证明单位公章处盖章并由证明单位负责人签字，视为对该房屋权属、用途合法性的确认。具体可出证的情况请参见《投资办照通用指南及风险提示》。<br>
	</div>
	</#if>
	</#if>
</body>
</html>