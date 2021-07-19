
<article style="width:1000px;margin:0 auto">
<!-- 数据展现 -->
<div vtype="formpanel" name="${(config.configName)!}_Form" dataview="false"
	titleDisplay="true" title="<img src='${(other.separator)!}static/images/nameInfo.png' style='margin-right:10px'>${(config.configTitle)!}" width="1000" showborder="false" 
	dataurl="${(other.separator)!}torch/service.do?fid=${config.functionId}&wid=${config.configId}<#if (config.conditions)?exists && ((config.conditions)?size>0)>&<#list config.conditions as condition>${((condition.conditionName)!)}={${((condition.conditionName)!)}}<#if condition_has_next>&</#if></#list></#if>" layout="table" layoutconfig="{cols:2, columnwidth: ['50%','50%']}" labelstyleclass="labelstyle" style="margin-bottom:10px">
	
<#if config?exists && config.columns?exists && (config.columns?size>0)>
<#list config.columns as column>
		<#if ((column.columnFieldType)!)=="text">
	<div name="${(column.columnName)!}" vtype="textfield" label="${(column.columnTitle)!}" labelwidth="80" labelalign="left" width="${(column.columnWidth)!"400"}" style="margin-left: 60px;" readOnly="true" ></div>
		</#if>
		<#if ((column.columnFieldType)!)=="combox">
	<div name="${(column.columnName)!}" vtype="comboxfield" label="${(column.columnTitle)!}" labelwidth="80" labelalign="left" width="${(column.columnWidth)!"400"}"  dataurl="<#if ((column.columnDict)!)!="">${(other.separator)!}dictionary/queryData.do?dicId=${(column.columnDict)!}</#if>" style="margin-left: 60px;"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="date">
	<div name="${(column.columnName)!}" vtype="datefield" label="${(column.columnTitle)!}"  labelwidth="80" labelalign="left" width="${(column.columnWidth)!"400"}" style="margin-left: 60px;"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="number">
	<div name="${(column.columnName)!}" vtype="numberfield" label="${(column.columnTitle)!}" labelwidth="80" labelalign="left" width="${(column.columnWidth)!"400"}" style="margin-left: 60px;"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="textarea">
	<div name="${(column.columnName)!}" vtype="textareafield" label="${(column.columnTitle)!}" labelwidth="80" labelalign="left" width="${(column.columnWidth)!"800"}" height="110" style="margin-left: 60px;"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="hide">
	<div name="${(column.columnName)!}" vtype="hiddenfield" label="${(column.columnTitle)!}" labelwidth="80" labelalign="left" width="${(column.columnWidth)!"400"}" ></div>
		</#if>
		<#if ((column.columnFieldType)!)=="password">
	<div name="${(column.columnName)!}" vtype="passwordfield" label="${(column.columnTitle)!}" labelwidth="80" labelalign="left" width="${(column.columnWidth)!"400"}" ></div>
		</#if>
		<#if ((column.columnFieldType)!)=="radio">
	<div name="${(column.columnName)!}" vtype="radiofield" label="${(column.columnTitle)!}" labelwidth="80" labelalign="left" width="${(column.columnWidth)!"400"}" dataurl="[{checked: true,value: '1',text: '是'},{value: '2',text: '否'}]" ></div>
		</#if>
</#list>
</#if>
</div>
	<div name="${(fbo.functionName)!}Button" align="center" colspan="2">
		<div id="${(fbo.functionName)!}_save_button" name="${(fbo.functionName)!}_save_button" vtype="button" text="上一步"
			defaultview="2" align="center" iconurl="${(other.separator)!}static/images/backWhite.png">
		</div>
		<div id="${(fbo.functionName)!}_reset_button" name="${(fbo.functionName)!}_reset_button" vtype="button" text="下一步"
			defaultview="2" align="center" iconurl="${(other.separator)!}static/images/nextWhite.png">
		</div>
	</div>
</article>