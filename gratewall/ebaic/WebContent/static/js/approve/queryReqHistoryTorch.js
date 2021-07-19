define(['require', 'jquery', 'approvecommon','util'], function(require, $, common,util){
var torch = {
		queryGrid_datarender : function (event,obj){
	},

	query_fromNames : [ 'queryGrid_Form'],


	queryReqHsSure : function(){
					$("div[name='queryGrid_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=queryReqHs&wid=queryReqHs");
					$("div[name='queryGrid_Grid']").gridpanel("query", [ "queryGrid_Form"]);
			},
	queryReqHsReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
			$("div[name='queryGrid_Grid']").gridpanel("option","datarender",torch.queryGrid_datarender);
			$("div[name='queryGrid_Grid']").gridpanel("reload");
		 
			$("div[name='queryReqHs_query_button']").off('click').on('click',torch.queryReqHsSure);
			$("div[name='queryReqHs_reset_button']").off('click').on('click',torch.queryReqHsReset);
			
			
			$("div[name='showdDetail']").on('click',torch.showDetail);
		 });
		});
	 },
	 
	 showDetail:function(){
		 var rowData = $("div[name='queryGrid_Grid']").gridpanel('getSelectedRowData');
	 		if(rowData.length==0){
	 			jazz.warn("请选择需要查看的数据!");
	 			return false;
	 		}
	 		var gid = rowData[0].gid;
	 		
	 		//这里引入iframe的高度过高
	 		window.location.href="../../../page/approve/reqhistory/reqHistoryTab.html?gid="+gid;
	 }		
			
};
torch._init();
return torch;

});
