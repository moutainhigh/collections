define(['require', 'jquery', 'approvecommon','util'], function(require, $, approvecommon,util){
var torch = {
		
		queryForm_datarender : function (event,obj){},
		query_fromNames : [ 'queryForm_Form'],
		processAssign_listSure : function(){
			var appDateBegin = $("div[name='appDateBegin']").datefield('getValue');
			var appDateEnd = $("div[name='appDateEnd']").datefield('getValue');
			if(appDateBegin&&appDateEnd){
				var num = Date.parse(new Date(appDateBegin))-Date.parse(new Date(appDateEnd));
				if(num&&num>0){
					jazz.warn("申请日期起始时间不能大于截止时间！");
					return false;
				}
			}
			if($("div[name='queryForm_Form']").formpanel('validate')){
				$("div[name='queryForm_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=f9744ebe23a04855900bff06bc9d5ec6&wid=fd9e752ef5814f23baa304f2a83dd63e");
				$("div[name='queryForm_Grid']").gridpanel("query", [ "queryForm_Form"]);
			}
		},
		processAssign_listReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
		},
		_init : function(){
			require(['domReady'], function (domReady) {
			    domReady(function () {
					$("div[name='_Form']").gridpanel("option","datarender",torch.queryForm_datarender);
					$("div[name='_Form']").gridpanel("reload");
					$("div[name='processAssign_list_query_button']").off('click').on('click',torch.processAssign_listSure);
					$("div[name='processAssign_list_reset_button']").off('click').on('click',torch.processAssign_listReset);
			    	$("div[name='openAssign']").on('click', torch.openAssign); 
			    	$("div[name='backAssign']").on('click', torch.openBackAssign);     	
    			    // 导出函数
			    	util.exports('getRequisitionIdarr',torch.getRequisitionIdarr);
			    	torch.noData();
			    });
			});
		},
		/**
	     * 点击分配按钮触发事件 
	     */
		openAssign: function (){
	    	var ii = torch.getRequisitionIdarr();
	    	if(ii.length==0){
	    		jazz.warn("请勾选待分配的申请记录!");
	    		return false;
	    	}
	    	util.openWindow("processAssign_person_list","选择审核人员","processAssign_person_list.html",600,450);
	    },
	    /**
	     * 点击退回修改再分配按钮触发事件 
	     */
	    openBackAssign : function (){
	    	util.openWindow("againAssignUser_list","选择审核人员","againAssignUser_list.html",800,450);
	    },
	    /**
	     * 关闭分配窗口 
	     */
	    closeAssign : function (refresh){
	    	if(refresh=='true'){
	    		jazz.info("分配成功");	
	    	}
	    	util.closeWindow("processAssign_person_list");
	    	if(refresh=='true'){
	    		torch.processAssign_listSure();
	    	}
	    } ,
	
	    /**
	     * 关闭退回修改再分配窗口
	     */
	    closeBackAssign : function (refresh){
	    	if(refresh=='true'){
	    		jazz.info("分配成功");
	    	}
	    	util.closeWindow("againAssignUser_list");
	    	if(refresh=='true'){
	    		torch.processAssign_listSure();
	    	}
	    },
	    getRequisitionIdarr : function (){
	    	var ii = $("div[name='queryForm_Grid']").gridpanel('getSelectedRowData');
	    	var requisitionarr = [];
	    	for(var i=0;i<ii.length;i++){
	    		requisitionarr[i] = ii[i].requisitionId;
	    	}
	    	return requisitionarr;
	    },
	    /*没有数据的时候显示图片*/
	    noData:function(){
	    	$.ajax({
	    		url:"../../../torch/service.do?fid=f9744ebe23a04855900bff06bc9d5ec6&wid=fd9e752ef5814f23baa304f2a83dd63e",
	    		type:"post",
	    		dataType:"json",
	    		async:false
	    	});
	    }
};
torch._init();
return torch;
});
