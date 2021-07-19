<!-- 数据展现 -->
<div vtype="formpanel" wid="${(config.configId)!}" name="${(config.configName)!}_Form" id="${(config.configName)!}_Form" 
	titleDisplay="true" title="<img src='${(other.separator)!}static/images/info-white.png'> ${(config.configTitle)!}" width="1000" 
    layout="table" layoutconfig="{cols:1, columnwidth: ['100%']}" style="margin: 0 auto">
	
<#if config?exists && config.columns?exists && (config.columns?size>0)>
<#list config.columns as column>
		<#if ((column.columnFieldType)!)=="text">
	<div name="${(column.columnName)!}" vtype="textfield" label="${(column.columnTitle)!}" labelwidth="142" height="46" labelalign="right" width="${(column.columnWidth)!"700"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="combox">
	<div name="${(column.columnName)!}" vtype="comboxfield" label="${(column.columnTitle)!}" labelwidth="142" height="46" labelalign="right" width="${(column.columnWidth)!"700"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" dataurl="<#if ((column.columnDict)!)!="">${(other.separator)!}dictionary/queryData.do?dicId=${(column.columnDict)!}</#if>" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="date">
	<div name="${(column.columnName)!}" vtype="datefield" label="${(column.columnTitle)!}"  labelwidth="142" height="46" labelalign="right" width="${(column.columnWidth)!"700"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="number">
	<div name="${(column.columnName)!}" vtype="numberfield" label="${(column.columnTitle)!}" labelwidth="142" height="46" labelalign="right" width="${(column.columnWidth)!"700"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="textarea">
	<div name="${(column.columnName)!}" vtype="textareafield" label="${(column.columnTitle)!}" labelwidth="142" height="46" labelalign="right" width="${(column.columnWidth)!"700"}" height="110" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="hide">
	<div name="${(column.columnName)!}" vtype="hiddenfield" label="${(column.columnTitle)!}" labelwidth="142" height="46" labelalign="right" width="${(column.columnWidth)!"700"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="password">
	<div name="${(column.columnName)!}" vtype="passwordfield" label="${(column.columnTitle)!}" labelwidth="142" height="46" labelalign="right" width="${(column.columnWidth)!"700"}" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
		<#if ((column.columnFieldType)!)=="radio">
	<div name="${(column.columnName)!}" vtype="radiofield" label="${(column.columnTitle)!}" labelwidth="142" height="46" labelalign="right" width="${(column.columnWidth)!"700"}" dataurl="[{checked: true,value: '1',text: '是'},{value: '2',text: '否'}]" tooltip="${(column.columnTips)!}" rule="${(column.columnRule)!}" readonly="<#if ((column.columnEditable)!)=="0">true</#if>"></div>
		</#if>
</#list>
	<div name="${(fbo.functionName)!}_listToolbar" vtype="toolbar" align="center">
		<div id="${(fbo.functionName)!}_save_button" name="${(fbo.functionName)!}_save_button" vtype="button" text="保&nbsp;存" 
			defaultview="2" align="center" iconurl="${(other.separator)!}static/images/save.png" iconalign="left">
		</div>
		<div id="${(fbo.functionName)!}_reset_button" name="${(fbo.functionName)!}_reset_button" vtype="button" text="重&nbsp;置" 
			defaultview="2" align="left" iconurl="${(other.separator)!}static/images/reset.png" iconalign="left" style="float: right">
		</div>
	</div>
</#if>
</div>