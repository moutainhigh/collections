define(['require', 'jquery', 'common'], function(require, $, common){
var torch = {
	<#if queryList?exists && (queryList?size>0)>
	<#list queryList as query>
	<#if ((query.queryType)!)=='Grid'>
	${(query.configName)!}_datarender : function (event,obj){
	<#list query.columns as column>
	<#if ((column.columnFieldType)!)=="edit">
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var htm = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="" style="margin-right:5px;">'
						+ '<img src="${(other.separator)!}static/images/editGreen.png"></a><a href="javascript:void(0);" onclick="">'
						+ '<img src="${(other.separator)!}static/images/deleteRed.png"></a></div>';
			data[i]["${(column.columnName)!}"] = htm; 
		}
		return data;
	</#if>
	</#list>
	},
	</#if>
	</#list>

	query_fromNames : [<#list queryList as query><#if (query.conditions)?exists && ((query.conditions)?size>0)> '${(query.configName)!}_Form'<#if query_has_next>,</#if></#if></#list>],

	</#if>
	<#assign haveUpdate = false>
	<#if editList?exists && (editList?size>0)>

	edit_primaryValues : function(){
	    var result = "";
	    <#list editList as edit>
		if(jazz.util.getParameter('${(edit.primaryKey)!}')){
		result = result + "&${(edit.primaryKey)!p}=" + jazz.util.getParameter('${(edit.primaryKey)!}');
		}
		</#list>
		return result;
	},
	edit_fromNames : [<#list editList as edit> '${(edit.configName)!}_Form'<#if edit_has_next>,</#if></#list>],


	<#assign haveUpdate = false>
	<#list editList as edit><#if ((edit.editType)!)=="update"><#assign haveUpdate = true><#break></#if></#list>

	<#if haveUpdate>
	${(fbo.functionName)!}Query : function(){
		<#assign u_indx=0>
		var updateKey= "&wid=<#list editList as edit><#if ((edit.editType)!)=="update"><#if u_indx!=0>,</#if><#assign u_indx=u_indx+1>${(edit.configId)!p}</#if></#list>";
		$.ajax({
				url:'${(other.separator)!}torch/service.do?fid=${(fbo.functionId)!}'+updateKey+torch.edit_primaryValues(),
				type:"post",
				async:false,
				dataType:"json",
				success: function(data){
					var jsonData = data.data;
					if($.isArray(jsonData)){
						 for(var i = 0, len = jsonData.length; i<len; i++){
							 $("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
						 }
					 }
				}
			});
	},
	</#if>

	</#if>

	<#if editList?exists && (editList?size>0)>		
	${(fbo.functionName)!}Save : function(){
				var params={		 
						 url:"${(other.separator)!}torch/service.do?fid=${(fbo.functionId)!}"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(data,param,res){
							 if(data && data.name){
								$("div[name='"+data.name+"']").formpanel("setValue",data || {});
							 }
							 jazz.info("保存成功");		
						 }
					};
					$.DataAdapter.submit(params);
			},
	${(fbo.functionName)!}Reset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	<#else>
	${(fbo.functionName)!}Sure : function(){
			<#list queryList as query>
				<#if ((query.queryType)!)=='Grid'>
					$("div[name='${(query.configName)!}_Grid']").gridpanel("option", "dataurl","${(other.separator)!}torch/service.do?fid=${query.functionId}&wid=${query.configId}");
					$("div[name='${(query.configName)!}_Grid']").gridpanel("query", [ "${(query.configName)!}_Form"]);
				</#if>
				<#if ((query.queryType)!)=='Form'>
					$("div[name='${(query.configName)!}_Form']").formpanel("option","dataurl","${(other.separator)!}torch/service.do?fid=${query.functionId}&wid=${query.configId}<#if (query.conditions)?exists && ((query.conditions)?size>0)>&<#list query.conditions as condition>${((condition.conditionName)!)}={${((condition.conditionName)!)}}<#if condition_has_next>&</#if></#list></#if>");
					$("div[name='${(query.configName)!}_Form']").formpanel("reload");
				</#if>
			</#list>
			},
	${(fbo.functionName)!}Reset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	</#if>
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		<#if queryList?exists && (queryList?size>0)>
		<#list queryList as query>
		<#if ((query.queryType)!)=='Grid'>
			 $("div[name='${(query.configName)!}']").panel('addTitleButton', [ {
				id : "id_1",
				align : "right",
				icon : "${(other.separator)!}static/images/addicon.png",
				word : "增加",
				iconClass : "backColor",
				height:"16px",
				lineHeight:"16px",
				backgroundColor : "#79bf86",
				backgroundPositionX:"3px",
				backgroundPositionY:"5px",
				click : function(e, ui) {
					alert('这是个自定义按钮');
				}
			} ]);
			$("div[name='${(query.configName)!}_Grid']").gridpanel("option","datarender",torch.${(query.configName)!}_datarender);
			$("div[name='${(query.configName)!}_Grid']").gridpanel("reload");
		</#if>
		</#list>
		</#if>
		 
		<#if haveUpdate>
			torch.${(fbo.functionName)!}Query();
		</#if>
		<#if editList?exists && (editList?size>0)>
			$("div[name='${(fbo.functionName)!}_save_button']").off('click').on('click',torch.${(fbo.functionName)!}Save);
			$("div[name='${(fbo.functionName)!}_reset_button']").off('click').on('click',torch.${(fbo.functionName)!}Reset);
		<#else>
			$("div[name='${(fbo.functionName)!}_query_button']").off('click').on('click',torch.${(fbo.functionName)!}Sure);
			$("div[name='${(fbo.functionName)!}_reset_button']").off('click').on('click',torch.${(fbo.functionName)!}Reset);
		</#if>
		 });
		});
	 }
};
torch._init();
return torch;

});
