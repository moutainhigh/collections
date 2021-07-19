<a name='contact'></a>
<div class='chapter-contact'>
	<div class='second-title'>财务负责人信息表</div>
	
	<table class='contact-table'>
		<#if entc??>
		<#list entc as ec>
		<#if (ec.contactType??)&&(ec.contactType == '2')>
			<tr>
				<th>姓名</th><td>${(ec.name)!""}</td><th>移动电话</th><td>${(ec.mobile)!""}</td>
			</tr>
			<tr>
				<th>证件类型</th><td>${(ec.cerType)!""}</td><th>证件号码</th><td>${(ec.cerNo)!""}</td>
			</tr>
		</#if>
		</#list>
		</#if>
	</table>
	
	<div class='contact-text'>&nbsp;&nbsp;&nbsp;&nbsp;财务负责人：一般由总会计师或财务总监担任，全面负责企业的财务管理、会计核算与监督工作。发生变化的，由企业向税务主管机关申请变更。</div>
	<br/><br/><br/><br/>
	<div class='second-title'>企业联系人信息表</div>
	<table class='contact-table'>
		<#if entc??>
		<#list entc as ec>
		<#if (ec.contactType??)&&(ec.contactType == '0')>
			<tr>
				<th>姓名</th><td>${(ec.name)!""}</td><th>移动电话</th><td>${(ec.mobile)!""}</td>
			</tr>
			<tr>
				<th>证件类型</th><td>${(ec.cerType)!""}</td><th>证件号码</th><td>${(ec.cerNo)!""}</td>
			</tr>
			<tr>
				<th rowspan='2'>企业公共<br />联系方式</th><th>固定电话</th><td colspan='2'>${(ec.tel)!""}</td>
			</tr>
			<tr>
				<th>电子邮箱</th><td colspan='2'>${(ec.email)!""}</td>
			</tr>
		</#if>
		</#list>
		</#if>
	</table>
	<div class='contact-text'>&nbsp;&nbsp;&nbsp;&nbsp;企业联系人：负责本企业与工商等部门的联系沟通，及时转达工商部门对企业传达的信息及相关的法律、法规、规章及政策性意见；向工商部门反映企业的需求或意见。联系人凭本人个人信息登录企业信用信息公示系统依法向社会公示本企业有关信息。联系人应了解登记相关法规和企业信息公示有关规定，熟悉操作企业信用信息公示系统。企业联系人一经确认应当保持相对稳定，发生变化的，可以在企业申办变更登记时向登记机关进行备案。</div>
<#if ((req.state)!"")=="8">
			<div class="page-and-pages">第[page]页  &nbsp;&nbsp;共[sumPage]页</div>
		</#if>
		<div class='split'></div>

<div class='p'></div>
</div>