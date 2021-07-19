define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
		queryForm_datarender : function (event,obj){
	},

	query_fromNames : [ 'queryForm_Form'],


	query_license2Sure : function(){
					$("div[name='queryForm_Grid']").gridpanel("option", "dataurl","../../../../torch/service.do?fid=3922320F48262C42E055000000000001&wid=3922320F48272C42E055000000000001");
					$("div[name='queryForm_Grid']").gridpanel("query", [ "queryForm_Form"]);
			},
	query_license2Reset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
		license2Edit: function(){
	//				alert("1111");
			var selectRules = $("div[name='queryForm_Grid']").gridpanel('getSelectedRowData');
			if(selectRules.length>1){
				jazz.warn("只能编辑一条数据");
				return;
			}
			if(selectRules.length==0){
				jazz.warn("请选择需要操作的数据");
				return;
			}
			var licConfigId = selectRules[0].licConfigId;
		    //alert(editId);
			util.openWindow("edit","修改经营许可","license2Edit.html?licConfigId="+licConfigId,1000,500);
		},
		
		license2Insert : function(){
				util.openWindow("insert","新增经营许可","license2Insert.html",1000,500);
			},
		license2Delete : function(){
			var selectedId = $("div[name = 'queryForm_Grid']").gridpanel('getSelectedRowData');
			if(selectedId.length > 1){
				jazz.warn("只能删除一条数据!");
				return;
			}
			var delId = selectedId[0].licConfigId;
			$.ajax({
				url : '../../../torch/service.do?fid=394E8213E4870EDEE055000000000001&licConfigId='+delId,
				type:"post",
				dataType : "json",
				async : false,
				success: function(data){
					jazz.info('删除成功!',function(){
						location.reload();
					});
				},
				error : function(data){
					jazz.warn('删除失败 请重试!');
				}
			});
		},
		licenseQuery : function(event){
			 var code = event.keyCode ;
			 if(code == "13"){
				 torch.query_license2Sure();
			 }
		 },

	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
	    	document.onkeydown = torch.licenseQuery;
			$("div[name='_Form']").gridpanel("option","datarender",torch.queryForm_datarender);
			$("div[name='_Form']").gridpanel("reload");
		 
			$("div[name='query_license2_query_button']").off('click').on('click',torch.query_license2Sure);
			$("div[name='query_license2_reset_button']").off('click').on('click',torch.query_license2Reset);
			$("div[name='license2Edit_update_button']").off('click').on('click',torch.license2Edit);
			$("div[name='license2Edit_insert_button']").off('click').on('click',torch.license2Insert);
			$("div[name='license2Delet_button']").off('click').on('click',torch.license2Delete);
		 });
		});
	 }
};
torch._init();
return torch;

});
