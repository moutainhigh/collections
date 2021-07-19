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
			<#if key=="备注" || key=="类别" || key=="单位地址" || key=="联系人" || key == "地址" || key=="经营场所" || key=="生产场所" || key=="住所" || key=="企业住所" || key=="申请申证单元" || key=="核准申证单元" || key=="资料审查意见" || key=="受理意见" || key=="现场核查结论" || key=="报批意见" || key=="经办意见" || key=="经办退回" || key=="初审意见" || key=="审核意见" || key=="审批意见" || key=="法定代表人或负责人">
				<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td colspan="3" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
			<#elseif key=="发证日期" || key=="有效期" || key=="接收日期" || key== "资料审查日期" || key== "受理日期" || key== "现场核查开始时间"  || key== "报批日期" || key== "经办日期" || key== "初审日期" || key== "审核日期" || key == "审批日期">
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
		  	<#elseif key=="移动电话" || key=="税务代理人联系电话">
		  		<#if weaponMap[key]??>
			  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td id="phone" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewPhone('${weaponMap[key]}');">查看(该操作会记录日志)</a></td>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if> 
		  	<#elseif key=="电话" || key=="固定电话" || key=="分店电话">
		  		<#if weaponMap[key]??>
			  		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td id="tel" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewTel('${weaponMap[key]}');">查看(该操作会记录日志)</a></td>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td>
		  			<#assign cou=cou+1 />
		  		</#if> 
		  	<#elseif key=="证件号码">
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
		 	<#if key=="备注" || key=="类别" || key=="单位地址" || key=="联系人" || key == "地址" || key=="经营场所" || key=="生产场所" || key=="住所" || key=="企业住所" || key=="申请申证单元" || key=="核准申证单元" || key=="资料审查意见" || key=="受理意见" || key=="现场核查结论" || key=="报批意见" || key=="经办意见" || key=="经办退回" || key=="初审意见" || key=="审核意见" || key=="审批意见" || key=="法定代表人或负责人">
		 		<td style="text-align: right;padding-right: 1px;"></td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"></td></tr>
		 		<tr><td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;" colspan="3">${weaponMap[key]?default("")}</td></tr>
		 		<#assign cou=cou+1 />
		 	<#elseif key=="发证日期" || key=="有效期" || key=="接收日期" || key== "资料审查日期" || key== "受理日期" || key== "现场核查开始时间"  || key== "报批日期" || key== "经办日期" || key== "初审日期" || key== "审核日期" || key == "审批日期">
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
		  	<#elseif key=="移动电话" ||  key=="税务代理人联系电话">
		  		<#if weaponMap[key]??>
			  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td id="phone" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewPhone('${weaponMap[key]}');">查看(该操作会记录日志)</a></td></tr>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="电话" || key=="固定电话" || key=="分店电话">
		  		<#if weaponMap[key]??>
			  		<td style="text-align: right;padding-right: 1px;">${key}:</td><td id="tel" style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;"><a href="javascript:void(0);" onclick="viewTel('${weaponMap[key]}');">查看(该操作会记录日志)</a></td></tr>
			  		<#assign cou=cou+1 />
		  		<#else>
		  			<td style="text-align: right;padding-right: 1px;">${key}:</td><td style="padding-left: 5px;padding-bottom: 8px;padding-top: 8px;">${weaponMap[key]?default("")}</td></tr>
		  			<#assign cou=cou+1 />
		  		</#if>
		  	<#elseif key=="证件号码">
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