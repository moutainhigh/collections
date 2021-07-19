define(['jquery', 'jazz','util' ], function($, jazz,util){
 var torch ={};
	torch = {
		/**
		 * 模块初始化
		 */
			_init: function(){
				$_this = this;
				require(['domReady'], function (domReady) {
					    domReady(function () {
					    	$("#tips").css("display","block");
				    		$("#save_button").off('click').on('click',$_this.querySPList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
							
						    util.exports('editFunc',$_this.editFunc);
						    
						    
					    });
				});
	    	},
	    	
			 /**
			   * 查询食品流通服务许可证信息
			   */
	    	querySPList : function(){
	    		//torch.getCount();
	    		//$("#tips").css("display","block");
	    		
	    		$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.querySPList);
	    		}, 1800);
	    		
	    		var gridUrl="../../otherselect/SPQueryList.do";
	    		$("#SPQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#SPQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#SPQueryListGrid").gridpanel('query', ['SPQueryListPanel'],null);
	    		
			},
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editFunc(\''+data[i].id+'\')">' + "详情" + '</a>'
	    						/*+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
	    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>'*/
	    	 					
	    	 					;
	    		}	
	    		return data;
	    	},
	    	
	    	
	    	editFunc:function(id){
	    		util.openWindow("SPQueryDetail","食品流通许可证信息详情信息",$_this.getContextPath()+"/page/otherselect/SPQueryDetail.html?id="+id,700,350);
	    	},
	    	
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;
	    		var index = pathName.substr(1).indexOf("/");
	    		var result = pathName.substr(0,index+1);
	    		return result;
	    	},
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    	},
	    	edit_fromNames : [ 'SPQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
