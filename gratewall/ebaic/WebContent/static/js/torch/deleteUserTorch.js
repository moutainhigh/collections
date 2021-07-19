define(['require', 'jquery', 'common'], function(require, $, common){
var torch = {
		deleteUser_datarender : function (event,obj){
	},

	query_fromNames : [ 'deleteUser_Form'],


	deleteUserSure : function(){
					$("div[name='deleteUser_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=37A36066F9F16112E055000000000001&wid=37A36066F9F26112E055000000000001");
					$("div[name='deleteUser_Grid']").gridpanel("query", [ "deleteUser_Form"]);
			},
	deleteUserReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
			$("div[name='_Form']").gridpanel("option","datarender",torch.deleteUser_datarender);
			$("div[name='_Form']").gridpanel("reload");
		 
			$("div[name='deleteUser_query_button']").off('click').on('click',torch.deleteUserSure);
			$("div[name='deleteUser_reset_button']").off('click').on('click',torch.deleteUserReset);
		 });
		});
	 }
};
torch._init();
return torch;

});
