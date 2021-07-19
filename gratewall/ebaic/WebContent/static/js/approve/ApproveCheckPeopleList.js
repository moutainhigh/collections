define(['require', 'jquery', 'approvecommon','util'], function(require, $, approvecommon, util){

    var g = {
    	/**
    	 * 模块初始化。
    	 */
    		
		query_fromNames : [ 'queryGrid_Form'],


    	queryCheckPeople : function(){
    					$("div[name='queryGrid_Grid']").gridpanel("option", "dataurl","../../../../torch/service.do?fid=query_check_people&wid=queryCheckPeople");
    					$("div[name='queryGrid_Grid']").gridpanel("query", [ "queryGrid_Form"]);
    			},
    	queryCheckPeopleReset : function(){
    				for( x in g.query_fromNames){
    					$("div[name='"+g.query_fromNames[x]+"']").formpanel("reset");
    				} 
    			},	
    	
    		
    	_init: function(){
    		require(['domReady'], function (domReady) {
    			  domReady(function () {
    				// 绑定事件
			    	$("[name='queryCheckPeople_query_button']").on('click', g.saveAssign);
			    	$("[name='queryCheckPeople_reset_button']").on('click', g.closeAssing);
			    	
			    	$("[name='queryCheckPeople_query']").on('click',g.queryCheckPeople);
			    	$("[name='queryCheckPeople_reset']").on('click',g.queryCheckPeopleReset);
			    	
    			  });
    			});
	    	
    	} ,
    	
    	
    	
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
    		var userName = userInfo[0].userName;
    		var userId = userInfo[0].userId;
    		jazz.confirm("确认分配给审核人员 "+userName+"?", function(){
    			var params = {
    					url:'../../../approve/censor/saveAssign.do',
    					params : {
    						reqArr:arr,
    						userId:userId
    					},
    					callback : function(data, r, res) {
    						if(res.getAttr('result')=='success'){
    							util.closeWindow('query_check_people',true);
    							//window.parent.ebaic.closeAssign("true");
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
    		util.closeWindow('query_check_people');
		}
    	

    };
    g._init();
    return g;
});