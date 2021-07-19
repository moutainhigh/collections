define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
		queryGrid_datarender : function (event,obj){
	},

	query_fromNames : [],


	queryManageScopeSure : function(){
					$("div[name='queryGrid_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=37826A2DF0DB48FBE055000000000001&wid=37826A2DF0DC48FBE055000000000001");
					$("div[name='queryGrid_Grid']").gridpanel("query", [ "queryGrid_Form"]);
			},
	queryManageScopeReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	
	//编辑功能
	queryManageScopeEditFun : function(){
		var selectedRules = $("div[name = 'queryGrid_Grid']").gridpanel('getSelectedRowData');
		if(selectedRules.length>1){
			jazz.warn("只能编辑一条数据!");
			return;
		}
		if(selectedRules.length == 0){
			jazz.warn("请选择要操作的数据.");
			return;
		}
		var editId = selectedRules[0].sjId;
		
		util.openWindow("edit","修改经营范围信息","editManageScope.html?sj_id="+editId,1200,500);
	},
	
	
	//新增功能
	queryManageScopeInsertFun : function(){
		util.openWindow("insert","新增经营范围信息","insertManagerScope.html",1200,500);
	},
			
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
			$("div[name='_Form']").gridpanel("option","datarender",torch.queryGrid_datarender);
			$("div[name='_Form']").gridpanel("reload");
		 
			$("div[name='queryManageScope_query_button']").off('click').on('click',torch.queryManageScopeSure);
			$("div[name='queryManageScope_reset_button']").off('click').on('click',torch.queryManageScopeReset);
			$("div[name='queryManageScope_edit_button']").off('click').on('click',torch.queryManageScopeEditFun);
			$("div[name='queryManageScope_insert_button']").off('click').on('click',torch.queryManageScopeInsertFun);
		 });
		});
	 }
};
torch._init();
return torch;

});
