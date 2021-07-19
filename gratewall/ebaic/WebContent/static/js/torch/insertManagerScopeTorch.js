define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
	
	edit_primaryValues : function(){
	    return "&sjId=" + jazz.util.getParameter('sjId')
	},
	edit_fromNames : [ 'insertManagerScope_Form'],





	insertManagerScopeSave : function(){
				var params={		 
						 url:"../../../torch/service.do?fid=3794907362E1517DE055000000000001"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功",function(){
								 util.closeWindow("queryManageScope.html",true);
							 });		
						 }
					};
					$.DataAdapter.submit(params);
			},
	insertManagerScopeReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
			
	insertManagerScopeReset : function(){
		util.closeWindow("queryManageScope",true);
	},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		 
			$("div[name='insertManagerScope_save_button']").off('click').on('click',torch.insertManagerScopeSave);
			$("div[name='insertManagerScope_reset_button']").off('click').on('click',torch.insertManagerScopeReset);
			$("div[name='insertManageScope_return_button']").off('click').on('click',torch.insertManagerScopeReset);
		 });
		});
	 }
};
torch._init();
return torch;

});
