define(['require', 'jquery', 'entCommon'], function(require, $, common){
var torch = {
		queryEntValidate_datarender : function (event,obj){
	},

	query_fromNames : [ 'queryEntValidate_Form'],


	queryEntValidateHsRecordSure : function(){
					$("div[name='queryEntValidate_Grid']").gridpanel("option","datarender",torch.queryEntValidate_datarender);
					$("div[name='queryEntValidate_Grid']").gridpanel("option", "dataurl","../../../../torch/service.do?fid=query_ent_validate_hs_record&wid=query_ent_validate_hs_record");
					$("div[name='queryEntValidate_Grid']").gridpanel("query", [ "queryEntValidate_Form"]);
			},
	queryEntValidateHsRecordReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	back_button : function(){
		window.location.href='../../../page/apply/ent_account/queryEntValidateRecord.html';
	},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
	    	torch.queryEntValidateHsRecordSure();
			$("div[name='_Form']").gridpanel("option","datarender",torch.queryEntValidate_datarender);
			$("div[name='_Form']").gridpanel("reload");
		 
			$("div[name='queryEntValidateHsRecord_query_button']").off('click').on('click',torch.queryEntValidateHsRecordSure);
			$("div[name='queryEntValidateRecord_reset_button']").off('click').on('click',torch.queryEntValidateHsRecordReset);
			$("div[name='queryEntValidateHsRecord_back_button']").off('click').on('click',torch.back_button);
		 });
		});
	 }
};
torch._init();
return torch;

});
