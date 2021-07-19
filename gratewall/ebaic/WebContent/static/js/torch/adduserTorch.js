define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
	
	edit_primaryValues : function(){
	    return "&id=" + jazz.util.getParameter('id')
	},
	edit_fromNames : [ 'adduser_Form'],





	adduserSave : function(){
				var params={		 
						 url:"../../../torch/service.do?fid=37969B4B7D9D6A3EE055000000000001"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功",function(){
								 util.closeWindow("add",true);
							 });		
						 }
					};
					$.DataAdapter.submit(params);
			},
	adduserReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		 
			$("div[name='adduser_save_button']").off('click').on('click',torch.adduserSave);
			$("div[name='adduser_reset_button']").off('click').on('click',torch.adduserReset);
		 });
		});
	 }
};
torch._init();
return torch;

});
