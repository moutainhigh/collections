define(['require', 'jquery', 'common', 'util'], function(require, $, common,util){
var torch = {
		applySetupScope_datarender : function (event,obj){
	},

	query_fromNames : [ 'applySetupScope_Form'],


	applySetupScopeSure : function(){
					$("div[name='applySetupScope_Grid']").gridpanel("option", "dataurl","../../../../torch/service.do?fid=applySetupScope&wid=applySetupScope");
					$("div[name='applySetupScope_Grid']").gridpanel("query", [ "applySetupScope_Form"]);
			},
	submit : function(){
		
		if(!window.parent){
			alert('当前页面只能从经营范围编辑页面打开。');
			return ;
		}
		if(!window.parent.Scope){
			alert('父页面没有Scope变量。');
			return ;
		}
		
		var fcodes = $("input[name='selectedScopefCodeInput']").val() || '';
		var codes = $("input[name='selectedScopeCodeInput']").val() || '';
		var names = $("#selectedScopeNameValue").text() || '';
		
		var fcodeArray = fcodes.split(',');
		var codeArray = codes.split(',');
		var nameArray = names.split('、');
		if(!codeArray){
			codeArray = []; 
		}
		if(!$.isArray(codeArray)){
			codeArray = [];
		}
		
		var ret = [];
		var fret = [];
		
		for(var i=0,len=codeArray.length;i<len;++i){
			var fcode = fcodeArray[i];
			var code = codeArray[i];
			var name = nameArray[i];
			if(code && name && fcode){
				var zscope = {name:name, code:code,group:fcode };
				ret.push(zscope);
			}
		}
		fret.push(ret);
		window.parent.Scope.setCommonScope(fret);
		$('.jazz-titlebar-icon-close',window.parent.document).click();
	},
	cancel : function(){
		util.closeWindow("moreScope",false);
	},
	
	/**
	 * 初始化。
	 */
	initScope : function(inputScopes){
		var scopes = inputScopes || [];
		if(!$.isArray(scopes)){
			scopes = [];
		}
		var codes='',names='',fcodes='';
		var first = true;
		var scope = scopes[0];
		if(!$.isArray(scope)){
			scope = [];
		}
		for(var i=0,len=scope.length;i<len;++i){
			var o = scope[i];
			if(!o){continue;}
			if(!o.name){continue;}
			if(!o.code){continue;}
			if(!o.group){continue;}
			if(first){
				first = false;
				fcodes+= o.group;
				codes += o.code ;
				names += o.name ;
			}else{
				fcodes += ',' + o.group ;
				codes += ',' + o.code ;
				names += '、'+ o.name ;
			}
		}

		$("input[name='selectedScopefCodeInput']").val(fcodes);
		$("input[name='selectedScopeCodeInput']").val(codes);
		$("#selectedScopeNameValue").text(names);
		
		$(".jazz-grid-headerCell-box").hide();
	},
	scopeForm_datarender : function(item,rowsdata) {
		var data = rowsdata.data;
		if(!data){
			return data;
		}
		for (var i = 0; i < data.length; i++) {
			var row = data[i];
			
			var fcode = row["fdm"] || '';
			data[i]["fdm"] = "<span name='fdm'>"+fcode+"</span>";
			
			var code = row["dm"] || '';
			data[i]["dm"] = "<span name='dm'>"+code+"</span>";
			
			var text = row["wb"] || '';
			data[i]["wb"] = "<span name='wb'>"+text+"</span>";
		}
		return data;
	},
	
	/**
	 * 设置checkbox的选中
	 * shiyameng
	 */
	selectCheckbox: function(){
		var codes = $("input[name='selectedScopeCodeInput']").val() || '';
		$('span[name = "dm"]').each(function(index,item){
			var code = $(item).text();
			if(codes.indexOf(code) != -1){
				var checkbox = $(item).parent().prevAll('.jazz-grid-cell-box').eq(0);
				checkbox.click();
			}
		})
	},
	
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
	    	
			$("div[name='applySetupScope_Grid']").gridpanel("option","dataurl","../../../../torch/service.do?fid=applySetupScope&wid=applySetupScope");
			$("div[name='applySetupScope_Grid']").gridpanel("option","datarender",torch.scopeForm_datarender);
			$('div[name="grid_paginator"]').paginator("option","gopage",torch.selectCheckbox);
			$("div[name='applySetupScope_Grid']").gridpanel("reload",null,torch.selectCheckbox);
		 
			$('#applySetupScope_query_button').off('click').on('click',torch.applySetupScopeSure);
			$('#applySetupScope_submit_button').off('click').on('click',torch.submit);
			$('#applySetupScope_cancel_button').off('click').on('click',torch.cancel);
			
			var inputScopes = [];
			if(window.parent){
				if(window.parent.Scope){
					if(window.parent.Scope.getCommonScope){
						inputScopes = window.parent.Scope.getCommonScope();
					}
				}
			}
			torch.initScope(inputScopes);
			
			$("div[name='applySetupScope_Grid']").find("input[type='checkbox']").live('click',function(){
				var checked = $(this).attr('checked')=='checked';
				var fcode = $(this).parent().parent().find("span[name='fdm']").text() || '';
				var code = $(this).parent().parent().find("span[name='dm']").text() || '';
				var name = $(this).parent().parent().find("span[name='wb']").text() || '';
				if(!code){
					return ;
				}
				if(!fcode){
					return;
				}
				var fcodes = $("input[name='selectedScopefCodeInput']").val() || '';
				var codes = $("input[name='selectedScopeCodeInput']").val() || '';
				var names = $("#selectedScopeNameValue").text() || '';
				
				var codesJudge = ',' + codes + ',';
				
				if(checked){
					// 设置为选中
					var pos = codesJudge.indexOf(code);
					if(pos>-1){ // 已经存在
						return ;
					}
					if(codes){//原来不为空，则追加
						fcodes = fcodes + ',' + fcode;
						codes = codes + ',' + code ;
						names = names + '、' + name ;
					}else{//原来为空，则设值
						fcodes = fcode;
						codes = code ;
						names = name ;
					}
				}else{
					// 设置为取消选中
					var pos = codesJudge.indexOf(code);
					if(pos<0){ // 不存在
						return ;
					}
					if(!codes){//原来为空
						return
					}
					//group
					fcodes = fcodes.replace(',' + fcode + ','      ,     ',');
					fcodes = fcodes.replace( fcode + ','           ,     '');
					fcodes = fcodes.replace(',' + fcode            ,     '');
					fcodes = fcodes.replace( fcode                 ,     '');
					
					codes = codes.replace(',' + code + ','      ,     ',');
					codes = codes.replace( code + ','           ,     '');
					codes = codes.replace(',' + code            ,     '');
					codes = codes.replace( code                 ,     '');
					
					names = names.replace('、' + name + '、'    ,     '、');
					names = names.replace( name + '、'          ,     '');
					names = names.replace('、' + name           ,     '');
					names = names.replace(name                  ,     '');
				}
				
				$("input[name='selectedScopefCodeInput']").val(fcodes);
				$("input[name='selectedScopeCodeInput']").val(codes);
				$("#selectedScopeNameValue").text(names);
				
			});
			
		 });
		});
	 }
};
torch._init();
return torch;

});
