define(['require', 'jquery', 'approvecommon', 'util'], function(require, $, approvecommon, util){

    var g = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
    			  domReady(function () {
    				// 绑定事件
			    	$("[name='queryBackAssign_query_button']").on('click', g.doBackAssign);
			    	$("[name='queryBackAssign_reset_button']").on('click', g.closeBackAssign);
			    	
    			  });
    			});
	    	
    	} ,
    	
    	/**
    	 * 退回修改再分配
    	 */
    	doBackAssign:function(){
    	var censorUser = g.getCensorUserArr();
    	var params = {
    			url:'../../../approve/censor/saveBackAssign.do',
    			params : {
    					censorUserArr:censorUser
    			},
    			callback:function(data,r,res){
    				var result = res.getAttr('result');
    				if(result=='success'){
    					jazz.info("分配成功");
    					util.closeWindow('query_back_check_people',true);
    					//window.parent.ebaic.closeBackAssign("true");
    				}else{
    					jazz.info("分配失败");
    				}
    			}
    		}
    		$.DataAdapter.submit(params);
    	},
    	
    	/**
    	 * 关闭退回修改再分配
    	 */
    	closeBackAssign:function(){
    		util.closeWindow('query_back_check_people');
    		//window.parent.ebaic.closeBackAssign("false");
    	},
    	
    	
    	/**
    	 * 获取列表中的审核用户信息 -转为数组形式 
    	 */
    	getCensorUserArr:function(){
    		var censorUser = $("div[name='queryGrid_Grid']").gridpanel('getSelectedRowData');
    		if(censorUser.length==0){
    			jazz.info("请勾选审查人员！");
    			return false;
    		}
    		var censorUserArr = [];
    		for(var i=0;i<censorUser.length;i++){
    			censorUserArr[i] = censorUser[i].censorUserId;
    		}
    		return censorUserArr;
    	}
    	
    };
    g._init();
    return g;
});