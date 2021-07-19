define(['require', 'jquery','util'], function(require, $,util){
var torch = {

	_init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
	    	torch.loadInfo();
			$("div[name='processSubConAmDetail_return_button']").off('click').on('click',torch.closeDetailWindow);
		 });
		});
	 },
	 
	 loadInfo : function(){
		 	$("div[name='processSubConAmDetail_Grid']").gridpanel("option", "dataurl", "../../../../torch/service.do?fid=processSubConAmDetail&wid=processSubConAmDetail&investorId=" + jazz.getParameter("investorId"));
		 	$("div[name='processSubConAmDetail_Grid']").gridpanel("reload");
	 },
	 
	 closeDetailWindow : function(){
		 util.closeWindow('conAmDetailW',false);
	 }
};
torch._init();
return torch;

});
