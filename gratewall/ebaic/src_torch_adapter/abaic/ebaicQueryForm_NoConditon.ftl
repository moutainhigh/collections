
<article style="width:1000px;margin:0 auto">
<!-- 数据展现 -->
	<div vtype="gridpanel" name="${(config.configName)!}_Grid" wid="queryAssign"
		titleDisplay="true" title="<img src='${(other.separator)!}static/images/inv-white.png' style='margin-right:10px'>${(config.configTitle)!}" width="1000" showborder="true"
		lineno="false" datarender="" dataurl="${(other.separator)!}torch/service.do?fid=${(config.functionId)!}&wid=${(config.configId)!}" 
		selecttype="0" linestyle="0" style="margin-bottom:10px">
		<div vtype="gridcolumn" name="grid_column" width="100%" hideheader=true>
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
	<div name="${(fbo.functionName)!}Button" align="center" colspan="2">
		<div id="${(fbo.functionName)!}_save_button" name="${(fbo.functionName)!}_save_button" vtype="button" text="上一步"
			defaultview="2" align="center" iconurl="${(other.separator)!}static/images/backWhite.png">
		</div>
		<div id="${(fbo.functionName)!}_reset_button" name="${(fbo.functionName)!}_reset_button" vtype="button" text="下一步"
			defaultview="2" align="center" iconurl="${(other.separator)!}static/images/nextWhite.png">
		</div>
	</div>
</article>