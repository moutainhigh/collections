define(['require', 'jquery', 'approvecommon','util'], function(require, $, approvecommon,util){
var torch = {
		queryGrid_datarender : function (event,obj){
	},

	query_fromNames : [ 'queryGrid_Form'],


	queryMyTaskSure : function(){
					$("div[name='queryGrid_Grid']").gridpanel("option", "dataurl","../../../../torch/service.do?fid=mytask_list&wid=queryMytaskList");
					$("div[name='queryGrid_Grid']").gridpanel("query", [ "queryGrid_Form"]);
			},
	queryMyTaskReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
			
	myTaskApprove:function(){
	    		var rowData = $("div[name='queryGrid_Grid']").gridpanel('getSelectedRowData');
	    		if(rowData.length==0){
	    			jazz.warn("请选择需要审核的数据!");
	    			return false;
	    		}
	    		var gid = rowData[0].gid;
	    		$.ajax({
	    			url:'../../../approve/process/checkIsOnePerson.do',
	    			data:{
	    				"gid":gid
	    			},
	    			type:'post',
	    			dataType:'json',
	    			success:function(data){
	    				if(data==false){
	    					jazz.warn("核准人和审查人不能是同一个人！");
	    					return false;
	    				}else{
	    					$.ajax({
	    		    			url:'../../../../pdf/v2/generateFile.do',
	    		    			data:{
	    		    				"gid":gid,
	    		    				"listCode":"setup_1100",
	    		    				"type":"1"
	    		    			},
	    		    			type:'post',
	    		    			dataType:'json',
	    		    			success:function(data){
	    		    			},
	    		    			error:function(data){
	    		    				jazz.info("数据异常，请联系管理员！");
	    		    			}
	    		    		});
	    					window.location.href="../../../page/approve/process/approve.html?gid="+gid;
	    				}
	    			},
	    			error:function(data){
	    				jazz.info("数据异常，请联系管理员！");
	    			}
	    		});
	    	},
	    	
	    
	 backAssign:function(){
		 	var rowData = $("div[name='queryGrid_Grid']").gridpanel('getSelectedRowData');
		 	if(rowData.length==0){
		 		jazz.warn("请选择需要退回的数据!");
		 		return false;
		 	}
		 	
		 	jazz.confirm("确认退回吗？",function(){
		 		var gid = rowData[0].gid;
		 		
		 		$.ajax({
				 		url:'../../../approve/approval/backAssign.do',
				 		data:{
				 			gid:gid
				 		},
				 		type:'post',
				 		dataType:'json',
				 		success:function(data){
				 			if(data=='success'){
				 				torch.queryMyTaskSure();
				 			}else{
				 				jazz.warn("数据已经被修改，不允许退回！");
				 				return false;
				 			}
				 		},
				 		error:function(){
				 			jazz.warn("数据已经被修改，不允许退回！");
			 				return false;
				 		}
				 	});
			 	
		 	});
		 	
	 },
	    	
			
			
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
			$("div[name='_Form']").gridpanel("option","datarender",torch.queryGrid_datarender);
			$("div[name='_Form']").gridpanel("reload");
			
			$("div[name='queryMyTask_query_button']").off('click').on('click',torch.queryMyTaskSure);
			$("div[name='queryMyTask_reset_button']").off('click').on('click',torch.queryMyTaskReset);
			
			$("[name='mytask_button']").on('click',torch.myTaskApprove);
			$("[name='mytask_back_button']").on('click',torch.backAssign);
		 });
		});
	 }
};
torch._init();
return torch;

});
