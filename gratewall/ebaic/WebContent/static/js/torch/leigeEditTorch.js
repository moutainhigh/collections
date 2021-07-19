define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
	
	edit_primaryValues : function(){
	    return "&id=" + jazz.util.getParameter('id')
	},
	edit_fromNames : [ 'bianji_Form'],



	leigeEditQuery : function(){
		var updateKey= "&wid=3781B9225C7A1C82E055000000000001";
		$.ajax({
				url:'../../../torch/service.do?fid=sdfsdf2123dfd'+updateKey+torch.edit_primaryValues(),
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


	leigeEditSave : function(){
				var params={		 
						 url:"../../../torch/service.do?fid=sdfsdf2123dfd"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功",function(){
								 util.closeWindow("edit",true);
							 });		
						 }
					};
					$.DataAdapter.submit(params);
			},
	leigeEditReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		 
			torch.leigeEditQuery();
			$("div[name='leigeEdit_save_button']").off('click').on('click',torch.leigeEditSave);
			$("div[name='leigeEdit_reset_button']").off('click').on('click',torch.leigeEditReset);
		 });
		});
	 }
};
torch._init();
return torch;

});
