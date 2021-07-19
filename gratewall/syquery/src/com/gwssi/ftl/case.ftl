<#--   
    fremarker 不支持null, 可以用！ 来代替为空的值。  
    其实也可以给一个默认值    
    value-----${weaponMap[key]?default("null")}  
    还可以 在输出前判断是否为null  
    <#if weaponMap[key]??>  <#else> </#if>都可以  
    --> 
<#assign cou=0>
<#if weaponMap??&&weaponMap?size gt 1>
<#list weaponMap?keys as key> 
 	<#--<#if weaponMap[key]??>--> 
		<#if cou%2==0>
			<#if key=="案发地" || key=="案情简要" || key=="当事人名称" || key=="违反法律" || key=="处罚种类内容"|| key=="案件名称" || key=="处罚决定内容概述" || key=="处罚理由"|| key=="处罚依据" || key=="处罚理由"|| key=="涉嫌违法事实"|| key=="住所"|| key=="单位名称" || key=="移送理由">
				<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td colspan="3" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
			<#elseif key=="案发时间" || key=="立案日期" || key=="执行日期" || key== "结案日期" || key== "执照有效期限" || key== "注销日期"  || key== "吊销日期" || key== "处罚决定书签发日期"  || key== "登记时间" || key== "案源终止时间" || key== "移送日期" >
				<#if weaponMap[key]??>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?substring(0,10)}</td>
		  			<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="案值" || key=="罚款金额" || key=="没收金额" || key=="变价金额">
		  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")} 元</td>
		  		<#assign cou=cou+1 />  
		 	<#else>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		 		<#assign cou=cou+1 />
		 	</#if>
		 <#else>
		 	<#if key=="案发地" || key=="案情简要" || key=="当事人名称" || key=="违反法律" || key=="处罚种类内容"|| key=="案件名称" || key=="处罚决定内容概述" || key=="处罚理由"|| key=="处罚依据" || key=="处罚理由"|| key=="涉嫌违法事实"|| key=="住所"|| key=="单位名称" || key=="移送理由">
		 		<td style="text-align: right;padding-right: 1px;"></td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"></td></tr>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;" colspan="3">${weaponMap[key]?default("")}</td></tr>
		 	<#elseif key=="案发时间" || key=="立案日期" || key=="执行日期" || key== "结案日期" || key== "执照有效期限" || key== "注销日期"  || key== "吊销日期" || key== "处罚决定书签发日期"  || key== "登记时间" || key== "案源终止时间" || key== "移送日期" >
		 		<#if weaponMap[key]??>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?substring(0,10)}</td></tr>
		  			<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="案值" || key=="罚款金额" || key=="没收金额" || key=="变价金额">
		  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")} 元</td></tr>
		  		<#assign cou=cou+1 />
		 	<#else>
		  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  		<#assign cou=cou+1 />
		  	</#if>
		 </#if>
    <#--</#if>--> 
</#list>
	<#if cou%2!=0> 
		 	<td style="text-align: right;padding-right: 1px;"></td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"></td></tr>
		 	<#assign cou=cou+1 />
	</#if>  
<#elseif weaponMap??&&weaponMap?size gt 0&&weaponMap?size lte 1>
<#list weaponMap?keys as key> 
 	<#--<#if weaponMap[key]??>--> 
		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td colspan="3" style="padding-left: 5px;">${weaponMap[key]?default("")}</td></tr>
     <#--</#if>--> 
</#list>
</#if>