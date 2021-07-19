define(['require','jquery', 'approvecommon','util'], function(require, $, approvecommon, util){

    var g = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				// 绑定事件
		    	$("[name='assign_button']").on('click', g.saveAssign);
		    	$("[name='return_button']").on('click', g.closeAssing); 
			  });
			});
	    	
    	} ,
    	
    	
    	/**
    	 * 分配
    	 * @returns {Boolean}
    	 */
    	saveAssign:function(){
    		var userInfo = $("div[name='queryGrid_Grid']").gridpanel("getSelectedRowData");
    		if(userInfo.length==0){
    			jazz.warn("至少选取一位审核人员!"); 
    			return false;
    		}
    		jazz.confirm("确认要分配吗?", function(){
    			var userIdArr = [];
    			for(var i=0;i<userInfo.length;i++){
    				userIdArr[i] = userInfo[i].approveUserId;
    			}
    			var params = {
    					url:'../../../approve/approval/backAgainAssign.do',
    					params : {
    						userIdArr:userIdArr
    					},
    					callback : function(jsonData,param,res) {
    						if(res.getAttr('result')=='success'){
    							util.closeWindow('againAssignUser_list',true);
    							return ;
    						}else{
    							jazz.info("分配失败");
    						}
    					}
    			}
    			$.DataAdapter.submit(params);	
    		});
    	},
    	
    	/**
    	 * 关闭分配页面
    	 */
    	closeAssing:function(){
			util.closeWindow('againAssignUser_list',false);
		}

    };
    g._init();
    return g;
});