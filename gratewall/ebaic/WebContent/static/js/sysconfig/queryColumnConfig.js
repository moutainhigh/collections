define(['jquery', 'jazz', 'util'], function($, jazz,util){
	 var torch ={};
		torch = {
	    	_init: function(){
	    		$_this = this;
	    		require(['domReady'], function (domReady) {
				    domReady(function () {
			    		var columnId = jazz.util.getParameter("columnId")||"";
			    		if(columnId){
			    			$_this.reloadEditColumn(columnId);
			    		}
			    		var configId = jazz.util.getParameter("configId");
			    		if(configId){
			    			$("#configId").hiddenfield("setValue",configId);
			    		}
			    		$("#save_button").off('click').on('click',$_this.saveQueryColumn);
				    	$("#back_button").off('click').on('click',$_this.goBack);
				    	$("#reset_button").off('click').on('click',torch.resetEditColumn);
				    });
				});
				
	    	},
	    	reloadEditColumn:function(columnId){
	    		var params = {
	    				columnId:columnId
	    			}
	    			url="../../../../torch/config/queryQueryColumnDetail.do";
	    			$("#queryColumnPanel").formpanel("option","dataurlparams",params);
	    			$("#queryColumnPanel").formpanel("option","dataurl",url);
	    			$("#queryColumnPanel").formpanel("reload");
	    	},
	    	//配置queryColumnConfig页面保存方法
	    	saveQueryColumn:function (){
	    		var params = {
	    				url : '../../../../torch/config/saveQueryColumn.do',
	    				components : [ 'queryColumnPanel' ],
	    				callback : function(data, param, res) {
	    					var msg = res.getAttr("save");
	    					if(msg=='success'){
	    						jazz.info("保存成功！",function(){
	    							util.closeWindow("queryConfig.html",true);
	    						});
	    					}
	    				}
	    			};
	    		$.DataAdapter.submit(params);
	    	},
	    	
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditColumn : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    	},
	    	edit_fromNames : [ 'queryColumnPanel'],
	    	
	    	goBack:function (){
	    		history.go(-1);
	    	}
	    };
	    torch._init();
	    return torch;
	    
});

