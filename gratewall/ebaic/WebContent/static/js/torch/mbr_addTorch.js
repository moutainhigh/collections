define(['jquery', 'jazz','js/renderTemplate'], function($, jazz,renderTemplate){
var torch = {
	
	edit_primaryValues : "&entmemberId="+jazz.util.getParameter('entmemberId'),
	edit_fromNames : [ 'applySetupMbrAdd_Form'],





	applySetupMbrAddSave : function(){
				var params={		 
						 url:"../../../../torch/service.do?fid=applySetupMbrAdd"+torch.edit_primaryValues,
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功");		
						 }
					};
					$.DataAdapter.submit(params);
			},
	applySetupMbrAddReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){
		$("body").renderTemplate({templateName:'footer',insertType:'append'});
		$("body").renderTemplate({templateName:'header',insertType:'prepend'}); 
	 
		$("div[name='applySetupMbrAdd_save_button']").off('click').click(torch.applySetupMbrAddSave);
		$("div[name='applySetupMbrAdd_reset_button']").off('click').click(torch.applySetupMbrAddReset);
	 }
};
torch._init();
return torch;

});
