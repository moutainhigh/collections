<#--   
    fremarker 不支持null, 可以用！ 来代替为空的值。  
    其实也可以给一个默认值    
    value-----${weaponMap[key]?default("null")}  
    还可以 在输出前判断是否为null  
    <#if weaponMap[key]??>  <#else> </#if>都可以  
    --> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试</title>
</head>

<body>
<h1>你好</h1>
<#assign cou=0>
<#if weaponMap??&&weaponMap?size gt 1>
<table>
<#list weaponMap?keys as key> 
 	<#if weaponMap[key]??>
		<#if cou%2==0>
			<tr><td>${key}</td><td>${weaponMap[key]}</td>
		 <#else>
		  	<td>${key}</td><td>${weaponMap[key]}</td></tr>
		 </#if> 
		 <#assign cou=cou+1 />
    </#if>
</#list> 
</table>
<#elseif weaponMap??&&weaponMap?size gt 0&&weaponMap?size lte 1>
<#list weaponMap?keys as key> 
 	<#if weaponMap[key]??>
 	<table>
		<tr><td>${key}</td><td colspan="3">${weaponMap[key]}</td></tr></table>
    </#if>
</#list>
</#if> 
</body>
</html>