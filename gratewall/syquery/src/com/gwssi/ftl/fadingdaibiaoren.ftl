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
			<#if key=="企业(机构)名称" || key=="经营(业务)范围" || key=="一般经营项目" || key=="许可经营项目" || key=="住所" || key=="经营范围" || key=="经营范围及方式" || key=="经营场所" || key=="主要经营场所" || key== "营业场所" || key=="分支机构" ||key == "地址">
				<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td colspan="3" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
			<#elseif key=="成立日期" || key=="核准日期" || key=="经营期限自" || key== "经营期限至" || key== "执照有效期限" || key== "注销日期"  || key== "吊销日期" || key== "出生日期" || key== "生产经营期限止" || key== "生产经营期限起" || key == "营业期限自" || key == "营业期限至" ||key =="经营(驻在)期限自" ||key=="经营(驻在)期限至" || key=="合伙期限自" || key=="合伙期限至">
				<#if weaponMap[key]??>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?substring(0,10)}</td>
		  			<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="认缴注册资本" || key== "实缴注册资本" || key== "注册资本折万人民币" || key == "投资总额折万人民币" || key=="投资总额" ||key == "资金数额" || key == "成员出资总额">
		  		<#if weaponMap[key]??>
			  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]} 万元</td>
			  		<#assign cou=cou+1 />
			  	<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="注册资本折万美元" || key== "投资总额折万美元">
		  		<#if weaponMap[key]??>
			  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]} 万美元</td>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#-- <#elseif key=="法定代表人" || key== "负责人" || key== "执行事务合伙人" || key== "投资人" || key== "首席代表">
		  		<#if weaponMap[key]??>
			  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewDataSource();">${weaponMap[key]}</a></td>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if> 
		  	<#elseif key=="经营者">
		  		<#if weaponMap[key]??>
			  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewDataSource();">${weaponMap[key]}</a></td>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if> -->
		  	<#elseif key=="姓名">
		  		<#if weaponMap[key]??>
		  			<#if weaponMap["证照号码"]??>
				  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewDataSource('${weaponMap["证照号码"]}');">${weaponMap[key]}</a></td>
				  		<#assign cou=cou+1 />
				  	<#else>
		  				<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
				  		<#assign cou=cou+1 />
				  	</#if>
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if> 
		  	<#elseif key=="移动电话">
		  		<#if weaponMap[key]??>
			  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td id="phone" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewPhone('${weaponMap[key]}');">查看(该操作会记录日志)</a></td>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if> 
		  	<#elseif key=="电话">
		  		<#if weaponMap[key]??>
			  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td id="tel" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewTel('${weaponMap[key]}');">查看(该操作会记录日志)</a></td>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if> 
		  	<#elseif key=="证照号码">
		  		<#if weaponMap[key]??>
			  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td id="cerno" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewCerno('${weaponMap[key]}');">查看(该操作会记录日志)</a></td>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if> 
		 	<#else>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		 		<#assign cou=cou+1 />
		 	</#if>
		 <#else>
		 	<#if key=="企业(机构)名称" || key=="经营(业务)范围" || key=="一般经营项目" || key=="许可经营项目" || key=="住所" || key=="经营范围" || key=="经营范围及方式" || key=="经营场所" || key=="主要经营场所" || key== "营业场所" || key=="分支机构" ||key == "地址">
		 		<td style="text-align: right;padding-right: 1px;"></td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"></td></tr>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;" colspan="3">${weaponMap[key]?default("")}</td></tr>
		 		<#assign cou=cou+1 />
		 	<#elseif key=="成立日期" || key=="核准日期" || key=="经营期限自" || key== "经营期限至" || key== "执照有效期限" || key== "注销日期"  || key== "吊销日期" || key== "出生日期" || key== "生产经营期限止" || key== "生产经营期限起" || key == "营业期限自"||key == "营业期限至" ||key =="经营(驻在)期限自" ||key=="经营(驻在)期限至"|| key=="合伙期限自" || key=="合伙期限至">
		 		<#if weaponMap[key]??>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?substring(0,10)}</td></tr>
		  			<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="认缴注册资本" || key== "实缴注册资本" || key== "注册资本折万人民币" || key == "投资总额折万人民币" || key=="投资总额" ||key == "资金数额" || key == "成员出资总额">
		  		<#if weaponMap[key]??>
			  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")} 万元</td></tr>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="注册资本折万美元" || key== "投资总额折万美元">
		  		<#if weaponMap[key]??>
			  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")} 万美元</td></tr>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#-- <#elseif key=="法定代表人" || key== "负责人" || key== "执行事务合伙人" || key== "投资人" || key== "首席代表">
		  		<#if weaponMap[key]??>
			  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewDataSource();">${weaponMap[key]}</a></td></tr>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="经营者">
		  		<#if weaponMap[key]??>
			  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewDataSource();">${weaponMap[key]}</a></td></tr>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if> -->
		  	<#elseif key=="姓名">
		  		<#if weaponMap[key]??>
		  			<#if weaponMap["证照号码"]??>
				  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewDataSource('${weaponMap["证照号码"]}');">${weaponMap[key]}</a></td></tr>
				  		<#assign cou=cou+1 />
				  	<#else>
				  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
				  		<#assign cou=cou+1 />
				  	</#if>	
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="移动电话">
		  		<#if weaponMap[key]??>
			  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td id="phone" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewPhone('${weaponMap[key]}');">查看(该操作会记录日志)</a></td></tr>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="电话">
		  		<#if weaponMap[key]??>
			  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td id="tel" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewTel('${weaponMap[key]}');">查看(该操作会记录日志)</a></td></tr>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="证照号码">
		  		<#if weaponMap[key]??>
			  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td id="cerno" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewCerno('${weaponMap[key]}');">查看(该操作会记录日志)</a></td></tr>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
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