define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
	
	edit_primaryValues : function(){
	    return "&sjId=" + jazz.util.getParameter('sj_id')
	},
	edit_fromNames : [ 'editManageScope_Form'],

	biaoZhunYongYuWeiHuQuery : function(){
		var updateKey= "&wid=37B99F7C0F803A45E055000000000001";
		$.ajax({
				url:'../../../torch/service.do?fid=37B99F7C0F7F3A45E055000000000001'+updateKey+torch.edit_primaryValues(),
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


	biaoZhunYongYuWeiHuSave : function(){
				var params={		 
						 url:"../../../torch/service.do?fid=37B99F7C0F7F3A45E055000000000001"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功",function(){
								 util.closeWindow("queryStandardMaintain",true);
							 });		
						 }
					};
					$.DataAdapter.submit(params);
			},
	biaoZhunYongYuWeiHuReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	biaoZhunYongYuWeiHuReturn : function(){
			util.closeWindow("queryStandardMaintain",true);
	},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		 
			torch.biaoZhunYongYuWeiHuQuery();
			$("div[name='biaoZhunYongYuWeiHu_save_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuSave);
			$("div[name='biaoZhunYongYuWeiHu_reset_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuReset);
			$("div[name='biaoZhunYongYuWeiHu_return_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuReturn);
		 });
		});
	 }
};
torch._init();
return torch;

});
