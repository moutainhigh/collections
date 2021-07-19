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
					    	//$_this.queryListUserRolesByUserId();
					    	$_this.getSelect();
					    	$_this.getSelectOld();
					    	$_this.getClearInfos();
				    		$("#save_button").off('click').on('click',$_this.queryCFList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
							
						    util.exports('regDetail',$_this.regDetail);
						    util.exports('regfile',$_this.regfile);
					    });
				});
	    	},
	    	 //获取当前用户的user_id 获取用户的角色，然后返回到前台判断是否有权限  
	    	queryListUserRolesByUserId : function() {
	    		var wnb  = "";
	    		var params = {
	    				url : '../../quickselect/queryUserRoles.do',
	    				callback : function(data, param, res) {
	    					 wnb = data.data;
	    					 if(wnb=="success"){
	    						 $("#entnameOld").css("display","block");
	    					 }
	    				}
	    		};
	    		$.DataAdapter.submit(params);
			},
	    	getSelect:function(){
	    		$("#select").comboxfield('addOption', '等于', '1');
	    		$("#select").comboxfield('addOption', '包含', '2');
	    		$("div[name='select']").comboxfield('option', 'defaultvalue', 1);
	    	},
	    	
	    	getSelectOld:function(){
	    		$("#selectOld").comboxfield('addOption', '等于', '1');
	    		$("#selectOld").comboxfield('addOption', '包含', '2');
	    		$("div[name='selectOld']").comboxfield('option', 'defaultvalue', 1);
	    	},
	    	
	    	
	    	getCount:function(){
	    		var params = {
	    				url : '../../quickselect/QuickQueryList.do',
	    				components: ['CFQueryListPanel'],
	    				callback : function(data, param, res) {
	    					$("#totals").html(data.data);
	    				}
	    		};
	    		$.DataAdapter.submit(params);
	    	},
			 /**
			   * 查询失信被执行人信息
			   */
	    	queryCFList : function(){
	    		var entname = $('#entname').textfield("getValue");
	    		var regno = $('#regno').textfield("getValue");
	    		var entnameOld = $("div[name='entnameOld']").textfield("getValue");
				if (!entname && !regno && !entnameOld) {
					jazz.warn("请输入商事主体名称或统一社会信用代码！");
					return false;
				}
	    		var gridUrl="../../quickselect/QuickQueryList.do";
	    		$("#CFQueryListGrid").gridpanel("option",'datarender',torch.backFunction);
	    		$("#CFQueryListGrid").gridpanel("option",'dataurl',gridUrl);
	    		$("#CFQueryListGrid").gridpanel('query', ['CFQueryListPanel'],null);
			},
			/**
			 * SYS_FUNCTION_CONFIG列表回调函数
			 */
			backFunction:function (event, obj) {
	    		var data = obj.data;
	    		if (data.length == 0) {
	    			jazz.warn("该查询没有精确匹配到商事主体");
	    		} else {
	    			$(".nodata").css("display", "none");
	    		}
	    		/*for (var i = 0; i < data.length; i++) {
	    			data[i]["opt"] = '<a href="javascript:void(0);" onclick="ebaic.editFunc(\''+data[i].sExtSequence+'\')">' + "详情" + '</a>'
	    						+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.configEditOrQuery(\''+data[i].functionId+'\')">' + "配置" + '</a>'
	    	 					+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ebaic.delFunc(\''+data[i].functionId+'\')">' + "删除" + '</a>'
	    	 					;
	    		}	*/
	    		
	    		for (var i = 0; i < data.length; i++) {
	    			data[i]["entname"] = '<a href="javascript:void(0)" title="'+data[i]["entname"] +'" onclick="ebaic.regDetail(\''
	    					+ data[i]["pripid"]
	    					+ '\',\''
	    					+ data[i]["type"]
	    					+ '\',\''
	    					+ data[i]["opetype"]
	    					+ '\',\''
	    					+ data[i]["entstatus"]
			    			+ '\',\''
			    			+ data[i]["entname"]
			    			+ '\',\''
			    			+ data[i]["regno"]
			    			+ '\',\''
			    			+ data[i]["entid"]
			    			+ '\',\''
							+ data[i]["bregno"]
	    					+ '\')">' + data[i]["entname"] + ' </a>';
	    			data[i]["file"] = '<a href="javascript:void(0)" onclick="ebaic.regfile(\''
						+ data[i]["entid"]
						+ '\')">' + '影像' + ' </a>';
	    			//data[i]["estdate"] = data[i]["estdate"].substring(0,10);
	    		}
	    		return data;
	    	},
	    	regfile:function(entid){
	    		var url = "http://aaicweb03/EIMIS/frmShowEntImage.aspx?entid=" + entid;
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
	    	regDetail:function(id, enttype, opetype, entstatus, entname, regno, entid,bregno){
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
	    				+ encode(entstatus)+ "&entname=" + encode(entname)+ "&regno=" + encode(regno) + "&entid=" + encode(entid) + "&bregno=" + encode(bregno);
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
	    	
	    	//清空监听文本框清空内容的
	    	getClearInfos:function(){
	    		$("#entnameOld").find("input[name='entnameOld']").on("input propertychange",function(){
	    			$("div[name='select']").comboxfield("option","disabled",true);
	    			$("div[name='regno']").textfield("setValue");
	    			$("div[name='regno']").textfield("option","disabled",true);
	    			$("div[name='entname']").textfield("setValue");
	    			$("div[name='entname']").textfield("option","disabled",true);
	    		
	    		});
	    	},
	    	
	    	/**
	    	 * 重置方法
	    	 */
	    	resetEditCondition : function(){				
				for( x in torch.edit_fromNames){
					$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");					
				}
				
				$('#select').comboxfield('setValue', '1', null);
				$('#selectOld').comboxfield('setValue', '1', null);
				$("div[name='select']").comboxfield("option","disabled",false);
				$("div[name='regno']").textfield("option","disabled",false);
				$("div[name='regno']").textfield("option","disabled",false)
    			$("div[name='entname']").textfield("option","disabled",false);

	    	},
	    	edit_fromNames : [ 'CFQueryListPanel']
	    	
		};
	
		torch._init();
		return torch;
});
