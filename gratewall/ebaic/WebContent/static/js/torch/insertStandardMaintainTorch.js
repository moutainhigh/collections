define(['require', 'jquery', 'common','util'], function(require, $, common,util){
var torch = {
	
	edit_primaryValues : function(){
	    return "&sjId=" + jazz.util.getParameter('sjId')
	},
	edit_fromNames : [ 'insertManageScope_Form'],

	biaoZhunYongYuWeiHuSave : function(){
				var params={		 
						 url:"../../../torch/service.do?fid=37B99F7C0F853A45E055000000000001"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功",function(){
								util.closeWindow("queryStandardMaintain.html",true);
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
	insertManagerScopeReturn : function(){
		util.closeWindow("queryStandardMaintain",true);
	},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
		 
			$("div[name='biaoZhunYongYuWeiHu_save_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuSave);
			$("div[name='biaoZhunYongYuWeiHu_reset_button']").off('click').on('click',torch.biaoZhunYongYuWeiHuReset);
			$("div[name='biaoZhunYongYuWeiHu_insert_button']").off('click').on('click',torch.insertManagerScopeReturn);
			
			$("div[name = 'fdm']").comboxfield('option','dataurl','../../../dmj/querySortStandardMaintain.do?');
		 });
		});
	 }
};
torch._init();
return torch;

});
