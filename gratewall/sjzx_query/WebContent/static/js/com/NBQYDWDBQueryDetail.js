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
					    	var ancheid = jazz.util.getParameter("ancheid")||"";
				    		if(ancheid){
				    			$_this.queryFunctionConfig(ancheid);
				    		}
					    	$("#back_button").off('click').on('click',$_this.goBack);
			    	   	 	$("tr:even").css({background:"#EFF6FA"});
			    		 	$("tr:odd").css({background:"#FBFDFD"});
			    		 	
			    		 	$(".jazz-field-comp-input").attr("disabled","disabled");
					    });
				});
	    	},
	    	
			 /**
				 * 根据sExtSequence查询查询失信被执行人信息（DC_BL_LAOLAI）
				 */
			 queryFunctionConfig : function(ancheid){
				 var params = {
						 ancheid:ancheid
			    		};
				   //查询失信被执行人信息（DC_BL_LAOLAI）
				  //   var panelUrl="../../otherselect/getQueryById.do";
				     var panelUrl="../../NBselect/getDWDBQueryDetail.do?ancheid="+ancheid;
				 //	$("#querySXDetialPanel").formpanel("option","dataurlparams",params);
	    			$("#NBQYDWDBDetialPanel").formpanel("option","dataurl",panelUrl);
	    			$("#NBQYDWDBDetialPanel").formpanel("reload");
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
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editFunc(\''+data[i].functionId+'\')">' + "编辑" + '</a>'
	    						+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
	    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>';
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
	    	editFunc:function(functionId){
	    		util.openWindow("editFunctionConfig","编辑功能信息","editFunctionConfig.html?functionId="+functionId,1000,350);
	    		/*var url = $_this.getContextPath()+"/mgr/torch/sysconfig/editFunctionConfig.html?functionId="+functionId;
	    		window.location.href=url;*/
	    	},
	    	/**
	    	 * 打开queryEditConfig.html页面
	    	 */
	    	configEditOrQuery:function(functionId){
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/queryEditConfig.html?functionId="+functionId;
	    		window.location.href=url;
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
	    	goBack:function (){
	    		history.go(1);
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
