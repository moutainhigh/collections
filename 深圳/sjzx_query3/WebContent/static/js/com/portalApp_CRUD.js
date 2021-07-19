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
					    	//页面元素处理
							$_this.loadData();
							$("#back_button").off('click').on('click',$_this.goBack);
					    	$("#save_button").off('click').on('click',$_this.saveData);
					    	
					    	
			    		 //	$("tr:odd").css({background:"#FBFDFD"});
			    	//	 	$(".jazz-field-comp-input").attr("disabled","disabled");
			    		////若董事有兼任经理，初始化经理信息
//							$_this.initManagerData();
							//函数导出
			    		 //	util.exports("birthdayDateVerify",$_this.birthdayDateVerify());
					    });
				});
	    	},
	    	
	    	loadData : function() {
	    		var pkSysIntegration = jazz.util.getParameter("pkSysIntegration")||"";
				var type = jazz.util.getParameter("type")||"";
				if(type == "detail"){
					$("tr:even").css({background:"#EFF6FA"});
					$("#save_button").button("hide");
					$("#systemName").textfield('option','rule','');
					$("#pid").comboxfield('option','rule','');
					$("#integratedUrl").textfield('option','rule','');
					$("#portalAppEditOrAddPannel").formpanel("option","readonly",true);
					$_this.setMessage(pkSysIntegration);
				}
			
				if(type == "add"){
					$('div[name="createrName"]').textfield("option","disabled",true);
					$('div[name="createrTime"]').textfield("option","disabled",true);
					$('div[name="modifierName"]').hide();
					$('div[name="modifierTime"]').hide();
					//$_this.setMessage(pkSysIntegration);
				}
				if(type == "edit"){
					$('div[name="createrName"]').textfield("option","disabled",true);
					$('div[name="createrTime"]').textfield("option","disabled",true);
					$('div[name="modifierName"]').textfield("option","disabled",true);
					$('div[name="modifierTime"]').textfield("option","disabled",true);
					$_this.setMessage(pkSysIntegration);
				}
			},
			
			setMessage:function(pkSysIntegration){
				$.ajax({
					url : '../../PortalAppSelect/PortalAppQueryDetail.do',
					data:{
						pkSysIntegration:pkSysIntegration
    				},
					type : "post",
					async : false,
					dataType : "json",
					success : function(data) {
						var jsonData = data.data;
						if ($.isArray(jsonData)) {
							for (var i = 0, len = jsonData.length; i < len; i++) {
								$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
						    }
					    }
					}
    			});
			},
			
			
			saveData:function(){
				var systemName = $("div[name='systemName']").textfield('getValue');
				var pid = $("div[name='pid']").comboxfield('getValue');
				var integratedUrl = $("div[name='integratedUrl']").textfield('getValue');
				
				var pkSysIntegration = jazz.util.getParameter("pkSysIntegration")||"";
				var oldIp = jazz.util.getParameter("oldIp")||"";
				var type = jazz.util.getParameter("type")||"";
				if(systemName == ''||pid == ''||integratedUrl==''){
					jazz.warn("有必填选项未填写，保存失败 ！");
				}else{
					if(type == "add"){
						var params = {
								url : '/PortalAppSelect/saveAdd.do',
								components : [ 'portalAppCRUDPanel' ],
								params:{
									pkSysIntegration:pkSysIntegration
				    			},
								callback : function(data, param, res) { 
									var msg = res.getAttr("save");
			    					if(msg=='success'){
			    						jazz.info("保存成功！",function(){
			    							util.closeWindow("portalAppQueryList.html",true);
			    							/*var configId = jazz.util.getParameter("configId");
			    				    		if(configId){
			    				    			$_this.reloadQueryConfig(configId);
			    				    		}*/
			    						});
			    					}else{
			    						jazz.warn("该条数据已经存在，添加失败 ！");
			    					}
								}
							};
							$.DataAdapter.submit(params);
					}
					if(type == "edit"){
						var params = {
								url : '/PortalAppSelect/saveUpdate.do',
								components : [ 'portalAppCRUDPanel' ],
								params:{
									pkSysIntegration:pkSysIntegration,
									oldIp:oldIp
				    			},
								callback : function(data, param, res){
									var msg = res.getAttr("save");
			    					if(msg=='success'){
			    						jazz.info("保存成功！",function(){
			    							util.closeWindow("portalAppQueryList.html",true);
			    							/*var configId = jazz.util.getParameter("configId");
			    				    		if(configId){
			    				    			$_this.reloadQueryConfig(configId);
			    				    		}*/
			    						});
			    					}
									else{
										jazz.warn("编辑失败");
									}
								}
						};
						$.DataAdapter.submit(params);
					}
					
				}
				
			},
			 /**
				 * 根据sExtSequence查询查询失信被执行人信息（DC_BL_LAOLAI）
				 */
			 queryFunctionConfig : function(id){
				/* var params = {
						 id:id
			    		};*/
				   //查询失信被执行人信息（DC_BL_LAOLAI）
				  //   var panelUrl="../../otherselect/getQueryById.do";
				     var panelUrl="../../NBselect/getContactDetailById.do?id="+id;
				 //	$("#querySXDetialPanel").formpanel("option","dataurlparams",params);
	    			$("#queryContactDetialPanel").formpanel("option","dataurl",panelUrl);
	    			$("#queryContactDetialPanel").formpanel("reload");
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
	    	
	    	//html中获取当前页的 上下文路径
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
	    	},
	    	goBack:function (){
	    		//window.top.$("#frame_maincontent").get(0).contentWindow.leave();
	    		$_this.closeWindow("portalApp_CRUD");
	    	},
	    	/**
	    	 * 关闭窗口
	    	 */
	    	closeWindow:function (name) {
	    		if(top != self){
	    			var oWin = $("div[name='" + name + "']", window.parent.document);
	    			var close = oWin.find('.jazz-titlebar-icon-close');
	    			if(close){
	    				/*if(reload){
	    					parent.location.reload();
	    				}*/
	    				close.click();
	    			}
	    			
	    		}else{
	    			var oWin = $("div[name='" + name + "']");
	        		if(oWin){
	        			oWin.window("close");
	        		}
	    		}   		
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
