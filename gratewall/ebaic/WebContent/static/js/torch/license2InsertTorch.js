define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
	
	edit_primaryValues : function(){
	    return "&licConfigId=" + jazz.util.getParameter('licConfigId')
	},
	edit_fromNames : [ 'insertLicence_Form'],

	license2InsertSave : function(){
				var params={		 
						 url:"../../../torch/service.do?fid=3927EBDB2CCD336AE055000000000001"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功",function(){
								 util.closeWindow("license2",true);
							 });
						 }
					};
					$.DataAdapter.submit(params);
			},
	license2InsertReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		 
			$("div[name='license2Insert_save_button']").off('click').on('click',torch.license2InsertSave);
			$("div[name='license2Insert_reset_button']").off('click').on('click',torch.license2InsertReset);
		 });
		});
	 }
};
torch._init();
return torch;

});
