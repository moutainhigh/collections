define(['require','jquery', 'jazz'], function(require, $, jazz){
	var torch = {
		_init : function(){
			require(['domReady'], function (domReady) {
				domReady(function () {
					torch.applySetupInvCpEditQuery();
				});//enf domReady
			});
			
		},
		
		query_fromNames : [ 'applySetupInvCpStagespayList_Form'],
		edit_primaryValues : function(){return "&investorId="+jazz.util.getParameter('investorId')},
		edit_fromNames : [ 'applySetupInvCpSave_Form'],
		
		applySetupInvCpEditQuery : function(){
			var updateKey= "&wid=applySetupInvCpSave";
			$.ajax({
					url:'../../../torch/service.do?fid=applySetupInvCpEdit'+updateKey + torch.edit_primaryValues(),
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
						 //地址联级，获取省份值来设置市下拉框	
						 if(jsonData.length == 1){
							 
					  		var domProv = jsonData[0].data.prov;
					  		if(domProv){
					  			var dataUrl = '../../../dmj/queryDomCities.do?provinceZipCode=' + domProv;
					    		$("#city").comboxfield('option', 'dataurl',dataUrl);
					    		$("#city").comboxfield('reload');
					  		}
				    		
						 }
					}
				});
		},
		
		applySetupInvCpEditSave : function(){
			var params={		 
					 url:"../../../torch/service.do?fid=applySetupInvCpEdit"+torch.edit_primaryValues(),
					 components: torch.edit_fromNames,
					 callback: function(jsonData,param,res){
						 jazz.info("保存成功");		
					 }
				};
				$.DataAdapter.submit(params);
		}

			
	}
	torch._init();
    return torch;
});


