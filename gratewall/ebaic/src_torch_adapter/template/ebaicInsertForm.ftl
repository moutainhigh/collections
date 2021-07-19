<!-- 数据展现 -->
<div vtype="formpanel" name="${(config.configName)!}_Form" wid="${((config.configId)!)}" dataview="false"
	titleDisplay="true" title="${(config.configTitle)!}" width="100%" showborder="true" 
	dataurl="" layout="table" layoutconfig="{cols:2, columnwidth: ['50%','50%']}" labelstyleclass="labelstyle">
	
<#if config?exists && config.columns?exists && (config.columns?size>0)>
<#list config.columns as column>
		<#if ((column.columnFieldType)!)=="text">
	<div name="${(column.columnName)!}" vtype="textfield" label="${(column.columnTitle)!}" labelwidth="200" labelalign="right" width="${(column.columnWidth)!"500"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="combox">
	<div name="${(column.columnName)!}" vtype="comboxfield" label="${(column.columnTitle)!}" labelwidth="200" labelalign="right" width="${(column.columnWidth)!"500"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" dataurl="<#if ((column.columnDict)!)!="">${(other.separator)!}dictionary/queryData.do?dicId=${(column.columnDict)!}</#if>" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="date">
	<div name="${(column.columnName)!}" vtype="datefield" label="${(column.columnTitle)!}"  labelwidth="200" labelalign="right" width="${(column.columnWidth)!"500"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="number">
	<div name="${(column.columnName)!}" vtype="numberfield" label="${(column.columnTitle)!}" labelwidth="200" labelalign="right" width="${(column.columnWidth)!"500"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="textarea">
	<div name="${(column.columnName)!}" vtype="textareafield" label="${(column.columnTitle)!}" labelwidth="200" labelalign="right" width="${(column.columnWidth)!"500"}" height="110" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="hide">
	<div name="${(column.columnName)!}" vtype="hiddenfield" label="${(column.columnTitle)!}" labelwidth="200" labelalign="right" width="${(column.columnWidth)!"500"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="password">
	<div name="${(column.columnName)!}" vtype="passwordfield" label="${(column.columnTitle)!}" labelwidth="200" labelalign="right" width="${(column.columnWidth)!"500"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="radio">
	<div name="${(column.columnName)!}" vtype="radiofield" label="${(column.columnTitle)!}" labelwidth="200" labelalign="right" width="${(column.columnWidth)!"500"}" dataurl="[{checked: true,value: '1',text: '是'},{value: '2',text: '否'}]" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
</#list>
</#if>
</div>