<!DOCTYPE html>
<head>
<title>联系人信息</title>
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
		margin-top:60px;
		margin-bottom:10px;
		border:1px solid #ffffff;
	}
	.table-bg td{
		border:1px solid #000000;
		height:60px;	
		text-align:center;
	}
	.text-area{
		font-size:15px;
		font-family:方正书宋简体;
		margin-left:100px;
		margin-right:100px;
		margin-bottom:10px;
		text-align:left;
	}
	.left-text{
		text-align:left;
		font-size:15px;
		font-family:方正书宋简体;
		margin-left:100px;
		margin-right:100px;
		margin-top:9px;
		margin-bottom:5px;
	}
</style>
</head>
<body>
	<div class='title'>财务负责人信息表</div>
	<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
		<#if entc??>
		<#list entc as ec>
		<#if (ec.contactType??)&&(ec.contactType == '2')>
			<tr>
				<td>姓名</td><td>${(ec.name)!""}</td><td>移动电话</td><td>${(ec.mobile)!""}</td>
			</tr>
			<tr>
				<td>证件类型</td><td>${(ec.cerType)!""}</td><td>证件号码</td><td>${(ec.cerNo)!""}</td>
			</tr>
		</#if>
		</#list>
		</#if>
	</table>
	<div class='left-text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;财务负责人：一般由总会计师或财务总监担任，全面负责企业的财务管理、会计核算与监督工作。发生变化的，由企业向税务主管机关申请变更。</div>
	<div class='title'>企业联系人信息表</div>
	<table class='table-bg' cellpadding='0' cellspacing='0' border='1' align='center' width='1000'>
		<#if entc??>
		<#list entc as ec>
		<#if (ec.contactType??)&&(ec.contactType == '0')>
			<tr>
				<td>姓名</td><td>${(ec.name)!""}</td><td>移动电话</td><td>${(ec.mobile)!""}</td>
			</tr>
			<tr>
				<td>证件类型</td><td>${(ec.cerType)!""}</td><td>证件号码</td><td>${(ec.cerNo)!""}</td>
			</tr>
			<tr>
				<td rowspan='2'>企业公共联系方式</td><td>固定电话</td><td colspan='2'>${(ec.tel)!""}</td>
			</tr>
			<tr>
				<td>电子邮箱</td><td colspan='2'>${(ec.email)!""}</td>
			</tr>
		</#if>
		</#list>
		</#if>
	</table>
	<div class='left-text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;企业联系人：负责本企业与工商等部门的联系沟通，及时转达工商部门对企业传达的信息及相关的法律、法规、规章及政策性意见；向工商部门反映企业的需求或意见。联系人凭本人个人信息登录企业信用信息公示系统依法向社会公示本企业有关信息。联系人应了解登记相关法规和企业信息公示有关规定，熟悉操作企业信用信息公示系统。企业联系人一经确认应当保持相对稳定，发生变化的，可以在企业申办变更登记时向登记机关进行备案。</div>
</body>
</html>