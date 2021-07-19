define(['require', 'jquery', 'common','util','validator'], function(require, $, common,util,validator){
var torch = {
	
	edit_primaryValues : function(){
		//alert(jazz.util.getParameter('sj_id'));
	    return "&sjId=" + jazz.util.getParameter('sj_id');
	},
	edit_fromNames : [ 'editManageScope_Form'],

	editManageScopeQuery : function(){
		var updateKey= "&wid=3790EDB46F5B52DFE055000000000001";
		$.ajax({
				url:'../../../torch/service.do?fid=3790EDB46F5A52DFE055000000000001'+updateKey+torch.edit_primaryValues(),
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


	editManageScopeSave : function(){
				var params={		 
						 url:"../../../torch/service.do?fid=3790EDB46F5A52DFE055000000000001"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功",function(){
								 util.closeWindow("queryManageScope",true);
							 });		
						 }
					};
					$.DataAdapter.submit(params);
			},
	editManageScopeReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
			
	editManageScopeReturn : function(){
		util.closeWindow("queryManageScope",true);
	},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		 
			torch.editManageScopeQuery();
			$("div[name='editManageScope_save_button']").off('click').on('click',torch.editManageScopeSave);
			$("div[name='editManageScope_reset_button']").off('click').on('click',torch.editManageScopeReset);
			$("div[name='editManageScope_return_button']").off('click').on('click',torch.editManageScopeReturn);
		 });
		});
	 }
};
torch._init();
return torch;

});
