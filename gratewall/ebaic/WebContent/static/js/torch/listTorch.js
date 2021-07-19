define(['jquery', 'jazz','js/renderTemplate'], function($, jazz,renderTemplate){
var torch = {
		queryBusiListForIndex_datarender : function (event,obj){
	},

	query_fromNames : [ 'queryBusiListForIndex_Form'],


	lxqtestSure : function(){
					$("div[name='queryBusiListForIndex_Grid']").gridpanel("option", "dataurl","../../../../../torch/service.do?fid=lxqtest&wid=lxq_get_list");
					$("div[name='queryBusiListForIndex_Grid']").gridpanel("query", [ "queryBusiListForIndex_Form"]);
			},
	lxqtestReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){
		$("body").renderTemplate({templateName:'footer',insertType:'append'});
		$("body").renderTemplate({templateName:'header',insertType:'prepend'}); 
		$("div[name='_Form']").gridpanel("option","datarender",torch.queryBusiListForIndex_datarender);
		$("div[name='_Form']").gridpanel("reload");
	 
		$("div[name='lxqtest_query_button']").off('click').click(torch.lxqtestSure);
		$("div[name='lxqtest_reset_button']").off('click').click(torch.lxqtestReset);
	 }
};
torch._init();
return torch;

});
