define(['jquery', 'jazz','js/renderTemplate'], function($, jazz,renderTemplate){
var torch = {
	
	edit_primaryValues : "&userId="+jazz.util.getParameter('userId'),
	edit_fromNames : [ 'userInfo_Form'],





	lxqTestFuncSave : function(){
				var params={		 
						 url:"../../../../../torch/service.do?fid=lxqTestFunc"+torch.edit_primaryValues,
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功");		
						 }
					};
					$.DataAdapter.submit(params);
			},
	lxqTestFuncReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){
		$("body").renderTemplate({templateName:'footer',insertType:'append'});
		$("body").renderTemplate({templateName:'header',insertType:'prepend'}); 
	 
		$("div[name='lxqTestFunc_save_button']").off('click').click(torch.lxqTestFuncSave);
		$("div[name='lxqTestFunc_reset_button']").off('click').click(torch.lxqTestFuncReset);
	 }
};
torch._init();
return torch;

});
