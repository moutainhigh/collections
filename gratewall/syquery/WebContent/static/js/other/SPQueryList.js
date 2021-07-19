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
	    		
	    		var queryValue1 = $("div[name = 'spltLicenseNo']").textfield("getValue");
	    		var queryValue2 = $("div[name = 'companyName']").textfield("getValue");
	    		
	    		var queryValue3 = $.trim(queryValue1);
	    		var queryValue4 = $.trim(queryValue2);
	    		
	    	//	if(queryValue3 || queryValue4){
	    			var gridUrl="otherselect/SPQueryList.do";
		    		$("#SPQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
		    		$("#SPQueryListGrid").gridpanel("option",'dataurl',gridUrl);
		    		$("#SPQueryListGrid").gridpanel('query', ['SPQueryListPanel'],null);
	    		/*}else{
	    			jazz.warn("请输入查询条件");
	    		}*/
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
	    		//window.open ($_this.getContextPath()+"/page/otherselect/SPQueryDetail.html?id="+id,'详细信息','height=290,width=600,top=180,left=370,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
	    		//util.openWindow("addFunctionConfig","详细信息",$_this.getContextPath()+"/page/otherselect/SPQueryDetail.html?id="+id,600,290,'');
	    		$("#winContent").css("display","block");
	    		$("#winContent").attr("src",$_this.getContextPath()+"/SPQueryDetail.html?id="+id);
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
