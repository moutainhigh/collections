define(['require','jquery','common','../widget/Address'], function(require, $, common){
	var torch ={};
	torch = {
			_init : function(){
				require(['domReady'], function (domReady) {
					domReady(function () {
						torch.applySetupInvPersonEditQuery();
//						$("div[name='applySetupInvPersonEdit_query_button']").on('click',torch.applySetupInvPersonEditSave);
					});//enf domReady
				});
				
				
			},
			
			query_fromNames : [ 'applySetupInvPersonCon_Form'],
			edit_primaryValues : function(){return "&gid="+jazz.util.getParameter('gid')+"&investorId="+jazz.util.getParameter('investorId');},
			edit_fromNames : [ 'applySetupInvPersonSave_Form'],
			
			applySetupInvPersonEditQuery : function(){
			var updateKey= "&wid=applySetupInvPersonSave";
				$.ajax({
						url:'../../../torch/service.do?fid=applySetupInvPersonEdit'+updateKey+torch.edit_primaryValues(),
						type:"post",
						async:false,
						dataType:"json",
						success: function(data){
						    var jsonData = data.data;
							if($.isArray(jsonData)){
								 for(var i = 0, len = jsonData.length; i<len; i++){
									 $("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
																			
								 }
								 /**户籍登记地址**/
					    		 $("#address").Address({provCtrlId:'prov',cityCtrlId:'city'},"",{proValue:jsonData[0].data.prov,cityValue:jsonData[0].data.city});
							 }
						}
				});
			},
			
			applySetupInvPersonEditSave : function(){
				var params={		 
						 url:"../../../torch/service.do?fid=applySetupInvPersonEdit"+torch.edit_primaryValues(),
						 components: torch.edit_fromNames,
						 callback: function(jsonData,param,res){
							 jazz.info("保存成功");		
						 }
					};
					$.DataAdapter.submit(params);
			}
		
			
	};
	torch._init();
    return torch;
});


