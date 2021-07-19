define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
		queryGrid_datarender : function (event,obj){
	},

	query_fromNames : [ 'queryGrid_Form'],

	showReqHistorySure : function(){
					$("div[name='queryGrid_Grid']").gridpanel("option", "dataurl","../../../../torch/service.do?fid=showReqHistory&wid=showReqHistory&gid="+jazz.util.getParameter("gid"));
					$("div[name='queryGrid_Grid']").gridpanel("query", [ "queryGrid_Form"]);
			},
	showReqHistoryReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
	    	torch.showReqHistorySure();
			$("div[name='_Form']").gridpanel("option","datarender",torch.queryGrid_datarender);
			$("div[name='_Form']").gridpanel("reload");
		 
			$("div[name='showReqHistory_query_button']").off('click').on('click',torch.showReqHistorySure);
			$("div[name='showReqHistory_reset_button']").off('click').on('click',torch.showReqHistoryReset);
		 });
		});
	 }
};
torch._init();
return torch;

});
