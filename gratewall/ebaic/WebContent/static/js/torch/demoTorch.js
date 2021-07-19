define(['jquery', 'jazz', 'common'], function($, common){
	var demoTorch = {

		query_fromNames : [ 'queryGrid_Form'],
    	approve_censor_listSure : function(){
						$("div[name='queryGrid_Grid']").gridpanel("option", "dataurl","/ebaic/torch/service.do?fid=query_assign&wid=queryAssign");
						$("div[name='queryGrid_Grid']").gridpanel("query", [ "queryGrid_Form"]);
				},
		approve_censor_listReset : function(){
					for( x in demoTorch.query_fromNames){
						$("div[name='"+demoTorch.query_fromNames[x]+"']").formpanel("reset");
					} 
				},
		aa : function(){
			alert(demoTorch.query_fromNames);
		}
	}
    return demoTorch;

});

