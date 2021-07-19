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
<table>
 <#assign cou=0>
 <#list weaponMap?keys as key> 
 
<tr>
 ${key_index+1} 通天塔${(key_index+1)%2} 
 ${cou}哈哈哈${cou%2}
    key--->${key}<br/>  
    value----->${weaponMap[key]!("null")}  
    <#if weaponMap[key]??> <#assign cou=cou+1 /> <#else> 空 </#if>
    <br/>  
  </#list> 
  </table> 
</body>
</html>