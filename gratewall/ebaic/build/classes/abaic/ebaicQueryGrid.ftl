<#assign haveCondition = true>
<#assign showTitle = false>

<#if !(config?exists) || !(config.conditions?exists) || (config.conditions?size<1)>
	<#assign haveCondition = false>
<#else>
	<#list config.conditions as condition>
		<#if ((condition.conditionFieldType)!)!="hide">
			<#assign showTitle = true>
			<#break>
		</#if>
	</#list>
</#if>

<#if haveCondition>
<!-- 查询条件 -->
<div name="${(config.configName)!}" 
	vtype="panel" width="1000" layout="auto"
	labelstyleclass="labelstyle" title="${(config.configTitle)!}" 
	titledisplay=<#if showTitle>"true"<#else>"false"</#if> showborder="true" closable="false"
	style="margin: 0 auto">
	
	<div name="${(config.configName)!}_Form" wid="${(config.configId)!}" vtype="formpanel" width="100%" showborder=false
			layout="table" layoutconfig="{cols:2, columnwidth: ['50%','50%']}">
	<#if config?exists && config.conditions?exists && (config.conditions?size>0)>
		<#list config.conditions as condition>
			<#if ((condition.conditionFieldType)!)=="text">
		<div name="${(condition.conditionName)!}" vtype="textfield" label="${(condition.conditionTitle)!}" labelwidth="150" labelalign="right" height="46" width="${(condition.conditionWidth)!"400"}" tooltip="${(condition.conditionTips)!}" rule="${(condition.conditionRule)!}" ></div>
			</#if>
			<#if ((condition.conditionFieldType)!)=="combox">
		<div name="${(condition.conditionName)!}" vtype="comboxfield" label="${(condition.conditionTitle)!}" labelwidth="150" labelalign="right" height="46"  width="${(condition.conditionWidth)!"400"}" tooltip="${(condition.conditionTips)!}" rule="${(condition.conditionRule)!}" dataurl="<#if ((condition.conditionDict)!)!="">${(other.separator)!}dictionary/queryData.do?dicId=${(condition.conditionDict)!}</#if>"></div>
			</#if>
			<#if ((condition.conditionFieldType)!)=="date">
		<div name="${(condition.conditionName)!}" vtype="datefield" label="${(condition.conditionTitle)!}"  labelwidth="150" labelalign="right" height="46" width="${(condition.conditionWidth)!"400"}" tooltip="${(condition.conditionTips)!}" rule="${(condition.conditionRule)!}"></div>
			</#if>
			<#if ((condition.conditionFieldType)!)=="number">
		<div name="${(condition.conditionName)!}" vtype="numberfield" label="${(condition.conditionTitle)!}" labelwidth="150" labelalign="right" height="46" width="${(condition.conditionWidth)!"400"}" tooltip="${(condition.conditionTips)!}" rule="${(condition.conditionRule)!}"></div>
			</#if>
			<#if ((condition.conditionFieldType)!)=="textarea">
		<div name="${(condition.conditionName)!}" vtype="textareafield" label="${(condition.conditionTitle)!}" labelwidth="150" labelalign="right" height="150"  width="${(condition.conditionWidth)!"400"}" tooltip="${(condition.conditionTips)!}" rule="${(condition.conditionRule)!}"></div>
			</#if>
			<#if ((condition.conditionFieldType)!)=="hide">
		<div name="${(condition.conditionName)!}" vtype="hiddenfield" label="${(condition.conditionTitle)!}" labelwidth="150" labelalign="right" height="46" width="${(condition.conditionWidth)!"400"}" tooltip="${(condition.conditionTips)!}" rule="${(condition.conditionRule)!}"></div>
			</#if>
			<#if ((condition.conditionFieldType)!)=="password">
		<div name="${(condition.conditionName)!}" vtype="passwordfield" label="${(condition.conditionTitle)!}" labelwidth="150" labelalign="right" height="46" width="${(condition.conditionWidth)!"400"}" tooltip="${(condition.conditionTips)!}" rule="${(condition.conditionRule)!}"></div>
			</#if>
			<#if ((condition.conditionFieldType)!)=="radio">
		<div name="${(condition.conditionName)!}" vtype="radiofield" label="${(condition.conditionTitle)!}" labelwidth="150" labelalign="right" height="46" width="${(condition.conditionWidth)!"400"}" tooltip="${(condition.conditionTips)!}" dataurl="[{checked: true,value: '1',text: '是'},{value: '2',text: '否'}]" rule="${(condition.conditionRule)!}"></div>
			</#if>
		</#list>
		<div name="${(fbo.functionName)!}Button" align="center" colspan="2">
			<div id="${(fbo.functionName)!}_query_button" name="${(fbo.functionName)!}_query_button" vtype="button" text="查&nbsp;询"
				defaultview="2" align="center" iconurl="${(other.separator)!}static/images/check.png">
			</div>
			<div id="${(fbo.functionName)!}_reset_button" name="${(fbo.functionName)!}_reset_button" vtype="button" text="重&nbsp;置"
				defaultview="2" align="center" iconurl="${(other.separator)!}static/images/reset.png">
			</div>
		</div>
	</#if>
	</div>

	<!-- 数据展现 -->
	<div vtype="gridpanel" name="${(config.configName)!}_Grid" wid="${(config.configId)!}" datarender=""
		titledisplay="false" title="${(config.configTitle)!}" width="1000" showborder="true" 
		style="margin: 0 auto; border: none;" lineno=false
		dataurl="${(other.separator)!}torch/service.do?fid=${(config.functionId)!}&wid=${(config.configId)!}" selecttype="2"
		editortype="row">
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
		<#if config?exists && config.columns?exists && (config.columns?size>0)>
		<#list config.columns as column>
				<div name="${(column.columnName)!}" text="${(column.columnTitle)!}" <#if ((column.columnFieldType)!)=="hide">visible="false"</#if> textalign="center" width="${(column.columnWidth)!"20%"}" dataurl="<#if ((column.columnDict)!)!="">${(other.separator)!}dictionary/queryData.do?dicId=${(column.columnDict)!}</#if>"></div>
		</#list>
		</#if>
			</div>
		</div>
		<div vtype="gridtable" name="grid_table" width="100%"></div>
		<div vtype="paginator" name="grid_paginator" theme="1" pagerows="10" width="100%"></div>
	</div>
</div>
</#if>