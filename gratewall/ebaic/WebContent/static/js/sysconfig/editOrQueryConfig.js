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
					    	var functionId = jazz.util.getParameter("functionId")||"";
				    		if(functionId){
				    			$_this.editOrQueryConfig(functionId);
				    		}
							//$("#function_save_button").off('click').on('click',$_this.addFuncConfig);
							$("#function_save_button").off('click').on('click',$_this.insertFunConfig);
							$("#function_reset_button").off('click').on('click',torch.resetFuncConfig);
							$("#back_button").off('click').on('click',torch.goBack);
							$("#add_query_config_button").off('click').on('click',torch.addQueryConfig);
							$("#add_edit_config_button").off('click').on('click',torch.addEditConfig);
							
							
							util.exports('editEditOrQueryConfig',$_this.editEditOrQueryConfig);
							util.exports('delEditOrQuery',$_this.delEditOrQuery);
							
							
							util.exports('editQueryConfig',$_this.editQueryConfig);
							util.exports('configQueryConfig',$_this.configQueryConfig);
							util.exports('delqueryConfig',$_this.delqueryConfig);
							
							util.exports('editEditConfig',$_this.editEditConfig);
							util.exports('configEditConfig',$_this.configEditConfig);
							util.exports('delEditConfig',$_this.delEditConfig);
					    });
				});
	    	},
	    	
			 /**
				 * 根据functionId查询SYS_FUNCTION_CONFIG,sys_query_column_config和sys_query_condition_config
				 */
	    	editOrQueryConfig : function(functionId){
				 	var params = {
						 functionId:functionId
			    		};
				 	//查询SYS_FUNCTION_CONFIG并且能编辑
			    	var url="../../../../torch/config/queryFuncConfig.do";
		    		$("#functionConfigPanel").formpanel("option","dataurlparams",params);
		    		$("#functionConfigPanel").formpanel("option","dataurl",url);
		    		$("#functionConfigPanel").formpanel("reload");	   
		    		
		    		//根据functionId查询sys_edit_config和sys_query_config，并且有回调函数
		    		var gridUrl="../../../../torch/config/editOrQueryGrid.do";
		    		$("#editOrQueryGrid").gridpanel("option",'datarender',torch.backEditOrQuery);
		    		$("#editOrQueryGrid").gridpanel("option","dataurlparams",params);
		    		$("#editOrQueryGrid").gridpanel("option",'dataurl',gridUrl);
		    		$("#editOrQueryGrid").gridpanel("reload");
				
			},
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backEditOrQuery:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			var tempVal = data[i].configSql;
	    			if(tempVal.indexOf("select")!=-1){
	    				data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editQueryConfig(\''+data[i].configId+'\')">' + "编辑" + '</a>'
	    				+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configQueryConfig(\''+data[i].configId+'\')">' + "配置" + '</a>'
	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delqueryConfig(\''+data[i].configId+'\')">' + "删除" + '</a>';
	    			}else{
	    				data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editEditConfig(\''+data[i].configId+'\')">' + "编辑" + '</a>'
	    				+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditConfig(\''+data[i].configId+'\')">' + "配置" + '</a>'
	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delEditConfig(\''+data[i].configId+'\')">' + "删除" + '</a>';
	    			}
	    		}	
	    		return data;
	    	},
	    	/**
	    	 * 弹出addQueryConfig.html页面
	    	 */
	    	editQueryConfig:function (configId){
	    		util.openWindow("editQueryConfig","编辑组件信息","addQueryConfig.html?configId="+configId,1050,450);
	    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/editConfig.html?configId="+configId;
	    		window.location.href=url;*/
	    	},
	    	/**
	    	 * 弹出addEditConfig.html页面
	    	 */
	    	editEditConfig:function (configId){
	    		util.openWindow("editEditConfig","编辑组件信息","addEditConfig.html?configId="+configId,1050,450);
	    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/editConfig.html?configId="+configId;
	    		window.location.href=url;*/
	    	},
	    	
	    	
	    	/**
	    	 * 跳转到queryConfigDetail页面
	    	 */
	    	configQueryConfig:function (configId){
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/queryConfig.html?configId="+configId;
	    		window.location.href=url;
	    	},
	    	
	    	/**
	    	 * 跳转到editConfigDetail页面
	    	 */
	    	configEditConfig:function (configId){
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/editConfig.html?configId="+configId;
	    		window.location.href=url;
	    	},
	    	
	    	/**
	    	 * 清除指定queryConfig
	    	 */
	    	delqueryConfig:function(configId){
	    		/*jazz.info("对不起，您的权限不足，请联系管理员！");
	    		return;*/
	    		jazz.confirm("确认要删除该组件项吗？",function(){
	    			var params = {
		    				url : '../../../../torch/config/deleteQueryConfig.do',
		    				params:{
		    					configId:configId
		    				},
		    				callback : function(data, param, res) {
		    					var msg = res.getAttr("del");
		    					if(msg=='success'){
		    						jazz.info("删除成功！",function(){
		    							var functionId = jazz.util.getParameter("functionId")||"";
		    				    		if(functionId){
		    				    			$_this.editOrQueryConfig(functionId);
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
	    	 * 清除指定editConfig
	    	 */
	    	delEditConfig:function(configId){
	    		/*jazz.info("对不起，您的权限不足，请联系管理员！");
	    		return;*/
	    		jazz.confirm("确认要删除该组件项吗？",function(){
	    			var params = {
		    				url : '../../../../torch/config/deleteEditConfig.do',
		    				params:{
		    					configId:configId
		    				},
		    				callback : function(data, param, res) {
		    					var msg = res.getAttr("del");
		    					if(msg=='success'){
		    						jazz.info("删除成功！",function(){
		    							var functionId = jazz.util.getParameter("functionId")||"";
		    				    		if(functionId){
		    				    			$_this.editOrQueryConfig(functionId);
		    				    		}
		    						});
		    					}
		    				}
		    		};
		    		$.DataAdapter.submit(params);
	    		},function(){
	    			
	    		});
	    		
	    	},
	    	//配置function页面保存方法
	    	insertFunConfig:function (){
	    		var params = {
	    			url : '../../../../torch/config/insertFunConfig.do',
	    			components : [ 'functionConfigPanel' ],
	    			callback : function(data, param, res) {
	    				var msg = res.getAttr("save");
	    				if(msg=='success'){
	    					jazz.info("保存成功！",function(){
	    						util.closeWindow("queryEditConfig.html",true);
	    					});
	    				}
	    			}
	    		};
	    		$.DataAdapter.submit(params);
	    	},

	    	/**
	    	 * 跳转到queryEditConfig页面
	    	 */
	    	editFunc:function(){
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/queryEditConfig.html";
	    		window.location.href=url;
	    		
	    	},
	    	
	    	/**
	    	 * 跳转到funcConfig添加页面
	    	 */
	    	addFuncConfig:function(){
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/functionConfig.html";
	    		window.location.href=url;
	    		
	    	},

	    	/**
	    	 * 跳转到queryConfig添加页面
	    	 */
	    	addQueryConfig:function (){
	    		var functionId = jazz.util.getParameter("functionId")||"";
	    		util.openWindow("addQueryConfig","添加查询组件","addQueryConfig.html?functionId="+functionId,1050,450);
	    		/*var url = torch.getContextPath()+"/mgr/torch/sysconfig/queryConfig.html?functionId="+functionId;
	    		window.location.href=url;*/
	    	},
	    	
	    	/**
	    	 * 弹出editConfig添加页面
	    	 */
	    	addEditConfig:function (){
	    		var functionId = jazz.util.getParameter("functionId")||"";
	    		util.openWindow("addEditConfig","添加编辑组件","addEditConfig.html?functionId="+functionId,1050,450);
	    		/*var url = torch.getContextPath()+"/mgr/torch/sysconfig/editConfig.html?functionId="+functionId;
	    		window.location.href=url;*/
	    	},
	    	/**
	    	 * 重置方法
	    	 */
	    	resetFuncConfig : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    	},
	    	edit_fromNames : [ 'functionConfigPanel'],

	    	
	    	//html中获取当前页的 上下文路径
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
	    	},
	    	goBack:function (){
	    		history.go(-1);
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
	    	
		};
	
		torch._init();
		return torch;
});
