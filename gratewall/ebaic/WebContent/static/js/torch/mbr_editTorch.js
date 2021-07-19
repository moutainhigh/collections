define(['jquery', 'jazz','js/renderTemplate'], function($, jazz,renderTemplate){
var torch = {
	
	edit_primaryValues : "&entmemberId="+jazz.util.getParameter('entmemberId'),
	edit_fromNames : [ 'applySetupMbrEdit_Form'],



	applySetupMbrEditQuery : function(){
		var updateKey= "&wid=applySetupMbrEdit";
		$.ajax({
				url:'../../../../torch/service.do?fid=applySetupMbrEdit'+updateKey+torch.edit_primaryValues,
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


	applySetupMbrEditSave : function(){
				var params={		 
						 url:"../../../../torch/service.do?fid=applySetupMbrEdit"+torch.edit_primaryValues,
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功");		
						 }
					};
					$.DataAdapter.submit(params);
			},
	applySetupMbrEditReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){
		$("body").renderTemplate({templateName:'footer',insertType:'append'});
		$("body").renderTemplate({templateName:'header',insertType:'prepend'}); 
	 
		torch.applySetupMbrEditQuery();
		$("div[name='applySetupMbrEdit_save_button']").off('click').click(torch.applySetupMbrEditSave);
		$("div[name='applySetupMbrEdit_reset_button']").off('click').click(torch.applySetupMbrEditReset);
	 }
};
torch._init();
return torch;

});
