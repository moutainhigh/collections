define(['require', 'jquery', 'common'], function(require, $, common){
var torch = {
	
	edit_primaryValues : function(){
	    return "&gid=" + jazz.util.getParameter('gid')
	},
	edit_fromNames : [ 'applyPaLeAuth_Form'],



	applyPaLeAuthQuery : function(){
		var updateKey= "&wid=applyPaLeAuth";
		$.ajax({
				url:'../../../../../torch/service.do?fid=applyPaLeAuth'+updateKey+torch.edit_primaryValues(),
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


	applyPaLeAuthSave : function(){
				var params={		 
						 url:"../../../../../torch/service.do?fid=applyPaLeAuth"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功");		
						 }
					};
					$.DataAdapter.submit(params);
			},
	applyPaLeAuthReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		 
			torch.applyPaLeAuthQuery();
			$("div[name='applyPaLeAuth_save_button']").off('click').on('click',torch.applyPaLeAuthSave);
			$("div[name='applyPaLeAuth_reset_button']").off('click').on('click',torch.applyPaLeAuthReset);
		 });
		});
	 }
};
torch._init();
return torch;

});
