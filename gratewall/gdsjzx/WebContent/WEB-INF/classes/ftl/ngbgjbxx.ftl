<#--   
    fremarker ��֧��null, �����ã� ������Ϊ�յ�ֵ��  
    ��ʵҲ���Ը�һ��Ĭ��ֵ    
    value-----${weaponMap[key]?default("null")}  
    ������ �����ǰ�ж��Ƿ�Ϊnull  
    <#if weaponMap[key]??>  <#else> </#if>������  
    --> 
<#assign cou=0>
<#if weaponMap??&&weaponMap?size gt 1>
<#list weaponMap?keys as key> 
 	<#if weaponMap[key]??>
		<#if cou%2==0>
			<#if key=="��ҵ(����)����">
				<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td colspan="3" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]}</td>
		 	<#else>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]}</td>
		 		<#assign cou=cou+1 />
		 	</#if>
		 <#else>
		 	<#if key=="��ҵ(����)����">
		 		<td style="text-align: right;padding-right: 1px;"></td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"></td></tr>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;" colspan="3">${weaponMap[key]}</td></tr>
		 	<#else>
		  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]}</td></tr>
		  		<#assign cou=cou+1 />
		  	</#if>
		 </#if> 
    </#if>
</#list> 
<#elseif weaponMap??&&weaponMap?size gt 0&&weaponMap?size lte 1>
<#list weaponMap?keys as key> 
 	<#if weaponMap[key]??>
		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td colspan="3" style="padding-left: 5px;">${weaponMap[key]}</td></tr>
    </#if>
</#list>
</#if>