define(['require', 'jquery', 'approvecommon','util'], function(require, $, approvecommon, util){

    var g = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				// 绑定事件
		    	$("[name='openAssign']").on('click', g.openAssign); 
		    	$("[name='backAssign']").on('click', g.openBackAssign); 
	   	
		    	// 导出函数
		    	util.exports('closeAssign',g.closeAssign);
		    	util.exports('getRequisitionIdarr',g.getRequisitionIdarr);
		    	
		    	util.exports('closeBackAssign',g.closeBackAssign);
		    	util.exports('getRequisitionIdarr',g.getRequisitionIdarr);
		    	g.noData();
		    	
			  });
			  
			});
	    	
    	} ,
    	/**
    	 * 打开分配窗口
    	 * @returns {Boolean}
    	 */
    	openAssign: function (){
	    	var selectedReqIds = g.getRequisitionIdarr();
	    	if(selectedReqIds.length==0){
	    		jazz.info("请勾选待分配的申请记录");
	    		return false;
	    	}
	    	var requisitionarr = [];
	    	for(var i=0;i<selectedReqIds.length;i++){
	    		requisitionarr[i] = selectedReqIds[i].entId;
	    	}
	    	util.openWindow("query_check_people","选择审核人员","queryCheckPeople_list.html",600,450);
	    } ,
	    /**
	     * 打开退回修改再分配功能
	     */
	    openBackAssign : function (){
	    	util.openWindow("query_back_check_people","选择审核人员","censor_back_assign_list.html",800,450);
	    },
	    /**
	     * 获取选择的数据 
	     */
	    getRequisitionIdarr : function (){
	    	var ii = $("div[name='queryGrid_Grid']").gridpanel('getSelectedRowData');
	    	var requisitionarr = [];
	    	for(var i=0;i<ii.length;i++){
	    		requisitionarr[i] = ii[i].requisitionId;
	    	}
	    	return requisitionarr;
	    },
	    /**
	     * 关闭分配窗口 
	     */
	    closeAssign : function (refresh){
	    	if(refresh=='true'){
	    		jazz.info("分配成功");	
	    		tf.approve_censor_list.approve_censor_listSure();
	    	}
	    	util.closeWindow("query_check_people");
	    } ,
	
	    /**
	     * 关闭退回修改再分配窗口
	     */
	    closeBackAssign : function (refresh){
	    	if(refresh=='true'){
	    		jazz.info("分配成功");
	    		tf.approve_censor_list.approve_censor_listSure();
	    	}
	    	util.closeWindow("query_back_check_people");
	    },
	   /* 无数据显示图片*/
	    noData:function(){
	    	$.ajax({
	    		url:"../../../torch/service.do?fid=query_assign&wid=queryAssign",
	    		type:"post",
	    		async:false,
				dataType:"json",
	    		success:function(data){
	    			var len=data.data[0].data.totalrows;
	    			if(len==0){
	    				$("#img").css("display","block");
	    			}
	    		}
	    	});
	    }

    };
    g._init();
    return g;
});