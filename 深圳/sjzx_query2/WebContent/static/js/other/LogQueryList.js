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
					    	
					    	$("div[name = 'logStart']").datefield("option","change",function(){
					    		if(!$_this.validate("logEnd","logStart")){
					    			jazz.warn("日志起始日期不能晚于结束日期!");
					    		}
					    	});
					    	$("div[name = 'logEnd']").datefield("option","change",function(){
					    		if(!$_this.validate("logEnd","logStart")){
					    			jazz.warn("日志起始日期不能晚于结束日期!");
					    		}
					    	});
				    		
				    		$("#save_button").off('click').on('click',$_this.queryLogList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    util.exports('editFunc',$_this.editFunc);
						    
						    
					    });
				});
	    	},
	    	
	    	queryLogList : function(){
	    		//torch.getCount();
	    		//$("#tips").css("display","block");
	    		var gridUrl="../../otherselect/LogQueryList.do";
	    		$("#LogQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#LogQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#LogQueryListGrid").gridpanel('query', ['LogQueryListPanel'],null);
	    		
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
	    		//util.openWindow("SPQueryDetail","食品流通许可证信息详情信息",$_this.getContextPath()+"/page/otherselect/SPQueryDetail.html?id="+id,700,350);
	    		window.open (
	    				$_this.getContextPath()+"/page/otherselect/LogQueryDetail.html?id="+id,
	    				'详细信息',
	    				'height=380,width=600,top=200,left=420,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
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
	    	//校验成立日期中起始日期不能晚于截止日期
	    	validate: function(Time1,Time2){
	    		var endTime = $("div[name = '"+Time1+"']").datefield('getValue');
	    		var startTime = $("div[name = '"+Time2+"']").datefield('getValue');
	    		
	    		if(!endTime){
	    			endTime = "9999-12-31";
	    		}
	    		
	    		if(!startTime){
	    			startTime = "0000-01-01";
	    		}
	    		
	    		var endTimeArray = endTime.split("-");
	    		var startTimeArray = startTime.split("-");
	    		
	    		if(endTimeArray[0]<startTimeArray[0]){
	    			return false;
	    		}else if(endTimeArray[0]==startTimeArray[0]){
	    			if(endTimeArray[1]<startTimeArray[1]){
	    				return false;
	    			}else if(endTimeArray[1]==startTimeArray[1]){
	    				if(endTimeArray[2]<startTimeArray[2]){
	    					return false;
	    				}else{
	    					return true;
	    				}
	    			}else{
	    				return true;
	    			}
	    		}else{
	    			return true;
	    		}
	    		return true;
	    	},
	    	
	    	edit_fromNames : [ 'LogQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
