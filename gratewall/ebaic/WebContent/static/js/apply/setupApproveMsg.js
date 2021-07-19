define([ 'require', 'jquery', 'jazz' ], function(require, $, jazz) {
	var demo = {
		_init : function() {
			require([ 'domReady' ], function(domReady) {
				domReady(function() {
					var gid = jazz.util.getParameter("gid") || '';
					if(!gid){
						jazz.info("未获得gid。");
						return ;
					}
					var params = {
		    		        url : '../../../apply/setup/approveMsg/lastest.do',
		    		        params:{
		    		        	gid:gid
		    		        },
		    		        callback : function(data, param, res) {
		    		        	var msg = res.getAttr("msg") || '';
		    		        	if(!msg){
		    		        		// 没有审批记录
		    		        		$(".wrapAdvice").css("display","none");
		    		        		return;
		    		        	}else{
		    		        		$(".wrapAdvice").css("display","block");
		    		        		$(".tips").renderTemplate({templateName:"approve_msg",insertType:"append"},msg); 
		    		        	}
		    		        } 	
		    		    };  	
		    		$.DataAdapter.submit(params);
				});
			});
		}
	};
	demo._init();
	return demo;
});