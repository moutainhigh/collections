define(['require', 'jquery', 'approvecommon'], function(require, $, approvecommon){
var torch = {
		archiveLogForm_datarender : function (event,obj){
	},

	query_fromNames : [ 'archiveLogForm_Form'],


	queryArchiveLogSure : function(){
		if($("div[name='archiveLogForm_Form']").formpanel('validate')){
			$("div[name='archiveLogForm_Grid']").gridpanel("option", "dataurl","../../../../torch/service.do?fid=38BE7D47F6081987E055000000000001&wid=38BE7D47F6091987E055000000000001");
			$("div[name='archiveLogForm_Grid']").gridpanel("query", [ "archiveLogForm_Form"]);
		}
	},
	queryArchiveLogReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
			$("div[name='_Form']").gridpanel("option","datarender",torch.archiveLogForm_datarender);
			$("div[name='_Form']").gridpanel("reload");
		 
			$("div[name='queryArchiveLog_query_button']").off('click').on('click',torch.queryArchiveLogSure);
			$("div[name='queryArchiveLog_reset_button']").off('click').on('click',torch.queryArchiveLogReset);
			$("div[name='archiveLogForm_Grid']").gridpanel("option", "datarender",function(obj,resultData){
				var dataArrs = resultData.data;
				if(dataArrs.length>0){
					for(var i=0;i<dataArrs.length;i++){
						if(dataArrs[i].state==1){
							dataArrs[i].state = "成功";
						}
						if(dataArrs[i].state==0){
							dataArrs[i].state = "失败";
						}
					}
				}
			});
		 });
		});
	 }
};
torch._init();
return torch;

});
