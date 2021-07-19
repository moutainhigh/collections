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
			<#if key=="企业名称" || key=="联系地址" || key=="网站名称" || key=="域名地址" || key=="个体工商户名称" || key=="地址" || key=="涉及主体名称" || key=="网址" || key=="案件名称" || key=="信息提供方类型" || key=="处罚决定内容概述" || key=="处罚理由"|| key=="处罚依据" || key=="处罚理由"|| key=="涉嫌违法事实"|| key=="住所"|| key=="单位名称">
				<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td colspan="3" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
			<#elseif key=="年报时间" || key=="首次公示时间" || key=="归档时间" || key=="受理时间" || key== "办结时间" || key== "第一次反馈时间" || key== "最后修改时间"  || key== "调解结束时间" || key== "反馈时间">
				<#if weaponMap[key]??>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?substring(0,10)}</td>
		  			<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="资产总额" || key=="负债总额" || key=="销售(营业)收入" || key=="其中主营业务收入" || key=="利润总额" || key=="净利润" || key=="纳税总额" || key=="所有者权益合计">
		  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")} 万元</td>
		  		<#assign cou=cou+1 />  
		 	<#else>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		 		<#assign cou=cou+1 />
		 	</#if>
		 <#else>
		 	<#if key=="企业名称" || key=="联系地址" || key=="网站名称" || key=="域名地址" || key=="个体工商户名称" || key=="地址" || key=="涉及主体名称" || key=="网址" || key=="案件名称" || key=="信息提供方类型" || key=="处罚决定内容概述" || key=="处罚理由"|| key=="处罚依据" || key=="处罚理由"|| key=="涉嫌违法事实"|| key=="住所"|| key=="单位名称">
		 		<td style="text-align: right;padding-right: 1px;"></td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"></td></tr>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;" colspan="3">${weaponMap[key]?default("")}</td></tr>
		 	<#elseif key=="年报时间" || key=="首次公示时间" || key=="归档时间" || key=="受理时间" || key== "办结时间" || key== "第一次反馈时间" || key== "最后修改时间"  || key== "调解结束时间" || key== "反馈时间">
		 		<#if weaponMap[key]??>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?substring(0,10)}</td></tr>
		  			<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="资产总额" || key=="负债总额" || key=="销售(营业)收入" || key=="其中主营业务收入" || key=="利润总额" || key=="净利润" || key=="纳税总额" || key=="所有者权益合计">
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