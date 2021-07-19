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
					    	//企业年报头信息处理
				    	    $_this.queryNBDetail();
				    	    
				    	    //初始化【企业年报基本信息】 
							$_this.initQYJBXXData();
							 //初始化【企业年报资产状况信息】 
							$_this.initQYZCZKData();
							
							//函数导出
						    util.exports('NBDetailList',$_this.NBDetailList);
					    });
				});
	    	},
	    	//查询总条数
	    	getCount:function(){
	    		 $("#totals").text("") 
	    		var params = {
	    				url : '../../NBselect/NBQueryList.do?flag=1',
	    				components: ['NBQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
			 /**
			   * 查询个体或企业年报信息
			   */
	    	queryNBDetail : function(){
	    		var toBackUrl = jazz.util.getParameter('toBackUrl')||'';//跳转到后台的地址
	    		var entityNo = jazz.util.getParameter('entityNo')||'';
	    		var id = jazz.util.getParameter('id')||'';
	    		var type = jazz.util.getParameter('type')||'';
				/*	torch.getCount();
	    		$("#tips").css("display","block");*/
	    		 var params = {
	    				 entityNo:entityNo,
	    				 id:id,
	    				 type:type
			    		};

	    		var gridUrl="../../"+toBackUrl;;
	    		$("#NBGTQueryDetailGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#NBGTQueryDetailGrid").gridpanel("option","dataurlparams",params);
	    		$("#NBGTQueryDetailGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#NBGTQueryDetailGrid").gridpanel("reload");
	    		
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
	    			//data[i]["ancheyear"]= data[i].ancheyear+ "年度报告";
	    			//data[i]["updatetime"] = data[i].updatetime.split("-")[0]+"年" +data[i].updatetime.split("-")[1]+"月"+data[i].updatetime.split("-")[2]+"日";
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.NBDetailList(\''+data[i].ancheid+'\')">' + "" + '</a>'
	    						/*+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
	    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>'*/
	    	 					;
	    		}
	    		
	    		return data;
	    	},
	    	/**
	    	 * 打开企业或者个体报表信息页面
	    	 */
	    	NBDetailList:function(ancheid){
	    		var isGtOrQe = jazz.util.getParameter('isGtOrQe')||'';
	    		if(isGtOrQe==0){
	    			var url = $_this.getContextPath()+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&type=gt";
	    		}
	    		if(isGtOrQe==1){
	    			var url = $_this.getContextPath()+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&type=qy";
	    		}
	    		
	    		window.open(url);
	    	},
	    	
	    	/*
	    	 * 清除指定funcConfig
	    	 */
	    	delFunc:function(functionId){
	    		jazz.info("对不起，您的权限不足，请联系管理员！");
	    		return;
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
	    	regDetail:function(id, enttype, opetype, entstatus){
	    			var economicproperty ="";
	    	  		if(enttype.substring(0,1)=="1"||enttype.substring(0,1)=="2"||enttype.substring(0,1)=="3"||enttype.substring(0,1)=="4" || enttype.substring(0,1)=="A" || enttype.substring(0,1)=="C" ){//内资企业
	    	  			economicproperty="2";
	    	  		}else if(enttype.substring(0,1)=="5"||enttype.substring(0,1)=="6"||enttype.substring(0,1)=="7" || enttype.substring(0,1)=="W" || enttype.substring(0,1)=="Y"  ){//外资企业
	    	  			economicproperty= "3";
	    	  		}else if(enttype.substring(0,2)=="95"){//个体
	    	  			economicproperty= "1";
	    	  		}else if(enttype.substring(0,1) == "8"){//集团
	    	  			economicproperty= "4";
	    	  		}else{
	    	  			economicproperty= "2"; //暂时先写成2
	    	  		}
	    	  	//	var urlleft="<%=request.getContextPath()%>";
	    		var urlright = "page/reg/regDetail.jsp";
	    		var url = $_this.getContextPath() + "/" + urlright + "?flag=" + encode("0")
	    				+ "&economicproperty=" + encode(economicproperty) + "&priPid="
	    				+ encode(id) + "&opetype=" + encode(opetype) + "&entstatus="
	    				+ encode(entstatus);
	    		window.open(url);

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
	    		var url = $_this.getContextPath()+"/mgr/torch/sysconfig/functionConfig.html";
	    		window.location.href=url;
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
	    				$('#entmark').radiofield('setValue','1');
	    				$('#nbmark').radiofield('setValue','1');
	    	},
	    	edit_fromNames : [ 'NBQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
