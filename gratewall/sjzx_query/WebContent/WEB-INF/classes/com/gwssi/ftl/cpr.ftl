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
			<#if key=="简要内容" || key=="更多内容" || key=="互联网原文" || key=="主题" || key=="基本信息" || key=="地址" || key=="涉及主体名称" || key=="网址" || key=="案件名称" || key=="信息提供方类型" || key=="处罚决定内容概述" || key=="处罚理由"|| key=="处罚依据" || key=="处罚理由"|| key=="涉嫌违法事实"|| key=="住所"|| key=="单位名称">
				<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td colspan="3" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
			<#elseif key=="要求回复时间" || key=="登记时间" || key=="归档时间" || key=="受理时间" || key== "办结时间" || key== "第一次反馈时间" || key== "最后修改时间"  || key== "调解结束时间" || key== "反馈时间">
				<#if weaponMap[key]??>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?substring(0,10)}</td>
		  			<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="案值" || key=="罚款金额" || key=="没收金额" || key=="变价金额">
		  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")} 万元</td>
		  		<#assign cou=cou+1 />  
		 	<#else>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		 		<#assign cou=cou+1 />
		 	</#if>
		 <#else>
		 	<#if key=="简要内容" || key=="更多内容" || key=="互联网原文" || key=="主题" || key=="基本信息" || key=="地址" || key=="涉及主体名称" || key=="网址" || key=="案件名称" || key=="信息提供方类型" || key=="处罚决定内容概述" || key=="处罚理由"|| key=="处罚依据" || key=="处罚理由"|| key=="涉嫌违法事实"|| key=="住所"|| key=="单位名称">
		 		<td style="text-align: right;padding-right: 1px;"></td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"></td></tr>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;" colspan="3">${weaponMap[key]?default("")}</td></tr>
		 	<#elseif key=="要求回复时间" || key=="登记时间" || key=="归档时间" || key=="受理时间" || key== "办结时间" || key== "第一次反馈时间" || key== "最后修改时间"  || key== "调解结束时间" || key== "反馈时间">
		 		<#if weaponMap[key]??>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?substring(0,10)}</td></tr>
		  			<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="案值" || key=="罚款金额" || key=="没收金额" || key=="变价金额">
		  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")} 万元</td></tr>
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