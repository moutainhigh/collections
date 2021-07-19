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
					    	//初始化列表数据
					    	
					  /*  	var gridUrl="../../otherselect/YRQueryList.do";
				    		$("#YRQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
				    		$("#YRQueryListGrid").gridpanel("option",'dataurl',gridUrl);
				    		$("#YRQueryListGrid").gridpanel('query', ['YRQueryListPanel'],null);*/
				    		
				    		
				    		
					    	$("#save_button").off('click').on('click',$_this.queryYRList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
							
						    util.exports('editFunc',$_this.editFunc);
					    });
				});
	    	},
	    	getCount:function(){
	    		var params = {
	    				url : '../../otherselect/YRQueryList.do?flag=1',
	    				components: ['YRQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
			 /**
			   * 查询失信被执行人信息
			   */
	    	queryYRList : function(){
	    		torch.getCount();
	    		$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.queryYRList);
	    		}, 1800);
	    		
	    		$("#tips").css("display","block");
		    		//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
	    		var gridUrl="../../otherselect/YRQueryList.do";
	    		$("#YRQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#YRQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#YRQueryListGrid").gridpanel('query', ['YRQueryListPanel'],null);
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
	    			data[i]["entname"] = '<a href="javascript:void(0);"  title="'+data[i]["entname"] +'" onclick="ebaic.editFunc(\''+data[i]["pripid"]+'\')">' + data[i]["entname"] + '</a>';
	    		}	
	    		return data;
	    	},
	    	
	    	/**
	    	 * 清除指定funcConfig
	    	 */
	    	delFunc:function(functionId){
	    		/*jazz.info("对不起，您的权限不足，请联系管理员！");
	    		return;*/
	    		jazz.confirm("确认要删除该功能项吗？",function(){
	    			var params = {
		    				url : '../../../../admin/torch/delFunc.do',
		    				params:{
		    					functionId:functionId
		    				},
		    				callback : function(data, param, res) {
		    					var msg = res.getAttr("del");
		    					if(msg=='success'){
		    						jazz.info("删除成功！",function(){
		    							var treeId = jazz.util.getParameter("treeId");
		    				    		if(treeId){
		    				    			$_this.queryFunctionConfig(treeId);
		    				    		}
		    						});
		    					}
		    				}
		    		};
		    		$.DataAdapter.submit(params);
	    		},function(){
	    			
	    		});
	    		
	    	},

	    	/**
	    	 * 弹出editFunctionConfig.html页面
	    	 */
	    	editFunc:function(pripid){
	    		var url = $_this.getContextPath()+"/page/otherselect/YRDetail.jsp?pripid="+pripid + "&type=YR";
	    		window.open(url);
	    	},
	    	/**
	    	 * 打开queryEditConfig.html页面
	    	 */
	    	configEditOrQuery:function(functionId){
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/queryEditConfig.html?functionId="+functionId;
	    		window.open(url);
	    	},
	    	
	    	/**
	    	 * 弹出funcConfig添加页面
	    	 */
	    	addFuncConfig:function(){
	    		var treeId = jazz.util.getParameter("treeId");
	    		var treePath = jazz.util.getParameter("treePath");
	    		util.openWindow("addFunctionConfig","新增功能信息","functionConfig.html?treeId="+treeId+"&treePath="+treePath,1000,350);
	    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/functionConfig.html";
	    		window.location.href=url;*/
	    	},
	    	//html中获取当前页的 上下文路径
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
	    	},
	    	
	    	/**
	    	 * 批量清理func
	    	 */
	    	funcBatchDelete:function(){
	    		var selected = $('div[name="funcGrid"]').gridpanel('getSelection');
	    		if (selected == null || selected.length<1 ){
	    			jazz.info("请选中至少一个目标");
	    		}else if(selected.length>=1){
		    		jazz.confirm("确定清除Func？",function(){
		    			var params = {
			    				url : '../../../../admin/torch/delBatchFunc.do',
			    				components: ['funcGrid'],
			    				callback : function(data, param, res) {
			    					var msg = res.getAttr("del");
			    					if(msg=='success'){
			    						jazz.info("删除成功！",function(){
			    							torch.redisSave();
			    						});
			    					}
			    				}
			    		};
			    		$.DataAdapter.submit(params);
		    		},function(){
		    			
		    		});
	    		}
	    	},
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    	},
	    	edit_fromNames : [ 'YRQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
