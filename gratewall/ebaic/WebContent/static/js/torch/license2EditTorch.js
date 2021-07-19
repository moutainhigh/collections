define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
	
	edit_primaryValues : function(){
	    return "&licConfigId=" + jazz.util.getParameter('licConfigId')
	},
	edit_fromNames : [ 'licenseupdate_Form'],



	license2EditQuery : function(){
		var updateKey= "&wid=39274CD31F4078F6E055000000000001";
		$.ajax({
				url:'../../../../torch/service.do?fid=3922320F48262C42E055000000000001'+updateKey+torch.edit_primaryValues(),
				type:"post",
				async:false,
				dataType:"json",
				success: function(data){
					var jsonData = data.data;
					if($.isArray(jsonData)){
						 for(var i = 0, len = jsonData.length; i<len; i++){
							 $("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
						 }
					 }
				}
			});
	},


	license2EditSave : function(){
				var params={		 
						 url:"../../../../torch/service.do?fid=3922320F48262C42E055000000000001"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功",function(){
								 util.closeWindow("edit",true);
							 });		
						 }
					};
					$.DataAdapter.submit(params);
			},
	license2EditReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		 
			torch.license2EditQuery();
			$("div[name='license2Edit_save_button']").off('click').on('click',torch.license2EditSave);
			$("div[name='license2Edit_reset_button']").off('click').on('click',torch.license2EditReset);
		 });
		});
	 }
};
torch._init();
return torch;

});
