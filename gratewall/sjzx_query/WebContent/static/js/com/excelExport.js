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
					    	
					    	$("div[name='fddbrMobtel']").find("div[index='0']").css("width","136");
					    	$("div[name='gsllyMobtel']").find("div[index='0']").css("width","136");
					    	
				    		$("#save_button").off('click').on('click',$_this.exportExcel);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
					    });
				});
	    	},
	    	
			 /**
				 * 导出excel
				 */
	    	exportExcel : function(e){
	    		var data = decode(jazz.util.getParameter("data"))||"";
	    		var excelFlag = jazz.util.getParameter("excelFlag");	
	    		var count = jazz.util.getParameter("count");
	    		var aa = $("#formpanel").formpanel('getValue');
	    		var jsonstr = JSON.stringify(aa);
	    		
	    		var parant = window.parent;
	    		
	    		$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		/*var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.exportExcel);
	    		}, 4000);*/
	    		
	    	 		
	    		//alert(jsonstr);
	    		if(excelFlag == '1'){
	    			if(count > 10000){
	    				$('#save_button').button('disable');
	    				jazz.confirm('由于您导出的数据较多，导出时间<br>可能较长。请耐心等待！',function(){
			    			//top.topWindow.window('close');
			    			//jazz.window.close(e)
	    					$("#process").css("display","block");	   
	    					parant.post("../comselect/exportExcel.do", {mydata :data, select: jsonstr});
	    						//$_this.post("../../comselect/exportExcel.do", {mydata :data, select: jsonstr});
			    		},function(){
			    		});
	    			}else{
	    				$('#save_button').button('disable');
	    				jazz.confirm('即将导出您查询出的全部数据。',function(){
	    					$("#process").css("display","block");	   
			    			//top.topWindow.window('close');
			    			//jazz.window.close(e)
			    			//$_this.post("../../comselect/exportExcel.do", {mydata :data, select: jsonstr});
	    					//$_this.post("../../comselect/exportExcel.do", {mydata :data, select: jsonstr});
	    					parant.post("../comselect/exportExcel.do", {mydata :data, select: jsonstr});
			    		},function(){
			    		});
	    			}
		    	}else{
		    		$('#save_button').button('disable');
	    			jazz.confirm('导出?<br>该导出最多只能导出前5000条！！',function(){
	    				$("#process").css("display","block");	   
		    			//$_this.post("../../comselect/exportExcel.do", {mydata :data, select: jsonstr});
    						//$_this.post("../../comselect/exportExcel.do", {mydata :data, select: jsonstr});
	    				parant.post("../comselect/exportExcel.do", {mydata :data, select: jsonstr});
		    		},function(){
		    		});
	    		}
				        		
			 },
			 
			 post : function(URL, PARAMS) {        
				    var temp = document.createElement("form");        
				    temp.action = URL;        
				    temp.method = "post";        
				    temp.style.display = "none";        
				    for (var x in PARAMS) {        
				        var opt = document.createElement("textarea");        
				        opt.name = x;        
				        opt.value = PARAMS[x];                
				        temp.appendChild(opt);        
				    }        
				    document.body.appendChild(temp);        
				    temp.submit();               
				},
				 /*  //查询失信被执行人信息（DC_BL_LAOLAI）
				  //   var panelUrl="../../otherselect/getQueryById.do";
				     var panelUrl="../../otherselect/getQueryById.do?data="+data;
				 //	$("#querySXDetialPanel").formpanel("option","dataurlparams",params);
	    			$("#querySXDetialPanel").formpanel("option","dataurl",panelUrl);
	    			$("#querySXDetialPanel").formpanel("reload");*/
	    	
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
	    	
	   	 /**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    	},
	    	edit_fromNames : [ 'formpanel']
		};
	
		torch._init();
		return torch;
});
