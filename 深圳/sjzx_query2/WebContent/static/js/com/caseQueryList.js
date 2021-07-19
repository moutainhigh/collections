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
					    /*	
					    	var gridUrl="../../caseShow/caseQueryList.do";
				    		$("#caseQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
				    		$("#caseQueryListGrid").gridpanel("option",'dataurl',gridUrl);
				    		$("#caseQueryListGrid").gridpanel('query', ['caseQueryListPanel'],null);*/
				    		
				    		$("#save_button").off('click').on('click',$_this.querycaseList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    $("#add_button").off('click').on('click',$_this.exportFunc);//导出
						    util.exports('caseDetail',$_this.caseDetail);
						    $("#toDates1").css({"display":"inline-block"});
						    $("#toDates2").css({"display":"inline-block"});
						    $("#btnExport").css("display","block");
					    });
				});
	    	},
	    	getCount:function(){
	    		var params = {
	    				url : '../../caseShow/caseQueryList.do?flag=1',
	    				components: ['caseQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
			 /**
			   * 查询失信被执行人信息
			   */
	    	querycaseList : function(){
	    		torch.getCount();
	    		$("#tips").css("display","block");
	    		$("#contactTips").css("display","block");
	    		
	    		$('#save_button').button('disable');
	    		$("#save_button").off('click');
	    		var timer = null;  
	    		clearTimeout(timer);  
	    			timer = setTimeout(function() { 
	    				$('#save_button').button('enable');
	    	    		$("#save_button").on('click',$_this.querycaseList);
	    		}, 1800);
	    		
		    		//查询失信被执行人信息（DC_BL_LAOLAI），并且有回调函数
	    		var gridUrl="../../caseShow/caseQueryList.do";
	    		$("#caseQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#caseQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#caseQueryListGrid").gridpanel('query', ['caseQueryListPanel'],null);
			},
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
			//	$("#tips").css("display","block");
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			$(".nodata").css("display", "block");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["casename"] = '<a href="javascript:void(0);" title="'+data[i]["casename"] +'" onclick="ebaic.caseDetail(\''
	    			    +data[i]["id"]
	    				+ '\',\''
						+ data[i]["litiganttype"]
	    				+ '\',\''
	    				+ data[i]["casesourceno"]
	    			    +'\')">' + data[i]["casename"] + '</a>';
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
	    	caseDetail:function(id, litiganttype,casesourceno){
	    		var url = $_this.getContextPath()+"/page/comselect/caseEntDetail.jsp?id="+id+"&litiganttype="+litiganttype+"&casesourceno="+casesourceno+"&type=case";
	    		window.open(url);

	    	},
	    	/**
	    	 * 打开queryEditConfig.html页面
	    	 */
	    	configEditOrQuery:function(id){
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
	    	
	    	exportFunc : function(){
	    		var caseno = $("#caseno").textfield('getValue');
	    		var casename = $("#casename").textfield('getValue');
	    		var litigantname = $("#litigantname").textfield('getValue');
	    		var litigantcerno = $("#litigantcerno").textfield('getValue');
	    		var pendecno = $("#pendecno").textfield('getValue');
	    		var pendecissdate_begin = $("#pendecissdate_begin").datefield('getValue');
	    		var pendecissdate_end = $("#pendecissdate_end").datefield('getValue');
	    		var caseregistertime_begin = $("#caseregistertime_begin").datefield('getValue');
	    		var caseregistertime_end = $("#caseregistertime_end").datefield('getValue');
	    		var casestate = $("#casestate").hiddenfield('getValue');
	    		$_this.post("../../caseShow/exportExcel.do", {
	    			caseno:caseno,
	    			casename:casename,
	    			litigantname:litigantname,
	    			litigantcerno:litigantcerno,
	    			pendecno:pendecno,
	    			pendecissdate_begin:pendecissdate_begin,
	    			pendecissdate_end:pendecissdate_end,
	    			caseregistertime_begin:caseregistertime_begin,
	    			caseregistertime_end:caseregistertime_end,
	    			casestate:casestate
	    			});
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
	    	
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){
	    				for( x in torch.edit_fromNames){
	    					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
	    				} 
	    	},
	    	edit_fromNames : [ 'caseQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
