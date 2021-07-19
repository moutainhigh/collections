define(['require','jquery', 'approvecommon','util'], function(require, $, approvecommon, util){

    var g = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		require(['domReady'], function (domReady) {
    			  domReady(function () {
    				// 绑定事件
    			    	$("[name='processAssign_person_list_query_button']").on('click', g.saveAssign);
    			    	$("[name='processAssign_person_list_reset_button']").on('click', g.closeAssing); 
    			    	
    			    	$("[name='queryCheckPeople_query']").on('click',g.queryCheckPeople);
    			    	$("[name='queryCheckPeople_reset']").on('click',g.queryCheckPeopleReset);
    			    	
    			  });
    			});
	    	
    	} ,
    	
    	
    	
    	query_fromNames : [ 'queryGrid_Form'],


    	queryCheckPeople : function(){
    					$("div[name='queryGrid_Grid']").gridpanel("option", "dataurl","../../../../torch/service.do?fid=0f71f2385e3c486ea0872175327f413f&wid=98aa9570a1194bfa9421725cbb9ea2a8");
    					$("div[name='queryGrid_Grid']").gridpanel("query", [ "queryGrid_Form"]);
    			},
    	queryCheckPeopleReset : function(){
    				for( x in g.query_fromNames){
    					$("div[name='"+g.query_fromNames[x]+"']").formpanel("reset");
    				} 
    			},
    	
    	
    	
    	/**
    	 * 分配
    	 * @returns {Boolean}
    	 */
    	saveAssign:function(){
    		var userInfo = $("div[name='queryGrid_Grid']").gridpanel("getSelectedRowData");
    		var arr = window.parent.ebaic.getRequisitionIdarr();
    		if(userInfo.length==0){
    			jazz.info("请选择一位审核人员!");
    			return false;
    		}
    		var userName = userInfo[0].userName||'';
    		var userId = userInfo[0].userId||'';
    		jazz.confirm("确认分配给审核人员 "+userName+"?", function(){
    			var params = {
    					url:'../../../approve/approval/saveAssign.do',
    					params : {
    						reqArr:arr,
    						userId:userId
    					},
    					callback : function(data, r, res) {
    						if(res.getAttr('result')=='success'){
    							util.closeWindow('processAssign_person_list',true);
    						}else{
    							jazz.info("分配失败!");
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
    		util.closeWindow('processAssign_person_list',false);
		}

    };
    g._init();
    return g;
});