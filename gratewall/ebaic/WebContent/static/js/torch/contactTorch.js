define(['require' ,'jquery', 'common'], function(require, $, common){

	var torch = {
			_init : function(){
				require(['domReady'], function (domReady) {
					domReady(function () {
						var ii = torch.edit_primaryValues();
						torch.applySetupContactQuery();
						$("#applySetupBasic_save_button").on('click',torch.applySetupContactSave);
						$("#applySetupBasic_back_button").on('click',torch.backStep);
						$("#applySetupBasic_forward_button").on('click',function(){torch.applySetupContactSave('next')});
						
					});//enf domReady
				});
				
			},
			edit_primaryValues : function(){return "&gid=" + jazz.util.getParameter('gid')},
			edit_fromNames : [ 'applySetupEntContact_Form', 'applySetupFrContact_Form'],
			
			applySetupContactQuery : function(){
				var updateKey= "&wid=applySetupEntContact,applySetupFrContact";
				$.ajax({
						url:'../../../torch/service.do?fid=applySetupContact'+ updateKey + torch.edit_primaryValues(),
						type:"post",
						async:false,
						dataType:"json",
						success: function(data){
						    var jsonData = data.data;
							if($.isArray(jsonData)){
								 for(var i = 0, len = jsonData.length; i<len; i++){
									 $("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
									 
									 if(jsonData[i].name == "applySetupFrContact_Form"){
										 if(jsonData[i].data.cerType=="1"){
											 $("#fcerNo").textfield("option","rule","must_idcard_length;0;20");
										 }else{
											 $("#fcerNo").textfield("option","rule","must_length;0;20"); 
										 }
									 }
									 if(jsonData[i].name == "applySetupEntContact_Form"){
										 if(jsonData[i].data.cerType=="1"){
											 $("#cerNo").textfield("option","rule","must_idcard_length;0;20");
										 }else{
											 $("#cerNo").textfield("option","rule","must_length;0;20");		 
										 }
									 }
								 }
							 }
							
							if(jsonData.length == 2){
								//由于jazz组件bug:页面上有俩个同名的comboxfield，只能发送一个.do
								//所以修改了页面cerType的name,并在此处为cerType的新name赋值
								if(jsonData[1].name == "applySetupFrContact_Form"){//赋值的前提条件，否则俩个表单的数据相反
									$("#fcerType").comboxfield('reload');
									if(jsonData[0].data){
										$("#name").autocompletecomboxfield('setText',jsonData[0].data.name);
									}
									if(jsonData[1].data){
										$("#fcerType").comboxfield('setValue',jsonData[1].data.cerType);
										$("#fname").autocompletecomboxfield('setText',jsonData[1].data.name);
									}
									
									if(jsonData[1].data.cerValFrom && jsonData[1].data.cerValTo){
										$("#fcerValFrom").datefield('setValue',jsonData[1].data.cerValFrom.substring(0,10));
										$("#fcerValTo").datefield('setValue',jsonData[1].data.cerValTo.substring(0,10));
									}
								}else{//TODO 目前不会走这个，防止以后有变先加上
									$("#fcerType").comboxfield('reload');
									if(jsonData[1].data){
										$("#name").autocompletecomboxfield('setText',jsonData[1].data.name);
									}
									if(jsonData[0].data){
										$("#fcerType").comboxfield('setValue',jsonData[0].data.cerType);
										$("#fname").autocompletecomboxfield('setText',jsonData[0].data.name);
									}
									
									if(jsonData[0].data.cerValFrom && jsonData[0].data.cerValTo){
										$("#fcerValFrom").datefield('setValue',jsonData[0].data.cerValFrom.substring(0,10));
										$("#fcerValTo").datefield('setValue',jsonData[0].data.cerValTo.substring(0,10));
									}
								}
								
								
							}
						}
					});
			},
			
			applySetupContactSave : function(type){
				torch.copyName();
				/*var cerValFromC = $("#cerValFrom").datefield('getValue');
				var cerValToC = $("#cerValTo").datefield('getValue');
				
				var fcerValFromC = $("#fcerValFrom").datefield('getValue');
				var fcerValToC = $("#fcerValTo").datefield('getValue');*/
			
//				var res = torch.dateCompare(cerValFromC,cerValToC,fcerValFromC,fcerValToC);
//				var now = new Date();
//				alert(res2);
////				var res2 = torch.dateCompare(fcerValToC,now);
//				if(res2 == "unpassed"){
//					jazz.error('证件有效期时间不能早于当天! ');
//					return;
//				}
				//if(cerValToC == null)
				/*if(res == "unpassed"){
					jazz.error('证件有效期起始时间不能晚于截止时间！');
					return;
				}*/
//				if(res == "unpassedT"){
//					jazz.error("证件有效期不能早于ji！");
//					return;
//				}
				
				var params={		 
					 url:"../../../torch/service.do?fid=applySetupContact"+ torch.edit_primaryValues(),
					 components: torch.edit_fromNames,
					/* params:{
						 cerValFromC : cerValFromC,
						 cerValTo : cerValToC,
						 fcerValFromC : fcerValFromC,
						 fcerValToC : fcerValToC
					 },*/
					 callback: function(jsonData,param,res){
						 if (type == "next") {
							 torch.nextStep();
						 } else {
							 jazz.info('保存成功！');
						 }
					 }
				};
				$.DataAdapter.submit(params);
			},
			
			copyName : function(){
	    		var nameValue = $("#name").autocompletecomboxfield('getText');
	    		var fnameValue = $("#fname").autocompletecomboxfield('getText');
	    		if(nameValue && fnameValue){
					$("#textName").hiddenfield('setValue',nameValue);
					$("#textFname").hiddenfield('setValue',fnameValue);
	    		}
	    	},
			
			backStep:function(){
	 	    	window.location.href="../../../page/apply/setup/member.html?gid="+jazz.util.getParameter('gid');
	 	    },
	 	    
	 	    nextStep:function(){
	 	    	window.location.href="../../../page/apply/setup/upload.html?gid="+jazz.util.getParameter('gid');
	 	    },
	 	    
	 	   dateCompare : function(cerValFromC,cerValToC,fcerValFromC,fcerValToC){
	    		
	 		  if(cerValFromC  && fcerValFromC ){
	    			var res1 = torch.dateCompareDetail(cerValFromC,cerValToC);
	    			var res2 = torch.dateCompareDetail(fcerValFromC,fcerValToC);
	    			if(res1 == "unpassed" || res2 == "unpassed"){
	    				return "unpassed";
	    			}
//	    			if(res1 == "unpassedT" || res2 == "unpassedT"){
//	    				return "unpassedT";
//	    			}
	 		  }
	 	   },
	    	
	    	dateCompareDetail : function(cerValFrom,cerValTo){
	    		var cerValFromTime=cerValFrom.split("-");
	    		var cerValFromT= new Date(cerValFromTime[0],cerValFromTime[1],cerValFromTime[2]);
	    		
	    		var cerValToTime ;
	    		var cerValToT;
	    		if(cerValTo == ""){
	    			return ;
//	    			cerValTo = "2999-12-31";
//	    			cerValToTime=cerValTo.split("-");
//					cerValToT= new Date(cerValToTime[0],cerValToTime[1],cerValToTime[2]);
//					if(cerValFromT > cerValToT){
//		    			return "unpassed";
//					}
	    		}else{
	    			cerValToTime=cerValTo.split("-");
					cerValToT= new Date(cerValToTime[0],cerValToTime[1],cerValToTime[2]);
					if(cerValFromT > cerValToT){
		    			return "unpassed";
					}
	    		}
	    	},
	}
	torch._init();
    return torch;
});

