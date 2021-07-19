define(['require', 'jquery', 'common'], function(require, $, common){
var torch = {
		queryForm_datarender : function (event,obj){
	},

	query_fromNames : [ 'queryForm_Form'],


	approvalReceive_listSure : function(){
					$("div[name='queryForm_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=5187ecb80fae47e0ac739f8edbe6c643&wid=33CB2653196B66F1E055000000000001");
					$("div[name='queryForm_Grid']").gridpanel("query", [ "queryForm_Form"]);
			},
	approvalReceive_listReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
			$("div[name='_Form']").gridpanel("option","datarender",torch.queryForm_datarender);
			$("div[name='_Form']").gridpanel("reload");
		 
			$("div[name='approvalReceive_list_query_button']").off('click').on('click',torch.approvalReceive_listSure);
			$("div[name='approvalReceive_list_reset_button']").off('click').on('click',torch.approvalReceive_listReset);
		 });
		});
	 }
};
torch._init();
return torch;

});
