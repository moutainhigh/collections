define(['jquery', 'jazz','js/renderTemplate'], function($, jazz,renderTemplate){
var torch = {

	query_fromNames : [ 'applySetupMbrDList_Form', 'applySetupMbrMList_Form', 'applySetupMbrSList_Form'],


	edit_primaryValues : "&gid="+jazz.util.getParameter('gid'),
	edit_fromNames : [ 'applySetupMbrSave_Form'],



	applySetupMbrQuery : function(){
		var updateKey= "&wid=applySetupMbrSave";
		$.ajax({
				url:'../../../../torch/service.do?fid=applySetupMbr'+updateKey+torch.edit_primaryValues,
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


	applySetupMbrSave : function(){
				var params={		 
						 url:"../../../../torch/service.do?fid=applySetupMbr"+torch.edit_primaryValues,
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功");		
						 }
					};
					$.DataAdapter.submit(params);
			},
	applySetupMbrReset : function(){
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){
		$("body").renderTemplate({templateName:'footer',insertType:'append'});
		$("body").renderTemplate({templateName:'header',insertType:'prepend'}); 
		$("div[name='applySetupMbrDList_Grid']").gridpanel("option","dataurl",'../../../../torch/service.do?fid=applySetupMbr&wid=applySetupMbrDList&gid='+jazz.util.getParameter('gid'));
		$("div[name='applySetupMbrDList_Grid']").gridpanel("reload");
		$("div[name='applySetupMbrMList_Grid']").gridpanel("option","dataurl",'../../../../torch/service.do?fid=applySetupMbr&wid=applySetupMbrMList&gid='+jazz.util.getParameter('gid'));
		$("div[name='applySetupMbrMList_Grid']").gridpanel("reload");
		$("div[name='applySetupMbrSList_Grid']").gridpanel("option","dataurl",'../../../../torch/service.do?fid=applySetupMbr&wid=applySetupMbrSList&gid='+jazz.util.getParameter('gid'));
		$("div[name='applySetupMbrSList_Grid']").gridpanel("reload");
	 
		torch.applySetupMbrQuery();
		$("div[name='applySetupMbr_save_button']").off('click').click(torch.applySetupMbrSave);
		$("div[name='applySetupMbr_reset_button']").off('click').click(torch.applySetupMbrReset);
	 }
};
torch._init();
return torch;

});
